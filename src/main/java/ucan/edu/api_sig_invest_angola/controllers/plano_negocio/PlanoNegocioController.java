package ucan.edu.api_sig_invest_angola.controllers.plano_negocio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucan.edu.api_sig_invest_angola.utils.Resource;

import java.io.Serial;

@RestController
@RequestMapping(value = "/plano-negocio")
@RequiredArgsConstructor
public class PlanoNegocioController extends Resource {

    @Serial
    private static final long serialVersionUID = 1L;

    // Add your service and methods here

    // Example method
    // @PostMapping("/create")
    // public ResponseEntity<PlanoNegocioReturnDTO> createPlanoNegocio(@RequestBody PlanoNegocioRequestDTO request) {
    //     return ResponseEntity.ok(planoNegocioService.create(request));
    // }
}
