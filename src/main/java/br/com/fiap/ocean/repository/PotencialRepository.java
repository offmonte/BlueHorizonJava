package br.com.fiap.ocean.repository;

import br.com.fiap.ocean.entity.Potencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotencialRepository extends JpaRepository<Potencial, Long> {
}
