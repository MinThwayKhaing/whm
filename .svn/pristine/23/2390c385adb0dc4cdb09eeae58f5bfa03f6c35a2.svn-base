package com.dat.whm.web.authentication;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.dat.whm.user.entity.User;
import com.dat.whm.web.Constants;

public class ExamPhaseListener implements PhaseListener {

	 public void beforePhase(PhaseEvent event) {
	 }

	 public void afterPhase(PhaseEvent event) {
	 }

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	private FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	private Application getApplication() {
		return getFacesContext().getApplication();
	}

	private User getUser() {
		FacesContext context = getFacesContext();
		ExpressionFactory eFactory = context.getApplication().getExpressionFactory();
		ValueExpression binding = eFactory.createValueExpression(context.getELContext(), "#{sessionScope." + Constants.LOGIN_USER + "}", User.class);
		User user = (User) binding.getValue(context.getELContext());
		return user;
	}

	private void forward(FacesContext facesContext, String page) {
		UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, page);
		facesContext.setViewRoot(view);
		facesContext.renderResponse();
	}
}