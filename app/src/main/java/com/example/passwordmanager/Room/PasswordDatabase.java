package com.example.passwordmanager.Room;

import android.app.Application;
import android.os.AsyncTask;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities={PasswordEntity.class},version = 1)
public abstract class PasswordDatabase extends RoomDatabase {
    private  static PasswordDatabase instance;
    public abstract PasswordDao passwordDao();

    public static synchronized PasswordDatabase getInstance(Application application)
    {
        if (instance==null)
        {

            instance = Room.databaseBuilder(application.getApplicationContext(),
                    PasswordDatabase.class,
                    "Password_Dartabase")
                    .fallbackToDestructiveMigration().addCallback(callback).build();

        }
        return instance;
    }

    private  static RoomDatabase.Callback callback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateASync(instance).execute();
        }
    };

    public static class PopulateASync extends AsyncTask<Void, Void,Void>
    {
        private PasswordDao passwordDao;

        public PopulateASync(PasswordDatabase database) {
            passwordDao=database.passwordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            passwordDao.insert(new PasswordEntity("Facebook","abc"));
            passwordDao.insert(new PasswordEntity("Instagram","def"));
            return null;
        }
    }
}
