package com.example.passwordmanager.Room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDao {
    @Insert
    void insert(PasswordEntity passwordEntity);
    @Update
    void update(PasswordEntity passwordEntity);
    @Delete
    void delete(PasswordEntity passwordEntity);
    @Query("DELETE FROM Pass_table")
    void deleteAll();
    @Query("SELECT * FROM Pass_table")
    LiveData<List<PasswordEntity>> showAll();

}
