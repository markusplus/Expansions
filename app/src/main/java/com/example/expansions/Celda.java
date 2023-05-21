package com.example.expansions;

import android.annotation.SuppressLint;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;
import java.util.List;

public class Celda {
    private int x, y, tamanoPoblados, tamanoCarreteras;
    private int ladron, numero, recurso;
    private int cont=2; //Para saber el indice de la ultima carretera
    private List<Poblado> poblados;
    private List<Carretera> carreteras;
    private Datos datos;
    private Random rnd;
    private ConstraintLayout constraint;

    public Celda(Datos datos, int x, int y, ConstraintLayout constraint, int tamanoPoblados, int tamanoCarreteras) {
        this.datos = datos;
        this.x = x;
        this.y = y;
        this.constraint = constraint;
        cambiaRecurso();
        this.ladron = -1;
        asignaNumero(datos);
        this.poblados = new LinkedList<>();
        this.carreteras = new LinkedList<>();
        this.tamanoPoblados = tamanoPoblados;
        this.tamanoCarreteras = tamanoCarreteras;
        inicializaCarreteras();
        inicializaPoblados();
    }

    private void asignaNumero(Datos datos) {
        if(this.x == 2 && this.y == 2)
            this.numero = 7;
        else {
            rnd = new Random();
            int rand = rnd.nextInt(datos.getNumeros().size());
            this.numero = datos.getNumeros().get(rand);
            datos.getNumeros().remove(rand);
        }
        TextView text = (TextView) this.constraint.getViewById(R.id.numero);
        text.setText(String.valueOf(this.numero));
    }

    public Celda putPosicion(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public void setLadron(int v) {
        this.ladron = v;
    }
    public int getLadron() {
        return this.ladron;
    }
    public int getNumero() {
        return this.numero;
    }
    public int getPosX() {
        return x;
    }
    public int getPosY() {
        return y;
    }
    @SuppressLint("ResourceAsColor")
    public void cambiaRecurso() {
        if(this.x == 2 && this.y == 2) {    //El ladron siempre estara en el centro del tablero
            this.constraint.getViewById(R.id.material_hexagono).setBackgroundResource(R.mipmap.desierto);
            this.recurso = 5;
        }
        else {  //Todos los recursos se limitan para que aparezcan en 4 casillas como maximo, menos el ladrillo y la piedra, que puede aparecer 3 veces como maximo
            this.rnd = new Random();
            List<Integer> backgrounds = this.datos.getBackgrounds();
            int aux = rnd.nextInt(backgrounds.size());
            this.constraint.getViewById(R.id.material_hexagono).setBackgroundResource(backgrounds.get(aux));
            if(backgrounds.get(aux)==R.mipmap.madera)
                this.recurso = 0;
            else if(backgrounds.get(aux)==R.mipmap.oveja)
                this.recurso = 1;
            else if(backgrounds.get(aux)==R.mipmap.trigo)
                this.recurso = 2;
            else if(backgrounds.get(aux)==R.mipmap.ladrillo)
                this.recurso = 3;
            else if(backgrounds.get(aux)==R.mipmap.piedra)
                this.recurso = 4;
            backgrounds.remove(aux);
        }
    }
    public int getRecurso() {
        return this.recurso;
    }
    public ConstraintLayout getConstraint() {
        return constraint;
    }
    public Poblado getPoblado(int i) {
        return poblados.get(i);
    }
    public Carretera getCarretera(int i) {
        return carreteras.get(i);
    }
    public int getTamanoPoblados(){return this.tamanoPoblados;}
    public int getTamanoCarreteras(){return this.tamanoCarreteras;}
    public void inicializaCarreteras() {
        int aux = 0;
        for(int i = 2; i < this.tamanoCarreteras+2; i++) {
            carreteras.add(new Carretera(aux, (ImageButton) this.constraint.getChildAt(i)));
            cont++;
            aux++;
        }
    }
    public void inicializaPoblados() {
        int aux = 0;
        for(int i = cont; i < this.tamanoPoblados+cont; i++) {
            poblados.add(new Poblado(aux, (ImageButton) this.constraint.getChildAt(i)));
            aux++;
        }
    }
}
