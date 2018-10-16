 package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Pedido;

 public class MainActivity extends AppCompatActivity {

     private Button btnNuevoPedido;
     private Button btnHistorial;
     private Button btnListaProductos;
     private Button btnConfiguracion;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
         btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this, PedidoActivity.class);
                 startActivity(i);
             }
         });

         btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
         btnHistorial.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this, HistorialActivity.class);
                 startActivity(i);
             }
         });

         btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
         btnListaProductos.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this, ProductosActivity.class);
                 startActivity(i);
             }
         });

         btnConfiguracion= (Button) findViewById(R.id.btnMainConfiguracion);
         btnConfiguracion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this, ConfiguracionActivity.class);
                 startActivity(i);
             }
         });
     }
}
