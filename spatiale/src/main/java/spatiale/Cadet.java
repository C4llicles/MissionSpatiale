package spatiale;

public class Cadet extends Personne {
    private Personne tuteur;
    private int cptMissionsReussies;

    // faire un if tuteur null {throw} ici et try catch pour la création ? pour ne pas pouvoir créer un cadet sans tuteur par exemple
    public Cadet(String nom, String prenom, Genre genre, String telephone, Personne newTuteur) {
        super(nom, prenom, genre, telephone);
        if (newTuteur == null) {
            printf("Erreur : le cadet doit avoir un tuteur");
            return;
        }
        if (!(newTuteur instanceof Operateur op)) {
            printf("Erreur : le tuteur du cadet doit etre un operateur");
            return;
        }
        tuteur = op;
        cptMissionsReussies = 0;
    }

    public void changerTuteur(Personne newTuteur) {
        if (!(newTuteur instanceof Operateur op)) {
            printf("Erreur : le tuteur du cadet doit etre un operateur");
            return;
        }
        tuteur = op;
    }

    public void promotion() {
        // à faire (ici ou dans Operateur / ailleurs ?)
    }

    // après une mission, il faut incrémenter cptMissionsReussies
}