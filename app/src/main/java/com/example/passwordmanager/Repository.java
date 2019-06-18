package com.example.passwordmanager;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.passwordmanager.Room.PasswordDao;
import com.example.passwordmanager.Room.PasswordDatabase;
import com.example.passwordmanager.Room.PasswordEntity;

import java.util.List;

public class Repository {

    private PasswordDao passwordDao;
    private LiveData<List<PasswordEntity>> allNotes;

    public Repository(Application application) {
        PasswordDatabase db = PasswordDatabase.getInstance(application);
        passwordDao = db.passwordDao();
        allNotes = passwordDao.showAll();
    }

    void insert(PasswordEntity passwordEntity)
    {
        new PassinsertAsyncTask(passwordDao).execute(passwordEntity);
    }

    void update(PasswordEntity passwordEntity)
    {
        new PassupdateAsyncTask(passwordDao).execute(passwordEntity);
    }

    void delete(PasswordEntity passwordEntity)
    {
        new PassdeleteAsyncTask(passwordDao).execute(passwordEntity);

    }

    void delete_all()
    {
        new Passdelete_AllAsyncTask(passwordDao).execute();
    }

    LiveData<List<PasswordEntity>> getAllNotes()
    {
        return allNotes;
    }

    //Asynchronous classes creation

    public static class PassinsertAsyncTask extends AsyncTask<PasswordEntity,Void,Void>
    {
        private PasswordDao passwordDao;

        public PassinsertAsyncTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            passwordDao.insert(passwordEntities[0]);
            return null;
        }
    }

    public static class PassupdateAsyncTask extends AsyncTask<PasswordEntity,Void,Void>
    {
        private PasswordDao passwordDao;

        public PassupdateAsyncTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            passwordDao.update(passwordEntities[0]);
            return null;
        }
    }

    public static class PassdeleteAsyncTask extends AsyncTask<PasswordEntity,Void,Void>
    {
        private PasswordDao passwordDao;

        public PassdeleteAsyncTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            passwordDao.delete(passwordEntities[0]);
            return null;
        }
    }

    public static class Passdelete_AllAsyncTask extends AsyncTask<PasswordEntity,Void,Void>
    {
        private PasswordDao passwordDao;

        public Passdelete_AllAsyncTask(PasswordDao passwordDao) {
            this.passwordDao = passwordDao;
        }

        @Override
        protected Void doInBackground(PasswordEntity... passwordEntities) {
            passwordDao.deleteAll();
            return null;
        }
    }


}
