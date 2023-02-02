/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Interface for project volume service.
 *
 ***************************************************************************************/
package com.dat.whm.projectvolume.service.interfaces;

import com.dat.whm.exception.SystemException;
import com.dat.whm.projectvolume.entity.ProjectVolume;

public interface IProjectVolumeService {
	public ProjectVolume addNewProjectVolumn(ProjectVolume projectVolumn) throws SystemException;
}
