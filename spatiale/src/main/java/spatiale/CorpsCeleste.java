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

public class CorpsCeleste {
    int id;
    int nom;
    int taille;
    String[][] carte;
    TypeMonde typeMonde;
}