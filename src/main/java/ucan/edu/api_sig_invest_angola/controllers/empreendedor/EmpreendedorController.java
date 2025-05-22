package ucan.edu.api_sig_invest_angola.controllers.empreendedor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRetornoDTO;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.models.empreendedor.Empreendedor;
import ucan.edu.api_sig_invest_angola.services.empreendedor.EmpreendedorService;
import ucan.edu.api_sig_invest_angola.services.endereco.EnderecoService;
import ucan.edu.api_sig_invest_angola.utils.Resource;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.Serial;

@RestController
@RequestMapping("/empreendedor")
@RequiredArgsConstructor
public class EmpreendedorController extends Resource {
    @Serial
    private static final long serialVersionUID = -34567891L;
    private final EmpreendedorService empreendedorService;
    private final EnderecoService enderecoService;

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestDataReturnDTO> atualizar(
            @PathVariable Long id,
            @RequestPart("empreendedorResquetDTO") String empreendedorJson,
            @RequestPart MultipartFile anexoBilhete,
            @RequestPart MultipartFile anexoNif,
            @RequestPart MultipartFile anexoCartaoMunicipe,
            @RequestPart MultipartFile anexoRegistroCriminal) {
        try {

            return okRequest(empreendedorService.atualizar(id, empreendedorJson,
                    anexoBilhete, anexoNif, anexoCartaoMunicipe, anexoRegistroCriminal));
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }


    @GetMapping("/buscar-por-id/{id}")
    public ResponseEntity<RestDataReturnDTO> buscarPorId(@PathVariable Long id) {
        try {
            EmpreendedorRetornoDTO empreendedor = this.empreendedorService.buscarPorId(id);
            if (empreendedor == null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequest(empreendedor);
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping("/buscar-por-localidade/{localidadeId}")
    public ResponseEntity<RestDataReturnDTO> buscarPorLocalidade(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nomeEmpreendedor") String colunaOder,
            @PathVariable String localidadeId) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOder);
            Page<EmpreendedorRetornoDTO> empreendorRetornoDTOS = this.empreendedorService.buscarPorLocalidade(localidadeId, pageRequest);
            if (empreendorRetornoDTOS == null || empreendorRetornoDTOS.isEmpty()) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestPaginado(empreendorRetornoDTOS);
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<RestDataReturnDTO> buscarTodos(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nomeEmpreendedor") String colunaOder
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString("ASC"), colunaOder);
            Page<EmpreendedorRetornoDTO> empreendedores = empreendedorService.buscarTodos(pageRequest);
            if (empreendedores == null || empreendedores.isEmpty()) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequestPaginado(empreendedores);
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping("/buscar-por-conta/{contaId}")
    public ResponseEntity<RestDataReturnDTO> buscarEmpreendedorPorConta(@PathVariable Long contaId) {
        try {
            EmpreendedorRetornoDTO empreendedor = this.empreendedorService.buscarEmpreendedorPorConta(contaId);
            if (empreendedor == null) {
                return notFoundRequest(MessageUtils.getMessage("sem.nada.no.retorno"), null);
            }
            return okRequest(empreendedor);
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<RestDataReturnDTO> deletar(@PathVariable Long id) {
        try {
            this.empreendedorService.deletar(id);
            return okRequest("sucesso", 1, "sucesso");
        } catch (Exception e) {
            return super.badRequest("erro", null, e);
        }
    }

    @GetMapping("/empreendedores/{id}/anexo-bilhete")
    public ResponseEntity<byte[]> downloadAnexoBilhete(@PathVariable Long id) {

        Empreendedor empreendedor = empreendedorService.buscarModelPeloId(id);
        if (empreendedor == null){
            return ResponseEntity.notFound().build();
        }

        byte[] arquivo = empreendedor.getAnexoBilhete();
        String nomeArquivo = empreendedor.getNomeAnexoBilhete();
        String mimeType = empreendedor.getTipoMimeAnexoBilhete();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(arquivo);
    }

    @GetMapping("/empreendedores/{id}/anexo-comprovativo-nif")
    public ResponseEntity<byte[]> downloadAnexoNif(@PathVariable Long id) {

        Empreendedor empreendedor = empreendedorService.buscarModelPeloId(id);
        if (empreendedor == null){
            return ResponseEntity.notFound().build();
        }

        byte[] arquivo = empreendedor.getAnexoNif();
        String nomeArquivo = empreendedor.getNomeAnexoNif();
        String mimeType = empreendedor.getTipoMimeAnexoNif();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(arquivo);
    }

    @GetMapping("/empreendedores/{id}/anexo-cartao-municipe")
    public ResponseEntity<byte[]> downloadAnexoCartaoMunicipe(@PathVariable Long id) {

        Empreendedor empreendedor = empreendedorService.buscarModelPeloId(id);
        if (empreendedor == null){
            return ResponseEntity.notFound().build();
        }

        byte[] arquivo = empreendedor.getAnexoCartaoMunicipe();
        String nomeArquivo = empreendedor.getNomeAnexoCartaoMunicipe();
        String mimeType = empreendedor.getTipoMimeAnexoCartaoMunicipe();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(arquivo);
    }

    @GetMapping("/empreendedores/{id}/anexo-registro-criminal")
    public ResponseEntity<byte[]> downloadAnexoRegistroCriminal(@PathVariable Long id) {

        Empreendedor empreendedor = empreendedorService.buscarModelPeloId(id);
        if (empreendedor == null){
            return ResponseEntity.notFound().build();
        }

        byte[] arquivo = empreendedor.getAnexoRegistroCriminal();
        String nomeArquivo = empreendedor.getNomeAnexoRegistroCriminal();
        String mimeType = empreendedor.getTipoMimeAnexoRegistroCriminal();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(arquivo);
    }

}
