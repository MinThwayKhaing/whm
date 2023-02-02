/***************************************************************************************
 * @author Aye Thida Phyo
 * @Date 2017-09-06
 * @Version 1.0
 * @Purpose Created for PhaseIII : Team Management
 *
 ***************************************************************************************/

package com.dat.whm.web.manageteam;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.SystemException;
import com.dat.whm.team.entity.Team;
import com.dat.whm.team.service.interfaces.ITeamService;
import com.dat.whm.userteam.entity.UserTeam;
import com.dat.whm.userteam.service.interfaces.IUserTeamService;
import com.dat.whm.userteamauthority.entity.UserTeamAuthority;
import com.dat.whm.userteamauthority.service.interfaces.IUserTeamAuthorityService;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;

@ManagedBean(name = "ManageTeamActionBean")
@ViewScoped
public class ManageTeamActionBean extends BaseBean {	

	private boolean createNew;
	@ManagedProperty(value = "#{TeamService}")
	private ITeamService teamService;
	@ManagedProperty(value = "#{UserTeamService}")
	private IUserTeamService userteamService;
	@ManagedProperty(value = "#{UserTeamAuthorityService}")
	private IUserTeamAuthorityService userteamAuthorityService;
	private Team team;
	private List<Team> teams;
	boolean textTeamName;
	boolean btnAdd;
	private UserTeam userteam;

	@PostConstruct
	public void init() {
		createNewTeam();
		load();
	}

	private void load() {
		teams = teamService.findAllTeam();
	}

	public void createNewTeam() {
		createNew = true;
		team = new Team();
		setTextTeamName(false);
	}

	public void prepareUpdateTeam(Team team) {
		createNew = false;
        this.team = team;
        setTextTeamName(true);
	}


	public void addNewTeam() {
		try {
			List<Team> result = teamService.findByTeamName(team
					.getTeamName(), DeleteDiv.DELETE);

			if (!result.isEmpty()) {
				teamService.updateByTeamName(team);
			} else {
				team.setDelDiv(DeleteDiv.ACTIVE);
				teamService.addNewTeam(team);
			}
			 addInfoMessage(null, MessageId.INSERT_SUCCESS,
					team.getTeamName());
			createNewTeam();
			load();
			
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void updateTeam() {
		try {			
			teamService.updateTeam(team);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, team.getTeamName());
			createNewTeam();
			load();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void deleteTeam(Team team) {
		
		List<UserTeam> user_team = userteamService.findByTeamID(team);
		
		List<UserTeamAuthority> user_team_auth = userteamAuthorityService.findByTeamID(team);
		
		try {
			if (!user_team.isEmpty()) {
				addErrorMessage(null, MessageId.MS0007, team.getTeamName());
			}else if(!user_team_auth.isEmpty()){
				addErrorMessage(null, MessageId.MS0007, team.getTeamName());	
			}else{
				team.setDelDiv(DeleteDiv.DELETE);
				teamService.updateTeam(team);
				addInfoMessage(null, MessageId.DELETE_SUCCESS, team.getTeamName());
			}
			createNewTeam();
			load();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}	
	
	public void checkTeamName() {

		List<Team> result = null;
		result = teamService.findByTeamName(team.getTeamName(), DeleteDiv.ACTIVE);
		
		try {
			if (!result.isEmpty()) {
				setBtnAdd(true);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"Team already exists!!"));
			}else{
				setBtnAdd(false);
			}
		} catch (SystemException e) {
			handelSysException(e);
		}

	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public ITeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(ITeamService teamService) {
		this.teamService = teamService;
	}
	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public boolean isTextTeamName() {
		return textTeamName;
	}

	public void setTextTeamName(boolean textTeamName) {
		this.textTeamName = textTeamName;
	}

	public boolean isBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(boolean btnAdd) {
		this.btnAdd = btnAdd;
	}

	public IUserTeamService getUserteamService() {
		return userteamService;
	}

	public void setUserteamService(IUserTeamService userteamService) {
		this.userteamService = userteamService;
	}

	public UserTeam getUserteam() {
		return userteam;
	}

	public void setUserteam(UserTeam userteam) {
		this.userteam = userteam;
	}

	public IUserTeamAuthorityService getUserteamAuthorityService() {
		return userteamAuthorityService;
	}

	public void setUserteamAuthorityService(
			IUserTeamAuthorityService userteamAuthorityService) {
		this.userteamAuthorityService = userteamAuthorityService;
	}
}