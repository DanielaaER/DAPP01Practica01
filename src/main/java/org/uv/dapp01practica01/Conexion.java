/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danielaa
 */
public class Conexion {

    private static Conexion cx = null;
    private Connection con = null;

    private Conexion() {
        String ip = "localhost";
        String port = "5432";
        String db = "ejemplo";
        String url = "jdbc:postgresql://" + ip + ":" + port + "/" + db + "";
        String usr = "postgres";
        String pwd = "boli";
        try {
            con = DriverManager.getConnection(url, usr, pwd);
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Conexion getInstance() {
        if (cx == null) {
            cx = new Conexion();

        }
        return cx;
    }

    Connection getConnection() {
        return con;
    }

    public void cerrarConexion() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
