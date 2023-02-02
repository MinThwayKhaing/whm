package com.dat.whm;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("PasswordCodecHandler")
public class PasswordCodecHandler {
	private PasswordEncoder encoder;

    public PasswordCodecHandler() {
    	encoder = new StandardPasswordEncoder("jobglobalmm");
    }

    public String encode(String password) {
        return encoder.encode(password);
    }
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
    	return encoder.matches(rawPassword, encodedPassword);
    }
    
    public static void main(String[] args) {
        PasswordCodecHandler passwordCodecHandler= new PasswordCodecHandler(); 
        String s1 = passwordCodecHandler.encode("password");
        System.out.println("password : " + s1);

        String s2 = passwordCodecHandler.encode("admin");
        System.out.println("admin : " + s2);

        String s3 = passwordCodecHandler.encode("administrator");
        System.out.println("administrator : " + s3);

        String s4 = passwordCodecHandler.encode("123");
        System.out.println("123: " + s4);
        
        boolean match = passwordCodecHandler.matches("123", "20f0aa543fe8f99336ce29a49b4f428accc7b93aafc1d9418a0375ff09ec40d7370f3894bc80a680");
        System.out.println(match);
    }
}
