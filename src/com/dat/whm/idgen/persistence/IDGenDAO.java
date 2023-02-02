package com.dat.whm.idgen.persistence;

import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.idgen.IDGen;
import com.dat.whm.idgen.persistence.interfaces.IDGenDAOInf;
import com.dat.whm.persistence.BasicDAO;

@Repository("IDGenDAO")
public class IDGenDAO extends BasicDAO implements IDGenDAOInf {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getNextId(String generateItem) throws DAOException {
		IDGen idGen = null;
		try {
			logger.debug("getNextId() method has been started.");
			Query selectQuery = em.createNamedQuery("IDGen.findByName");
			selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			selectQuery.setHint("javax.persistence.lock.timeout", 30000);
			selectQuery.setParameter("generateItem", generateItem);
			idGen = (IDGen) selectQuery.getSingleResult();
			idGen.setMaxValue(idGen.getMaxValue() + 1);
			idGen = em.merge(idGen);
			logger.debug("getNextId() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("getNextId() method has been failed.", pe);
			throw translate("Failed to update ID Generation for " + generateItem, pe);
		}
		return idGen;
	}
}
