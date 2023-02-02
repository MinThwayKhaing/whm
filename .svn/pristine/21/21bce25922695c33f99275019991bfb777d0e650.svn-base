package com.dat.whm.persistence;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dat.whm.exception.DAOException;


/**
 * @author Zaw Than Oo
 */
@Service(value = "SQLErrorCodeTranslator")
public class SQLErrorCodeTranslator {
	
	@Resource(name = "SQL_ERROR_CODE")
    private Properties properties;
    
    public DAOException translate(String message, SQLException sqlex) {
        String errorCode = properties.getProperty(sqlex.getErrorCode() + "");
        DAOException daoException = new DAOException(errorCode, message, sqlex);
        return daoException;
    }
}
