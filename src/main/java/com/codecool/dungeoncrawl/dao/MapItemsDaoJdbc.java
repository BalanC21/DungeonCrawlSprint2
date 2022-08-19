package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.repositories.MapItemsDao;
import com.codecool.dungeoncrawl.model.MapItemsRecord;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapItemsDaoJdbc implements MapItemsDao {

    private DataSource dataSource;

    public MapItemsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MapItemsRecord item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO mapitems (save_name, inventory_type, x, y) VALUES (?, ?, ? , ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, item.saveName());
            st.setString(2, item.itemType());
            st.setInt(3, item.x());
            st.setInt(4, item.y());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    @Override
    public List<MapItemsRecord> getAll(String saveName) {
        MapItemsRecord mapItemsRecord = null;
        List<MapItemsRecord> mapItemsRecords = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {

            String sql = "select * from mapitems where save_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, saveName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                mapItemsRecord = new MapItemsRecord(rs.getString("save_name"), rs.getString("inventory_type"), rs.getInt("x"), rs.getInt("y"));
                mapItemsRecords.add(mapItemsRecord);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(mapItemsRecords.size() + " size mapItemsRecords");
        return mapItemsRecords;
    }
}
