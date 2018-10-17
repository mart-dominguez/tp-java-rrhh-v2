package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.CategoriaRest;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    EditText textoCat;
    Button btnCrear;
    Button btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        textoCat = (EditText) findViewById(R.id.txtNombreCategoria);
        btnCrear = (Button) findViewById(R.id.btnCrearCategoria);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r= new Runnable() {
                    @Override
                    public void run() {
                        Categoria c = new Categoria();
                        c.setNombre(textoCat.getText().toString());
                        CategoriaRest rest = new CategoriaRest();
                        rest.crearCategoria(c);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CategoriaActivity.this,"Categoria creada",Toast.LENGTH_LONG).show();
                                textoCat.setText("");
                            }
                        });
                    }
                };
                Thread t1 = new Thread(r);
                t1.start();
            }
        });
        btnMenu= (Button) findViewById(R.id.btnCategoriaVolver);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoriaActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
