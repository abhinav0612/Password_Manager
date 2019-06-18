package com.example.passwordmanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.passwordmanager.Room.PasswordEntity;

import java.util.List;

public class PasswordModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<PasswordEntity>> allnotes;
    public PasswordModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allnotes = repository.getAllNotes();
    }


    void insert_pass(PasswordEntity passwordEntity)
    {
        repository.insert(passwordEntity);
    }

    void update_pass(PasswordEntity passwordEntity)
    {
        repository.update(passwordEntity);
    }

    void delete_pass(PasswordEntity passwordEntity)
    {
        repository.delete(passwordEntity);
    }

    void deleteAll_pass()
    {
        repository.delete_all();
    }
    LiveData<List<PasswordEntity>> getAllnotes()
    {
        return allnotes;
    }

}
