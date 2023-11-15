package com.example.arbomaisandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arbomaisandroid.entities.Arvore;

import java.util.List;

@Dao()
public interface ArvoreDao {
    @Query("SELECT * FROM Arvore WHERE id = :id LIMIT 1")
    Arvore getArvoreById(int id);
    @Update
    void update(Arvore arvore);
    @Insert
    void insertAll(Arvore... arvore);
    @Delete
    void delete(Arvore arvore);
    @Query("SELECT * FROM Arvore WHERE usuarioId = :usuarioId")
    List<Arvore> getArvoresByUsuarioId(int usuarioId);
}
