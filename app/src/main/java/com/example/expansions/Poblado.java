package com.example.expansions;

import android.widget.ImageButton;

public class Poblado {

    private int posicion, valor;
    private ImageButton img;

    public Poblado(int posicion, ImageButton img) {
        this.posicion = posicion;
        this.valor = -1;
        this.img = img;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void colocaPoblado(){
        this.img.setBackgroundResource(R.mipmap.poblado);
    }
    public ImageButton getImageButton(){
        return this.img;
    }
}
