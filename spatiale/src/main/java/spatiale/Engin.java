package spatiale;

import java.util.List;

enum Etat {
    Disponible,
    EnMission,
    Detruit,
    Panne
}

enum TypeDeplacement {
    AERIEN,
    TERRESTRE
}

abstract class Engin {
    private final int id;
    private int nom;
    private Etat etat;
    private List<Integer> position;
    public int idMission;
    private final StrategieExploration strategieExploration;
    private TypeDeplacement typeDeplacement;

    public Engin(int id, int nom, Etat etat,  List<Integer> position, StrategieExploration strategieExploration) {
        this.id = id;
        this.nom = nom;
        this.etat = etat;
        this.position = position;
        this.strategieExploration = java.util.Objects.requireNonNull(strategieExploration);
    }

    public Engin(int id, int nom, Etat etat, StrategieExploration strategieExploration) {
        this(id, nom, etat, List.of(-1, -1), strategieExploration);
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public void setPosition(List<Integer> position) {
        this.position = position;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public void setTypeDeplacement(TypeDeplacement typeDeplacement) {
        this.typeDeplacement = java.util.Objects.requireNonNull(typeDeplacement);
    }

    public void assignerMission(int idMission) {
        this.idMission = idMission;
    }

    public int getId() {
        return id;
    }

    public int getNom() {
        return nom;
    }

    public List<Integer> getPosition() {
        return position;
    }

    public Etat getEtat() {
        return etat;
    }

    public TypeDeplacement getTypeDeplacement() {
        return typeDeplacement;
    }

    public int getIdMission() {
        return idMission;
    }

    public StrategieExploration getStrategieExploration() {
        return strategieExploration;
    }

    public boolean deplacer(List<Integer> newPosition, int idMission, CorpsCeleste corpsCeleste) {
        if (this.etat == Etat.EnMission && this.idMission != idMission) {
            Main.afficher("Cet engin n'est pas disponible pour cette mission. Il est actuellement occupé par une autre mission.");
            return false;
        } else if (this.etat == Etat.Panne) {
            Main.afficher("L'engin est en panne, il ne peut pas être déplacé. Veuillez le réparer avant de tenter de le déplacer.");
            return false;
        } else if (this.etat == Etat.Detruit) {
            Main.afficher("L'engin est détruit et ne peut plus être déplacé.");
            return false;
        } else if (this.etat == Etat.EnMission && this.idMission == idMission) {
            if (this.typeDeplacement == TypeDeplacement.AERIEN) {
                Main.afficher("L'engin est de type aérien et peut se déplacer vers n'importe quelle zone du corps céleste.");
                position = newPosition;
                Main.afficher("L'engin a été déplacé vers la nouvelle position : " + newPosition);
                panneAleatoire();
                return true;
            } else if (this.typeDeplacement == TypeDeplacement.TERRESTRE) {
                if (corpsCeleste.getCarte()[newPosition.get(0)][newPosition.get(1)].isCartographie()) {
                    if (corpsCeleste.getCarte()[newPosition.get(0)][newPosition.get(1)].isARisque()) {
                        Main.afficher("Attention : la zone vers laquelle vous tentez de déplacer l'engin est à risque. Déplacement annulé.");
                        return false;
                    } else {
                        Main.afficher("La zone vers laquelle vous tentez de déplacer l'engin est sûre. Déplacement autorisé.");
                        position = newPosition;
                        Main.afficher("L'engin a été déplacé vers la nouvelle position : " + newPosition);
                        panneAleatoire();
                        return true;
                    }
                } else {
                    Main.afficher("La zone vers laquelle vous tentez de déplacer l'engin n'a pas été cartographiée. Attention : le déplacement vous expose à des risques inconnus. Déplacement autorisé mais soyez prudent...");
                    corpsCeleste.getCarte()[newPosition.get(0)][newPosition.get(1)].setCartographie(true);
                    if (corpsCeleste.getCarte()[newPosition.get(0)][newPosition.get(1)].isARisque()) {
                        Main.afficher("La zone vers laquelle vous vous êtes déplacé était une zone à risque. L'engin a été détruit.");
                        this.etat = Etat.Detruit;
                        return false;
                    } else {
                        Main.afficher("La zone vers laquelle vous vous êtes déplacé était une zone sûre.");
                        position = newPosition;
                        Main.afficher("L'engin a été déplacé vers la nouvelle position : " + newPosition);
                        panneAleatoire();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void explorer(CorpsCeleste corpsCeleste) {
        java.util.Objects.requireNonNull(corpsCeleste);
        if (etat != Etat.EnMission) {
            throw new IllegalStateException("Seul un engin en mission peut explorer.");
        }
        if (position.get(0) < 0 || position.get(1) < 0 || position.get(0) >= corpsCeleste.getLongueur() || position.get(1) >= corpsCeleste.getLargeur()) {
            throw new IllegalArgumentException("La position de l'engin est en dehors des limites du corps céleste.");
        }
        strategieExploration.explorer(corpsCeleste, position.get(0), position.get(1));
    }

    public void reparer(Personne personne) {
        if (!(personne instanceof Operateur operateur)) { // si personne n'est pas un operateur alors return
                                                          // si personne un operateur alors operateur = personne, comme ça on a accès aux méthodes de Operateur
            Main.afficher("Erreur : la personne passée en argument n'est pas un opérateur.");
            return;
        }
        
        if (!operateur.getCompetences().contains(Competence.MAINTENANCE)) {
            Main.afficher("Erreur : l'opérateur n'a pas les compétences nécessaires pour réparer l'engin.");
            return;
        }

        if (this.etat == Etat.Panne) {
            this.etat = Etat.Disponible;
            Main.afficher("L'engin a été réparé et est maintenant disponible.");
        } else if (this.etat == Etat.Detruit) {
            Main.afficher("L'engin est détruit et ne peut pas être réparé.");
        } else {
            Main.afficher("L'engin n'est pas en panne et n'a pas besoin de réparation.");
        }
    }

    public void panneAleatoire() {
        if (Math.random() < 0.025) { // 10% de chance de tomber en panne
            this.etat = Etat.Panne;
            Main.afficher("L'engin est tombé en panne.");
        }
    }
}