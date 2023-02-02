/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.taskactivity.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.taskactivity.entity.TaskActivity;
import com.dat.whm.taskactivity.persistence.interfaces.ITaskActivityDAO;
import com.dat.whm.taskactivity.service.interfaces.ITaskActivityService;

@Service("TaskActivityService")
public class TaskActivityService implements ITaskActivityService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "TaskActivityDAO")
	private ITaskActivityDAO taskActivityDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public TaskActivity addNewTaskActivity(TaskActivity taskActivity) throws SystemException {
		try {
			logger.debug("addTaskActivity() method has been started.");
			taskActivity = taskActivityDAO.insert(taskActivity);
			logger.debug("addTaskActivity() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewTaskActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a TaskActivity", e);
		}
		return taskActivity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TaskActivity updateTaskActivity(TaskActivity taskActivity) throws SystemException {
		try {
			logger.debug("updateTaskActivity() method has been started.");
			taskActivity = taskActivityDAO.update(taskActivity);
			logger.debug("updateTaskActivity() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateTaskActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update TaskActivity", e);
		}
		return taskActivity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTaskActivity(TaskActivity taskActivity) throws SystemException {
		try {
			logger.debug("deleteTaskActivity() method has been started.");
			taskActivityDAO.delete(taskActivity);
			logger.debug("deleteTaskActivity() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteTaskActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete TaskActivity", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TaskActivity> findAllTaskActivity() throws SystemException {
		logger.debug("findAllTaskActivity() method has been started.");
		List<TaskActivity> result = taskActivityDAO.findAll();
		logger.debug("findAllTaskActivity() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TaskActivity findTaskActivity(int id) throws SystemException {
		logger.debug("findTaskActivity() method has been started.");
		TaskActivity taskActivity = taskActivityDAO.findById(id);
		logger.debug("findTaskActivity() method has been finished.");
		return taskActivity;
	}
}