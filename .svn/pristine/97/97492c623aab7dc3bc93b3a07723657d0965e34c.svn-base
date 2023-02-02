/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose Validator class for empty string value.
 *
 ***************************************************************************************/
package com.dat.whm.web.common.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator
public class NoBlankSpaceValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //Check if user has typed only blank spaces
        if(value.toString().trim().isEmpty()){
            FacesMessage msg = new FacesMessage("Incorrect input provided", "The input can not be empty!!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
