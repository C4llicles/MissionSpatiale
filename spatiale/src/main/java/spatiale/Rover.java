package spatiale;

public class Rover extends Engin {

    public Rover(int id, int nom) {
        super(id, nom, Etat.Disponible, new ExplorationPrelevement());
        setTypeDeplacement(TypeDeplacement.TERRESTRE);
    }
}