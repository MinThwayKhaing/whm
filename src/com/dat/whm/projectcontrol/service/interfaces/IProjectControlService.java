/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Interface for project control service.
 *
 ***************************************************************************************/
package com.dat.whm.projectcontrol.service.interfaces;

import com.dat.whm.exception.SystemException;
import com.dat.whm.projectcontrol.entity.ProjectControl;

public interface IProjectControlService {
	public ProjectControl addNewProjectControl(ProjectControl projectControl) throws SystemException;
}
