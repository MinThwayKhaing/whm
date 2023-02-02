/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose << Setting file reader.>>
 *
 ***************************************************************************************/
package com.dat.whm.web.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Properties {
	
	
	private Properties() {
    }
	
	public static String getProperty(String file, String key) {

        ResourceBundle bundle = null;
        
            bundle = ResourceBundle.getBundle(file);
            String value = bundle.getString(key);

        return value;

    }
	
	public static List<String> getPropertyList(String file, String key) {

        ResourceBundle bundle = null;
        
            bundle = ResourceBundle.getBundle(file);
            String str = bundle.getString(key);
            List<String> value = new ArrayList<>(Arrays.asList(str.split(",")));

        return value;

    }
}
