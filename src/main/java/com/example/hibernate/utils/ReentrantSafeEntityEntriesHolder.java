package com.example.hibernate.utils;

import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Map;

@Component
public class ReentrantSafeEntityEntriesHolder {

    @Autowired
    private EntityManager em;

    @Transactional
    public Map.Entry<Object, EntityEntry>[] getEntries() {
        return em.unwrap(SessionImplementor.class)
                .getPersistenceContext()
                .reentrantSafeEntityEntries();
    }
}
