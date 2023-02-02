/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-08
 * @Version 1.0
 * @Purpose DAO class for contact person.
 *
 ***************************************************************************************/
package com.dat.whm.contactperson.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.contactperson.entity.ContactPerson;
import com.dat.whm.contactperson.persistence.interfaces.IContactPersonDAO;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;

@Repository("ContactPersonDAO")
public class ContactPersonDAO extends BasicDAO  implements IContactPersonDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public ContactPerson insert(ContactPerson contanctPerson) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(contanctPerson);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert ContactPerson(Id = "+ contanctPerson.getId() + ")", pe);
		}
		return contanctPerson;
	}

}
