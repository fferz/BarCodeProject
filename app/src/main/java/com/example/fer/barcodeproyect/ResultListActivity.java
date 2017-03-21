package com.example.fer.barcodeproyect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class ResultListActivity extends AppCompatActivity {

    ListView lv_ResultList;
    Button btnVolver, btnCompartir, btnLimpiar;
    static ArrayList result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Intent intent = getIntent();
        result = getIntent().getExtras().getStringArrayList("ResultListIntent");
        listAdapter(result);

        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnCompartir = (Button) findViewById(R.id.btnCompartir);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listAdapter(new ArrayList());
                Intent intent = new Intent();
                intent.putExtra("limpiarSharedPref", true);

            }
        });

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCodes(arrayListToString(result));
            }
        });

    }

        public ArrayAdapter listAdapter(ArrayList list){

            lv_ResultList = (ListView) findViewById(R.id.lv_ResultList);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            lv_ResultList.setAdapter(adapter);
            adapter.notifyDataSetChanged(); //solo para cuando limpio lista
            return adapter;

        }

        public void shareCodes(String s){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, s); //hace falta un string con el extra_text
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        public String arrayListToString(ArrayList arrayList){
            StringBuilder sb = new StringBuilder();
            for (Object s : arrayList)
            {
                sb.append(s);
                sb.append("\n");
            }
            return sb.toString();
        }


    //TODO list
    // eliminar elementos del arraylist
    // hacer tests
    }




