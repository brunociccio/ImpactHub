package br.com.plusoft.impacthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.plusoft.impacthub.model.DocumentosAmbientais;

@Repository
public interface DocumentosAmbientaisRepository extends JpaRepository<DocumentosAmbientais, Long> {

}
