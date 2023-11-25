package com.example.arbomaisandroid.views.arvore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arbomaisandroid.R;
import com.example.arbomaisandroid.database.LocalDatabase;
import com.example.arbomaisandroid.entities.Arvore;
import com.example.arbomaisandroid.utils.ConvertFloat;

public class ArvoreView extends AppCompatActivity {
    int arvoreId;
    int usuarioId;
    Arvore arvore;
    LocalDatabase db;
    EditText edtTextEspecie;
    EditText edtNumberAltura;
    TextView txtVoltar;
    Button btnExcluir;
    Button btnSalvar;

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
                   System.out.println("Fudeu" + arvoreId);
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

        Arvore novaArvore = new Arvore(usuarioId, especie, altura);
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
}