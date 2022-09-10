package com.localbusinessplatform.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Deflater;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbusinessplatform.google.GoogleResponse;
import com.localbusinessplatform.model.Orderx;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LbpUtil {

	public String calculateDistance(String origin, String destination) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin +"&destinations=" + destination+ "&units=imperial&key=<enter_key_here>")
            .method("GET", null)
            .build();

        	Response response = client.newCall(request).execute();
			ObjectMapper mapper = new ObjectMapper();
			GoogleResponse googleResponse = mapper.readValue(response.body().string(), GoogleResponse .class);
			
			String extractDistance = googleResponse.getRows().get(0).getElements().get(0).getDistance().getText();
			
			String newDistance = "0";
			if (extractDistance.contains("mi")) {
				newDistance = extractDistance.replaceAll(" mi", "");
			} else {
				newDistance = "0"; //for any units besides miles
			}
			
			return newDistance;
	}
	
	public void sortArrayList(List<Orderx> order){
		//Sort String Date
		Collections.sort(order, new Comparator<Orderx>() {
		DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		@Override
		public int compare(Orderx o1, Orderx o2) {
		try {
		  return f.parse(o1.getOrderDate()).compareTo(f.parse(o2.getOrderDate()));
		    } catch (ParseException e) {
		      throw new IllegalArgumentException(e);
		    }
		}
		}.reversed());
	}
	
    // compress the image bytes before storing it in the database
    public byte[] compressFile(byte[] image) {
        Deflater compress = new Deflater();
        compress.setInput(image);
        compress.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(image.length);
        byte[] bufferSize = new byte[1024];
        while (!compress.finished()) {
            int ct = compress.deflate(bufferSize);
            byteArrayOutputStream.write(bufferSize, 0, ct);
        }
        try {
        	byteArrayOutputStream.close();
        } catch (IOException e) {
        }
        return byteArrayOutputStream.toByteArray();

    }
	
}
