package com.dat.whm.team.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.team.entity.Team;
import com.dat.whm.team.persistence.interfaces.ITeamDAO;
import com.dat.whm.team.service.interfaces.ITeamService;

@Service("TeamService")
public class TeamService implements ITeamService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "TeamDAO")
	private ITeamDAO teamDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Team addNewTeam(Team team) throws SystemException {
		try {
			logger.debug("addTeam() method has been started.");
			team = teamDAO.insert(team);
			logger.debug("addTeam() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewTeam() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Team", e);
		}
		return team;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Team updateTeam(Team team) throws SystemException {
		try {
			logger.debug("updateTeam() method has been started.");
			team = teamDAO.update(team);
			logger.debug("updateTeam() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateTeam() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to update Team", e);
		}
		return team;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTeam(Team team) throws SystemException {
		try {
			logger.debug("deleteTeam() method has been started.");
			teamDAO.delete(team);
			logger.debug("deleteTeam() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteTeam() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to delete Team", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Team> findAllTeam() throws SystemException {
		logger.debug("findAllTeam() method has been started.");
		List<Team> result = teamDAO.findAll();
		logger.debug("findAllTeam() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Team findTeam(int id) throws SystemException {
		logger.debug("findTeam() method has been started.");
		Team team = teamDAO.findById(id);
		logger.debug("findTeam() method has been finished.");
		return team;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Team findTeamByName(String name) throws SystemException {
		logger.debug("findTeamByName() method has been started.");
		Team team = teamDAO.findByName(name);
		logger.debug("findTeamByName() method has been finished.");
		return team;
	}
	

	/**
	    * Revised By : Aye Thida Phyo
	    * Revised Date : 2017/09/11
	    * Explanation: Added for findByTeamName method for search.
	    */  	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Team> findByTeamName(String teamName, DeleteDiv delDiv) throws SystemException {
		List<Team> result = null;
		try {
			logger.debug("findByTeamName() method has been started.");
			result = teamDAO.findByTeamName(teamName, delDiv);
			logger.debug("findByTeamName() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByTeamName() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Team by teamName", e);
		}
		return result;
	}

/**
 * Revised By : Aye Thida Phyo
 * Revised Date : 2017/09/11
 * Explanation: Added for updateByTeamName method for updating.
 */  
@Transactional(propagation = Propagation.REQUIRED)
public void updateByTeamName(Team team) throws SystemException {
	try {
		logger.debug("updateByTeamName() method has been started.");
		team = teamDAO.updateByTeamName(team);
		logger.debug("updateByTeamName() method has been successfully finisehd.");
	} catch (DAOException e) {
		logger.error("updateByTeamName() method has been failed.");
		throw new SystemException(e.getErrorCode(), "Faield to update Team", e);
	}
}
}