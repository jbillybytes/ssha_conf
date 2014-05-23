package org.ssha.conference.api.utilities;

import java.util.ArrayList;

public class Helpers {

	/*Function for join ArrayLists*/
	public static String join(ArrayList<String> list, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String item : list) {
			if (first)
				first = false;
			else
				sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}

	
}
