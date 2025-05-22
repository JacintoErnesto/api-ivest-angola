package ucan.edu.api_sig_invest_angola.controllers.localidade;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import org.springframework.cache.CacheManager;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessTokenRejectException;
import ucan.edu.api_sig_invest_angola.models.localidade.Localidade;
import ucan.edu.api_sig_invest_angola.services.localidade.LocalidadeService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/suporte-negocio/localidade")
@RequiredArgsConstructor
public class LocalidadeController  extends Resource {
    @Serial
    private static final long serialVersionUID = -33833287L;
    private final LocalidadeService localidadeService;
    private final CacheManager cacheManager;

    @PostMapping(value = "/criar", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> criar(@RequestBody LocalidadeRequestDTO localidadeRequestDTO) {
        try {
            return okRequestOneCaching(this.localidadeService.criar(localidadeRequestDTO), "null.valor.entidade", "Localidade");
        } catch (PortalBusinessTokenRejectException e) {
            throw new PortalBusinessTokenRejectException(e.getMessage());
        }
        catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @GetMapping(value = "/buscar-todas-localidades", produces ="application/json")
    @Cacheable(value = "localidades")
    public ResponseEntity<RestDataReturnDTO> buscarTodasLocalidades() {
        try {
            String msg = MessageUtils.getMessage("sucesso");
            List<LocalidadeReturnDTO> localidadeRequestDTO =this.localidadeService.buscarTodos();
            return okRequest(localidadeRequestDTO,localidadeRequestDTO.size(), msg);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }
        catch (Exception e) {
            System.out.println("erro = " + e.getMessage());
            return super.okRequestOneCaching(null, e.getMessage(), e);
        }
    }
    @GetMapping(value = "/buscar-todas-localidade-page", produces ="application/json")
    @Cacheable(value = "localidades-page")
    public ResponseEntity<RestDataReturnDTO> buscarTodasLocalidadesPaginadas(@RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "10") int size,
                                                                    @RequestParam(required = false, defaultValue = "designacao") String colunaOrder) {

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOrder);
        try {
            Page<LocalidadeReturnDTO> localidadeReturnDTOS = this.localidadeService.buscarTodosPaginados(pageable);
            if (localidadeReturnDTOS == null || localidadeReturnDTOS.isEmpty()) {
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            }
            return super.okRequest(localidadeReturnDTOS, (int) localidadeReturnDTOS.getTotalElements(), MessageUtils.getMessage("sucesso"));
        } catch (Exception e) {
            return super.badRequest("erro.na.busca", null, e);
        }
    }
    @GetMapping(value = "/buscar-localidade-filho-pelo-id-pai", produces ="application/json")
    @Cacheable(value = "localidades-filho")
    public ResponseEntity<RestDataReturnDTO> buscarLocalidadeFilhoPeloIdPai(
            @NotBlank(message = "O localidadeId é um campo obrigatório ") @RequestParam String localidadeId) {
        try {
            List<LocalidadeReturnDTO> localidadeReturnDTOS = this.localidadeService.buscarLocalidadeFilhoPeloIdPai(localidadeId);
            if (localidadeReturnDTOS == null || localidadeReturnDTOS.isEmpty()) {
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            }
            return super.okRequest(localidadeReturnDTOS, localidadeReturnDTOS.size(), MessageUtils.getMessage("sucesso"));
        } catch (Exception e) {
            return super.badRequest("erro.na.busca", null, e);
        }
    }
    @GetMapping(value = "/limparCache", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> limparCache() {
        try {
            clearCache();
            return okRequestOne("", null);
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping(value = "/buscar-todas-locaidade-pai", produces = "application/json")
    @Cacheable(value = "localidades-pai")
    public ResponseEntity<RestDataReturnDTO> buscarTodasLocalidadesPai() {
        try {
            String msg = MessageUtils.getMessage("sucesso");
            List<LocalidadeReturnDTO> localidadeRequestDTO = this.localidadeService.buscarTodosPai();
            return okRequest(localidadeRequestDTO, localidadeRequestDTO.size(), msg);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.okRequestOneCaching(null, e.getMessage(), e);
        }
    }

    private void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("localidades")).clear();
        Objects.requireNonNull(cacheManager.getCache("localidades-page")).clear();
        Objects.requireNonNull(cacheManager.getCache("localidades-filho")).clear();
        Objects.requireNonNull(cacheManager.getCache("localidades-pai")).clear();
    }

}
