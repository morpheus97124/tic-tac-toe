package Controller;


import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.awt.event.ActionEvent;



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
        switchVBoxes(settingsItems, menuItems);
    }
}
