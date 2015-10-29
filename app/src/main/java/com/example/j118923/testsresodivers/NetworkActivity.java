package com.example.j118923.testsresodivers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkActivity extends AsyncTask<String,Void,String> {


    private static final String DEBUG_TAG = "DEBUG";
    private Context context;
    private TextView nom,prenom,dept;
    private EditText nomRech;

    public NetworkActivity(Context context, EditText nomRech, TextView nom, TextView prenom, TextView dept) {
        this.context = context;
        this.nom = nom;
        this.prenom=prenom;
        this.dept=dept;
        this.nomRech=nomRech;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return downloadUrl(params[0], params[1]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    private String downloadUrl(String myurl, String param) throws IOException {
        System.out.println(myurl);
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            System.out.println(contentAsString);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    protected void onPostExecute(String result){
        // Parse les données JSON
        //String returnString =new String();
        System.out.println("result=" + result);
        try{
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                // Affichage nom et prénom dans le LogCat
                Log.i("log_tag", "Nom: " + nomRech +
                                ", Prénom: " + json_data.getString("prenom")+
                                ", Département: "+ json_data.getString("dept")
                );
                // Résultats de la requête
                //returnString += "\n\t" + jArray.getJSONObject(i);
                //returnString += "\n\t" +nb+"\t"+"Nom: " + json_data.getString("nom") + ", Prénom: " + json_data.getString("prenom");
                this.nom.setText(nomRech.getText().toString());
                this.prenom.setText(json_data.getString("prenom"));
                this.dept.setText(json_data.getString("dept"));

            }
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
    }

}
