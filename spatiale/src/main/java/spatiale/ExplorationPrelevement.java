package spatiale;

public class ExplorationPrelevement implements StrategieExploration {
    @Override
    public void explorer(CorpsCeleste corpsCeleste, int x, int y) {
        java.util.Objects.requireNonNull(corpsCeleste);
        if (x < 0 || y < 0 || x >= corpsCeleste.getLongueur() || y >= corpsCeleste.getLargeur()) {
            throw new IllegalArgumentException("Les coordonnées sont en dehors des limites du corps céleste.");
        }
        if (corpsCeleste.getCarte()[x][y].isPreleve()) {
            throw new IllegalStateException("La zone a déjà été prélevée.");
        }
        corpsCeleste.getCarte()[x][y].setPreleve(true);
    }   
}