package org.uv.dapp01practica01;
import java.util.List;

public interface IDAO {
    List<Empleado> buscarAll();
    boolean guardar(Empleado empleado);
    boolean eliminar(int id);
    boolean modificar(Empleado empleado, int id);
}
