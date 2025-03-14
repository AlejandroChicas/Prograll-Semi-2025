package com.alexis.miprimeraplicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorAmigos extends BaseAdapter {
    Context context;
    ArrayList<productos> alProductos;
    productos misProductos;
    LayoutInflater inflater;

    public AdaptadorAmigos(Context context, ArrayList<productos> alProductos) {
        this.context = context;
        this.alProductos = alProductos;
    }

    //Cuenta el array de Productos y da su cantidad de valores
    @Override
    public int getCount() {
        return alProductos.size();
    }

    //Obtiene el objeto de la posicion del array y su posicion
    @Override
    public Object getItem(int position) {
        return alProductos.get(position);
    }

    //Obtiene el id del objeto de la posicion del array
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Obtiene la vista de la posicion del array
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fotos, parent, false);
        try {
            misProductos = alProductos.get(position);

            TextView tempVal = itemView.findViewById(R.id.lblNombreAdaptador);
            tempVal.setText(misProductos.getCodigo());

            tempVal = itemView.findViewById(R.id.lblTelefonoAdaptador);
            tempVal.setText(misProductos.getDescripcion());

            tempVal = itemView.findViewById(R.id.lblEmailAdaptador);
            tempVal.setText(misProductos.getpresentacion());
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}