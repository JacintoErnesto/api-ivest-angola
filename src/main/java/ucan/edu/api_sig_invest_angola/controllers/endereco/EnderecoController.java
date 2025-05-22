package ucan.edu.api_sig_invest_angola.controllers.endereco;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.services.endereco.EnderecoService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;

@RestController
@RequestMapping(value = "/endereco")
@RequiredArgsConstructor
public class EnderecoController extends Resource {
    @Serial
    private static final long serialVersionUID = -338366793287L;
    private final EnderecoService enderecoService;

    @PostMapping(value = "/criar", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> criar(@Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        try {
            return okRequestOne(this.enderecoService.create(enderecoRequestDTO), "null.valor.entidade", "Endereco");
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping(value = "/buscar-todos", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarTodosEnderecos(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nomeRua") String colunaOder
    ) {
        try {

            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOder);
            Page<EnderecoReturnDTO> enderecoReturnDTOS = enderecoService.buscarTodos(pageRequest);
            if (enderecoReturnDTOS==null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestPaginado(enderecoReturnDTOS);

        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @GetMapping(value = "/buscar-por-id/{id}", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarEnderecoPorId(@PathVariable Long id) {
        try {
            EnderecoReturnDTO enderecoReturnDTO = enderecoService.buscarPorId(id);
            if (enderecoReturnDTO == null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequest(enderecoReturnDTO, 1, MessageUtils.getMessage("sucesso"));
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping(value = "/buscar-por-localidade", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarEnderecoPorLocalidade(
            @RequestParam String localidade,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nomeRua") String colunaOder
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOder);
            Page<EnderecoReturnDTO> enderecoReturnDTOS = enderecoService.buscarPorLocalidade(localidade, pageRequest);
            if (enderecoReturnDTOS == null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestPaginado(enderecoReturnDTOS);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @GetMapping(value = "/buscar-por-nome-bairro", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarEnderecoPorNomeBairro(
            @RequestParam String nomeBairro,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nomeRua") String colunaOder
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOder);
            Page<EnderecoReturnDTO> enderecoReturnDTOS = enderecoService.buscarPorNomeBairo(nomeBairro, pageRequest);
            if (enderecoReturnDTOS == null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestPaginado(enderecoReturnDTOS);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @GetMapping(value = "/buscar-por-nome-rua", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> buscarEnderecoPorNomeRua(
            @RequestParam String nomeRua,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nomeRua") String colunaOder
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOder);
            Page<EnderecoReturnDTO> enderecoReturnDTOS = enderecoService.buscarPorNomeRua(nomeRua, pageRequest);
            if (enderecoReturnDTOS == null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestPaginado(enderecoReturnDTOS);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @DeleteMapping(value = "/deletar", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> deletarEndereco(@RequestParam Long id) {
        try {
            enderecoService.delete(id);
            return okRequestOne("", null);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }
    @PutMapping(value = "/atualizar", produces = "application/json")
    public ResponseEntity<RestDataReturnDTO> atualizarEndereco(
            @RequestParam Long id,
            @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO
    ) {
        try {
            return okRequestOne(this.enderecoService.update(id, enderecoRequestDTO), "null.valor.entidade", "Endereco");
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

}
