/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.taskactivity.persistence.interfaces;

import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.taskactivity.entity.TaskActivity;

public interface ITaskActivityDAO {
	public TaskActivity insert(TaskActivity taskActivity) throws DAOException;

	public TaskActivity update(TaskActivity taskActivity) throws DAOException;

	public void delete(TaskActivity taskActivity) throws DAOException;
	
	public List<TaskActivity> findAll() throws DAOException;
	
	public TaskActivity findById(int id) throws DAOException;
}