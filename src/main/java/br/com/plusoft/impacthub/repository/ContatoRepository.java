package br.com.plusoft.impacthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.plusoft.impacthub.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
   
}
