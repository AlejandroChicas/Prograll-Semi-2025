package com.example.miprimeraaplicacion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AjustesActivity extends AppCompatActivity {

    private Switch switchNotificaciones, switchTemaOscuro, switchRecordatorios, switchChats, switchIdioma;
    private Button btnGuardarAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // Inicializando los componentes
        switchNotificaciones = findViewById(R.id.switchNotificaciones);
        switchTemaOscuro = findViewById(R.id.switchTemaOscuro);
        switchRecordatorios = findViewById(R.id.switchRecordatorios);
        switchChats = findViewById(R.id.switchChats);
        switchIdioma = findViewById(R.id.switchIdioma);
        btnGuardarAjustes = findViewById(R.id.btnGuardarAjustes);

        // Cargar los valores guardados al iniciar la actividad
        cargarAjustes();

        // Configurar botón para guardar los ajustes
        btnGuardarAjustes.setOnClickListener(v -> {
            // Obtener el estado de los switches
            boolean notificacionesActivadas = switchNotificaciones.isChecked();
            boolean temaOscuroActivo = switchTemaOscuro.isChecked();
            boolean recordatorioAutomatico = switchRecordatorios.isChecked();
            boolean chatsActivados = switchChats.isChecked();
            boolean idiomaEspanol = switchIdioma.isChecked();

            // Guardar estos ajustes
            guardarAjustes(notificacionesActivadas, temaOscuroActivo, recordatorioAutomatico, chatsActivados, idiomaEspanol);

            // Mostrar mensaje de confirmación
            String mensaje = "Ajustes guardados:\n" +
                    "Notificaciones: " + (notificacionesActivadas ? "Activadas" : "Desactivadas") + "\n" +
                    "Tema Oscuro: " + (temaOscuroActivo ? "Activo" : "Inactivo") + "\n" +
                    "Recordatorio Automático: " + (recordatorioAutomatico ? "Activado" : "Desactivado") + "\n" +
                    "Chats: " + (chatsActivados ? "Activados" : "Desactivados") + "\n" +
                    "Idioma: " + (idiomaEspanol ? "Español" : "Otro");

            Toast.makeText(AjustesActivity.this, mensaje, Toast.LENGTH_LONG).show();
        });
    }

    // Método para guardar los ajustes
    private void guardarAjustes(boolean notificaciones, boolean temaOscuro, boolean recordatorios, boolean chats, boolean idioma) {
        SharedPreferences prefs = getSharedPreferences("AjustesPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Notificaciones", notificaciones);
        editor.putBoolean("TemaOscuro", temaOscuro);
        editor.putBoolean("Recordatorios", recordatorios);
        editor.putBoolean("Chats", chats);
        editor.putBoolean("Idioma", idioma);
        editor.apply();
    }

    // Método para cargar los ajustes guardados
    private void cargarAjustes() {
        SharedPreferences prefs = getSharedPreferences("AjustesPrefs", MODE_PRIVATE);
        switchNotificaciones.setChecked(prefs.getBoolean("Notificaciones", false));
        switchTemaOscuro.setChecked(prefs.getBoolean("TemaOscuro", false));
        switchRecordatorios.setChecked(prefs.getBoolean("Recordatorios", false));
        switchChats.setChecked(prefs.getBoolean("Chats", false));
        switchIdioma.setChecked(prefs.getBoolean("Idioma", true)); // predeterminado: español activado
    }

}
