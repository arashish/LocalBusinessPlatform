package com.localbusinessplatform.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.localbusinessplatform.constant.LBPConstants;
import com.localbusinessplatform.google.GoogleResponse;
import com.localbusinessplatform.impl.UserPrincipal;
import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.ItemWrapper;
import com.localbusinessplatform.model.MessageCenter;
import com.localbusinessplatform.model.Orderx;
import com.localbusinessplatform.model.Review;
import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.ItemRepository;
import com.localbusinessplatform.repository.MessageCenterRepository;
import com.localbusinessplatform.repository.OrderxRepository;
import com.localbusinessplatform.repository.ReviewRepository;
import com.localbusinessplatform.repository.StoreRepository;
import com.localbusinessplatform.repository.UserRepository;
import com.localbusinessplatform.response.OrderData;
import com.localbusinessplatform.response.SearchData;
import com.localbusinessplatform.response.UserData;
import com.localbusinessplatform.service.LbpService;
import com.localbusinessplatform.util.JwtUtil;
import com.localbusinessplatform.util.LbpUtil;
import com.mysql.jdbc.Constants;

@RestController
public class LbpController {
	
	@Autowired
	LbpService lbpService;

	@Autowired
	JwtUtil jwtUtil;
	
	@CrossOrigin
	@GetMapping(value = { "/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String login() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		System.out.println(jwtUtil.generateToken(user.getUsername()));
		return jwtUtil.generateToken(user.getUsername());
	}
	 

	@CrossOrigin
	@GetMapping(value = { "/home" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserData home() throws Exception  {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		UserData userData= lbpService.getUserInformation(user);
		return userData;
	}

	@CrossOrigin
	@PostMapping(value = { "/signup" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@RequestBody User user) throws Exception {
		String status = lbpService.addUser(user);
		return status;
	}

	@CrossOrigin
	@PostMapping(value = "/updateprofile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateProfile(@RequestBody User user) throws Exception {
		String status = lbpService.updateProfile(user);
		return status;
	}
	
	@CrossOrigin
	@GetMapping(value = { "/logoutsession" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
		return LBPConstants.Status_OK;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/createstore" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createStore(@RequestBody Store store) throws Exception {
		String status = lbpService.createStore(store);
		return status;
	}
	
	
	@CrossOrigin
	@PostMapping(value = { "/additem" },consumes = { "application/json", "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Item addItem(@RequestPart("imageFile") MultipartFile file, @RequestPart("itemWrapper") ItemWrapper itemWrapper) throws Exception { //@RequestPart("imageFile") MultipartFile file
		Item item = lbpService.addItem(file, itemWrapper);
		return item;
	}
	
	@CrossOrigin
	@DeleteMapping(value = {"/deleteitem/{itemId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteItem(@PathVariable(value = "itemId") int itemId) throws Exception {
		String status = lbpService.deleteItem(itemId);
		return status;
	}
	
	@CrossOrigin
	@DeleteMapping(value = {"/deletestore/{storeId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteStore(@PathVariable(value = "storeId") int storeId) throws Exception {
		String status = lbpService.deleteStore(storeId);
		return status;
	}
	
	
	@CrossOrigin
	@PostMapping(value = { "/update" },consumes = { "application/json", "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Item updateItem(@RequestPart("imageFile") MultipartFile file, @RequestPart("itemWrapper") ItemWrapper itemWrapper) throws Exception { //@RequestPart("imageFile") MultipartFile file
		Item item = lbpService.updateItem(file, itemWrapper);
		return item;
	}
	
	@CrossOrigin
	@GetMapping(value = { "/searchitem" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchData> searchItem( @RequestParam(value = "itemName") String itemName,  @RequestParam(value = "category") String category) throws NumberFormatException, IOException {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		List<SearchData> searchData = lbpService.searchItem(user, itemName, category);
		return searchData;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/createorder" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createOrder(@RequestBody List<Orderx> orders) throws Exception {
		String status = lbpService.createOrder(orders);
		return status;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/shiporder" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String shipOrder(@RequestBody Orderx order) throws Exception {
		String status = lbpService.shipOrder(order);
		return status;
	}
	
	
	@CrossOrigin
	@GetMapping(value = { "/checkorder" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderData> checkOrder() throws Exception {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		List<OrderData> orderData = lbpService.checkOrder(user);
		return orderData;
	}
	
	
	@CrossOrigin
	@GetMapping(value = { "/orderstatus" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderData> orderStatus() throws Exception {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		List<OrderData> orderData = lbpService.orderStatus(user);
		return orderData;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/updatemessage" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MessageCenter> updateMessage(@RequestBody MessageCenter messageCenter) throws Exception {
		List<MessageCenter> messageCenterList = lbpService.updateMessage(messageCenter);
		return messageCenterList;
	} 
	
	@CrossOrigin
	@PostMapping(value = { "/deletemessage" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MessageCenter> deleteMessage(@RequestBody MessageCenter messageCenter) throws Exception {
		List<MessageCenter> messageCenterList = lbpService.deleteMessage(messageCenter);
		return messageCenterList;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/createreview" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createReview(@RequestBody Review review) throws Exception {
		String status = lbpService.createReview(review);
		return status;
	}
	
}
