package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.repositories.InventoryDao;
import com.codecool.dungeoncrawl.logic.ItemType;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.InventoryRecord;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoJdbc implements InventoryDao {
    private DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(InventoryRecord item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO items (save_name, inventory_type) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, item.saveName());
            st.setString(2, item.itemName());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();

        } catch (SQLException throwables) {
//            throw new RuntimeException("Error while", throwables.getCause());
            System.out.println(throwables.getMessage());
        }
    }

    @Override
    public void update(InventoryRecord item) {

    }

    @Override
    public InventoryRecord get(int id) {
        return null;
    }

    @Override
    public List<InventoryRecord> getAll(String saveName) {
        InventoryRecord inventoryRecord = null;
        List<InventoryRecord> inventoryRecords = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {

            String sql = "select * from items where save_name = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, saveName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                inventoryRecord = new InventoryRecord(rs.getString("inventory_type"), rs.getString("save_name"));
                System.out.println(inventoryRecord.itemName());
                inventoryRecords.add(inventoryRecord);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
        }
        System.out.println(inventoryRecords.size() + " size inventoryRecords");
        return inventoryRecords;
    }
}
