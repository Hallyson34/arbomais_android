package com.example.arbomaisandroid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arbomaisandroid.R;
import com.example.arbomaisandroid.database.LocalDatabase;
import com.example.arbomaisandroid.entities.TipoUsuario;
import com.example.arbomaisandroid.entities.Usuario;
import com.example.arbomaisandroid.utils.Hashing;
import com.example.arbomaisandroid.views.arvore.ArvoreListView;

public class MainActivity extends AppCompatActivity {
    private LocalDatabase db;
    private EditText edtNome;
    private EditText edtSenha;
    private Button btnEntrar;
    private Button btnCriar;
    private Usuario usuarioLogado;
    private Intent arvoreListViewIntent;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = LocalDatabase.getDatabase(getApplicationContext());
        this.insertFirstAdmin();
        edtNome = findViewById(R.id.edtTextNome);
        edtSenha = findViewById(R.id.edtTextSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCriar = findViewById(R.id.btnCriar);
        arvoreListViewIntent = new Intent(this, ArvoreListView.class);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()) {
                    showArvoresListView();
                };
            }
        });
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyFieldsEmpty()) {
                    cadastrarUsuario(edtNome.getText().toString(), edtSenha.getText().toString(),
                            TipoUsuario.STANDARD, false);
                }
            }
        });
    }

    private void insertFirstAdmin() {
        Usuario admin = db.usuarioModel().getUsuarioByNome("ADM");
        if(admin == null) {
            Usuario usuario = new Usuario("ADM", "ADM", TipoUsuario.ADMIN, true);
            usuario.setSenha(Hashing.hashPassword(usuario.getSenha()));
            db.usuarioModel().insertAll(usuario);
        }
    }

    private boolean verifyFieldsEmpty() {
        String nome = edtNome.getText().toString();
        if(nome.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o campo nome!", Toast.LENGTH_SHORT).show();
            return true;
        }
        String senha = edtSenha.getText().toString();
        if(senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o campo senha!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean login() {
        Usuario usuarioCadastrado = db.usuarioModel().getUsuarioByNome(edtNome.getText().toString());
        if(usuarioCadastrado == null) {
            Toast.makeText(getApplicationContext(), "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!usuarioCadastrado.isAtivo()) {
            Toast.makeText(getApplicationContext(), "Usuário não permitido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Hashing.hashPassword(edtSenha.getText().toString()).equals(usuarioCadastrado.getSenha())) {
            Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
            return false;
        }
        usuarioLogado = usuarioCadastrado;
        return true;
    }

    private void cadastrarUsuario(String nome, String senha, TipoUsuario tipoUsuario, boolean ativo) {
        Usuario usuarioExistente = db.usuarioModel().getUsuarioByNome(nome);
        if (usuarioExistente != null) {
            Toast.makeText(getApplicationContext(), "Usuário já cadastrado!", Toast.LENGTH_SHORT).show();
            return;
        }
        Usuario usuario = new Usuario(nome, senha, tipoUsuario, ativo);
        usuario.setSenha(Hashing.hashPassword(usuario.getSenha()));
        db.usuarioModel().insertAll(usuario);
        Toast.makeText(getApplicationContext(),
                        "Aguarde o administrador confirmar seu cadastro.", Toast.LENGTH_SHORT)
                .show();
    }

    private void showArvoresListView() {
        arvoreListViewIntent.putExtra("USUARIO_ID", usuarioLogado.getId());
        arvoreListViewIntent.putExtra("USUARIO_TIPO", usuarioLogado.getTipo());
        startActivity(arvoreListViewIntent);
    }
}