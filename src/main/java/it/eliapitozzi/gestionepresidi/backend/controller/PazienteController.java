package it.eliapitozzi.gestionepresidi.backend.controller;


import it.eliapitozzi.gestionepresidi.backend.exception.PazienteNotFoundException;
import it.eliapitozzi.gestionepresidi.backend.linkassembler.PazienteModelAssembler;
import it.eliapitozzi.gestionepresidi.backend.model.Paziente;
import it.eliapitozzi.gestionepresidi.backend.repository.PazienteRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Elia
 */
@RestController
public class PazienteController {

    private final PazienteRepository repository;
    private final PazienteModelAssembler assembler;

    public PazienteController(PazienteRepository repository, PazienteModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/api/paziente")
    public CollectionModel<EntityModel<Paziente>> all() {

        List<EntityModel<Paziente>> pazienti = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pazienti,
                    linkTo(methodOn(PazienteController.class).all()).withSelfRel()
                );

    }

    @PostMapping("/api/paziente")
    public ResponseEntity<?> newPaziente(@RequestBody Paziente newPaziente) {
        EntityModel<Paziente> entityModel = assembler.toModel(repository.save(newPaziente));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/api/paziente/{id}")
    public EntityModel<Paziente> one(@PathVariable Long id) {

        Paziente paziente = repository.findById(id)
                .orElseThrow(() -> new PazienteNotFoundException(id));

        return assembler.toModel(paziente);
    }

    @PutMapping("/api/paziente/{id}")
    public ResponseEntity<?> replacePaziente(@RequestBody Paziente newPaziente,
                                                @PathVariable Long id) {

        Paziente updatedPaziente = repository.findById(id)
                .map(paziente -> {
                    paziente.setNominativo(newPaziente.getNominativo());
                    paziente.setIndirizzo(newPaziente.getIndirizzo());
                    paziente.setLocalita(newPaziente.getLocalita());
                    paziente.setTelefono(newPaziente.getTelefono());
                    return repository.save(paziente);
                })
                .orElseGet(() -> {
                    newPaziente.setId(id);
                    return repository.save(newPaziente);
                });

        EntityModel<Paziente> entityModel = assembler.toModel(updatedPaziente);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/api/paziente/{id}")
    public ResponseEntity<?> deletePaziente(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
