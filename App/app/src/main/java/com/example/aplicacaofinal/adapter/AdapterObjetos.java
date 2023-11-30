package com.example.aplicacaofinal.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
/*import com.google.firebase.firestore.core.View;
import com.google.firebase.database.core.view.View;*/
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacaofinal.R;
//import com.squareup.picasso.Picasso;

/*
public class AdapterObjetos extends RecyclerView.Adapter<AdapterObjetos.MyViewHolder> {

    private List<Objeto> objetos;
    private Context context;

    public AdapterObjetos(List<Objeto> objetos, Context context) {
        this.objetos = objetos;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_objeto, parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Objeto objeto = objetos.get(position);
        holder.titulo.setText(objeto.getTitulo());
        holder.valor.setText(objeto.getValor());

        //recuperar imagem
        List<String> urlFotos objeto.getFotos();
        String urlCapa  = urlFotos.get(0);
        Picasso.get().load(urlCapa).into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return objetos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView valor;
        ImageView foto;

        public MyViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            valor = itemView.findViewById(R.id.imageObjeto);
        }
    }
}*/
