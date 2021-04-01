package io.github.aquiles.agenda.model.repository;

import io.github.aquiles.agenda.model.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
