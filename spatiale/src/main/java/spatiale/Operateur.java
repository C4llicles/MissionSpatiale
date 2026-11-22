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

    // ELIOTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
    // jsuis pas sûre comment ça marche ici, regarde stp j'ai jamais vraiment fait de throw
    // jsp si je dois faire un return ou si ça le fait tout seul
    // et j'ai la flemme de regarer maintenant j'ai mal à la tête
    public void ajouterCompetence(Competence comp) {
        if (competences.size() >= 3) {
            throw ("L'operateur concerne a deja le nombre maximal de competences (3)");
            return;
        }
        this.competences.add(comp);
    }
}