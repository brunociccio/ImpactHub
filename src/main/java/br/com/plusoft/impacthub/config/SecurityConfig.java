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
                .roles("USER") // Define a função USER para permissões limitadas
                .build();

        UserDetails admin = User.withUsername("adminImpacthub")
                .password(passwordEncoder().encode("impacthub"))
                .roles("ADMIN") // Define a função ADMIN para permissões completas
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita o CSRF para simplificar as requisições
            .authorizeHttpRequests(auth -> auth
                // Permite acesso apenas à página de login e recursos estáticos
                .requestMatchers("/custom-login", "/css/**", "/js/**", "/images/**").permitAll()
                // Permite acesso público aos endpoints de cadastro
                .requestMatchers("/CadastroCNPJ", "/Contato", "/Endereco", "/Documento", "/Login", "/Cadastro").permitAll()
                // Permite acesso ao endpoint /chatEsg apenas para usuários com as funções USER ou ADMIN
                .requestMatchers("/chatEsg").hasAnyRole("USER", "ADMIN")
                // Permite acesso a todos os endpoints para usuários com a função ADMIN
                .requestMatchers("/**").hasRole("ADMIN")
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
            .and()
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
