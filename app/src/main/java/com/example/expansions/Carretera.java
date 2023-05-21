package com.example.expansions;

import android.widget.ImageButton;

public class Carretera {
    private int posicion, valor;
    private ImageButton img;

    public Carretera(int posicion, ImageButton img) {
        this.posicion = posicion;
        this.valor = -1;
        this.img = img;
    }

    public int getPosicion() {
        return this.posicion;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void colocaCarretera(){
        this.img.setBackgroundResource(R.drawable.carretera);
    }
    public ImageButton getImageButton(){
        return this.img;
    }
}
