package spatiale;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

enum EtatMission {
    PLANIFIEE,
    EN_COURS,
    TERMINEE
}

// s'il nous faudra un getEtat(), il faut le faire en synchronized (comme getRapport()) pour éviter les bugs si jamais ya un thread qui veut le récupérer quand un autre thread est en train de le changer
// dans ce cas on pourra supprimer volatile quand on crée l'attribut etat mais on peut le laisser aussi, better safe than sorry

public class Mission {
    private String nom;
    private CorpsCeleste corpsCeleste;
    private LocalDate dateLancement;
    private int budget;
    private Directeur directeur;
    private List<Operateur> operateurs;
    private List<Engin> engins;
    private volatile EtatMission etat = EtatMission.PLANIFIEE; // volatile pour que le programme puisse consulter l'état pendant la simulation
    private RapportMission rapport;

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
        if (cptZonesExplorees >= Config.SEUIL_REUSSITE*corpsCeleste.getNombreZonesObjectifs()) {
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

    public synchronized void terminerMission(int nbreZonesExplorees, int nbreEchantillons, int interetTotal) { // synchronized permet d'éviter les bugs lors de la consultation de l'état/récupération du rapport par plusieurs thread en même temps
        if (!(etat == EtatMission.EN_COURS)) {
            // je crois que c'est le bon exception, jte laisse changer sinon, et on peut mettre un catch dans le main genre try {mission.terminerMission;} catch(IllegalStateException e) {System.out.println("Impossible de terminer : " + e.getMessage())}
            // throw new IllegalStateException("Mission n'est pas en cours, donc impossible à terminer");
            System.out.println("Mission pas en cours, donc impossible à terminer");
            return;
        }

        // vu qu'on doit pas mettre de print en dehors de main et les tests, pour moi tout ça c'est inutile, d'ici
        if (verifierMissionEchouee()) {
            System.out.println("La mission a échoué. Tous les engins capables de prélever ont été détruits.");
        } else if (verifierMissionReussie()) {
            System.out.println("La mission a réussi !");
        } else {
            System.out.println("La mission n'est pas encore terminée.");
        }
        // jusqu'ici, c pas grave si on sait pas ce qui se passe exactement je suppose, tant qu'on a la réponse si la mission est réussie ou pas
        
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
        
        this.rapport = new RapportMission(this.nom, this.dateLancement, this.corpsCeleste.getNom(), nbreZonesExplorees, nbreEchantillons, interetTotal, reussite, budget);
        // quand on appelle terminerMission(...), il faudra aussi appeler depotRapports.enregistrer(mission.getRapport()); pour l'enregistrer dans la bd
    }

    public synchronized RapportMission getRapport() { // synchronized permet d'éviter les bugs lors de la consultation de l'état/récupération du rapport par plusieurs thread en même temps
        if (etat != EtatMission.TERMINEE) {
            throw new IllegalStateException("Mission n'est pas encore terminée, impossible de récupérer le rapport");
        }
        return rapport;
    }
}