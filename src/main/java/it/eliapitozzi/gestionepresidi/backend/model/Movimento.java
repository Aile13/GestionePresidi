package it.eliapitozzi.gestionepresidi.backend.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Elia
 */
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Movimento {

    @Id
    @GeneratedValue
    private Long id;

    private String numeroBollaConsegna;
    private String numeroBollaRitiro;

    private Date dataConsegna;
    private Date dataRitiro;

    private Materiale nomeMateriale;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Paziente paziente;

    public Movimento() {
    }

    public Movimento(String numeroBollaConsegna, String numeroBollaRitiro, Date dataConsegna, Date dataRitiro, Materiale nomeMateriale, Paziente paziente) {
        this.numeroBollaConsegna = numeroBollaConsegna;
        this.numeroBollaRitiro = numeroBollaRitiro;
        this.dataConsegna = dataConsegna;
        this.dataRitiro = dataRitiro;
        this.nomeMateriale = nomeMateriale;
        this.paziente = paziente;
    }
}
