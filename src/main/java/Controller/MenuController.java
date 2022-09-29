package Controller;


import Model.Grid;
import Model.Tile;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;


public class MenuController {

    final int gameGridMax = 500;

    int cellSize;

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

    @FXML
    private Spinner streakNumberSpinner;

    @FXML
    private Label infoLabel;


    GameSettings gameSettings;

    Map<String,ImageView> imageViewMap = new HashMap<>();

    Map<String,Button> buttonMap = new HashMap<>();

    Image oImage = new Image("/Pictures/o.png");
    Image xImage = new Image("/Pictures/x.png");

    private boolean isGameOver = false;

    Grid grid = new Grid(3);//TMP

    GameSettings.DifficultyLevel difficultyLevel = GameSettings.DifficultyLevel.EASY;


    @FXML
    public void initialize(){
        menuPane.setDisable(false);
        gamePane.setDisable(true);
        SpinnerValueFactory<Integer>gridSizeSpinnerValueFactory = new SpinnerValueFactory.
                IntegerSpinnerValueFactory(3,20,3);
        SpinnerValueFactory<Integer>streakNumberSpinnerValueFactory = new SpinnerValueFactory.
                IntegerSpinnerValueFactory(3,10,3);
        gridSizeSpinner.setValueFactory(gridSizeSpinnerValueFactory);
        streakNumberSpinner.setValueFactory(streakNumberSpinnerValueFactory);
        gameSettings = new GameSettings(GameSettings.DifficultyLevel.EASY, 3,3);
        instructionLabel.setText("Your turn. You are 'X'.");

    }

    @FXML
    private void newGameAction(){
        menuPane.setVisible(false);
        gamePane.setVisible(true);
        gamePane.setDisable(false);
        gameSettings = new GameSettings(difficultyLevel,
                (Integer) gridSizeSpinner.getValue(),
                (Integer) streakNumberSpinner.getValue());
        //Grid grid = new Grid(gameSettings.getGridSize());//NOT TMP
        grid = new Grid(gameSettings.getGridSize());

        setUpGrid();

        Platform.runLater(()->{
            gameGrid.getScene().setOnMouseClicked(e -> {
                /*if(!isGameOver){//Player turn
                    System.out.println(e.getSource());
                    isGameOver = grid.isThereMatch(3);
                }
                else{
                    System.out.println("GAME OVER, YOU WON THE GAME" + isGameOver);
                    instructionLabel.setText("GAME OVER, YOU WON THE GAME" + isGameOver);
                }
                if(!isGameOver){
                    //Computer turn
                    instructionLabel.setText("It's my turn. Let me think for a moment...." + isGameOver);
                    setCell(grid.computerMove(gameSettings),oImage);
                    //doit(1, Tile.TileContent.O);
                    //setCell(1,oImage);
                    instructionLabel.setText("Your turn. You are 'X'." + isGameOver);
                }*/

            });
        });
        infoLabel.setText(String.format("Grid size : %d*%d\nTarget streak : %d\nDifficulty : %s",
                gameSettings.getGridSize(),
                gameSettings.getGridSize(),
                gameSettings.getStreakNumber(),
                gameSettings.getDifficultyLevel()));

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
        difficultyLevel = GameSettings.DifficultyLevel.EASY;
        easyButton.setDisable(true);
        mediumButton.setDisable(false);
        hardButton.setDisable(false);
    }

    @FXML
    private void mediumAction(){
        difficultyLevel = GameSettings.DifficultyLevel.MEDIUM;
        easyButton.setDisable(false);
        mediumButton.setDisable(true);
        hardButton.setDisable(false);
    }

    @FXML
    private void hardAction(){
        difficultyLevel = GameSettings.DifficultyLevel.HARD;
        easyButton.setDisable(false);
        mediumButton.setDisable(false);
        hardButton.setDisable(true);
    }


    public void setCell(int id, Image image){
        Pair<Integer,Integer> coordinate = Grid.convertIdToCoordinate(id,gameSettings.getGridSize());
        //System.out.println(coordinate);
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

        cellSize = gameGridMax/gameSettings.getGridSize();

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

                imageViewMap.get(ivKey).setPickOnBounds(true);
                imageViewMap.get(ivKey).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        doit(Integer.parseInt(ivKey.substring(2)), Tile.TileContent.X);
                        if(imageViewMap.get(ivKey).getImage()==null && !isGameOver){
                            Platform.runLater(() -> {
                                //imageViewMap.get(ivKey).setImage(xImage);
                            });

                        }
                        //System.out.println(mouseEvent.getSource());
                    }
                });
                num++;
            }
        }
    }

    public void doit(int id, Tile.TileContent tileContent){

        boolean opponentCanPick = true;
        if(!isGameOver && imageViewMap.get(String.format("iv%d",id)).getImage()!=oImage &&
                imageViewMap.get(String.format("iv%d",id)).getImage()!=xImage){//Player turn
            grid.setTile(id, tileContent);
            //System.out.println(e.getSource());
            isGameOver = grid.isThereMatch(gameSettings.getStreakNumber());
            //System.out.println("isGameOver = " + isGameOver);
            setCell(id,xImage);

            checkForWinner();
            if(!isGameOver){

                //Computer turn
                instructionLabel.setText("It's my turn. Let me think for a moment...." + isGameOver);
                setCell(grid.computerMove(gameSettings),oImage);
                instructionLabel.setText("Your turn. You are 'X'." + isGameOver);
                isGameOver = grid.isThereMatch(gameSettings.getStreakNumber());
            }
            checkForWinner();

        }


    }
    private void checkForWinner(){
        if(isGameOver){
            if(grid.getWinningTile()== Tile.TileContent.X){
                instructionLabel.setText("Congratulations! You won!");
            }
            else if(grid.getWinningTile()== Tile.TileContent.O){
                instructionLabel.setText("Oh no... Maybe next time...");
            }

            int startX = this.grid.winLocation.getWinlocation()[0]*(cellSize)+cellSize/2;
            int startY = this.grid.winLocation.getWinlocation()[1]*(cellSize)+cellSize/2;
            int endX = this.grid.winLocation.getWinlocation()[2]*(cellSize)+cellSize/2;
            int endY = this.grid.winLocation.getWinlocation()[3]*(cellSize)+cellSize/2;
            System.out.println(String.format("startX : %d\nstartY : %d\nendX : %d\nendY : %d",startX,startY,endX,endY));
            Line line = new Line(startX,startY,endX,endY);
            line.setStroke(Color.RED);
            line.setStrokeWidth(5.0);
            gamePane.getChildren().add(line);
            gameGrid.setDisable(true);
        }
        
    }

}
