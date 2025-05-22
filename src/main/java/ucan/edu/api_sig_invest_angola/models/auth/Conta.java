package ucan.edu.api_sig_invest_angola.models.auth;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;
import ucan.edu.api_sig_invest_angola.models.empreendedor.Empreendedor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_conta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conta  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    @NotNull(message = "Email não pode ser nulo")
    private String username;
    @Column(name = "password")
    @NotNull(message = "Password não pode ser nulo")
    private String password;
    @Column(name = "tipo_conta")
    @NotNull(message = "Tipo de conta não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @Column(name = "perfil_completo")
    private boolean perfilCompleto;

    @OneToOne(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Empreendedor empreendedor;

    @Column(name = "data_registo")
    private LocalDateTime dataRegisto;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipoConta.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
