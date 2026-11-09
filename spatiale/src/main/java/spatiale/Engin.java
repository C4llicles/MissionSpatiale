package spatiale;

import java.util.List;

enum Etat {
    Disponible,
    EnMission,
    Detruit,
    Panne
}

abstract class Engin {
    private final int id;
    private int nom;
    private Etat etat;
    private List<Integer> position;
    public int idMission;

    public Engin(int id, int nom, Etat etat,  List<Integer> position) {
        this.id = id;
        this.nom = nom;
        this.etat = etat;
        this.position = position;
    }

    public Engin(int id, int nom, Etat etat) {
        this(id, nom, etat, List.of(-1, -1));
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

    public int getIdMission() {
        return idMission;
    }

    public void deplacer(List<Integer> newPosition, int idMission) {
        if (this.etat == Etat.EnMission && this.idMission == idMission) {
            this.position = newPosition;
            System.out.println("L'engin a été déplacé vers la nouvelle position : " + newPosition);
        } else if (this.etat == Etat.EnMission && this.idMission != idMission) {
            System.out.println("Cet engin n'est pas disponible pour cette mission. Il est actuellement occupé par une autre mission.");
        } else if (this.etat == Etat.Panne) {
            System.out.println("L'engin est en panne, il ne peut pas être déplacé. Veuillez le réparer avant de tenter de le déplacer.");
        } else if (this.etat == Etat.Detruit) {
            System.out.println("L'engin est détruit et ne peut plus être déplacé.");
        } else {
            System.out.println("L'engin n'est pas engagé dans une mission.");
        }
    }

    abstract void cartographier();

    abstract void prelever();

    public void reparer(Object personne) {
        if (!(personne instanceof Operateur operateur)) {
            System.out.println("Erreur : la personne passée en argument n'est pas un opérateur.");
            return;
        }
        
        if (!operateur.getCompetences().contains(Operateur.Competence.MAINTENANCE)) {
            System.out.println("Erreur : l'opérateur n'a pas les compétences nécessaires pour réparer l'engin.");
            return;
        }

        if (this.etat == Etat.Panne) {
            this.etat = Etat.Disponible;
            System.out.println("L'engin a été réparé et est maintenant disponible.");
        } else if (this.etat == Etat.Detruit) {
            System.out.println("L'engin est détruit et ne peut pas être réparé.");
        } else {
            System.out.println("L'engin n'est pas en panne et n'a pas besoin de réparation.");
        }
    }
}