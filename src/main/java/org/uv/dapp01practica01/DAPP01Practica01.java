/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.uv.dapp01practica01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danielaa
 */
public class DAPP01Practica01 {

    public static void main(String[] args) {
        try {
            connection();
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al ejecutar el programa", ex);

        }
    }

    public static void connection() {
        Connection con = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/ejemplo";
            String usr = "postgres";
            String pwd = "boli";
            con = DriverManager.getConnection(url, usr, pwd);
            menu(con);

            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, "Se conecto");
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void menu(Connection con) {
        Scanner scan = new Scanner(System.in);

        int n = 0;
        boolean step;
        do {
            try {
                System.out.println("Bienvenido, seleccione una opcion porfavor:");
                System.out.println("1. Crear empleado \n2. Ver empleados \n3. Actualizar empleado \n4. Eliminar Empleado \n5. Salir");
                n = scan.nextInt();
                switch (n) {
                    case 1:
                        create(con);
                        break;
                    case 2:
                        read(con);
                        break;
                    case 3:
                        update(con);
                        break;
                    case 4:
                        delete(con);
                        break;
                    default:
                        break;
                }
                step = false;
            } catch (Exception e) {
                step = true;
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, e);
            }
        } while ((n < 1 || n < 5) || step);
    }

    public static Empleado employe() {

        Scanner scan = new Scanner(System.in);
        Empleado empleadotemp = new Empleado();
        System.out.println("Ingresa los datos empleado: \n");
        System.out.println("Ingresa el nombre: ");
        empleadotemp.setNombre(scan.next());
        System.out.println("Ingresa la direccion: ");
        empleadotemp.setDireccion(scan.next());
        System.out.println("Ingresa el telefono: ");
        empleadotemp.setTelefono(scan.nextInt());
        return empleadotemp;

    }

    public static Empleado getEmploye(Connection con, int id) {
        Statement st = null;
        ResultSet result;
        Empleado empleadotemp = new Empleado();
        try {
            st = con.createStatement();
            String sql = "SELECT * FROM empleadotemporal WHERE id=" + id;

            result = st.executeQuery(sql);
            while (result.next()) {
                empleadotemp.setNombre(result.getString("nombre"));
                empleadotemp.setDireccion(result.getString("direccion"));
                empleadotemp.setTelefono(result.getInt("telefono"));
            }
            System.out.println("Nombre: " + empleadotemp.getNombre() + ", Dirección: " + empleadotemp.getDireccion() + ", Teléfono: " + empleadotemp.getTelefono());
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return empleadotemp;
    }

    public static void create(Connection con) {
        Statement st = null;
        try {

            Empleado empleadotemp;
            empleadotemp = employe();

            st = con.createStatement();
            String sql = "INSERT INTO empleadotemporal (nombre, direccion, telefono) VALUES ('" + empleadotemp.getNombre() + "', '" + empleadotemp.getDireccion() + "', '" + empleadotemp.getTelefono() + "')";
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void read(Connection con) {

        Statement st = null;
        ResultSet result;
        try {
            System.out.println("Los empleados son: \n");
            st = con.createStatement();
            String sql = "SELECT * FROM empleadotemporal";
            result = st.executeQuery(sql);
            int id = 0;

            Empleado empleadotemp = new Empleado();
            while (result.next()) {

                empleadotemp.setNombre(result.getString("nombre"));
                empleadotemp.setDireccion(result.getString("direccion"));
                empleadotemp.setTelefono(result.getInt("telefono"));
                id = id + 1;
                String formattedId = String.format("%-5s", id);
                String formattedNombre = String.format("%-12s", empleadotemp.getNombre());
                String formattedDireccion = String.format("%-15s", empleadotemp.getDireccion());
                String formattedTelefono = String.format("%-10d", empleadotemp.getTelefono());
                System.out.println("| " + formattedId + "| " + formattedNombre + " | " + formattedDireccion + " | " + formattedTelefono + " |");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void update(Connection con) {

        Scanner scan = new Scanner(System.in);
        Statement st = null;
        try {
            read(con);
            System.out.print("\n Ingresa el ID del empleado a actualizar:");
            int id = scan.nextInt();
            getEmploye(con, id);
            Empleado empleadonew = employe();

            st = con.createStatement();
            String sql = "UPDATE empleadotemporal SET nombre = '" + empleadonew.getNombre() + "', direccion = '" + empleadonew.getDireccion() + "', telefono = " + empleadonew.getTelefono() + " WHERE id = " + id + "";

            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void delete(Connection con) {
        Scanner scan = new Scanner(System.in);
        Statement st = null;
        try {
            read(con);
            System.out.print("\n Ingresa el ID del empleado a eliminar:");
            int id = scan.nextInt();
            getEmploye(con, id);
            st = con.createStatement();
            String sql = "Delete FROMT empleadotemporal WHERE id = " + id + "";

            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, ex);
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
