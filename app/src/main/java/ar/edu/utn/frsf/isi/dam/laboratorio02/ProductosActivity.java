package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;

public class ProductosActivity extends AppCompatActivity {

    private ArrayAdapter<Categoria> categoriasAdapter;
    private ArrayAdapter<Producto> productosAdapter;
    private Spinner spinerCategorias;
    private ListView listaProductos;
    private EditText cantidad;
    private Button btnAgregar;
    private Integer idProductoSel;
    private ProductoRepository productoRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        productoRepo = new ProductoRepository();
        idProductoSel=0;
        Categoria[] cats = productoRepo.getCategorias().toArray(new Categoria[0]);
        categoriasAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,cats);
        spinerCategorias = (Spinner) findViewById(R.id.cmbProductosCategoria);
        spinerCategorias.setAdapter(categoriasAdapter);
        spinerCategorias.setSelection(0);
        productosAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice,productoRepo.buscarPorCategoria((Categoria)spinerCategorias.getSelectedItem()));
        listaProductos = (ListView) findViewById(R.id.lstProductos);
        listaProductos.setAdapter(productosAdapter);

        cantidad = (EditText) findViewById(R.id.edtProdCantidad);
        btnAgregar = (Button) findViewById(R.id.btnProdAddPedido);
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

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCallingActivity()!=null) {
                    int pos = listaProductos.getCheckedItemPosition();
                    Intent i = new Intent();
                    i.putExtra("cantidad", Integer.valueOf(cantidad.getText().toString()));
                    i.putExtra("idProducto", productosAdapter.getItem(pos).getId());
                    setResult(Activity.RESULT_OK, i);
                }
                finish();
            }
        });
    }
}
