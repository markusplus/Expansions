package com.example.expansions;

import android.util.Log;
import android.widget.ImageButton;

import java.util.List;

public class Poblado {

    private int posicion, valor, id;
    private ImageButton img;
    private Jugador jugador;

    public Poblado(int posicion, ImageButton img) {
        this.posicion = posicion;
        this.valor = 0;
        this.img = img;
        this.id = Datos.idPoblado;
        Datos.idPoblado++;
    }
    public Poblado(int posicion, int id) {  //Para los poblados (fantasma) - Poblados duplicados que aparecen en otra celda
        this.posicion = posicion;
        this.valor = 0;
        this.id = id;
    }
    public int getPosicion() {
        return posicion;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {this.id = id;}
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    public void colocaPrimerosPoblados(Partida partida){
        this.img.setBackgroundResource(R.mipmap.poblado);
        this.valor = 1; //Pasa a colocado
        this.jugador = partida.getJugadorActivo();
        this.jugador.restaPoblado();
        this.jugador.sumaPuntoVictoria();
        Log.e("Poblado colocado", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado un poblado en la posicion " + String.valueOf(this.posicion));
        JuegoActivity.imprimeConsola(partida.getContext(), "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado un poblado en la posicion " + String.valueOf(this.posicion));
        for(int i = 0; i < Datos.tablero.size(); i++) {
            List<Poblado> poblados = Datos.tablero.get(i).getPoblados();
            for(int j = 0; j < poblados.size(); j++) {
                if(poblados.get(j).getId() == this.id && poblados.get(j).getValor() == 0) {
                    poblados.get(j).setValor(1);
                    poblados.get(j).setJugador(partida.getJugadorActivo());
                    Log.e("Poblado colocado", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado un poblado en la posicion " + String.valueOf(poblados.get(j).posicion) + " de la celda x: "+Datos.tablero.get(i).getPosX()+", "+Datos.tablero.get(i).getPosY());
                    JuegoActivity.imprimeConsola(partida.getContext(), "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado un poblado en la posicion " + String.valueOf(poblados.get(j).posicion) + " de la celda x: "+Datos.tablero.get(i).getPosX()+", "+Datos.tablero.get(i).getPosY());
                }
            }
        }
    }

    public void colocaPoblado(Partida partida){
        this.jugador = partida.getJugadorActivo();
        if(partida.getConstrucciones().construye(this.jugador, Construcciones.POBLADO)) {
            this.img.setBackgroundResource(R.mipmap.poblado);
            this.jugador.restaPoblado();
            this.valor = 1; //Pasa a colocado
            Log.e("Poblado colocado", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado un poblado en la posicion " + String.valueOf(this.posicion));

            for(int i = 0; i < Datos.tablero.size(); i++) {
                List<Poblado> poblados = Datos.tablero.get(i).getPoblados();
                for(int j = 0; j < poblados.size(); j++) {
                    if(poblados.get(j).getId() == this.id) {
                        poblados.get(j).setValor(1);
                        poblados.get(j).setJugador(partida.getJugadorActivo());
                        Log.e("Poblado colocado", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado un poblado en la posicion " + String.valueOf(this.posicion) + " de la celda x: "+Datos.tablero.get(i).getPosX()+", "+Datos.tablero.get(i).getPosY());
                    }
                }
            }
        }
        else
            Log.e("Poblado fallido", "El jugador " + String.valueOf(partida.getTurno()) + " no tiene suficiente materia prima");
    }
    public void colocaCiudad(Partida partida){
        this.img.setBackgroundResource(R.mipmap.ciudad);
        this.img.setAlpha(1f);
        this.img.setEnabled(true);
        this.valor = 2; //Pasa a colocado
        this.jugador = partida.getJugadorActivo();
        this.jugador.restaCiudad();
        partida.getConstrucciones().construye(this.jugador, Construcciones.CIUDAD);
        Log.e("Ciudad colocada", "El jugador " + String.valueOf(partida.getTurno()) + " ha colocado una ciudad en la posicion " + String.valueOf(this.posicion));
    }
    public ImageButton getImageButton(){
        return this.img;
    }
    public Jugador getJugador() {
        return this.jugador;
    }
}
