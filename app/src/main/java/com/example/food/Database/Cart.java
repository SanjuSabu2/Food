package com.example.food.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyCart")
public class Cart {
    @PrimaryKey
    @NonNull
    String id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "imageid")
    String imageid;

    @ColumnInfo(name = "description")
    String descript;

    @ColumnInfo(name = "price")
    String price;

    @ColumnInfo(name = "count")
    int itemcount;

    public Cart() {
        this.id = null;
        this.name = null;
        this.imageid = null;
        this.descript = null;
        this.price = null;
        this.itemcount = 1;
    }

    public Cart(String id, String name, String imageid, String descript, String price) {
        this.id = id;
        this.name = name;
        this.imageid = imageid;
        this.descript = descript;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getItemcount() {
        return itemcount;
    }

    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }
}
