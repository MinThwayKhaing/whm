/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2018-02-02
 * @Version 1.0
 * @Purpose Created for HR Overhead Cost Management function.
 ***************************************************************************************/
package com.dat.whm.hrcost.service.interfaces;


import java.util.List;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.hrcost.entity.HRCost;

public interface IHRCostService {
	
	public List<HRCost> findAllHRCostS();
	public List<HRCost> findByYearS(String year,DeleteDiv delDiv);
	public HRCost findByHRCostS(double cost);
	//public HRCost  findByIdS(int id);
	public HRCost updateHRCostS(HRCost hrcost);
	//public HRCost updateByYearS(HRCost hrcost);
	public HRCost insertHRCostS(HRCost hrcost);
	public void deleteHRCostS(HRCost hrcost);
}
