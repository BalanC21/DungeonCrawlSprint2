package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.InventoryRecord;

import java.util.List;

public interface InventoryDao {
    void add(InventoryRecord item);

    List<InventoryRecord> getAll(String saveName);
}
