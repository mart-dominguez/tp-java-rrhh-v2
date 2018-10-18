package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.CategoriaRest;
import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.RestClient;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity {

    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private Boolean flagActualizacion;

    private ArrayAdapter<Categoria> comboAdapter;

    private Producto p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_productos);
        flagActualizacion = false;
        opcionNuevoBusqueda = (ToggleButton) findViewById(R.id.abmProductoAltaNuevo);

        idProductoBuscar = (EditText) findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = (EditText) findViewById(R.id.abmProductoNombre);
        descProducto = (EditText) findViewById(R.id.abmProductoDescripcion);
        precioProducto = (EditText) findViewById(R.id.abmProductoPrecio);
        comboCategorias = (Spinner) findViewById(R.id.abmProductoCategoria);
        btnMenu = (Button) findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = (Button) findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = (Button) findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar= (Button) findViewById(R.id.btnAbmProductoBorrar);
        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        idProductoBuscar.setEnabled(false);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                CategoriaRest catRest = new CategoriaRest();
                Categoria[] cats = catRest.listarTodas().toArray(new Categoria[0]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comboAdapter = new ArrayAdapter<Categoria>(GestionProductoActivity.this,android.R.layout.simple_spinner_dropdown_item,cats);
                        comboCategorias.setAdapter(comboAdapter);
                    }
                });
            }
        };
        Thread cargarCombo = new Thread(r);
        cargarCombo.start();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = new Producto(nombreProducto.getText().toString(),descProducto.getText().toString(),Double.valueOf(precioProducto.getText().toString()).doubleValue(),(Categoria)comboCategorias.getSelectedItem());
                ProductoRetrofit clienteRestProducto = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                Call<Producto> llamadaGuardar=null;
                Log.d("LAB_04",p.toString());
                if(flagActualizacion){
                   p.setId(Integer.valueOf(idProductoBuscar.getText().toString()));
                    llamadaGuardar = clienteRestProducto.actualizarProducto(p.getId(),p);
               }else {
                    llamadaGuardar= clienteRestProducto.crearProducto(p);
               }
                llamadaGuardar.enqueue(new Callback<Producto>() {
                       @Override
                       public void onResponse(Call<Producto> call, Response<Producto> response) {
                           Toast.makeText(GestionProductoActivity.this,"Listo!",Toast.LENGTH_LONG).show();
                           if(response.code()==200 || response.code()==201 || response.code()==304 ){
                               Toast.makeText(GestionProductoActivity.this,"Producto creado",Toast.LENGTH_LONG).show();
                               nombreProducto.setText("");
                               descProducto.setText("");
                               precioProducto.setText("");
                           }
                       }

                       @Override
                       public void onFailure(Call<Producto> call, Throwable t) {

                       }
                   });
            }
        });

        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flagActualizacion =isChecked;
                btnBuscar.setEnabled(isChecked);
                btnBorrar.setEnabled(isChecked);
                idProductoBuscar.setEnabled(isChecked);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                Integer idBuscar = Integer.valueOf(idProductoBuscar.getText().toString());
                Call<Producto> llamada = clienteRest.buscarProductoPorId(idBuscar);
                llamada.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {
                        if(response.code()==200 || response.code()==201){
                            p = response.body();
                            nombreProducto.setText(p.getNombre());
                            descProducto.setText(p.getDescripcion());
                            precioProducto.setText(""+p.getPrecio());
                            int posicion = comboAdapter.getPosition(p.getCategoria());
                            comboCategorias.setSelection(posicion);
                        }
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {

                    }
                });
            }
        });

    }
}
