package com.example.expansions;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;
import java.util.List;

public class Celda {
    private int x, y, tamanoPoblados, tamanoCarreteras;
    public static final int TAMPOBLADOS = 6;
    private int ladron, numero, recurso;
    private int cont = 2; //Para saber el indice de la ultima carretera
    private List<Poblado> poblados;
    private List<Carretera> carreteras;
    private Datos datos;
    private Partida partida;
    private Random rnd;
    private ConstraintLayout constraint;

    public Celda(Datos datos, Partida partida, int x, int y, ConstraintLayout constraint, int tamanoPoblados, int tamanoCarreteras) {
        this.partida = partida;
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
        if (this.x == 2 && this.y == 2)
            this.numero = 7;
        else {
            rnd = new Random();
            int rand = rnd.nextInt(datos.getNumeros().size());
            this.numero = datos.getNumeros().get(rand);
            //Log.e("numero_celda", "La celda x=" + this.x + ", y="+this.y+" tiene el valor "+this.numero);
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
        if (this.x == 2 && this.y == 2) {    //El ladron siempre estara en el centro del tablero
            this.constraint.getViewById(R.id.material_hexagono).setBackgroundResource(R.mipmap.desierto);
            this.recurso = 5;
        } else {  //Todos los recursos se limitan para que aparezcan en 4 casillas como maximo, menos el ladrillo y la piedra, que puede aparecer 3 veces como maximo
            this.rnd = new Random();
            List<Integer> backgrounds = this.datos.getBackgrounds();
            int aux = rnd.nextInt(backgrounds.size());
            this.constraint.getViewById(R.id.material_hexagono).setBackgroundResource(backgrounds.get(aux));
            if (backgrounds.get(aux) == R.mipmap.madera)
                this.recurso = Datos.MADERA;
            else if (backgrounds.get(aux) == R.mipmap.oveja)
                this.recurso = Datos.OVEJA;
            else if (backgrounds.get(aux) == R.mipmap.trigo)
                this.recurso = Datos.TRIGO;
            else if (backgrounds.get(aux) == R.mipmap.ladrillo)
                this.recurso = Datos.LADRILLO;
            else if (backgrounds.get(aux) == R.mipmap.piedra)
                this.recurso = Datos.PIEDRA;
            backgrounds.remove(aux);
        }
    }

    public int getRecurso() {
        return this.recurso;
    }

    public String getRecursoNombre() {
        String result = "";
        switch (this.recurso) {
            case Datos.MADERA:
                result = "madera";
                break;
            case Datos.OVEJA:
                result = "oveja";
                break;
            case Datos.TRIGO:
                result = "trigo";
                break;
            case Datos.LADRILLO:
                result = "ladrillo";
                break;
            case Datos.PIEDRA:
                result = "piedra";
                break;
        }
        return result;
    }

    public ConstraintLayout getConstraint() {
        return constraint;
    }

    public Poblado getPoblado(int i) {
        Poblado result = null;
        try {
            result = poblados.get(i);
        } catch (IndexOutOfBoundsException e) {
            Log.e("Error", x + ", " + y);
        }
        return result;
    }

    public Poblado getPobladoPos(int pos) {
        Poblado result = null;
        for (int i = 0; i < poblados.size(); i++) {
            if (poblados.get(i).getPosicion() == pos)
                result = poblados.get(i);
        }
        return result;
    }

    public Carretera getCarretera(int i) {
        return carreteras.get(i);
    }

    public Carretera getCarreteraPos(int pos) {
        Carretera result = null;
        for (int i = 0; i < carreteras.size(); i++) {
            if (carreteras.get(i).getPosicion() == pos)
                result = carreteras.get(i);
        }
        return result;
    }

    public int getTamanoPoblados() {
        return this.tamanoPoblados;
    }

    public int getTamanoCarreteras() {
        return this.tamanoCarreteras;
    }

    public void inicializaCarreteras() {
        for (int i = 2; i < this.tamanoCarreteras + 2; i++) {
            String nombre = this.constraint.getResources().getResourceName(this.constraint.getChildAt(i).getId());
            int pos = Character.getNumericValue(nombre.charAt(nombre.length() - 1));
            carreteras.add(new Carretera(pos, (ImageButton) this.constraint.getChildAt(i)));
            cont++;
        }
    }

    public void inicializaPoblados() {
        List<Integer> nums = new ArrayList<>();
        nums.add(0);
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        nums.add(5);
        try {
            for (int i = cont; i < this.tamanoPoblados + cont; i++) {
                String nombre = this.constraint.getResources().getResourceName(this.constraint.getChildAt(i).getId());
                int pos = Character.getNumericValue(nombre.charAt(nombre.length() - 1));
                poblados.add(new Poblado(pos, (ImageButton) this.constraint.getChildAt(i)));
                nums.remove(nums.indexOf(pos));
            }
            if (!nums.isEmpty()) {
                for (int i = 0; i < nums.size(); i++) {
                    if (x == 0) {
                        if (nums.get(i) == 4)
                            poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x, y - 1).getPobladoPos(nums.get(i) - 2).getId()));
                        else
                            poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x, y - 1).getPobladoPos(Datos.getMasDos(nums.get(i))).getId()));
                    }
                    else if (x == 1 || x == 2) {
                        if (y != 0) {
                            if (nums.get(i) == 0)
                                poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y - 1).getPobladoPos(nums.get(i) + 2).getId()));
                            else if (nums.get(i) == 1)
                                poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y).getPobladoPos(Datos.getMasDos(nums.get(i))).getId()));
                            else if (nums.get(i) == 4)
                                poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x, y - 1).getPobladoPos(nums.get(i) - 2).getId()));
                            else if (nums.get(i) == 5)
                                poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y - 1).getPobladoPos(Datos.getMenosDos(nums.get(i))).getId()));
                        }
                        else {
                            if (nums.get(i) == 0) {
                                poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y).getPobladoPos(Datos.getMenosDos(nums.get(i))).getId()));
                            } else if (nums.get(i) == 1) {
                                poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y).getPobladoPos(nums.get(i) + 2).getId()));
                            }
                        }
                    }
                    else if(x > 2) {
                        if (nums.get(i) == 0)
                            poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y).getPobladoPos(nums.get(i) + 2).getId()));
                        else if (nums.get(i) == 1)
                            poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y + 1).getPobladoPos(nums.get(i) + 2).getId()));
                        else if (nums.get(i) == 4)
                            poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x, y - 1).getPobladoPos(nums.get(i) - 2).getId()));
                        else if (nums.get(i) == 5)
                            poblados.add(new Poblado(nums.get(i), datos.getCeldaXY(x - 1, y).getPobladoPos(nums.get(i) - 2).getId()));
                    }
                }
            }
        } catch (NullPointerException e) {
            Log.e("Error", x + ", " + y);
        }
    }

    public List<Poblado> getPoblados() {
        return poblados;
    }
    public List<Carretera> getCarreteras() {
        return carreteras;
    }
}
