package com.localbusinessplatform.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbusinessplatform.google.GoogleResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LbpUtil {

	public String calculateDistance(String origin, String destination) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin +"&destinations=" + destination+ "&units=imperial&key=AIzaSyAgVg1ZT-SpZ1GZma4SWjM7TlBz3kWVozg")
            .method("GET", null)
            .build();

        	Response response = client.newCall(request).execute();
			ObjectMapper mapper = new ObjectMapper();
			GoogleResponse googleResponse = mapper.readValue(response.body().string(), GoogleResponse .class);
			
			return googleResponse.getRows().get(0).getElements().get(0).getDistance().getText();
	}
	
}
