package com.example.passwordmanager.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pass_table")
public class PasswordEntity {

    private String title;
    private String password;
    @PrimaryKey(autoGenerate = true)
    private Integer id;


    public PasswordEntity(String title, String password) {
        this.title = title;
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }
}
