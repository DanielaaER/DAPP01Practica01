/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.uv.dapp01practica01;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Danielaa
 */
public class DAPP01Practica01 {

    public static void main(String[] args) {
        try {

            menu();
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, "Error al ejecutar el programa", ex);

        }
    }

    public static void menu() {
        Scanner scan = new Scanner(System.in);

        int n = 0;
        boolean step;
        do {
            try {
                System.out.println("Bienvenido, seleccione una opcion por favor:");
                System.out.println("1. Empleados \n2. Ventas \n3. Salir");
                n = scan.nextInt();
                switch (n) {
                    case 1:
                        menuEmpleado();
                        break;
                    case 2:
                        menuVenta();
                        break;
                    default:
                        break;
                }
                step = false;
            } catch (Exception e) {
                step = true;
                Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.SEVERE, null, e);
            }
        } while ((n < 1 || n < 3) || step);
    }

    public static void menuEmpleado() {
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
                        createEmploye();
                        break;
                    case 2:
                        readEmploye();
                        break;
                    case 3:
                        updateEmploye();
                        break;
                    case 4:
                        deleteEmploye();
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

    public static void menuVenta() {
        Scanner scan = new Scanner(System.in);

        int n = 0;
        boolean step;
        do {
            try {
                System.out.println("Bienvenido, seleccione una opcion porfavor:");
                System.out.println("1. Crear venta \n2. Ver ventas \n3. Actualizar venta \n4. Eliminar venta \n5. Salir");
                n = scan.nextInt();
                switch (n) {
                    case 1:
                        createVenta();
                        break;
                    case 2:
                        readVenta();
                        break;
                    case 3:
                        updateVenta();
                        break;
                    case 4:
                        deleteVenta();
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

    public static Venta venta() {
        Venta venta = new Venta();
        venta.setCliente("publico general");
        venta.setFecha(new Date(new java.util.Date().getTime()));
        venta.setTotal(100000);
        for (int i = 0; i < 5;
                i++) {
            DetalleVenta det = new DetalleVenta();
            det.setPrecio(1000);
            det.setCantidad(10);
            det.setProducto("Producto " + i);
            det.setVenta(venta);
            venta.getDetalleVenta().add(det);
        }
        return venta;

    }
    
    public static Venta getVenta(int id){
         Venta ventatemp = new Venta();
        DAOVenta daoventa = new DAOVenta();
        try {
            ventatemp = daoventa.buscarById(id);
            System.out.println("Cliente: " + ventatemp.getCliente()+ ", Fecha: " + ventatemp.getFecha()+ ", Total: " + ventatemp.getTotal());
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }
        return ventatemp;
    
    }

    public static PojoEmpleado employe() {
        Scanner scan = new Scanner(System.in);
        PojoEmpleado empleadotemp = new PojoEmpleado();
        System.out.println("Ingresa los datos empleado: \n");
        System.out.println("Ingresa el nombre: ");
        empleadotemp.setNombre(scan.next());
        System.out.println("Ingresa la direccion: ");
        empleadotemp.setDireccion(scan.next());
        System.out.println("Ingresa el telefono: ");
        empleadotemp.setTelefono(scan.next());
        return empleadotemp;

    }

    public static PojoEmpleado getEmploye(int id) {
        PojoEmpleado empleadotemp = new PojoEmpleado();
        DAOEmpleado daoempleado = new DAOEmpleado();
        try {
            empleadotemp = daoempleado.buscarById(id);
            System.out.println("Nombre: " + empleadotemp.getNombre() + ", Dirección: " + empleadotemp.getDireccion() + ", Teléfono: " + empleadotemp.getTelefono());
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }
        return empleadotemp;
    }

    public static void createEmploye() {
        try {
            PojoEmpleado empleadotemp = employe();
            DAOEmpleado daoempleado = new DAOEmpleado();
            daoempleado.guardar(empleadotemp);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }

    }
    
    public static void createVenta() {
        try {
            Venta ventatemp = venta();
            DAOVenta daoVenta = new DAOVenta();
            daoVenta.guardar(ventatemp);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }

    }

    public static void readEmploye() {

        try {
            System.out.println("Los empleados son: \n");

            DAOEmpleado daoempleado = new DAOEmpleado();
            List<PojoEmpleado> empleados = daoempleado.buscarAll();
            for (int i = empleados.size() - 1; i >= 0; i--) {
                PojoEmpleado empleadotemp = empleados.get(i);
                String formattedId = String.format("%-5s", empleadotemp.getId());
                String formattedNombre = String.format("%-12s", empleadotemp.getNombre());
                String formattedDireccion = String.format("%-15s", empleadotemp.getDireccion());
                String formattedTelefono = String.format("%-10s", empleadotemp.getTelefono());
                System.out.println("| " + formattedId + "| " + formattedNombre + " | " + formattedDireccion + " | " + formattedTelefono + " |");
            }
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);

        }
    }
    
    
    public static void readVenta() {

        try {
            System.out.println("Las ventas son son: \n");

            DAOVenta daoventa = new DAOVenta();
            List<Venta> ventas = daoventa.buscarAll();
            for (int i = ventas.size() - 1; i >= 0; i--) {
                Venta ventatemp = ventas.get(i);
                String formattedId = String.format("%-5s", ventatemp.getId());
                String formattedNombre = String.format("%-12s", ventatemp.getCliente());
                String formattedDireccion = String.format("%-15s", ventatemp.getFecha());
                String formattedTelefono = String.format("%-10s", ventatemp.getTotal());
                System.out.println("| " + formattedId + "| " + formattedNombre + " | " + formattedDireccion + " | " + formattedTelefono + " |");
            }
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);

        }
    }

    public static void updateEmploye() {
        Scanner scan = new Scanner(System.in);
        try {
            readEmploye();
            System.out.print("\n Ingresa el ID del empleado a actualizar:");
            int id = scan.nextInt();
            getEmploye(id);
            PojoEmpleado empleadonew = employe();
            DAOEmpleado daoempleado = new DAOEmpleado();
            daoempleado.modificar(empleadonew);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }

    }
    
    public static void updateVenta() {
        Scanner scan = new Scanner(System.in);
        try {
            readVenta();
            System.out.print("\n Ingresa el ID de la venta a actualizar:");
            int id = scan.nextInt();
            getVenta(id);
            Venta ventanew = venta();
            DAOVenta daoventa = new DAOVenta();
            daoventa.modificar(ventanew);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }

    }


    public static void deleteEmploye() {
        Scanner scan = new Scanner(System.in);
        try {
            readEmploye();
            System.out.print("\n Ingresa el ID del empleado a eliminar:");
            int id = scan.nextInt();
            PojoEmpleado empleado = getEmploye(id);
            DAOEmpleado daoempleado = new DAOEmpleado();
            daoempleado.eliminar(empleado);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class
                    .getName()).log(Level.INFO, null, ex);
        }
    }

    public static void deleteVenta() {
        Scanner scan = new Scanner(System.in);
        try {
            readVenta();
            System.out.print("\n Ingresa el ID de la venta a eliminar:");
            int id = scan.nextInt();
            Venta venta = getVenta(id);
            DAOVenta daoventa = new DAOVenta();
            daoventa.eliminar(venta);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class
                    .getName()).log(Level.INFO, null, ex);
        }
    }

}
