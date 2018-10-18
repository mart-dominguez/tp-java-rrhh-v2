package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.CategoriaRest;
import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.ProductoRepositoryMemory;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;

public class ProductosActivity extends AppCompatActivity {

    private ArrayAdapter<Categoria> categoriasAdapter;
    private ArrayAdapter<Producto> productosAdapter;
    private Spinner spinerCategorias;
    private ListView listaProductos;

    private EditText edtCantidad;
    private Button btnAgregar;
    private Integer idProductoSel;
    private ProductoRepositoryMemory productoRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        productoRepo = new ProductoRepositoryMemory();
        idProductoSel=0;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                CategoriaRest catRest = new CategoriaRest();
                Categoria[] cats = catRest.listarTodas().toArray(new Categoria[0]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        categoriasAdapter = new ArrayAdapter<Categoria>(ProductosActivity.this,android.R.layout.simple_spinner_dropdown_item,cats);
                        spinerCategorias = (Spinner) findViewById(R.id.cmbProductosCategoria);
                        spinerCategorias.setAdapter(categoriasAdapter);
                        spinerCategorias.setSelection(0);
                        spinerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                productosAdapter.clear();
                                productosAdapter.addAll(productoRepo.buscarPorCategoria((Categoria)parent.getItemAtPosition(position)));
                                productosAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        productosAdapter= new ArrayAdapter<Producto>(ProductosActivity.this,android.R.layout.simple_list_item_single_choice,productoRepo.buscarPorCategoria((Categoria)spinerCategorias.getSelectedItem()));
                        listaProductos = (ListView) findViewById(R.id.lstProductos);
                        listaProductos.setAdapter(productosAdapter);
                    }
                });
            }
        };
        Thread hiloCargarCombo = new Thread(r);
        hiloCargarCombo.start();



        edtCantidad = (EditText) findViewById(R.id.edtProdCantidad);
        btnAgregar = (Button) findViewById(R.id.btnProdAddPedido);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int cantidad = Integer.valueOf(edtCantidad.getText().toString());
                    if(cantidad<=0) return;
                    int pos = listaProductos.getCheckedItemPosition();
                    Intent i = new Intent();
                    i.putExtra("cantidad", cantidad);
                    i.putExtra("idProducto", productosAdapter.getItem(pos).getId());
                    setResult(Activity.RESULT_OK, i);
                    finish();
            }
        });
    }
}
