package ma.cinefx.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seance_id")
    private Seance seance;

    private String clientNom;
    private int nbPlaces;

    public Reservation() {}

    public Reservation(Seance seance, String clientNom, int nbPlaces) {
        this.seance = seance;
        this.clientNom = clientNom;
        this.nbPlaces = nbPlaces;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }
    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    public int getNbPlaces() { return nbPlaces; }
    public void setNbPlaces(int nbPlaces) { this.nbPlaces = nbPlaces; }
}
