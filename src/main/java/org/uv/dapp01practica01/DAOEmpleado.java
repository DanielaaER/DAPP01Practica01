package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOEmpleado implements IDAO<PojoEmpleado, Integer> {

    private final Conexion databaseConnection;

    public DAOEmpleado() {
        this.databaseConnection = Conexion.getInstance();
    }

    @Override
    public boolean guardar(PojoEmpleado empleado) {
        Conexion con = Conexion.getInstance();
        TransaccionDB tra = new TransaccionDB<PojoEmpleado>(empleado) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "insert into empleadotemporal (nombre,direccion,telefono) values (?,?,?)";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setString(1, empleado.getNombre());
                    pstm.setString(2, empleado.getDireccion());
                    pstm.setString(3, empleado.getTelefono());
                    pstm.execute();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

            }
        };
        return con.execute(tra);
    }

    @Override
    public boolean eliminar(Integer id) {
        Conexion con = Conexion.getInstance();
        TransaccionDB tra = new TransaccionDB<Integer>(id) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "DELETE FROM empleadotemporal WHERE id = ?";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setInt(1, pojo);
                    pstm.execute();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        return con.execute(tra);
    }

    @Override
    public boolean modificar(PojoEmpleado empleado, Integer id) {
        Conexion con = databaseConnection;
        TransaccionDB tra = new TransaccionDB<PojoEmpleado>(empleado) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "UPDATE empleadotemporal SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setString(1, empleado.getNombre());
                    pstm.setString(2, empleado.getDireccion());
                    pstm.setString(3, empleado.getTelefono());
                    pstm.setInt(4, id);
                    pstm.execute();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        return con.execute(tra);
    }

    @Override
    public PojoEmpleado buscarById(Integer id) {
        Conexion con = databaseConnection;
        SelectionDB select = new SelectionDB() {
            @Override
            public List buscar(Connection con) {
                try {
                    String sql = "SELECT * FROM empleadotemporal WHERE id = ?";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.setObject(1, id);
                    ResultSet result = pstm.executeQuery();
                    List lst = new ArrayList<PojoEmpleado>();
                    PojoEmpleado p = new PojoEmpleado();
                    if (result.next()) {
                        p.setId(result.getInt("id"));
                        p.setNombre(result.getString("nombre"));
                        p.setDireccion(result.getString("direccion"));
                        p.setTelefono(result.getString("telefono"));
                        lst.add(p);
                    }
                    return lst;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }

        };
        PojoEmpleado empleado = (PojoEmpleado) con.select(select).get(0);
        return empleado;

    }

    @Override
    public List<PojoEmpleado> buscarAll() {

        Conexion con = databaseConnection;
        SelectionDB select = new SelectionDB() {
            @Override
            public List buscar(Connection con) {
                try {
                    String sql = "SELECT * FROM empleadotemporal";
                    PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet result = pstm.executeQuery();
                    List<PojoEmpleado> lst = new ArrayList<>();
                    while (result.next()) {
                        PojoEmpleado p = new PojoEmpleado();
                        p.setId(result.getInt("id"));
                        p.setNombre(result.getString("nombre"));
                        p.setDireccion(result.getString("direccion"));
                        p.setTelefono(result.getString("telefono"));
                        lst.add(p);
                    }
                    return lst;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }

        };

        //List<PojoEmpleado> empleados = (List<PojoEmpleado>) (PojoEmpleado) con.select(select);
        List<PojoEmpleado> empleados = con.select(select);
        return empleados;
    }
}
