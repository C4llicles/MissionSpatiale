package spatiale;

public class Directeur extends Personne {
    private int budgetTotalMissionsReussies;

    public Directeur(String nom, String prenom, Genre genre, String telephone, int budget) {
        super(nom, prenom, genre, telephone);
        this.budgetTotalMissionsReussies = budget;
    }

    public void setBudgetTotalMissionsReussies(int budget) {
        budgetTotalMissionsReussies = budget;
    }
}