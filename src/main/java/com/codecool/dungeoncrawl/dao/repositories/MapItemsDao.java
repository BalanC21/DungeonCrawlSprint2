package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.InventoryRecord;
import com.codecool.dungeoncrawl.model.MapItemsRecord;

import java.util.List;

public interface MapItemsDao {
    void add(MapItemsRecord item);

    void update(MapItemsRecord item);

    MapItemsRecord get(int id);

    List<MapItemsRecord> getAll(String saveName);
}
