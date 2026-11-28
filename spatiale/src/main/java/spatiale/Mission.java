package spatiale;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private static int id;
    private String nom;
    private CorpsCeleste corpsCeleste;
    private LocalDate dateLancement;
    private int budget;
    private Directeur directeur;
    private List<Operateur> operateurs;
    private List<Engin> engins;
    private volatile EtatMission etat = EtatMission.PLANIFIEE; // volatile pour que le programme puisse consulter l'état pendant la simulation
    private RapportMission rapport;

    public Mission(int newId, String newNom, CorpsCeleste newCorpsCeleste, LocalDate newDate, int newBudget, Directeur newDirecteur, List<Operateur> newOperateurs, List<Engin>newEngins) {
        id = newId;
        nom = newNom;
        corpsCeleste = newCorpsCeleste;
        dateLancement = newDate;
        budget = newBudget;
        directeur = newDirecteur;
        operateurs = newOperateurs;
        engins = newEngins;
    }
// getLongueurMax()
    public void genererZonesObjectifs(int lon, int larg, Random random, CorpsCeleste corpsCeleste) {
        if (lon < 1 || lon > corpsCeleste.getLongueurMax() || larg < 1 || larg > corpsCeleste.getLargeurMax()) {
            throw new IllegalArgumentException("La longueur doit être entre 1 et " + corpsCeleste.getLongueurMax() + ", la largeur entre 1 et " + corpsCeleste.getLargeurMax());
        }
        int minZonesObjectifs = (int)(0.25 * (lon * larg)); // 25% des zones
        int maxZonesObjectifs = (int)(0.75 * (lon * larg)); // 75% des zones
        corpsCeleste.setNombreZonesObjectifs(
                random.nextInt(maxZonesObjectifs - minZonesObjectifs + 1) + minZonesObjectifs);
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
            Main.afficher("Mission réussie : " + cptZonesExplorees + " zones explorées sur " + corpsCeleste.getNombreZonesObjectifs() + " zones objectifs.");
            return true;
        } else {
            for (Engin engin : engins) {
                if (engin.getEtat() != Etat.Detruit && engin.getEtat() != Etat.Panne && engin.getStrategieExploration() instanceof ExplorationPrelevement) {
                    return false;
                }
        }
            Main.afficher("Mission échouée : plus aucun engin capable de prélever n'est disponible.");
            return false;
        }
    }

    public synchronized void terminerMission(int nbreZonesExplorees, int nbreEchantillons, int interetTotal) { // synchronized permet d'éviter les bugs lors de la consultation de l'état/récupération du rapport par plusieurs thread en même temps
        if (!(etat == EtatMission.EN_COURS)) {
            Main.afficher("Mission pas en cours, donc impossible à terminer");
            return;
        }

        Main.afficher("Clôture de la mission \"" + nom + "\".");
        boolean reussite = verifierMissionReussie();

        if (reussite) {
            for (Operateur op : operateurs) {
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
        } else {
            directeur.incrementerMissionsEchouees(budget);
        }
        for (Operateur op : operateurs) {
            op.setMissionEnCours(false);
            for (Cadet cadet : op.getCadetsSousTutelle()) {
                cadet.setMissionEnCours(false);
            }
        }
        directeur.decrementerMissionsEnCours();

        for (Engin engin : engins) {
            if (engin.getEtat() != Etat.Detruit) {
                Main.afficher("L'engin " + engin.getNom() + " est de retour à la base et est maintenant disponible.");
                engin.setEtat(Etat.Disponible);
            }
        }

        etat = EtatMission.TERMINEE;
        
        this.rapport = new RapportMission(this.nom, this.dateLancement, this.corpsCeleste.getNom(), nbreZonesExplorees, nbreEchantillons, interetTotal, reussite, budget);
        Main.afficher("Mission \"" + nom + "\" terminée : " + (reussite ? "réussite." : "échec."));
        Main.afficher("Bilan : " + nbreZonesExplorees + " zones explorées sur "
                + corpsCeleste.getNombreZonesObjectifs() + " zones objectifs, "
                + nbreEchantillons + " échantillons, intérêt scientifique total de "
                + interetTotal + ", budget de " + budget + ".");
        // quand on appelle terminerMission(...), il faudra aussi appeler depotRapports.enregistrer(mission.getRapport()); pour l'enregistrer dans la bd
    }

    public synchronized RapportMission getRapport() { // synchronized permet d'éviter les bugs lors de la consultation de l'état/récupération du rapport par plusieurs thread en même temps
        if (etat != EtatMission.TERMINEE) {
            throw new IllegalStateException("Mission n'est pas encore terminée, impossible de récupérer le rapport");
        }
        return rapport;
    }

    public void demarrerMission() {
        // Conditions de démarrage de la mission
        for (Operateur op : operateurs) {
            if (op.isMissionEnCours()) {
                Main.afficher("Impossible de démarrer la mission : l'opérateur " + op.getNom() + " est déjà en mission.");
                return;
            }
            for (Cadet cadet : op.getCadetsSousTutelle()) {
                if (cadet.isMissionEnCours()) {
                    Main.afficher("Impossible de démarrer la mission : le cadet " + cadet.getNom() + " est déjà en mission.");
                    return;
                }
            }
        }

        // Démarrage de la mission
        etat = EtatMission.EN_COURS;
        java.time.Instant startTime = java.time.Instant.now();
        java.time.Instant tempsFin = startTime.plusMillis((corpsCeleste.getNombreZonesObjectifs() * Config.TEMPS_PAR_ZONE) / engins.size());
        while (java.time.Instant.now().isBefore(tempsFin)) {
            for (Operateur op : operateurs) {
                if (op.getCompetences().contains(Competence.MAINTENANCE) && !op.getCooldownEnCours()) {
                    for (Engin engin : engins) {
                        if (engin.getEtat() == Etat.Panne) {
                            engin.reparer(op);
                            op.setCooldownEnCours(true);
                            break; // on sort de la boucle des engins pour ne pas essayer de réparer plusieurs engins en même temps avec le même opérateur
                        }
                    }
                }
            }
            for (Operateur op : operateurs) {
                if (op.getCompetences().contains(Competence.PILOTAGE_ROVER) && !op.getCooldownEnCours()) {
                    for (Engin engin : engins) {
                        if (engin.getEtat() == Etat.EnMission && engin.getStrategieExploration() instanceof ExplorationPrelevement) {
                            List<List<Integer>> positionsDisponibles = new ArrayList<>();
                            for (int x = 0; x < corpsCeleste.getLongueur(); x++) {
                                for (int y = 0; y < corpsCeleste.getLargeur(); y++) {
                                    Zone zone = corpsCeleste.getCarte()[x][y];
                                    if (!zone.isPreleve() && !(zone.isCartographie() && zone.isARisque())) {
                                        positionsDisponibles.add(List.of(x, y));
                                    }
                                }
                            }
                            if (!positionsDisponibles.isEmpty()) {
                                List<Integer> newPosition = positionsDisponibles.get(new Random().nextInt(positionsDisponibles.size()));
                                if (engin.deplacer(newPosition, id, corpsCeleste)
                                        && engin.getEtat() != Etat.Detruit
                                        && engin.getEtat() != Etat.Panne) {
                                    engin.explorer(corpsCeleste);
                                }
                            }
                            op.setCooldownEnCours(true);
                            break;
                        }
                    }
                } else if (op.getCompetences().contains(Competence.PILOTAGE_DRONE) && !op.getCooldownEnCours()) {
                    for (Engin engin : engins) {
                        if (engin.getEtat() == Etat.EnMission && engin.getStrategieExploration() instanceof ExplorationCartographie) {
                            List<List<Integer>> positionsDisponibles = new ArrayList<>();
                            for (int x = 0; x < corpsCeleste.getLongueur(); x++) {
                                for (int y = 0; y < corpsCeleste.getLargeur(); y++) {
                                    if (!corpsCeleste.getCarte()[x][y].cartographie) {
                                        positionsDisponibles.add(List.of(x, y));
                                    }
                                }
                            }
                            if (!positionsDisponibles.isEmpty()) {
                                List<Integer> newPosition = positionsDisponibles.get(new Random().nextInt(positionsDisponibles.size()));
                                if (engin.deplacer(newPosition, id, corpsCeleste)
                                        && engin.getEtat() != Etat.Detruit
                                        && engin.getEtat() != Etat.Panne) {
                                    engin.explorer(corpsCeleste);
                                }
                            }
                            op.setCooldownEnCours(true);
                            break;
                        }
                    }
                }
            }
            for (Operateur op : operateurs) {
                if (op.getCooldownEnCours()) {
                    op.setCooldownEnCours(false);
                }
            }
        }
        terminerMission(corpsCeleste.getNombreZonesExplorees(), corpsCeleste.getNombreEchantillonsPreleves(), corpsCeleste.getInteretTotal());
    }
}