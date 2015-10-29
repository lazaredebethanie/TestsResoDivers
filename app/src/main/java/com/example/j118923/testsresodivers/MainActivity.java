package com.example.j118923.testsresodivers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private TextView status,nom,prenom,dept;
    private EditText nomRech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView)findViewById(R.id.tvStatusConn);
        nomRech = (EditText)findViewById(R.id.tfNom);
        nom = (TextView)findViewById(R.id.tvResNom);
        prenom = (TextView)findViewById(R.id.tvResPrenom);
        dept = (TextView)findViewById(R.id.tvResDept);
    }

    // méthode permettant de contrôler que le device est connecté au réseau.
    public void ClkTstConn (View view) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // fetch data
                System.out.println("Connexion OK");
                status.setText("réseau OK");

            } else {
                // display error
                System.out.println("Connexion KO");
                status.setText("réseau KO");
            }
     }

    public void clkRetrieveData (View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        String stringUrl="http://www.villepinte93.com//ovh_info_pers.php";
        String nomRechL=nomRech.getText().toString();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        try {
            String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nomRechL, "UTF-8");
            new NetworkActivity(this,nomRech,nom,prenom,dept).execute(stringUrl,data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void clkAddPers (View view) {
        Intent intentAddAct = new Intent(this,AddPersonne.class);
        startActivity(intentAddAct);
    }

}
