package br.com.fiap.ocean.service;

import br.com.fiap.ocean.dto.request.CredencialRequest;
import br.com.fiap.ocean.dto.response.CredencialResponse;
import br.com.fiap.ocean.entity.Credencial;
import br.com.fiap.ocean.repository.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredencialService implements ServiceDTO<Credencial, CredencialRequest, CredencialResponse> {

    @Autowired
    private CredencialRepository repo;

    @Override
    public Credencial toEntity(CredencialRequest r) {
        return Credencial.builder()
                .email(r.email())
                .senha(r.senha())
                .build();
    }

    @Override
    public CredencialResponse toResponse(Credencial e) {
        return CredencialResponse.builder()
                .id(e.getId())
                .email(e.getEmail())
                .build();
    }

    @Override
    public List<Credencial> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Credencial> findAll(Example<Credencial> example) {
        return repo.findAll(example);
    }

    public Page<Credencial> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Credencial> findAll(Example<Credencial> example, Pageable pageable) {
        return repo.findAll(example, pageable);
    }

    @Override
    public Credencial findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Credencial save(Credencial e) {
        return repo.save(e);
    }
}
