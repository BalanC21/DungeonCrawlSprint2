package com.codecool.dungeoncrawl.dao;
import com.codecool.dungeoncrawl.dao.repositories.GameStateDao;
import com.codecool.dungeoncrawl.model.GameState;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private final DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id, name) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
    public GameState get(int id) {
        return null;
    }

    @Override
    public String getSaveNameGameState(String name) {
        String nameValue = "";
        try (Connection conn = dataSource.getConnection()) {
            String sql = "select name from game_state where name ilike ? limit 1";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            nameValue = rs.getString(1);
            System.out.println("Name Already Exist " + nameValue);

        } catch (SQLException throwables) {
//            throw new RuntimeException("Error while", throwables.getCause());
            System.out.println(throwables.getMessage());
        }
        return nameValue;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
