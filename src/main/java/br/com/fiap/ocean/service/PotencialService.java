package br.com.fiap.ocean.service;

import br.com.fiap.ocean.dto.request.PotencialRequest;
import br.com.fiap.ocean.dto.response.PotencialResponse;
import br.com.fiap.ocean.entity.Potencial;
import br.com.fiap.ocean.repository.PotencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotencialService implements ServiceDTO<Potencial, PotencialRequest, PotencialResponse> {

    @Autowired
    private PotencialRepository repo;

    @Override
    public Potencial toEntity(PotencialRequest r) {
        return Potencial.builder()
                .cidade(r.cidade())
                .escala(r.escala())
                .data(r.data())
                .build();
    }

    @Override
    public PotencialResponse toResponse(Potencial e) {
        return new PotencialResponse(
                e.getId(),
                e.getCidade(),
                e.getEscala(),
                e.getData()
        );
    }

    @Override
    public List<Potencial> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Potencial> findAll(Example<Potencial> example) {
        return repo.findAll(example);
    }

    public Page<Potencial> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Potencial> findAll(Example<Potencial> example, Pageable pageable) {
        return repo.findAll(example, pageable);
    }

    @Override
    public Potencial findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Potencial save(Potencial e) {
        return repo.save(e);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
