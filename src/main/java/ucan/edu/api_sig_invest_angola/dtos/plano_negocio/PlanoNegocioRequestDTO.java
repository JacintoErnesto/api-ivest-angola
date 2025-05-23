package ucan.edu.api_sig_invest_angola.dtos.plano_negocio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlanoNegocioRequestDTO(
        @NotBlank(message = "O campo ideia do negócio não deve estar vazio")
        @Size(min = 50, max = 300, message = "A ideia do negócio deve ter entre 10 e 300 caracteres.")
        String nomeIdeiaNegocio,
        @NotBlank(message = "O campo problema e cliente não deve estar vazio")
        @Size(min = 20, max = 500, message = "Descreva o problema e quem é o cliente com no mínimo 20 e no máximo 500 caracteres.")
        String problemaECliente,
        @NotBlank(message = "O campo modelo de receita não deve estar vazio")
        @Size(min = 10, max = 300, message = "O modelo de receita deve ter entre 10 e 300 caracteres.")
        String modeloDeReceita,
        @NotBlank(message = "O campo concorrência e diferenciais não deve estar vazio")
        @Size(min = 20, max = 400, message = "Descreva a concorrência e diferenciais em 20 a 400 caracteres.")
        String concorrenciaEDiferenciais,
        @NotBlank(message = "O campo estrutura, equipe ou tecnologia não deve estar vazio")
        @Size(min = 20, max = 400, message = "Explique brevemente a estrutura ou equipe com 20 a 400 caracteres.")
        String estruturaEquipeTecnologia,
        @NotBlank(message = "O campo investimento inicial não deve estar vazio")
        @Size(min = 10, max = 300, message = "Informe o valor e uso do investimento em 10 a 300 caracteres.")
        String investimentoInicial,
        @NotBlank(message = "O campo riscos ou desafios não deve estar vazio")
        @Size(min = 20, max = 400, message = "Descreva pelo menos 1 risco ou desafio com 20 a 400 caracteres.")
        String desafiosOuRiscos,
        @NotBlank(message = "O campo área de atuação não deve estar vazio")
        Long areaDeAtuacaoId,
        @NotBlank(message = "O campo empreendedor não deve estar vazio")
        Long empreendedorId,
        @NotBlank(message = "O campo localidade não deve estar vazio")
        String localidadeId
        ) {
}
