/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.task.entity.Task;
import com.dat.whm.task.persistence.interfaces.ITaskDAO;
import com.dat.whm.task.service.interfaces.ITaskService;

@Service("TaskService")
public class TaskService implements ITaskService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "TaskDAO")
	private ITaskDAO taskDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Task addNewTask(Task task) throws SystemException {
		try {
			logger.debug("addTask() method has been started.");
			task = taskDAO.insert(task);
			logger.debug("addTask() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewTask() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Task", e);
		}
		return task;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Task updateTask(Task task) throws SystemException {
		try {
			logger.debug("updateTask() method has been started.");
			task = taskDAO.update(task);
			logger.debug("updateTask() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateTask() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update Task", e);
		}
		return task;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTask(Task task) throws SystemException {
		try {
			logger.debug("deleteTask() method has been started.");
			taskDAO.delete(task);
			logger.debug("deleteTask() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteTask() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete Task", e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Task> findAllTask() throws SystemException {
		logger.debug("findAllTask() method has been started.");
		List<Task> result = taskDAO.findAll();
		logger.debug("findAllTask() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Task findTask(int id) throws SystemException {
		logger.debug("findTask() method has been started.");
		Task task = taskDAO.findById(id);
		logger.debug("findTask() method has been finished.");
		return task;
	}
}