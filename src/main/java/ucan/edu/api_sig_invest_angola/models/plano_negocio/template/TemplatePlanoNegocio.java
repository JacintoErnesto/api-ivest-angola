package ucan.edu.api_sig_invest_angola.models.plano_negocio.template;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucan.edu.api_sig_invest_angola.models.area_atuacao.AreaAtuacao;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_template_plano_negocio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplatePlanoNegocio {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "O campo templatePlanoNegocio não deve estar vazio")
    @Column(name = "template_plano_negocio_json", columnDefinition = "TEXT")
    private String templatePlanoNegocioJson;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "area_atuacao_id", referencedColumnName = "id")
    private AreaAtuacao areaDeAtuacao;
    @Timestamp
    private LocalDateTime dataCadastro;
}
