package com.dat.whm.team.persistence;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.team.entity.Team;
import com.dat.whm.team.persistence.interfaces.ITeamDAO;

@Repository("TeamDAO")
public class TeamDAO extends BasicDAO implements ITeamDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	    * Revised By : Aye Thida Phyo
	    * Revised Date : 2017/09/07
	    * Explanation: Modify for insert method for orderNo field.
	    */	
	@Transactional(propagation = Propagation.REQUIRED)
	public Team insert(Team team) throws DAOException {
		try {			        
			logger.debug("insert() method has been started.");
			int result = (int) em.createQuery("select max(t.orderNo) from Team t").getSingleResult();
			team.setOrderNo(result + 1);
			em.persist(team);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Team(TeamName = " + team.getTeamName() + ")", pe);
		}
		return team;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Team update(Team team) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			team = em.merge(team);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update Team(TeamName = " + team.getTeamName() + ")", pe);
		}
		return team;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Team team) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			team = em.merge(team);
			em.remove(team);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Team(TeamName = " + team.getTeamName() + ")", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Team> findAll() throws DAOException {
		List<Team> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("Team.findAll");
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Team.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Team findById(int id) throws DAOException {
		Team result = null;
		try {
			logger.debug("findById() method has been started.");
			Query q = em.createNamedQuery("Team.findById");
			q.setParameter("id", id);
			result = (Team) q.getSingleResult();
			em.flush();
			logger.debug("findById() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findById() method has been failed.", pe);
			throw translate("Failed to find Team(Id = " + id + ")", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Team findByName(String name) throws DAOException {
		Team result = null;
		try {
			logger.debug("findByName() method has been started.");
			Query q = em.createNamedQuery("Team.findByName");
			q.setParameter("teamName", name);
			result = (Team) q.getSingleResult();
			em.flush();
			logger.debug("findByName() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findByName() method has been failed.", pe);
			throw translate("Failed to find Team(Name = " + name + ")", pe);
		}
		return result;
	}
	

	/**
	    * Revised By : Aye Thida Phyo
	    * Revised Date : 2017/09/11
	    * Explanation: Added for findByTeamName method for search.
	    */	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Team> findByTeamName(String teamName, DeleteDiv delDiv) throws DAOException {
		List<Team> result = null;
		try {
			logger.debug("findByTeamName() method has been started.");
			Query q = em.createNamedQuery("Team.findByTeamName");
			q.setParameter("teamName", teamName);
			q.setParameter("delDiv", delDiv );
			result = q.getResultList();
			em.flush();
			logger.debug("findByTeamName() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findByTeamName() method has been failed.", pe);
			throw translate("Failed to search Team by TeamName(TeamName = " + teamName + ")", pe);
		}
		return result;
	}
	
	/**
	    * Revised By : Aye Thida Phyo
	    * Revised Date : 2017/09/12
	    * Explanation: Added for updateByTeamName method for update.
	    */
	@Transactional(propagation = Propagation.REQUIRED)
	public Team updateByTeamName(Team team) throws DAOException {
		try {
			logger.debug("updateByTeamName() method has been started.");
			Query q = em.createNamedQuery("Team.updateByTeamName");
			q.setParameter("teamName", team.getTeamName());
			q.setParameter("fullName", team.getFullName());
			q.executeUpdate();
			em.flush();
			logger.debug("updateByTeamName() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("updateByTeamName() method has been failed.", pe);
			throw translate("Failed to update Team(TeamName = " + team.getTeamName() + ")", pe);
		}
		return team;
	}
}