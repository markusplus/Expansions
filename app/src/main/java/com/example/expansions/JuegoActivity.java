package com.example.expansions;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class JuegoActivity extends AppCompatActivity {
    private LinearLayout verticalLayout;
    public static LinearLayout consolaLayout;
    public static TextView textoHora;
    private ConstraintLayout fondo;
    private ConstraintLayout contenedor_consola;
    private int izquierda = 0;
    public static int f = 5;
    public static String tiempoActual;
    private Datos datos;
    private Partida partida;
    public static FloatingActionButton next_fav, poblado_fav, carretera_fav, ciudad_fav, consola_fav;
    public static ExtendedFloatingActionButton tira_fav;
    private int cont = 0;
    private Context context;
    private Dialog dialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
        context = this;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.consola);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        verticalLayout = findViewById(R.id.verticalLayout);
        consolaLayout = dialog.findViewById(R.id.layoutConsola);
        fondo = (ConstraintLayout) findViewById(R.id.fondo);
        fondo.setBackgroundColor(Color.parseColor("#38546a"));
        datos = new Datos();
        partida = new Partida(this,2);
        next_fav = findViewById(R.id.fav);
        //next_fav.setEnabled(false);
        tira_fav = findViewById(R.id.tira);
        //tira_fav.setEnabled(false);
        poblado_fav = findViewById(R.id.poblado_fav);
        //poblado_fav.setEnabled(false);
        carretera_fav = findViewById(R.id.carretera_fav);
        //carretera_fav.setEnabled(false);
        ciudad_fav = findViewById(R.id.ciudad_fav);
        //ciudad_fav.setEnabled(false);
        consola_fav = findViewById(R.id.consola_fav);
        textoHora = dialog.findViewById(R.id.textoHora);
        tiempoActual = Datos.obtenerHoraActual();
        consola_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    textoHora.setText(Datos.obtenerTiempoTranscurrido(tiempoActual));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                dialog.show();
            }
        });
        next_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partida.cambiaTurno();
                if(partida.getTurnoGlobal() < 3) {
                    next_fav.setEnabled(false);
                    activaPoblados();
                }
                else {
                    tira_fav.setEnabled(true);
                    tira_fav.setIconResource(R.mipmap.dado_fav);
                    next_fav.setEnabled(false);
                    poblado_fav.setEnabled(false);
                    carretera_fav.setEnabled(false);
                    ciudad_fav.setEnabled(false);
                }
            }
        });

        tira_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tira_fav.setIcon(null);
                tira_fav.setText(String.valueOf(partida.tirada(datos)));
                tira_fav.setEnabled(false);
                next_fav.setEnabled(true);
                Datos.compruebaAcciones(partida);
            }
        });

        poblado_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activaPoblados();
            }
        });
        carretera_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activaCarreterasBoton();
            }
        });
        ciudad_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activaCiudades();
            }
        });
        iniciaHexagonos(MainActivity.tamanoTablero, verticalLayout);
    }

    public void iniciaHexagonos(double filas, LinearLayout layout) {
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
                else if((i > 0 && i < Math.round(filas/2)) && j==0) {
                    tamanoPoblaciones = 4;
                    tamanoCarreteras = 5;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0, null, false);
                }
                else if(i > 0 && i < Math.round(filas/2)) {
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
                else if((i >= filas/2 && i < filas)&& j==0) {
                    tamanoPoblaciones = 3;
                    tamanoCarreteras = 4;
                    constraint = (ConstraintLayout) inflater.inflate(R.layout.casillas_sin0_sin5, null, false);
                }
                else if(i >= filas/2 && i < filas){
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
                Celda celdaAuxiliar = new Celda(datos, partida, i, j, constraint, tamanoPoblaciones, tamanoCarreteras);
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
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() + 300);
            }
            else if(i==1) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() + 150);
            }
            else if(i==3) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() - 150);
            }
            else if(i==4) {
                layoutAuxiliar.setTranslationY(layoutAuxiliar.getTranslationY() - 300);
            }
            layout.addView(layoutAuxiliar);
        }
    }

    public void creaBotonesPoblado(Celda celdaAuxiliar) {
        for(int z = 0; z < celdaAuxiliar.getTamanoPoblados(); z++) {
            int finalZ = z;
            celdaAuxiliar.getPoblado(z).getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(partida.getTurnoGlobal() < 3) {
                        celdaAuxiliar.getPoblado(finalZ).colocaPrimerosPoblados(partida);
                        activaCarreteras(celdaAuxiliar, celdaAuxiliar.getPoblado(finalZ));
                        desactivaPoblados();
                    }
                    else {
                        if(celdaAuxiliar.getPoblado(finalZ).getValor() == 0) {
                            celdaAuxiliar.getPoblado(finalZ).colocaPoblado(partida);
                            desactivaPoblados();
                            poblado_fav.setEnabled(false);
                            Datos.compruebaAcciones(partida);
                        }
                        else if(celdaAuxiliar.getPoblado(finalZ).getValor() == 1) {
                            celdaAuxiliar.getPoblado(finalZ).colocaCiudad(partida);
                            desactivaCiudades();
                            ciudad_fav.setEnabled(false);
                            Datos.compruebaAcciones(partida);
                        }
                    }
                }
            });
        }
    }

    public void creaBotonesCarretera(Celda celdaAuxiliar) {
        for(int z = 0; z < celdaAuxiliar.getTamanoCarreteras(); z++) {
            int finalZ = z;
            celdaAuxiliar.getCarretera(z).getImageButton().setEnabled(false);
            celdaAuxiliar.getCarretera(z).getImageButton().setAlpha(0f);
            celdaAuxiliar.getCarretera(z).getImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(partida.getTurnoGlobal() < 3)
                        if(partida.getTurnoGlobal() != 1) {
                            celdaAuxiliar.getCarretera(finalZ).colocaCarreteraInicial(partida);
                            desactivaCarreteras();
                            next_fav.setEnabled(true);
                            if(partida.getTurnoGlobal() == 2) {
                                tira_fav.setEnabled(true);
                                next_fav.setEnabled(false);
                            }
                        }
                        else{
                            celdaAuxiliar.getCarretera(finalZ).colocaCarreteraInicial(partida);
                            desactivaCarreteras();
                            if(cont == 0)
                                activaPoblados();
                            else
                                next_fav.setEnabled(true);
                            cont++;
                        }
                    else{
                        celdaAuxiliar.getCarretera(finalZ).colocaCarretera(partida);
                        desactivaCarreteras();
                        carretera_fav.setEnabled(false);
                        Datos.compruebaAcciones(partida);
                    }
                }
            });
        }
    }

    public void activaCarreteras(Celda celda, Poblado poblado) {
        celda.getCarreteraPos(poblado.getPosicion()).getImageButton().setAlpha(1f);
        celda.getCarreteraPos(poblado.getPosicion()).getImageButton().setEnabled(true);
        celda.getCarreteraPos(Datos.getMenosUno(poblado.getPosicion())).getImageButton().setAlpha(1f);
        celda.getCarreteraPos(Datos.getMenosUno(poblado.getPosicion())).getImageButton().setEnabled(true);
    }
    public void activaCarreterasBoton() {
        List<Celda> tablero = datos.getTablero();
        for(int i = 0; i < tablero.size(); i++) {
            Celda celda = tablero.get(i);
            for(int j = 0; j < celda.getPoblados().size(); j++) {
                if(celda.getPobladoPos(j).getJugador() != null && celda.getPobladoPos(j).getJugador().getNumero() == partida.getJugadorActivo().getNumero()){
                    if(celda.getCarreteraPos(j) != null) {
                        if (celda.getCarreteraPos(j).getValor() == 0 && celda.getCarreteraPos(j).getImageButton() != null) {
                            celda.getCarreteraPos(j).getImageButton().setEnabled(true);
                            celda.getCarreteraPos(j).getImageButton().setAlpha(1f);
                        }
                        if(celda.getCarreteraPos(Datos.getMenosUno(j)) != null) {
                            if (celda.getCarreteraPos(Datos.getMenosUno(j)).getValor() == 0 && celda.getCarreteraPos(Datos.getMenosUno(j)).getImageButton() != null) {
                                celda.getCarreteraPos(Datos.getMenosUno(j)).getImageButton().setEnabled(true);
                                celda.getCarreteraPos(Datos.getMenosUno(j)).getImageButton().setAlpha(1f);
                            }
                        }
                    }
                }
            }
        }
    }
    public void desactivaCarreteras() {
        List<Celda> tablero = datos.getTablero();
        for(int i = 0; i < tablero.size(); i++) {
            Celda celda = tablero.get(i);
            for(int j = 0; j < celda.getCarreteras().size(); j++) {
                if(celda.getCarreteraPos(j) != null && celda.getCarreteraPos(j).getImageButton() != null && celda.getCarreteraPos(j).getValor() == 0) {
                    celda.getCarreteraPos(j).getImageButton().setEnabled(false);
                    celda.getCarreteraPos(j).getImageButton().setAlpha(0f);
                }
            }
        }
    }
    public void activaPoblados() {
        List<Celda> tablero = datos.getTablero();
        for(int i = 0; i < tablero.size(); i++) {
            Celda celda = tablero.get(i);
            for(int j = 0; j < celda.getPoblados().size(); j++) {
                if(celda.getPobladoPos(j) != null && celda.getPobladoPos(j).getImageButton() != null && celda.getPobladoPos(j).getValor() == 0) {
                    celda.getPobladoPos(j).getImageButton().setEnabled(true);
                    celda.getPobladoPos(j).getImageButton().setAlpha(1f);
                }
            }
        }
    }
    public void activaCiudades() {
        List<Celda> tablero = datos.getTablero();
        for(int i = 0; i < tablero.size(); i++) {
            Celda celda = tablero.get(i);
            for(int j = 0; j < celda.getPoblados().size(); j++) {
                if(celda.getPobladoPos(j) != null && celda.getPobladoPos(j).getImageButton() != null && celda.getPobladoPos(j).getValor() == 1) {
                    if(celda.getPobladoPos(j).getJugador() == partida.getJugadorActivo()) {
                        celda.getPobladoPos(j).getImageButton().setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
                        celda.getPobladoPos(j).getImageButton().setEnabled(true);
                        celda.getPobladoPos(j).getImageButton().setAlpha(1f);
                    }
                }
            }
        }
    }
    public void desactivaCiudades() {
        List<Celda> tablero = datos.getTablero();
        for(int i = 0; i < tablero.size(); i++) {
            Celda celda = tablero.get(i);
            for(int j = 0; j < celda.getPoblados().size(); j++) {
                if(celda.getPobladoPos(j) != null && celda.getPobladoPos(j).getImageButton() != null && (celda.getPobladoPos(j).getValor() == 1 || celda.getPobladoPos(j).getValor() == 2)) {
                    celda.getPobladoPos(j).getImageButton().setBackgroundTintList(null);
                }
            }
        }
    }
    public void desactivaPoblados() {
        List<Celda> tablero = datos.getTablero();
        for(int i = 0; i < tablero.size(); i++) {
            Celda celda = tablero.get(i);
            for(int j = 0; j < celda.getPoblados().size(); j++) {
                if(celda.getPobladoPos(j) != null && celda.getPobladoPos(j).getImageButton() != null && celda.getPobladoPos(j).getValor() == 0) {
                    celda.getPobladoPos(j).getImageButton().setEnabled(false);
                    celda.getPobladoPos(j).getImageButton().setAlpha(0f);
                }
            }
        }
    }

    public static void imprimeConsola(Context context, String mensaje) {
        TextView lineaTexto = new TextView(context);
        lineaTexto.setText(mensaje + "\n----------------------------------------------------");
        lineaTexto.setTextColor(context.getResources().getColor(R.color.white));
        consolaLayout.addView(lineaTexto);
    }

    public static void desactivaBotones() {
        poblado_fav.setEnabled(false);
        carretera_fav.setEnabled(false);
        ciudad_fav.setEnabled(false);
        tira_fav.setEnabled(false);
        next_fav.setEnabled(false);
    }
}
