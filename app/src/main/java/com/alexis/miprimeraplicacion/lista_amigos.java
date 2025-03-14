package com.alexis.miprimeraplicacion;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class lista_amigos extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsAmigos;
    Cursor cProductos;
    DB db;
    final ArrayList<productos> alProductos = new ArrayList<productos>();
    final ArrayList<productos> alProductosCopia = new ArrayList<productos>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    productos misProductos;
    FloatingActionButton fab;
    int posicion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);
        parametros.putString("accion","nuevo");

        db = new DB(this);

        fab = findViewById(R.id.fabAgregarAmigo);
        fab.setOnClickListener(view -> abriVentana());
        obtenerDatosAmigos();
        buscarAmigos();
    }

    //Metodos agregados
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle(jsonArray.getJSONObject(posicion).getString("nombre"));
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    //Metodos agregados
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        try{
            //Si el item seleccionado es igual a nuevo, se abre la ventana agregar amigo
            if(item.getItemId() == R.id.mxnNuevo){
                abriVentana();
            //Si el item seleccionado es igual a Modificar
            }else if (item.getItemId() == R.id.mnxModificar){
                parametros.putString("accion","modificar");
                parametros.putString("productos", jsonArray.getJSONObject(posicion).toString());
                abriVentana();
            }else if (item.getItemId() == R.id.mnxEliminar){
                //Eliminar amigo
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
            return super.onContextItemSelected(item);
        }

    }


    //Abre la ventana de amigos
    private void abriVentana(){
        Intent intent = new Intent(this, MainActivity.class);
        //Se envian los parametros a la ventana de amigos
        intent.putExtras(parametros);
        startActivity(intent);
    }

    //Obtiene los datos de los amigos
    private void obtenerDatosAmigos(){
        try{
            cProductos = db.lista_amigos();
            //Si hay datos en la tabla
            if(cProductos.moveToFirst()){//move to first es para moverse entre los datos
                jsonArray = new JSONArray();//Array de objetos se pasan a un JSON
                do{
                    jsonObject = new JSONObject();
                    jsonObject.put("idProducto", cProductos.getString(0));
                    jsonObject.put("codigo", cProductos.getString(1));
                    jsonObject.put("descripcion", cProductos.getString(2));
                    jsonObject.put("marca", cProductos.getString(3));
                    jsonObject.put("presentacion", cProductos.getString(4));
                    jsonObject.put("precio", cProductos.getString(5));
                    jsonObject.put("foto", cProductos.getString(6));
                    jsonArray.put(jsonObject);
                }while(cProductos.moveToNext());
                mostrarDatosAmigos();
            }else{
                mostrarMsg("No hay productos registrados.");
                abriVentana();
            }
        }catch (Exception e){
            mostrarMsg("Error: UNO" + e.getMessage());
        }
    }
    //Muestra los datos de los amigos
    private void mostrarDatosAmigos(){
        try{
            if(jsonArray.length()>0){
                ltsAmigos = findViewById(R.id.ltsAmigos);
                alProductos.clear();
                alProductosCopia.clear();

                for (int i=0; i<jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    misProductos = new productos(
                            jsonObject.getString("idProducto"),
                            jsonObject.getString("codigo"),
                            jsonObject.getString("descripcion"),
                            jsonObject.getString("marca"),
                            jsonObject.getString("presentacion"),
                            jsonObject.getString("precio"),
                            jsonObject.getString("foto")
                    );
                    alProductos.add(misProductos);
                }
                alProductosCopia.addAll(alProductos);
                ltsAmigos.setAdapter(new AdaptadorAmigos(this, alProductos));
                registerForContextMenu(ltsAmigos);
            }else{
                mostrarMsg("No hay productos registrados.");
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
                alProductos.clear();
                String buscar = tempVal.getText().toString().trim().toLowerCase();
                if( buscar.length()<=0){
                    alProductos.addAll(alProductosCopia);
                }else{
                    for (productos item: alProductosCopia){
                        //item.get obtiene el valor de la posicion del array, toLowerCase convierte el valor a minusculas
                        //contains verifica si el valor contiene la cadena de texto
                        if(item.getCodigo().toLowerCase().contains(buscar) ||
                                item.getDescripcion().toLowerCase().contains(buscar) ||
                                item.getMarca().toLowerCase().contains(buscar)){
                            //Si cumple la condicion se agrega al array
                            alProductos.add(item);
                        }
                    }
                    ltsAmigos.setAdapter(new AdaptadorAmigos(getApplicationContext(), alProductos));
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