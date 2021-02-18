package it.eliapitozzi.gestionepresidi.backend.controller;

import it.eliapitozzi.gestionepresidi.backend.exception.MovimentoNotFoundException;
import it.eliapitozzi.gestionepresidi.backend.exception.PazienteNotFoundException;
import it.eliapitozzi.gestionepresidi.backend.linkassembler.MovimentoModelAssembler;
import it.eliapitozzi.gestionepresidi.backend.model.Movimento;
import it.eliapitozzi.gestionepresidi.backend.model.Paziente;
import it.eliapitozzi.gestionepresidi.backend.repository.MovimentoRepository;
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
public class MovimentoController {

    private final MovimentoRepository movimentoRepository;
    private final PazienteRepository pazienteRepository;

    private final MovimentoModelAssembler assembler;

    public MovimentoController(MovimentoRepository movimentoRepository, PazienteRepository pazienteRepository, MovimentoModelAssembler assembler) {
        this.movimentoRepository = movimentoRepository;
        this.pazienteRepository = pazienteRepository;
        this.assembler = assembler;
    }

    @GetMapping("/api/movimento")
    public CollectionModel<EntityModel<Movimento>> all() {

        List<EntityModel<Movimento>> movimenti = movimentoRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(movimenti,
                linkTo(methodOn(MovimentoController.class).all()).withSelfRel());
    }

    @GetMapping("/api/movimento/{id}")
    public EntityModel<Movimento> one(@PathVariable Long id) {

        Movimento movimento = movimentoRepository.findById(id)
                .orElseThrow(() -> new MovimentoNotFoundException(id));

        return assembler.toModel(movimento);
    }

    @GetMapping("api/movimento/paziente/{id}")
    public CollectionModel<EntityModel<Movimento>> allSpecifyingPazienteId(@PathVariable Long id) {

        Paziente paziente = pazienteRepository.findById(id).orElseThrow(() -> new PazienteNotFoundException(id));

        List<EntityModel<Movimento>> movimenti = movimentoRepository.findAllByPaziente(paziente).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        //
        // System.out.println(movimenti.size());
        return CollectionModel.of(movimenti,
                linkTo(methodOn(MovimentoController.class).allSpecifyingPazienteId(id)).withSelfRel(),
                linkTo(methodOn(MovimentoController.class).all()).withRel("/api/movimento")
            );

    }

    @PostMapping("/api/movimento")
    public ResponseEntity<EntityModel<Movimento>> newMovimento(@RequestBody Movimento newMovimento) {

        EntityModel<Movimento> entityModel = assembler.toModel(movimentoRepository.save(newMovimento));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/api/movimento/paziente/{id}")
    public ResponseEntity<EntityModel<Movimento>> newMovimentoSpecifyingPazienteId(
                                                        @RequestBody Movimento newMovimento, @PathVariable Long id) {

        Paziente paziente = pazienteRepository.findById(id).orElseThrow(() -> new PazienteNotFoundException(id));
        newMovimento.setPaziente(paziente);

        return newMovimento(newMovimento);
    }

    @PutMapping("/api/movimento/{id}")
    public ResponseEntity<?> replaceMovimento(@RequestBody Movimento newMovimento,
                                              @PathVariable Long id) {

        Movimento updatedMovimento = movimentoRepository.findById(id)
                .map(movimento -> {
                    movimento.setNumeroBollaConsegna(newMovimento.getNumeroBollaConsegna());
                    movimento.setNumeroBollaRitiro(newMovimento.getNumeroBollaRitiro());
                    movimento.setDataConsegna(newMovimento.getDataConsegna());
                    movimento.setDataRitiro(newMovimento.getDataRitiro());
                    movimento.setNomeMateriale(newMovimento.getNomeMateriale());
                    movimento.setPaziente(newMovimento.getPaziente());

                    return movimentoRepository.save(movimento);
                })
                .orElseGet(() -> {
                    newMovimento.setId(id);
                    return movimentoRepository.save(newMovimento);
                });

        EntityModel<Movimento> entityModel = assembler.toModel(updatedMovimento);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/api/movimento/{id}")
    public ResponseEntity<?> deleteMovimento(@PathVariable Long id) {
        movimentoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
