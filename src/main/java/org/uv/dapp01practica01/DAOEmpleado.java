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
        PojoEmpleado empleado = new PojoEmpleado();
        TransaccionDB tra = new TransaccionDB<PojoEmpleado>(empleado) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "SELECT * FROM empleadotemporal WHERE id = ?";
                    PreparedStatement pstm = con.prepareStatement(sql);

                    pstm.setInt(1, id);
                    ResultSet result;
                    result = pstm.executeQuery();

                    if (result.next()) {
                        empleado.setId(result.getInt("id"));
                        empleado.setNombre(result.getString("nombre"));
                        empleado.setDireccion(result.getString("direccion"));
                        empleado.setTelefono(result.getString("telefono"));
                    }

                    pstm.execute();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        con.execute(tra);
        return empleado;

    }

    @Override
    public List<PojoEmpleado> buscarAll() {
        List<PojoEmpleado> empleados = new ArrayList<>();
        TransaccionDB tra = new TransaccionDB<List<PojoEmpleado>>(empleados) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql = "SELECT * FROM empleadotemporal";
                    Statement st = con.createStatement();
                    ResultSet result = st.executeQuery(sql);
                    while (result.next()) {
                        PojoEmpleado empleado = new PojoEmpleado();
                        empleado.setId(result.getInt("id"));
                        empleado.setNombre(result.getString("nombre"));
                        empleado.setDireccion(result.getString("direccion"));
                        empleado.setTelefono(result.getString("telefono"));
                        empleados.add(empleado);
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        databaseConnection.execute(tra);
        return empleados;
    }
}
