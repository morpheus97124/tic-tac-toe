package Controller;

/**
 * Class for storing info about the customizable aspects of the game
 */
public class GameSettings {
    public enum DifficultyLevel{
        EASY, MEDIUM, HARD;
    }

    private DifficultyLevel difficultyLevel;
    private int gridSize;
    private int streakNumber;

    public GameSettings(DifficultyLevel difficultyLevel, int gridSize, int streakNumber) {
        this.difficultyLevel = difficultyLevel;
        this.gridSize = gridSize;
        this.streakNumber = streakNumber;
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

    public void setStreakNumber(int streakNumber){
        this.streakNumber = streakNumber;
    }
    public int getStreakNumber(){
        return this.streakNumber;
    }

    public String toString(){
        return "Game difficulty " + this.difficultyLevel
        + "\nGrid size: " + this.gridSize + "x" + this.gridSize;
    }
}
