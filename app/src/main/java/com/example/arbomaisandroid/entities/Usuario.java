package com.example.arbomaisandroid.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.arbomaisandroid.utils.Hashing;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String nome;
    String senha;
    TipoUsuario tipo;
    boolean ativo;

    public Usuario(){}

    public Usuario(String nome, String senha, TipoUsuario tipo, boolean ativo) {
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.getNome() +  ", Tipo: " + this.getTipo();
    }
}
