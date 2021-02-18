package it.eliapitozzi.gestionepresidi.frontend;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import it.eliapitozzi.gestionepresidi.backend.model.Paziente;
import it.eliapitozzi.gestionepresidi.backend.repository.PazienteRepository;

/**
 * @author Elia
 */
@Route()
public class MainView extends VerticalLayout {

    final Grid<Paziente> grid;
    private final PazienteRepository repository;

    public MainView(PazienteRepository repository) {
        this.repository = repository;
        this.grid = new Grid<>(Paziente.class);
        add(grid);
        listPazienti();

    }

    private void listPazienti() {
        grid.setItems(repository.findAll());
    }
}