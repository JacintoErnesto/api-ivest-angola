package ucan.edu.api_sig_invest_angola.dtos.exception;

import lombok.Getter;

@Getter
public class ErroDeFormularioDto {
    private final String campo;
    private final String mensagemErro;
    public ErroDeFormularioDto(String campo, String mensagemErro) {
        this.campo = campo;
        this.mensagemErro = mensagemErro;
    }
}

