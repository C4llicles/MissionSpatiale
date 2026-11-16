package spatiale;

public class Drone extends Engin {

    public Drone(int id, int nom) {
        super(id, nom, Etat.Disponible, new ExplorationCartographie());
    }
}