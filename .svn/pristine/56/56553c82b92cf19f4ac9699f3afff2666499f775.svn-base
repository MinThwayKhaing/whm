package com.dat.whm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RandomTest {
	public static void main(String[] args) {
	    List<String> list = new ArrayList<String>();
	    for(int i = 0; i < 100; i++){
	        list.add(i + "");
	    }

	    Collections.shuffle(list);
	    String[] randomArray = list.subList(0, 10).toArray(new String[10]);

	    for(String num : randomArray){
	        System.out.println(num);
	    }
	}
}
