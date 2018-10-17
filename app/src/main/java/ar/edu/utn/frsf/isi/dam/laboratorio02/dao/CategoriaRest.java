package ar.edu.utn.frsf.isi.dam.laboratorio02.dao;

import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRest {

    public void crearCategoria(Categoria c) {
        HttpURLConnection urlConnection = null;
        DataOutputStream printout =null;
        InputStream in =null;
        try {
            JSONObject categoriaJson = new JSONObject();
            categoriaJson.put("nombre",c.getNombre());

            URL url = new URL("http://10.0.2.2:5000/categorias");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            printout = new DataOutputStream(urlConnection.getOutputStream());
            Log.d("TEST-ARR",categoriaJson.toString());
            Log.d("TEST-ARR",URLEncoder.encode(categoriaJson.toString(),"UTF-8"));
            String str = categoriaJson.toString();
            byte[] jsonData=str.getBytes("UTF-8");
            printout.write(jsonData);
            printout.flush();

// leer las respuestas
            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();
            if( urlConnection.getResponseCode() ==200 || urlConnection.getResponseCode()==201){
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                    //analizar los datos recibidos
                    Log.d("LAB_04",sb.toString());
                }
            }else{
                // lanzar excepcion o mostrar mensaje de error
                // que no se pudo ejecutar la operacion
            }
            Log.d("LAB_04",urlConnection.getResponseMessage());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(printout!=null) try {
                printout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }
    }

    public List<Categoria> listarTodas(){
        List<Categoria> resultado = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        InputStream in =null;
        try {
            URL url = new URL("http://10.0.2.2:5000/categorias");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Type","application/json");
            urlConnection.setRequestMethod("GET");
            // leer las respuestas
            in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader isw = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            int data = isw.read();
            if( urlConnection.getResponseCode() ==200 || urlConnection.getResponseCode()==201){
                while (data != -1) {
                    char current = (char) data;
                    sb.append(current);
                    data = isw.read();
                    //analizar los datos recibidos
                }
                Log.d("LAB_04",sb.toString());
                JSONTokener tokener = new JSONTokener(sb.toString());
                JSONArray listaCategorias = (JSONArray) tokener.nextValue();
                for(int i=0;i<listaCategorias.length();i++){
                    Categoria cat = new Categoria();
                    cat.setId(listaCategorias.getJSONObject(i).getInt("id"));
                    cat.setNombre(listaCategorias.getJSONObject(i).getString("nombre"));
                    resultado.add(cat);
                }
            }else{
                // lanzar excepcion o mostrar mensaje de error
                // que no se pudo ejecutar la operacion
            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(in!=null) try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(urlConnection !=null)urlConnection.disconnect();
        }
        return resultado;
    }
}
