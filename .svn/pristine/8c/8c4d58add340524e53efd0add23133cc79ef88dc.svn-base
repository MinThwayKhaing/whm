/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.task.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.task.entity.Task;
import com.dat.whm.task.persistence.interfaces.ITaskDAO;

@Repository("TaskDAO")
public class TaskDAO extends BasicDAO implements ITaskDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public Task insert(Task task) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(task);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Task(Description = "
					+ task.getDescription() + ")", pe);
		}
		return task;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Task update(Task task) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			task = em.merge(task);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update Task(Description = "
					+ task.getDescription() + ")", pe);
		}
		return task;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Task task) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			task = em.merge(task);
			em.remove(task);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Task(Description = "
					+ task.getDescription() + ")", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Task> findAll() throws DAOException {
		List<Task> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("Task.findAll");
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Task.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Task findById(int id) throws DAOException {
		Task result = null;
		try {
			logger.debug("findById() method has been started.");
			Query q = em.createNamedQuery("Task.findById");
			q.setParameter("id", id);
			result = (Task) q.getSingleResult();
			em.flush();
			logger.debug("findById() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findById() method has been failed.", pe);
			throw translate("Failed to find Task(Id = " + id + ")",
					pe);
		}
		return result;
	}
}