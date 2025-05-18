package ucan.edu.api_sig_invest_angola.services.serviceImpls.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.auth.AutenticatioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthrReturnDTO;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;
import ucan.edu.api_sig_invest_angola.repositories.AuthRepository;
import ucan.edu.api_sig_invest_angola.services.auth.AuthService;
import ucan.edu.api_sig_invest_angola.services.auth.JwtService;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;


import java.time.LocalDateTime;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthrReturnDTO criarConta(AuthRequestDTO authRequestDTO) {
        try {
            isUsernameExist(authRequestDTO.username());

            Conta conta = new Conta();
            conta.setUsername(authRequestDTO.username());
            conta.setPassword(passwordEncoder.encode(authRequestDTO.password()));
            conta.setTipoConta(TipoConta.forDescricao(String.valueOf(authRequestDTO.tipoConta())));
            conta.setDataRegisto(LocalDateTime.now());

            this.authRepository.save(conta);
            Map<String, Object> claims = Map.of(
                    "id", conta.getId(),
                    "nome", conta.getAuthorities()
            );
            String jwt = jwtService.gerarToken(claims, conta);

            AuthrReturnDTO authrReturnDTO = new AuthrReturnDTO();
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
    public AuthrReturnDTO login(AutenticatioRequestDTO autenticatioRequest) {
        try {
            authenticationManager.authenticate(
                    new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            autenticatioRequest.username(),
                            autenticatioRequest.password()
                    )
            );
        }catch (BadCredentialsException ex) {
            // Aqui você lança sua exceção personalizada
            throw new PortalBusinessException("Usuário ou senha incorretos");
        }
        Conta conta = this.buscarContaPorUsername(autenticatioRequest.username());
        if (conta == null)
            throw new PortalBusinessException(MessageUtils.getMessage("auth.username.not.exist"));
        Map<String, Object> claims = Map.of(
                "id", conta.getId(),
                "tipoConta", conta.getAuthorities()
        );
        String jwt = jwtService.gerarToken(claims, conta);
        AuthrReturnDTO authrReturnDTO = new AuthrReturnDTO();
        authrReturnDTO.setData(jwt);

        return authrReturnDTO;
    }

    private void isUsernameExist(String username) {
        if (this.buscarContaPorUsername(username) != null) {
            throw new PortalBusinessException(MessageUtils.getMessage("auth.username.exist"));
        }
    }

    @Override
    public Conta buscarContaPorUsername(String username) {
        return this.authRepository.findByUsername(username).orElse(null);
    }
}
