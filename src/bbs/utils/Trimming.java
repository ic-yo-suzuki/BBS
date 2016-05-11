package bbs.utils;

public class Trimming {
	public static String trim(String value) {
	    if (value == null || value.length() == 0)
	        return value;
	    int st = 0;
	    int len = value.length();
	    char[] val = value.toCharArray();
	    while ((st < len) && ((val[st] <= ' ') || (val[st] == '　'))) {
	        st++;
	    }
	    while ((st < len) && ((val[len - 1] <= ' ') || (val[len - 1] == '　'))) {
	        len--;
	    }
	    return ((st > 0) || (len < value.length())) ? value.substring(st, len) : value;
	}


}
