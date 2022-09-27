package Controller;


import Model.Grid;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;


public class MenuController {

    @FXML
    private Pane menuPane;

    @FXML
    private Pane gamePane;

    @FXML
    private Button settingsButton;

    @FXML
    private Button quitGameButton;

    @FXML
    private VBox menuItems;

    @FXML
    private VBox settingsItems;

    @FXML
    private Spinner gridSizeSpinner;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label instructionLabel;


    GameSettings gameSettings;

    Map<String,ImageView> imageViewMap = new HashMap<>();

    Map<String,Button> buttonMap = new HashMap<>();

    Image oImage = new Image("/Pictures/o.png");
    Image xImage = new Image("/Pictures/x.png");

    private boolean isGameOver = false;


    @FXML
    public void initialize(){
        menuPane.setDisable(false);
        gamePane.setDisable(true);
        SpinnerValueFactory<Integer>spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3,20,3);
        gridSizeSpinner.setValueFactory(spinnerValueFactory);
        gameSettings = new GameSettings(GameSettings.DifficultyLevel.EASY, 3);
        instructionLabel.setText("Your turn. You are 'X'.");

    }

    @FXML
    private void newGameAction(){
        menuPane.setVisible(false);
        gamePane.setVisible(true);
        gamePane.setDisable(false);
        gameSettings = new GameSettings(GameSettings.DifficultyLevel.EASY, (Integer) gridSizeSpinner.getValue());
        Grid grid = new Grid(gameSettings.getGridSize());
        setUpGrid();

        Platform.runLater(()->{
            gameGrid.getScene().setOnMouseClicked(e -> {
                if(!isGameOver){
                    System.out.println("You clicked");
                    instructionLabel.setText("Your turn. You are 'X'.");
                    System.out.println(e.getSource());
                }
            });
            /*gameGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {//works, looking for a better solution
                double x = e.getX();
                double y = e.getY();
                System.out.println("[" + x + ", " + y + "]");
            });*/
        });


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
        gameSettings.setGridSize((Integer) gridSizeSpinner.getValue());
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


    public void setCell(int id, Image image){
        Pair<Integer,Integer> coordinate = Grid.convertIdToCoordinate(id,gameSettings.getGridSize());
        System.out.println(coordinate);
        int y = coordinate.getKey();
        int x = coordinate.getValue();
        String nameString = String.format("iv%d",id);
        for(String string : imageViewMap.keySet()){
            if(string.equals(nameString)){
                imageViewMap.get(string).setImage(image);
            }
        }
        Logger.info(String.format("Cell has been updated at [%d,%d]",x,y));
    }

    private void setUpGrid(){

        int maxSize = 500;
        int cellSize = maxSize/gameSettings.getGridSize();

        gameGrid.getRowConstraints().set(0,new RowConstraints(cellSize));
        for(int i=0;i<gameSettings.getGridSize()-1;i++){
            RowConstraints rowConstraints = new RowConstraints(cellSize);
            gameGrid.getRowConstraints().add(rowConstraints);
        }
        for(int j=0;j<gameSettings.getGridSize();j++){
            ColumnConstraints columnConstraints = new ColumnConstraints(cellSize);
            gameGrid.getColumnConstraints().add(columnConstraints);
        }
        int num = 0;
        for(int i = gameSettings.getGridSize()-1;i> -1;i--){
            for(int j = 0;j< gameSettings.getGridSize();j++){
                String ivKey = String.format("iv%d",num);
                ImageView imageView = new ImageView();
                imageView.setFitWidth(cellSize);
                imageView.setFitHeight(cellSize);
                imageViewMap.put(ivKey,imageView);
                gameGrid.add(imageViewMap.get(ivKey),j,i);//(elem, col, row)
                //TEMPORARY CODE STARTS //DOESNT WORK FOR SOME REASON
                imageViewMap.get(ivKey).setPickOnBounds(true);
                imageViewMap.get(ivKey).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        imageViewMap.get(ivKey).setImage(xImage);
                    }
                });

                /*String btnKey = String.format("btn%d",num);
                Button button = new Button();
                button.setMinWidth(cellSize);
                button.setMaxWidth(cellSize);
                button.setMinHeight(cellSize);
                button.setMaxHeight(cellSize);
                buttonMap.put(btnKey,button);
                gameGrid.add(buttonMap.get(btnKey),j,i);
                buttonMap.get(btnKey).setText("EMPTY");
                buttonMap.get(btnKey).setDisable(false);
                buttonMap.get(btnKey).setVisible(true);
                buttonMap.get(btnKey).setViewOrder(0);
                buttonMap.get(btnKey).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Click");
                    }
                });*/
                /*buttonMap.get(btnKey).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("Click");
                    }
                });*/
                //TEMPORARY CODE END
                num++;
                //Logger.info(String.format("ImageView added to the grid at [%d,%d]",j,i));
            }
        }
        setCell(0,oImage);
        /*setCell(1,xImage);
        setCell(2,oImage);
        setCell(3,xImage);
        setCell(4,oImage);
        setCell(5,xImage);
        setCell(6,oImage);
        setCell(7,xImage);*/

    }

}
