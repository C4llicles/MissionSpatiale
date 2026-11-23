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

    public Mission(String newNom, CorpsCeleste newCorpsCeleste, LocalDate newDate, int newBudget, Directeur newDirecteur, List<Operateur> newOperateurs, List<Engin>newEngins) {
        nom = newNom;
        corpsCeleste = newCorpsCeleste;
        dateLancement = newDate;
        budget = newBudget;
        directeur = newDirecteur;
        operateurs = newOperateurs;
        engins = newEngins;
    }
// getLongueurMax()
    private void genererZonesObjectifs(int lon, int larg, Random random, CorpsCeleste corpsCeleste) {
        if (lon < 1 || lon > corpsCeleste.getLongueurMax() || larg < 1 || larg > corpsCeleste.getLargeurMax()) {
            throw new IllegalArgumentException("La longueur doit être entre 1 et " + corpsCeleste.getLongueurMax() + ", la largeur entre 1 et " + corpsCeleste.getLargeurMax());
        }
        corpsCeleste.setNombreZonesObjectifs(1 + random.nextInt(lon * larg)); // nbre de zones objectifs entre 1 et toutes les zones possibles
        for (int i = 0; i < corpsCeleste.getNombreZonesObjectifs(); i++) {
            int x, y;
            do {
                x = random.nextInt(lon);
                y = random.nextInt(larg);
            } while (corpsCeleste.getCarte()[x][y].isObjectif()); // on s'assure que la zone n'est pas déjà un objectif
            corpsCeleste.getCarte()[x][y].setObjectif(true);
            
        }
    }


    public void demarrerMission() {

    }

    public void terminerMission(int nbreZonesExplorees, int nbreEchantillons, int interetTotal) {
        if (!(etat == EtatMission.EN_COURS)) {
            System.out.println("Mission pas en cours, donc impossible à terminer");
            return;
        }
        for (Operateur op : operateurs) {
            for (Cadet cadet : op.getCadetsSousTutelle()) {
                if (cadet.isMissionEnCours()) {
                    cadet.incrementerCompteurMissionsReussies();
                }
                if (cadet.getCompteurMissionsReussies() >= 3) {
                    op.getCadetsSousTutelle().remove(cadet);
                    cadet.promotion();
                }
            }
        }
        // booléan réussite = true si 70% des zones objectives ont été explorées
        RapportMission rapport = new RapportMission(nom, dateLancement, corpsCeleste.getNom(), nbreZonesExplorees, nbreEchantillons, interetTotal, reussite, budget);
    }
}