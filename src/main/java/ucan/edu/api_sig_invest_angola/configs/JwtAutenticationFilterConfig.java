package ucan.edu.api_sig_invest_angola.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ucan.edu.api_sig_invest_angola.dtos.response.RestDataReturnDTO;
import ucan.edu.api_sig_invest_angola.enums.response.ResponseCode;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessTokenRejectException;
import ucan.edu.api_sig_invest_angola.services.auth.JwtService;
import ucan.edu.api_sig_invest_angola.services.serviceImpls.auth.JwtServiceImpl;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtAutenticationFilterConfig extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAutenticationFilterConfig.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String tokenJwt = authHeader.substring(7);

            if (tokenJwt.isBlank()) {
                throw new PortalBusinessTokenRejectException("Token JWT está vazio.");
            }

            String username = jwtService.extrairUsernameToken(tokenJwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (!jwtService.validarToken(tokenJwt, userDetails)) {
                    throw new PortalBusinessTokenRejectException(MessageUtils.getMessage("token.expirou"));
                }

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // ou use um logger para gravar
            System.err.println("Erro no filtro JWT: " + e.getMessage());

            if (!response.isCommitted()) {
                response.resetBuffer();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                ObjectMapper mapper = new ObjectMapper();
                RestDataReturnDTO rest = new RestDataReturnDTO(
                        null,
                        0,
                        ResponseCode.TOKEN_REJECT.getDescricao(),
                        MessageUtils.getMessage("token.expirou")
                );

                String json = mapper.writeValueAsString(rest);
                response.getWriter().write(json);
            }
        }
    }
}


