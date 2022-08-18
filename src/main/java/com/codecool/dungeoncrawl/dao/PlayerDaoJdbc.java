package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.repositories.PlayerDao;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private final DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, attack, x, y) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getAttack());
            statement.setInt(4, player.getX());
            statement.setInt(5, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE player SET hp = ?, x = ?, y = ? WHERE player_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, player.getHp());
            st.setInt(2, player.getX());
            st.setInt(3, player.getY());
            st.setString(4, player.getPlayerName());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getDbX(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x FROM player WHERE id = ?";
            return prepareStatementMethod(id, conn, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getDbY(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT y FROM player WHERE id = ?";
            return prepareStatementMethod(id, conn, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getDbHp(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT hp FROM player WHERE id = ?";
            return prepareStatementMethod(id, conn, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getIdByName(String playerName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM player WHERE player_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, playerName);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return Integer.parseInt(null);
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel playerStatsByPlayerName(String playerName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_name, hp, attack, x, y from player where id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, playerName);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return new PlayerModel("Don't Exist", 0, 0);
            }
            return new PlayerModel(rs.getString("player_name"), rs.getInt("hp"), rs.getInt("hp"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMap(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT current_map FROM game_state WHERE player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int prepareStatementMethod(int id, Connection conn, String sql) throws SQLException {
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            return 0;
        }
        return rs.getInt(1);
    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }

}
