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
import com.localbusinessplatform.impl.UserPrincipal;
import com.localbusinessplatform.model.Item;
import com.localbusinessplatform.model.ItemWrapper;
import com.localbusinessplatform.model.Store;
import com.localbusinessplatform.model.User;
import com.localbusinessplatform.repository.ItemRepository;
import com.localbusinessplatform.repository.StoreRepository;
import com.localbusinessplatform.repository.UserRepository;
import com.localbusinessplatform.response.UserData;
import com.localbusinessplatform.util.JwtUtil;
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
	JwtUtil jwtUtil;
	
	@Autowired
	UserData userData;
	
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
		Optional<User> finduser = userRepository.findById(user.getId());
		if (finduser.isPresent()) {
			User existingUser = finduser.get();
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
