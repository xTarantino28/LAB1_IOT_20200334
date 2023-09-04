package com.example.LAB1_IOT_20200334;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.transition.Visibility;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

    private Timer timer;
    private TimerTask task;
    private long seconds;

    private boolean terminoJuego;

    private  ArrayList<String> listaEstadisticas = new ArrayList<>();

    private int nroJuegos;


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

        nroJuegos = 1;
        jugarAhorcado();


        Button playAgainButton = findViewById(R.id.button_nuevo_juego);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!terminoJuego) {
                    String estadistica = "Juego " + nroJuegos + ": Canceló";
                    listaEstadisticas.add(estadistica);
                }
                nroJuegos++;
                jugarAhorcado();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_estadistica,menu);
        return true;
    }

    public void estadisticaBtn(MenuItem menuItem){
        Intent intent = new Intent(activity_juego_run.this, EstadisticaActivity.class);
        intent.putExtra("listaEstadistica",listaEstadisticas);
        startActivity(intent);
    }


    //metodo para empezar el juego.
    private void jugarAhorcado() {
        terminoJuego = false;
        timer = new Timer();
        task = new TimerTask() {
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;

                // Calcular el tiempo transcurrido en segundos
                seconds = elapsedTime / 1000;

                //System.out.println("Segundos transcurridos: " + seconds);

                // Detener el temporizador después de 10 segundos
                //if (seconds >= 10) {
                //    timer.cancel(); // Detener el temporizador
                //   System.out.println("Temporizador detenido después de 10 segundos.");
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);



        String nuevaPalabra = palabras[random.nextInt(palabras.length)]; //sortea una palabra del array de palabras. considera el tamanio del array
        while(nuevaPalabra.equals(palabraActual)) {  //el siguiente juego debe ser con una palabra diferente a la anterior
            nuevaPalabra = palabras[random.nextInt(palabras.length)];
        }
        palabraActual = nuevaPalabra;

        characterViews = new TextView[palabraActual.length()]; //tamanio del arreglo de caracteres definido segun la palabra

        TextView gameTextAlert = findViewById(R.id.gameTextAlert);
        gameTextAlert.setText("");
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
                deshabilitarBotones();

                timer.cancel();

                TextView gameTextAlert = findViewById(R.id.gameTextAlert);
                gameTextAlert.setText("Ganó / Terminó en "+ seconds +"s");
                gameTextAlert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                //TextView estadisticaGano = new TextView(activity_juego_run.this);
                //estadisticaGano.setText("Juego " + nroJuegos + ": Terminó en "+ seconds +"s");
                String estadistica = "Juego " + nroJuegos + ": Terminó en "+ seconds +"s";
                listaEstadisticas.add(estadistica);
                terminoJuego = true;
            }
        } else if (parteDelCuerpoActual<nroPartesCuerpo) {
            partesDelCuerpo[parteDelCuerpoActual].setVisibility(View.VISIBLE);
            if ( parteDelCuerpoActual == nroPartesCuerpo-1) {
                deshabilitarBotones();

                timer.cancel();
                TextView gameTextAlert = findViewById(R.id.gameTextAlert);
                gameTextAlert.setText("Perdio / Terminó en "+ seconds +"s");
                gameTextAlert.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                String estadistica = "Juego " + nroJuegos + ": Terminó en "+ seconds +"s";
                listaEstadisticas.add(estadistica);
                terminoJuego = true;
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

    public void deshabilitarBotones(){
        for(int i=0; i<gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }



}