package spatiale;

public class ExplorationCartographie implements StrategieExploration {
    @Override
    public void explorer(CorpsCeleste corpsCeleste, int x, int y) {
        corpsCeleste.getCarte()[x][y].setCartographie(true);
    }   
}