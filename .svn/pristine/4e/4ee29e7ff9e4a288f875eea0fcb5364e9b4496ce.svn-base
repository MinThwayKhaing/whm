package com.dat.whm.web.home;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name = "HomePage")
public class HomePage {
     
    public List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 7; i++) {
            images.add("image" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
