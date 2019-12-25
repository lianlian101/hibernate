package com.hibernate.test;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import com.hibernate.entity.Address;
import com.hibernate.entity.User;
import com.hibernate.util.SessionFactoryUtil;

public class SelectTest {

    private SessionFactory sessionFactory;
    
    private Session session;
    
    private Transaction transaction;
    
    private void before(){
        // 获取SessionFactory
        sessionFactory = SessionFactoryUtil.getSessionFactory();
        // 获取session
        session = sessionFactory.openSession();
        // 开启事物
        transaction = session.beginTransaction();
    }
    
    private void after(){
        // 提交事物
        transaction.commit();
        // 关闭链接
        session.close();
        sessionFactory.close();
    }
    
    // =========================OID(根据主键查询)===================
    
    @Test
    public void oneToMany_oid(){
        try {
            before();
            
            /*
             * 单表查询
             */
            User user = session.get(User.class, 1);
            System.out.println(user.getUsername());
            
            /*
             * 延迟查询（用到才查询）
             */
            Set<Address> addresses = user.getAddresses();
            for (Address address : addresses) {
                System.out.println(address);
            }
            
            after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ==========================导航查询语句===============================
    
    @Test
    public void oneToMany_get(){
        try {
            before();
            
            /*
             * 单表查询
             */
            User user = session.get(User.class, 1);
            /*
             * 当获取包含的属性时，才去执行关联查询 [导航查询]
             */
            user.getAddresses().size();
            
            after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ==========================HQL(Hibernate Query Language)==============================
    
    /**
     * 一对多查询 hql
     */
    @Test
    public void oneToMany(){
        try {
            before();
            Query query = session.createQuery("from User");
            /*
             * 此时只执行单表查询 
             */
            List<User> list = query.list();
            for (User user : list) {
                System.out.println(user.getUsername());
                /*
                 * 查询包含的set集合，获取的时候才去执行关联查询sql，不去获取set属性，不去执行关联查询sql
                 */
                System.out.println(user.getAddresses().size());
            }
            after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 多对一查询 hql
     */
    @Test
    public void manyToOne(){
        try {
            before();
            Query query = session.createQuery("from Address");
            /*
             * 测试只执行单表查询
             */
            List<Address> list = query.list();
            for (Address address : list) {
                System.out.println("addressName --> " + address.getAddressName());
                /*
                 * 当需要获取关联的对象时，才执行关联查询，当关联的外键一样时，sql省略（这种情况只适合查询多对一的情况，查询多的一方时，去获取一的情况），直接取结果
                 */
                System.out.println(address.getUser().getUsername());
            }
            after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 多对多查询 hql
     */
    @Test
    public void manyToMany(){
        try {
            before();
            
            Query query = session.createQuery("from User");
            /*
             * 此时只是单表查询 
             */
            List<User> list = query.list();
            for (User user : list) {
                System.out.println(user.getUsername());
                /*
                 * 需要获取关联对象时，才执行关联查询
                 */
                System.out.println(user.getAddresses() + "--" + user.getRoles());
            }
            
            after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ============================普通SQL=========================
    
    /**
     * 一对多查询 sql
     */
    @Test
    public void oneToMany_sql(){
        try {
            before();
            
            SQLQuery query = session.createSQLQuery("select id, username from user where id = ? and username = ?").addEntity(User.class);
            query.setInteger(0, 1);
            query.setString(1, "张三");
            List<User> list = query.list();
            for (User user : list) {
                System.out.println(user);
            }

            after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
