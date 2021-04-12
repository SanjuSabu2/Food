package com.example.food.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    public void addToCart(Cart cart);

    @Query("SELECT * FROM MyCart")
    public List<Cart> getData();

    @Query("SELECT EXISTS (SELECT 1 FROM mycart WHERE id=:id)")
    public int isAddToCart(String id);

    @Query("select COUNT (*) from MyCart")
    int countCart();

    @Query("DELETE FROM MyCart WHERE id=:id ")
    int deleteItem(String id);

    @Query("UPDATE MyCart SET count = :counter WHERE id=:id")
    void updatecount(String id, int counter);

    @Query("SELECT SUM(price * count) FROM MyCart")
    int carttotal();

    @Query("SELECT count FROM MyCart WHERE id=:id")
    int returncount(String id);

    @Query("SELECT SUM(count) FROM MyCart")
    int returncartcount();
}
