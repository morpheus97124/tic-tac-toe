package Model;

public class Tile {
    public enum TileContent{
        EMPTY, X, O;
    }

    private TileContent tileContent;

    public Tile(TileContent tileContent) {
        this.tileContent = tileContent;
    }

    public TileContent getTileContent() {
        return tileContent;
    }

    public void setTileContent(TileContent tileContent) {
        this.tileContent = tileContent;
    }

    public String toString(){
        return "Content: " + this.tileContent + "\n";
    }
}
