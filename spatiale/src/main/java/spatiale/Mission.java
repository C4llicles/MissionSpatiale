package spatiale;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

enum EtatMission {
    PLANIFIEE,
    EN_COURS,
    TERMINEE
}

public class Mission {
    private String nom;
    private CorpsCeleste corpsCeleste;
    private LocalDate dateLancement;
    private int budget;
    private Directeur directeur;
    private List<Operateur> operateurs;
    private List<Engin> engins;
    private int[][] zonesObjectifs; // un tableau contenant les indices des zones objectives
    private volatile EtatMission etat = EtatMission.PLANIFIEE; // volatile pour que le programme puisse consulter l'état pendant la simulation

    Mission(String newNom, CorpsCeleste newCorpsCeleste, LocalDate newDate, int newBudget, Directeur newDirecteur, List<Operateur> newOperateurs, List<Engin>newEngins) {
        nom = newNom;
        corpsCeleste = newCorpsCeleste;
        dateLancement = newDate;
        budget = newBudget;
        directeur = newDirecteur;
        operateurs = newOperateurs;
        engins = newEngins;

    }
// getLongueurMax()
    private int[][] genererZonesObjectifs(int lon, int larg, Random random, CorpsCeleste corpsCeleste) {
        if (lon < 1 || lon > corpsCeleste.getLongueurMax() || larg < 1 || larg > corpsCeleste.getLargeurMax()) {
            throw new IllegalArgumentException("La longueur doit être entre 1 et " + corpsCeleste.getLongueurMax() + ", la largeur entre 1 et " + corpsCeleste.getLargeurMax());
        }

        int nbreZones = 1 + random.nextInt(lon * larg); // nbre de zones objectifs entre 1 et toutes les zones possibles
        int[][] zonesObjectifs = new int[nbreZones][2]; // paires de coordonnées, en quantité nbreZones
        boolean[][] dejaChoisie = new boolean[larg][lon]; // pour vérifier si une zone a déjà été choisie
    }


    public void demarrerMission() {

    }
}