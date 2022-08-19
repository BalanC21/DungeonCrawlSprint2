package com.codecool.dungeoncrawl.dao.repositories;

import com.codecool.dungeoncrawl.model.MapItemsRecord;

import java.util.List;

public interface MapItemsDao {
    void add(MapItemsRecord item);

    List<MapItemsRecord> getAll(String saveName);
}
