package ar.edu.utn.frsf.isi.dam.laboratorio02.dao;

import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.isi.dam.laboratorio02.modelo.Producto;

public class ProductoRepositoryDefault implements ProductoRepository {
    @Override
    public List<Producto> getLista() {
        return null;
    }

    @Override
    public List<Categoria> getCategorias() {
        return null;
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return null;
    }

    @Override
    public List<Producto> buscarPorCategoria(Categoria cat) {
        return null;
    }
}
