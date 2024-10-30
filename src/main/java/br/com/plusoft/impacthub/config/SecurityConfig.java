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
        UserDetails user = User.withUsername("impacthub")
                .password(passwordEncoder().encode("impacthub"))
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
            .csrf(csrf -> csrf.disable()) // Desabilita o CSRF
            .authorizeHttpRequests(auth -> auth
                // Permite acesso apenas à página de login e recursos estáticos
                .requestMatchers("/custom-login", "/css/**", "/js/**", "/images/**").permitAll()
                // Exige autenticação para qualquer outra requisição
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/custom-login") // Página de login customizada 
                .loginProcessingUrl("/login") // URL para processar o login via formulário
                .defaultSuccessUrl("/chatEsg", true)  // Redireciona para /chatEsg após login bem-sucedido
                .failureUrl("/custom-login?error=true") // Redireciona para a página de login com erro
                .permitAll() // Permite o acesso a qualquer pessoa à página de login
            )
            .httpBasic() // Permite autenticação básica para testes no Insomnia
            .and() // Conclui a configuração de autenticação básica e inicia o próximo bloco
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/custom-login")  // Página de login customizada para OAuth2
                .defaultSuccessUrl("/chatEsg", true)  // Redireciona para /chatEsg após login via OAuth2 (GitHub)
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
