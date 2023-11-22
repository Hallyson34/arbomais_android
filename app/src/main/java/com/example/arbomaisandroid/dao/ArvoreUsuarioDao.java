package com.example.arbomaisandroid.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.arbomaisandroid.entities.ArvoreUsuario;

import java.util.List;

@Dao()
public interface ArvoreUsuarioDao {
    @Query("SELECT arv.id as arvoreId, especie, createdAt, usuarioId, usu.nome as nomeUsuario FROM Arvore as arv INNER JOIN Usuario as usu ON arv.usuarioId = usu.id;")
    List<ArvoreUsuario> getAll();
}
