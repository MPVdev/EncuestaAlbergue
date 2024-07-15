package com.mpv.encuestaalbergue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    TextView tvTitulo, tvP1, tvP2, tvP3, tvP4, tvP5, tvP6, tvP7, tvP8, tvP9, tvP10, tvP11, tvP12, tvP13;
    EditText edP1, edP2, edP3, edP4Otros, edP10, edP13;
    Button btnGuardar, btnEnviar;
    Spinner sprP5, sprP6, sprP7, sprP9, sprP11;
    CheckBox cbP4Perros, cbP4Gatos, cbP4Conejos, cbP4Pajaros, cbP8Op1, cbP8Op2, cbP8Op3, cbP12Op1, cbP12Op2, cbP12Op3, cbP12Op4, cbP12Op5, cbP12Op6, cbP12Op7;
    String respuestaJson = "";
    JsonCreator jsonCreator;
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


    }
    public void conexionFrontBack(){
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

    public void anadirRespuestas() {
        String P1, P5, P6, P7, P9, P10, P11, P13;
        int P2, P3;
        ArrayList<String> P4, P8, P12;
        P4 = new ArrayList<>();
        P1 = edP1.getText().toString();
        P2 = Integer.parseInt(edP2.getText().toString());
        P3 = Integer.parseInt(edP3.getText().toString());
    }

    public void ConsumirAPI(){
        String url="";
        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder()
                .url(url)
                .build();
        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }else{
                        respuestaJson=responseBody.string();
                    }
                    Log.i("data", responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}