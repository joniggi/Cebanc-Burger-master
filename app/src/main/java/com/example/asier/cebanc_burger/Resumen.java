package com.example.asier.cebanc_burger;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Resumen extends AppCompatActivity {
//declaramos las variable necesarias

    private FloatingActionButton llam;
    private Button stop;
    private FloatingActionButton carrito;

    public ArrayList<String> bebidass;
    public ArrayAdapter<String> adaptador3;
    public ListView listView3;

    public ArrayList<String> hamburguesass;
    public ArrayAdapter<String> adaptador4;
    public ListView listView4;
    public double precioBebidas;
    private double precioHamburguesas;
    private double precioTotalHamburguesas;
    private double precioTotal;
    private String cadena;
    private String cadena2;
    private TextView total;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;


    MediaPlayer mp3;
    MediaPlayer mp32;

    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long tiempo = 0;
    private long tiempo2 = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen);

        //recohjemos los datos de la actividad anterior
        Bundle extras = getIntent().getExtras();
        bebidass = extras.getStringArrayList("array1");
        hamburguesass = extras.getStringArrayList("array2");
        nombre=""+extras.getString("nombre");
        apellidos=""+extras.getString("apellidos");
        direccion=""+extras.getString("direccion");
        telefono=""+extras.getString("telefono");

        precioBebidas=extras.getDouble("preciobebidas");
        precioTotalHamburguesas=extras.getDouble("preciototalhamburguesas");

        bebidass.get(0);
        //identificamos los objetos del xml
        llam=(FloatingActionButton)findViewById(R.id.btnLlamar);
        stop=(Button) findViewById(R.id.stop);
        carrito=(FloatingActionButton) findViewById(R.id.carrito);
        total=(TextView) findViewById(R.id.total);

        mp3 = MediaPlayer.create(this,R.raw.registradora);
        mp32 = MediaPlayer.create(this,R.raw.notificacion);

        tabLayout();
        infor();

        precioTotal=precioBebidas+precioTotalHamburguesas;
        cadena = String.valueOf(precioTotal);
        total.setText(cadena + "€");

        regalo();

        cadena2 = String.valueOf(precioHamburguesas);


        llam.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llamar(v);
        }
    });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp3.start();
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Tramitando pedido ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                progressBarStatus = 0;

                tiempo = 0;

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {
                            progressBarStatus = hacerTiempo();

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                        if (progressBarStatus >= 100) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBar.dismiss();
                        }
                    }
                }).start();

            //acabar();
            }
        });

        //bebidass.add("");

        //creamos el adapter con el string array correspondiente y le asignamos dicho adapter al listview correspondiente
        adaptador3=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bebidass);
        listView3=(ListView)findViewById(R.id.listView3);
        listView3.setAdapter(adaptador3);

        //de nuevo creamos el adapter con el string array correspondiente y le asignamos dicho adapter al listview correspondiente
        adaptador4=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hamburguesass);
        listView4=(ListView)findViewById(R.id.listView4);
        listView4.setAdapter(adaptador4);


}

    //este metodo llama al telefono indicado en el metodo
    public void llamar(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:943674987"));
        startActivity(intent);
    }


    public int hacerTiempo() {
        while (tiempo <= 1000) {
            tiempo++;
            if (tiempo == 100) {
                return 10;
            } else if (tiempo == 200) {
                return 20;
            } else if (tiempo == 500) {
                return 50;
            } else if (tiempo == 800) {
                return 80;
            }

        }
        return 100;

    }

    public void acabar(){
        while(tiempo2 <=300){
            tiempo++;
        }
        Toast.makeText(this, "Gracias por confiar en nosotros", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Resumen.this, MainActivity.class);
        startActivityForResult(i, 1234);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    //metodo que carga el tablayout
    public void tabLayout() {
        TabLayout tabs2 = (TabLayout) findViewById(R.id.tabs3);
        tabs2.addTab(tabs2.newTab().setText("RESUMEN").setIcon(R.drawable.logor));

    }

    //este metodo lanza una notificacion dependiendo del importe de la compra
    public void regalo(){

        if(precioTotal>15 && precioTotal<=25){
            mp32.start();
            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=new Notification.Builder
                    (getApplicationContext()).setContentTitle("Peluche Android").setContentText("Gracias por su compra, ha ganado un fantastico peluche Android").
                    setContentTitle("Peluche Android").setSmallIcon(R.drawable.android).build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);
        }else if(precioTotal>25){
            mp32.start();
            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=new Notification.Builder
                    (getApplicationContext()).setContentTitle("Cheque regalo").setContentText("Gracias por su compra, ha ganado un cheque comida para comer en Cebanc").
                    setContentTitle("Cheque regalo").setSmallIcon(R.drawable.chequeregalo).build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);
        }

    }

    //metodo que lanza una ventana emergente para poder salir
    public void salir() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Salir");
        dialogo1.setMessage("¿Estas seguro de que deseas salir?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Toast.makeText(Resumen.this, "Hasta pronto !!!", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();


    }

    //metodo que informa al usuario de sus datos
    public void infor() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Informacion del cliente");
        dialogo1.setMessage("Nombre: " + nombre +"\n Apellidos: " + apellidos +"\n Direccion: " + direccion + "\n Telefono: " + telefono);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

        dialogo1.show();


    }

}
