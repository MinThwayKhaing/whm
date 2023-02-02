/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.user.persistence;

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
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.persistence.interfaces.IUserDAO;

@Repository("UserDAO")
public class UserDAO extends BasicDAO implements IUserDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public User insert(User user) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(user);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert User(Username = "
					+ user.getUsername() + ")", pe);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User update(User user) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			user = em.merge(user);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update User(Username = "
					+ user.getUsername() + ")", pe);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(User user) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			/**
			 * Revised By   : Sai Kyaw Ye Myint
			 * Revised Date : 2017/09/08
			 * Explanation  : Modify for user physical delete to logical delete.
			 */
			user.setDelDiv(DeleteDiv.DELETE);
			user = em.merge(user);
			//em.remove(user);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete User(Username = "
					+ user.getUsername() + ")", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> findAll() {
		List<User> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("User.findAll");
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of User.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User find(String username) throws DAOException {
		User result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("User.findByUsername");
			q.setParameter("username", username);
			result = (User) q.getSingleResult();
			em.flush();
			logger.debug("findAll() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("find() method has been failed.", pe);
			throw translate("Failed to find User(Username = " + username + ")",
					pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> findByRole(Role role) throws DAOException {
		List<User> result = null;
		try {
			logger.debug("findByRole() method has been started.");
			Query q = em.createNamedQuery("User.findByRole");
			q.setParameter("role", role);
			result = q.getResultList();
			em.flush();
			logger.debug("findByRole() method has been finished.");
		} catch (PersistenceException pe) {
			logger.error("findByRole() method has been fail.", pe);
			throw translate("Failed to find User by JobTitle : "
					+ role.getLabel(), pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User findById(String id) throws DAOException {
		User result = null;
		try {
			logger.debug("findById() method has been started.");
			Query q = em.createNamedQuery("User.findById");
			q.setParameter("id", id);
			result = (User) q.getSingleResult();
			em.flush();
			logger.debug("findById() method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findById() method has been failed.", pe);
			throw translate("Failed to find User(Id = " + id + ")",
					pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePassword(String username, String newPassword)
			throws DAOException {
		try {
			logger.debug("changePassword() method has been started.");
			Query q = em.createNamedQuery("User.changePassword");
			q.setParameter("username", username);
			q.setParameter("newPassword", newPassword);
			q.executeUpdate();
			em.flush();
			logger.debug("changePassword() method has been finished.");
		} catch (PersistenceException pe) {
			logger.error("changePassword() method has been fail.", pe);
			throw translate("Failed to change Password (Username = " + username
					+ ")", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(String username, String defaultPassword)
			throws DAOException {
		try {
			logger.debug("resetPassword() method has been started.");
			Query q = em.createNamedQuery("User.resetPassword");
			q.setParameter("username", username);
			q.setParameter("defaultPassowrd", defaultPassword);
			q.executeUpdate();
			em.flush();
			logger.debug("resetPassword() method has been finished.");
		} catch (PersistenceException pe) {
			logger.error("resetPassword() method has been fail.", pe);
			throw translate("Failed to reset Password (Username = " + username
					+ ")", pe);
		}
	}
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find user information by user id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserInfoListByUserId(int year, int month,
			User user) throws DAOException {
		String query = null;
		List<Object[]> result = null;
		Query q = null;
		try {
			logger.debug("findAllUserByYearAndMonth() method has been started.");
				if (user.getRole() == Role.ADMIN) {
					
					query = "SELECT DISTINCT u.username,u.fullname,u.id"
							+ " FROM users u"
							+ " WHERE u.id <> ?4  AND u.del_div = ?5 ORDER BY u.username";
							

					q = em.createNativeQuery(query);
					
				} else if (user.getRole() == Role.MANAGEMENT) {
					query = "SELECT DISTINCT u.username,u.fullname,u.id"
							+ " FROM users u, user_team_mainly_belong utm,team t,daily_report dr,user_team_authority uta "
							+ " WHERE u.id = utm.user_id "
							+ " AND utm.team_id = t.team_id AND uta.team_id = t.team_id AND uta.user_id = ?3  AND u.id <> ?4 AND u.del_div = ?5 "
							+ " AND dr.staff_id = u.id ORDER BY u.username";

					q = em.createNativeQuery(query);
					q.setParameter(3, user.getId());
					
				}

			q.setParameter(1, year);
			q.setParameter(2, month);
			q.setParameter(4, "USR0001");
			q.setParameter(5, com.dat.whm.common.entity.DeleteDiv.ACTIVE);
			
			result = q.getResultList();
			em.flush();
			logger.debug("findAllUserByYearAndMonth() method has been finished.");
		} catch (PersistenceException pe) {
			logger.error("findAllUserByYearAndMonth() method has been fail.",
					pe);
			throw translate("Failed to find User by Year and Month ", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTeamInfoList(User user) throws DAOException {
		String query = null;
		List<Object[]> result = null;
		Query q = null;
		try {
			logger.debug("findAllUserByYearAndMonth() method has been started.");
				if (user.getRole() == Role.ADMIN) {
					
					query = "SELECT u.USER_ID,u.TEAM_ID,t.TEAM_NAME FROM user_team_mainly_belong u "
						   + " INNER JOIN team t ON t.team_id=u.team_id where t.del_div=0";
							

					q = em.createNativeQuery(query);
					
				} else if (user.getRole() == Role.MANAGEMENT) {
					query = "SELECT u.USER_ID,u.TEAM_ID,t.TEAM_NAME FROM user_team_mainly_belong u "
							+ " INNER JOIN team t ON t.team_id=u.team_id "
							+ " INNER JOIN user_team_authority uta ON uta.team_id = t.team_id "
							+ " WHERE uta.user_id = ?3 and t.del_div=0";
								

						q = em.createNativeQuery(query);
						q.setParameter(3, user.getId());
				}

			
			result = q.getResultList();
			em.flush();
			logger.debug("findAllUserByYearAndMonth() method has been finished.");
		} catch (PersistenceException pe) {
			logger.error("findAllUserByYearAndMonth() method has been fail.",
					pe);
			throw translate("Failed to find User by Year and Month ", pe);
		}
		return result;

	}
}
