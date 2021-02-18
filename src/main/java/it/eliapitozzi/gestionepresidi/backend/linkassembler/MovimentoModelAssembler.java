package it.eliapitozzi.gestionepresidi.backend.linkassembler;

import it.eliapitozzi.gestionepresidi.backend.controller.MovimentoController;
import it.eliapitozzi.gestionepresidi.backend.model.Movimento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Elia
 */
@Component
public class MovimentoModelAssembler implements RepresentationModelAssembler<Movimento, EntityModel<Movimento>> {
    @Override
    public EntityModel<Movimento> toModel(Movimento movimento) {
        return EntityModel.of(movimento,
                linkTo(methodOn(MovimentoController.class).one(movimento.getId())).withSelfRel(),
                linkTo(methodOn(MovimentoController.class).all()).withRel("/api/movimento"),
                linkTo(methodOn(MovimentoController.class)
                        .allSpecifyingPazienteId(movimento.getPaziente().getId())).withRel("/api/movimento/paziente")
        );
    }

}
