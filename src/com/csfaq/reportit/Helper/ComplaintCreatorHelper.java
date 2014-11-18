package com.csfaq.reportit.Helper;

import com.csfaq.reportit.constants.Constants;

public class ComplaintCreatorHelper {
	
	public static String createTrafficPoliceSubmitUrl(String name, String phNum, String email, int type, String complaint) {
		
		return String.format(Constants.CITY_TRAFFIC_POLICE_URL, name, phNum, email, type, complaint);
		
	}

}
