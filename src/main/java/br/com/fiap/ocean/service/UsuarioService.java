package br.com.fiap.ocean.service;

import br.com.fiap.ocean.dto.request.UsuarioRequest;
import br.com.fiap.ocean.dto.response.UsuarioResponse;
import br.com.fiap.ocean.entity.Usuario;
import br.com.fiap.ocean.entity.Credencial;
import br.com.fiap.ocean.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private CredencialService credencialService;

    @Override
    public Usuario toEntity(UsuarioRequest r) {

        if (Objects.isNull(r)) return null;

        var credencial = credencialService.toEntity(r.credencial());

        return Usuario.builder()
                .credencial(credencial)
                .denominacao(r.denominacao())
                .nascimento(r.nascimento())
                .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {

        if (Objects.isNull(e)) return null;

        var credencial = credencialService.toResponse(e.getCredencial());

        return UsuarioResponse.builder()
                .id(e.getId())
                .denominacao(e.getDenominacao())
                .nascimento(e.getNascimento())
                .credencial(credencial)
                .build();
    }

    @Override
    public List<Usuario> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Usuario> findAll(Example<Usuario> example) {
        return repo.findAll(example);
    }

    public Page<Usuario> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Usuario> findAll(Example<Usuario> example, Pageable pageable) {
        return repo.findAll(example, pageable);
    }

    @Override
    public Usuario findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario e) {
        return repo.save(e);
    }

    public Usuario findByLogin(Credencial credencial) {
        return repo.findByCredencial(credencial);
    }
}
