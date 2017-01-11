/*package com.example.asier.cebanc_burger;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class Bebidas extends AppCompatActivity {
    private Spinner spinnerBebidas;
    private NumberPicker numberPicker;
    private Button añadir;
    int numero;
    private TextView jon;
    //private FloatingActionButton fabInfoTamaño,fabInfoCarne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebidas);



        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        añadir = (Button) findViewById(R.id.añadir);
        jon = (TextView) findViewById(R.id.jon);
        spinnerBebidas = (Spinner) findViewById(R.id.spinnerBebidas);

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
                        //se puede hacer tambien asi: texto.setText("Seleccionado:"+ array[position]);

//Podemos recuperar el ítem seleccionado usando
//parent.getItemAtPosition(position)
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        jon.setText("");
                    }
                });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bebidasElegidas();
            }
        });


    }

  public void bebidasElegidas() {
        if (numero == 0) {
            //bebidass.add
            Intent i = new Intent(Bebidas.this, AnadirBebidas.class);
            i.putExtra("refresco", "Coca-Cola");
            startActivityForResult(i, 1234);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }




    }
}
            */
