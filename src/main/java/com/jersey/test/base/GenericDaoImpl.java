package com.jersey.test.base;


import com.jersey.test.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class GenericDaoImpl implements GenericDao {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Transaction transaction = null;
    private Session session = null;


    @Override
    public <T> T addEntity(T entity) throws Exception {
        try {

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getEntity(String query, List<Object> values) throws Exception {
        T entity = null;
        try {
            session = sessionFactory.openSession();
            Query sqlQuery = session.createQuery(query);

            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    sqlQuery.setParameter(i, values.get(i));
                }
            }
            entity = (T) sqlQuery.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getEntityNative(String query) throws Exception {
        T entity = null;
        try {
            session = sessionFactory.openSession();
            Query sqlQuery = session.createSQLQuery(query);
            entity = (T) sqlQuery.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getEntities(String query, List<Object> values)
            throws Exception {
        List<T> entities = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            Query sqlQuery = session.createQuery(query);

            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    sqlQuery.setParameter(i, values.get(i));
                }
            }
            entities = sqlQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
            //tx.rollback();
        }
        return entities;
    }

    @Override
    public <T> boolean deleteEntity(T delete) throws Exception {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(delete);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T> int updateEntity(String query, List<Object> values) throws Exception {
        int result = 0;
        try {
            session = sessionFactory.openSession();
            Query sqlQuery = session.createQuery(query);

            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    sqlQuery.setParameter(i, values.get(i));
                }
            }
            result = sqlQuery.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public <T> T updateEntity(T entity) throws Exception {

        try {

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
		/*	if (entity instanceof MasterDTO)
				((MasterDTO)entity).setUpdatedAt(new Date());
*/
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T addUpdateEntity(T entity) throws Exception {
        session = sessionFactory.openSession();
        session.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public <T> T addEntity(String query, List<Object> values) throws Exception {
        T entity = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query sqlQuery = session.createQuery(query);

            if (values != null && values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    sqlQuery.setParameter(i, values.get(i));
                }
            }
            sqlQuery.executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
