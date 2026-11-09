package spatiale;

import java.util.*;

public class Operateur extends Personne {
    public enum Competence {
        PILOTAGE_ROVER,
        PILOTAGE_DRONE,
        GEOLOGIE,
        TELECOMMUNICATIONS,
        ENERGIE,
        MAINTENANCE // une compétence qui permet de réparer un engin
    }

    private EnumSet<Competence> competences;

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
}