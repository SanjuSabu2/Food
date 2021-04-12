package com.example.food.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = Cart.class, version = 3)
public abstract class MyDatabase extends RoomDatabase {
    public abstract CartDao cartDao();

}
