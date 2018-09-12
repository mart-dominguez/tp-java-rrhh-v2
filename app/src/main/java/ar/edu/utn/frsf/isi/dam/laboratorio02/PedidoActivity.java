package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PedidoActivity extends AppCompatActivity {

    private EditText edtCorreo;
    private EditText edtDireccion;
    private EditText edtHoraEntrega;
    private RadioGroup optEnvio;
    private Button btnHacerPedido;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        edtCorreo = (EditText) findViewById(R.id.edtPedidoCorreo) ;
        edtDireccion = (EditText) findViewById(R.id.edtPedidoDireccion) ;
        edtHoraEntrega = (EditText) findViewById(R.id.edtPedidoHoraEntrega) ;
        optEnvio = (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        btnHacerPedido = (Button) findViewById(R.id.btnPedidoHacerPedido);
        btnVolver = (Button) findViewById(R.id.btnPedidoVolver);

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

                Log.d("APP_LAB02","HORA "+hora.getTime());
            }
        });



    }
}
