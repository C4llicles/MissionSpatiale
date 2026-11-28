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
    private boolean cooldownEnCours; // pour éviter que l'opérateur utilise sa compétence deux fois en un cyble.

    public Operateur(String nom, String prenom, Genre genre, String telephone, Set<Competence> competences) {
        super(nom, prenom, genre, telephone);
        if (competences == null || competences.isEmpty() || competences.size() > 3 || (competences.contains(Competence.PILOTAGE_ROVER) && competences.contains(Competence.PILOTAGE_DRONE))) {
            throw new IllegalArgumentException(
                "Un opérateur doit avoir entre 1 et 3 compétences et un opérateur ne peut pas avoir deux compétences de pilotage différentes."
            );
        }
        this.competences = EnumSet.copyOf(competences);
    }

    public Set<Competence> getCompetences() {
        return Set.copyOf(competences);
    }

    public boolean getCooldownEnCours() {
        return cooldownEnCours;
    }

    public void setCompetences(Set<Competence> competences) {
        if (competences == null || competences.isEmpty() || competences.size() > 3 || (competences.contains(Competence.PILOTAGE_ROVER) && competences.contains(Competence.PILOTAGE_DRONE))) {
            throw new IllegalArgumentException(
                "Un opérateur doit avoir entre 1 et 3 compétences et un opérateur ne peut pas avoir deux compétences de pilotage différentes."
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
    
    public void setCooldownEnCours(boolean cooldownEnCours) {
        this.cooldownEnCours = cooldownEnCours;
    }

    public List<Cadet> getCadetsSousTutelle() {
        if (cadetsSousTutelle == null) {
            cadetsSousTutelle = new ArrayList<>();
        }
        return cadetsSousTutelle;
    }
    
    public void ajouterCompetence(Competence comp) {
        if (competences.size() >= 3) {
            throw new IllegalArgumentException ("L'operateur concerne a deja le nombre maximal de competences (3)");
        }
        if (comp == Competence.PILOTAGE_ROVER && competences.contains(Competence.PILOTAGE_DRONE) || comp == Competence.PILOTAGE_DRONE && competences.contains(Competence.PILOTAGE_ROVER)) {
            throw new IllegalArgumentException("L'operateur ne peut pas avoir deux competences de pilotage differentes");
        }
        this.competences.add(comp);
    }
}