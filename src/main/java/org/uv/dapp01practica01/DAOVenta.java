/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Danielaa
 */
public class DAOVenta implements IDAO<Venta, Integer> {
    
    private final Conexion databaseConnection;

    public DAOVenta() {
        this.databaseConnection = Conexion.getInstance();
    }

    @Override
    public boolean guardar(Venta pojo) {
    SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(pojo);
        transaction.commit();
        System.out.println("Se guardo con el id " + pojo.getId());
        return true;    
    
    }

    @Override
    public boolean eliminar(Venta pojo) {
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(pojo);
        transaction.commit();
        return true;
    }

    @Override
    public boolean modificar(Venta pojo) {
    SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Venta existingVenta = session.get(Venta.class, pojo.getId());
        if (existingVenta != null) {
            existingVenta.setCliente(pojo.getCliente());
            existingVenta.setFecha(pojo.getFecha());
            existingVenta.setTotal(pojo.getTotal());
            existingVenta.setDetalleVenta(pojo.getDetalleVenta());
            session.update(existingVenta);
            transaction.commit();
            return true;
        } else {
            // Handle case where the entity doesn't exist
            transaction.rollback();
            return false;
        }
    }


    @Override
    public Venta buscarById(Integer id) {
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Venta vent = session.get(Venta.class, id);
        transaction.commit();
        return vent;
    }

    @Override
    public List<Venta> buscarAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Venta> ventas = session.createQuery("FROM venta").list();
        transaction.commit();
        return ventas;
    
    }
}
