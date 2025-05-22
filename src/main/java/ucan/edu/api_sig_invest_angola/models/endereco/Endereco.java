package ucan.edu.api_sig_invest_angola.models.endereco;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucan.edu.api_sig_invest_angola.models.localidade.Localidade;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo nome do bairro não deve estar vazio")
    private String nomeBairro;
    @NotBlank(message = "O campo nome da rua não deve estar vazio")
    private String nomeRua;
    private String numeroCasa;
    @ManyToOne(fetch = FetchType.LAZY)
    private Localidade localidade;
    @Timestamp
    private LocalDateTime dataRegsitro;
}
