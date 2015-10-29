package com.example.j118923.testsresodivers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AddPersonne extends AppCompatActivity {

    private Context context;
    private TextView status;
    private EditText nom,prenom,dept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personne);
        nom = (EditText)findViewById(R.id.etNom);
        prenom = (EditText)findViewById(R.id.etPrenom);
        dept = (EditText)findViewById(R.id.etDept);
        status =(TextView)findViewById(R.id.tvStatus);

    }

    public void clkInsert (View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        String stringUrl="http://www.villepinte93.com//ovh_insert_pers.php";
        String nomL=nom.getText().toString();
        String prenomL=prenom.getText().toString();
        String deptL=dept.getText().toString();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        try {
            String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nomL, "UTF-8");
            data += "&" +URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenomL, "UTF-8");
            data += "&" +URLEncoder.encode("dept", "UTF-8") + "=" + URLEncoder.encode(deptL, "UTF-8");
            new NetworkActivity(this,status).execute(stringUrl,data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }




}





