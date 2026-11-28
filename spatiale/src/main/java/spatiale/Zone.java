package spatiale;

public class Zone {
    TypeTerrain type;
    int interet; // 0-10
    boolean preleve;
    boolean cartographie;
    boolean objectif;
    boolean aRisque;

    public Zone(TypeTerrain type, int interet, boolean preleve, boolean cartographie) {
        this.type = type;
        this.interet = interet;
        this.preleve = preleve;
        this.cartographie = cartographie;
        this.objectif = false;
        
    }

    public TypeTerrain getTypeTerrain() {
        return type;
    }

    public int getInteret() {
        return interet;
    }

    public boolean isPreleve() {
        return preleve;
    }

    public boolean isCartographie() {
        return cartographie;
    }

    public boolean isObjectif() {
        return objectif;
    }

    public boolean isARisque() {
        return aRisque;
    }

    public void setType(TypeTerrain type) {
        this.type = type;
    }

    public void setInteret(int interet) {
        this.interet = interet;
    }

    public void setPreleve(boolean preleve) {
        this.preleve = preleve;
    }

    public void setCartographie(boolean cartographie) {
        this.cartographie = cartographie;
    }

    public void setObjectif(boolean objectif) {
        this.objectif = objectif;
    }
}