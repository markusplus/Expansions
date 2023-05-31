package com.example.expansions;

import android.util.Log;

import java.util.List;

public class Jugador {

    private static final int MADERA = 0, OVEJA = 1, TRIGO = 2, LADRILLO = 3, PIEDRA = 4;
    private int[] materias_primas = {0, 0, 0, 0, 0};
    private int numeroPoblados, numeroCarreteras, numeroCiudades;
    private static final int MAXPOBLADOS = 5;
    private static final int MAXCARRETERAS = 15;
    private static final int MAXCIUDADES = 4;
    private int numero;
    private int puntosVictoria;

    public Jugador(int numero) {
        this.numero = numero;
        numeroPoblados = MAXPOBLADOS;
        numeroCarreteras = MAXCARRETERAS;
        numeroCiudades = MAXCIUDADES;
        this.puntosVictoria = 0;
        Log.e("jugador", "Se inicia un jugador");
    }

    public void setRecurso(int recurso, int value) {
        this.materias_primas[recurso] += value;
    }
    public int getNumero() {
        return this.numero;
    }
    public int getPuntosVictoria() {
        return puntosVictoria;
    }
    public void sumaPuntoVictoria() {
        this.puntosVictoria++;
    }
    public int getMadera() {
        return materias_primas[Datos.MADERA];
    }
    public int getOveja() {
        return materias_primas[Datos.OVEJA];
    }
    public int getTrigo() {
        return materias_primas[Datos.TRIGO];
    }
    public int getLadrillo() {
        return materias_primas[Datos.LADRILLO];
    }
    public int getPiedra() {
        return materias_primas[Datos.PIEDRA];
    }
    public int[] getMaterias_primas() {
        return materias_primas;
    }
    public void restaPoblado() {
        this.numeroPoblados--;
    }
    public void restaCarretera() {
        this.numeroCarreteras--;
    }
    public void restaCiudad() {
        this.numeroCiudades--;
    }
    public boolean puedeConstruir(int[] materiales) {
        boolean result = true;
        for(int i = 0; i < materiales.length; i++) {
            if (this.materias_primas[i] < materiales[i])
                result = false;
        }
        return result;
    }
    public int getNumeroPoblados() {
        int result = 0;
        if(Datos.getPobladosJugador(this) != null)
            Datos.getPobladosJugador(this).size();
        return result;
    }
    public String toString() {
        return "Jugador: "+numero+". Madera: "+materias_primas[MADERA]+", Oveja: "+materias_primas[OVEJA]+", Trigo: "+materias_primas[TRIGO]+", Ladrillo: "+materias_primas[LADRILLO]+", Piedra: "+materias_primas[PIEDRA];
    }
}
