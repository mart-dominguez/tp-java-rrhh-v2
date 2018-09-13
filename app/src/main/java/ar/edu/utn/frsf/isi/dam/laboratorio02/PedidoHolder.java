package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PedidoHolder {
    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView estado;
    public ImageView tipoEntrega;
    public Button btnCancelar;
    public Button btnVerDetalle;
    public PedidoHolder(View v){
        this.tvMailPedido = (TextView) v.findViewById(R.id.tvFilaCorreoPedido);
        this.tvHoraEntrega = (TextView) v.findViewById(R.id.tvFilaHoraEntrega);
        this.tvCantidadItems = (TextView) v.findViewById(R.id.tvFilaCantidadItems);
        this.tvPrecio = (TextView) v.findViewById(R.id.tvFilaPrecio);
        this.estado = (TextView) v.findViewById(R.id.tvFilaEstado);
        this.tipoEntrega = (ImageView) v.findViewById(R.id.imgFilaTipoEntrega);
        this.btnCancelar = (Button) v.findViewById(R.id.btnFilaCancelar);
        this.btnVerDetalle = (Button) v.findViewById(R.id.btnVerDetalle);
    }

}
