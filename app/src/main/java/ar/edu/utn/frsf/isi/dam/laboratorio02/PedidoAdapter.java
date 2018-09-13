package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Pedido;

public class PedidoAdapter extends ArrayAdapter<Pedido>{

    private Context ctx;
    private List<Pedido> datos;

    public PedidoAdapter(@NonNull Context context,@NonNull List<Pedido> objects) {
        super(context, 0, objects);
        this.ctx = context;
        this.datos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vistaResultado = convertView;
        if(vistaResultado==null){
            vistaResultado = LayoutInflater.from(this.ctx).inflate(R.layout.fila_pedido,parent,false);
        }
        PedidoHolder miHolder = (PedidoHolder) vistaResultado.getTag();
        if(miHolder == null){
            miHolder = new PedidoHolder(vistaResultado);

            miHolder.btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int  indice = (int) view.getTag();
                    Pedido pedidoSeleccionado = datos.get(indice);
                    if(pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO)||
                            pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO)||
                            pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
                        pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
                        PedidoAdapter.this.notifyDataSetChanged();
                    }
                }
            });

            vistaResultado.setTag(miHolder);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido pedidoActual = this.datos.get(position);
        miHolder.tvMailPedido.setText("Contacto: "+pedidoActual.getMailContacto());
        miHolder.tvCantidadItems.setText("Items: "+pedidoActual.getDetalle().size());
        miHolder.tvHoraEntrega.setText("Fecha de Entrega: "+sdf.format(pedidoActual.getFecha()));
        miHolder.estado.setText("Estado: "+pedidoActual.getEstado().toString());
        switch (pedidoActual.getEstado()){
            case LISTO:
                miHolder.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                miHolder.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                miHolder.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                miHolder.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                miHolder.estado.setTextColor(Color.BLUE);
                break;
        }
        miHolder.tvPrecio.setText("A pagar $: "+pedidoActual.total());
        if(pedidoActual.getRetirar()){
            miHolder.tipoEntrega.setImageResource(R.drawable.retira);
        }else{
            miHolder.tipoEntrega.setImageResource(R.drawable.envio);
        }
        miHolder.btnCancelar.setTag(position);

        return vistaResultado;
    }
}
