package com.example.asier.cebanc_burger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    //definimos las varible que vamos a utilizar
    private ImageView entrar;
    private MediaPlayer mp3;
    private ImageView internet;
    private FloatingActionButton basedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //Aqui cargamos el mapa en el fragment del xml
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    //identificamos los objetos del xml
        entrar = (ImageView) findViewById(R.id.entrar);
        internet = (ImageView) findViewById(R.id.internet);
        mp3 = MediaPlayer.create(this,R.raw.moneda);
        basedatos = (FloatingActionButton) findViewById(R.id.basedatos);

        //hemos tenido que poner sin metodo porque sino nos cascaba la app


        TabLayout tabs = (TabLayout) findViewById(R.id.tabs5);
        tabs.addTab(tabs.newTab().setText("BIENVENIDO").setIcon(R.drawable.logom));

        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 1);

        SQLiteDatabase db = burguer_dbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {//Cerramos la base de datos
            db.close();
        }

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp3.start();
                //lanzamos la siguiente actividad
                Intent i = new Intent
                        (MainActivity.this,DatosCliente.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //con el metodo parse de la clase Uri abrimos la siguiente direccion
                Uri uri = Uri.parse("http://www.cebanc.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        basedatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarBasedatos(v);
            }
        });

    }

    /*public void runBaseDeDatos(){

        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 1);

        SQLiteDatabase db = burguer_dbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {//Cerramos la base de datos
            db.close();
        }
    }*/

    public void comprobarBasedatos(View view){
        HamburSQLiteHelper kebab_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 2);
        SQLiteDatabase db = kebab_dbh.getReadableDatabase();

        Cursor ce = db.rawQuery("SELECT USUARIO, NOMBRE_USU, APELLIDOS, DIRECCION, TELF FROM USUARIOS", null);

        try{
            if(ce.moveToFirst()){
                do{
                    int usuario = ce.getInt(0);
                    String nombre_usuario = ce.getString(1);
                    String apellidos = ce.getString(2);
                    String direccion = ce.getString(3);
                    String telefono = ce.getString(4);

                    Toast toastCarrito = Toast.makeText(getApplicationContext(),
                            Integer.toString(usuario) + nombre_usuario + apellidos + direccion + telefono, Toast.LENGTH_SHORT);
                    toastCarrito.show();
                }while(ce.moveToNext());
            }
        }catch(Exception ex){

        }

        ce.close();
        db.close();


    }


    public void onMapReady(GoogleMap googleMap) {

        //este metodo es el que pone el mapa en modo satelite
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //a continuacion metemos las coordenadas asi como el marcador y demas
        LatLng cebancburguer = new LatLng(43.30469411639206, -
                2.0168709754943848);
        googleMap.addMarker(new MarkerOptions()
                .position(cebancburguer)
                .title("Cebanc-Burguer"));


        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cebancburguer)
                .zoom(13)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition
                (cameraPosition));

    }
}
