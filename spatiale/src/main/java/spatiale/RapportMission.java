package spatiale;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rapportMission")
public class RapportMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Integer et pas int pour avoir null au lieu de 0 par défaut

    @Column(nullable = false)
    private String nomMission;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String nomCorpsCeleste;

    private int nbreZonesExplorees;
    private int nbreEchantillons;
    private int interetScientifiqueTotal;
    private boolean reussite;
    private int budget;

    public RapportMission() {} // constructeur vide requis par JPA

    public RapportMission(String nomMission, LocalDate date, String nomCorpsCeleste, int nbreZonesExplorees, int nbreEchantillons, int interetScientifiqueTotal, boolean reussite, int budget) {
        this.nomMission = nomMission;
        this.date = date;
        this.nomCorpsCeleste = nomCorpsCeleste;
        this.nbreZonesExplorees = nbreZonesExplorees;
        this.nbreEchantillons = nbreEchantillons;
        this.interetScientifiqueTotal = interetScientifiqueTotal;
        this.reussite = reussite;
        this.budget = budget;
    }

    public Integer getId() {
        return id;
    }

    public String getNomMission() {
        return nomMission;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNomCorpsCeleste() {
        return nomCorpsCeleste;
    }

    public int getNbreZonesExplorees() {
        return nbreZonesExplorees;
    }

    public int getNbreEchantillons() {
        return nbreEchantillons;
    }

    public int getInteretScientifiqueTotal() {
        return interetScientifiqueTotal;
    }

    public boolean isReussite() {
        return reussite;
    }

    public int getBudget() {
        return budget;
    }

    
}