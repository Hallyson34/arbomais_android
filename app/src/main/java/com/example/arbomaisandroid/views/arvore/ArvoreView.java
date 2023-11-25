package com.example.arbomaisandroid.views.arvore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.arbomaisandroid.R;
import com.example.arbomaisandroid.database.LocalDatabase;
import com.example.arbomaisandroid.entities.Arvore;
import com.example.arbomaisandroid.utils.ConvertFloat;

public class ArvoreView extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    int arvoreId;
    int usuarioId;
    Arvore arvore;
    LocalDatabase db;
    EditText edtTextEspecie;
    EditText edtNumberAltura;
    TextView txtVoltar;
    Button btnExcluir;
    Button btnSalvar;
    Button btnAddFoto;
    ImageView imageViewArvore;
    Bitmap imagemArvore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvore_view);
        arvoreId = getIntent().getIntExtra(
                "ARVORE_ID", -1);
        usuarioId = getIntent().getIntExtra(
                "USUARIO_ID", -1);
        db = LocalDatabase.getDatabase(getApplicationContext());
        txtVoltar = findViewById(R.id.txtVoltarArvore);
        edtTextEspecie = findViewById(R.id.edtTextEspecie);
        edtNumberAltura = findViewById(R.id.edtNumberAltura);
        btnExcluir = findViewById(R.id.btnExcluirArvore);
        btnSalvar = findViewById(R.id.btnSalvarArvore);
        imageViewArvore = findViewById(R.id.imgArvore);
        btnAddFoto = findViewById(R.id.btnAddFoto);

        txtVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arvoreId != -1) {
                    editarArvore();
                } else {
                    criarArvore();
                }
            }
        });
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirArvore();
            }
        });
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arvoreId != -1) {
            arvore = db.arvoreModel().getArvoreById(arvoreId);
            preencheArvore();
            btnExcluir.setVisibility(View.VISIBLE);
        } else {
            btnExcluir.setVisibility(View.GONE);
        }
    }

    private void preencheArvore() {
        edtTextEspecie.setText(arvore.getEspecie());
        edtNumberAltura.setText(ConvertFloat.floatToString(arvore.getAltura()));
        Bitmap arvoreImg = arvore.getImagem();
        if(arvoreImg != null)
            imageViewArvore.setImageBitmap(arvoreImg);
    }

    private void editarArvore() {
        String especie = edtTextEspecie.getText().toString();
        float altura = ConvertFloat.stringToFloat(edtNumberAltura.getText().toString());
        if (especie.isEmpty()) {
            Toast.makeText(this, "Certifique-se de preencher o campo especie.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (altura < 1) {
            Toast.makeText(this, "Certifique-se de digitar um número válido.", Toast.LENGTH_SHORT).show();
            return;
        }
        arvore.setEspecie(especie);
        arvore.setAltura(altura);

        if (imagemArvore != arvore.getImagem()) {
            arvore.setImagem(imagemArvore);
        }

        db.arvoreModel().update(arvore);
        Toast.makeText(this, "Árvore editada com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void criarArvore() {
        String especie = edtTextEspecie.getText().toString();
        if (especie.isEmpty()) {
            Toast.makeText(this, "Certifique-se de preencher o campo especie.", Toast.LENGTH_SHORT).show();
            return;
        }

        String txtAltura = edtNumberAltura.getText().toString();
        if (txtAltura.isEmpty()) {
            Toast.makeText(this, "Certifique-se de preencher o campo altura.", Toast.LENGTH_SHORT).show();
            return;
        }

        float altura = ConvertFloat.stringToFloat(edtNumberAltura.getText().toString());
        if (altura < 1) {
            Toast.makeText(this, "Certifique-se de digitar um número válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Arvore novaArvore = new Arvore(usuarioId, especie, altura, imagemArvore);
        db.arvoreModel().insertAll(novaArvore);
        Toast.makeText(this, "Árvore criada com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void excluirArvore() {
        new AlertDialog.Builder(ArvoreView.this)
                .setTitle("Exclusão de Arvore")
                .setMessage("Deseja excluir essa arvore?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.arvoreModel().delete(arvore);
                        Toast.makeText(ArvoreView.this, "Árvore excluída com sucesso.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void captureImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagemArvore = imageBitmap;
            imageViewArvore.setImageBitmap(imageBitmap);
        }

    }
}