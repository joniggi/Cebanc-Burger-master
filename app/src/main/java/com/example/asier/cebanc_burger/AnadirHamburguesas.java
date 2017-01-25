package com.example.asier.cebanc_burger;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AnadirHamburguesas extends DatosCliente {

    //creamos todas las variables necesarias
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
    private int cantidad;
    private NumberPicker numerPicker;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;

    public ArrayList<String> hamburguesass=new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_hamburguesas);

        //identificamos los objetos del xml
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
        numerPicker = (NumberPicker)findViewById(R.id.numberPicker3);

        spinnerTamaño=(Spinner) findViewById(R.id.spinnerTamaño);
        spinnerCarne=(Spinner) findViewById(R.id.spinnerCarne);

        //este metodo carga el tablayout (la ventana verde de arriba)
        tabLayout();
        //este metodo recoje los datos de la actividad anterior
        recogerDatosHamburguesa();
        rdbClasica.setChecked(true);
        //asignamos el minimo y el maximo del numberpicker
        numerPicker.setMinValue(1);
        numerPicker.setMaxValue(10);

        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hamburguesass.size()>0){
                    lanzaBebidas();
                }else{
                    Toast.makeText(AnadirHamburguesas.this, " Elige una hamburguesa hombre ;) ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                botonesRadiales();
                calculaPrecioHamburguesas();
                cantidad=numerPicker.getValue();

                if (cantidad==0){
                }else if(cantidad>1){
                    Toast.makeText(AnadirHamburguesas.this, "Se han añadido " + cantidad + " hamburguesas" + " de carne de " + carne + "\nde tipo " + modelo+" "+precioHamburguesas+"€", Toast.LENGTH_SHORT).show();
                    hamburguesass.add(cantidad+"X Hamburguesa " + tamaño+" de "+carne+" tipo: "+modelo+" "+precioHamburguesas+" €" );
                    precioTotalHamburguesas=precioTotalHamburguesas+(precioHamburguesas*cantidad);
                }else{
                    Toast.makeText(AnadirHamburguesas.this, "Se ha añadido una hamburguesa " + tamaño + " de carne de " + carne + "\nde tipo " + modelo+" "+precioHamburguesas+"€", Toast.LENGTH_SHORT).show();
                    hamburguesass.add(cantidad+"X Hamburguesa " + tamaño+" de "+carne+" tipo: "+modelo+" "+precioHamburguesas+" €" );
                    precioTotalHamburguesas=precioTotalHamburguesas+(precioHamburguesas*cantidad);
                }



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

        //creamos el adapter con el string array correspondiente y le asignamos dicho adapter al spinner correspondiente
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
                        if (spinnerTamaño.getSelectedItem().toString().equalsIgnoreCase("Normal 4€")){
                            tamaño="Normal";
                        }else if(spinnerTamaño.getSelectedItem().toString().equalsIgnoreCase("Whopper 5€")){
                            tamaño="Whopper";
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        //de nuevo creamos el adapter con el string array correspondiente y le asignamos dicho adapter al spinner correspondiente
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
        //recojemos los datos de la actividad anterior
    public void recogerDatosHamburguesa() {
        Bundle extras = getIntent().getExtras();
        String s = "Hola " + extras.getString("nombre") + ", escoge tu hamburguesa:";
        texto.setText(s);
        nombre=""+extras.getString("nombre");
        apellidos=""+extras.getString("apellidos");
        direccion=""+extras.getString("direccion");
        telefono=""+extras.getString("telefono");


    }

    //lanzamos la siguiente actividad
     public void lanzaBebidas(){
        Bundle extras = getIntent().getExtras();
        Intent i=new Intent(AnadirHamburguesas.this,AnadirBebidas.class);
         i.putExtra("array2",hamburguesass);
         i.putExtra("preciototalhamburguesas",precioTotalHamburguesas);
        String keyIdentifer  = extras.getString("nombre");
        i.putExtra("zipi", keyIdentifer );
         i.putExtra("nombre", nombre );
         i.putExtra("apellidos", apellidos );
         i.putExtra("direccion", direccion );
         i.putExtra("telefono", telefono );


        startActivityForResult(i,1234);
        overridePendingTransition(R.anim.zoom_forward_in,R.anim.zoom_forward_out);
    }

    //metodo para ir hacia atras siempre que pulse dos veces la tecla en un intervalo determinado de tiempo
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

    //metodo para cargar el tablayout
    public void tabLayout() {
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("HAMBURGUESAS").setIcon(R.drawable.logoh));

    }


    //metodo para mostrar la informacion info_tam
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

    //metodo para mostrar la informacion info_tipo
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

    //metodo para saber que rdb esta checkeado y asignar a una variable string su correspondiente nombre
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
    //este metodo calcula el precio de las hamburguesas dependiendo del radiobutton que este marcado
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


