package ucan.edu.api_sig_invest_angola.models.area_atuacao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_area_atuacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AreaAtuacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String designacao;
    @Timestamp
    LocalDateTime dataRegistro;
}
