package com.example.asier.cebanc_burger;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

        //hemos tenido que poner sin metodo porque sino nos cascaba la app

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs5);
        tabs.addTab(tabs.newTab().setText("BIENVENIDO").setIcon(R.drawable.logom));


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
