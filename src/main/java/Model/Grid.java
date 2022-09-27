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
        for (Tile tile : this.grid) {
            sb.append(tile);
        }
        return sb.toString();
    }

    public boolean isThereMatch(int target){
        //be kéne járni a gridet bal alsó sarokból-n borderrel
        //minden not empty cella esetében megvizsgálni a négy directiont
        //ha nincs meg továbmenni
        //ha megvan kilépni
        for(int i = 0; i < this.grid.length;i++){
        //for(Tile tile : this.grid){//bejárni a gridet
            //bejárni a vízszintesen
            //bejárni függőlegesen
            //bejárni így \
            //bejárni így /
            Tile.TileContent currentTileContent = null;
            if(this.grid[i].getTileContent() == Tile.TileContent.O){
                currentTileContent = Tile.TileContent.O;
            }
            else if(this.grid[i].getTileContent() == Tile.TileContent.X){
                currentTileContent = Tile.TileContent.X;
            }
            else{
                continue;
            }
            //vízszintesen
            int count = 1;
            while(whoRight(i)== currentTileContent){
                count++;
                if(count >= target){
                    return true;
                }
            }
            //függőlegesen
            count = 1;
            while(whoTop(i)==currentTileContent){
                count++;
                if(count >= target){
                    return true;
                }
            }
            //ha így van \
            count = 1;
            while(whoTopLeft(i)==currentTileContent){
                count++;
                if(count >= target){
                    return true;
                }
            }
            //ha így van /
            count = 1;
            while(whoTopRight(i)==currentTileContent){
                count++;
                if(count >= target){
                    return true;
                }
            }
        }
        return false;
    }

    //Functions for returning the types of neighboring tiles
    public Tile.TileContent whoLeft(int id){
        if(id%this.n==0) {//Tile is at the far left side
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-1].getTileContent();
    }
    public Tile.TileContent whoRight(int id){
        if(id%this.n==(this.n-1)){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id+1].getTileContent();
    }
    public Tile.TileContent whoTop(int id){
        if(id/this.n==this.n-1){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id+this.n].getTileContent();
    }
    /*public Tile.TileContent whoBottom(int id){
        if(id/n==0){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-this.n].getTileContent();
    }*/
    public Tile.TileContent whoTopLeft(int id){
        if(whoLeft(id)== Tile.TileContent.BORDER || whoTop(id)== Tile.TileContent.BORDER) {
            return Tile.TileContent.BORDER;
        }
        return this.grid[id+this.n-1].getTileContent();
    }
    public Tile.TileContent whoTopRight(int id){
        if(whoRight(id)==Tile.TileContent.BORDER || whoTop(id)== Tile.TileContent.BORDER){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id+this.n+1].getTileContent();
    }
    /*public Tile.TileContent whoBottomLeft(int id){
        if(whoLeft(id)== Tile.TileContent.BORDER || whoBottom(id)== Tile.TileContent.BORDER){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-n-1].getTileContent();
    }*/
    /*public Tile.TileContent whoBottomRight(int id){
        if(whoBottom(id)== Tile.TileContent.BORDER || whoRight(id)== Tile.TileContent.BORDER){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-n+1].getTileContent();
    }*/

    public void setTile(int id, Tile.TileContent tileContent){
        if(tileContent != Tile.TileContent.BORDER){
            this.grid[id].setTileContent(tileContent);
        }
    }
}
