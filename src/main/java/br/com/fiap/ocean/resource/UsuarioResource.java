package br.com.fiap.ocean.resource;

import br.com.fiap.ocean.dto.request.AguaRequest;
import br.com.fiap.ocean.dto.request.UsuarioRequest;
import br.com.fiap.ocean.dto.request.PotencialRequest;
import br.com.fiap.ocean.dto.response.AguaResponse;
import br.com.fiap.ocean.dto.response.UsuarioResponse;
import br.com.fiap.ocean.dto.response.PotencialResponse;
import br.com.fiap.ocean.entity.Agua;
import br.com.fiap.ocean.entity.Credencial;
import br.com.fiap.ocean.entity.Usuario;
import br.com.fiap.ocean.entity.Potencial;
import br.com.fiap.ocean.repository.CredencialRepository;
import br.com.fiap.ocean.service.AguaService;
import br.com.fiap.ocean.service.UsuarioService;
import br.com.fiap.ocean.service.PotencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.time.LocalDate;

@Tag(name = "Usuario", description = "API para gerenciamento de usuários")
@RestController
@RequestMapping(value = "/cadastro")
public class UsuarioResource {

    @Autowired
    private UsuarioService service;

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private AguaService aguaService;

    @Autowired
    private PotencialService potencialService;

