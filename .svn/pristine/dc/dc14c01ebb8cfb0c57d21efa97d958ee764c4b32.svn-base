package com.dat.whm.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zaw Than Oo
 */
public class FormatID {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyyyy");
	
	public static String formatId(String id, String prefix, int maxLength) {
        if (!id.startsWith(prefix)) {
            int length = id.length() + prefix.length();
            for (; (maxLength - length) > 0; length++) {
                id = '0' + id;
            }
            id = prefix + id + getDateString();
        }
        return id;
    }

	private static String getDateString() {
		return simpleDateFormat.format(new Date());
	}

	public static void main(String args[]) {
		System.out.println(formatId("202", "INSU", 10));
	}
}
