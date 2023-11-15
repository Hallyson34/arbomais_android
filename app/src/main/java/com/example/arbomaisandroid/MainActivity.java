package com.example.arbomaisandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arbomaisandroid.database.LocalDatabase;
import com.example.arbomaisandroid.entities.TipoUsuario;
import com.example.arbomaisandroid.entities.Usuario;
import com.example.arbomaisandroid.utils.Hashing;

public class MainActivity extends AppCompatActivity {
    private LocalDatabase db;
    private EditText edtNome;
    private EditText edtSenha;
    private Button btnEntrar;
    private Button btnCriar;


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
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario(edtNome.getText().toString(), edtSenha.getText().toString(),
                        TipoUsuario.STANDARD, false);
                Toast.makeText(getApplicationContext(),
                        "Aguarde o administrador confirmar seu cadastro.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void insertFirstAdmin() {
        cadastrarUsuario("ADM", "ADM", TipoUsuario.ADMIN, true);
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
    }
}