/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-08-01
 * @Version 1.0
 * @Purpose For dynamically error message.
 *
 ***************************************************************************************/
package com.dat.whm.web.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ErrorMesssageBean")
@ViewScoped
public class ErrorMesssageBean {
	private String errorMessage;
	
	public void controlForm() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(session.getAttribute("message") != null){
			setErrorMessage((String) session.getAttribute("message"));
		}else {
			setErrorMessage("Server Error!!!");
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
