package br.com.fiap.ocean.repository;

import br.com.fiap.ocean.entity.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredencialRepository extends JpaRepository<Credencial, Long> {
    Credencial findByEmail(String email);
}
