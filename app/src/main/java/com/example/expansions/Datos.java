package com.example.expansions;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class Datos {
    public static int idPoblado;
    public static final int NUMERO_RECURSOS = 5;
    public static final int MADERA = 0;
    public static final int OVEJA = 1;
    public static final int TRIGO = 2;
    public static final int LADRILLO = 3;
    public static final int PIEDRA = 4;
    public static List<Celda> tablero;
    private List<Integer> numeros;  //Lista que contiene todos los numeros a asignar en las celdas
    private List<Integer> backgrounds;
    private int pobladosJugador = 0;
    public Datos(){
        rellenaBackgrounds();
        rellenaNumeros();
        tablero = new LinkedList<>();
    }

    public Celda getCeldaXY(int x, int y) {
        Celda celda = null;
        if(!tablero.isEmpty()) {
            for (int i = 0; i < tablero.size(); i++) {
                if (tablero.get(i).getPosX() == x && tablero.get(i).getPosY() == y)
                    celda = tablero.get(i);
            }
        }
        return celda;
    }

    public List<Celda> getCelda(int numero) {
        List<Celda> celda = new ArrayList<>();
        if (!tablero.isEmpty()) {
            for (int i = 0; i < tablero.size(); i++) {
                if (tablero.get(i).getNumero() == numero)
                    celda.add(tablero.get(i));
            }
        }
        return celda;
    }

    public List<Integer> getNumeros() {
        return this.numeros;
    }
    public List<Integer> getBackgrounds() {
        return this.backgrounds;
    }
    public List<Celda> getTablero() {
        return this.tablero;
    }
    public void rellenaBackgrounds(){
        int tamano = 18;
        this.backgrounds = new ArrayList<>();
        switch(MainActivity.tamanoTablero) {
            case 5:
                tamano = tamano * 1;
            case 7:
                tamano = tamano * 2;
            case 8:
                tamano = tamano * 3;
        }
        int factor = tamano / 18;
        for(int i = 0; i < 4 * factor; i++) {
            this.backgrounds.add(R.mipmap.madera);
            this.backgrounds.add(R.mipmap.oveja);
            this.backgrounds.add(R.mipmap.trigo);
        }
        for(int i = 0; i < 3 * factor; i++) {
            this.backgrounds.add(R.mipmap.ladrillo);
            this.backgrounds.add(R.mipmap.piedra);
        }
    }
    public void rellenaNumeros(){
        numeros = new ArrayList<>();
        int tamano = 18;
        switch(MainActivity.tamanoTablero) {
            case 5:
                tamano = tamano * 1;
            case 7:
                tamano = tamano * 2;
            case 8:
                tamano = tamano * 3;
        }
        int factor = tamano / 18;
        for(int i = 0; i < 1 * factor; i++) {
            numeros.add(2);
            numeros.add(12);
        }
        for(int i = 0; i < 2 * factor; i++) {
            numeros.add(3);
            numeros.add(3);
            numeros.add(4);
            numeros.add(4);
            numeros.add(5);
            numeros.add(5);
            numeros.add(6);
            numeros.add(6);
            numeros.add(8);
            numeros.add(8);
            numeros.add(9);
            numeros.add(9);
            numeros.add(10);
            numeros.add(10);
            numeros.add(11);
            numeros.add(11);
        }
    }
    public static int getMenosDos(int num) {
        int result = num - 2;
        if(result < 0)
            result = 5 + result + 1;
        return result;
    }
    public static int getMasDos(int num) {
        int result = num + 2;
        if(result > 5)
            result = result - 6;
        return result;
    }

    public static int getMenosUno(int num) {
        int result = num - 1;
        if(result < 0)
            result = 5;
        return result;
    }
    public static int getNumeroRecursos() {
        return NUMERO_RECURSOS;
    }
    public static String getMateriaString(int n) {
        String result = "";
        if(n == 0)
            result = "madera";
        else if(n == 1)
            result = "oveja";
        else if(n == 2)
            result = "trigo";
        else if(n == 3)
            result = "ladrillo";
        else if(n == 4)
            result = "piedra";
        return result;
    }
    public static void compruebaAcciones(Partida partida) {
        if(partida.getJugadorActivo().puedeConstruir(Construcciones.POBLADO))
            JuegoActivity.poblado_fav.setEnabled(true);
        if(partida.getJugadorActivo().puedeConstruir(Construcciones.CARRETERA))
            JuegoActivity.carretera_fav.setEnabled(true);
        if(partida.getJugadorActivo().puedeConstruir(Construcciones.CIUDAD))
            JuegoActivity.ciudad_fav.setEnabled(true);
        if(partida.getJugadorActivo().getPuntosVictoria() == MainActivity.puntosVictoria) {
            JuegoActivity.imprimeConsola(partida.getContext(), "El jugador " + partida.getJugadorActivo().getNumero() + " ha ganado");
        }

    }
    @SuppressLint("SimpleDateFormat")
    public static String obtenerFechaConFormato(String formato) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
    public static String obtenerHoraActual() {
        String formato = "HH:mm:ss";
        return obtenerFechaConFormato(formato);
    }

    public static String obtenerTiempoTranscurrido(String tiempo) throws ParseException {
        String formato = "HH:mm:ss";
        String actual = "";
        long result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date1 = sdf.parse(tiempo);
        long milis1 = date1.getTime();
        actual = obtenerHoraActual();
        Date date2 = sdf.parse(actual);
        long milis2 = date2.getTime();
        result = milis2 - milis1;
        Date date3 = new Date(result);
        return sdf.format(date3);
    }
}
