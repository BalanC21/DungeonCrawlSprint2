package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ItemsDaoJdbc implements ItemsDao{

    private final DataSource dataSource;

    public ItemsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(List<Cell> cells, int gameStateId) {
        for (Cell cell : cells) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "INSERT INTO items (game_state_id, item_type,x, y) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, gameStateId);
                statement.setString(2, cell.getTileName());
                statement.setInt(3, cell.getX());
                statement.setInt(4, cell.getY());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
