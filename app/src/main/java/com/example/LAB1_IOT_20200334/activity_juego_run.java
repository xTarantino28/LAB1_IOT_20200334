package com.example.LAB1_IOT_20200334;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class activity_juego_run extends AppCompatActivity {

    private String[] palabras;
    private String palabraActual;
    private Random random;
    private TextView[] characterViews; //arreglo de textviews para mostrar guiones segun la cantidad de letras de la palabra

    private LinearLayout palabrasLayout; //contenedor characterViews

    private LetterAdapter adapter;
    private GridView gridView;

    private int contadorCorrecto; //determina las cuentas correctas
    private int nroLetrasPalabra; //longitud de palabra actual del juego

    private ImageView[] partesDelCuerpo;

    private int nroPartesCuerpo = 6;
    private int parteDelCuerpoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_run);

        getSupportActionBar().setTitle("TeleAhorcado");
        palabras = getResources().getStringArray(R.array.palabras);
        palabrasLayout = findViewById(R.id.matchWordLayout);
        gridView = findViewById(R.id.gridLetters);
        random = new Random();
        partesDelCuerpo = new ImageView[nroPartesCuerpo];
        partesDelCuerpo[0] = findViewById(R.id.head);
        partesDelCuerpo[1] = findViewById(R.id.body);
        partesDelCuerpo[2] = findViewById(R.id.right_hand);
        partesDelCuerpo[3] = findViewById(R.id.lefthand);
        partesDelCuerpo[4] = findViewById(R.id.leftleg);
        partesDelCuerpo[5] = findViewById(R.id.rightleg);


        jugarAhorcado();


        Button playAgainButton = findViewById(R.id.button_nuevo_juego);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jugarAhorcado();
            }
        });
    }


    //metodo para empezar el juego.
    private void jugarAhorcado() {
        String nuevaPalabra = palabras[random.nextInt(palabras.length)]; //sortea una palabra del array de palabras. considera el tamanio del array
        while(nuevaPalabra.equals(palabraActual)) {  //el siguiente juego debe ser con una palabra diferente a la anterior
            nuevaPalabra = palabras[random.nextInt(palabras.length)];
        }
        palabraActual = nuevaPalabra;

        characterViews = new TextView[palabraActual.length()]; //tamanio del arreglo de caracteres definido segun la palabra

        palabrasLayout.removeAllViews();
        for(int i=0; i<palabraActual.length(); i++   ) { //recorremos la palabraActual y agregamos las letras al textview
            characterViews[i] = new TextView(this); // inicializa el textview en cada pos del arrelgo de textviews
            characterViews[i].setText(String.valueOf(palabraActual.charAt(i)));
            characterViews[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            characterViews[i].setGravity(Gravity.CENTER);
            characterViews[i].setTextColor(Color.WHITE);
            characterViews[i].setBackgroundResource(R.drawable.letter_bg);
            palabrasLayout.addView(characterViews[i]);
        }
        adapter = new LetterAdapter(this);
        //agregar el adaptador en gridview
        gridView.setAdapter(adapter); //se agregan las letras generadas en buttons hacia el gridview
        contadorCorrecto = 0;
        nroLetrasPalabra = palabraActual.length();
        parteDelCuerpoActual = 0;

        for (int i=0; i<nroPartesCuerpo;i++){
            partesDelCuerpo[i].setVisibility(View.INVISIBLE);
        }

    }


    //metodo para gestionar cada letra presionada
    public void letraPresionada(View view) {
        String letra = ((TextView) view).getText().toString(); //convertimo el button a texto
        char letraCharacter = letra.charAt(0); //obtenemos solo la letra

        //deshabilitar el boton presionado
        view.setEnabled(false);
        boolean letraCorrecta = false; //permitira gestionar si van armandose el ahorcado

        for(int i=0;i<palabraActual.length();i++){
            if(letraCharacter==palabraActual.charAt(i)){
                letraCorrecta = true; //permite gestionar si gano el usuario
                contadorCorrecto++; //la suma final de cuentas correctas debe ser igual a la longitud de la palabra
                characterViews[i].setTextColor(Color.BLACK);
            }
        }


        if (letraCorrecta) {
            if(contadorCorrecto==nroLetrasPalabra) {
                TextView gameTextAlert = findViewById(R.id.gameTextAlert);
                gameTextAlert.setText("Ganó / Terminó en " + "time" + "s");
                gameTextAlert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        } else if (parteDelCuerpoActual<nroPartesCuerpo) {
            partesDelCuerpo[parteDelCuerpoActual].setVisibility(View.VISIBLE);
            if ( parteDelCuerpoActual == nroPartesCuerpo-1) {
                TextView gameTextAlert = findViewById(R.id.gameTextAlert);
                gameTextAlert.setText("Perdio / Terminó en " + "time" + "s");
                gameTextAlert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
            parteDelCuerpoActual++;
        }

        /*
        if (letraCorrecta) {
            if(contadorCorrecto==nroLetrasPalabra) {
                TextView gameTextAlert = findViewById(R.id.gameTextAlert);
                gameTextAlert.setText("Ganó / Terminó en " + "time" + "s");
            }
        } else if (parteDelCuerpoActual<nroPartesCuerpo) {
                partesDelCuerpo[parteDelCuerpoActual].setVisibility(View.VISIBLE);
                parteDelCuerpoActual++;
        } else {
            TextView gameTextAlert = findViewById(R.id.gameTextAlert);
            gameTextAlert.setText("Ganó / Terminó en " + "time" + "s");
        }*/

    }



}