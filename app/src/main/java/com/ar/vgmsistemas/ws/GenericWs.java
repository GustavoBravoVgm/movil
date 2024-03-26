package com.ar.vgmsistemas.ws;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ar.vgmsistemas.bo.PreferenciaBo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public abstract class GenericWs{

    //protected String serviceNamespace = "http://bo.vgmsistemas.com.ar";
    protected String webService;
    protected Integer metodo;
    protected Context context;
    protected String url;
    protected int timeOut = 200000; // En milisegundos
    //File cacheDir;
    
    protected static final String VOLUMEN_DE_DATOS_ALTO = "ALTO";
    protected static final String VOLUMEN_DE_DATOS_MEDIO = "MEDIO";
    protected static final String VOLUMEN_DE_DATOS_BAJO = "BAJO";

    protected static final int NO_REINTENTAR = 0;
    protected final String tag = "genericRequest";
    protected final String tagToken = "token";
    public static final String KEY_TOKEN = "token";
    protected String token = "";
    protected static final String TOKEN_INVALIDO = "401";
    public static final String PREFERENCIA = "preferencia_token";


    protected static final String AUTORIZATION = "Basic " + "dmdtcHJldmVudGE6cHJldmVudGEq";

    private RequestQueue mRequestQueue;
    public GenericWs(){
    	url = PreferenciaBo.getInstance().getPreferencia().getUrlServidorActiva();
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }
    protected synchronized String callWebService
            (String url, String webService, int method, final Map<String,String> parametros, String volumnenDeDatos)
            throws Exception {
        return callWebService(url, webService, method, parametros, volumnenDeDatos, null);
    }

    protected synchronized String callWebService
    	(String url, String webService, int method, final Map<String,String> parametros, String volumnenDeDatos, final JSONObject body)
    throws Exception {

        if (volumnenDeDatos.equals(VOLUMEN_DE_DATOS_ALTO)) {
            timeOut = PreferenciaBo.getInstance().getPreferencia().getTimeOutAlto();
        } else if (volumnenDeDatos.equals(VOLUMEN_DE_DATOS_MEDIO)) {
            timeOut = PreferenciaBo.getInstance().getPreferencia().getTimeOutMedio();
        } else if (volumnenDeDatos.equals(VOLUMEN_DE_DATOS_BAJO)) {
            timeOut = PreferenciaBo.getInstance().getPreferencia().getTimeOutBajo();
        }

        //refreshToken();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        token = preferences.getString(KEY_TOKEN,"");
        if (token.equals("")){ refreshToken();}

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest sr = new StringRequest(method,url+"/servicios"+webService/*+convertParamsToString(parametros)*/, future, future){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params = parametros;
                params.put("access_token",token);
                return params;
            }
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept","application/json");
                return headers;
            }
            /*@Override
            public byte[] getBody(){
                if (body != null)
                    return body.toString().getBytes();
                else
                    return null;
            }*/
        };

        sr.setTag(tag);
        RetryPolicy retryPolicy;
                    retryPolicy = new DefaultRetryPolicy
                    (timeOut, NO_REINTENTAR, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        sr.setRetryPolicy(retryPolicy);
        getRequestQueue().add(sr);

        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }  catch (ExecutionException e) {
            e.printStackTrace();
            if(e.getCause() instanceof VolleyError)
            {
                //grab the volley error from the throwable and cast it back
                VolleyError volleyError = (VolleyError)e.getCause();
                //now just grab the network response like normal
                NetworkResponse networkResponse = volleyError.networkResponse;
                if (networkResponse != null && String.valueOf(networkResponse.statusCode).equals(TOKEN_INVALIDO)){
                    refreshToken();
                    return callWebService(url, webService, method, parametros, volumnenDeDatos);
                }
            }
            return "";
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private String convertParamsToString(Map<String, String> params){
        if (params == null)
            params = new HashMap<>();

        params.put("access_token",token);
        String result = "";
        int count = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
            if(count == 0)
                result += "?";
            else
                result += "&";

            result += entry.getKey() + "=" + entry.getValue();
            count++;
        }
        return result;
    }

    public synchronized void refreshToken()
            throws Exception {

        webService = "/oauth/token";
        metodo = Request.Method.POST;
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest sr = new StringRequest(metodo,url+webService, future, future){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("grant_type","password");
                params.put("username","vgm");
                params.put("password","abc123");
                return params;
            }

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization",AUTORIZATION);
                headers.put("Accept","application/json");
                return headers;
            }
        };

        sr.setTag(tagToken);
        RetryPolicy retryPolicy;
        retryPolicy = new DefaultRetryPolicy
                (PreferenciaBo.getInstance().getPreferencia().getTimeOutToken(), NO_REINTENTAR, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        sr.setRetryPolicy(retryPolicy);
        getRequestQueue().add(sr);

        try {
            JSONObject jsonObject = new JSONObject(future.get());
            token = jsonObject.getString("access_token");
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
            editor.putString(KEY_TOKEN, token);
            editor.apply();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
