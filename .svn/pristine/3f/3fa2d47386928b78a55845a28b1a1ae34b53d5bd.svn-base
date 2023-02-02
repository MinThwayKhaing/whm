/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/

package com.dat.whm.web.manage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.userteamhistory.entity.UserTeamHistory;
import com.dat.whm.userteamhistory.service.interfaces.IUserTeamHistoryService;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;
import com.dat.whm.userteammainlybelonghistory.service.interfaces.IUserTeamMainlyBelongHistoryService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;

@ManagedBean(name = "ManageUserActionBean")
@ViewScoped
public class ManageUserActionBean extends BaseBean {

	private boolean createNew;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;
	@ManagedProperty(value = "#{TeamService}")
	private ITeamService teamService;
	private User user;
	private List<User> userList;
	private List<Team> selectedTeams;
	private List<Team> teams;
	private boolean checkedValue;
	private String displayPsw;
	private String displayDefaultPsw;
	/**
	 * Revised By   : Sai Kyaw Ye Myint
	 * Revised Date : 2017/09/12
	 * Explanation  : Added service object for user team history and tempTeamlist.
	 */
	@ManagedProperty(value = "#{UserTeamHistoryService}")
	private IUserTeamHistoryService userTeamHistoryService;
	private List<Team> tempTeamList = new ArrayList<>();
	
	/**
	 * Revised By   : Aye Myat Mon
	 * Revised Date : 2019/02/14
	 * Explanation  : Added service object for mainly belong team history and main belong team.
	 */
	@ManagedProperty(value = "#{UserTeamMainlyBelongHistoryService}")
	private IUserTeamMainlyBelongHistoryService mainlyBelongTeamHistoryService;
	
	private Team previousMainTeamInfo;
	
	@PostConstruct
	public void init() {
		createNewUser();
		load();
	}

	public Role[] getRoles() {
		return Role.values();
	}

	private void load() {
		User loginUser = (User) getParam(Constants.LOGIN_USER);
		userList = userService.findAllUser();
		teams = teamService.findAllTeam();
	}

	public void createNewUser() {
		createNew = true;
		setDisplayPsw("true");
		setDisplayDefaultPsw("none");
		user = new User();
		user.setRole(Role.ADMIN);
	}

	public void prepareUpdateUser(User user) {
		createNew = false;
		setDisplayPsw("none");
		setDisplayDefaultPsw("true");
		this.user = user;
		/**
		 * Revised By   : Sai Kyaw Ye Myint
		 * Revised Date : 2017/09/12
		 * Explanation  : Store team list before edit.
		 */
		this.tempTeamList.addAll(user.getTeams());
		/**
		 * Revised By   : Aye Myat Mon
		 * Revised Date : 2019/02/14
		 * Explanation  : Store "Mainly Belong Team" before edit.
		 */
		previousMainTeamInfo = user.getMainlyBelongTeam();
	}

