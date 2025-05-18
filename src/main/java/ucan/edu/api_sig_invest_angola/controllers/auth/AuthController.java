package ucan.edu.api_sig_invest_angola.controllers.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.api_sig_invest_angola.dtos.auth.AutenticatioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.auth.AuthrReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.services.auth.AuthService;
import ucan.edu.api_sig_invest_angola.utils.Resource;

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
    public ResponseEntity<AuthrReturnDTO> criarConta(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        try {
            return okAuthRequestOne(this.authService.criarConta(authRequestDTO).getData(), null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badAuthRequest("erro", null, e);
        }

    }

    @PostMapping(value = "/login", produces = "application/json")

    public ResponseEntity<AuthrReturnDTO> login(@Valid @RequestBody AutenticatioRequestDTO autenticatioRequest) {
        try {
            return okAuthRequestOne(this.authService.login(autenticatioRequest).getData(), null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badAuthRequest("erro", null, e);
        }
    }
}
