package ucan.edu.api_sig_invest_angola.controllers.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.api_sig_invest_angola.dtos.auth.*;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.services.auth.AuthService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends Resource {
    @Serial
    private static final long serialVersionUID = 1L;

    private final AuthService authService;

    @PostMapping(value = "/criar-conta", produces = "application/json")
    @CrossOrigin("*")
    public ResponseEntity<AuthReturnDTO> criarConta(@Valid @RequestBody AuthContaRequestDTO authRequestDTO) {
        try {
            return okAuthRequestOne(this.authService.criarConta(authRequestDTO).getData(), null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badAuthRequest("erro", null, e);
        }

    }

    @PostMapping(value = "/login", produces = "application/json")

    public ResponseEntity<AuthReturnDTO> login(@Valid @RequestBody AutenticatioRequestDTO autenticatioRequest) {
        try {
            return okAuthRequestOne(this.authService.login(autenticatioRequest).getData(), null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badAuthRequest("erro", null, e);
        }
    }

    @GetMapping(value = "/buscar-conta-por-username", produces = "application/json")
    public ResponseEntity<AuthReturnDTO> buscarContaPorUsername(@RequestParam String username) {
        try {
            return okAuthRequestOne(this.authService.buscarContaPorUsername(username), null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badAuthRequest("erro", null, e);
        }
    }

    @PutMapping(value = "/resetar-password", produces = "application/json")
    public ResponseEntity<AuthReturnDTO> resetarPassword(@RequestParam Long id,
                                                         @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        try {
            boolean isReset = this.authService.resetarPassword(id, resetPasswordRequestDTO);
            if (!isReset) {
                throw new PortalBusinessException(MessageUtils.getMessage("erro.resetar.senha"));
            }
            return okAuthRequestOne(true, null);
        } catch (PortalBusinessException e) {
            return super.badAuthRequest(e.getMessage(), null, e);
        } catch (Exception e) {
            return super.badAuthRequest("erro.interno", null, e);
        }
    }

    @GetMapping(value = "/buscar-contas-por-tipo-conta", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarContasPorTipoConta(@RequestParam String tipoConta,
                                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                                  @RequestParam(required = false, defaultValue = "id") String colunaOrder) {

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOrder);

        try {
            Page<AuthContaReturnDTO> contas = this.authService.buscarContasPorTipoConta(tipoConta, pageable);
            if (contas.isEmpty()) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestAuthPaginado(contas);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping(value = "/buscar-todas-contas", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarTodasContas(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "10") int size,
                                                               @RequestParam(required = false, defaultValue = "id") String colunaOrder) {
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOrder);
        try {
            Page<AuthContaReturnDTO> authContaReturnDTOS = this.authService.buscartodas(pageable);
            if (authContaReturnDTOS.isEmpty()) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestAuthPaginado(authContaReturnDTOS);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
}
