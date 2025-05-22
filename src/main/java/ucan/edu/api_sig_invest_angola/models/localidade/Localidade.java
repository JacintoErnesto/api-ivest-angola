package ucan.edu.api_sig_invest_angola.models.localidade;

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
@Table(name = "tb_localidade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Localidade {
    @Id
    private String id;
    @NotBlank
    private String designacao;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "localidade_pai_id")
    private Localidade localidade;
    @Timestamp
    @NotNull
    private LocalDateTime dataRegisto;
}
