package com.dat.whm.idgen.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
//import org.apache.naming.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.idgen.IDGen;
import com.dat.whm.idgen.exception.CustomIDGeneratorException;
import com.dat.whm.idgen.persistence.interfaces.IDGenDAOInf;
import com.dat.whm.idgen.service.interfaces.ICustomIDGenerator;

@Service("CustomIDGenerator")
public class CustomIDGenerator implements ICustomIDGenerator {
	private Logger logger = Logger.getLogger(this.getClass());
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");

	@Resource(name = "IDGenDAO")
	private IDGenDAOInf idGenDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String getNextId(String genName) throws CustomIDGeneratorException {
		String id = null;
		try {
			logger.debug("getNextId() method has been started.");
			id = formatId(idGenDAO.getNextId(genName));
			logger.debug("getNextId() method has been successfully finished. Generated ID : " + id);
		} catch (DAOException e) {
			logger.error("getNextId() method has been failed.");
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	private String formatId(IDGen idGen) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		int maxLength = idGen.getLength();
		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		id = prefix + id + getDateString() ;
		return id;
	}

	private String getDateString() {
		return simpleDateFormat.format(new Date());
	}

	public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        ICustomIDGenerator customIDGenerator = (ICustomIDGenerator)context.getBean("CustomIDGenerator");
		String id = customIDGenerator.getNextId("APP_CODE");
		System.out.println(id);
	}
}
