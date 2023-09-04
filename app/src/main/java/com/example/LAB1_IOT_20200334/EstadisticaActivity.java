package com.example.LAB1_IOT_20200334;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class EstadisticaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);
        getSupportActionBar().setTitle("TeleAhorcado");

        Intent intent = getIntent();
        ArrayList<String> listaEstadistica = intent.getStringArrayListExtra("listaEstadistica");

        ListaEstadisticaAdapter adapter = new ListaEstadisticaAdapter();
        adapter.setContext(EstadisticaActivity.this);
        adapter.setListaEstadistica(listaEstadistica);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EstadisticaActivity.this));

    }
}