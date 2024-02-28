package org.uv.dapp01practica01;

import java.util.List;

public interface IDAO<T, ID> {

    public T buscarById(ID id);

    List<T> buscarAll();

    public boolean guardar(T pojo);

    public boolean eliminar(ID id);

    public boolean modificar(T pojo);
}
