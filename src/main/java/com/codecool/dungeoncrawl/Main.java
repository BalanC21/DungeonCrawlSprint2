package com.codecool.dungeoncrawl;
//Good
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventory = new Label();
    Button button = new Button("Pick Up");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(new Label("Health: "), 0, 1);
        ui.add(new Label("Inventory"), 0, 2);
        ui.add(healthLabel, 1, 1);
        ui.add(inventory, 1, 2);
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

                }
                inventory.setText("" + map.getPlayer().getItemTypeList());

            }
        });
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");

        primaryStage.setScene(scene);

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);


        primaryStage.setTitle("Dungeon Crawl");

        Cell cell = map.getCell(10, 10);

        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().lootEnemy();
                map.getPlayer().move(0, -1);
                enemyAction();
                refresh();
                break;
            case DOWN:
                map.getPlayer().lootEnemy();
                map.getPlayer().move(0, 1);
                enemyAction();
                refresh();
                break;
            case LEFT:
                map.getPlayer().lootEnemy();
                map.getPlayer().move(-1, 0);
                enemyAction();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().lootEnemy();
                map.getPlayer().move(1, 0);
                enemyAction();
                refresh();
                break;
            case W:
                refresh();
                map.getPlayer().attack();
                enemyAction();
                refresh();
                break;
        }
    }

    private void refresh() {
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
        inventory.setText("" + map.getPlayer().getItemTypeList());
    }

    private void enemyAction() {
        List<Enemy> enemyList = map.getEnemyList();
        for (Enemy enemy : enemyList) {
            enemy.attack();
            refresh();
        }
    }
}
