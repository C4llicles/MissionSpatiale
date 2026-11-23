package spatiale;

public class Zone {
    TypeTerrain type;
    int interet; // 0-10
    boolean preleve;
    boolean cartographie;

    public Zone(TypeTerrain type, int interet, boolean preleve, boolean cartographie) {
        this.type = type;
        this.interet = interet;
        this.preleve = preleve;
        this.cartographie = cartographie;
    }
}