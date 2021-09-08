package com.localbusinessplatform;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.localbusinessplatform.util.LbpUtil;



@SpringBootApplication
public class LocalBusinessPlatformApplication {

 

public static void main(String[] args) {
		LbpUtil lbpUtil = new LbpUtil();
		try {
			System.out.println(lbpUtil.calculateDistance("6823 N Terra Vista Dr, Peoria, IL", "242-23 91st Ave, Bellerose, NY"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpringApplication.run(LocalBusinessPlatformApplication.class, args);
	}
	
	

}
