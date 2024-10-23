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
            .csrf(csrf -> csrf.disable()) // Desabilita o CSRF por enquanto
            .authorizeHttpRequests(auth -> auth
                // Permite acesso à página de login e aos recursos estáticos (CSS, JS, etc.)
                .requestMatchers("/custom-login", "/oauth2/authorization/github", "/css/**", "/js/**", "/images/**").permitAll()
                // Exige autenticação para qualquer outra requisição
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/custom-login") // Página de login customizada que desenvolvemos
                .loginProcessingUrl("/login") // URL para processar o login via formulário
                .defaultSuccessUrl("/home", true)  // Redireciona para /home após login bem-sucedido
                .failureUrl("/custom-login?error=true") // Redireciona para a página de login com erro
                .permitAll() // Permite o acesso a qualquer pessoa à página de login
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/custom-login")  // Página de login customizada para OAuth2
                .defaultSuccessUrl("/home", true)  // Redireciona para /home após login via OAuth2 (GitHub)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // URL para logout
                .logoutSuccessUrl("/custom-login?logout=true") // Redireciona para página de login após logout
                .permitAll()
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

