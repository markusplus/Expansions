package com.example.expansions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout verticalLayout;
    private ConstraintLayout fondo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalLayout = findViewById(R.id.verticalLayout);
        fondo = (ConstraintLayout) findViewById(R.id.fondo);
        fondo.setBackgroundColor(Color.parseColor("#38546a"));
        iniciaHexagonos(5, verticalLayout);

    }

    public void iniciaHexagonos(double filas, LinearLayout layout) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels 1080px
        int height = metrics.heightPixels; // alto absoluto en pixels 2220px
        boolean fin = false;
        int k = -1;
        int primeraFila = (int) Math.round(filas/2);
        List<Integer> tamanos = new LinkedList<>();
        tamanos.add(primeraFila);
        for(int i = primeraFila+1; i <= filas; i++)
            tamanos.add(i);

        for(int i = (int) filas-1; i >= primeraFila; i--)
            tamanos.add(i);

        //Log.e("Ses", tamanos.toString());
        Datos.tablero = new LinkedList<>();

        for(int i = 0; i < filas; i++) {
            int izquierda = 0;
            k++;
            LinearLayout layoutAuxiliar = new LinearLayout(this);
            layoutAuxiliar.setOrientation(LinearLayout.HORIZONTAL);
            layoutAuxiliar.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            for(int j = 0; j < tamanos.get(k); j++) {
                /*
                Datos.tablero.add(celdaAuxiliar);
                LayoutInflater inflater = LayoutInflater.from(this);
                LinearLayout layoutAux = (LinearLayout) inflater.inflate(R.layout.casillas, null, false);
                layoutAuxiliar.addView(celdaAuxiliar);
                celdaAuxiliar.addView(layoutAux);*/
                LayoutInflater inflater = LayoutInflater.from(this);
                ConstraintLayout constraint = (ConstraintLayout) inflater.inflate(R.layout.prueba, null, false);
                constraint.findViewById(R.id.layout).setX(izquierda);
                Celda celdaAuxiliar = new Celda(this, i, j);
                izquierda -= 175;
                layoutAuxiliar.addView(celdaAuxiliar);

            }
            layout.addView(layoutAuxiliar);
        }
    }
}