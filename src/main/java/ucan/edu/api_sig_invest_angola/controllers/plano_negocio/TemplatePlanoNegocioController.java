package ucan.edu.api_sig_invest_angola.controllers.plano_negocio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplatePlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplateNegocioReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.services.plano_negocio.template.TemplatePlanoNegocioService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;
import java.util.Objects;

@RestController
@RequestMapping("/suporte-negocio/template-plano-negocio")
@RequiredArgsConstructor
public class TemplatePlanoNegocioController extends Resource {
    @Serial
    private static final long serialVersionUID = -33833287L;
    private final TemplatePlanoNegocioService templatePlanoNegocioService;
    private final CacheManager cacheManager;

    @PostMapping(value = "/criar", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> criar(@Valid @RequestBody
                                                       TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO) {
        try {
            return okRequest(this.templatePlanoNegocioService.criarTemplatePlanoNegocio(templatePlanoNegocioRequestDTO));
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @PostMapping(value = "/buscar-todos", produces ="application/json")
    @Cacheable(value = "buscar-todos")
    public ResponseEntity<RestDataReturnDTO> buscarTodos(@RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size,
                                                    @RequestParam(required = false, defaultValue = "id") String colunaOerder){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOerder);
        try {
            Page<TemplateNegocioReturnDTO> templatePlanoNegocioReturnDTOPage = this.templatePlanoNegocioService.listarTemplatePlanoNegocio(pageRequest);
            if (templatePlanoNegocioReturnDTOPage == null)
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            return okRequestPaginado(templatePlanoNegocioReturnDTOPage);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @PostMapping(value = "/buscar-todos-area-atuacao/{areaAtuacaoId}", produces ="application/json")
    @Cacheable(value = "buscar-todos-area-atuacao")
    public ResponseEntity<RestDataReturnDTO> buscarTodos(@RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size,
                                                         @RequestParam(required = false, defaultValue = "id") String colunaOerder,
                                                         @PathVariable Long areaAtuacaoId){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOerder);
        try {
            Page<TemplateNegocioReturnDTO> templatePlanoNegocioReturnDTOPage = this.templatePlanoNegocioService.listarTemplatePlanoNegocioPorAreaAtuacao(areaAtuacaoId,pageRequest);
            if (templatePlanoNegocioReturnDTOPage == null)
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            return okRequestPaginado(templatePlanoNegocioReturnDTOPage);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @GetMapping(value = "/buscar-por-id/{id}", produces ="application/json")
    @Cacheable(value = "buscar-por-id")
    public ResponseEntity<RestDataReturnDTO> buscarPorId(@PathVariable Long id) {
        try {
            String msg = MessageUtils.getMessage("sucesso");
            TemplateNegocioReturnDTO templatePlanoNegocioReturnDTO = this.templatePlanoNegocioService.buscarTemplatePlanoNegocioPorId(id);
            if (templatePlanoNegocioReturnDTO == null)
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            return okRequestOne(templatePlanoNegocioReturnDTO, msg);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @PutMapping(value = "/atualizar/{id}", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody
                                                       TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO) {
        try {
            return okRequest(this.templatePlanoNegocioService.atualizarTemplatePlanoNegocio(id, templatePlanoNegocioRequestDTO));
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @DeleteMapping(value = "/deletar/{id}", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> deletar(@PathVariable Long id) {
        try {
            this.templatePlanoNegocioService.deletarTemplatePlanoNegocio(id);
            return okRequest(null, 0, MessageUtils.getMessage("sucesso"));
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
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

    private void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("buscar-por-id")).clear();
        Objects.requireNonNull(cacheManager.getCache("buscar-todos-area-atuacao")).clear();
        Objects.requireNonNull(cacheManager.getCache("buscar-todos")).clear();
    }

}
