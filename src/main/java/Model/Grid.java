package Model;

import javafx.util.Pair;
import org.tinylog.Logger;

public class Grid {
    private int n;

    Tile[] grid;

    public Grid(int n) {
        this.n = n;
        this.grid = new Tile[n*n];
        for(int i=0;i<n*n;i++){
            grid[i] = new Tile(Tile.TileContent.EMPTY);
        }
        Logger.info("Grid is created\n" + this);
    }

    public static Pair<Integer,Integer> convertIdToCoordinate(int id, int n){
        int x = (n-1)-(id/n);
        int y = id%n;
        Pair<Integer,Integer> coordinate = new Pair(x,y);
        return coordinate;
    }

    public static int convertCoordinateToId(Pair<Integer,Integer> coordinate,int n){
        int x = coordinate.getValue();
        int y = coordinate.getKey();
        return (n-1-y)*n+x;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Size of the grid is: " + this.n + "x" + this.n + "\n");
        for (Tile tile:
             grid) {
            sb.append(tile);
        }
        return sb.toString();
    }
    
}
