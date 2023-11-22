package com.example.arbomaisandroid.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "id", childColumns = "usuarioId",
        onDelete = ForeignKey.CASCADE))
public class Arvore {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int usuarioId;
    String especie;
    float altura;
    String createdAt;

    public Arvore() {}
    public Arvore(int usuarioId, String especie, float altura) {
        this.usuarioId = usuarioId;
        this.especie = especie;
        this.altura = altura;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.createdAt = sdf.format(now);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return this.getEspecie();
    }
}
