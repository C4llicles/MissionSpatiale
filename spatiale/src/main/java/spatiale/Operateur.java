package spatiale;

import java.util.*;

enum Competence {
    PILOTAGE_ROVER,
    PILOTAGE_DRONE,
    GEOLOGIE,
    TELECOMMUNICATIONS,
    ENERGIE,
    MAINTENANCE // une compétence qui permet de réparer un engin
}

public class Operateur extends Personne {
    private EnumSet<Competence> competences;
    private boolean missionEnCours;
    private List<Cadet> cadetsSousTutelle;

    public Operateur(String nom, String prenom, Genre genre, String telephone, Set<Competence> competences) {
        super(nom, prenom, genre, telephone);
        if (competences == null || competences.isEmpty() || competences.size() > 3) {
            throw new IllegalArgumentException(
                "Un opérateur doit avoir entre 1 et 3 compétences."
            );
        }
        this.competences = EnumSet.copyOf(competences);
    }

    public Set<Competence> getCompetences() {
        return Set.copyOf(competences);
    }

    public void setCompetences(Set<Competence> competences) {
        if (competences == null || competences.isEmpty() || competences.size() > 3) {
            throw new IllegalArgumentException(
                "Un opérateur doit avoir entre 1 et 3 compétences."
            );
        }
        this.competences = EnumSet.copyOf(competences);
    }

    public boolean isMissionEnCours() {
        return missionEnCours;
    }

    public void setMissionEnCours(boolean missionEnCours) {
        this.missionEnCours = missionEnCours;
    }

    public List<Cadet> getCadetsSousTutelle() {
        if (cadetsSousTutelle == null) {
            cadetsSousTutelle = new ArrayList<>();
        }
        return cadetsSousTutelle;
    }

    // ELIOT
    // jsuis pas sûre comment ça marche ici, regarde stp j'ai jamais vraiment fait de throw
    // jsp si je dois faire un return ou si ça le fait tout seul
    // et j'ai la flemme de regarer maintenant j'ai mal à la tête
    public void ajouterCompetence(Competence comp) {
        if (competences.size() >= 3) {
            throw new IllegalArgumentException ("L'operateur concerne a deja le nombre maximal de competences (3)");
        }
        this.competences.add(comp);
    }
}