package com.example.expansions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button empiezaJuego_button;
    private EditText tamanoTablero_text, puntosVictoria_text, jugadores_text;
    public static int tamanoTablero, puntosVictoria, numeroJugadores;
    private Context context;
    private Intent myIntent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_inicio);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
        myIntent = new Intent(this, JuegoActivity.class);
        context = this;
        empiezaJuego_button = (Button) findViewById(R.id.empiezaJuego_button);
        tamanoTablero_text = (EditText) findViewById(R.id.tamanoTablero_text);
        puntosVictoria_text = (EditText) findViewById(R.id.puntosVictoria_text);
        jugadores_text = (EditText) findViewById(R.id.jugadores_text);
        empiezaJuego_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tamanoTablero_text.getText().length() == 1 && puntosVictoria_text.getText().length() > 0 && jugadores_text.getText().length() > 0) {
                    if(Character.getNumericValue(puntosVictoria_text.getText().charAt(0)) > 2 && Character.getNumericValue(jugadores_text.getText().charAt(0)) >= 2) {
                        tamanoTablero = Character.getNumericValue(tamanoTablero_text.getText().charAt(0));
                        puntosVictoria = Integer.parseInt(puntosVictoria_text.getText().toString());
                        numeroJugadores = Character.getNumericValue(jugadores_text.getText().charAt(0));
                        if (tamanoTablero % 2 != 0 && puntosVictoria != 0) {
                            myIntent.putExtra("key", "");
                            context.startActivity(myIntent);
                        } else {
                            tamanoTablero_text.setText("");
                            puntosVictoria_text.setText("");
                            jugadores_text.setText("");
                        }
                    }
                    else {
                        tamanoTablero_text.setText("");
                        puntosVictoria_text.setText("");
                        jugadores_text.setText("");
                    }
                }
                else {
                    tamanoTablero_text.setText("");
                    puntosVictoria_text.setText("");
                    jugadores_text.setText("");
                }
            }
        });
    }

}