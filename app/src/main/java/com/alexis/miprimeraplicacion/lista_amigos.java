package com.alexis.miprimeraplicacion;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_amigos extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsAmigos;
    Cursor cAmigos;
    DB db;
    final ArrayList<amigos> alAmigos = new ArrayList<amigos>();
    final ArrayList<amigos> alAmigosCopia = new ArrayList<amigos>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    amigos misAmigos;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);

        db = new DB(this);

        fab = findViewById(R.id.fabAgregarAmigo);
        fab.setOnClickListener(view -> abriVentana());
        obtenerDatosAmigos();
        buscarAmigos();
    }
    //Abre la ventana de amigos
    private void abriVentana(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //Obtiene los datos de los amigos
    private void obtenerDatosAmigos(){
        try{
            cAmigos = db.lista_amigos();
            //Si hay datos en la tabla
            if(cAmigos.moveToFirst()){//move to first es para moverse entre los datos
                jsonArray = new JSONArray();//Array de objetos se pasan a un JSON
                do{
                    jsonObject = new JSONObject();
                    jsonObject.put("idAmigo", cAmigos.getString(0));
                    jsonObject.put("nombre", cAmigos.getString(1));
                    jsonObject.put("direccion", cAmigos.getString(2));
                    jsonObject.put("telefono", cAmigos.getString(3));
                    jsonObject.put("email", cAmigos.getString(4));
                    jsonObject.put("dui", cAmigos.getString(5));
                    jsonObject.put("foto", cAmigos.getString(6));
                    jsonArray.put(jsonObject);
                }while(cAmigos.moveToNext());
                mostrarDatosAmigos();
            }else{
                mostrarMsg("No hay amigos registrados.");
                abriVentana();
            }
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    //Muestra los datos de los amigos
    private void mostrarDatosAmigos(){
        try{
            if(jsonArray.length()>0){
                ltsAmigos = findViewById(R.id.ltsAmigos);
                alAmigos.clear();
                alAmigosCopia.clear();

                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    misAmigos = new amigos(
                            jsonObject.getString("idAmigo"),
                            jsonObject.getString("nombre"),
                            jsonObject.getString("direccion"),
                            jsonObject.getString("telefono"),
                            jsonObject.getString("email"),
                            jsonObject.getString("dui"),
                            jsonObject.getString("foto")
                    );
                    alAmigos.add(misAmigos);
                }
                alAmigosCopia.addAll(alAmigos);
                ltsAmigos.setAdapter(new AdaptadorAmigos(this, alAmigos));
                registerForContextMenu(ltsAmigos);
            }else{
                mostrarMsg("No hay amigos registrados.");
                abriVentana();
            }
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    //Busca los amigos
    private void buscarAmigos(){
        TextView tempVal = findViewById(R.id.txtBuscarAmigos);
        tempVal.addTextChangedListener(new TextWatcher() {
            //TexWatcher
            //Antes
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //Durante
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alAmigos.clear();
                String buscar = tempVal.getText().toString().trim().toLowerCase();
                if( buscar.length()<=0){
                    alAmigos.addAll(alAmigosCopia);
                }else{
                    for (amigos item: alAmigosCopia){
                        //item.get obtiene el valor de la posicion del array, toLowerCase convierte el valor a minusculas
                        //contains verifica si el valor contiene la cadena de texto
                        if(item.getNombre().toLowerCase().contains(buscar) ||
                                item.getDui().toLowerCase().contains(buscar) ||
                                item.getEmail().toLowerCase().contains(buscar)){
                            //Si cumple la condicion se agrega al array
                            alAmigos.add(item);
                        }
                    }
                    ltsAmigos.setAdapter(new AdaptadorAmigos(getApplicationContext(), alAmigos));
                }
            }
            //Despues
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}