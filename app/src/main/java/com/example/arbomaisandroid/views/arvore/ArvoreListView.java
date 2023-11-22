package com.example.arbomaisandroid.views.arvore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arbomaisandroid.R;
import com.example.arbomaisandroid.database.LocalDatabase;
import com.example.arbomaisandroid.entities.Arvore;
import com.example.arbomaisandroid.entities.ArvoreUsuario;
import com.example.arbomaisandroid.entities.TipoUsuario;
import com.example.arbomaisandroid.views.usuario.UsuarioView;

import java.util.List;

public class ArvoreListView extends AppCompatActivity {
    int usuarioId;
    TipoUsuario tipoUsuario;
    ListView listArvores;
    List<ArvoreUsuario> arvoresUsuario;
    Intent edtIntent;
    Intent userIntent;
    LocalDatabase db;
    TextView txtEmptyTrees;
    Button btnNovaArvore;
    ImageButton userIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvore_list_view);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listArvores = findViewById(R.id.listArvore);
        usuarioId = getIntent().getIntExtra(
                "USUARIO_ID", -1);
        tipoUsuario = (TipoUsuario) getIntent().getSerializableExtra("USUARIO_TIPO");
        System.out.println(usuarioId +  tipoUsuario.toString());
        txtEmptyTrees = findViewById(R.id.edtTextListaArvore);
        btnNovaArvore = findViewById(R.id.btnNovaArvore);
        userIcon = findViewById(R.id.userIcon);

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUsuario();
            }
        });

        btnNovaArvore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaArvore();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, ArvoreView.class);
        preencheArvores();
    }

    private void preencheArvores() {
        arvoresUsuario = db.arvoreUsuarioModel().getAll();
        ArrayAdapter<ArvoreUsuario> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, arvoresUsuario);
        listArvores.setAdapter(adapter);
        if (arvoresUsuario.isEmpty()) {
            txtEmptyTrees.setText(getString(R.string.empty_trees));
        } else {
            txtEmptyTrees.setText("");
            listArvores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    editarArvore(position);
                }
            });
        }
    }

    private void editarArvore(int position) {
        ArvoreUsuario arvoreSelecionada = arvoresUsuario.get(position);
        if (tipoUsuario == TipoUsuario.ADMIN ||
            (tipoUsuario == TipoUsuario.STANDARD && usuarioId == arvoreSelecionada.getUsuarioId())
        ) {
            edtIntent.putExtra("ARVORE_ID",
                    arvoreSelecionada.getArvoreId());
            edtIntent.putExtra("USUARIO_ID", usuarioId);
            startActivity(edtIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Você não tem permissão para alterar essa árvore!", Toast.LENGTH_SHORT).show();
        }
    }

    private void novaArvore() {
        edtIntent.putExtra("ARVORE_ID", -1);
        edtIntent.putExtra("USUARIO_ID", usuarioId);
        startActivity(edtIntent);
    }

    private void editarUsuario() {
        userIntent = new Intent(this, UsuarioView.class);
        userIntent.putExtra("USUARIO_ID", usuarioId);
        startActivity(userIntent);
    }
}