/***************************************************************************************
 * @author Kyi Saw Win
 * @Date 2017-09-25
 * @Version 1.0
 * @Purpose Interface of Holidays Service.
 *
 ***************************************************************************************/
package com.dat.whm.holidays.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.SystemException;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.web.common.SearchDate;

public interface IHolidaysService {
	
	public int countByYearMonth(SearchDate searchDate) throws SystemException;

	/**
	 * Created By   : Ye Phyo Ko
	 * Created Date : 2018/01/11
	 * Explanation  : Added addNewHoliday(), findByDate(), findAllHoliday(),
	 * 				  updateHoliday() and deleteHoliday() methods.
	 */
	public Holidays addNewHoliday(Holidays holiday) throws SystemException;
	public List<Holidays> findByDate(Date date);
	public List<Holidays> findAllHoliday();
	public Holidays updateHoliday(Holidays holiday);
	public void deleteHoliday(Holidays holiday);
	
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
