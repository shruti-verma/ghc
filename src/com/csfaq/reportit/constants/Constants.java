package com.csfaq.reportit.constants;

public class Constants {

    public static final String BROWSER_USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.78.2 (KHTML, like Gecko) Version/7.0.6 Safari/537.78.2";

	public static final String CITY_TRAFFIC_POLICE_BASE_URL = "www.bangaloretrafficpolice.gov.in";
	// "/doSubmit/postcomplaint.aspx?txtname=testName&txtphone=999999999&txtemail=foo%40bar.com&txtCategory=1&txtcomplaint=This+is+a+test+complaint&stname=&Submit=Submit HTTP/1.1";
	public static final String CITY_TRAFFIC_POLICE_PARAMS = "/doSubmit/postcomplaint.aspx?txtname=%s&txtphone=%s&txtemail=%s&txtCategory=%d&txtcomplaint=%s&stname=&Submit=Submit";
	
	public static final String CITY_TRAFFIC_POLICE_URL = CITY_TRAFFIC_POLICE_BASE_URL + CITY_TRAFFIC_POLICE_PARAMS;

}