package marcer.pau.streaming.protocol;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import marcer.pau.streaming.model.Aplicacio;


public class JSONProtocol {

    private static JSONProtocol singleton;
    private static JSONProtocolListener observador;

    private JSONProtocol() {

    }
    public static JSONProtocol getInstance(){
        if(singleton == null)
            singleton = new JSONProtocol();
        return singleton;
    }

    public interface JSONProtocolListener {
        void onDiscoveryResponse(String ip, String port);
        void onAppRequestResponse(List<Aplicacio> listApps);
        void onStartResponse(String ip, String port, String URI);
        void onStopResponse();
        void onErrorResponse();
    }

    public void registerListener(JSONProtocolListener jsonProtocolListener) {
        observador = jsonProtocolListener;
    }

    /*
     * missatge inicial del mobil al broadcast de la subxarxa contra el port de control n
     */
    public JSONObject getDiscoveryMessage() {
        JSONObject object = new JSONObject();
        try {
            object.put("VTWCONTROL",new JSONArray()
                    .put(new JSONObject()
                            .put("REQUEST","DISCOVERY"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /*
     * client mobil fa un request de totes les app disponibles al servidor
     */
    public JSONObject getAppRequestMessage() {
        JSONObject object = new JSONObject();
        try {
            object.put("VTWCONTROL",new JSONArray()
                    .put(new JSONObject()
                            .put("REQUEST","APP-REQUEST"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /*
     * 3 + enviem les app disponibles al mobil i el servidor ens retorna els canvis pertinents
     */
    public JSONObject getAppUpdateMessage(List<Aplicacio> listApps) {
        JSONObject object = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(new JSONObject().put("REQUEST","APP-UPDATE"));
            for (Aplicacio aplicacio : listApps){
                jsonArray.put(new JSONObject()
                        .put("APP",aplicacio.getIdentificador()));
            }
            object.put("VTWCONTROL", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /*
     * request to start an app
     */
    public JSONObject getStartAppMessage(Aplicacio aplicacio, List<String> options) {
        JSONObject object = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < options.size(); i++){
                jsonArray.put(new JSONObject()
                            .put("OPT"+String.valueOf(i+1),options.get(i)));
            }
            object.put("VTWCONTROL",new JSONArray()
                    .put(new JSONObject()
                            .put("REQUEST","START"))
                    .put(new JSONObject()
                            .put("ID",aplicacio.getIdentificador()))
                    .put(new JSONObject()
                            .put("OPTIONS", jsonArray))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /*
     * request to stop an app
     */
    public JSONObject getStopAppMessage(Aplicacio aplicacio, List<String> options) {
        JSONObject object = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < options.size(); i++){
                jsonArray.put(new JSONObject()
                        .put("OPT"+String.valueOf(i+1),options.get(i)));
            }
            object.put("VTWCONTROL",new JSONArray()
                    .put(new JSONObject()
                            .put("REQUEST","STOP"))
                    .put(new JSONObject()
                            .put("ID",aplicacio.getIdentificador()))
                    .put(new JSONObject()
                            .put("OPTIONS", jsonArray))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /*
     * Error message
     */
    public JSONObject getErrorMessage(String errorCode, String errorMsg) {
        JSONObject object = new JSONObject();
        try {
            object.put("VTWCONTROL",new JSONArray()
                    .put(new JSONObject()
                            .put("ERROR",errorCode))
                    .put(new JSONObject()
                            .put("MESSAGE", new JSONArray()
                                    .put(new JSONObject()
                                        .put("msg",errorMsg))
                            )
                    )
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /*
     * Gyroscope packet schema
     */
    public JSONObject getGyroscopeMessage(float x, float y, float z){
        JSONObject object = new JSONObject();
        try {
            object.put("VTWDATA",new JSONArray()
                    .put(new JSONObject()
                            .put("x",String.valueOf(x)))
                    .put(new JSONObject()
                            .put("y",String.valueOf(y)))
                    .put(new JSONObject()
                            .put("z",String.valueOf(z)))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /*
     * Decode JSON incoming objects and call the apropiate listener
     */
    public void inputJSON(JSONObject jsonObject){
        if(observador != null)
        try {
            Log.d("JSONRecived",jsonObject.toString());
            JSONArray control = jsonObject.getJSONArray("VTWCONTROL");
            JSONObject resposta = (JSONObject)control.get(0);

            if (!resposta.isNull("RESPONSE"))
                switch (resposta.getString("RESPONSE")) {
                    //case "DISCOVERY":
                    case "OK":
                        observador.onDiscoveryResponse(control.getJSONObject(1).getString("IP"),control.getJSONObject(2).getString("PORT"));
                        break;
                    case "APP-LIST":
                        JSONArray array = control.getJSONObject(1).getJSONArray("APP-LIST");
                        List<Aplicacio> aplicacioList = new LinkedList<>();
                        for(int i=0; i<array.length(); i+=2){
                            Aplicacio aplicacio = new Aplicacio("",array.getJSONObject(i).getInt("ID"),
                                    (Bitmap) array.getJSONObject(i+1).get("THUMBNAIL"));
                            aplicacioList.add(aplicacio);
                        }
                        observador.onAppRequestResponse(aplicacioList);
                        break;
                    case "START":
                        observador.onStartResponse(
                                control.getJSONObject(2).getString("IP"),
                                control.getJSONObject(3).getString("PORT"),
                                control.getJSONObject(4).getString("URI")
                        );
                        break;
                    case "STOP":
                        observador.onStopResponse();
                        break;
                    case "ERROR":
                        observador.onErrorResponse();
                        break;
                    default:
                        Log.d("JSONProtocol","Error: RESPONSE code not found '"+ resposta.getString("RESPONSE") + "'");
                }
            else if (!resposta.isNull("ERROR"))
                switch (resposta.getString("ERROR")) {
                    case "error_code":
                        break;
                    default:
                        Log.d("JSONProtocol","Error: ERROR code not found '"+ resposta.getString("ERROR") + "'");
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        else
            Log.d("JSONProtocol","No obs setted! Reply doesn't do anything!");

    }

}


