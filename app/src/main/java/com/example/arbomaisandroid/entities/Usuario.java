package com.example.arbomaisandroid.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity()
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int arvoresId[];
    private String nome;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(){}

    public Usuario(String nome, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
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
