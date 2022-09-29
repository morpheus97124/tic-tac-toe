package Model;

import Controller.GameSettings;
import Controller.MenuController;
import javafx.util.Pair;
import org.tinylog.Logger;

import java.lang.reflect.Array;
import java.util.Random;

public class Grid {
    private int n;
    private Tile.TileContent winningTile = Tile.TileContent.EMPTY;
    Tile[] grid;
    public class WinLocation{
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public WinLocation(Integer[] coordinates){
            this.startX = coordinates[0];
            this.startY = coordinates[1];
            this.endX = coordinates[2];
            this.endY = coordinates[3];
            System.out.println(String.format("startX : %d\nstartY : %d\nendX : %d\nendY : %d",coordinates[0],coordinates[1],coordinates[2],coordinates[3]));
        }
        public static Integer[] doCalc(int id, int n, int target, Direction direction){
            int endX = convertIdToCoordinate(id, n).getValue();
            int endY = convertIdToCoordinate(id, n).getKey();
            int startX;
            int startY;
            switch (direction){
                case HORIZONTAL :
                    startX = endX - (target - 1);
                    startY = endY;
                    break;
                case VERTICAL :
                    startX = endX;
                    startY = endY + (target - 1);
                    break;
                case DEGREE45 :
                    startX = endX - (target - 1);
                    startY = endY + (target - 1);
                    break;
                case DEGREE135 :
                    startX = endX + (target - 1);
                    startY = endY + (target - 1);
                    break;
                default :
                    startX = endX;
                    startY = endY;
            }
            Integer[] winLocation = new Integer[] {startX, startY, endX, endY};
            return winLocation;
        }

        public enum Direction{
            HORIZONTAL, VERTICAL, DEGREE45, DEGREE135;
        }

