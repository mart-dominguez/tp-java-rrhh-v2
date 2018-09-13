package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.PedidoRepository;

public class HistorialActivity extends AppCompatActivity {

    private PedidoAdapter pedidoAdapter;
    private ListView listaPedidos;
    private Button btnNuevo;
    private Button btnMenu;

    private PedidoRepository repoPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        repoPedido = new PedidoRepository();

        pedidoAdapter = new PedidoAdapter(this,repoPedido.getLista());
        listaPedidos = (ListView) findViewById(R.id.lstHistorialPedidos);

        listaPedidos.setAdapter(pedidoAdapter);

        btnNuevo= (Button) findViewById(R.id.btnHistorialNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistorialActivity.this,PedidoActivity.class);
                startActivity(i);
            }
        });

        btnMenu= (Button) findViewById(R.id.btnHistorialMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistorialActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
