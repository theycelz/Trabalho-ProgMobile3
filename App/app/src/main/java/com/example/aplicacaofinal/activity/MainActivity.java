package com.example.aplicacaofinal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.aplicacaofinal.R;
import com.example.aplicacaofinal.helper.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class MainActivity extends AppCompatActivity {
    private Button botaoAcessar;
    private EditText campoSenha,campoEmail;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutentificacao();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                if( !email.isEmpty()) {
                    if(!senha.isEmpty()) {
                        //verificar o estado de switch
                        if(tipoAcesso.isChecked()) {//cadastro
                            autenticacao.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sucesound);
                                        mediaPlayer.start();
                                        Intent intent = new Intent(MainActivity.this, MeusObjetosActivity.class);
                                        startActivity(intent);
                                    } else {
                                        String erroExcecao = "";
                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e) {
                                            erroExcecao = "Digite uma senha mais forte";

                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            erroExcecao = "E-mail inválido";
                                        }catch(FirebaseAuthUserCollisionException e){
                                            erroExcecao = "Essa conta já está cadastrada";
                                        }catch (Exception e){
                                            erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.erro);
                                        mediaPlayer.start();
                                    }
                                    //direcionar após
                            }
                            });
                        }else{ //login
                            autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sucesound);
                                                mediaPlayer.start();
                                                Intent intent = new Intent(MainActivity.this, MeusObjetosActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(getApplicationContext(), "E-mail ou Senha incorretos. Verifique se digitou corretamente.", Toast.LENGTH_SHORT).show();
                                                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.erro);
                                                mediaPlayer.start();
                                            }
                                        }
                                    }
                            );

                        }

                    }else{
                        Toast.makeText(MainActivity.this, "Preencha a Senha!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Preencha o E-mail!",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.editcadastroemail);
        campoSenha = findViewById(R.id.editsenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switch1);
    }



}



























