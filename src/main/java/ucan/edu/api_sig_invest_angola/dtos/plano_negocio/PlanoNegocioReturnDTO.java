package ucan.edu.api_sig_invest_angola.dtos.plano_negocio;

import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoReturnDTO;
import ucan.edu.api_sig_invest_angola.dtos.empreendedor.EmpreendedorRetornoDTO;

import java.time.LocalDateTime;

public record PlanoNegocioReturnDTO(
        Long id,
        String nomeIdeiaNegocio,
        String problemaECliente,
        String modeloDeReceita,
        String concorrenciaEDiferenciais,
        String estruturaEquipeTecnologia,
        String investimentoInicial,
        String desafiosOuRiscos,
        AreaAtuacaoReturnDTO areaDeAtuacao,
        EmpreendedorRetornoDTO empreendedorRetornoDTO,
        LocalDateTime dataRegistro
) {
}
