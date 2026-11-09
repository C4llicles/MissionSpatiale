package spatiale;

import java.util.concurrent.atomic.AtomicLong;

abstract class Personne {
    public enum Genre {
        Femme,
        Homme,
        Autre
    }

    private static final AtomicLong COMPTEUR = new AtomicLong(0);
    
    private final long id;
    private String nom;
    private String prenom;
    private Genre genre;
    private String telephone;

    Personne(String nom, String prenom, Genre genre, String telephone) {
        this.id = COMPTEUR.incrementAndGet();
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.telephone = telephone;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }

    public void setPrenom(String newPnom) {
        this.prenom = newPnom;
    }

    public void setGenre(Genre newGenre) {
        this.genre = newGenre;
    }

    public void setTelephone(String newTel) {
        this.telephone = newTel;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTelephone() {
        return telephone;
    }
}