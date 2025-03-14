package com.alexis.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Button btn;
    TextView tempVal;
    DB db;
    //Acción nuevo para crear un nuevo registro
    String accion = "nuevo", idProducto = "";
    String mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        return msg;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        btn = findViewById(R.id.btnGuardarAmigo);
        btn.setOnClickListener(view->guardarAmigo());

        fab = findViewById(R.id.fabListaAmigos);
        fab.setOnClickListener(view->abrirVentana());
        mostrarDatos();
    }
    private void mostrarDatos(){
        try {
            //Recuperamos los parametros que vienen para modificar
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if(accion.equals("modificar")){
                //Recuperamos los datos del amigo
                JSONObject datos = new JSONObject(parametros.getString("productos"));
                idProducto = datos.getString("idProducto");
                tempVal = findViewById(R.id.txtNombre);
                tempVal.setText(datos.getString("codigo"));
                tempVal = findViewById(R.id.txtDireccion);
                tempVal.setText(datos.getString("descripcion"));
                tempVal = findViewById(R.id.txtTelefono);
                tempVal.setText(datos.getString("marca"));
                tempVal = findViewById(R.id.txtEmail);
                tempVal.setText(datos.getString("presentacion"));
                tempVal = findViewById(R.id.txtDui);
                tempVal.setText(datos.getString("precio"));}
        }
        catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void abrirVentana(){
        Intent intent = new Intent(this, lista_amigos.class);
        startActivity(intent);
    }
    private void guardarAmigo() {
        tempVal = findViewById(R.id.txtNombre);
        String nombre = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDireccion);
        String direccion = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtTelefono);
        String telefono = tempVal.getText().toString();
        tempVal = findViewById(R.id.txtEmail);
        String email = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDui);
        String dui = tempVal.getText().toString();
        //Arreglo de datos
        String[] datos = {"", nombre, direccion, telefono, email, dui, ""};
        //Llamando al metodo administrar amigos de la clase DB
        db.administrar_amigos("agregar", datos);
        Toast.makeText(getApplicationContext(), "Registro guardado con exito", Toast.LENGTH_LONG).show();
        abrirVentana();//Abrir ventanas
    }
}

