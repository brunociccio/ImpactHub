package br.com.plusoft.impacthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.plusoft.impacthub.model.Cadastro;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
    // Métodos personalizados de consulta podem ser adicionados aqui, se necessário
}
