package com.itgura.dms_mediator.util;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;

import java.util.Map;

public class DummyEntityManagerFactory implements EntityManagerFactory {

    @Override
    public EntityManager createEntityManager() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return null;
    }


    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    // Implement all abstract methods with minimal or no functionality
    // and throw UnsupportedOperationException for methods that are called to make debugging easier.



    @Override
    public boolean isOpen() {
        // Return false or true based on your dummy implementation needs
        return false;
    }

    @Override
    public void close() {
        // Since it's a dummy, no action is required here
    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public Cache getCache() {
        return null;
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return null;
    }

    @Override
    public void addNamedQuery(String name, Query query) {

    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return null;
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {

    }


}
