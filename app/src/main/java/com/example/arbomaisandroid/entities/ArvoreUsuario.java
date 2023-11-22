package com.example.arbomaisandroid.entities;


public class ArvoreUsuario {
    public int arvoreId;
    public int usuarioId;
    public String especie;
    public String nomeUsuario;
    public String createdAt;

    public int getArvoreId() { return arvoreId; }
    public int getUsuarioId() { return usuarioId; }

    @Override
    public String toString() {
        return "ID: " + this.getArvoreId() + " Espécie: " + this.especie + " Usuário: "+ this.nomeUsuario + ", Data de Criação: " + this.createdAt;
    }
}
