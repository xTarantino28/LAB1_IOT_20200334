package com.example.LAB1_IOT_20200334;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaEstadisticaAdapter extends RecyclerView.Adapter<ListaEstadisticaAdapter.EstadisticaViewHolder> {

    public List<String> getListaEstadistica() {
        return listaEstadistica;
    }

    public void setListaEstadistica(List<String> listaEstadistica) {
        this.listaEstadistica = listaEstadistica;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private List<String> listaEstadistica;
    private Context context;

    @NonNull
    @Override
    public EstadisticaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new EstadisticaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EstadisticaViewHolder holder, int position) {
        String estadisticaElm = listaEstadistica.get(position);
        holder.estadistica = estadisticaElm;

        TextView textViewEstadistica = holder.itemView.findViewById(R.id.idDato);
        textViewEstadistica.setText(estadisticaElm);

    }

    @Override
    public int getItemCount() {
        return listaEstadistica.size();
    }

    public class EstadisticaViewHolder extends RecyclerView.ViewHolder{

        String estadistica;

        public EstadisticaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
