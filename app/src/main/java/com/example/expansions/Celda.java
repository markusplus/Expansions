package com.example.expansions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import java.util.Random;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;
import java.util.List;

public class Celda extends androidx.constraintlayout.widget.ConstraintLayout {
    private int x, y;
    private int ladron, numero;
    private List<Casilla> casillas;
    private int[] backgrounds = {R.mipmap.ladrillo, R.mipmap.madera, R.mipmap.piedra, R.mipmap.oveja, R.mipmap.trigo};
    private Random rnd;

    public Celda(@NonNull Context context, int x, int y) {
        super(context);
        this.rnd = new Random();
        this.x = x;
        this.y = y;
        this.setBackgroundResource(backgrounds[rnd.nextInt(5)]);
        this.ladron = -1;
        this.numero = -1;
        this.casillas = new LinkedList<>();
        casillas.add(new Casilla(context, 0));
        casillas.add(new Casilla(context, 1));
        casillas.add(new Casilla(context, 2));
        casillas.add(new Casilla(context, 3));
        casillas.add(new Casilla(context, 4));
        casillas.add(new Casilla(context, 5));
    }
    public Celda putPosicion(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void setLadron(int v) {
        this.ladron = v;
    }
    public int getLadron() {
        return this.ladron;
    }
    public void setNumero(int v) {
        this.numero = v;
    }
    public int getNumero() {
        return this.numero;
    }
    public int getPosX() {
        return x;
    }
    public int getPosY() {
        return y;
    }

    public Casilla getCasilla(int i) {
        return casillas.get(i);
    }
}
