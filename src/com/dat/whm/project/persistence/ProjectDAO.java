/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.project.persistence;

import java.util.Date;
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
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.persistence.interfaces.IProjectDAO;

@Repository("ProjectDAO")
public class ProjectDAO extends BasicDAO implements IProjectDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public Project insert(Project project) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(project);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Project(ProjectName = "
					+ project.getProjectName() + ")", pe);
		}
		return project;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Project update(Project project) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			project = em.merge(project);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update Project(ProjectName = "
					+ project.getProjectName() + ")", pe);
		}
		return project;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Project project) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			project = em.merge(project);
			em.remove(project);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Project(ProjectName = "
					+ project.getProjectName() + ")", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Project> findAll() throws DAOException {
		List<Project> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("Project.findAll");
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Project.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Project findById(String id) throws DAOException {
		Project result = null;
		try {
			logger.debug("findById() method has been started.");
			Query q = em.createNamedQuery("Project.findById");
			q.setParameter("id", id);
			result = (Project) q.getSingleResult();
			em.flush();
			logger.debug("findById() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findById() method has been failed.", pe);
			throw translate("Failed to find Project(Id = " + id + ")",
					pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Project findByName(String name) throws DAOException {
		Project result = null;
		try {
			logger.debug("findByName() method has been started.");
			Query q = em.createNamedQuery("Project.findByName");
			q.setParameter("name", name);
			result = (Project) q.getSingleResult();
			em.flush();
			logger.debug("findByName() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findByName() method has been failed.", pe);
			throw translate("Failed to find Project(Name = " + name + ")",
					pe);
		}
		return result;
	}

	/**
	 * Revised By   : Ye Thu Naing
	 * Revised Date : 2017/09/19
	 * Explanation  : Get start date form project table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findStartDateByProjectId(String projectId) throws DAOException {
		Date result;
		try {
			logger.debug("findStartDateByProjectId() method has been started.");
			Query q = em.createNamedQuery("Project.findStartDateByProjectId");
			q.setParameter("id", projectId);
			result = (Date) q.getSingleResult();
			em.flush();
			logger.debug("findStartDateByProjectId method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findStartDateByProjectId method has been failed.", pe);
			throw translate("Failed to find Start Date by Project(projectId = " + projectId + ")",
					pe);
		}
		return result;
	}

	/**
	 * Revised By   : Aye Myat Mon
	 * Revised Date : 2018/04/30
	 * Explanation  : Get Client Organization form project table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findClientOrganization() throws DAOException
	{
		List<String> result = null;
		try{
			logger.debug("findClientOrganization()method has been started."); 
			Query q= em.createNamedQuery("Project.findClientOrganization");
			result = q.getResultList();
			 em.flush();
			logger.debug("findClientOrganization()method has been finished."); 
			
		}catch(PersistenceException pe){
			logger.error("findClientOrganization() method has been failed.", pe);
		}
		return result;
	}

	
}