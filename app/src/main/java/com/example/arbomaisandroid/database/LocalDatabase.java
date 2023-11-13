package com.example.arbomaisandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.arbomaisandroid.entities.Arvore;
import com.example.arbomaisandroid.entities.Usuario;

@Database(entities = {Arvore.class, Usuario.class}, version = 2, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase INSTANCE;
    public static LocalDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, "ArboMais").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
//    public abstract TipoDao tipoModel();
//    public abstract ObjetoDao objetoModel();
//    public abstract ObjetoTipoDao objetoTipoModel();
}
