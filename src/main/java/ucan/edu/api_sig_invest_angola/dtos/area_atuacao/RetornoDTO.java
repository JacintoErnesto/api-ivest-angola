package ucan.edu.api_sig_invest_angola.dtos.area_atuacao;

public record RetornoDTO(int codigo, String mensagem) {
    public RetornoDTO(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
