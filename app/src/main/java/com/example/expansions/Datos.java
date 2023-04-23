package com.example.expansions;

import java.util.List;

public class Datos {

    public static List<Celda> tablero;

    public Celda getCelda(int x, int y) {
        Celda celda = null;
        for(int i = 0; i < tablero.size(); i++) {
            if(tablero.get(i).getPosX() == x && tablero.get(i).getPosY() == y)
                celda = tablero.get(i);
        }
        return celda;
    }

}
