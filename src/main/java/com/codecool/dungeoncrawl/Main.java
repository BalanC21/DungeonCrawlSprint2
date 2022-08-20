package com.codecool.dungeoncrawl;
//Good

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.*;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    Scene scene;
    GameMap map = MapLoader.loadMap("/map.txt");
    //    GameMap emptyMap = MapLoader.loadMap("/emptyMap.txt");
    String ana = "/map.txt";
    boolean loadFromDB;
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
    Label inventory = new Label();
    String searchTerm;

    private GameDatabaseManager gameDatabaseManager;
    List<KeyCode> keyCodes = new ArrayList<>();
    GridPane ui = new GridPane();


    BorderPane upperPanel = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        gameDatabaseManager = new GameDatabaseManager();
        Button pickUpButton = new Button("Pick Up");
        pickUpButton.setFocusTraversable(false);


        //BorderPane for the pick up button
        upperPanel.setPadding(new Insets(5));
        upperPanel.setLeft(pickUpButton);

        //Add elements to Grid
        ui.add(upperPanel, 0, 0);
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(5, 5, 5, 5));
        ui.add(new Label("Health: "), 0, 1);
        ui.add(new Label("Attack"), 0, 2);
        ui.add(new Label("Inventory"), 0, 3);
        ui.add(healthLabel, 1, 1);
        ui.add(attackLabel, 1, 2);
        ui.add(inventory, 1, 3);
        ui.setVgap(5);


        pickUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cell playerCell = map.getPlayer().getCell();
                Cell selectedCell = map.getCell(playerCell.getX(), playerCell.getY());
                if (selectedCell.getType() == CellType.SWORD) {
                    map.getPlayer().addItem(ItemType.SWORD);
                    selectedCell.setType(CellType.FLOOR);
                } else if (selectedCell.getType() == CellType.KEY) {
                    map.getPlayer().addItem(ItemType.KEY);
                    selectedCell.setType(CellType.FLOOR);
                } else if (selectedCell.getType() == CellType.HEALTH) {
                    map.getPlayer().addItem(ItemType.HEALTH);
                    selectedCell.setType(CellType.FLOOR);
                }
                inventory.setText("" + map.getPlayer().getItemTypeList() + "\n");

            }
        });


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        scene = new Scene(borderPane);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");


        primaryStage.setScene(scene);
        refresh("");
        scene.setOnKeyPressed(keyEvent -> {
            try {
                onKeyPressed(keyEvent);
            } catch (InvocationTargetException | IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }


    public void openSavedGamePopUp(String input) {
        Stage openStage = new Stage();
        openStage.setTitle("Open");

        TilePane tilePane = new TilePane();
        Scene sc = new Scene(tilePane, 200, 200);
        sc.getRoot().setStyle("-fx-font-family: 'serif'");

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Open");
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want to open the game saved with this name?");
        alert.getDialogPane().setStyle("-fx-font-family: 'serif'");


        Optional<ButtonType> result = alert.showAndWait();

        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            loadFromDB = true;
//            this.searchTerm = new String(input);
            refresh(input);
            alert.hide();

            System.out.println("Ok pressed");
        } else {
            System.out.println("canceled");
        }
    }

    public void showStage(GameDatabaseManager gameDatabaseManager) {
        Stage newStage = new Stage();
        newStage.setTitle("Save");
        VBox comp = new VBox();
        GridPane ui = new GridPane();
        ui.setAlignment(Pos.CENTER_LEFT);

        ui.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid

        TextField nameField = new TextField("Name");
        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");


        ui.add(nameField, 0, 0);
        ui.add(saveBtn, 1, 0);
        ui.add(cancelBtn, 0, 1);

        comp.getChildren().add(ui);

        Scene stageScene = new Scene(comp, 300, 100);
        stageScene.getRoot().setStyle("-fx-font-family: 'serif'");

        newStage.setScene(stageScene);
        newStage.show();
        saveBtn.setOnAction(event -> {
            String input = String.valueOf(nameField.getText());
            System.out.println("first apperence of input  " + input);
            try {
                gameDatabaseManager.setup();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (gameDatabaseManager.checkGameExists(input)) {
                openSavedGamePopUp(input);
                System.out.println("Input before assigning to SearchTerm" + searchTerm);
            } else {
                gameDatabaseManager.saveGame(ana, map.getPlayer(), map.getEnemyList(), input, map.getMap());
            }

            newStage.hide();

        });
        cancelBtn.setOnAction(actionEvent -> {
            newStage.hide();

        });
    }


    private void onKeyPressed(KeyEvent keyEvent) throws InvocationTargetException, IllegalAccessException, SQLException {

        switch (keyEvent.getCode()) {
            case UP:
                getPlayerStats(0, -1);
                enemyAction(true);
                refresh("");
                break;
            case DOWN:
                getPlayerStats(0, 1);
                enemyAction(true);
                refresh("");
                break;
            case LEFT:
                getPlayerStats(-1, 0);
                enemyAction(true);
                refresh("");
                break;
            case RIGHT:
                getPlayerStats(1, 0);
                enemyAction(true);
                refresh("");
                break;
            case W:
                getPlayerStats(0, 0);
                map.getPlayer().attack();
                enemyAction(false);
                refresh("");
                break;
            case CONTROL:
                System.out.println("CONTROL start");
                keyCodes.add(KeyCode.CONTROL);
                System.out.println("Ana");
                System.out.println(keyCodes);
                System.out.println("CONTROL end");
                break;
            case S:
                System.out.println("S start");
                keyCodes.add(KeyCode.S);
                System.out.println(keyCodes);
                if (keyCodes.contains(KeyCode.CONTROL)) {
                    if (keyCodes.contains(KeyCode.S)) {
                        System.out.println("control and s");
                        showStage(gameDatabaseManager);
                        keyCodes.clear();
                    }
                }
                System.out.println("S end");
                break;

        }
    }

    private void getPlayerStats(int dx, int dy) {
        refresh("");
        map.getPlayer().modifyPlayerStats();
        map.getPlayer().lootEnemy();
        map.getPlayer().move(dx, dy);
        map.getPlayer().getNextMap();
    }

    private void openSavedGame(String input) {
        System.out.println("enters DB");
        ana = "/emptyMap.txt";
        map = MapLoader.loadMap("/emptyMap.txt");
        System.out.println("Search term to be searched  " + input);
        int playerId = gameDatabaseManager.getPlayerId(input);
        int game_id = gameDatabaseManager.getGameModel(input).getGameId();


        System.out.println("the game id is:  " + game_id);
        System.out.println("playerId" + playerId);


        // add player on mp
        PlayerModel playerModel = gameDatabaseManager.getPlayerModel(playerId);
        Cell playerCell = map.getCell(playerModel.getX(), playerModel.getY());
        playerCell.setType(CellType.PLAYER);
        Player player = new Player(playerCell);
        map.setPlayer(player);
        player.setHealth(playerModel.getHp());
        player.setAttack(playerModel.getAttack());
        player.setItemTypeList(playerModel.getItemTypeList());


        //add enemies on map
        List<EnemyModel> enemyModel = gameDatabaseManager.getEnemiesDao(game_id);
        List<Enemy> enemyList = new ArrayList<>();
        for (EnemyModel enemy : enemyModel) {
            Cell enemyCell = map.getCell(enemy.getX(), enemy.getY());
            String enemyType = enemy.getEnemyType();
            int health = enemy.getHp();
            switch (enemyType) {
                case "skeleton":
                    enemyCell.setType(CellType.SKELETON);
                    Skeleton skeleton = new Skeleton(enemyCell);
                    skeleton.setHealth(health);
                    enemyList.add(skeleton);
                    break;
                case "archer":
                    enemyCell.setType(CellType.ARCHER);
                    Archer archer = new Archer(enemyCell);
                    archer.setHealth(health);
                    enemyList.add(archer);
                    break;
                case "sentinel":
                    enemyCell.setType(CellType.SENTINEL);
                    Sentinel sentinel = new Sentinel(enemyCell);
                    sentinel.setHealth(health);
                    enemyList.add(sentinel);
                    break;
            }
        }
        map.setEnemyList(enemyList);


        // add items on map
        List<ItemModel> itemModels = gameDatabaseManager.getItemModels(game_id);
        for (ItemModel item : itemModels) {
            Cell itemCell = map.getCell(item.getX(), item.getY());
            String enemyType = item.getType();
            switch (enemyType) {
                case "key":
                    itemCell.setType(CellType.KEY);
                    break;
                case "sword":
                    itemCell.setType(CellType.SWORD);
                    break;
                case "health":
                    itemCell.setType(CellType.HEALTH);
                    break;
            }
        }
        loadFromDB = false;
        scene.setOnKeyPressed(keyEvent -> {
            try {
                onKeyPressed(keyEvent);
            } catch (InvocationTargetException | IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void refresh(String input) {

        if (Player.newMap) {
            ana = "/map2.txt";
            map = MapLoader.loadMap("/map2.txt");
            Player.newMap = false;
            scene.setOnKeyPressed(keyEvent -> {
                try {
                    onKeyPressed(keyEvent);
                } catch (InvocationTargetException | IllegalAccessException | SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if (loadFromDB) {
            openSavedGame(input);
        }

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        attackLabel.setText("" + map.getPlayer().getAttack());
        inventory.setText("" + map.getPlayer().getItemTypeList());
    }

    private void enemyAction(boolean doSomething) {
        List<Enemy> enemyList = map.getEnemyList();
        for (Enemy enemy : enemyList) {
            if (!enemy.isCharacterAlive()) {
                enemy.attack();
                if (doSomething && !enemy.isCharacterAlive()){
                    enemy.move(Util.getRandomInt(), Util.getRandomInt());}
            }
        }
    }
}