	public void addNewUser() {
		try {
			user.setPassword(user.getUsername());
			/**
			 * Revised By   : Sai Kyaw Ye Myint
			 * Revised Date : 2017/09/08
			 * Explanation  : Modify for delete div in user.
			 */
			user.setDelDiv(DeleteDiv.ACTIVE);
			/**
			 * Revised By   : Aye Myat Mon
			 * Revised Date : 2019/02/15
			 * Explanation  : Modify for user mainly belong team history.
			 */
			User updatedUser = new User();
			updatedUser=userService.addNewUser(user);
			mainlyBelongTeamHistoryService.insertMainTeamHistory(updatedUser);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, user.getFullName());
			createNewUser();
			load();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}
	
	
	public void updateUser() {
		try {
		
		/**
		 * Revised By   : Aye Myat Mon
		 * Revised Date : 2019/02/14
		 * Explanation  : Check for "Mainly Belong Team" change.
		 */
		int currentMainTeamId;
		currentMainTeamId = user.getMainlyBelongTeam().getId();
		
		boolean updateInfo = true;
		
		if(previousMainTeamInfo != null)
		{	
			if(currentMainTeamId != previousMainTeamInfo.getId())
			{
				updateInfo = checkMainlyBelongTeam(user);
			}
		}
		
		
		if(updateInfo == true)
		{
			
			userService.updateUser(user);
			/**
			 * Revised By   : Sai Kyaw Ye Myint
			 * Revised Date : 2017/09/12
			 * Explanation  : Check for team change.
			 */
			User loginUser= (User) getParam(Constants.LOGIN_USER);
			UserTeamHistory userTeamHistory= new UserTeamHistory();
			userTeamHistory.setUserId(user);
			userTeamHistory.setUpdateBy(loginUser);
			userTeamHistory.setUpdateDate(new Date());
			for (Team team : tempTeamList) {
				if (!user.getTeams().contains(team)) {
					userTeamHistory.setTeamId(team);
					userTeamHistoryService.addRecord(userTeamHistory);
				}
			}
			tempTeamList.clear();

			if (isCheckedValue()) {
				userService.resetPassword(user.getUsername());
			}
			if(currentMainTeamId != previousMainTeamInfo.getId()){
			if(previousMainTeamInfo != null)
			{
				
				
						//Update the end date of previous mainly Belong Team. Update only when user has previous mainly belong team.
						GregorianCalendar yesterday = new GregorianCalendar();
						yesterday.add(Calendar.DATE, -1);
						
						UserTeamMainlyBelongHistory oldMainTeamInfo = new UserTeamMainlyBelongHistory();
						oldMainTeamInfo.setUser(user);
						oldMainTeamInfo.setEndDate(yesterday.getTime());
						oldMainTeamInfo.setTeam(previousMainTeamInfo);
						mainlyBelongTeamHistoryService.updateEndDate(oldMainTeamInfo);
							
						//Insert current mainly Belong Team Information.
						mainlyBelongTeamHistoryService.insertMainTeamHistory(user);
						
				
			}
			else
			{
				//Insert current mainly Belong Team Information.
				mainlyBelongTeamHistoryService.insertMainTeamHistory(user);
			}
		}
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, user.getFullName());
			createNewUser();
			load();
		
		}
		//}
		else if(updateInfo == false)
		{
			String errorMessage = "Mainly Belong Team is already existed within the updated period!";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							errorMessage));
			createNewUser();
			load();
			
		}
	} catch (SystemException e) {
		handelSysException(e);
	}
}
	
	/**
	 * Revised By   : Aye Myat Mon
	 * Revised Date : 2019/02/15
	 * Explanation  : Check for user mainly belong team change.
	 */
	public boolean checkMainlyBelongTeam (User user)
	{
		boolean checkMainTeam = true;
		
		List<UserTeamMainlyBelongHistory> teamHistoryList = new ArrayList<>();
		teamHistoryList = mainlyBelongTeamHistoryService.findByIds(user.getId());
		//start
		System.out.println("User list size = "+teamHistoryList.size());
		for(UserTeamMainlyBelongHistory ut:teamHistoryList){
			System.out.println("User name = "+ut.getUser().getFullName());
			System.out.println("Team name = "+ut.getTeam().getTeamName());
		}
		//end
		Date startDate = null;
		
		if (teamHistoryList.size()>0)
		{	
			//Date today = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date today = null;
			try {
				today = formatter.parse(formatter.format(new Date()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Check user's updated mainly belong team has already existed between start date and end date.
			 for (UserTeamMainlyBelongHistory mainTeamList : teamHistoryList) {
				try {
					
					startDate =formatter.parse(formatter.format(mainTeamList.getStartDate())) ;
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(today.compareTo(startDate) == 0 )
				{
					checkMainTeam = false;
					return false;
				}
				
			}
		}
		
		return checkMainTeam;
	}

	public void deleteUser(User user) {
		try {
			/**
			 * Revised By   : Sai Kyaw Ye Myint
			 * Revised Date : 2017/09/11
			 * Explanation  : Modify for user physical delete to logical delete.
			 */
			//userService.deleteUser(user);
			user.setDelDiv(DeleteDiv.DELETE);
			userService.updateUser(user);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, user.getFullName());
			load();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public String showTeams(User user) {
		String teamsName = "";
		for (int i = 0; i < user.getTeams().size(); i++) {
			if (i != 0) {
				teamsName += "," + user.getTeams().get(i).getTeamName();
			} else {
				teamsName += user.getTeams().get(i).getTeamName();
			}
		}
		return teamsName;
	}

	public String showAuthorities(User user) {
		String teamsName = "";
		for (int i = 0; i < user.getAuthorities().size(); i++) {
			if (i != 0) {
				teamsName += "," + user.getAuthorities().get(i).getTeamName();
			} else {
				teamsName += user.getAuthorities().get(i).getTeamName();
			}
		}
		return teamsName;
	}

	public IUserTeamHistoryService getUserTeamHistoryService() {
		return userTeamHistoryService;
	}

	public void setUserTeamHistoryService(
			IUserTeamHistoryService userTeamHistoryService) {
		this.userTeamHistoryService = userTeamHistoryService;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ITeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(ITeamService teamService) {
		this.teamService = teamService;
	}

	public List<Team> getSelectedTeams() {
		return selectedTeams;
	}

	public void setSelectedTeams(List<Team> selectedTeams) {
		this.selectedTeams = selectedTeams;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public boolean isCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(boolean checkedValue) {
		this.checkedValue = checkedValue;
	}

	public String getDisplayPsw() {
		return displayPsw;
	}

	public void setDisplayPsw(String displayPsw) {
		this.displayPsw = displayPsw;
	}

	public String getDisplayDefaultPsw() {
		return displayDefaultPsw;
	}

	public void setDisplayDefaultPsw(String displayDefaultPsw) {
		this.displayDefaultPsw = displayDefaultPsw;
	}

	public IUserTeamMainlyBelongHistoryService getMainlyBelongTeamHistoryService() {
		return mainlyBelongTeamHistoryService;
	}

	public void setMainlyBelongTeamHistoryService(
			IUserTeamMainlyBelongHistoryService mainlyBelongTeamHistoryService) {
		this.mainlyBelongTeamHistoryService = mainlyBelongTeamHistoryService;
	}

	
}