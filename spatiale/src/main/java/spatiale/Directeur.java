package spatiale;

public class Directeur extends Personne {
    private int budgetTotalMissionsReussies;
    private int nombreMissionsReussies;
    private int budgetTotalMissionsEchouees;
    private int nombreMissionsEchouees;
    private int nombreDeMissionsEnCours;

    public Directeur(String nom, String prenom, Genre genre, String telephone) {
        super(nom, prenom, genre, telephone);
        budgetTotalMissionsReussies = 0;
        nombreMissionsReussies = 0;
        budgetTotalMissionsEchouees = 0;
        nombreMissionsEchouees = 0;
        nombreDeMissionsEnCours = 0;
    }

    public void setBudgetTotalMissionsReussies(int budget) {
        budgetTotalMissionsReussies = budget;
    }

    public void setNombreMissionsReussies(int nombre) {
        nombreMissionsReussies = nombre;
    }

    public void setBudgetTotalMissionsEchouees(int budget) {
        budgetTotalMissionsEchouees = budget;
    }

    public void setNombreMissionsEchouees(int nombre) {
        nombreMissionsEchouees = nombre;
    }

    public void setNombreDeMissionsEnCours(int nombre) {
        nombreDeMissionsEnCours = nombre;
    }

    public int getBudgetTotalMissionsReussies() {
        return budgetTotalMissionsReussies;
    }

    public int getNombreMissionsReussies() {
        return nombreMissionsReussies;
    }

    public int getBudgetTotalMissionsEchouees() {
        return budgetTotalMissionsEchouees;
    }

    public int getNombreMissionsEchouees() {
        return nombreMissionsEchouees;
    }

    public int getNombreDeMissionsEnCours() {
        return nombreDeMissionsEnCours;
    }

    public void incrementerMissionsReussies(int budget) {
        nombreMissionsReussies++;
        budgetTotalMissionsReussies += budget;
    }

    public void incrementerMissionsEchouees(int budget) {
        nombreMissionsEchouees++;
        budgetTotalMissionsEchouees += budget;
    }

    public void incrementerMissionsEnCours() {
        nombreDeMissionsEnCours++;
    }

    public void decrementerMissionsEnCours() {
        if (nombreDeMissionsEnCours > 0) {
            nombreDeMissionsEnCours--;
        }
    }

    public void ajouterBudgetMissionsReussies(int budget) {
        budgetTotalMissionsReussies += budget;
    }

    public void ajouterBudgetMissionsEchouees(int budget) {
        budgetTotalMissionsEchouees += budget;
    }

    public void afficherStatistiques() {
        System.out.println("Statistiques du Directeur :");
        System.out.println("Nombre de missions réussies : " + nombreMissionsReussies);
        System.out.println("Budget total des missions réussies : " + budgetTotalMissionsReussies);
        System.out.println("Nombre de missions échouées : " + nombreMissionsEchouees);
        System.out.println("Budget total des missions échouées : " + budgetTotalMissionsEchouees);
        System.out.println("Nombre de missions en cours : " + nombreDeMissionsEnCours);
    }
}