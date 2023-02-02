/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-22
 * @Version 1.0
 * @Purpose Validator class for email format.
 *
 ***************************************************************************************/
package com.dat.whm.web.common.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.dat.whm.web.Constants;
import com.dat.whm.web.common.Properties;

@FacesValidator
public class EmailValidator implements Validator {
	private Pattern pattern;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)throws ValidatorException {
		
        pattern = Pattern.compile(Properties.getProperty(Constants.APP_SETTING, Constants.EMAIL_PATTERN));
        
		if(value == null || value.toString().trim().length() == 0) {
            return;
        }
		
		if(!pattern.matcher(value.toString()).matches()) {
			FacesMessage msg = new FacesMessage("Incorrect input provided", value.toString() + " is not a valid email");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
		
	}

}
