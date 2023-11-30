package com.example.aplicacaofinal.model;

import com.example.aplicacaofinal.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Objeto {
    private String idObjeto;
    private String situacao;
    private String categoria;
    private String titulo;
    private String valor;
    private String descricao;
    private List<String> fotos;

    public Objeto() {
        DatabaseReference objetoref = ConfiguracaoFirebase.getFirebase().child("meus_objetos");
        setIdObjeto( objetoref.push().getKey() );
    }

    public void salvar(){
        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference objetoref = ConfiguracaoFirebase.getFirebase().child("meus_objetos");
        objetoref.child(idUsuario).child(getIdObjeto()).setValue(this);
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
