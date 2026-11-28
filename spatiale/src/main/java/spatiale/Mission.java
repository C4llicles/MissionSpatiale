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
        int minZonesObjectifs = (int) 0.25 * (lon * larg); // 25% des zones
        int maxZonesObjectifs = (int) 0.75 * (lon * larg); // 75% des zones
        corpsCeleste.setNombreZonesObjectifs(random.nextInt(maxZonesObjectifs * minZonesObjectifs + 1) + minZonesObjectifs);
        for (int i = 0; i < corpsCeleste.getNombreZonesObjectifs(); i++) {
            int x, y;
            do {
                x = random.nextInt(lon);
                y = random.nextInt(larg);
            } while (corpsCeleste.getCarte()[x][y].isObjectif()); // on s'assure que la zone n'est pas déjà un objectif
            corpsCeleste.getCarte()[x][y].setObjectif(true);
            
        }
    }

    public boolean verifierMissionReussie() {
        int cptZonesExplorees = 0;
        for (int x = 0; x < corpsCeleste.getLongueur(); x++) {
            for (int y = 0; y < corpsCeleste.getLargeur(); y++) {
                if (corpsCeleste.getCarte()[x][y].objectif) {
                    if (corpsCeleste.getCarte()[x][y].preleve) {
                        cptZonesExplorees++;
                    }
                }
            }
        }
        if (Config.SEUIL_REUSSITE*cptZonesExplorees >= corpsCeleste.getNombreZonesObjectifs()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifierMissionEchouee() {
        if (!(etat == EtatMission.EN_COURS)) {
            System.out.println("Mission pas en cours, donc impossible à vérifier");
            return false;
        } else {
            for (Engin engin : engins) {
                if (engin.getEtat() != Etat.Detruit && engin.getEtat() != Etat.Panne && engin.getStrategieExploration() instanceof ExplorationPrelevement) {
                    return false; // au moins un engin pouvant prelever et donc terminer la mission n'est pas détruit, donc la mission n'est pas échouée
                }
            }
            return true; // tous les engins sont détruits, donc la mission est échouée
        }
    }

    public void demarrerMission() {
        boolean loop = true;

    }

    public void terminerMission(int nbreZonesExplorees, int nbreEchantillons, int interetTotal) {
        if (!(etat == EtatMission.EN_COURS)) {
            System.out.println("Mission pas en cours, donc impossible à terminer");
            return;
        }
        if (verifierMissionEchouee()) {
            System.out.println("La mission a échoué. Tous les engins capables de prélever ont été détruits.");
        } else if (verifierMissionReussie()) {
            System.out.println("La mission a réussi !");
        } else {
            System.out.println("La mission n'est pas encore terminée.");
        }
        for (Operateur op : operateurs) {
            op.setMissionEnCours(false);
            for (Cadet cadet : op.getCadetsSousTutelle()) {
                if (cadet.isMissionEnCours()) {
                    cadet.incrementerCompteurMissionsReussies();
                    cadet.setMissionEnCours(false);
                }
                if (cadet.getCompteurMissionsReussies() >= 3) {
                    op.getCadetsSousTutelle().remove(cadet);
                    cadet.promotion();
                }
            }
        }
        directeur.incrementerMissionsReussies(budget);
        directeur.decrementerMissionsEnCours();

        boolean reussite = verifierMissionReussie();
        
        RapportMission rapport = new RapportMission(nom, dateLancement, corpsCeleste.getNom(), nbreZonesExplorees, nbreEchantillons, interetTotal, reussite, budget);
    }
}