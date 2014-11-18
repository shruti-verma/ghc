package com.csfaq.reportit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.csfaq.reportit.constants.Constants;

public class NetworkUtils {
	
	public static String TAG = NetworkUtils.class.getName();
	
    public static String loadFromNetwork(String urlString) throws IOException {

        InputStream stream = null;
        try {
            stream = downloadUrl(urlString);
            return readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     *
     * @param urlString A string representation of a URL.
     * @param asBrowser A boolean flag, if set, the web request goes as if from a browser
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws java.io.IOException
     */
    public static InputStream downloadUrl(String urlString, boolean asBrowser) throws IOException {

        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (asBrowser) {
            conn.setRequestProperty("User-Agent", Constants.BROWSER_USER_AGENT);
        }
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        // Start the query
        conn.connect();

        return conn.getInputStream();
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     *
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws java.io.IOException
     */
    public static InputStream downloadUrl(String urlString) throws IOException {
        return downloadUrl(urlString, false);
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     *
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws java.io.IOException
     */
    public static InputStream downloadUrlAsBrowser(String urlString) throws IOException {
        return downloadUrl(urlString, true);
    }


    /**
     * Reads an InputStream and converts it to a String.
     *
     * @param stream InputStream containing HTML from website
     * @return String version of InputStream.
     * @throws java.io.IOException
     */
    public static String readIt(InputStream stream) throws IOException {

        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line);
            }

            reader.close();

            return builder.toString();

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static HttpResponse postData(String url, StringEntity entity, List<NameValuePair> headers) {

        HttpResponse response = null;
        try {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            for (NameValuePair nvPair : headers) {
                httppost.addHeader(nvPair.getName(), nvPair.getValue());
            }
            httppost.setEntity(entity);

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            Log.w(TAG, e.getLocalizedMessage(), e);
        } catch (IOException e) {
            Log.w(TAG, e.getLocalizedMessage(), e);
        }
        return response;
    }

}