    @Operation(summary = "Retorna todos os usuários")
    @GetMapping
    public ResponseEntity<EntityModel<Page<UsuarioResponse>>> findAll(
            @Parameter(description = "Denominação do usuário") @RequestParam(name = "denominacao", required = false) String denominacao,
            @Parameter(description = "Data de nascimento do usuário") @RequestParam(name = "nascimento", required = false) LocalDate nascimento,
            @Parameter(description = "ID da credencial associada ao usuário") @RequestParam(name = "credencial.id", required = false) Long idCredencial,
            @Parameter(description = "E-mail da credencial associada ao usuário") @RequestParam(name = "credencial.email", required = false) String credencialEmail,
            Pageable pageable
    ) {
        Credencial credencial = null;
        if (idCredencial != null && idCredencial > 0) {
            credencial = credencialRepository.findById(idCredencial).orElse(null);
        } else if (credencialEmail != null && !credencialEmail.isEmpty()) {
            credencial = credencialRepository.findByEmail(credencialEmail);
        }

        Usuario cadastro = Usuario.builder()
                .credencial(credencial)
                .denominacao(denominacao)
                .nascimento(nascimento)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Usuario> example = Example.of(cadastro, matcher);

        Page<Usuario> encontrados = service.findAll(example, pageable);

        if (encontrados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Page<UsuarioResponse> resposta = encontrados.map(service::toResponse);

        EntityModel<Page<UsuarioResponse>> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findAll(denominacao, nascimento, idCredencial, credencialEmail, pageable)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Retorna um usuário por ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<UsuarioResponse>> findById(@PathVariable Long id) {
        Usuario encontrado = service.findById(id);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioResponse resposta = service.toResponse(encontrado);

        EntityModel<UsuarioResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findById(id)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Retorna um usuário por e-mail")
    @GetMapping("/email/{email}")
    public ResponseEntity<EntityModel<UsuarioResponse>> findByEmail(@PathVariable String email) {
        Credencial login = credencialRepository.findByEmail(email);
        if (login == null) {
            return ResponseEntity.notFound().build();
        }

        Usuario cadastro = service.findByLogin(login);
        if (cadastro == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioResponse resposta = service.toResponse(cadastro);

        EntityModel<UsuarioResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findByEmail(email)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Cria um novo usuário")
    @Transactional
    @PostMapping
    public ResponseEntity<EntityModel<UsuarioResponse>> save(@RequestBody @Valid UsuarioRequest r) {
        Usuario entity = service.toEntity(r);
        Usuario saved = service.save(entity);
        UsuarioResponse resposta = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        EntityModel<UsuarioResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(uri).withSelfRel());

        return ResponseEntity.created(uri).body(resource);
    }

    @Operation(summary = "Realiza login de usuário")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        Credencial login = credencialRepository.findByEmail(email);
        if (login == null || login.getSenha() == null || !login.getSenha().equals(senha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
        return ResponseEntity.ok().body("Login bem-sucedido para o usuário: " + login.getEmail());
    }

    @Operation(summary = "Retorna todas as águas")
    @GetMapping("/agua")
    public ResponseEntity<EntityModel<Page<AguaResponse>>> findAllAgua(Pageable pageable) {
        Page<Agua> encontrados = aguaService.findAll(pageable);
        if (encontrados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Page<AguaResponse> resposta = encontrados.map(aguaService::toResponse);

        EntityModel<Page<AguaResponse>> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findAllAgua(pageable)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Retorna uma água por ID")
    @GetMapping("/agua/{id}")
    public ResponseEntity<EntityModel<AguaResponse>> findAguaById(@PathVariable Long id) {
        Agua encontrado = aguaService.findById(id);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        AguaResponse resposta = aguaService.toResponse(encontrado);

        EntityModel<AguaResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findAguaById(id)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Cria uma nova água")
    @PostMapping("/agua")
    public ResponseEntity<EntityModel<AguaResponse>> saveAgua(@RequestBody @Valid AguaRequest r) {
        Agua entity = aguaService.toEntity(r);
        Agua saved = aguaService.save(entity);
        AguaResponse resposta = aguaService.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        EntityModel<AguaResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(uri).withSelfRel());

        return ResponseEntity.created(uri).body(resource);
    }

    @Operation(summary = "Atualiza uma água existente")
    @PutMapping("/agua/{id}")
    public ResponseEntity<EntityModel<AguaResponse>> updateAgua(@PathVariable Long id, @RequestBody @Valid AguaRequest request) {
        Agua existingAgua = aguaService.findById(id);
        if (existingAgua == null) {
            return ResponseEntity.notFound().build();
        }

        existingAgua.setCidade(request.cidade());
        existingAgua.setPh(request.ph());
        existingAgua.setOxigenio(request.oxigenio());
        existingAgua.setNitrato(request.nitrato());
        existingAgua.setFosfato(request.fosfato());
        existingAgua.setMicroplastico(request.microplastico());
        existingAgua.setQualidadeDaAgua(request.qualidadeDaAgua());

        Agua updatedAgua = aguaService.save(existingAgua);
        AguaResponse response = aguaService.toResponse(updatedAgua);

        EntityModel<AguaResponse> resource = EntityModel.of(response);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).updateAgua(id, request)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findAguaById(id)).withRel("find"));

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Exclui uma água por ID")
    @DeleteMapping("/agua/{id}")
    public ResponseEntity<Void> deleteAgua(@PathVariable Long id) {
        Agua existingAgua = aguaService.findById(id);
        if (existingAgua == null) {
            return ResponseEntity.notFound().build();
        }

        aguaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retorna todos os potenciais")
    @GetMapping("/potencial")
    public ResponseEntity<EntityModel<Page<PotencialResponse>>> findAllPotencial(Pageable pageable) {
        Page<Potencial> encontrados = potencialService.findAll(pageable);
        if (encontrados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Page<PotencialResponse> resposta = encontrados.map(potencialService::toResponse);

        EntityModel<Page<PotencialResponse>> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findAllPotencial(pageable)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Retorna um potencial por ID")
    @GetMapping("/potencial/{id}")
    public ResponseEntity<EntityModel<PotencialResponse>> findPotencialById(@PathVariable Long id) {
        Potencial encontrado = potencialService.findById(id);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        PotencialResponse resposta = potencialService.toResponse(encontrado);

        EntityModel<PotencialResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findPotencialById(id)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Cria um novo potencial")
    @PostMapping("/potencial")
    public ResponseEntity<EntityModel<PotencialResponse>> savePotencial(@RequestBody @Valid PotencialRequest r) {
        Potencial entity = potencialService.toEntity(r);
        Potencial saved = potencialService.save(entity);
        PotencialResponse resposta = potencialService.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        EntityModel<PotencialResponse> resource = EntityModel.of(resposta);
        resource.add(WebMvcLinkBuilder.linkTo(uri).withSelfRel());

        return ResponseEntity.created(uri).body(resource);
    }

    @Operation(summary = "Atualiza um potencial existente")
    @PutMapping("/potencial/{id}")
    public ResponseEntity<EntityModel<PotencialResponse>> updatePotencial(@PathVariable Long id, @RequestBody @Valid PotencialRequest request) {
        Potencial existingPotencial = potencialService.findById(id);
        if (existingPotencial == null) {
            return ResponseEntity.notFound().build();
        }

        existingPotencial.setCidade(request.cidade());
        existingPotencial.setEscala(request.escala());

        Potencial updatedPotencial = potencialService.save(existingPotencial);
        PotencialResponse response = potencialService.toResponse(updatedPotencial);

        EntityModel<PotencialResponse> resource = EntityModel.of(response);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).updatePotencial(id, request)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioResource.class).findPotencialById(id)).withRel("find"));

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Exclui um potencial por ID")
    @DeleteMapping("/potencial/{id}")
    public ResponseEntity<Void> deletePotencial(@PathVariable Long id) {
        Potencial existingPotencial = potencialService.findById(id);
        if (existingPotencial == null) {
            return ResponseEntity.notFound().build();
        }

        potencialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
