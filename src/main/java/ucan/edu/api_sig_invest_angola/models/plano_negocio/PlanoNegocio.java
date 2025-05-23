package ucan.edu.api_sig_invest_angola.models.plano_negocio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoReturnDTO;
import ucan.edu.api_sig_invest_angola.models.empreendedor.Empreendedor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_plano_negocio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanoNegocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo nomeIdeia negócio não pode estar vazio")
    private String nomeIdeiaNegocio;
    @NotBlank(message = "O campo programa e cliente não pode estar vazio")
    private String problemaECliente;
    @NotBlank(message = "O campo modelo de receita não pode estar vazio")
    private String modeloDeReceita;
    @NotBlank(message = "O campo concorrecnia e diferencias não pode estar vazio")
    private String concorrenciaEDiferenciais;
    @NotBlank(message = "O campo Estrtura ou equipa tecnológica não pode estar vazio")
    private String estruturaEquipeTecnologia;
    @NotBlank(message = "O campo investimento inicial não pode estar vazio")
    private String investimentoInicial;
    @NotBlank(message = "O campo desafios ou riscos não pode estar vazio")
    private String desafiosOuRiscos;
    @NotNull(message = "O campo Area de atuação não pode estar vazio")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "area_atuacao_id", referencedColumnName = "id")
    private AreaAtuacaoReturnDTO areaDeAtuacao;
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "empreendedor_id", referencedColumnName = "id")
    private Empreendedor empreendedor;
    private LocalDateTime dataRegistro;
    
}
