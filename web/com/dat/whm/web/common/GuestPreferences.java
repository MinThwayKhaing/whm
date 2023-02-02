/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dat.whm.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "GuestPreferences")
@SessionScoped
public class GuestPreferences {
	private Theme theme = new Theme("aristo", "/images/theams/Aristo.png");
	private String themeName;
	private List<Theme> themeList;
	
	public List<Theme> getThemeList() {
		if(themeList == null) {
			themeList = new ArrayList<Theme>();
		}
		themeList.add(new Theme("afterdark", "/images/theams/Afterdark.png"));
		themeList.add(new Theme("afternoon", "/images/theams/Afternoon.png"));
		themeList.add(new Theme("afterwork", "/images/theams/Afterwork.png"));
		themeList.add(new Theme("aristo", "/images/theams/Aristo.png"));
		themeList.add(new Theme("black-tie", "/images/theams/Black-Tie.png"));
		themeList.add(new Theme("blitzer", "/images/theams/Blitzer.png"));
		themeList.add(new Theme("bluesky", "/images/theams/Bluesky.png"));
		themeList.add(new Theme("bootstrap", "/images/theams/Bootstrap.png"));
		themeList.add(new Theme("cruze", "/images/theams/Cruze.png"));
		themeList.add(new Theme("casablanca", "/images/theams/Casablanca.png"));
		themeList.add(new Theme("cupertino", "/images/theams/Cupertino.png"));
		themeList.add(new Theme("dark-hive", "/images/theams/Dark-Hive.png"));
		themeList.add(new Theme("dot-luv", "/images/theams/Dot-luv.png"));
		themeList.add(new Theme("eggplant", "/images/theams/Eggplant.png"));
		themeList.add(new Theme("excite-bike", "/images/theams/Excite-bike.png"));
		themeList.add(new Theme("flick", "/images/theams/Flick.png"));
		themeList.add(new Theme("glass-x", "/images/theams/Glass-x.png"));
		themeList.add(new Theme("home", "/images/theams/Home.png"));
		themeList.add(new Theme("hot-sneaks", "/images/theams/Hot-sneaks.png"));
		themeList.add(new Theme("le-frog", "/images/theams/Le-frog.png"));
		themeList.add(new Theme("midnight", "/images/theams/Midnight.png"));
		themeList.add(new Theme("mint-choc", "/images/theams/Mint-choc.png"));
		themeList.add(new Theme("overcast", "/images/theams/Overcast.png"));
		themeList.add(new Theme("pepper-grinder", "/images/theams/Pepper-Grinder.png"));
		themeList.add(new Theme("redmond", "/images/theams/Redmond.png"));
		themeList.add(new Theme("rocket", "/images/theams/Rocket.png"));
		themeList.add(new Theme("sam", "/images/theams/Sam.png"));
		themeList.add(new Theme("smoothness", "/images/theams/Smoothness.png"));
		themeList.add(new Theme("south-street", "/images/theams/South-Street.png"));
		themeList.add(new Theme("start", "/images/theams/Start.png"));
		themeList.add(new Theme("swanky-purse", "/images/theams/Swanky-Purse.png"));
		themeList.add(new Theme("trontastic", "/images/theams/Trontastic.png"));
		themeList.add(new Theme("ui-darkness", "/images/theams/UI-Darkness.png"));
		themeList.add(new Theme("ui-lightness", "/images/theams/UI-Lightness.png"));
		themeList.add(new Theme("vader", "/images/theams/Vader.png"));
		return themeList;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
}
