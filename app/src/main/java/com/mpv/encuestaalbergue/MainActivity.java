package com.mpv.encuestaalbergue;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    TextView tvTitulo, tvP1, tvP2, tvP3, tvP4, tvP5, tvP6, tvP7, tvP8, tvP9, tvP10, tvP11, tvP12, tvP13;
    EditText edP1, edP2, edP3, edP4Otros, edP10, edP13;
    Button btnGuardar, btnEnviar;
    Spinner sprP5, sprP6, sprP7, sprP9, sprP11;
    CheckBox cbP4Perros, cbP4Gatos, cbP4Conejos, cbP4Pajaros, cbP8Op1, cbP8Op2, cbP8Op3, cbP12Op1, cbP12Op2, cbP12Op3, cbP12Op4, cbP12Op5, cbP12Op6, cbP12Op7;

    String respuesta, P1, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13;
    int P2, P3;
    BDHelper bdHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexionFrontBack();

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
                conseguirDatos();
                CrearTabla();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarDatos();
            }
        });
    }

    public void conexionFrontBack() {
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
    }

    @SuppressLint("Range")
    public String obtenerSQLite() {
        SQLiteDatabase db = bdHelper.onRead();
        Cursor cursor = db.rawQuery("SELECT * FROM Encuesta", null);

        JSONArray jsonArray = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("P1", cursor.getString(cursor.getColumnIndex("P1")));
                jsonObject.put("P2", cursor.getInt(cursor.getColumnIndex("P2")));
                jsonObject.put("P3", cursor.getInt(cursor.getColumnIndex("P3")));
                jsonObject.put("P4", cursor.getString(cursor.getColumnIndex("P4")));
                jsonObject.put("P5", cursor.getString(cursor.getColumnIndex("P5")));
                jsonObject.put("P6", cursor.getString(cursor.getColumnIndex("P6")));
                jsonObject.put("P7", cursor.getString(cursor.getColumnIndex("P7")));
                jsonObject.put("P8", cursor.getString(cursor.getColumnIndex("P8")));
                jsonObject.put("P9", cursor.getString(cursor.getColumnIndex("P9")));
                jsonObject.put("P10", cursor.getString(cursor.getColumnIndex("P10")));
                jsonObject.put("P11", cursor.getString(cursor.getColumnIndex("P11")));
                jsonObject.put("P12", cursor.getString(cursor.getColumnIndex("P12")));
                jsonObject.put("P13", cursor.getString(cursor.getColumnIndex("P13")));
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        db.close();

        return jsonArray.toString();
    }

    public void EnviarDatos() {
        String url = "https://api.funvid.xyz";
        OkHttpClient cliente = new OkHttpClient();
        String json = obtenerSQLite();

        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);
        Request post = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        cliente.newCall(post).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Error en la conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Código inesperado " + response);
                    } else {
                        respuesta = responseBody.string();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Error en la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void conseguirDatos() {
        P1 = edP1.getText().toString();
        P2 = Integer.parseInt(edP2.getText().toString());
        P3 = Integer.parseInt(edP3.getText().toString());
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
        P9 = sprP9.getSelectedItem().toString();
        P10 = edP10.getText().toString();
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
        P13 = edP13.getText().toString();
    }

    public void Limpiar() {
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
    }

    public void CrearTabla() {
        BDHelper admin = new BDHelper(this, "Encuesta.db", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        if (P1 != null && P2 != 0 && P3 != 0 && P4 != null && P5 != null && P6 != null && P7 != null && P8 != null && P9 != null && P10 != null && P11 != null && P12 != null && P13 != null) {
            ContentValues datosRegistrar = new ContentValues();

            datosRegistrar.put("P1", P1);
            datosRegistrar.put("P2", P2);
            datosRegistrar.put("P3", P3);
            datosRegistrar.put("P4", P4);
            datosRegistrar.put("P5", P5);
            datosRegistrar.put("P6", P6);
            datosRegistrar.put("P7", P7);
            datosRegistrar.put("P8", P8);
            datosRegistrar.put("P9", P9);
            datosRegistrar.put("P10", P10);
            datosRegistrar.put("P11", P11);
            datosRegistrar.put("P12", P12);
            datosRegistrar.put("P13", P13);

            bd.insert("Encuesta", null, datosRegistrar);

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
            Limpiar();
        }
    }
}