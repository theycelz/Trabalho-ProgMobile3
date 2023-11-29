package com.example.aplicacaofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.aplicacaofinal.databinding.ActivityMeusObjetosBinding;
import com.example.aplicacaofinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeusObjetosActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMeusObjetosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_objetos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_cadastrar) {

            Intent intentCadastrar = new Intent(MeusObjetosActivity.this, CadastrarObjetoActivity.class);
            startActivity(intentCadastrar);
            return true;
        } else if (id == R.id.menu_sair) {

            Intent intentSair = new Intent(MeusObjetosActivity.this, MainActivity.class);
            startActivity(intentSair);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
















