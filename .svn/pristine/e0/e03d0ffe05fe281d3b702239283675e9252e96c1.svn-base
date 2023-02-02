/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.taskactivity.persistence;

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
import com.dat.whm.taskactivity.entity.TaskActivity;
import com.dat.whm.taskactivity.persistence.interfaces.ITaskActivityDAO;

@Repository("TaskActivityDAO")
public class TaskActivityDAO extends BasicDAO implements ITaskActivityDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public TaskActivity insert(TaskActivity taskActivity) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(taskActivity);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert TaskActivity(Description = "
					+ taskActivity.getDescription() + ")", pe);
		}
		return taskActivity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TaskActivity update(TaskActivity taskActivity) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			taskActivity = em.merge(taskActivity);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update TaskActivity(Description = "
					+ taskActivity.getDescription() + ")", pe);
		}
		return taskActivity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(TaskActivity taskActivity) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			taskActivity = em.merge(taskActivity);
			em.remove(taskActivity);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete TaskActivity(Description = "
					+ taskActivity.getDescription() + ")", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TaskActivity> findAll() throws DAOException {
		List<TaskActivity> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("TaskActivity.findAll", TaskActivity.class);
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of TaskActivity.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TaskActivity findById(int id) throws DAOException {
		TaskActivity result = null;
		try {
			logger.debug("findById() method has been started.");
			Query q = em.createNamedQuery("TaskActivity.findById");
			q.setParameter("id", id);
			result = (TaskActivity) q.getSingleResult();
			em.flush();
			logger.debug("findById() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findById() method has been failed.", pe);
			throw translate("Failed to find TaskActivity(Id = " + id + ")",
					pe);
		}
		return result;
	}
}