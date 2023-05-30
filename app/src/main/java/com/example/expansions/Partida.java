package com.example.expansions;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Random;

public class Partida {
    private int turnoGlobal, turno, numJugadores;
    private Random rnd;
    private Jugador[] jugadores;
    private Construcciones construcciones;
    private Context context;

    public Partida(Context context, int numJugadores) {
        this.context = context;
        this.turnoGlobal = 0;
        rnd = new Random();
        this.numJugadores = numJugadores;
        this.jugadores = new Jugador[numJugadores];
        for(int i = 0; i < numJugadores; i++)
            this.jugadores[i] = new Jugador(i);
        this.turno = rnd.nextInt(numJugadores);
        construcciones = new Construcciones();
        Log.e("Jugador inicio", "Empieza el jugador " + String.valueOf(this.turno));
        JuegoActivity.imprimeConsola(context, "Empieza el jugador " + String.valueOf(this.turno));
    }
    public Context getContext() {
        return this.context;
    }
    public int getTurno() {
        return this.turno;
    }
    public Construcciones getConstrucciones() {
        return construcciones;
    }

    public Jugador getJugadorActivo() {
        return jugadores[this.turno];
    }

    public int getNumJugadores() {
        return this.numJugadores;
    }

    public int tirada(Datos datos) {
        int dado1 = rnd.nextInt(5)+1;
        int dado2 = rnd.nextInt(5)+1;
        int tirada = dado1 + dado2;
        Log.e("dado", String.valueOf(tirada));
        List<Celda> celda = datos.getCelda(tirada);
        int[][] poblados = getPobladosTirada(datos, numJugadores, tirada);
        for(int i = 0; i < jugadores.length; i++) {
            for(int j = 0; j < Datos.getNumeroRecursos(); j++) {
                jugadores[i].setRecurso(j, poblados[i][j]);
                if(poblados[i][j] != 0) {
                    Log.e("Recurso", "El jugador " + i + " ha obtenido " + poblados[i][j] + " de " + Datos.getMateriaString(j));
                    JuegoActivity.imprimeConsola(context, "El jugador " + i + " ha obtenido " + poblados[i][j] + " de " + Datos.getMateriaString(j));
                }
            }
        }
        return tirada;
    }
    public int[][] getPobladosTirada(Datos datos, int numJugadores, int tirada) {
        int pobladosTirada[][] = new int[numJugadores][Datos.getNumeroRecursos()];
        Poblado poblado;
        if (tirada != 7) {
            List<Celda> celdas = datos.getCelda(tirada);
            for(int j = 0; j < celdas.size(); j++) {
                for (int i = 0; i < celdas.get(j).getPoblados().size(); i++) {
                    poblado = celdas.get(j).getPoblados().get(i);
                    if (poblado.getValor() > 0) {
                        pobladosTirada[poblado.getJugador().getNumero()][celdas.get(j).getRecurso()] += poblado.getValor();
                    }
                }
            }
        }
        return pobladosTirada;
    }

    public void cambiaTurno() {
        if(this.turnoGlobal != 1){
            if(this.turno == 0)
                this.turno = 1;
            else
                this.turno = 0;
        }
        turnoGlobal++;
        JuegoActivity.tiempoActual = Datos.obtenerHoraActual();
        Log.e("Cambio turno", "Turno "+turnoGlobal+". Le toca al jugador " + this.turno);
        JuegoActivity.imprimeConsola(context, "Turno "+turnoGlobal+". Le toca al jugador " + this.turno);
        Log.e("Materias primas", getJugadorActivo().toString());
    }
    public int getTurnoGlobal() {
        return this.turnoGlobal;
    }
}
