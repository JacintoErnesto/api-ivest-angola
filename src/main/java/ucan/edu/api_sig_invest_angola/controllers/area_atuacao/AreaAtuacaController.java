package ucan.edu.api_sig_invest_angola.controllers.area_atuacao;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.RetornoDTO;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestMessageReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessTokenRejectException;
import ucan.edu.api_sig_invest_angola.services.area_atuacao.AreaAtuacaoService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.Utils;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/suporte-negocio/area-atuacao")
@RequiredArgsConstructor
public class AreaAtuacaController extends Resource {
    @Serial
    private static final long serialVersionUID = -3332833287L;
    private final AreaAtuacaoService areaAtuacaoService;
    private final CacheManager cacheManager;

    @PostMapping(value = "/criar", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> criar(@Valid @RequestBody AreaAtuacaoRequestDTO areaAtuacaoRequestDTO) {
        try {
            return okRequestOneCaching(this.areaAtuacaoService.criar(areaAtuacaoRequestDTO), "null.valor.entidade", "Area atuacao");
        } catch (PortalBusinessTokenRejectException e) {
            throw new PortalBusinessTokenRejectException(e.getMessage());
        }
        catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @GetMapping(value = "/buscar-todas", produces ="application/json")
    @Cacheable(value = "areaAtuacao")
    public ResponseEntity<RestDataReturnDTO> buscarTodasLocalidades() {
        try {
            String msg = MessageUtils.getMessage("sucesso");
            List<AreaAtuacaoReturnDTO> areaAtuacaoReturnDTOList =this.areaAtuacaoService.buscarTodos();
            if (areaAtuacaoReturnDTOList != null)
                return okRequestCaching(areaAtuacaoReturnDTOList);
            return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }
        catch (Exception e) {
            System.out.println("erro = " + e.getMessage());
            return super.okRequestOneCaching(null, e.getMessage(), e);
        }
    }

    @PutMapping(value = "atualizar-area")
    public ResponseEntity<RestDataReturnDTO> atualizarAreaAtuacao(@RequestParam Long id,
                                                                  @Valid @RequestBody
                                                                  AreaAtuacaoRequestDTO areaAtuacaoRequestDTO){
        try {
            AreaAtuacaoReturnDTO areaAtuacaoReturnDTO = this.areaAtuacaoService.atualizar(id, areaAtuacaoRequestDTO);
            if (areaAtuacaoReturnDTO == null) {
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            }
            return okRequest(areaAtuacaoReturnDTO,1,MessageUtils.getMessage("sucesso"));
        } catch (Exception e) {
            return super.badRequest("erro.atualizaro", null, e);
        }
    }
//    @GetMapping(value = "/buscar-todas-localidade-page", produces ="application/json")
//    @Cacheable(value = "localidades-page")
//    public ResponseEntity<RestDataReturnDTO> buscarTodasLocalidadesPaginadas(@RequestParam(required = false, defaultValue = "0") int page,
//                                                                             @RequestParam(required = false, defaultValue = "10") int size,
//                                                                             @RequestParam(required = false, defaultValue = "designacao") String colunaOrder) {
//
//        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOrder);
//        try {
//            Page<LocalidadeReturnDTO> localidadeReturnDTOS = this.localidadeService.buscarTodosPaginados(pageable);
//            if (localidadeReturnDTOS == null || localidadeReturnDTOS.isEmpty()) {
//                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
//            }
//            return super.okRequest(localidadeReturnDTOS, (int) localidadeReturnDTOS.getTotalElements(), MessageUtils.getMessage("sucesso"));
//        } catch (Exception e) {
//            return super.badRequest("erro.na.busca", null, e);
//        }
//    }
    @GetMapping(value = "/buscar-area-atuacao-id", produces ="application/json")
    @Cacheable(value = "areaAtuacaoId")
    public ResponseEntity<RestDataReturnDTO> buscarAreaAtuacaoPeloId(
            @NotBlank(message = "O localidadeId é um campo obrigatório ") @RequestParam Long id) {
        try {
            AreaAtuacaoReturnDTO areaAtuacaoReturnDTO = this.areaAtuacaoService.buscarPorId(id);
            if (areaAtuacaoReturnDTO == null) {
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            }
            return super.okRequest(areaAtuacaoReturnDTO, 1, MessageUtils.getMessage("sucesso"));
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
    @DeleteMapping(value = "/deletar", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> apagarAreaAtuacao(@RequestParam Long id) {
        try {
            this.areaAtuacaoService.deletar(id);
            RestDataReturnDTO dto = new RestDataReturnDTO();
            dto.setData(null);
            dto.setQuantidadeTotalItens(1);
            dto.setRetorno(new RestMessageReturnDTO(200, MessageUtils.getMessage("sucesso"))); // <- Verifique aqui!
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    private void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("areaAtuacao")).clear();
        Objects.requireNonNull(cacheManager.getCache("areaAtuacaoId")).clear();
    }

}
