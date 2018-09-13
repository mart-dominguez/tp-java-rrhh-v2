package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;

public class PedidoActivity extends AppCompatActivity {

    private EditText edtCorreo;
    private EditText edtDireccion;
    private EditText edtHoraEntrega;
    private RadioGroup optEnvio;
    private Button btnHacerPedido;
    private Button btnVolver;
    private Button btnAddProducto;
    private Button btnQuitarProducto;
    private ArrayAdapter<PedidoDetalle> detallePedidoAdapter;
    private ListView lista;

    private Pedido elPedido;

    private PedidoRepository repositorioPedido;
    private ProductoRepository repositorioProducto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        repositorioPedido = new PedidoRepository();
        repositorioProducto = new ProductoRepository();
        elPedido = new Pedido();

        edtCorreo = (EditText) findViewById(R.id.edtPedidoCorreo) ;
        edtDireccion = (EditText) findViewById(R.id.edtPedidoDireccion) ;
        edtHoraEntrega = (EditText) findViewById(R.id.edtPedidoHoraEntrega) ;
        optEnvio = (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        btnHacerPedido = (Button) findViewById(R.id.btnPedidoHacerPedido);
        btnVolver = (Button) findViewById(R.id.btnPedidoVolver);
        btnAddProducto = (Button) findViewById(R.id.btnPedidoAddProducto);
        btnQuitarProducto = (Button) findViewById(R.id.btnPedidoQuitarProducto);
        detallePedidoAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice,elPedido.getDetalle());
        lista = (ListView) findViewById(R.id.lstPedidoItems);
        lista.setAdapter(detallePedidoAdapter);
        optEnvio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                edtDireccion.setEnabled(((RadioButton)radioGroup.findViewById(R.id.optPedidoEnviar)).isChecked());
            }
        });


        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] horaIngresada = edtHoraEntrega.getText().toString().split(":");
                GregorianCalendar hora = new GregorianCalendar();
                int valorHora = Integer.valueOf(horaIngresada[0]);
                int valorMinuto = Integer.valueOf(horaIngresada[1]);
                hora.set(Calendar.HOUR,valorHora);
                hora.set(Calendar.MINUTE,valorMinuto);
                hora.set(Calendar.SECOND,Integer.valueOf(0));
                elPedido.setMailContacto(edtCorreo.getText().toString());
                elPedido.setRetirar(((RadioButton)optEnvio.findViewById(R.id.optPedidoRetira)).isChecked());
                if(!elPedido.getRetirar()) elPedido.setDireccionEnvio(edtCorreo.getText().toString());
                elPedido.setFecha(hora.getTime());
                elPedido.setEstado(Pedido.Estado.ACEPTADO);
                repositorioPedido.guardarPedido(elPedido);
                Log.d("APP_LAB02","Pedido "+elPedido.toString());
            }
        });

        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PedidoActivity.this,ProductosActivity.class);
                startActivityForResult(i,10,null);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10 && resultCode== Activity.RESULT_OK){
            Integer idProducto = data.getExtras().getInt("idProducto");
            Integer cantidad = data.getExtras().getInt("cantidad");
            Log.d("APP_LAB02", "resuelve "+idProducto+ " = "+cantidad );

            Producto prd = repositorioProducto.buscarPorId(idProducto);
            PedidoDetalle detalle = new PedidoDetalle(cantidad,prd);
            detalle.setPedido(elPedido);
            Log.d("APP_LAB02", "cantidad detalles "+elPedido.getDetalle().size());
            detallePedidoAdapter.notifyDataSetChanged();
            Log.d("APP_LAB02", "cantidad detalles "+elPedido.getDetalle().size());
        }
    }
}
