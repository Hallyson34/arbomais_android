package com.example.arbomaisandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arbomaisandroid.entities.Usuario;

import java.util.List;

@Dao()
public interface UsuarioDao {
    @Query("SELECT * FROM Usuario WHERE id = :id LIMIT 1")
    Usuario getUsuarioById(int id);
    @Query("SELECT * FROM Usuario WHERE nome = :nome LIMIT 1")
    Usuario getUsuarioByNome(String nome);
    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();
    @Update
    void update(Usuario usuario);
    @Insert
    void insertAll(Usuario... usuario);
    @Delete
    void delete(Usuario usuario);
}
