package com.devcuong.mycooking.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.devcuong.mycooking.obj.Meal;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    List<Meal> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meal favorite);

    @Delete
    void delete(Meal favorite);

    @Query("SELECT * FROM favorites WHERE idMeal = :idMeal LIMIT 1")
    Meal getMealById(String idMeal);
}