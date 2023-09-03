package com.example.LAB1_IOT_20200334;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LetterAdapter extends BaseAdapter {

    private String[] letras;
    private LayoutInflater letraInf;

    public LetterAdapter(Context context){
        letras = new String[26]; //tamanio del arreglo de letras igual al ABC .
        for(int i=0;i<letras.length;i++){
            letras[i]=""+(char)(i+'A');
        }
        letraInf = LayoutInflater.from(context);  //crear la vista
    }


    @Override
    public int getCount() {
        return letras.length;   //tamanio del adaptador
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { //agregar la vista
        Button btnLetra;
        if(view==null){
            btnLetra = (Button) letraInf.inflate(R.layout.letra,viewGroup,false);
        } else {
            btnLetra = (Button) view;
        }
        btnLetra.setText(letras[i]);
        return btnLetra;
    }
}
