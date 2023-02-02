/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-08
 * @Version 1.0
 * @Purpose Service class for contact person.
 *
 ***************************************************************************************/
package com.dat.whm.contactperson.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dat.whm.contactperson.entity.ContactPerson;
import com.dat.whm.contactperson.persistence.interfaces.IContactPersonDAO;
import com.dat.whm.contactperson.service.interfaces.IContactPersonService;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;

@Service("ContactPersonService")
public class ContactPersonService implements IContactPersonService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "ContactPersonDAO")
	private IContactPersonDAO contactPersonDAO;

	@Override
	public ContactPerson addNewContactPerson(ContactPerson contactPerson)throws SystemException {
		try {
			logger.debug("addNewContactPerson() method has been started.");
			contactPerson = contactPersonDAO.insert(contactPerson);
			logger.debug("addNewContactPerson() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewContactPerson() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Approval", e);
		}
		return contactPerson;
	}

}
