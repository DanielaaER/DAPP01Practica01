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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DAOEmpleado implements IDAO<PojoEmpleado, Integer> {

    private final Conexion databaseConnection;

    public DAOEmpleado() {
        this.databaseConnection = Conexion.getInstance();
    }

    @Override
    public boolean guardar(PojoEmpleado empleado) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(empleado);
        transaction.commit();
        System.out.println("Se guardo con el id " + empleado.getId());
        return true;
    }

    @Override
    public boolean eliminar(PojoEmpleado empleado) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(empleado);
        transaction.commit();
        return true;
    }

    @Override
    public boolean modificar(PojoEmpleado empleado) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        PojoEmpleado existingEmpleado = session.get(PojoEmpleado.class, empleado.getId());
        if (existingEmpleado != null) {
            existingEmpleado.setDireccion(empleado.getDireccion());
            existingEmpleado.setNombre(empleado.getNombre());
            existingEmpleado.setTelefono(empleado.getTelefono());
            session.update(existingEmpleado);
            transaction.commit();
            return true;
        } else {
            // Handle case where the entity doesn't exist
            transaction.rollback();
            return false;
        }
    }

    @Override
    public PojoEmpleado buscarById(Integer id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        PojoEmpleado empleado = session.get(PojoEmpleado.class, id);
        transaction.commit();
        return empleado;

    }

    @Override
    public List<PojoEmpleado> buscarAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<PojoEmpleado> empleados = session.createQuery("FROM empleadotemporal").list();
        transaction.commit();
        return empleados;
    }
}
