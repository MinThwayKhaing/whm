/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose enum class for organization(client and service provider).
 *
 ***************************************************************************************/
package com.dat.whm.common.entity;

public enum OrganizationDev {
	CLIENT {
		public String toString() {
			return "Client";
		}
	}
	, SERVICE_PROVIDER {
		public String toString() {
			return "Service Provider";
		}
	};
	
	public abstract String toString();
}
