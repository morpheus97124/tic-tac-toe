package Controller;

public class GameSettings {
    public enum DifficultyLevel{
        EASY, MEDIUM, HARD;
    }

    private DifficultyLevel difficultyLevel;
    private int gridSize;

    public GameSettings(DifficultyLevel difficultyLevel, int gridSize) {
        this.difficultyLevel = difficultyLevel;
        this.gridSize = gridSize;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public String toString(){
        return "Game difficulty " + this.difficultyLevel
        + "\nGrid size: " + this.gridSize + "x" + this.gridSize;
    }
}
