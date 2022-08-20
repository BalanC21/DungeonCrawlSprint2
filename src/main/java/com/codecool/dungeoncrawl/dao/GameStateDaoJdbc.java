package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.ItemType;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private final DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean checkIfSavedInstanceExists(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "select id from game_state where name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                if (id == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return false;
    }

    @Override
    public void add(GameState state, int selectedPlayerId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id, name) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            LocalDate localDate = LocalDate.now();
            Date date = Date.valueOf(localDate);
            st.setString(1, state.getCurrentMap());
            st.setDate(2, state.getSavedAt());
            st.setInt(3, selectedPlayerId);
            st.setString(4, state.getName());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            state.setId(rs.getInt(1));
        } catch (SQLException throwables) {
//            throw new RuntimeException("Error while", throwables.getCause());
            System.out.println(throwables.getMessage());
        }
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(String name) {
        GameState gameState = null;
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM game_state WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet result = st.executeQuery();
            result.next();
            int id = result.getInt("id");
            String savedName = result.getString("name");
            String current_map = result.getString("current_map");
            Date date = result.getDate("saved_at");
            int playerId = result.getInt("player_id");


            System.out.println(id + "  id");
            System.out.println(name + "   name");
            System.out.println(current_map + "   current_map");
            System.out.println(date.toString() + "   date");
            System.out.println(playerId + "  playerId");


            gameState = new GameState(current_map, date, savedName, id, playerId);

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return gameState;
    }


    public int getPlayerId(String name) {
        int selectedPlayerId = 0;
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_id FROM game_state WHERE name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, name);
            ResultSet result = st.executeQuery();
            result.next();
            selectedPlayerId = result.getInt("player_id");

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return selectedPlayerId;
    }


}
