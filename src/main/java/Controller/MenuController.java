package Controller;


import Model.Grid;
import Model.Tile;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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

    final int gameGridMax = 500;

    int cellSize;

    GameSettings gameSettings;

    Map<String,ImageView> imageViewMap = new HashMap<>();

    Image oImage = new Image("/Pictures/o.png");
    Image xImage = new Image("/Pictures/x.png");

    private boolean isGameOver = false;

    Grid grid = new Grid(3);

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
        grid = new Grid(gameSettings.getGridSize());
        setUpGrid();
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
                gameGrid.add(imageViewMap.get(ivKey),j,i);//(elem, oszlop, sor)
                imageViewMap.get(ivKey).setPickOnBounds(true);
                imageViewMap.get(ivKey).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        gameFlow(Integer.parseInt(ivKey.substring(2)), Tile.TileContent.X);

                    }
                });
                num++;
            }
        }
    }

    public void gameFlow(int id, Tile.TileContent tileContent){
        boolean opponentCanPick = true;
        if(!isGameOver && imageViewMap.get(String.format("iv%d",id)).getImage()!=oImage &&
                imageViewMap.get(String.format("iv%d",id)).getImage()!=xImage){
            //Player turn
            grid.setTile(id, tileContent);
            isGameOver = grid.isThereMatch(gameSettings.getStreakNumber());
            setCell(id,xImage);
            checkForWinner();
            if(!isGameOver && grid.howMany(Tile.TileContent.EMPTY)>0){
                //Computer turn
                instructionLabel.setText("It's my turn. Let me think for a moment....");
                setCell(grid.computerMove(gameSettings),oImage);
                instructionLabel.setText("Your turn. You are 'X'.");
                isGameOver = grid.isThereMatch(gameSettings.getStreakNumber());
                checkForWinner();
            }
        }
    }
    private void checkForWinner(){
        if(isGameOver){
            System.out.println(grid.getWinningTile());
            System.out.println(grid.getWinningTile());
            if(grid.getWinningTile()== Tile.TileContent.X){
                instructionLabel.setText("Congratulations! You won!");
                drawLine();
            }
            else if(grid.getWinningTile()== Tile.TileContent.O){
                instructionLabel.setText("Oh no... Maybe next time...");
                drawLine();
            }
            else{
                instructionLabel.setText("It's a tie!");
            }
            gameGrid.setDisable(true);
        }
    }

    private void drawLine(){
        int startX = this.grid.winLocation.getWinLocation()[0]*(cellSize)+cellSize/2;
        int startY = this.grid.winLocation.getWinLocation()[1]*(cellSize)+cellSize/2;
        int endX = this.grid.winLocation.getWinLocation()[2]*(cellSize)+cellSize/2;
        int endY = this.grid.winLocation.getWinLocation()[3]*(cellSize)+cellSize/2;
        Logger.info(String.format("Drew line at:\nstartX : %d\nstartY : %d\nendX : %d\nendY : %d",startX,startY,endX,endY));
        Line line = new Line(startX,startY,endX,endY);
        line.setStroke(Color.RED);
        line.setStrokeWidth(5.0);
        gamePane.getChildren().add(line);
    }
}
