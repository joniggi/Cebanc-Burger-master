package com.example.asier.cebanc_burger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Asier on 02/01/2017.
 */

public class AnadirBebidas extends AppCompatActivity {
    int numero;
    private TextView texto;
    private long backPressedTime = 0;
    private FloatingActionButton fab;
    //private ViewPager viewPager;
    private Button resumen;
    private Spinner spinnerBebidas;
    private double precioBebidas=0;

    public ArrayList<String> bebidass;
    public ArrayAdapter<String> adaptador2;
    public ListView listView2;
    private Button añadir;

    public ArrayList<String> hamburguesass;
    private double precioTotalHamburguesas;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_bebidas);

        Bundle extras = getIntent().getExtras();
        hamburguesass = extras.getStringArrayList("array2");
        precioTotalHamburguesas = extras.getDouble("preciototalhamburguesas");

        texto = (TextView) findViewById(R.id.textView14);
        recogerDatosHamburguesa();
        fab=(FloatingActionButton)findViewById(R.id.btnAnadirBebida);
        resumen=(Button)findViewById(R.id.button);
        añadir=(Button)findViewById(R.id.añadir);
        spinnerBebidas = (Spinner) findViewById(R.id.spinnerBebidas);
        tabLayout();


        bebidass=new ArrayList<String>();


        adaptador2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bebidass);
        listView2=(ListView)findViewById(R.id.listView2);
        listView2.setAdapter(adaptador2);



        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int posicion=i;

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(AnadirBebidas.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Estas seguro de que quieres eliminar ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        bebidass.remove(posicion);
                        adaptador2.notifyDataSetChanged();
                        //MIRAR LO DE LOS PRECIOS PARA RESTAR
                        if(posicion==0){
                            precioBebidas=precioBebidas-1.5;
                        }else if(posicion==1){
                            precioBebidas=precioBebidas-2;
                        }else if(posicion==2){
                            precioBebidas=precioBebidas-2;
                        }else if(posicion==3){
                            precioBebidas=precioBebidas-2.5;
                        }else if(posicion==4){
                            precioBebidas=precioBebidas-1;
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });

        ArrayAdapter<CharSequence> adaptador =
                ArrayAdapter.createFromResource
                        (this, R.array.Drinks, android.R.layout.simple_spinner_item);
//Creamos nuestro Spinner


        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerBebidas.setAdapter(adaptador);

        spinnerBebidas.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        numero = parent.getSelectedItemPosition();

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numero==0){
                    bebidass.add("Coca-Cola       1,50€");
                    precioBebidas=precioBebidas+1.5;
                    adaptador2.notifyDataSetChanged();
                }else if(numero==1){
                    bebidass.add("Kas Naranja       2€");
                    precioBebidas=precioBebidas+2;
                    adaptador2.notifyDataSetChanged();
                }else if(numero==2){
                    bebidass.add("Kas Limon       2€");
                    precioBebidas=precioBebidas+2;
                    adaptador2.notifyDataSetChanged();
                }else if(numero==3){
                    bebidass.add("Cerveza       2,50€");
                    precioBebidas=precioBebidas+2.5;
                    adaptador2.notifyDataSetChanged();
                }else if(numero==4){
                    bebidass.add("Agua       1€");
                    precioBebidas=precioBebidas+1;
                    adaptador2.notifyDataSetChanged();
                }


            }
        });


        resumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bebidass.size()>0){
                    lanzaResumen();
                }else{
                    Toast.makeText(AnadirBebidas.this, " ¿Te vas a quedar con sed ? ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public void recogerDatosHamburguesa() {
        Bundle extras = getIntent().getExtras();
        String s = "Bueno " + extras.getString("zipi") + " es hora de elejir las bebidas";
        texto.setText(s);
    }

   /* public void lanzarBebidas(){
        Intent in=new Intent(AnadirBebidas.this,Bebidas.class);
        in.putExtra("array1",bebidass);
        startActivity(in);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);}*/

    public void lanzaResumen(){
        Intent i=new Intent(AnadirBebidas.this,Resumen.class);
        i.putExtra("array1",bebidass);
        i.putExtra("array2",hamburguesass);
        i.putExtra("preciobebidas",precioBebidas);
        i.putExtra("preciototalhamburguesas",precioTotalHamburguesas);
        startActivity(i);
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
        TabLayout tabs2 = (TabLayout) findViewById(R.id.tabs2);
        tabs2.addTab(tabs2.newTab().setText("BEBIDAS").setIcon(R.drawable.logob));

    }




}


























