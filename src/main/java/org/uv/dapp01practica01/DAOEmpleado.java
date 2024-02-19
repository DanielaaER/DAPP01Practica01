/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danielaa
 */
public class DAOEmpleado {

    public boolean guardar(Empleado empleado) {
        Conexion con = Conexion.getInstance();
        PreparedStatement pstm = null;
        try {
            String sql = "INSERT INTO empleadotemporal (nombre, direccion, telefono) VALUES (?,?,?)";
            pstm = con.getConnection().prepareStatement(sql);
            pstm.setString(1, empleado.getNombre());
            pstm.setString(2, empleado.getDireccion());
            pstm.setString(3, empleado.getTelefono());
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (pstm != null) {
                        pstm.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }

    public boolean eliminar(int id) {
        Conexion con = Conexion.getInstance();
        PreparedStatement pstm = null;
        try {
            String sql = "Delete FROM empleadotemporal WHERE id = ?";
            pstm = con.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public boolean modificar(Empleado empleado, int id) {
        Conexion con = Conexion.getInstance();
        PreparedStatement pstm = null;
        try {
            String sql = "UPDATE empleadotemporal SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
            pstm = con.getConnection().prepareStatement(sql);
            pstm.setString(1, empleado.getNombre());
            pstm.setString(2, empleado.getDireccion());
            pstm.setString(3, empleado.getTelefono());
            pstm.setInt(4, id);
            pstm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public Empleado buscarId(int id) {
        Conexion con = Conexion.getInstance();
        PreparedStatement pstm = null;
        try {
            String sql = "SELECT * FROM empleadotemporal WHERE id=?";
            pstm = con.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet result;
            result = pstm.executeQuery();
            if (result.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(result.getInt("id"));
                empleado.setNombre(result.getString("nombre"));
                empleado.setDireccion(result.getString("direccion"));
                empleado.setTelefono(result.getString("telefono"));
                return empleado;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Empleado> buscarAll() {
        ResultSet result;
        Conexion con = Conexion.getInstance();
        Statement st = null;
        try {
            st = con.getConnection().createStatement();
            String sql = "SELECT * FROM empleadotemporal";
            result = st.executeQuery(sql);
            List<Empleado> empleados = new ArrayList<>();
            while (result.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(result.getInt("id"));
                empleado.setNombre(result.getString("nombre"));
                empleado.setDireccion(result.getString("direccion"));
                empleado.setTelefono(result.getString("telefono"));
                empleados.add(empleado);
            }
            return empleados;
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
