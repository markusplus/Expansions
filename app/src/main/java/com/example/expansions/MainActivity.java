package com.example.expansions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout verticalLayout;
    private ConstraintLayout fondo;
    private int izquierda = 0;

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
            k++;
            izquierda = 0;
            LinearLayout layoutAuxiliar = new LinearLayout(this);
            layoutAuxiliar.setOrientation(LinearLayout.HORIZONTAL);
            layoutAuxiliar.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            for(int j = 0; j < tamanos.get(k); j++) {
                LayoutInflater inflater = LayoutInflater.from(this);
                ConstraintLayout constraint = null;
                if(i==0 && j==0)
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_full, null, false);
                else if(i==0)
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin4, null, false);
                else if((i==1 && j==0) || (i==2 && j==0))
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0, null, false);
                else if(i==1 || i==2) {
                    if(j!=(tamanos.get(k)-1))
                        constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin4_sin5, null, false);
                    else
                        constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin4_sin5_last, null, false);
                }
                else if((i==3 && j==0) || (i==4 && j==0))
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin5, null, false);
                else if(i==3 || i==4){
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin4_sin5, null, false);
                }
                else
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_full, null, false);

                //constraint.getViewById(R.id.layout).setTranslationX(izquierda);
                //izquierda -= 25;
                Celda celdaAuxiliar = new Celda(this, i, j, constraint);
                celdaAuxiliar.getConstraint().findViewById(R.id.material_hexagono).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        celdaAuxiliar.cambiaRecurso();
                    }
                });
                celdaAuxiliar.getConstraint().findViewById(R.id.material_hexagono).setEnabled(false);   //Sólo se puede hacer click si hay que colocar el ladrón
                layoutAuxiliar.addView(celdaAuxiliar.getConstraint());
            }
            if(i==0) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() + 200);
            }
            else if(i==1) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() + 100);
            }
            else if(i==3) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() - 100);
            }
            else if(i==4) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() - 200);
            }
            layout.addView(layoutAuxiliar);
        }
    }
}