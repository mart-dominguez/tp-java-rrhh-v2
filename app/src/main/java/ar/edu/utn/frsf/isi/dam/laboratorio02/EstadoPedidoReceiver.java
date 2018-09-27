package ar.edu.utn.frsf.isi.dam.laboratorio02;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import ar.edu.utn.frsf.isi.dam.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    PedidoRepository repositoryPedido = new PedidoRepository();

    public static final String TAG_APP = "##EstadoPedidoReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG_APP,"Recibi el broadcast "+intent.getAction());
        int idPedido = intent.getExtras().getInt("idPedido");
        Pedido p = repositoryPedido.buscarPorId(idPedido);
        Toast.makeText(context,"Pedido para "+p.getMailContacto()+" ha cambiado de estado a "+p.getEstado().toString(),Toast.LENGTH_LONG).show();
        // Create an explicit intent for an Activity in your app
        Intent intentDestino = new Intent(context, PedidoActivity.class);
        intentDestino.putExtra("idPedidoSeleccionado",idPedido);
        intentDestino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentDestino, PendingIntent.FLAG_UPDATE_CURRENT);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CANAL01")
                .setSmallIcon(R.drawable.imagen06)
                .setContentTitle("Tu Pedido fue aceptado")
                .setContentText("Preparate para disfrutar de tu pedido.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("El costo será de $"+p.total())
                        .addLine("Previsto el envio para "+sdf.format(p.getFecha())))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(100+p.getId(), mBuilder.build());
    }
}
