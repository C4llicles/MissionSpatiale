package spatiale;

public class ExplorationPrelevement implements StrategieExploration {
    @Override
    public void explorer(CorpsCeleste corpsCeleste, int x, int y) {
        corpsCeleste.getCarte()[x][y].setPreleve(true);
    }   
}