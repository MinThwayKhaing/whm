/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-19
 * @Version 1.0
 * @Purpose DAO class for project information.
 *
 ***************************************************************************************/
package com.dat.whm.projectinformation.persistence;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.communicationplan.entity.CommunicationPlan;
import com.dat.whm.contactperson.entity.ContactPerson;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.project.entity.Project;
import com.dat.whm.projectcontrol.entity.ProjectControl;
import com.dat.whm.projectdeliverable.entity.ProjectDeliverable;
import com.dat.whm.projectinformation.persistence.interfaces.IProjectInformationDAO;
import com.dat.whm.projectschedule.entity.ProjectSchedule;
import com.dat.whm.projectvolume.entity.ProjectVolume;
import com.dat.whm.riskfactors.entity.RiskFactors;
import com.dat.whm.schedulestartend.entity.ScheduleStartEnd;

@Repository("ProjectInformationDAO")
public class ProjectInformationDAO extends BasicDAO implements IProjectInformationDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Project project) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			
			em.persist(project);
			
			int orderIndex = 1;
			                                                                        
			for (ContactPerson clientContactPerson : project.getContactPersonList()) {
				clientContactPerson.setOrderNo(orderIndex);
				clientContactPerson.setProjectId(project);
				orderIndex++;
				em.persist(clientContactPerson);
			}
			
			orderIndex = 1;
			for (ProjectControl projectControl : project.getProjectControlList()) {
				projectControl.setOrderNo(orderIndex);
				projectControl.setProjectId(project);
				orderIndex++;
				em.persist(projectControl);
			}
			
			orderIndex = 1;
			for (CommunicationPlan communicationPlan : project.getCommunicationPlanList()) {
				communicationPlan.setOrderNo(orderIndex);
				communicationPlan.setProjectId(project);
				orderIndex++;
				em.persist(communicationPlan);
			}
			
			orderIndex = 1;
			for (ProjectDeliverable projectDeliverable : project.getProjectDeliverablesList()) {
				projectDeliverable.setOrderNo(orderIndex);
				projectDeliverable.setProjectId(project);
				orderIndex++;
				em.persist(projectDeliverable);
			}
			
			orderIndex = 1;
			for (RiskFactors riskFactors : project.getRiskFactorList()) {
				riskFactors.setOrderNo(orderIndex);
				riskFactors.setProjectId(project);
				orderIndex++;
				em.persist(riskFactors);
			}
			
			orderIndex = 1;
			for (ProjectVolume projectVolumn : project.getProjectVolumeList()) {
				projectVolumn.setOrderNo(orderIndex);
				projectVolumn.setProjectId(project);
				orderIndex++;
				em.persist(projectVolumn);
			}
			
			orderIndex = 1;
			for (ProjectSchedule projectSchedule : project.getProjectScheduleList()) {
				projectSchedule.setOrderNo(orderIndex);
				projectSchedule.setProjectId(project);
				orderIndex++;
				for (ScheduleStartEnd scheduleStartEnd : projectSchedule.getScheduleStartEndList()) {
					scheduleStartEnd.setScheduleId(projectSchedule);
				}
				em.persist(projectSchedule);
			}
			
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert ", pe);
		}
		
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Project project) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			project = em.merge(project);
			int orderIndex = 1;
            
			for (ContactPerson clientContactPerson : project.getContactPersonList()) {
				clientContactPerson.setOrderNo(orderIndex);
				clientContactPerson.setProjectId(project);
				orderIndex++;
				em.persist(clientContactPerson);
			}
			
			orderIndex = 1;
			for (ProjectControl projectControl : project.getProjectControlList()) {
				projectControl.setOrderNo(orderIndex);
				projectControl.setProjectId(project);
				orderIndex++;
				em.persist(projectControl);
			}
			
			orderIndex = 1;
			for (CommunicationPlan communicationPlan : project.getCommunicationPlanList()) {
				communicationPlan.setOrderNo(orderIndex);
				communicationPlan.setProjectId(project);
				orderIndex++;
				em.persist(communicationPlan);
			}
			
			orderIndex = 1;
			for (ProjectDeliverable projectDeliverable : project.getProjectDeliverablesList()) {
				projectDeliverable.setOrderNo(orderIndex);
				projectDeliverable.setProjectId(project);
				orderIndex++;
				em.persist(projectDeliverable);
			}
			
			orderIndex = 1;
			for (RiskFactors riskFactors : project.getRiskFactorList()) {
				riskFactors.setOrderNo(orderIndex);
				riskFactors.setProjectId(project);
				orderIndex++;
				em.persist(riskFactors);
			}
			
			orderIndex = 1;
			for (ProjectVolume projectVolumn : project.getProjectVolumeList()) {
				projectVolumn.setOrderNo(orderIndex);
				projectVolumn.setProjectId(project);
				orderIndex++;
				em.persist(projectVolumn);
			}
			
			orderIndex = 1;
			for (ProjectSchedule projectSchedule : project.getProjectScheduleList()) {
				projectSchedule.setOrderNo(orderIndex);
				projectSchedule.setProjectId(project);
				orderIndex++;
				for (ScheduleStartEnd scheduleStartEnd : projectSchedule.getScheduleStartEndList()) {
					scheduleStartEnd.setScheduleId(projectSchedule);
				}
				em.persist(projectSchedule);
			}
			
			em.flush();
			logger.debug("updat() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update ", pe);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Project project) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			Query q = em.createNamedQuery("Project.deleteProjectById");
			q.setParameter("project_id", project.getId());
			
			q.executeUpdate();
			em.flush();
			logger.debug("delete() method has successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete project : "+ project.getId()+".", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void clear(Project project) throws DAOException {
		logger.debug("clear() method has been started.");
		
		Query q = em.createQuery("DELETE FROM ContactPerson p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() ContactPerson.");
		
		q = em.createQuery("DELETE FROM ProjectControl p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() ProjectControl.");
		
		q = em.createQuery("DELETE FROM CommunicationPlan p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() CommunicationPlan.");
		
		q = em.createQuery("DELETE FROM ProjectDeliverable p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() ProjectDeliverable.");
		
		q = em.createQuery("DELETE FROM RiskFactors p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() RiskFactors.");
		
		q = em.createQuery("DELETE FROM ProjectVolume p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() ProjectVolume.");
		
		for (ProjectSchedule projectSchedule : project.getProjectScheduleList()) {
			q = em.createQuery("DELETE FROM ScheduleStartEnd p WHERE p.scheduleId = :schedule_Id");
			q.setParameter("schedule_Id", projectSchedule);
			q.executeUpdate();
			em.flush();
			logger.debug("clear() ScheduleStartEnd.");
		}
		
		q = em.createQuery("DELETE FROM ProjectSchedule p WHERE p.projectId = :project_id");
		q.setParameter("project_id", project);
		q.executeUpdate();
		em.flush();
		logger.debug("clear() ProjectSchedule.");
		
		logger.debug("clear() method has been successfully finisehd.");
	}

}