        public Integer[] getWinlocation(){
            Integer[] winLocation = new Integer[] {this.startX, this.startY, this.endX, this.endY};
            return winLocation;
        }

    }
    public WinLocation winLocation;

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
        for(int i = 0; i < this.grid.length;i++){
            Tile.TileContent currentTileContent = Tile.TileContent.EMPTY;
            if(this.grid[i].getTileContent() == Tile.TileContent.O){
                currentTileContent = Tile.TileContent.O;
            }
            else if(this.grid[i].getTileContent() == Tile.TileContent.X){
                currentTileContent = Tile.TileContent.X;
            }
            else{
                continue;
            }
            System.out.println(currentTileContent);
            //vízszintesen
            int count = 1;
            int iCopy=i;
            while(whoRight(iCopy)== currentTileContent){
                count++;
                iCopy++;
                if(count >= target){
                    setWinningTile(currentTileContent);
                    winLocation = new WinLocation(WinLocation.doCalc(iCopy, this.n, target, WinLocation.Direction.HORIZONTAL));
                    return true;
                }
            }
            //függőlegesen
            count = 1;
            iCopy = i;
            while(whoTop(iCopy)==currentTileContent){
                count++;
                iCopy+=this.n;
                if(count >= target){
                    setWinningTile(currentTileContent);
                    winLocation = new WinLocation(WinLocation.doCalc(iCopy, this.n, target, WinLocation.Direction.VERTICAL));
                    return true;
                }
            }
            //ha így van \
            count = 1;
            iCopy=i;
            while(whoTopLeft(iCopy)==currentTileContent){
                count++;
                iCopy+=this.n-1;
                if(count >= target){
                    setWinningTile(currentTileContent);
                    winLocation = new WinLocation(WinLocation.doCalc(iCopy, this.n, target, WinLocation.Direction.DEGREE135));
                    return true;
                }
            }
            //ha így van /
            count = 1;
            iCopy = i;
            while(whoTopRight(iCopy)==currentTileContent){
                count++;
                iCopy+=this.n+1;
                if(count >= target){
                    setWinningTile(currentTileContent);
                    winLocation = new WinLocation(WinLocation.doCalc(iCopy, this.n, target, WinLocation.Direction.DEGREE45));
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
    public Tile.TileContent whoBottom(int id){
        if(id/n==0){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-this.n].getTileContent();
    }
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
    public Tile.TileContent whoBottomLeft(int id){
        if(whoLeft(id)== Tile.TileContent.BORDER || whoBottom(id)== Tile.TileContent.BORDER){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-n-1].getTileContent();
    }
    public Tile.TileContent whoBottomRight(int id){
        if(whoBottom(id)== Tile.TileContent.BORDER || whoRight(id)== Tile.TileContent.BORDER){
            return Tile.TileContent.BORDER;
        }
        return this.grid[id-n+1].getTileContent();
    }

    public void setTile(int id, Tile.TileContent tileContent){
        if(tileContent != Tile.TileContent.BORDER && this.grid[id].getTileContent()== Tile.TileContent.EMPTY){
            this.grid[id].setTileContent(tileContent);
            Logger.info("this.grid have changed");
            System.out.println("this.grid have changed");
            //System.out.println(this);
            System.out.println("Tile[" + convertIdToCoordinate(id,this.n) + "] with id-" + id + " has been set to "+ tileContent);
            //whoIsAroundMe(id);
        }
    }

    public Integer computerMove(GameSettings gameSettings){
        System.out.println(gameSettings.getDifficultyLevel());
        switch (gameSettings.getDifficultyLevel()){
            case EASY : return easyMove();
            case MEDIUM : return mediumMove();
            default : return n;//TODO
        }
    }

    private Integer easyMove(){
        Random random = new Random();
        int r = random.nextInt((this.n*this.n)-1);
        while(this.grid[r].getTileContent()!= Tile.TileContent.EMPTY){
            r = random.nextInt((this.n*this.n)-1);
        }
        setTile(r, Tile.TileContent.O);
        return r;
        //need to send back info to change the image
    }

    private Integer mediumMove(){
        //System.out.println("MEDIUM MOVE XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        int maxStreak = 0;
        int tmpStreak = 1;
        int maxId = 0;
        if(howMany(Tile.TileContent.O)==0){
            return easyMove();
        }
        for (int i = 0;i < this.grid.length;i++){
            System.out.println("i = " + i + "------------------------------------------------");
            if(this.grid[i].getTileContent() == Tile.TileContent.O){//bele se megy
                int iCopy = i;
                while(whoRight(iCopy)== Tile.TileContent.O){//check for horizontal
                    System.out.println("whoRightWhileStart iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                    tmpStreak++;
                    iCopy++;
                    System.out.println("whoRightWhileEnd iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                }
                if(maxStreak < tmpStreak) {
                    System.out.println("whoRightIfStart iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                    whoIsAroundMe(iCopy);
                    if (whoRight(iCopy) == Tile.TileContent.EMPTY) {
                        System.out.println("whoRightIfIfStart iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                        maxStreak = tmpStreak;
                        maxId = iCopy + 1;
                    }
                    else if (whoLeft(iCopy - tmpStreak + 1) == Tile.TileContent.EMPTY) {//ez lesz a baj
                        maxStreak = tmpStreak;
                        maxId = iCopy - tmpStreak;
                    }
                }
                tmpStreak = 1;
                iCopy = i;

                while(whoTop(iCopy)== Tile.TileContent.O){//check for vertical
                    System.out.println("whoTopWhile iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                    tmpStreak++;
                    iCopy+=this.n;
                }
                if(maxStreak < tmpStreak) {
                    if (whoTop(iCopy) == Tile.TileContent.EMPTY) {
                        maxStreak = tmpStreak;
                        maxId = iCopy + this.n;
                    }
                    else if (whoBottom((iCopy-(tmpStreak-1)*this.n)) == Tile.TileContent.EMPTY) {
                        maxStreak = tmpStreak;
                        maxId = iCopy - (tmpStreak*this.n);
                    }
                }
                tmpStreak = 1;
                iCopy = i;
                while(whoTopLeft(iCopy)== Tile.TileContent.O){//check for \
                    System.out.println("whoTopLeftWhile iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                    tmpStreak++;
                    iCopy+=(this.n-1);
                }
                if(maxStreak < tmpStreak) {
                    if (whoTopLeft(iCopy) == Tile.TileContent.EMPTY) {
                        maxStreak = tmpStreak;
                        maxId = iCopy + this.n - 1;
                    }
                    else if (whoBottomRight(iCopy - (tmpStreak-1)*this.n+(tmpStreak-1)) == Tile.TileContent.EMPTY) {
                        maxStreak = tmpStreak;
                        maxId = iCopy - (tmpStreak*this.n) + tmpStreak;
                    }
                }
                tmpStreak = 1;
                iCopy = i;
                while(whoTopRight(iCopy)== Tile.TileContent.O){//check for /x0x0
                    System.out.println("whoTopRightWhileStart iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                    tmpStreak++;
                    iCopy+=(this.n+1);
                    System.out.println("whoTopRightWhileEnd iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);

                }
                if(maxStreak < tmpStreak) {

                    if (whoTopRight(iCopy) == Tile.TileContent.EMPTY) {
                        System.out.println("whoTopRightIfIf iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                        maxStreak = tmpStreak;
                        maxId = iCopy + this.n + 1;
                    }
                    else if (whoBottomLeft(iCopy - (tmpStreak-1)*this.n -(tmpStreak-1)) == Tile.TileContent.EMPTY) {
                        System.out.println("whoTopRightIfElseif iCopy : " + iCopy + ", tmpStreak : " + tmpStreak + " maxId : " + maxId);
                        maxStreak = tmpStreak;
                        maxId = iCopy - (tmpStreak*this.n) - tmpStreak;
                    }
                }
                }
            }
        if(this.grid[maxId].getTileContent() == Tile.TileContent.EMPTY){
            setTile(maxId, Tile.TileContent.O);
            return maxId;
        }
        else{
            return easyMove();
        }
    }
    private void whoIsAroundMe(int id){
        System.out.println(String.format("whoLeft -> %s\nwhoTopLeft -> %s\nwhoTop -> %s\nwhoTopRight -> %s\nwhoRight -> %s",whoLeft(id),whoTopLeft(id),whoTop(id),whoTopRight(id),whoRight(id)));
    }

    private void setWinningTile(Tile.TileContent tileContent){
        winningTile = tileContent;
    }

    public Tile.TileContent getWinningTile(){
        return winningTile;
    }

    public Integer howMany(Tile.TileContent tileContent){
        int count = 0;
        for(Tile tile : this.grid){
            if(tile.getTileContent() == tileContent){
                count++;
            }
        }
        return count;
    }
}
