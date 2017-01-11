/*package com.example.asier.cebanc_burger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.String.*;



public class Hamburguesas extends MainActivity {
    private Spinner spinnerTamaño,spinnerCarne;
    private FloatingActionButton fabInfoTamaño,fabInfoCarne;
    private NumberPicker numberPicker;
    private Button finalizar;
    private TextView jon;
    private String tamaño,carne;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburguesas);

        spinnerTamaño = (Spinner) findViewById(R.id.spinnerTamaño);
        spinnerCarne = (Spinner) findViewById(R.id.spinnerCarne);
        fabInfoTamaño = (FloatingActionButton) findViewById(R.id.fabInfoTamaño);
        fabInfoCarne = (FloatingActionButton) findViewById(R.id.fabInfoCarne);
        numberPicker=(NumberPicker)findViewById(R.id.numberPicker2);
        finalizar=(Button)findViewById(R.id.btnFinalizar);
        jon=(TextView) findViewById(R.id.jon);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);

        ArrayAdapter<CharSequence> adaptador =
                ArrayAdapter.createFromResource
                        (this, R.array.Tamaño, android.R.layout.simple_spinner_item);
//Creamos nuestro Spinner


        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerTamaño.setAdapter(adaptador);

        spinnerTamaño.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        tamaño = (String)parent.getSelectedItem();


                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });

        ArrayAdapter<CharSequence> adaptador2 =
                ArrayAdapter.createFromResource
                        (this, R.array.Carne, android.R.layout.simple_spinner_item);
//Creamos nuestro Spinner


        adaptador2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerCarne.setAdapter(adaptador2);

        spinnerCarne.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        carne = (String)parent.getSelectedItem();


                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });




        fabInfoTamaño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTam(v);

            }
        });

        fabInfoCarne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTipo(v);

            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recorgerDatosPedido(v);
                Intent i = new Intent(Hamburguesas.this, AnadirHamburguesas.class);
                i.putExtra("tamaño", tamaño);
                i.putExtra("carme", carne);
                startActivityForResult(i, 1234);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });


    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

    public void mostrarTam(View view) {
        final Dialog customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(false);
        customDialog.setContentView(R.layout.info_tam);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Información");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("Normal: Es el tamaño de hamburguesa estándar, 150g de carne.Es el tamaño más popular. \n"+
                " \n"+
                "Whopper : Es el tamaño de hamburguesa más grande que disponemos, 300g de carne , para los más fanáticos de las hamburguesas.");


        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });


        customDialog.show();
    }

    public void mostrarTipo(View view){
        final Dialog customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCancelable(false);
        customDialog.setContentView(R.layout.info_tam);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Información");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText(" -Ternera : Deliciosa carne 100% vacuno vasca\n" +
                "     Valor nutricional(por cada 100g):\n" +
                "     Energía:1136kJ\n" +
                "     Grasas:14g\n" +
                "     Carbohidratos:22g\n" +
                "     Proteina:13g\n" +
                " -Pollo : Exquisito pollo criado en la región\n" +
                "     Valor nutricional(por cada 100g):\n"+
                "     Energía:967kJ\n" +
                "     Grasas:10g\n" +
                "     Carbohidratos:23g\n" +
                "     Proteina:11g\n" +
                " -Buey : El mejor buey regional\n" +
                "     Valor nutricional(por cada 100g):\n"+
                "     Energía:917kJ\n" +
                "     Grasas:11g\n" +
                "     Carbohidratos:20g\n" +
                "     Proteina:15g\n");

        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

/*    public void recorgerDatosPedido(View view){
        int spinner_pos = spinnerTamaño.getSelectedItemPosition();
        jon.setText(spinner_pos);


    }


} */

