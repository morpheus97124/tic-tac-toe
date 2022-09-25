package Controller;

public class GameSettings {
    public enum DifficultyLevel{
        EASY, MEDIUM, HARD;
    }

    private DifficultyLevel difficultyLevel;
    private int gridSizeX;
    private int gridSizeY;

    public GameSettings(DifficultyLevel difficultyLevel, int gridSizeX, int gridSizeY) {
        this.difficultyLevel = difficultyLevel;
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public void setGridSizeX(int gridSizeX) {
        this.gridSizeX = gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeY(int gridSizeY) {
        this.gridSizeY = gridSizeY;
    }

    public String toString(){
        return "Game difficulty " + this.difficultyLevel
        + "\nGrid size: " + this.gridSizeX + "x" + this.gridSizeY;
    }
}
