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
    private static final int LONGUEUR_MAX = 10;
    private static final int LARGEUR_MAX = 10;
    private int id;
    private int nom;
    private int longueur;
    private int largeur;
    private Zone[][] carte;
    private TypeMonde typeMonde;

    public CorpsCeleste(int id, int nom, int longueur, int largeur, Zone[][] carte, TypeMonde typeMonde) {
        this.id = id;
        this.nom = nom;
        this.longueur = longueur;
        this.largeur = largeur;
        this.carte = carte;
        this.typeMonde = typeMonde;
    }

    public CorpsCeleste(int id, int nom, int longueur, int largeur, TypeMonde typeMonde) {
        this.id = id;
        this.nom = nom;
        this.longueur = longueur;
        this.largeur = largeur;
        this.typeMonde = typeMonde;
        creerCarte();
    }

    public CorpsCeleste(int id, int nom, int longueur, int largeur) {
        this.id = id;
        this.nom = nom;
        this.longueur = longueur;
        this.largeur = largeur;
        this.typeMonde = TypeMonde.values()[(int) (Math.random() * TypeMonde.values().length)];
        creerCarte();
    }

    public CorpsCeleste(int id, int nom, TypeMonde typeMonde) {
        this.id = id;
        this.nom = nom;
        this.typeMonde = typeMonde;
        this.longueur = (int) (Math.random() * LONGUEUR_MAX) + 1;
        this.largeur = (int) (Math.random() * LARGEUR_MAX) + 1;
        creerCarte();
    }

    public CorpsCeleste(int id, int nom) {
        this.id = id;
        this.nom = nom;
        this.typeMonde = TypeMonde.values()[(int) (Math.random() * TypeMonde.values().length)];
        this.longueur = (int) (Math.random() * LONGUEUR_MAX) + 1;
        this.largeur = (int) (Math.random() * LARGEUR_MAX) + 1;
        creerCarte();
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
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

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public Zone[][] getCarte() {
        return carte;
    }

    public TypeMonde getTypeMonde() {
        return typeMonde;
    }

    public int getLongueurMax() {
        return LONGUEUR_MAX;
    }

    public int getLargeurMax() {
        return LARGEUR_MAX;
    }

    public void creerCarte() {
        for (int i = 0; i < longueur; i++) {
            for (int j = 0 ; j < largeur; j++) {
                TypeTerrain typeTerrain = TypeTerrain.values()[(int) (Math.random() * TypeTerrain.values().length)];
                int interet = (int) (Math.random() * 11);
                boolean preleve = false;
                boolean cartographie = false;
                carte[i][j] = new Zone(typeTerrain, interet, preleve, cartographie);
            }
        }
    }

    public void verifierCoordonnees(int x, int y) {
        if (x < 0 || x >= longueur || y < 0 || y >= largeur) {
            throw new IllegalArgumentException("Coordonnées hors limites du corps céleste.");
        }
    }

    public Zone getZone(int x, int y) {
        verifierCoordonnees(x, y);
        return carte[x][y];
    }
}