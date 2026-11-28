package spatiale;

public class Cadet extends Personne {
    private Operateur tuteur;
    private int compteurMissionsReussies;
    private boolean missionEnCours;

    // faire un if tuteur null {throw} ici et try catch pour la création ? pour ne pas pouvoir créer un cadet sans tuteur par exemple
    public Cadet(String nom, String prenom, Genre genre, String telephone, Operateur newTuteur) {
        super(nom, prenom, genre, telephone);
        tuteur = newTuteur;
        newTuteur.getCadetsSousTutelle().add(this);
        compteurMissionsReussies = 0;
    }

    public Personne getTuteur() {
        return tuteur;
    }

    public int getCompteurMissionsReussies() {
        return compteurMissionsReussies;
    }

    public void setMissionEnCours(boolean missionEnCours) {
        this.missionEnCours = missionEnCours;
    }

    public boolean isMissionEnCours() {
        return missionEnCours;
    }

    public void incrementerCompteurMissionsReussies() {
        compteurMissionsReussies++;
    }

    public void changerTuteur(Operateur newTuteur) {
        tuteur.getCadetsSousTutelle().remove(this);
        tuteur = newTuteur;
        tuteur.getCadetsSousTutelle().add(this);
    }

    public Operateur promotion() {
        if (compteurMissionsReussies < 3) {
            throw new IllegalStateException("Le cadet n'a pas encore réussi 3 missions et ne peut pas être promu.");
        }
    tuteur.getCadetsSousTutelle().remove(this);
    return new Operateur(getNom(), getPrenom(), getGenre(), getTelephone(), tuteur.getCompetences());
    }

    

}