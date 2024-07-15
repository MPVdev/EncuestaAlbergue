package com.mpv.encuestaalbergue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonCreator {

    JSONObject mainObject;
    JSONArray respuestasArray;

    public JsonCreator() {
        mainObject = new JSONObject();
        respuestasArray = new JSONArray();
        try {
            mainObject.put("respuestas", respuestasArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRespuesta(String P1, int P2, int P3, String[] P4, String P5, String P6, String P7, String[] P8, String P9, String P10, String P11, String[] P12, String P13) {
        JSONObject response = new JSONObject();
        try {
            response.put("P1", P1);
            response.put("P2", P2);
            response.put("P3", P3);
            response.put("P4", P4);
            response.put("P5", P5);
            response.put("P6", P6);
            response.put("P7", P7);
            response.put("P8", P8);
            response.put("P9", P9);
            response.put("P10", P10);
            response.put("P11", P11);
            response.put("P12", P12);
            response.put("P13", P13);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        respuestasArray.put(response);
    }

    public String getJson() {
        return mainObject.toString();
    }
}
