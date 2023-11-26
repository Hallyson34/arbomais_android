package com.example.arbomaisandroid.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
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
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] imagem;
    String createdAt;
    String latitude;
    String longitude;

    public Arvore() {}
    public Arvore(int usuarioId, String especie, float altura, Bitmap imagem, String latitude, String longitude) {
        this.setUsuarioId(usuarioId);
        this.setEspecie(especie);
        this.setAltura(altura);
        this.setImagem(imagem);
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.setCreatedAt(sdf.format(now));
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Bitmap getImagem() {
        if (imagem != null) {
            return BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
        }
        return null;
    }

    public void setImagem(Bitmap imagemBitmap) {
        if (imagemBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagemBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            this.imagem = stream.toByteArray();
        } else {
            this.imagem = null;
        }
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
