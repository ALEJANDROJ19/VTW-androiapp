package marcer.pau.streaming.protocol;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import marcer.pau.streaming.model.Aplicacio;
import marcer.pau.streaming.model.NetworkParameters;
import marcer.pau.streaming.network.VolleySingleton;

public class TCPControl {
    private Context context;

    public TCPControl(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        //Initialize here ur stuff, lazy programmer!
    }

    public void doDiscovery() {
        requestQueue(JSONProtocol.getInstance().getDiscoveryMessage());
    }

    public void doAppsRequest() {
        requestQueue(JSONProtocol.getInstance().getAppRequestMessage());
    }

    public void doStartApp(Aplicacio aplicacio, List<String> options) {
        requestQueue(JSONProtocol.getInstance().getStartAppMessage(aplicacio,options));
    }

    public void doStopApp(Aplicacio aplicacio, List<String> options) {
        requestQueue(JSONProtocol.getInstance().getStopAppMessage(aplicacio,options));
    }

    public void doUpdateApp(List<Aplicacio> aplicacions) {
        requestQueue(JSONProtocol.getInstance().getAppUpdateMessage(aplicacions));
    }

    public void doErrorMsg(String errorCode, String errorMsg){
        requestQueue(JSONProtocol.getInstance().getErrorMessage(errorCode,errorMsg));
    }

    private void requestQueue(JSONObject object){
        VolleySingleton.getInstance(context).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        constructString(),
                        object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONProtocol.getInstance().inputJSON(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("TAG", ""+error.toString());
                                Log.d("TAG", ""+error.getLocalizedMessage());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "aplication/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );


    }

    private String constructString(){
        return "http://"+NetworkParameters.getInstance().getIp()+":"+NetworkParameters.getInstance().getPort_tcpcontrol();
    }
}
