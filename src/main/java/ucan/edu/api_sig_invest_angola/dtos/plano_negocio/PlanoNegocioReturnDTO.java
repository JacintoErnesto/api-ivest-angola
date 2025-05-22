package ucan.edu.api_sig_invest_angola.dtos.plano_negocio;

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
        Long areaDeAtuacaoId,
        LocalDateTime dataRegistro
) {
}
