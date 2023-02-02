package com.dat.whm.team.persistence.interfaces;

import java.util.List;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.DAOException;
import com.dat.whm.team.entity.Team;

public interface ITeamDAO {
	public Team insert(Team team) throws DAOException;

	public Team update(Team team) throws DAOException;

	public void delete(Team team) throws DAOException;

	public List<Team> findAll() throws DAOException;

	public Team findById(int id) throws DAOException;

	public Team findByName(String name) throws DAOException;

	/** Revised By Aye Thida Phyo on 2017/09/11. */ 
	public List<Team> findByTeamName(String teamName, DeleteDiv delDiv) throws DAOException;

	/** Revised By Aye Thida Phyo on 2017/09/12. */ 
	public Team updateByTeamName(Team team) throws DAOException;
}