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
        Conexion con = Conexion.getInstance();
        TransaccionDB tra = new TransaccionDB<PojoEmpleado>(empleado) {
            @Override
            public boolean execute(Connection con) {
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session session = sf.getCurrentSession();
                Transaction transaction = session.beginTransaction();
                session.save(empleado);
                transaction.commit();
                System.out.println("Se guardo con el id " + empleado.getId());
                return true;
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

                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session session = sf.getCurrentSession();
                Transaction transaction = session.beginTransaction();
                session.delete(id);
                transaction.commit();
                return true;
            }
        };
        return con.execute(tra);
    }

    @Override
    public boolean modificar(PojoEmpleado empleado) {
        Conexion con = databaseConnection;
        TransaccionDB tra = new TransaccionDB<PojoEmpleado>(empleado) {
            @Override
            public boolean execute(Connection con) {
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session session = sf.getCurrentSession();
                Transaction transaction = session.beginTransaction();
                session.update(empleado);
                transaction.commit();
                return true;

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
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session session = sf.getCurrentSession();
                Transaction transaction = session.beginTransaction();
                PojoEmpleado result = session.get(PojoEmpleado.class, id);
                transaction.commit();
                List<PojoEmpleado> lst = new ArrayList<>();
                lst.add(result);
                return lst;
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
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session session = sf.getCurrentSession();
                Transaction transaction = session.beginTransaction();;
                Query query = session.createQuery("FROM PojoEmpleado");
                List<PojoEmpleado> lst = null;
                lst = query.list();
                transaction.commit();
                return lst;
            }

        };

        //List<PojoEmpleado> empleados = (List<PojoEmpleado>) (PojoEmpleado) con.select(select);
        List<PojoEmpleado> empleados = con.select(select);
        return empleados;
    }
}
