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

    @Override
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            LocalDate localDate = LocalDate.now();
            Date date = Date.valueOf(localDate);
            System.out.println(state.getCurrentMap() + " Map");
            System.out.println(date + " Date");
            System.out.println(state.getPlayer().getId() + " UUID");
            st.setString(1, state.getCurrentMap());
            st.setInt(2, state.getPlayer().getId());
            st.setDate(3, state.getSavedAt());
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
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
