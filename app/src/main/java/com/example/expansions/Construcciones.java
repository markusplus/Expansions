package com.example.expansions;

import android.util.Log;

public class Construcciones {

    public static final int[] CARRETERA = {1, 0, 0, 1, 0};
    public static final int[] POBLADO = {1, 1, 1, 1, 0};
    public static final int[] CIUDAD = {0, 0, 2, 0, 3};
    public static final int[] MAGIA = {0, 1, 1, 0, 1};

    public Construcciones() {}

    public boolean construye(Jugador jugador, int[] recurso) {
        boolean result = false;
        int[] materias_primas = null;
        if(jugador.puedeConstruir(recurso)) {
            materias_primas = jugador.getMaterias_primas();
            Log.e("Puede construir", "Se puede");
            for(int i = 0; i < recurso.length; i++){
                materias_primas[i] -= recurso[i];
            }
            result = true;
        }
        return result;
    }
}
