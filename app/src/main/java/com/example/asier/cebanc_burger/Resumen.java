package com.example.asier.cebanc_burger;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Resumen extends AppCompatActivity {

    private FloatingActionButton llam;
    private FloatingActionButton facebook;
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

    ImageView carrito;
    MediaPlayer mp3;

    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long tiempo = 0;
    private long tiempo2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen);

        Bundle extras = getIntent().getExtras();
        bebidass = extras.getStringArrayList("array1");
        hamburguesass = extras.getStringArrayList("array2");

        precioBebidas=extras.getDouble("preciobebidas");
        precioTotalHamburguesas=extras.getDouble("preciototalhamburguesas");

        bebidass.get(0);
        llam=(FloatingActionButton)findViewById(R.id.btnLlamar);
        facebook=(FloatingActionButton) findViewById(R.id.facebook);
        carrito=(ImageView) findViewById(R.id.carrito);
        total=(TextView) findViewById(R.id.total);

        mp3 = MediaPlayer.create(this,R.raw.registradora);

        tabLayout();

        precioTotal=precioBebidas+precioTotalHamburguesas;
        cadena = String.valueOf(precioTotal);
        total.setText(cadena + "€");

        cadena2 = String.valueOf(precioHamburguesas);


        llam.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llamar(v);
        }
    });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.facebook.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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

        adaptador3=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bebidass);
        listView3=(ListView)findViewById(R.id.listView3);
        listView3.setAdapter(adaptador3);

        adaptador4=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,hamburguesass);
        listView4=(ListView)findViewById(R.id.listView4);
        listView4.setAdapter(adaptador4);


}


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

    public void tabLayout() {
        TabLayout tabs2 = (TabLayout) findViewById(R.id.tabs3);
        tabs2.addTab(tabs2.newTab().setText("RESUMEN").setIcon(R.drawable.logor));

    }

}
