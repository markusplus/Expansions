package com.example.expansions;

import android.util.Log;
import android.widget.ImageButton;

public class Carretera {
    private int posicion, valor;
    private ImageButton img;
    private Jugador jugador;

    public Carretera(int posicion, ImageButton img) {
        this.posicion = posicion;
        this.valor = 0;
        this.img = img;
    }

    public int getPosicion() {
        return this.posicion;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void colocaCarreteraInicial(Partida partida){
        this.img.setBackgroundResource(R.drawable.carretera);
        this.valor = 1; //Pasa a colocado
        this.jugador = partida.getJugadorActivo();
        this.jugador.restaCarretera();
        Log.e("Carretera colocada", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado una carretera en la posicion " + String.valueOf(this.posicion));
    }

    public void colocaCarretera(Partida partida){
        if(partida.getConstrucciones().construye(partida.getJugadorActivo(), Construcciones.CARRETERA)) {
            this.img.setBackgroundResource(R.drawable.carretera);
            this.valor = 1; //Pasa a colocado
            this.jugador = partida.getJugadorActivo();
            this.jugador.restaCarretera();
            Log.e("Carretera colocada", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado una carretera en la posicion " + String.valueOf(this.posicion));
        }
    }
    public ImageButton getImageButton(){
        return this.img;
    }
    public Jugador getJugador() {
        return this.jugador;
    }
    public int getValor() {
        return this.valor;
    }
}
