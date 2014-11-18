package com.csfaq.reportit.constants;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Constants {

    public static final String BROWSER_USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.78.2 (KHTML, like Gecko) Version/7.0.6 Safari/537.78.2";

	public static final String CITY_TRAFFIC_POLICE_BASE_URL = "www.bangaloretrafficpolice.gov.in";
	// "/doSubmit/postcomplaint.aspx?txtname=testName&txtphone=999999999&txtemail=foo%40bar.com&txtCategory=1&txtcomplaint=This+is+a+test+complaint&stname=&Submit=Submit HTTP/1.1";
	public static final String CITY_TRAFFIC_POLICE_PARAMS = "/doSubmit/postcomplaint.aspx?txtname=%s&txtphone=%s&txtemail=%s&txtCategory=%d&txtcomplaint=%s&stname=&Submit=Submit";
	
	public static final String CITY_TRAFFIC_POLICE_URL = CITY_TRAFFIC_POLICE_BASE_URL + CITY_TRAFFIC_POLICE_PARAMS;
	
	
	
	
	
	
	public static final String BBMP_POST_URL = "http://vigeyegpms.in/bbmp/?module=helpdeskpublic&action=raisecomplaint";
	public static List<NameValuePair> BBMP_POST_HEADER = new ArrayList<NameValuePair>();
	public static final String BBMP_POST_BODY = "rs=updateRaiseComplaint&rsargs[]=subject!COL!%s!ROW!message!COL!!ROW!name!COL!%s!ROW!mobile!COL!%s!ROW!telephone!COL!!ROW!emailid!COL!%s!ROW!wardid!COL!%s!ROW!location!COL!%s!ROW!Submit!COL!Submit!ROW!Submit!COL!Reset!ROW!rcid!COL!!ROW!action!COL!raisecomplaint!ROW!module!COL!helpdeskpublic!ROW!message2!COL!%s&rsargs[]=post_data";

	static {
		BBMP_POST_HEADER.add(new BasicNameValuePair("Host", "vigeyegpms.in"));
		BBMP_POST_HEADER.add(new BasicNameValuePair("Connection", "keep-alive"));
		BBMP_POST_HEADER.add(new BasicNameValuePair("Origin", "http://vigeyegpms.in"));
		BBMP_POST_HEADER.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36"));
		BBMP_POST_HEADER.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
	}
}