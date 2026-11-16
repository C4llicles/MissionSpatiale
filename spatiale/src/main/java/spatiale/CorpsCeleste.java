package spatiale;

enum TypeMonde {
    /*
     * Enumération des types de mondes possibles.
     * Un seul type de monde pour le moment, mais cette énumération permet d'ajouter facilement de nouveaux types de mondes à l'avenir.
     */
    ROCHEUSE,
    GAZEUSE,
    LUNE
}

[[0,0],[0,1]]

[[0,0],[0,1],
[1,0],[1,1]]

enum TypeTerrain {
    Plaine,
    Cratere,
    Falaise,
    Ocean,
    Glace,
    Desert
}

record Zone(TypeTerrain type, int interet, boolean preleve, boolean cartographie) {}

public class CorpsCeleste {
    int id;
    int nom;
    int taille;
    Zone[][] carte;
    TypeMonde typeMonde;

    public CorpsCeleste(int id, int nom, int taille, Zone[][] carte, TypeMonde typeMonde) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.carte = carte;
        this.typeMonde = typeMonde;
    }

    public CorpsCeleste(int id, int nom) {
        
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setCarte(Zone[][] carte) {
        this.carte = carte;
    }

    public void setTypeMonde(TypeMonde typeMonde) {
        this.typeMonde = typeMonde;
    }

    public int getId() {
        return id;
    }

    public int getNom() {
        return nom;
    }

    public int getTaille() {
        return taille;
    }

    public Zone[][] getCarte() {
        return carte;
    }

    public TypeMonde getTypeMonde() {
        return typeMonde;
    }


}