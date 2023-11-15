package com.example.arbomaisandroid.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "id", childColumns = "usuarioId"))
public class Arvore {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int usuarioId;
    String especie;
    String classe;
    String genero;
    int altura;
    String endereco;
    boolean podaMalFeita;
    boolean riscoFiosEnergia;
    String createdAt;

    public Arvore() {}
    public Arvore(int usuarioId, String especie, String classe, String genero, int altura, String endereco, boolean podaMalFeita, boolean riscoFiosEnergia) {
        this.usuarioId = usuarioId;
        this.especie = especie;
        this.classe = classe;
        this.genero = genero;
        this.altura = altura;
        this.endereco = endereco;
        this.podaMalFeita = podaMalFeita;
        this.riscoFiosEnergia = riscoFiosEnergia;
        this.createdAt = new Date().toString();
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

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isPodaMalFeita() {
        return podaMalFeita;
    }

    public void setPodaMalFeita(boolean podaMalFeita) {
        this.podaMalFeita = podaMalFeita;
    }

    public boolean isRiscoFiosEnergia() {
        return riscoFiosEnergia;
    }

    public void setRiscoFiosEnergia(boolean riscoFiosEnergia) {
        this.riscoFiosEnergia = riscoFiosEnergia;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return this.getEspecie() +  ", Classe: " + this.getClasse() + ", Endereço: " + this.getEndereco();
    }
}
