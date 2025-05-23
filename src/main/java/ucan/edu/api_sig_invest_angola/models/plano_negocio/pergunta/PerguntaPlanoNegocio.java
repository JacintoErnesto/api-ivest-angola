package ucan.edu.api_sig_invest_angola.models.plano_negocio.pergunta;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_pergunta_plano_negocio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerguntaPlanoNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo pergunta não pode ser vazio")
    private String pergunta;

    public PerguntaPlanoNegocio(String pergunta, LocalDateTime dataCadastro) {
        this.pergunta = pergunta;
        this.dataCadastro = dataCadastro;
    }

    @NotNull
    @Timestamp
    private LocalDateTime dataCadastro;

}
