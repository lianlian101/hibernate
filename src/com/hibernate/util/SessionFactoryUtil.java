package com.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private static Configuration cfg;

    private static SessionFactory sessionFactory;

    static {
        // 加载配置文件
        cfg = new Configuration();
        cfg.configure();
        // 获取SessionFactory
        sessionFactory = cfg.buildSessionFactory();
    }

    /**
     * 获取SessionFactory
     * 
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
