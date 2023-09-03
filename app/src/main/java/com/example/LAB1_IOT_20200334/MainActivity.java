package com.example.LAB1_IOT_20200334;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_title_layout);

        // Asocia el context menu con el elemento textView TeleAhorcado_vista_principal (la presion larga)
        registerForContextMenu((TextView)findViewById(R.id.TeleAhorcado_vista_principal));

    }


    //Registra el menu creado en xml de la carpeta menu al sobreescribir el metodo onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_principal,menu);
    }


    // Configuracion las opciones del menu (acciones a tomar)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        int ItemId = item.getItemId();
        // Obtén una referencia al TextView
        TextView textView = findViewById(R.id.TeleAhorcado_vista_principal); // Reemplaza "R.id.miTextView" con el ID de tu TextView
        //Log.e("VAYA2", textView.getText().toString() );
        if (ItemId == R.id.context_azul) {// Cambia el color del texto del TextView
            //Log.d("COLOR",String.valueOf(Color.parseColor("#0000ff")));
            textView.setTextColor(ContextCompat.getColor(this, R.color.blue)); // Reemplaza "R.color.colorPersonalizado" con el color que desees usar
            return true;
        } else if (ItemId == R.id.context_verde) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.green));
            return true;
        } else if (ItemId == R.id.context_rojo) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.red));
            return true;
        }
        return super.onContextItemSelected(item);
    }
    /*
    In a regular Android project, constants in the resource R class are declared like this:

    public static final int main=0x7f030004;

    However, as of ADT 14, in a library project, they will be declared like this:

    public static int main=0x7f030004;

    In other words, the constants are not final in a library project. Therefore your code would no longer compile.

    The simple solution is to use il-else statement.*/





}