package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.model.EnemyModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class EnemiesDaoJdbc implements EnemiesDao {
    private final DataSource dataSource;


    public EnemiesDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    @Override
    public void add(List<EnemyModel> enemiesModel, int gameStateId) {
        for (EnemyModel enemy : enemiesModel) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "INSERT INTO enemies (game_state_id, enemy_type, hp, x, y) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, gameStateId);
                statement.setString(2, enemy.getEnemyType());
                statement.setInt(3, enemy.getHp());
                statement.setInt(4, enemy.getX());
                statement.setInt(5, enemy.getY());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                enemy.setId(resultSet.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void update(EnemyModel enemy) {

    }
}
