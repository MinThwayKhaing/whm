/***************************************************************************************
 * @author Kyi Saw Win
 * @Date 2017-09-25
 * @Version 1.0
 * @Purpose Interface of Holidays DAO.
 *
 ***************************************************************************************/
package com.dat.whm.holidays.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.web.common.SearchDate;

public interface IHolidaysDAO {
	
	public int countByYearMonth(SearchDate searchDate) throws DAOException;

	/**
	 * Created By   : Ye Phyo Ko
	 * Created Date : 2018/01/11
	 * Explanation  : Added insert(), findByDate(), findAll(),
	 * 				  update() and delete() methods.
	 */
	public Holidays insert(Holidays holiday);

	public List<Holidays> findByDate(Date date);

	public List<Holidays> findAll();

	public Holidays update(Holidays holiday);

	public void delete(Holidays holiday);
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find holidays by year and month.
	 */
	public List<Holidays> findByYearMonth(int year,int month);
	

	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Find all by date.
	 */
	public List<Holidays> findAllByDate(String year, String month);

}
