package br.com.fiap.ocean.service;

import br.com.fiap.ocean.dto.request.AguaRequest;
import br.com.fiap.ocean.dto.response.AguaResponse;
import br.com.fiap.ocean.entity.Agua;
import br.com.fiap.ocean.repository.AguaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AguaService implements ServiceDTO<Agua, AguaRequest, AguaResponse> {

    @Autowired
    private AguaRepository repo;

    @Override
    public Agua toEntity(AguaRequest r) {
        return Agua.builder()
                .cidade(r.cidade())
                .ph(r.ph())
                .oxigenio(r.oxigenio())
                .nitrato(r.nitrato())
                .fosfato(r.fosfato())
                .microplastico(r.microplastico())
                .salinidade(r.salinidade())
                .qualidadeDaAgua(r.qualidadeDaAgua())
                .build();
    }

    @Override
    public AguaResponse toResponse(Agua e) {
        return new AguaResponse(
                e.getId(),
                e.getCidade(),
                e.getPh(),
                e.getOxigenio(),
                e.getNitrato(),
                e.getFosfato(),
                e.getMicroplastico(),
                e.getSalinidade(),
                e.getQualidadeDaAgua()
        );
    }

    @Override
    public List<Agua> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Agua> findAll(Example<Agua> example) {
        return repo.findAll(example);
    }

    public Page<Agua> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Agua> findAll(Example<Agua> example, Pageable pageable) {
        return repo.findAll(example, pageable);
    }

    @Override
    public Agua findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Agua save(Agua e) {
        return repo.save(e);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
