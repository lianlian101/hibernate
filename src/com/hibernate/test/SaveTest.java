package com.hibernate.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import com.hibernate.entity.Address;
import com.hibernate.entity.Role;
import com.hibernate.entity.User;
import com.hibernate.util.SessionFactoryUtil;

public class SaveTest {

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
    
    /**
     * 一对多添加
     */
    @Test
    public void oneToManyOfSave(){
        before();
        User user = new User();
        user.setUsername("张三");
        
        Address address = new Address();
        address.setAddressName("北京");
        Address address2 = new Address();
        address2.setAddressName("天津");
        
        HashSet<Address> set = new HashSet<Address>();
        set.add(address);
        set.add(address2);
        user.setAddresses(set);
        
        address.setUser(user);
        address2.setUser(user);
        
        session.save(user);
        session.save(address);
        session.save(address2);
        
        after();
    }
    
    /**
     * 多对多添加
     */
    @Test
    public void manyToManyOfSave(){
        before();
        
        User user = new User();
        user.setUsername("小明");
        User user2 = new User();
        user2.setUsername("小兰");
        
        Role role = new Role();
        role.setRoleName("唱歌");
        Role role2 = new Role();
        role2.setRoleName("跳舞");
        
//        Set<User> userSet = new HashSet<User>();
        Set<Role> roleSet = new HashSet<Role>();
        
//        userSet.add(user);
//        userSet.add(user2);
        roleSet.add(role);
        roleSet.add(role2);
        
        user.setRoles(roleSet);
        user2.setRoles(roleSet);
        
//        role.setUsers(userSet);
//        role2.setUsers(userSet);
        
        session.save(user);
        session.save(user2);
//        session.save(role);
//        session.save(role2);
        
        after();
    }
    
    /**
     * 多对多添加
     */
    @Test
    public void manyToManyOfSave2(){
        before();
        
        User user = new User();
        user.setUsername("小明");
        User user2 = new User();
        user2.setUsername("小兰");
        
        Role role = new Role();
        role.setRoleName("唱歌");
        Role role2 = new Role();
        role2.setRoleName("跳舞");
        
        Set<User> userSet = new HashSet<User>();
//        Set<Role> roleSet = new HashSet<Role>();
        
        userSet.add(user);
        userSet.add(user2);
//        roleSet.add(role);
//        roleSet.add(role2);
        
//        user.setRoles(roleSet);
//        user2.setRoles(roleSet);
        
        role.setUsers(userSet);
        role2.setUsers(userSet);
        
//        session.save(user);
//        session.save(user2);
        session.save(role);
        session.save(role2);
        
        after();
    }
    
}
