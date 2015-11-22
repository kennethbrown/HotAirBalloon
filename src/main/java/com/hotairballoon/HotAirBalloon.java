package com.hotairballoon;

import com.hotairballoon.twitter.client.TwitterClient;

public class HotAirBalloon {
	
	public static void main(String[] args) throws Exception {
		TwitterClient client = new TwitterClient();
		
		System.out.println(client.search("@BernieSanders"));
	}

}
