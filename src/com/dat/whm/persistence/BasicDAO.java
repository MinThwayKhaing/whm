package com.dat.whm.persistence;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dat.whm.exception.DAOException;


/**
 * @author Zaw Than Oo
 */
public class BasicDAO {
    
    @PersistenceContext
    protected EntityManager em;
    
    @Resource(name = "SQLErrorCodeTranslator")
    private SQLErrorCodeTranslator sqlErrorCodeTranslator;

    
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public RuntimeException translate(String message, RuntimeException e) {
        DAOException dae = null;
        Throwable throwable = e;
        while (throwable != null && !(throwable instanceof SQLException)) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof SQLException) {
            dae = sqlErrorCodeTranslator.translate(message, (SQLException) throwable);
        }
        if (dae != null) {
            return dae;
        } else {
            return e;
        }
    }
}
