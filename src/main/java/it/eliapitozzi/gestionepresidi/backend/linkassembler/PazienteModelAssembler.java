package it.eliapitozzi.gestionepresidi.backend.linkassembler;

import it.eliapitozzi.gestionepresidi.backend.controller.PazienteController;
import it.eliapitozzi.gestionepresidi.backend.model.Paziente;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Elia
 */
@Component
public class PazienteModelAssembler implements
        RepresentationModelAssembler<Paziente, EntityModel<Paziente>> {

    @Override
    public EntityModel<Paziente> toModel(Paziente paziente) {
        return EntityModel.of(paziente,
                linkTo(methodOn(PazienteController.class).one(paziente.getId())).withSelfRel(),
                linkTo(methodOn(PazienteController.class).all()).withRel("/api/paziente")
        );
    }
}
