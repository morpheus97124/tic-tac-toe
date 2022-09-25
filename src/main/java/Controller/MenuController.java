package Controller;


import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.tinylog.Logger;


public class MenuController {

    @FXML
    private Button settingsButton;

    @FXML
    private Button quitGameButton;

    @FXML
    private VBox menuItems;

    @FXML
    private VBox settingsItems;

    @FXML
    private Spinner gridSizeXSpinner;

    @FXML
    private Spinner gridSizeYSpinner;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    GameSettings gameSettings = new GameSettings(GameSettings.DifficultyLevel.EASY, 3 ,3);

    @FXML
    public void initialize(){
        SpinnerValueFactory<Integer>spinnerValueFactoryX = new SpinnerValueFactory.IntegerSpinnerValueFactory(3,20,3);
        SpinnerValueFactory<Integer>spinnerValueFactoryY = new SpinnerValueFactory.IntegerSpinnerValueFactory(3,20,3);
        gridSizeXSpinner.setValueFactory(spinnerValueFactoryX);
        gridSizeYSpinner.setValueFactory(spinnerValueFactoryY);
    }

    @FXML
    private void quitGameAction(){
        Logger.info("The player has quit the game");
        Platform.exit();
        System.exit(0);
    }

    private void switchVBoxes(VBox vBox1, VBox vBox2){
        vBox1.setDisable(true);
        vBox1.setVisible(false);
        vBox2.setDisable(false);
        vBox2.setVisible(true);
    }

    @FXML
    private void settingsAction(){
        Logger.info("The player has pressed the settings button");
        switchVBoxes(menuItems,settingsItems);
    }

    @FXML
    private void backAction(){
        Logger.info("The player has returned to the main menu");
        Logger.info("New game settings are: \n" + gameSettings);
        gameSettings.setGridSizeX((Integer) gridSizeXSpinner.getValue());
        gameSettings.setGridSizeY((Integer) gridSizeYSpinner.getValue());
        switchVBoxes(settingsItems, menuItems);

        Logger.info("The player has returned to the main menu");
        Logger.info("New game settings are: \n" + gameSettings);
    }

    @FXML
    private void easyAction(){
        gameSettings.setDifficultyLevel(GameSettings.DifficultyLevel.EASY);
        easyButton.setDisable(true);
        mediumButton.setDisable(false);
        hardButton.setDisable(false);
    }

    @FXML
    private void mediumAction(){
        gameSettings.setDifficultyLevel(GameSettings.DifficultyLevel.MEDIUM);
        easyButton.setDisable(false);
        mediumButton.setDisable(true);
        hardButton.setDisable(false);
    }

    @FXML
    private void hardAction(){
        gameSettings.setDifficultyLevel(GameSettings.DifficultyLevel.HARD);
        easyButton.setDisable(false);
        mediumButton.setDisable(false);
        hardButton.setDisable(true);
    }
}
