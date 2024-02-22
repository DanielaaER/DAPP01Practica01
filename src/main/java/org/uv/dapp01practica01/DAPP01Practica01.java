/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.uv.dapp01practica01;

import java.util.List;
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
                System.out.println("Bienvenido, seleccione una opcion porfavor:");
                System.out.println("1. Crear empleado \n2. Ver empleados \n3. Actualizar empleado \n4. Eliminar Empleado \n5. Salir");
                n = scan.nextInt();
                switch (n) {
                    case 1:
                        create();
                        break;
                    case 2:
                        read();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        delete();
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
            empleadotemp = daoempleado.buscarId(id);
            System.out.println("Nombre: " + empleadotemp.getNombre() + ", Dirección: " + empleadotemp.getDireccion() + ", Teléfono: " + empleadotemp.getTelefono());
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }
        return empleadotemp;
    }

    public static void create() {
        try {
            PojoEmpleado empleadotemp = employe();
            DAOEmpleado daoempleado = new DAOEmpleado();
            daoempleado.guardar(empleadotemp);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }

    }

    public static void read() {

        try {
            System.out.println("Los empleados son: \n");

            DAOEmpleado daoempleado = new DAOEmpleado();
            List<PojoEmpleado> empleados = daoempleado.buscarAll();
            for (int i=empleados.size()-1; i >= 0; i--) {
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

    public static void update() {
        Scanner scan = new Scanner(System.in);
        try {
            read();
            System.out.print("\n Ingresa el ID del empleado a actualizar:");
            int id = scan.nextInt();
            getEmploye(id);
            PojoEmpleado empleadonew = employe();
            DAOEmpleado daoempleado = new DAOEmpleado();
            daoempleado.modificar(empleadonew, id);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class.getName()).log(Level.INFO, null, ex);
        }

    }

    public static void delete() {
        Scanner scan = new Scanner(System.in);
        try {
            read();
            System.out.print("\n Ingresa el ID del empleado a eliminar:");
            int id = scan.nextInt();
            getEmploye(id);
            DAOEmpleado daoempleado = new DAOEmpleado();
            daoempleado.eliminar(id);
        } catch (Exception ex) {
            Logger.getLogger(DAPP01Practica01.class
                    .getName()).log(Level.INFO, null, ex);
        }
    }

}
