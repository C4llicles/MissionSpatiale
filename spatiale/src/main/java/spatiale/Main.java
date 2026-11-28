package spatiale;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int missionId = 1;
        Random random = new Random();
        CorpsCeleste mars = new CorpsCeleste(missionId, "Mars", 5, 5, TypeMonde.ROCHEUSE);

        Directeur directeur = new Directeur("Martin", "Alex", Personne.Genre.Autre, "0000000000");
        Operateur operateur = new Operateur(
                "Durand", "Camille", Personne.Genre.Autre, "0000000000",
                Set.of(Competence.PILOTAGE_ROVER, Competence.GEOLOGIE));
        Operateur operateur2 = new Operateur(
                "Dupont", "Julien", Personne.Genre.Autre, "0000000000",
                Set.of(Competence.PILOTAGE_ROVER, Competence.ENERGIE));
        Operateur operateur3 = new Operateur(
                "Lefevre", "Sophie", Personne.Genre.Autre, "0000000000",
                Set.of(Competence.PILOTAGE_ROVER, Competence.TELECOMMUNICATIONS));
        Operateur operateurMaintenance = new Operateur(
                "Moreau", "Lucas", Personne.Genre.Autre, "0000000000",
                Set.of(Competence.MAINTENANCE, Competence.PILOTAGE_ROVER));
        Operateur piloteDrone = new Operateur(
                "Bernard", "Ines", Personne.Genre.Femme, "0000000000",
                Set.of(Competence.PILOTAGE_DRONE, Competence.TELECOMMUNICATIONS));
        Operateur piloteDrone2 = new Operateur(
                "Garcia", "Noah", Personne.Genre.Homme, "0000000000",
                Set.of(Competence.PILOTAGE_DRONE, Competence.GEOLOGIE));

        new Cadet("Petit", "Emma", Personne.Genre.Femme, "0000000000", operateur);
        new Cadet("Rousseau", "Maxime", Personne.Genre.Homme, "0000000000", operateurMaintenance);

        List<Engin> engins = List.of(
                new Rover(1, 1),
                new Rover(2, 2),
                new Rover(3, 3),
                new Drone(4, 4),
                new Drone(5, 5));
        for (int i = 0; i < engins.size(); i++) {
            Engin engin = engins.get(i);
            engin.setPosition(List.of(i, 0));
            engin.assignerMission(missionId);
            engin.setEtat(Etat.EnMission);
        }

        String nomMission = "Exploration de Mars";
        Mission mission = new Mission(
                missionId,
                nomMission,
                mars,
                LocalDate.now(),
                100_000,
                directeur,
                List.of(
                        operateur,
                        operateur2,
                        operateur3,
                        operateurMaintenance,
                        piloteDrone,
                        piloteDrone2),
                engins);

        mission.genererZonesObjectifs(mars.getLongueur(), mars.getLargeur(), random, mars);
        
        afficher("Démarrage de la mission \"" + nomMission + "\".");
        mission.demarrerMission();
    }

    public static void afficher(String message) {
        if (!Config.AFFICHER_MESSAGES) {
            throw new IllegalStateException(message);
        }
        System.out.println(message);
    }
}