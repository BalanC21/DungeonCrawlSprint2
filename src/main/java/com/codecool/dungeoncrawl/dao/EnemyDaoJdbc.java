package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.repositories.EnemyDao;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyDaoJdbc implements EnemyDao {
    private final DataSource dataSource;

    public EnemyDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(EnemyModel enemy, String playerName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemy (save_name, enemy_type, hp, x, y) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, playerName);
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
        String sql = "UPDATE enemy SET hp = ?, x = ?, y = ? WHERE save_name = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, enemy.getHp());
            st.setInt(2, enemy.getX());
            st.setInt(3, enemy.getY());
            st.setString(4, enemy.getEnemyName());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EnemyModel> getAll(String saveName) {
        EnemyModel enemyModel = null;
        List<EnemyModel> enemyModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {

            String sql = "select * from enemy where save_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, saveName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                enemyModel = new EnemyModel(rs.getString("enemy_type"), rs.getInt("hp"), rs.getInt("x"), rs.getInt("y"));
                enemyModels.add(enemyModel);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(enemyModels.size() + " size enemyModels");
        return enemyModels;
    }
}
