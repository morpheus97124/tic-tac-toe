import Controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.tinylog.Logger;

public class JavaFXApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Logger.info("The application has launched");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/Menu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Tic-Tac-Toe");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/Pictures/x.png"));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Logger.info("The player has quit the game");
    }
}
