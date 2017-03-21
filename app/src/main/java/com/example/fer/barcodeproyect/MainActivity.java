package com.example.fer.barcodeproyect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnScan, btnListar;
    TextView tvResult;
    Boolean limpiarListaBoolean = false;
    static SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button) findViewById(R.id.btnScan);
        btnListar = (Button) findViewById(R.id.btnListar);
        tvResult = (TextView) findViewById(R.id.tvResult);

        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);

        final Context context = this;

        //limpia el sharedPreferences
        if (getIntent().getExtras() != null){
                cleanData();
        }
        sharedPref = context.getSharedPreferences(
                "ScanList", Context.MODE_PRIVATE);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                intentIntegrator.initiateScan();

            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ResultListActivity.class);
                intent.putExtra("ResultListIntent", getResultList(context));
                startActivity(intent);
            }
        });

    }

    //get the list of all scanned things
    public ArrayList<String> getResultList(Context context){

        ArrayList<String> list = new ArrayList<String>();

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPref.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            list.add(entry.getValue().toString());

        }

        return list;
    }


    //get the results of the scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null) {
            if(result.getContents() == null) {
                tvResult.setText("Cancelled scan");
            } else {
                tvResult.setText("Scanned: "+ result.getContents());
                saveData(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    public void saveData(String data){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(data, data); //putString(clave, valor)
        editor.commit();
    }

    public void cleanData(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

}

