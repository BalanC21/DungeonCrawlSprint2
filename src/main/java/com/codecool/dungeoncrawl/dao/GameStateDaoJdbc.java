package com.codecool.dungeoncrawl.dao;

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
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id, name) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            LocalDate localDate = LocalDate.now();
            Date date = Date.valueOf(localDate);
            System.out.println(state.getCurrentMap() + " Map");
            System.out.println(date + " Date");
            System.out.println(state.getPlayer().getId() + " UUID");
            st.setString(1, state.getCurrentMap());
            st.setDate(2, state.getSavedAt());
            st.setInt(3, state.getPlayer().getId());
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
        return null;
    }


}
