package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.repositories.EnemyDao;
import com.codecool.dungeoncrawl.model.EnemyModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class EnemyDaoJdbc implements EnemyDao {
    private final DataSource dataSource;

    public EnemyDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(EnemyModel enemy, String input) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemy (save_name, enemy_type, hp, x, y) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, input);
            statement.setString(2, enemy.getEnemyName());
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

    @Override
    public void update(EnemyModel enemy) {

    }

    @Override
    public EnemyModel get(int id) {
        return null;
    }

    @Override
    public List<EnemyModel> getAll() {
        return null;
    }
}
