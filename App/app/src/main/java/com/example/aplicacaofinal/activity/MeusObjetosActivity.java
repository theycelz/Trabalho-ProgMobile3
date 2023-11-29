package com.example.aplicacaofinal.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*import com.example.aplicacaofinal.adapter.AdapterObjetos;*/
import com.example.aplicacaofinal.databinding.ActivityMeusObjetosBinding;
import com.example.aplicacaofinal.R;
import com.example.aplicacaofinal.helper.ConfiguracaoFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MeusObjetosActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMeusObjetosBinding binding;

    /*private RecyclerView recyclerObjetos;
    private List<Objeto> objetos = new ArrayList<>();

    private AdapterObjetos adapterObjetos;
    private DatabaseReference objetoUsuarioRef;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_objetos);

        //configurações iniciais
        /*objetoUsuarioRef = ConfiguracaoFirebase.getFirebase().child("meus objetos").child(ConfiguracaoFirebase.getIdUsuario());

        inicializarComponentes();*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recycler view
        /*recyclerObjetos.setLayoutManager(new LinearLayoutManager(this));
        recyclerObjetos.setHasFixedSize(true);
        adapterObjetos = new AdapterObjetos(objetos,this);
        recyclerObjetos.setAdapter(adapterObjetos);
        recuperarObjetos();*/
    }

    /*private void recuperarObjetos(){
            objetoUsuarioRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    objetos.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        objetos.add(ds.getValue(Objeto.class));
                    }
                    Collections.reverse(objetos);
                    adapterObjetos.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

   public void  inicializarComponentes(){
        recyclerObjetos = findViewById(R.id.recyclerObjetos);
    }*/

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
















