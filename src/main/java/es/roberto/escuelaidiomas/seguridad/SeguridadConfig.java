package es.roberto.escuelaidiomas.seguridad;

import ch.qos.logback.core.encoder.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/usuario/**"),
                                AntPathRequestMatcher.antMatcher("/webjars/**"),
                                AntPathRequestMatcher.antMatcher("/css/**"),
                                AntPathRequestMatcher.antMatcher("/image/**"),
                                PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/usuario/login")
                        .permitAll())
                .logout(out -> out
                        .logoutUrl("/usuario/logout")
                        .logoutSuccessUrl("/usuario/login?logout").permitAll());

        // Para que funcione la consola del h2
        http.csrf(csrf ->
                csrf.ignoringRequestMatchers(
                        AntPathRequestMatcher.antMatcher("/"),
                        AntPathRequestMatcher.antMatcher("/webjars/**"),
                        AntPathRequestMatcher.antMatcher("/css/**"),
                        AntPathRequestMatcher.antMatcher("/image/**"),
                        PathRequest.toH2Console()));

        // Para que funcione la consola del h2
        http.headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();

    }
}
