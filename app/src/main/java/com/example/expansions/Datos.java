package com.example.expansions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Datos {

    private static List<Celda> tablero;
    private List<Integer> numeros;  //Lista que contiene todos los numeros a asignar en las celdas
    private List<Integer> backgrounds;
    private int pobladosJugador = 0;
    public Datos(){
        rellenaBackgrounds();
        rellenaNumeros();
    }

    public Celda getCelda(int x, int y) {
        Celda celda = null;
        if(!tablero.isEmpty()) {
            for (int i = 0; i < tablero.size(); i++) {
                if (tablero.get(i).getPosX() == x && tablero.get(i).getPosY() == y)
                    celda = tablero.get(i);
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
        this.backgrounds = new ArrayList<>();
        this.backgrounds.add(R.mipmap.madera);
        this.backgrounds.add(R.mipmap.madera);
        this.backgrounds.add(R.mipmap.madera);
        this.backgrounds.add(R.mipmap.madera);
        this.backgrounds.add(R.mipmap.oveja);
        this.backgrounds.add(R.mipmap.oveja);
        this.backgrounds.add(R.mipmap.oveja);
        this.backgrounds.add(R.mipmap.oveja);
        this.backgrounds.add(R.mipmap.trigo);
        this.backgrounds.add(R.mipmap.trigo);
        this.backgrounds.add(R.mipmap.trigo);
        this.backgrounds.add(R.mipmap.trigo);
        this.backgrounds.add(R.mipmap.ladrillo);
        this.backgrounds.add(R.mipmap.ladrillo);
        this.backgrounds.add(R.mipmap.ladrillo);
        this.backgrounds.add(R.mipmap.piedra);
        this.backgrounds.add(R.mipmap.piedra);
        this.backgrounds.add(R.mipmap.piedra);

    }
    public void rellenaNumeros(){
        numeros = new ArrayList<>();
        numeros.add(2);
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
        numeros.add(12);
    }
}
