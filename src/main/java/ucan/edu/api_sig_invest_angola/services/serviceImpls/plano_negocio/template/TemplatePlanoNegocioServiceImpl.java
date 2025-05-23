package ucan.edu.api_sig_invest_angola.services.serviceImpls.plano_negocio.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.core.util.Json;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplatePlanoNegocioGeralReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplatePlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplateNegocioReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.plano_negocio.template.TemplatePlanoNegocio;
import ucan.edu.api_sig_invest_angola.repositories.plano_negocio.template.TemplatePlanoNegocioRepository;
import ucan.edu.api_sig_invest_angola.services.area_atuacao.AreaAtuacaoService;
import ucan.edu.api_sig_invest_angola.services.plano_negocio.template.TemplatePlanoNegocioService;
import ucan.edu.api_sig_invest_angola.utils.UtilsJackson;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TemplatePlanoNegocioServiceImpl implements TemplatePlanoNegocioService {
    private final TemplatePlanoNegocioRepository templatePlanoNegocioRepository;
    private final AreaAtuacaoService areaAtuacaoService;

    @Override
    @Transactional
    public TemplateNegocioReturnDTO criarTemplatePlanoNegocio(TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO) {
        try {
            validarCamposObrigatorios(templatePlanoNegocioRequestDTO);
            TemplatePlanoNegocio templatePlanoNegocio = new TemplatePlanoNegocio();
            templatePlanoNegocio.setTemplatePlanoNegocioJson(convertStringJsonParaJson(templatePlanoNegocioRequestDTO.templatePlanoNegocioJson()));
            templatePlanoNegocio.setAreaDeAtuacao(this.areaAtuacaoService.buscarModelPorId(templatePlanoNegocioRequestDTO.areaAtuacaoId()));
            templatePlanoNegocio.setDataCadastro(LocalDateTime.now());
            validarSejaExisteTemplateIgualSalvoPorAreaAtuacao(templatePlanoNegocio.getAreaDeAtuacao().getId(),
                    templatePlanoNegocio.getTemplatePlanoNegocioJson());
            this.templatePlanoNegocioRepository.save(templatePlanoNegocio);
            return converterParaDTO(templatePlanoNegocio);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }

    }


    @Override
    public Page<TemplateNegocioReturnDTO> listarTemplatePlanoNegocio(PageRequest pageRequest) {
        Page<TemplatePlanoNegocio> templatePlanoNegocioPage = this.templatePlanoNegocioRepository.findAll(pageRequest);
        if (!templatePlanoNegocioPage.isEmpty())
            return templatePlanoNegocioPage.map(this::converterParaDTO);
        return null;
    }

    @Override
    public TemplateNegocioReturnDTO buscarTemplatePlanoNegocioPorId(Long id) {
        TemplatePlanoNegocio templatePlanoNegocio = this.templatePlanoNegocioRepository.findById(id).orElse(null);
        return converterParaDTO(templatePlanoNegocio);
    }

    @Override
    public TemplateNegocioReturnDTO atualizarTemplatePlanoNegocio(Long id, TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO) {
        return null;
    }

    @Override
    public void deletarTemplatePlanoNegocio(Long id) {
        TemplatePlanoNegocio templatePlanoNegocio = this.templatePlanoNegocioRepository.findById(id).orElseThrow(() -> new PortalBusinessException("Template de plano de negócio não encontrado."));
        this.templatePlanoNegocioRepository.delete(templatePlanoNegocio);

    }

    @Override
    public Page<TemplateNegocioReturnDTO> listarTemplatePlanoNegocioPorAreaAtuacao(Long areaAtuacaoId, PageRequest pageRequest) {
        Page<TemplatePlanoNegocio> templatePlanoNegocioPage = this.templatePlanoNegocioRepository.findAllByAreaDeAtuacaoId(areaAtuacaoId, pageRequest);
        if (!templatePlanoNegocioPage.isEmpty())
            return templatePlanoNegocioPage.map(this::converterParaDTO);
        return null;
    }

    private void validarCamposObrigatorios(TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO) {
        if (templatePlanoNegocioRequestDTO.areaAtuacaoId() == null) {
            throw new PortalBusinessException("O campo areaAtuacaoId é obrigatório.");
        }
        if (templatePlanoNegocioRequestDTO.templatePlanoNegocioJson() == null || templatePlanoNegocioRequestDTO.templatePlanoNegocioJson().isBlank()) {
            throw new PortalBusinessException("O campo templateJson é obrigatório.");
        }
    }

    private String convertStringJsonParaJson(String json) {
        try {
            JsonNode jsonNode = Json.mapper().readTree(json);
            if (jsonNode == null || !jsonNode.isObject()) {
                throw new PortalBusinessException("O campo templateJson deve ser um objeto JSON válido.");
            }
            return jsonNode.toString();
        } catch (Exception e) {
            throw new PortalBusinessException("O campo templateJson deve ser um objeto JSON válido.");
        }
    }

    public TemplateNegocioReturnDTO converterParaDTO(TemplatePlanoNegocio templatePlanoNegocio) {
        if (templatePlanoNegocio == null) {
            return null;
        }
        try {
            TemplatePlanoNegocioGeralReturnDTO planoJsonConvertido =
                    UtilsJackson.jsonToObject(
                            templatePlanoNegocio.getTemplatePlanoNegocioJson(),
                            TemplatePlanoNegocioGeralReturnDTO.class
                    );

            return new TemplateNegocioReturnDTO(
                    templatePlanoNegocio.getId(),
                    planoJsonConvertido,
                    areaAtuacaoService.mapearAreaAtuacaoDTO(templatePlanoNegocio.getAreaDeAtuacao()),
                    templatePlanoNegocio.getDataCadastro()
            );

        } catch (JsonProcessingException e) {
            throw new PortalBusinessException("Erro ao converter JSON do plano de negócio para objeto DTO.", e);
        }
    }

    private void validarSejaExisteTemplateIgualSalvoPorAreaAtuacao(Long areAtuacaoId, String json) {
        List<TemplatePlanoNegocio> listaTemplatePlanoNegocio = this.templatePlanoNegocioRepository.findAllByAreaDeAtuacaoId(areAtuacaoId);
        if (listaTemplatePlanoNegocio != null && !listaTemplatePlanoNegocio.isEmpty()) {
            for (TemplatePlanoNegocio templatePlanoNegocio : listaTemplatePlanoNegocio) {
                if (templatePlanoNegocio.getTemplatePlanoNegocioJson().equals(json)) {
                    throw new PortalBusinessException("Já existe um template de plano de negócio igual salvo para esta área de atuação.");
                }
            }
        }
    }
}
