package com.example.expansions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class Casilla extends androidx.appcompat.widget.AppCompatImageButton {

    private int posicion, valor;

    @SuppressLint("ResourceAsColor")
    public Casilla(@NonNull Context context, int posicion) {
        super(context);
        this.setImageResource(R.mipmap.ic_launcher);
        this.setBackgroundColor(R.color.black);
        this.setBackground(null);
        this.setScaleType(ScaleType.FIT_XY);
        this.setAdjustViewBounds(false);
        this.setPadding(0,0,0,0);
        this.posicion = posicion;
        this.valor = -1;
        this.setLayoutParams(new LinearLayout.LayoutParams(75, 75));
    }

    public int getPosicion() {
        return posicion;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
