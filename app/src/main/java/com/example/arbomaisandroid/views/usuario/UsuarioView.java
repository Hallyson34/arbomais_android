package com.example.arbomaisandroid.views.usuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arbomaisandroid.R;
import com.example.arbomaisandroid.database.LocalDatabase;
import com.example.arbomaisandroid.entities.Arvore;
import com.example.arbomaisandroid.entities.ArvoreUsuario;
import com.example.arbomaisandroid.entities.TipoUsuario;
import com.example.arbomaisandroid.entities.Usuario;
import com.example.arbomaisandroid.utils.ConvertFloat;
import com.example.arbomaisandroid.utils.Hashing;
import com.example.arbomaisandroid.views.MainActivity;
import com.example.arbomaisandroid.views.arvore.ArvoreView;

import java.util.List;

public class UsuarioView extends AppCompatActivity {
    int usuarioId;
    Usuario usuario;
    LocalDatabase db;
    EditText edtTextNome;
    EditText edtTextSenha;
    TextView txtVoltar;
    Button btnExcluir;
    Button btnSalvar;
    ListView listUsuarios;
    Button btnSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_view);

        usuarioId = getIntent().getIntExtra(
                "USUARIO_ID", -1);
        db = LocalDatabase.getDatabase(getApplicationContext());
        txtVoltar = findViewById(R.id.txtVoltarUsuario);
        edtTextNome = findViewById(R.id.edtTextNome);
        edtTextSenha = findViewById(R.id.edtTextSenha);
        btnExcluir = findViewById(R.id.btnExcluirUsuario);
        btnSalvar = findViewById(R.id.btnSalvarUsuario);
        btnSair = findViewById(R.id.btnSairUsuario);
        listUsuarios = findViewById(R.id.listusuarios);

        txtVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUsuario();
            }
        });
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirUsuario();
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitUsuario();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuario = db.usuarioModel().getUsuarioById(usuarioId);
        preencheUsuario();

        if (usuario.getTipo() != TipoUsuario.ADMIN) {
            listUsuarios.setVisibility(View.GONE);
        } else {
            btnExcluir.setVisibility(View.VISIBLE);
            preencheUsuarios();
        }
    }

    private void preencheUsuario() {
        edtTextNome.setText(usuario.getNome());
    }

    private void preencheUsuarios() {
        List<Usuario> usuarios = db.usuarioModel().getAll();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, usuarios);
        listUsuarios.setAdapter(adapter);
        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuarioSelecionado = usuarios.get(position);
                String opcao = usuarioSelecionado.isAtivo()
                        ? "Deseja desativar esse usuário?" : "Deseja ativar esse usuário?";

                new AlertDialog.Builder(UsuarioView.this)
                        .setTitle("Ativação de Usuário")
                        .setMessage(opcao)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                usuarioSelecionado.setAtivo(!usuarioSelecionado.isAtivo());
                                db.usuarioModel().update(usuarioSelecionado);
                                Toast.makeText(UsuarioView.this, "Usuário alterado com sucesso.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });
    }

    private void editarUsuario() {
        String nome = edtTextNome.getText().toString();
        String senha = edtTextSenha.getText().toString();

        if (nome.isEmpty() && senha.isEmpty()) {
            Toast.makeText(this, "Preencha pelo menos um dos campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nome.isEmpty() && !nome.equals(usuario.getNome())) {
            Usuario usuarioExistente = db.usuarioModel().getUsuarioById(usuarioId);
            if (usuarioExistente != null) {
                Toast.makeText(getApplicationContext(), "Usuário com esse nome já cadastrado!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (!nome.isEmpty() && !senha.isEmpty()) {
            usuario.setNome(nome);
            usuario.setSenha(Hashing.hashPassword(senha));
            db.usuarioModel().update(usuario);
            Toast.makeText(getApplicationContext(), "Senha alterada com sucesso.", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }

        if (!nome.isEmpty() && senha.isEmpty()) {
            usuario.setNome(nome);
            db.usuarioModel().update(usuario);
            Toast.makeText(getApplicationContext(), "Nome alterado com sucesso.", Toast.LENGTH_SHORT)
                    .show();
            finish();
        } else {
            usuario.setSenha(Hashing.hashPassword(senha));
            db.usuarioModel().update(usuario);
            Toast.makeText(getApplicationContext(), "Senha alterada com sucesso.", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    private void excluirUsuario() {
        new AlertDialog.Builder(UsuarioView.this)
                .setTitle("Exclusão de Usuário")
                .setMessage("Deseja excluir esse usuário?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.usuarioModel().delete(usuario);
                        Toast.makeText(UsuarioView.this, "Usuário excluído com sucesso.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void exitUsuario() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}