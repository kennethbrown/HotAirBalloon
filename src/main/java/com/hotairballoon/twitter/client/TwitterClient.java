package com.hotairballoon.twitter.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class TwitterClient {
	
	private final HttpClient httpClient;
	
	public TwitterClient() {
		this.httpClient = HttpClients.createDefault();
	}
	
	public void teapot() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet("https://www.google.com/teapot");
		HttpResponse response = httpClient.execute(get);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
	}

}
