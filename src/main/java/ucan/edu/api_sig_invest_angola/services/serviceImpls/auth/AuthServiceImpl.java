package ucan.edu.api_sig_invest_angola.services.serviceImpls.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.auth.*;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRetornoDTO;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;
import ucan.edu.api_sig_invest_angola.repositories.auth.AuthRepository;
import ucan.edu.api_sig_invest_angola.services.auth.AuthService;
import ucan.edu.api_sig_invest_angola.services.auth.JwtService;
import ucan.edu.api_sig_invest_angola.services.empreendedor.EmpreendedorService;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmpreendedorService empreendedorService;

    @Override
    @Transactional
    public AuthReturnDTO criarConta(AuthContaRequestDTO authRequestDTO) {
        AuthContaReturnDTO authContaReturnDTO = null;
        try {
            isUsernameExist(authRequestDTO.username());

            Conta conta = new Conta();

            conta.setUsername(authRequestDTO.username());
            conta.setPassword(passwordEncoder.encode(authRequestDTO.password()));
            conta.setTipoConta(validarTipoContaExiste(authRequestDTO.tipoConta()));
            conta.setPerfilCompleto(false);
            conta.setDataRegisto(LocalDateTime.now());

            //Salvar a conta
            this.authRepository.save(conta);
            log.info("Salvou o objecto :" +conta);
            if (conta.getTipoConta().name().equals("EMPREENDEDOR")) {
                this.empreendedorService.create(new EmpreendedorRequestDTO(authRequestDTO.nome(), authRequestDTO.nif()), conta);
            }
            authContaReturnDTO = mapContaToAuthContaReturnDTO(conta);
            Map<String, Object> claims = buildClaims(authContaReturnDTO);
            String jwt = jwtService.gerarToken(claims, conta);

            AuthReturnDTO authrReturnDTO = new AuthReturnDTO();
            authrReturnDTO.setData(jwt);

            return authrReturnDTO;

        } catch (PortalBusinessException e) {
            LOGGER.error("Erro de negócio ao criar conta", e);
            throw e; // relança sem perder stacktrace
        } catch (IllegalArgumentException e) {
            LOGGER.error("Tipo de conta inválido", e);
            throw new PortalBusinessException("Tipo de conta inválido: " + authRequestDTO.tipoConta());
        } catch (Exception e) {
            LOGGER.error("Erro inesperado ao criar conta", e);
            throw new PortalBusinessException("Erro ao criar conta: " + e.getMessage());
        }
    }

    @Override
    public AuthReturnDTO login(AutenticatioRequestDTO autenticatioRequest) {
        AuthContaReturnDTO authContaReturnDTO = null;
        try {
            authenticationManager.authenticate(
                    new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            autenticatioRequest.username(),
                            autenticatioRequest.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            // Aqui você lança sua exceção personalizada
            throw new PortalBusinessException("Usuário ou senha incorretos");
        }
        Conta conta = this.authRepository.findByUsername(autenticatioRequest.username()).orElse(null);

        if (conta == null) {
            throw new PortalBusinessException(MessageUtils.getMessage("auth.username.not.exist"));
        }
        authContaReturnDTO = mapContaToAuthContaReturnDTO(conta);
        Map<String, Object> claims = buildClaims(authContaReturnDTO);

        String jwt = jwtService.gerarToken(claims, conta);
        AuthReturnDTO authrReturnDTO = new AuthReturnDTO();
        authrReturnDTO.setData(jwt);

        return authrReturnDTO;
    }

    private Map<String, Object> buildClaims(AuthContaReturnDTO dto) {
        Map<String, Object> contaMap = new HashMap<>();
        EmpreendedorRetornoDTO empreendedor = null;
        String nome = null;
        String nif = null;
        if (dto.tipoConta().equals("EMPREENDEDOR")) {
            empreendedor = this.empreendedorService.buscarEmpreendedorPorConta(dto.id());
            nome = empreendedor.nomeEmpreendedor();
            nif = empreendedor.nifEmpreendedor();
            System.out.println();
        }

        contaMap.put("id", dto.id());
        contaMap.put("username", dto.username());
        contaMap.put("nome", nome);
        contaMap.put("nif", nif);
        contaMap.put("tipoConta", dto.tipoConta());
        contaMap.put("perfilCompleto", dto.perfilCompleto());

        // Converte LocalDateTime para String (ISO format)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        contaMap.put("dataRegisto", dto.dataRegisto().format(formatter));

        // Transforma authorities em lista de strings
        contaMap.put("authorities", dto.authorities().stream()
                .map(auth -> auth.getAuthority())
                .toList());

        contaMap.put("accountNonExpired", dto.accountNonExpired());
        contaMap.put("accountNonLocked", dto.accountNonLocked());
        contaMap.put("credentialsNonExpired", dto.credentialsNonExpired());
        contaMap.put("enabled", dto.enabled());

        return Map.of("conta", contaMap);
    }

    private void isUsernameExist(String username) {
        if (this.buscarContaPorUsername(username) != null) {
            throw new PortalBusinessException(MessageUtils.getMessage("auth.username.exist"));
        }
    }

    private AuthContaReturnDTO mapContaToAuthContaReturnDTO(Conta conta) {
        return new AuthContaReturnDTO(
                conta.getId(),
                conta.getUsername(),
                conta.getTipoConta().toValue(),
                conta.isPerfilCompleto(),
                conta.getDataRegisto(),
                conta.getAuthorities(),
                conta.isAccountNonExpired(),
                conta.isAccountNonLocked(),
                conta.isCredentialsNonExpired(),
                conta.isEnabled()
        );
    }

    @Override
    public AuthContaReturnDTO buscarContaPorUsername(String username) {
        if (this.authRepository.findByUsername(username).isPresent()) {
            Conta conta = this.authRepository.findByUsername(username).get();
            return mapContaToAuthContaReturnDTO(conta);
        }
        return null;
    }

    @Override
    public boolean resetarPassword(Long id, ResetPasswordRequestDTO resetPasswordRequestDTO) {
        if (resetPasswordRequestDTO.password().equals(resetPasswordRequestDTO.confirmPassword())) {
            if (id != null && id > 0) {
                Conta conta = this.authRepository.findById(id).orElse(null);
                if (conta != null) {
                    conta.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.password()));
                    this.authRepository.save(conta);
                    return true;
                }
                return false;
            }
            throw new PortalBusinessException("O id da conta não pode ser nulo ou menor que 0");

        }
        throw new PortalBusinessException("As senhas não coincidem");
    }

    @Override
    public Page<AuthContaReturnDTO> buscarContasPorTipoConta(String tipoConta, PageRequest pageable) {
        TipoConta tipo = TipoConta.forDescricao(tipoConta);
        if (tipo == null) {
            throw new PortalBusinessException("Tipo de conta inválido: " + tipoConta);
        }
        return this.authRepository.findAllByTipoContaOrderById(tipo, pageable).map(
                this::mapContaToAuthContaReturnDTO);
    }

    @Override
    public Page<AuthContaReturnDTO> buscartodas(PageRequest pageRequest) {
        return this.authRepository.findAll(pageRequest).map(
                this::mapContaToAuthContaReturnDTO);
    }

    private TipoConta validarTipoContaExiste(String tipoConta){
        TipoConta tipoContaExiste = TipoConta.forDescricao(String.valueOf(tipoConta));
        if (tipoContaExiste == null)
            throw new PortalBusinessException("Tipo inválida");
        return tipoContaExiste;
    }
}
