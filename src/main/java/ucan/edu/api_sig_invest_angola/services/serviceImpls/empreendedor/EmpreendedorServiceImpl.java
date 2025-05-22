package ucan.edu.api_sig_invest_angola.services.serviceImpls.empreendedor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRetornoDTO;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;
import ucan.edu.api_sig_invest_angola.models.empreendedor.Empreendedor;
import ucan.edu.api_sig_invest_angola.models.endereco.Endereco;
import ucan.edu.api_sig_invest_angola.repositories.auth.AuthRepository;
import ucan.edu.api_sig_invest_angola.repositories.empreendedor.EmpreendedorRepository;
import ucan.edu.api_sig_invest_angola.services.empreendedor.EmpreendedorService;
import ucan.edu.api_sig_invest_angola.services.endereco.EnderecoService;
import ucan.edu.api_sig_invest_angola.utils.UtilsJackson;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmpreendedorServiceImpl implements EmpreendedorService {
    private final EmpreendedorRepository empreendedorRepository;
    private final EnderecoService enderecoService;
    private final AuthRepository authRepository;

    @Override
    @Transactional
    public Empreendedor create(EmpreendedorRequestDTO empreendedorResquetDTO, Conta conta) {
        try {
            validarCamposObigatorios(empreendedorResquetDTO.getNomeEmpreendedor());
            validarCamposObigatorios(empreendedorResquetDTO.getNifEmpreendedor());
            Empreendedor empreendedor = new Empreendedor();
            empreendedor.setNomeEmpreendedor(empreendedorResquetDTO.getNomeEmpreendedor());
            empreendedor.setNifEmpreendedor(empreendedorResquetDTO.getNifEmpreendedor());
            empreendedor.setConta(conta);
            empreendedor.setDataRegistro(LocalDateTime.now());
            return this.empreendedorRepository.save(empreendedor);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }

    }

    @Override
    public Page<EmpreendedorRetornoDTO> buscarTodos(PageRequest pageRequest) {
        Page<Empreendedor> empreendedores = this.empreendedorRepository.findAll(pageRequest);
        if (!empreendedores.isEmpty()) {
            return empreendedores.map(this::mapearParaEmpreendeorRetornoDTO);
        }
        return null;
    }

    @Override
    @Transactional
    public EmpreendedorRetornoDTO atualizar(Long id, String empreendedorJson,
                                            MultipartFile anexoBilhete, MultipartFile anexoNif,
                                            MultipartFile anexoCartaoMunicipe, MultipartFile anexoRegistroCriminal) {
        try {
            //EmpreendedorRequestDTO empreendedorRequestDTO = UtilsJackson.jsonToObject(empreendedorJson, EmpreendedorRequestDTO.class);
            EmpreendedorRequestDTO empreendedorRequestDTO = validarJson(empreendedorJson);
            Endereco endereco = new Endereco();
            EnderecoReturnDTO enderecoReturnDTO = null;
            Empreendedor empreendedor = this.empreendedorRepository.findById(id).orElseThrow(() -> new PortalBusinessException("Empreendedor não encontrado"));
            if (isPreenchido(empreendedorRequestDTO.getNomeEmpreendedor()))
                empreendedor.setNomeEmpreendedor(empreendedorRequestDTO.getNomeEmpreendedor());
            if (isPreenchido(empreendedorRequestDTO.getNifEmpreendedor()))
                empreendedor.setNifEmpreendedor(empreendedorRequestDTO.getNifEmpreendedor());
            validarCamposObrigatorio(empreendedorRequestDTO.getTelefoneEmpreendedor());
            validarCamposObrigatorio(empreendedorRequestDTO.getEmailEmpreendedor());
            validarAnexoObtigatorio(anexoBilhete);
            validarAnexoObtigatorio(anexoNif);
            validarAnexoObtigatorio(anexoCartaoMunicipe);
            validarAnexoObtigatorio(anexoRegistroCriminal);
            validarSeAnexosSaoPDF(anexoBilhete, anexoNif,anexoCartaoMunicipe,anexoRegistroCriminal);
            empreendedor.setTelefoneEmpreendedor(empreendedorRequestDTO.getTelefoneEmpreendedor());
            empreendedor.setEmailEmpreendedor(empreendedorRequestDTO.getEmailEmpreendedor());
            enderecoReturnDTO = enderecoService.create(empreendedorRequestDTO.getEndereco());
            empreendedor.setEndereco(enderecoService.mapParaEndereco(enderecoReturnDTO));
            salvarAnexosNoEmpreendedor(empreendedor, anexoBilhete, anexoNif,anexoCartaoMunicipe,anexoRegistroCriminal);
            this.empreendedorRepository.save(empreendedor);
            mudarEstadoContaParaCompleto(empreendedor.getConta().getId());
            return null;
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmpreendedorRetornoDTO buscarPorId(Long id) {
        Empreendedor empreendedor = this.empreendedorRepository.findById(id).orElse(null);
        return mapearParaEmpreendeorRetornoDTO(empreendedor);
    }

    @Override
    public Page<EmpreendedorRetornoDTO> buscarPorLocalidade(String LocalidadeId, PageRequest pageRequest) {
        Page<Empreendedor> empreendedores = this.empreendedorRepository
                .findAllByEnderecoLocalidadeIdOrderByNomeEmpreendedor
                        (LocalidadeId, pageRequest);
        if (!empreendedores.isEmpty())
            return empreendedores.map(this::mapearParaEmpreendeorRetornoDTO);
        return null;
    }

    @Override
    public EmpreendedorRetornoDTO buscarEmpreendedorPorConta(Long contaId) {
        Empreendedor empreendedor = this.empreendedorRepository.findByContaId(contaId).orElse(null);
        return mapearParaEmpreendeorRetornoDTO(empreendedor);
    }

    @Override
    public void deletar(Long EmpreendedorId) {
        Empreendedor empreendedor = this.empreendedorRepository.findById(EmpreendedorId).orElseThrow(() -> new PortalBusinessException(MessageUtils.getMessage("sem.nada.no.retorno")));
        this.empreendedorRepository.delete(empreendedor);

    }

    @Override
    public Empreendedor buscarModelPeloId(Long id) {
        return this.empreendedorRepository.findById(id).orElse(null);
    }

    private void validarCamposObigatorios(String campo) {
        if (campo == null || campo.isEmpty())
            throw new PortalBusinessException("O campo " + campo + " é obrigatório");
    }

    private EmpreendedorRetornoDTO mapearParaEmpreendeorRetornoDTO(Empreendedor empreendedor) {
        if (empreendedor == null) {
            return null;
        }
        return new EmpreendedorRetornoDTO(
                empreendedor.getId(),
                empreendedor.getConta().getId(),
                empreendedor.getNomeEmpreendedor(),
                empreendedor.getNifEmpreendedor(),
                empreendedor.getTelefoneEmpreendedor(),
                empreendedor.getEmailEmpreendedor(),
                enderecoService.mapParaEnderecoDTO(empreendedor.getEndereco()),
                empreendedor.getNomeAnexoBilhete(),
                empreendedor.getNomeAnexoNif(),
                empreendedor.getNomeAnexoCartaoMunicipe(),
                empreendedor.getNomeAnexoRegistroCriminal(),
                empreendedor.getDataRegistro()
        );
    }

    private boolean isPreenchido(String atributo) {
        return atributo != null && !atributo.isBlank();
    }

    private void validarCamposObrigatorio(String atributo) {
        if (atributo == null || atributo.isBlank()) {
            throw new PortalBusinessException("Campo " + atributo + " obrigatório não preenchido");
        }
    }

    private void validarAnexoObtigatorio(MultipartFile anexo) {
        if (anexo == null || anexo.getSize() == 0) {
            throw new PortalBusinessException("Anexo obrigatório não preenchido");
        }
    }

    private void validarObrigatorio(Long atributo) {
        if (atributo == null) {
            throw new PortalBusinessException("O campo " + atributo + " obrigatório não preenchido");
        }
    }


    private void validarSeAnexosSaoPDF(MultipartFile anexoBilhete, MultipartFile anexoNif,
                                       MultipartFile anexoCartaoMunicipe, MultipartFile anexoRegistroCriminal) {
        validarAnexoPDF(anexoBilhete, "Bilhete");
        validarAnexoPDF(anexoNif, "NIF");
        validarAnexoPDF(anexoCartaoMunicipe, "Cartão de Munícipe");
        validarAnexoPDF(anexoRegistroCriminal, "Registro Criminal");
    }

    private void validarAnexoPDF(MultipartFile file, String nomeCampo) {
        if (file == null || file.isEmpty()) {
            throw new PortalBusinessException("O anexo " + nomeCampo + " é obrigatório.");
        }

        String mimeType = file.getContentType();
        String nomeArquivo = file.getOriginalFilename();

        if (mimeType == null || !mimeType.equalsIgnoreCase("application/pdf")) {
            throw new PortalBusinessException("O anexo " + nomeCampo + " deve ser um arquivo PDF (tipo MIME inválido).");
        }

        if (nomeArquivo == null || !nomeArquivo.toLowerCase().endsWith(".pdf")) {
            throw new PortalBusinessException("O anexo " + nomeCampo + " deve ter a extensão .pdf.");
        }
    }


    private void salvarAnexosNoEmpreendedor(Empreendedor empreendedor, MultipartFile anexoBilhete, MultipartFile anexoNif,
                                            MultipartFile anexoCartaoMunicipe, MultipartFile anexoRegistroCriminal ) throws IOException {
        if (anexoBilhete != null && !anexoBilhete.isEmpty()) {
            empreendedor.setAnexoBilhete(anexoBilhete.getBytes());
            empreendedor.setNomeAnexoBilhete(limparNomeArquivo(anexoBilhete.getOriginalFilename()));
            empreendedor.setTipoMimeAnexoBilhete(anexoBilhete.getContentType());
        }

        if (anexoNif != null && !anexoNif.isEmpty()) {
            empreendedor.setAnexoNif(anexoNif.getBytes());
            empreendedor.setNomeAnexoNif(limparNomeArquivo(anexoNif.getOriginalFilename()));
            empreendedor.setTipoMimeAnexoNif(anexoNif.getContentType());
        }

        if (anexoCartaoMunicipe != null && !anexoCartaoMunicipe.isEmpty()) {
            empreendedor.setAnexoCartaoMunicipe(anexoCartaoMunicipe.getBytes());
            empreendedor.setNomeAnexoCartaoMunicipe(limparNomeArquivo(anexoCartaoMunicipe.getOriginalFilename()));
            empreendedor.setTipoMimeAnexoCartaoMunicipe(anexoCartaoMunicipe.getContentType());
        }

        if (anexoRegistroCriminal != null && !anexoRegistroCriminal.isEmpty()) {
            empreendedor.setAnexoRegistroCriminal(anexoRegistroCriminal.getBytes());
            empreendedor.setNomeAnexoRegistroCriminal(limparNomeArquivo(anexoRegistroCriminal.getOriginalFilename()));
            empreendedor.setTipoMimeAnexoRegistroCriminal(anexoRegistroCriminal.getContentType());
        }
    }

    private String limparNomeArquivo(String nomeOriginal) {
        if (nomeOriginal == null) {
            return null;
        }
        nomeOriginal = Paths.get(nomeOriginal).getFileName().toString();
        return nomeOriginal.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private void mudarEstadoContaParaCompleto(Long contaId) {
        Conta conta = this.authRepository.findById(contaId).orElseThrow(() -> new PortalBusinessException("Conta não encontrada"));
        conta.setPerfilCompleto(true);
        this.authRepository.save(conta);
    }
    private EmpreendedorRequestDTO validarJson(String empreendedorJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(empreendedorJson);

            // Validação de campos obrigatórios no nível raiz
            if (!rootNode.hasNonNull("telefoneEmpreendedor") ||
                    !rootNode.hasNonNull("emailEmpreendedor") ||
                    !rootNode.hasNonNull("nomeEmpreendedor") ||
                    !rootNode.hasNonNull("endereco")) {
                throw new PortalBusinessException("JSON inválido: campos obrigatórios ausentes no nível principal.");
            }

            // Validação dos campos obrigatórios dentro de "endereco"
            JsonNode enderecoNode = rootNode.get("endereco");
            if (!enderecoNode.hasNonNull("nomeBairro") ||
                    !enderecoNode.hasNonNull("nomeRua") ||
                    !enderecoNode.hasNonNull("numeroCasa") ||
                    !enderecoNode.hasNonNull("localidade")) {
                throw new PortalBusinessException("JSON inválido: campos obrigatórios ausentes no objeto 'endereco'.");
            }

            // Conversão segura para DTO após validação
            return mapper.treeToValue(rootNode, EmpreendedorRequestDTO.class);

        } catch (JsonProcessingException e) {
            throw new PortalBusinessException("JSON inválido ou mal formado: " + e.getOriginalMessage());
        }
    }


}
