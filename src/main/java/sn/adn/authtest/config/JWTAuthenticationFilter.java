package sn.adn.authtest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sn.adn.authtest.domaine.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        User user = null;
        try {
            user  = new User();
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        String jwt = Jwts.builder()
                .setSubject(u.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXTIMEPIRATION))
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.KEYS)
                .claim("role", u.getAuthorities())
                .compact();

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);
    }
}
