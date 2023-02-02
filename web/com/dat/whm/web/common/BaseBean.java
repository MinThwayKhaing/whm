package com.dat.whm.web.common;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.dat.whm.exception.SystemException;

@ManagedBean(name = "BaseBean")
@ViewScoped
public class BaseBean {
	
	/**
	* Revised By : Sai Kyaw Ye Myint  
	* Revised Date : 2017/08/01 
	* Explanation: Revised for tracking direct URL access.
	*/
	public void init() throws IOException {
		Map<String, String> referer = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
		HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String cacheControl = referer.get("cache-control");
		boolean backInquiryFlag = false;
		if (!FacesContext.getCurrentInstance().getExternalContext().getFlash().isEmpty()) {
			backInquiryFlag = (boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
		}
		if(cacheControl == null){
			cacheControl = "";
		}
		
		if (!backInquiryFlag) {
			if (httpRequest.getContentType() == null && !cacheControl.equals("max-age=0")) {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				FacesContext facesContext = getFacesContext();
				HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
				session.setAttribute("message","Direct url access dose not allow!!!");
				externalContext.redirect(externalContext.getRequestContextPath() + "/faces/error/error.xhtml");
			}
		}
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected Application getApplicationContext() {
		return getFacesContext().getApplication();
	}

	protected Map getApplicationMap() {
		return getFacesContext().getExternalContext().getApplicationMap();
	}

	protected Map getSessionMap() {
		return getFacesContext().getExternalContext().getSessionMap();
	}

	protected ResourceBundle getBundle() {
		return ResourceBundle.getBundle(getApplicationContext().getMessageBundle(),
				getFacesContext().getViewRoot().getLocale());
	}

	protected String getSystemPath() {
		Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		return systemPath;
	}

	protected String getMessage(String id, Object... params) {
		String text = null;
		try {
			text = getBundle().getString(id);
		} catch (MissingResourceException e) {
			if (id.equals("NOT_AVAILABLE_USERNAME")) {
				text = "User already exists.";
			} else {
				text = "!! key " + id + " not found !!";
			}
		}
		if (params != null) {
			MessageFormat mf = new MessageFormat(text);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}

	protected void addWranningMessage(String id, String errorCode, Object... params) {
		String message = getMessage(errorCode, params);
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_WARN, message, ""));
	}

	protected void addInfoMessage(String id, String errorCode, Object... params) {
		String message = getMessage(errorCode, params);
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
	}

	protected void addErrorMessage(String id, String errorCode, Object... params) {
		String message = getMessage(errorCode, params);
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
	}

	protected void addErrorMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	protected void addInfoMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	protected void addWranningMessage(String message) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}

	protected void addErrorByMessage(String id, String message) {
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	protected void addInfoByMessage(String id, String message) {
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	protected void addWranningByMessage(String id, String message) {
		getFacesContext().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}

	protected void handelSysException(SystemException systemException) {
		String errorCode = systemException.getErrorCode();
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage(errorCode), ""));
	}

	protected void putParam(String key, Object obj) {
		getSessionMap().put(key, obj);
	}

	protected Object getParam(String key) {
		return getSessionMap().get(key);
	}

	protected boolean isExistParam(String key) {
		return getSessionMap().containsKey(key);
	}

	protected boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if (value.toString().isEmpty()) {
			return true;
		}
		return false;
	}

	protected Map<String, Object> dialogOptions;

	protected Map<String, Object> getDialogOption() {
		if (dialogOptions == null) {
			dialogOptions = new HashMap<String, Object>();
			dialogOptions.put("modal", true);
			dialogOptions.put("draggable", false);
			dialogOptions.put("resizable", false);
			dialogOptions.put("contentHeight", 600);
			dialogOptions.put("contentWidth", 700);
		}
		return dialogOptions;
	}

	protected void openDialog(String dialog, boolean singleSelection) {
		putParam("singleSelection", singleSelection);
		RequestContext.getCurrentInstance().openDialog(dialog, getDialogOption(), null);
	}
}
