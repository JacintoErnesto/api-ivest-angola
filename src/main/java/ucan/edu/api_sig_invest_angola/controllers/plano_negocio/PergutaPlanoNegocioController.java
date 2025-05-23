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
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta.PerguntaPlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta.PerguntaPlanoNegocioReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.services.plano_negocio.pergunta.PerguntaPlanoNegocioService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;
import java.util.Objects;

@RestController
@RequestMapping("/suporte-negocio/pergunta-plano-negocio")
@RequiredArgsConstructor
public class PergutaPlanoNegocioController extends Resource {
    @Serial
    private static final long serialVersionUID = 1L;
    private final PerguntaPlanoNegocioService perguntaPlanoNegocioService;
    private final CacheManager cacheManager;
    @PostMapping(value = "/criar", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> criar(@Valid @RequestBody PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO) {
        try {
            return okRequest(this.perguntaPlanoNegocioService.criarPerguntaPlanoNegocio(perguntaPlanoNegocioRequestDTO));
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }


    @GetMapping(value = "/buscar-todos", produces ="application/json")
    @Cacheable(value = "buscar-todos")
    public ResponseEntity<RestDataReturnDTO> buscarTodos(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false, defaultValue = "id") String colunaOerder){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOerder);
        try {
            Page<PerguntaPlanoNegocioReturnDTO> perguntaPlanoNegocioReturnDTOPage = this.perguntaPlanoNegocioService.listarPerguntaPlanoNegocio(pageRequest);
            if (perguntaPlanoNegocioReturnDTOPage == null)
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            return okRequestPaginado(perguntaPlanoNegocioReturnDTOPage);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping(value = "/buscar/{id}", produces ="application/json")
    @Cacheable(value = "buscar-por-id")
    public ResponseEntity<RestDataReturnDTO> buscarPorId(@PathVariable Long id) {
        try {
            String msg = MessageUtils.getMessage("sucesso");
            PerguntaPlanoNegocioReturnDTO perguntaPlanoNegocioReturnDTO = this.perguntaPlanoNegocioService.buscarPerguntaPlanoNegocioPorId(id);
            if (perguntaPlanoNegocioReturnDTO == null)
                return super.okRequestOneCaching(null, "sem.nada.no.retorno", null);
            return  okRequestOne(perguntaPlanoNegocioReturnDTO, msg);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @PutMapping(value = "/atualizar/{id}", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO) {
        try {
            return okRequest(this.perguntaPlanoNegocioService.atualizarPerguntaPlanoNegocio(id, perguntaPlanoNegocioRequestDTO));
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @DeleteMapping(value = "/deletar/{id}", produces ="application/json")
    public ResponseEntity<RestDataReturnDTO> deletar(@PathVariable Long id) {
        try {
            this.perguntaPlanoNegocioService.deletarPerguntaPlanoNegocio(id);
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
        Objects.requireNonNull(cacheManager.getCache("buscar-todos")).clear();
    }
}
