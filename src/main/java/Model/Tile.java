package Model;

/**
 * Class for holding the Tile objects, representing the cells of the game table.
 */
public class Tile {
    public enum TileContent{
        EMPTY, X, O, BORDER;
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
