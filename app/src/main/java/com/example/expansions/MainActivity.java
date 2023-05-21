package com.example.expansions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private Datos datos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalLayout = findViewById(R.id.verticalLayout);
        fondo = (ConstraintLayout) findViewById(R.id.fondo);
        fondo.setBackgroundColor(Color.parseColor("#38546a"));
        datos = new Datos();
        iniciaHexagonos(5, verticalLayout);
        //Log.e("Celda 3,3", String.valueOf(datos.getCelda(4,0).getNumero()));
    }

    public void iniciaHexagonos(double filas, LinearLayout layout) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels 1080px
        int height = metrics.heightPixels; // alto absoluto en pixels 2220px
        boolean fin = false;
        int k = -1;
        int tamanoPoblaciones = 0, tamanoCarreteras = 0;
        int primeraFila = (int) Math.round(filas/2);
        List<Integer> tamanos = new LinkedList<>();
        tamanos.add(primeraFila);
        for(int i = primeraFila+1; i <= filas; i++)
            tamanos.add(i);

        for(int i = (int) filas-1; i >= primeraFila; i--)
            tamanos.add(i);
        List<Celda> tablero = datos.getTablero();
        tablero = new LinkedList<>();
        //Creacion tablero
        for(int i = 0; i < filas; i++) {
            k++;
            LinearLayout layoutAuxiliar = new LinearLayout(this);
            layoutAuxiliar.setOrientation(LinearLayout.HORIZONTAL);
            layoutAuxiliar.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            for(int j = 0; j < tamanos.get(k); j++) {
                LayoutInflater inflater = LayoutInflater.from(this);
                ConstraintLayout constraint = null;
                if(i==0 && j==0) {
                    tamanoPoblaciones = 6;
                    tamanoCarreteras = 6;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_full, null, false);
                }
                else if(i==0) {
                    tamanoPoblaciones = 4;
                    tamanoCarreteras = 5;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin4, null, false);
                }
                else if((i==1 && j==0) || (i==2 && j==0)) {
                    tamanoPoblaciones = 4;
                    tamanoCarreteras = 5;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0, null, false);
                }
                else if(i==1 || i==2) {
                    if(j!=(tamanos.get(k)-1)) {
                        tamanoPoblaciones = 2;
                        tamanoCarreteras = 3;
                        constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin4_sin5, null, false);
                    }
                    else {
                        tamanoPoblaciones = 3;
                        tamanoCarreteras = 4;
                        constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin4_sin5_last, null, false);
                    }
                }
                else if((i==3 && j==0) || (i==4 && j==0)) {
                    tamanoPoblaciones = 3;
                    tamanoCarreteras = 4;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin5, null, false);
                }
                else if(i==3 || i==4){
                    tamanoPoblaciones = 2;
                    tamanoCarreteras = 3;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin4_sin5, null, false);
                }
                else {
                    tamanoPoblaciones = 6;
                    tamanoCarreteras = 6;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_full, null, false);
                }

                //constraint.getViewById(R.id.layout).setTranslationX(izquierda);
                //izquierda -= 25;
                Celda celdaAuxiliar = new Celda(datos, i, j, constraint, tamanoPoblaciones, tamanoCarreteras);
                tablero.add(celdaAuxiliar);
                celdaAuxiliar.getConstraint().findViewById(R.id.material_hexagono).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //celdaAuxiliar.cambiaRecurso();
                    }
                });
                creaBotonesPoblado(celdaAuxiliar);
                creaBotonesCarretera(celdaAuxiliar);
                //celdaAuxiliar.getConstraint().findViewById(R.id.material_hexagono).setEnabled(false);   //Sólo se puede hacer click si hay que colocar el ladrón
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

    private void creaBotonesPoblado(Celda celdaAuxiliar) {
        for(int z = 0; z < celdaAuxiliar.getTamanoPoblados(); z++) {
            int finalZ = z;
            celdaAuxiliar.getPoblado(z).getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    celdaAuxiliar.getPoblado(finalZ).colocaPoblado();
                }
            });
        }
    }

    private void creaBotonesCarretera(Celda celdaAuxiliar) {
        for(int z = 0; z < celdaAuxiliar.getTamanoCarreteras(); z++) {
            int finalZ = z;
            celdaAuxiliar.getCarretera(z).getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    celdaAuxiliar.getCarretera(finalZ).colocaCarretera();
                }
            });
        }
    }
}