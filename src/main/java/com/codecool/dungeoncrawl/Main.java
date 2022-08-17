package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    Scene scene;
    GameMap map = MapLoader.loadMap("/map.txt");
    String mapName = "/map.txt";
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackLabel = new Label();
    Label inventory = new Label();
    Button button = new Button("Pick Up");
    private GameDatabaseManager gameDatabaseManager;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameDatabaseManager = new GameDatabaseManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(new Label("Health: "), 0, 1);
        ui.add(new Label("Attack"), 0, 2);
        ui.add(new Label("Inventory"), 0, 3);
        ui.add(healthLabel, 1, 1);
        ui.add(attackLabel, 1, 2);
        ui.add(inventory, 1, 3);
        ui.add(button, 0, 0);
        button.setFocusTraversable(false);

        button.setOnAction(new EventHandler<ActionEvent>() {
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
        refresh();
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

    public void showStage() {
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
            String saveName = String.valueOf(nameField.getText());
            try {
                gameDatabaseManager.setup();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

//            saveEnemy(map.getEnemyList(), saveName);
//            gameDatabaseManager.saveInventory(map.getPlayer(), saveName);
//            gameDatabaseManager.savePlayer(map.getPlayer(), saveName);
//            gameDatabaseManager.saveGame(mapName, map.getPlayer(), saveName);

            dataBaseWWork(saveName);

            newStage.hide();
        });
        cancelBtn.setOnAction(actionEvent -> {
            newStage.hide();
        });
    }

    private void dataBaseWWork(String saveName) {
        saveEnemy(map.getEnemyList(), saveName);
        gameDatabaseManager.saveInventory(map.getPlayer(), saveName);
        gameDatabaseManager.savePlayer(map.getPlayer(), saveName);
        gameDatabaseManager.saveGame(mapName, map.getPlayer(), saveName);
    }

    private void onKeyPressed(KeyEvent keyEvent) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<KeyCode> keyCodes = new ArrayList<>();

        switch (keyEvent.getCode()) {
            case UP:
                getPlayerStats(0, -1);
                enemyAction(true);
                refresh();
                break;
            case DOWN:
                getPlayerStats(0, 1);
                enemyAction(true);
                refresh();
                break;
            case LEFT:
                getPlayerStats(-1, 0);
                enemyAction(true);
                refresh();
                break;
            case RIGHT:
                getPlayerStats(1, 0);
                enemyAction(true);
                refresh();
                break;
            case W:
                getPlayerStats(0, 0);
                map.getPlayer().attack();
                enemyAction(false);
                refresh();
                break;
            case CONTROL:
                keyCodes.add(KeyCode.CONTROL);
            case S:
                if (keyCodes.contains(KeyCode.CONTROL)) {
                    System.out.println("control and s");
//                    showStage(gameDatabaseManager);
                    showStage();
                    keyCodes.clear();
                    // TODO: 16.08.2022 Nu merge CTRL + S!
                }
        }
    }

    private void getPlayerStats(int dx, int dy) {
        map.getPlayer().modifyPlayerStats();
        map.getPlayer().lootEnemy();
        map.getPlayer().move(dx, dy);
        map.getPlayer().getNextMap();
        refresh();
    }

    private void refresh() {
        if (Player.newMap) {
            mapName = "/map2.txt";
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
            if (enemy.isCharacterAlive()) {
                enemy.attack();
                if (doSomething && enemy.isCharacterAlive())
                    enemy.move(Util.getRandomInt(), Util.getRandomInt());
            }
        }
    }

    private void saveEnemy(List<Enemy> enemyList, String playerName) {
        for (Enemy enemy : enemyList) {
            if (enemy.isCharacterAlive())
                gameDatabaseManager.saveEnemy(enemy, playerName);
        }
    }
}
