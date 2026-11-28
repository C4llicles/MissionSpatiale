package spatiale;

import java.util.List;
import jakarta.persistence.*;

public class DepotRapports implements AutoCloseable {
    private final EntityManagerFactory emf;

    public DepotRapports() {
        emf = Persistence.createEntityManagerFactory("spatialePU"); // le nom doit correspondre à celui dans persistence.xml, ligne 8
    }

    public void enregistrer(RapportMission rapport) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rapport);
        em.getTransaction().commit();

        em.close();
    }

    public List<RapportMission> trouverTousLesRapports() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RapportMission> requete = em.createQuery(
            "SELECT r FROM rapportMission r", 
            RapportMission.class);

        return requete.getResultList();
    }

    // celle là prend au paramètre un corps celeste, et la suivante une chaîne de caractères avec un nom
    public List<RapportMission> trouverRapportsCorpsCeleste(CorpsCeleste cpsCel) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RapportMission> requete = em.createQuery(
            "SELECT r FROM rapportMission r WHERE r.nom = :nom", 
            RapportMission.class);
        requete.setParameter("nom", cpsCel.getNom());

        return requete.getResultList();
    }
    public List<RapportMission> trouverRapportsCorpsCeleste(String nom) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RapportMission> requete = em.createQuery(
            "SELECT r FROM rapportMission r WHERE r.nom = :nom", 
            RapportMission.class);
        requete.setParameter("nom", nom);

        return requete.getResultList();
    }

    @Override
    public void close() {
        emf.close(); // fermeture de la fabrique pour qu'aucun thread ne tourne après la fin du programme, comme demandé dans l'énoncé
    }
}
