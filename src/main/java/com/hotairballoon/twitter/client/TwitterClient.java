package com.hotairballoon.twitter.client;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TwitterClient {
	
	private final HttpClient httpClient;
	private String accessToken;
	
	private static final String authCode = "TGJXVklOTUZKcElCSVY4ZEg2eGtyeEZtVDpMaVF6Rkt2cDRXeUVXdW0wS3lKNWxlUGlnZ2NncEJoZlN0VGJTaVMzZGxkb1I2bmphOA==";

	public TwitterClient() throws ClientProtocolException, IOException {
		this.httpClient = HttpClients.createDefault();
		this.accessToken = getAccessToken();
	}
	
	public void teapot() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet("https://www.google.com/teapot");
		HttpResponse response = httpClient.execute(get);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
	}
	
	public List<String> search(String searchTerm) throws ClientProtocolException, IOException, JSONException {
		HttpGet get = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=" + URLEncoder.encode(searchTerm, "UTF-8"));
		HttpResponse response = execute(get);
		
		JSONTokener tokener = new JSONTokener(responseBodyAsString(response));
		JSONObject root = new JSONObject(tokener);
		
		JSONArray statuses = root.getJSONArray("statuses");
		
		List<String> tweets = new ArrayList<String>();
		
		for (int i = 0; i < statuses.length(); i++) {
			JSONObject status = statuses.getJSONObject(i);
			tweets.add(status.getString("text"));
		}
		
		return tweets;
	}
	
	private String getAccessToken() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost("https://api.twitter.com/oauth2/token");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		
		post.addHeader("Authorization", "Basic " + authCode);
		
		HttpResponse response = httpClient.execute(post);
		
	    String accessToken = responseBodyAsString(response).split("\"access_token\":\"")[1].split("\"")[0];
		
		return accessToken;
	}
	
	private String responseBodyAsString(HttpResponse response) throws UnsupportedOperationException, IOException {
		StringWriter writer=new StringWriter();
	    IOUtils.copy(response.getEntity().getContent(),writer);
	    return writer.toString();
	}
	
	private HttpResponse execute(HttpUriRequest request) throws ClientProtocolException, IOException {
		request.addHeader("Authorization", "Bearer " + accessToken);
		return httpClient.execute(request);
	}

}
