/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2018-02-02
 * @Version 1.0
 * @Purpose Created for HR Overhead Cost Management function.
 ***************************************************************************************/
package com.dat.whm.hrcost.persistence.interfaces;


import java.util.List;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.hrcost.entity.HRCost;

public interface IHRCostDAO {
	public List<HRCost> findAllHRCost();
	public List<HRCost> findByYearD(String year,DeleteDiv delDiv);
	public HRCost findByHRCost(double cost);
	//public HRCost  findByIdD(int id);
	public void deleteHRCostD(HRCost hrcost);
	public HRCost insertHRCostD(HRCost hrcost);
	//public HRCost updateByYearD(HRCost hrcost);
	public HRCost updateHRCostD(HRCost hrcost);
	
}
