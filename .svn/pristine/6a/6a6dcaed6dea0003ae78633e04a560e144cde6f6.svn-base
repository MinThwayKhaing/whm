package com.dat.whm.team.service.interfaces;

import java.util.List;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.SystemException;
import com.dat.whm.team.entity.Team;

public interface ITeamService {

	public Team addNewTeam(Team team) throws SystemException;

	public Team updateTeam(Team team) throws SystemException;

	public void deleteTeam(Team team) throws SystemException;

	public List<Team> findAllTeam() throws SystemException;

	public Team findTeam(int id) throws SystemException;

	public Team findTeamByName(String name) throws SystemException;
	
	/** Revised By Aye Thida Phyo on 2017/09/11. */ 
	public List<Team> findByTeamName(String teamName, DeleteDiv delDiv) throws SystemException;

	/** Revised By Aye Thida Phyo on 2017/09/12. */ 
	public void updateByTeamName(Team team) throws SystemException;
}