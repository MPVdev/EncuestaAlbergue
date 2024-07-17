package com.mpv.encuestaalbergue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 123;
    TextView tvTitulo, tvP1, tvP2, tvP3, tvP4, tvP5, tvP6, tvP7, tvP8, tvP9, tvP10, tvP11, tvP12, tvP13;
    EditText edP1, edP2, edP3, edP4Otros, edP10, edP13;
    Button btnGuardar, btnEnviar;
    Spinner sprP5, sprP6, sprP7, sprP9, sprP11;
    CheckBox cbP4Perros, cbP4Gatos, cbP4Conejos, cbP4Pajaros, cbP8Op1, cbP8Op2, cbP8Op3, cbP12Op1, cbP12Op2, cbP12Op3, cbP12Op4, cbP12Op5, cbP12Op6, cbP12Op7;

    String P1, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13;
    int P2, P3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verifica y solicita permisos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_PERMISSIONS);
            }
        }

        // Inicializa las vistas y otros componentes
        tvTitulo = findViewById(R.id.tvTitulo);
        tvP1 = findViewById(R.id.tvP1);
        tvP2 = findViewById(R.id.tvP2);
        tvP3 = findViewById(R.id.tvP3);
        tvP4 = findViewById(R.id.tvP4);
        tvP5 = findViewById(R.id.tvP5);
        tvP6 = findViewById(R.id.tvP6);
        tvP7 = findViewById(R.id.tvP7);
        tvP8 = findViewById(R.id.tvP8);
        tvP9 = findViewById(R.id.tvP9);
        tvP10 = findViewById(R.id.tvP10);
        tvP11 = findViewById(R.id.tvP11);
        tvP12 = findViewById(R.id.tvP12);
        tvP13 = findViewById(R.id.tvP13);
        edP1 = findViewById(R.id.edP1);
        edP2 = findViewById(R.id.edP2);
        edP3 = findViewById(R.id.edP3);
        edP4Otros = findViewById(R.id.edP4Otros);
        edP10 = findViewById(R.id.edP10);
        edP13 = findViewById(R.id.edP13);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEnviar = findViewById(R.id.btnEnviar);
        sprP5 = findViewById(R.id.sprP5);
        sprP6 = findViewById(R.id.sprP6);
        sprP7 = findViewById(R.id.sprP7);
        sprP9 = findViewById(R.id.sprP9);
        sprP11 = findViewById(R.id.sprP11);
        cbP4Perros = findViewById(R.id.cbP4Perros);
        cbP4Gatos = findViewById(R.id.cbP4Gatos);
        cbP4Conejos = findViewById(R.id.cbP4Conejos);
        cbP4Pajaros = findViewById(R.id.cbP4Pajaros);
        cbP8Op1 = findViewById(R.id.cbP8Op1);
        cbP8Op2 = findViewById(R.id.cbP8Op2);
        cbP8Op3 = findViewById(R.id.cbP8Op3);
        cbP12Op1 = findViewById(R.id.cbP12Op1);
        cbP12Op2 = findViewById(R.id.cbP12Op2);
        cbP12Op3 = findViewById(R.id.cbP12Op3);
        cbP12Op4 = findViewById(R.id.cbP12Op4);
        cbP12Op5 = findViewById(R.id.cbP12Op5);
        cbP12Op6 = findViewById(R.id.cbP12Op6);
        cbP12Op7 = findViewById(R.id.cbP12Op7);

        String[] alimentacion = getResources().getStringArray(R.array.alimentacion);
        ArrayAdapter<String> adapterAlimentacion = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alimentacion);
        adapterAlimentacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String[] adopcionOp = getResources().getStringArray(R.array.adopcionOp);
        ArrayAdapter<String> adapterAdopcionOp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, adopcionOp);
        adapterAdopcionOp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String[] frecuencia = getResources().getStringArray(R.array.frecuencia);
        ArrayAdapter<String> adapterFrecuencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frecuencia);
        adapterFrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String[] op = getResources().getStringArray(R.array.Op);
        ArrayAdapter<String> adapterOp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, op);
        adapterOp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sprP5.setAdapter(adapterAlimentacion);
        sprP6.setAdapter(adapterAdopcionOp);
        sprP7.setAdapter(adapterFrecuencia);
        sprP9.setAdapter(adapterOp);
        sprP11.setAdapter(adapterOp);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
                copyDatabase();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
            }
        });
    }

    public void enviarDatos() {
        File internalDir = getFilesDir();
        File backupDB = new File(internalDir, "rr/Encuesta_copiada.db");

        if (backupDB.exists()) {
            Uri uri = Uri.fromFile(backupDB);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("application/octet-stream");
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sendIntent.setPackage("com.whatsapp");

            try {
                startActivity(sendIntent);
            } catch (Exception e) {
                e.printStackTrace();
                // WhatsApp no está instalado o alguna otra excepción
            }
        } else {
            Toast.makeText(this, "db no existe", Toast.LENGTH_LONG).show();
        }
    }

    public void guardarDatos() {
        BDHelper admin = new BDHelper(this, "Encuesta.db", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        if (edP1.getText().toString().isEmpty()) {
            Toast.makeText(this, "La pregunta 1 no tiene respuesta", Toast.LENGTH_LONG).show();
            return;
        } else {
            P1 = edP1.getText().toString();
        }
        if (edP2.getText().toString().isEmpty()) {
            Toast.makeText(this, "La pregunta 2 no tiene respuesta", Toast.LENGTH_LONG).show();
            return;
        } else {
            P2 = Integer.parseInt(edP2.getText().toString());
        }
        if (edP3.getText().toString().isEmpty()) {
            Toast.makeText(this, "La pregunta 3 no tiene respuesta", Toast.LENGTH_LONG).show();
            return;
        } else {
            P3 = Integer.parseInt(edP3.getText().toString());
        }
        if (cbP4Perros.isChecked()) {
            P4 += "Perros, ";
        }
        if (cbP4Gatos.isChecked()) {
            P4 += "Gatos, ";
        }
        if (cbP4Conejos.isChecked()) {
            P4 += "Conejos, ";
        }
        if (cbP4Pajaros.isChecked()) {
            P4 += "Pajaros, ";
        }
        P4 += edP4Otros.getText().toString();
        if (!cbP4Pajaros.isChecked() && !cbP4Perros.isChecked() && !cbP4Gatos.isChecked() && !cbP4Conejos.isChecked() && edP4Otros.getText().toString().isEmpty()) {
            P4 += "Vacio";
        }
        P5 = sprP5.getSelectedItem().toString();
        P6 = sprP6.getSelectedItem().toString();
        P7 = sprP7.getSelectedItem().toString();
        if (cbP8Op1.isChecked()) {
            P8 += "Vacunas, ";
        }
        if (cbP8Op2.isChecked()) {
            P8 += "Estirilizacion, ";
        }
        if (cbP8Op3.isChecked()) {
            P8 += "Enfermedades, ";
        }
        if (!cbP8Op1.isChecked() && !cbP8Op2.isChecked() && !cbP8Op3.isChecked()) {
            P8 += "Vacio";
        }
        P9 = sprP9.getSelectedItem().toString();
        if (edP10.getText().toString().isEmpty()) {
            Toast.makeText(this, "La pregunta 10 no tiene respuesta", Toast.LENGTH_LONG).show();
            return;
        } else {
            P10 = edP10.getText().toString();
        }
        P11 = sprP11.getSelectedItem().toString();
        if (cbP12Op1.isChecked()) {
            P12 += "Peluqueria, ";
        }
        if (cbP12Op2.isChecked()) {
            P12 += "Hospedaje, ";
        }
        if (cbP12Op3.isChecked()) {
            P12 += "Veterinaria, ";
        }
        if (cbP12Op4.isChecked()) {
            P12 += "Cafe de gatos, ";
        }
        if (cbP12Op5.isChecked()) {
            P12 += "Entrenamiento, ";
        }
        if (cbP12Op6.isChecked()) {
            P12 += "Paseo de perros, ";
        }
        if (cbP12Op7.isChecked()) {
            P12 += "Tienda de mascotas, ";
        }
        if (!cbP12Op1.isChecked() && !cbP12Op2.isChecked() && !cbP12Op3.isChecked() && !cbP12Op4.isChecked() && !cbP12Op5.isChecked() && !cbP12Op6.isChecked() && !cbP12Op7.isChecked()) {
            P12 += "Vacio";
        }
        if (edP13.getText().toString().isEmpty()) {
            Toast.makeText(this, "La pregunta 13 no tiene respuesta", Toast.LENGTH_LONG).show();
            return;
        } else {
            P13 = edP13.getText().toString();
        }

        ContentValues values = new ContentValues();
        values.put("P1", P1);
        values.put("P2", P2);
        values.put("P3", P3);
        values.put("P4", P4);
        values.put("P5", P5);
        values.put("P6", P6);
        values.put("P7", P7);
        values.put("P8", P8);
        values.put("P9", P9);
        values.put("P10", P10);
        values.put("P11", P11);
        values.put("P12", P12);
        values.put("P13", P13);

        bd.insert("encuesta", null, values);
        bd.close();

        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
        limpiar();
    }

    public void limpiar() {
        edP1.setText(null);
        edP2.setText(null);
        edP3.setText(null);
        cbP4Perros.setChecked(false);
        cbP4Gatos.setChecked(false);
        cbP4Conejos.setChecked(false);
        cbP4Pajaros.setChecked(false);
        edP4Otros.setText(null);
        sprP5.setSelection(0);
        sprP6.setSelection(0);
        sprP7.setSelection(0);
        cbP8Op1.setChecked(false);
        cbP8Op2.setChecked(false);
        cbP8Op3.setChecked(false);
        sprP9.setSelection(0);
        edP10.setText(null);
        sprP11.setSelection(0);
        cbP12Op1.setChecked(false);
        cbP12Op2.setChecked(false);
        cbP12Op3.setChecked(false);
        cbP12Op4.setChecked(false);
        cbP12Op5.setChecked(false);
        cbP12Op6.setChecked(false);
        cbP12Op7.setChecked(false);
        edP13.setText(null);
        P1 = null;
        P2 = 0;
        P3 = 0;
        P4 = null;
        P5 = null;
        P6 = null;
        P7 = null;
        P8 = null;
        P9 = null;
        P10 = null;
        P11 = null;
        P12 = null;
        P13 = null;
    }

    public void copyDatabase() {
        try {
            File data = Environment.getDataDirectory();
            File internalDir = getFilesDir(); // Directorio de almacenamiento interno

            String currentDBPath = "/data/data/" + getPackageName() + "/databases/Encuesta.db";
            String backupDBPath = "Encuesta_copiada.db"; // Nombre del archivo copiado en el almacenamiento interno
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(internalDir, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(this, "Database copied to internal storage: " + backupDB.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "db no existe", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "error de copia", Toast.LENGTH_LONG).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos concedidos, puedes continuar con la funcionalidad que requiere estos permisos
            } else {
                Toast.makeText(this, "Permisos necesarios no concedidos", Toast.LENGTH_LONG).show();
            }
        }
    }
}