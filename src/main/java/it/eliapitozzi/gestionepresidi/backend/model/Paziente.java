package it.eliapitozzi.gestionepresidi.backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Elia
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Paziente {

    @Id
    @GeneratedValue
    private Long id;

    private String nominativo;
    private String indirizzo;
    private String localita;
    private String telefono;

    public Paziente() {
    }

    public Paziente(String nominativo, String indirizzo, String localita, String telefono) {
        this.nominativo = nominativo;
        this.indirizzo = indirizzo;
        this.localita = localita;
        this.telefono = telefono;
    }

}
