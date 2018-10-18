package ar.edu.utn.frsf.isi.dam.laboratorio02.dao;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;

public interface ProductoRepository {
    List<Producto> getLista();

    List<Categoria> getCategorias();

    Producto buscarPorId(Integer id);

    List<Producto> buscarPorCategoria(Categoria cat);
}
