package com.localbusinessplatform.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.ItemRepository;
import com.localbusinessplatform.repository.MessageCenterRepository;
import com.localbusinessplatform.repository.OrderxRepository;
import com.localbusinessplatform.repository.StoreRepository;
import com.localbusinessplatform.repository.UserRepository;
import com.localbusinessplatform.response.OrderData;
import com.localbusinessplatform.response.SearchData;
import com.localbusinessplatform.response.UserData;
import com.localbusinessplatform.util.JwtUtil;
import com.localbusinessplatform.util.LbpUtil;
import com.mysql.jdbc.Constants;

@RestController
public class LoginController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	OrderxRepository orderxRepository;
	
	@Autowired
	MessageCenterRepository messageCenterRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserData userData;
	
	@Autowired
	SearchData searchData;
	
	@Autowired
	OrderData orderData;
	
	LbpUtil lbpUtil = new LbpUtil();
	
	@CrossOrigin
	@GetMapping(value = { "/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String home() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		System.out.println(jwtUtil.generateToken(user.getUsername()));
		return jwtUtil.generateToken(user.getUsername());
	}
	 

	@CrossOrigin
	@GetMapping(value = { "/home" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserData login() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		Store findStore = storeRepository.findByUserId(user.getId());
		List<Item> findItem = new ArrayList();
		
		if (findStore != null) {
			findItem = itemRepository.findByStoreId(findStore.getStoreId());
		}
		
		List<MessageCenter> findMessages = messageCenterRepository.findBySenderIdOrRecipientId(user.getId(), user.getId());
		
		userData = new UserData();
		if (user != null) {
			userData.setUser(user);
		}
		
		if (findStore != null) {
			userData.setStore(findStore);
		}
		
		if (findItem != null) {
			userData.setItem(findItem);
		}
		
		if (findMessages != null) {
			userData.setMessageCenter(findMessages);
		}
		
		return userData;
	}

	@CrossOrigin
	@PostMapping(value = { "/signup" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addUser(@RequestBody User user) throws Exception {
		user.setActive(false); // default
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		user.setRegistrationdate(dtf.format(localDate));
		userRepository.save(user);
		return LBPConstants.Status_OK;
	}

	@CrossOrigin
	@PostMapping(value = "/updateprofile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String profile(@RequestBody User user) throws Exception {
		User finduser = userRepository.findById(user.getId());
		
		if (finduser != null) { //finduser.isPresent()
			//User existingUser = finduser.get();
			userRepository.save(user);
			// existing user
		} else {
			// there is no user the repo with the given 'id'
		}
		return LBPConstants.Status_OK;
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
		store.setPublish(false); // default
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		store.setRegistrationDate(dtf.format(localDate));
		storeRepository.save(store);
		return LBPConstants.Status_OK;
	}
	
	
	@CrossOrigin
	@PostMapping(value = { "/additem" },consumes = { "application/json", "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Item addItem(@RequestPart("imageFile") MultipartFile file, @RequestPart("itemWrapper") ItemWrapper itemWrapper) throws Exception { //@RequestPart("imageFile") MultipartFile file
		Item item = new Item();
		item.setItemId(itemWrapper.getItemId());
		item.setItemName(itemWrapper.getItemName());
		item.setDescription(itemWrapper.getDescription());
		item.setCategory(itemWrapper.getCategory());
		item.setInventoryQty(itemWrapper.getInventoryQty());
		item.setPrice(itemWrapper.getPrice());
		item.setItemImage(file.getBytes());
		item.setStoreId(itemWrapper.getStoreId());
		itemRepository.save(item);
		
	    //Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
		//item.setItemImage(null);
		
		Item findItem = itemRepository.findByItemId(item.getItemId());
		
		if (findItem != null) {
			return findItem;
		}
		
		return null;
	}
	
	@CrossOrigin
	@DeleteMapping(value = {"/deleteitem/{itemId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteItem(@PathVariable(value = "itemId") int itemId) throws Exception {
		itemRepository.deleteByItemId(itemId);
		return LBPConstants.Status_OK;
	}
	
	@CrossOrigin
	@DeleteMapping(value = {"/deletestore/{storeId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteStore(@PathVariable(value = "storeId") int storeId) throws Exception {
		storeRepository.deleteByStoreId(storeId);
		return LBPConstants.Status_OK;
	}
	
	
	@CrossOrigin
	@PostMapping(value = { "/update" },consumes = { "application/json", "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Item updateItem(@RequestPart("imageFile") MultipartFile file, @RequestPart("itemWrapper") ItemWrapper itemWrapper) throws Exception { //@RequestPart("imageFile") MultipartFile file
		Item item = new Item();
		item.setItemName(itemWrapper.getItemName());
		item.setDescription(itemWrapper.getDescription());
		item.setCategory(itemWrapper.getCategory());
		item.setInventoryQty(itemWrapper.getInventoryQty());
		item.setPrice(itemWrapper.getPrice());
		item.setItemImage(file.getBytes());
		item.setStoreId(itemWrapper.getStoreId());
		itemRepository.save(item);
	    //Image img = new Image(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
		//item.setItemImage(null);
		Item findItem = itemRepository.findByItemId(item.getItemId());
		if (findItem != null) {
			return findItem;
		}
		return null;
	}
	
	@CrossOrigin
	@GetMapping(value = { "/searchitem" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchData> searchItem( @RequestParam(value = "itemName") String itemName,  @RequestParam(value = "category") String category) throws NumberFormatException, IOException {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		String origin = user.getAddress() + ", " + user.getCity() + ", " + user.getState() + " " + user.getZipcode() + ", " + user.getCountry();
				
		List<Item> findItems = new ArrayList();
		if (category.equals("")) {
			findItems = itemRepository.findByItemName(itemName);
		} else {
			findItems = itemRepository.findByItemNameAndCategory(itemName, category);
		}
		
		//List<Item> filteredItems = new ArrayList();
		List<SearchData> searchDataList = new ArrayList();
		
		for (Item item: findItems) {
			Store findStore = storeRepository.findByStoreId(item.getStoreId());
			String destination = findStore.getStreet() + ", " + findStore.getCity() + ", " + findStore.getState() + " " + findStore.getZipcode() + ", " + findStore.getCountry();
			Double distance = Double.parseDouble(lbpUtil.calculateDistance(origin, destination));
			if (distance <= Double.parseDouble(user.getSearchdistance())){
				//filteredItems.add(item);
				searchData.setItem(item);
				searchData.setStore(findStore);
				searchData.setDistance(distance.toString());
				searchDataList.add(new SearchData(searchData));
			}
		}
		
		return searchDataList;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/createorder" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createOrder(@RequestBody List<Orderx> orders) throws Exception {
		if (orders != null) {
			for (Orderx order: orders) {
				orderxRepository.save(order);
			}
		}
		return LBPConstants.Status_OK;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/shiporder" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String shipOrder(@RequestBody Orderx order) throws Exception {
		if (order != null) {
			orderxRepository.save(order);
		}
		
		return LBPConstants.Status_OK;
	}
	
	
	@CrossOrigin
	@GetMapping(value = { "/checkorder" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderData> checkOrder() throws Exception {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		
		Store findStore = storeRepository.findByUserId(user.getId());
		
		List<Orderx> findOrders = new ArrayList();

		if (findStore != null) {
			findOrders = orderxRepository.findByStoreId(findStore.getStoreId());
		}
		
		List<OrderData> orderDataList = new ArrayList();
		
		for (Orderx order: findOrders) {
			User customer = userRepository.findById(order.getCustomerId());
			Store store = storeRepository.findByStoreId(order.getStoreId());
			Item item = itemRepository.findByItemId(order.getItemId());
			
			orderData.setOrder(order);
			orderData.setCustomer(customer);
			orderData.setStore(store);
			orderData.setItem(item);
			
			orderDataList.add(new OrderData(orderData));
		}
		
		return orderDataList;
	}
	
	
	@CrossOrigin
	@GetMapping(value = { "/orderstatus" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OrderData> orderStatus() throws Exception {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = principal.getUser();
		
		List<Orderx> findOrders = new ArrayList();
		
		if (user != null) {
			findOrders = orderxRepository.findByCustomerId(user.getId());
		}
		
		List<OrderData> orderDataList = new ArrayList();
		
		for (Orderx order: findOrders) {
			User customer = userRepository.findById(order.getCustomerId());
			Store store = storeRepository.findByStoreId(order.getStoreId());
			Item item = itemRepository.findByItemId(order.getItemId());
			
			orderData.setOrder(order);
			orderData.setCustomer(customer);
			orderData.setStore(store);
			orderData.setItem(item);
			
			orderDataList.add(new OrderData(orderData));
		}
		
		return orderDataList;
	}
	
	@CrossOrigin
	@PostMapping(value = { "/createmessage" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createMessage(@RequestBody MessageCenter messageCenter) throws Exception {
		if (messageCenter != null) {
				messageCenterRepository.save(messageCenter);
		}
		return LBPConstants.Status_OK;
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
