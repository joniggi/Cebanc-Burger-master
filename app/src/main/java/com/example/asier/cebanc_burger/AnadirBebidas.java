package com.example.asier.cebanc_burger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AnadirBebidas extends AppCompatActivity {
    int numero;
    private TextView texto;
    private long backPressedTime = 0;
    private FloatingActionButton fab;

    private ImageView resumen;
    private Spinner spinnerBebidas;
    private double precioBebidas=0;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;

    public ArrayList<String> bebidass;
    public ArrayAdapter<String> adaptador2;
    public ListView listView2;
    private Button añadir;

    public ArrayList<String> hamburguesass;
    private double precioTotalHamburguesas;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_bebidas);
        //Aqui recojemos los datos de la actividad anterior
        Bundle extras = getIntent().getExtras();
        hamburguesass = extras.getStringArrayList("array2");
        precioTotalHamburguesas = extras.getDouble("preciototalhamburguesas");

        //identificamos los objetos del xml
        texto = (TextView) findViewById(R.id.textView14);
        recogerDatosHamburguesa();
        fab=(FloatingActionButton)findViewById(R.id.btnAnadirBebida);
        resumen=(ImageView) findViewById(R.id.imageView8);
        añadir=(Button)findViewById(R.id.añadir);
        spinnerBebidas = (Spinner) findViewById(R.id.spinnerBebidas);
        tabLayout();


        bebidass=new ArrayList<String>();

        //creamos el adapter con el string array correspondiente y le asignamos dicho adapter al listview correspondiente
        adaptador2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bebidass);
        listView2=(ListView)findViewById(R.id.listView2);
        listView2.setAdapter(adaptador2);


    //este metodo elemina la posicion deseada del listview
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

                        precioBebidas=precioBebidas-2;

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

        //creamos el adapter con el string array correspondiente y le asignamos dicho adapter al spinner
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
        //añade las bebidas

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numero==0){
                    bebidass.add("Coca-Cola       2€");
                    precioBebidas=precioBebidas+2;
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
                    bebidass.add("Cerveza       2€");
                    precioBebidas=precioBebidas+2;
                    adaptador2.notifyDataSetChanged();
                }else if(numero==4){
                    bebidass.add("Agua       2€");
                    precioBebidas=precioBebidas+2;
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

    //recoje los datos de la actividad anterior
    public void recogerDatosHamburguesa() {
        Bundle extras = getIntent().getExtras();
        String s = "Bueno " + extras.getString("zipi") + " es hora de elegir las bebidas";
        texto.setText(s);
        nombre=""+extras.getString("nombre");
        apellidos=""+extras.getString("apellidos");
        direccion=""+extras.getString("direccion");
        telefono=""+extras.getString("telefono");
    }

    //lanza los datos y la siguiente actividad
    public void lanzaResumen(){
        Intent i=new Intent(AnadirBebidas.this,Resumen.class);
        i.putExtra("array1",bebidass);
        i.putExtra("array2",hamburguesass);
        i.putExtra("preciobebidas",precioBebidas);
        i.putExtra("preciototalhamburguesas",precioTotalHamburguesas);
        i.putExtra("nombre", nombre );
        i.putExtra("apellidos", apellidos );
        i.putExtra("direccion", direccion );
        i.putExtra("telefono", telefono );
        startActivity(i);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }

    //este metodo vuelve atras siempre que pulses el boton dos veces en el intermalo de tiempo determinado
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

    //carga el tablayout
    public void tabLayout() {
        TabLayout tabs2 = (TabLayout) findViewById(R.id.tabs2);
        tabs2.addTab(tabs2.newTab().setText("BEBIDAS").setIcon(R.drawable.logob));

    }




}


























