package com.ugb.miprimeraaplicacion;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tempVal, areaResult;
    TabHost tbh;
    Button btnMetros, btnArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhMetros);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("Metros").setContent(R.id.tabMetros).setIndicator("Metros", null));
        tbh.addTab(tbh.newTabSpec("Area").setContent(R.id.tabConversor).setIndicator("Área", null));

        // Configuración para conversión de metros
        btnMetros = findViewById(R.id.btnMetrosConvertir);
        btnMetros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tempVal = findViewById(R.id.txtMetrosCantidad);
                    double num1 = Double.parseDouble(tempVal.getText().toString());
                    double Calculo;

                    if (num1 <= 18) {
                        Calculo = 6;
                    } else if (num1 <= 28) {
                        Calculo = (num1 - 18) * 0.45 + 6;
                    } else {
                        Calculo = (num1 - 28) * 0.65 + 4.5 + 6;
                    }

                    tempVal = findViewById(R.id.lblrespuesta);
                    tempVal.setText("Precio: " + Calculo + "$.");
                } catch (NumberFormatException e) {
                    tempVal.setText("Ingrese un valor válido.");
                }
            }
        });

        // Configuración para conversión de área
        btnArea = findViewById(R.id.btnAreaConvertir);
        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView areaInput = findViewById(R.id.txtAreaCantidad);
                    double area = Double.parseDouble(areaInput.getText().toString());
                    double tareas = area / 437.5; // 1 tarea ≈ 437.5 m² en El Salvador
                    double manzanas = area / 7000; // 1 manzana ≈ 7000 m² en El Salvador
                    double pieCuadrado = area * 10.764;
                    double varaCuadrada = area * 1.43;
                    double yardaCuadrada = area * 1.196;
                    double metroCuadrado = area;
                    double hectareas = area / 10000;

                    areaResult = findViewById(R.id.lblAreaRespuesta);
                    areaResult.setText(String.format("%.2f Pies Cuadrados\n%.2f Varas Cuadradas\n%.2f Yardas Cuadradas\n%.2f Metros Cuadrados\n%.2f Tareas\n%.2f Manzanas\n%.2f Hectáreas",
                            pieCuadrado, varaCuadrada, yardaCuadrada, metroCuadrado, tareas, manzanas, hectareas));
                } catch (NumberFormatException e) {
                    areaResult.setText("Ingrese un valor válido.");
                }
            }
        });
    }
}



