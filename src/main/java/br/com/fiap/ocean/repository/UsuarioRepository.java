package br.com.fiap.ocean.repository;

import br.com.fiap.ocean.entity.Usuario;
import br.com.fiap.ocean.entity.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCredencial(Credencial credencial);
}
