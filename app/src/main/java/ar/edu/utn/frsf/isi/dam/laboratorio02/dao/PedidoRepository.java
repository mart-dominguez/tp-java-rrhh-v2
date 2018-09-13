package ar.edu.utn.frsf.isi.dam.laboratorio02.dao;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;

public class PedidoRepository {

    private static List<Pedido> LISTA_PEDIDOS = new ArrayList<>();
    private static int GENERADOR_ID_PEDIDO = 1;

    public List<Pedido> getLista(){
        return LISTA_PEDIDOS;
    }

    public void guardarPedido(Pedido p){
        if(p.getId()!=null && p.getId()>0) {
            LISTA_PEDIDOS.remove(p);
        }else{
            p.setId(GENERADOR_ID_PEDIDO ++);
        }
        LISTA_PEDIDOS.add(p);
    }

    public void borrarPedido(Pedido p){
        if(p.getId()!=null && p.getId()>0) {
            LISTA_PEDIDOS.remove(p);
        }
    }

    public Pedido buscarPorId(Integer id){
        for(Pedido p: LISTA_PEDIDOS){
            if(p.getId().equals(id)) return p;
        }
        return null;
    }


}
