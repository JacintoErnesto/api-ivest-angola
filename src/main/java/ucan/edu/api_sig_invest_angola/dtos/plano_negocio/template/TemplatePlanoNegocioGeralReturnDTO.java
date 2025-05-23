package ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplatePlanoNegocioGeralReturnDTO implements Serializable {
    private String resumo_executivo;
    private String descricao_da_empresa;
    private String produto_ou_servico;
    private AnaliseDeMercado analise_de_mercado;
    private String modelo_de_negocio;
    private EstrategiaDeMarketing estrategia_de_marketing;
    private PlanoOperacional plano_operacional;
    private PlanoFinanceiro plano_financeiro;
    private AnaliseDeRisco analise_de_risco;
    private String estrategia_de_crescimento;
    private String informacoes_adicionais;
    @Data
    public static class AnaliseDeMercado {
        private String segmento_de_cliente;
        private String tamanho_de_mercado;
        private String concorrencia;
        // Getters e setters
    }
    @Data
    public static class EstrategiaDeMarketing {
        private String precificacao;
        private String distribuicao;
        private String promocao;
        // Getters e setters
    }
    @Data
    public static class PlanoOperacional {
        private String processos_chave;
        private String infraestrutura;
        private String equipe;
        // Getters e setters
    }
    @Data
    public static class PlanoFinanceiro {
        private String investimento_inicial;
        private String projecoes;
        private String fonte_de_receita;
        // Getters e setters
    }
    @Data
    public static class AnaliseDeRisco {
        private String riscos_tecnicos;
        private String riscos_de_mercado;
        private String riscos_operacionais;
        // Getters e setters
    }
}
