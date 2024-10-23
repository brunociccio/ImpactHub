package br.com.plusoft.impacthub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("devbrunociccio")
                .password(passwordEncoder().encode("@Oliveira052020"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permite acesso livre à página de login e aos recursos estáticos (CSS, JS, etc.)
                .requestMatchers("/custom-login", "/css/**", "/js/**", "/images/**", "/docs/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Qualquer outra requisição precisa estar autenticada
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/custom-login") // Página de login customizada
                .loginProcessingUrl("/login") // Endpoint padrão de processamento de login
                .defaultSuccessUrl("/home", true)  // Redireciona para /home após login bem-sucedido
                .failureUrl("/custom-login?error=true") // Exibe erro ao falhar login
                .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/custom-login")  // Página de login customizada para OAuth2
                .defaultSuccessUrl("/home", true)  // Redireciona para /home após login via OAuth2 (GitHub)
            );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
