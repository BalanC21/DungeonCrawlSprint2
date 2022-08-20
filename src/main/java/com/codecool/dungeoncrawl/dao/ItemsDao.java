package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.List;

public interface ItemsDao {

    void add(List<Cell> cells, int gameStateId);
    List<ItemModel> getItemModelList (int gameId);
}
