package com.example.asier.cebanc_burger;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Asier on 14/12/2016.
 */

public class AnadirHamburguesas extends DatosCliente {

    private TextView texto;
    private long backPressedTime = 0;
    private FloatingActionButton fab;
    private ImageView bebidas;
    private FloatingActionButton fabInfoTamaño;
    private FloatingActionButton fabInfoCarne;
    private RadioButton rdbClasica;
    private RadioButton rdbClasicaconqueso;
    private RadioButton rdbDobleconqueso;
    private RadioButton rdbVegetal;
    private RadioButton rdbEspecial;
    private double precioHamburguesas=0;
    private double precioTotalHamburguesas=0;

    private Spinner spinnerTamaño;
    private Spinner spinnerCarne;
    private String carne;
    private String tamaño;
    private String modelo;
    //private ViewPager viewPager;


    public ArrayList<String> hamburguesass=new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_hamburguesas);
        texto = (TextView) findViewById(R.id.textView14);
        fab=(FloatingActionButton)findViewById(R.id.btnAnadirBebida);
        fabInfoTamaño=(FloatingActionButton)findViewById(R.id.fabInfoTamaño);
        fabInfoCarne=(FloatingActionButton)findViewById(R.id.fabInfoCarne);
        bebidas=(ImageView) findViewById(R.id.imageView5);
        rdbClasica=(RadioButton)findViewById(R.id.rdbClasica);
        rdbClasicaconqueso=(RadioButton)findViewById(R.id.rdbClasicaconqueso);
        rdbDobleconqueso=(RadioButton)findViewById(R.id.rdbDobleconqueso);
        rdbVegetal=(RadioButton)findViewById(R.id.rdbVegetal);
        rdbEspecial=(RadioButton)findViewById(R.id.rdbEspecial);

        spinnerTamaño=(Spinner) findViewById(R.id.spinnerTamaño);
        spinnerCarne=(Spinner) findViewById(R.id.spinnerCarne);


        tabLayout();
        recogerDatosHamburguesa();
        rdbClasica.setChecked(true);

        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hamburguesass.size()>0){
                    lanzaBebidas();
                }else{
                    Toast.makeText(AnadirHamburguesas.this, " Elije una hamburguesa hombre ;) ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                botonesRadiales();
                calculaPrecioHamburguesas();
                precioTotalHamburguesas=precioTotalHamburguesas+precioHamburguesas;
                Toast.makeText(AnadirHamburguesas.this, "Se ha añadido una hamburguesa " + tamaño + " de carne de " + carne + "\nde tipo " + modelo+" "+precioHamburguesas+"€", Toast.LENGTH_SHORT).show();
                hamburguesass.add("1X Hamburguesa " + tamaño+" de "+carne+" tipo: "+modelo+" "+precioHamburguesas+" €" );


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


        ArrayAdapter<CharSequence> adaptador =
                ArrayAdapter.createFromResource
                        (this, R.array.Tamaño, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerTamaño.setAdapter(adaptador);
        spinnerTamaño.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        tamaño= spinnerTamaño.getSelectedItem().toString();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        ArrayAdapter<CharSequence> adaptador2 =
                ArrayAdapter.createFromResource
                        (this, R.array.Carne, android.R.layout.simple_spinner_item);
        adaptador2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerCarne.setAdapter(adaptador2);
        spinnerCarne.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        carne=spinnerCarne.getSelectedItem().toString();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });



    }

    public void recogerDatosHamburguesa() {
        Bundle extras = getIntent().getExtras();
        String s = "Hola " + extras.getString("nombre") + ", escoge tu hamburguesa:";
        texto.setText(s);

    }

     public void lanzaBebidas(){
        Bundle extras = getIntent().getExtras();
        Intent i=new Intent(AnadirHamburguesas.this,AnadirBebidas.class);
         i.putExtra("array2",hamburguesass);
         i.putExtra("preciototalhamburguesas",precioTotalHamburguesas);
        String keyIdentifer  = extras.getString("nombre");
        i.putExtra("zipi", keyIdentifer );

        startActivityForResult(i,1234);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }

    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // El tiempo para clickar  otra vez y salir
            backPressedTime = t;
            Toast.makeText(this, "Pulse otra vez 'atrás' para retroceder",
                    Toast.LENGTH_SHORT).show();
        } else {

            super.onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }

    public void tabLayout() {
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("HAMBURGUESAS").setIcon(R.drawable.logoh));


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

    public void botonesRadiales(){
        if (rdbClasica.isChecked()){
            modelo="Clasica";
        }else if(rdbClasicaconqueso.isChecked()){
            modelo="Clasica con queso";
        }else if(rdbDobleconqueso.isChecked()){
            modelo="Doble con queso";
        }else if(rdbVegetal.isChecked()){
            modelo="Vegetal";
        }else if(rdbEspecial.isChecked()){
            modelo="Especial";
        }


    }

    public void calculaPrecioHamburguesas() {
        if (tamaño.equalsIgnoreCase("Normal")) {
            if (rdbDobleconqueso.isChecked()) {
                precioHamburguesas = 5;
            } else if (rdbVegetal.isChecked() || rdbEspecial.isChecked()) {
                precioHamburguesas = 6;
            } else {
                precioHamburguesas = 4;
            }
        } else if (tamaño.equalsIgnoreCase("Whopper")) {
            if (rdbDobleconqueso.isChecked()) {
                precioHamburguesas = 6;
            } else if (rdbVegetal.isChecked() || rdbEspecial.isChecked()) {
                precioHamburguesas = 7;
            } else {
                precioHamburguesas = 5;
            }

        }
    }





}


