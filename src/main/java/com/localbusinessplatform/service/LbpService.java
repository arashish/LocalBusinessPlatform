package com.localbusinessplatform.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.localbusinessplatform.constant.LBPConstants;
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
import com.localbusinessplatform.util.JwtUtil;
import com.localbusinessplatform.util.LbpUtil;

@Service
public class LbpService {
	
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
	ReviewRepository reviewRepository;
		
	@Autowired
	UserData userData;
	
	LbpUtil lbpUtil = new LbpUtil();
	
	public UserData getUserInformation(User user) {
		List<Item> findItem = new ArrayList();
		List<MessageCenter> findMessages = new ArrayList();
		List<Review> findReviews = new ArrayList();
		List<Orderx> findOrders = new ArrayList();
		Store findStore = new Store();
	
		if (user.getUsertype().equals("seller")) { //if the user is a seller then it will retrieve the messages and reviews by using storeId
			findStore = storeRepository.findByUserId(user.getId());
			if (findStore != null) {
				findItem = itemRepository.findByStoreId(findStore.getStoreId());
				findMessages = messageCenterRepository.findBySenderUsernameOrRecipientUsername(findStore.getEmail(), findStore.getEmail());
				findReviews = reviewRepository.findByrevieweeUsername(findStore.getEmail());
				findOrders = orderxRepository.findByStoreId(findStore.getStoreId());
			} 
		} else {
			findMessages = messageCenterRepository.findBySenderUsernameOrRecipientUsername(user.getUsername(), user.getUsername());
			findReviews = reviewRepository.findByrevieweeUsername(user.getUsername());
			findOrders = orderxRepository.findByCustomerId(user.getId());
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
		
		if (findMessages != null) {
			userData.setMessageCenter(findMessages);
		}
		
		if (findOrders != null) {
			userData.setOrder(findOrders);
		}
		
		if (findReviews !=null) {
			userData.setReview(findReviews);
		}
		
		return userData;
	}
	
	public String addUser(User user) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		user.setRegistrationdate(dtf.format(localDate));
		userRepository.save(user);
		return LBPConstants.Status_OK;
	}

	public String updateProfile(User user) {
		User findUser = userRepository.findById(user.getId());
		if (findUser != null) { //finduser.isPresent()
			Store findStore = storeRepository.findByUserId(findUser.getId());
			if (user.getUsertype().equals("buyer") && findStore != null) {
				findStore.setPublish(false);
				storeRepository.save(findStore);
			}
			userRepository.save(user);
			// existing user
		} else {
			// there is no user the repo with the given 'id'
		}
		return LBPConstants.Status_OK;
	}

	public String createStore(Store store) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		store.setRegistrationDate(dtf.format(localDate));
		storeRepository.save(store);
		return LBPConstants.Status_OK;
	}
	
	public Item addItem(MultipartFile file, ItemWrapper itemWrapper) throws IOException {
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
		Item findItem = itemRepository.findByItemId(item.getItemId());
//		if (findItem != null) {
//			return findItem;
//		}
		return findItem;
	}
	
	public String deleteItem(int itemId) {
		itemRepository.deleteByItemId(itemId);
		return LBPConstants.Status_OK;
	}
	
	public String deleteStore(int storeId) {
		storeRepository.deleteByStoreId(storeId);
		itemRepository.deleteByStoreId(storeId); //deletes all the items that are sold by that deleted store
		return LBPConstants.Status_OK;
	}

	public Item updateItem(MultipartFile file, ItemWrapper itemWrapper) throws IOException {
		Item item = new Item();
		item.setItemName(itemWrapper.getItemName());
		item.setDescription(itemWrapper.getDescription());
		item.setCategory(itemWrapper.getCategory());
		item.setInventoryQty(itemWrapper.getInventoryQty());
		item.setPrice(itemWrapper.getPrice());
		item.setItemImage(file.getBytes());
		item.setStoreId(itemWrapper.getStoreId());
		itemRepository.save(item);
		Item findItem = itemRepository.findByItemId(item.getItemId());
		return findItem;
	}
	
	public List<SearchData> searchItem(User user, String itemName, String category) throws NumberFormatException, IOException{
		String origin = user.getAddress() + ", " + user.getCity() + ", " + user.getState() + " " + user.getZipcode() + ", " + user.getCountry();
		List<Item> findItems = new ArrayList();
		if (category.equals("All Category") && !itemName.equals("")) {
			findItems = itemRepository.findByItemNameOrDescriptionContains(itemName,itemName);
		}else if(category.equals("All Category") && itemName.equals("") ){
			findItems = itemRepository.findAll();
		}else if (!category.equals("All Category") && itemName.equals("")) {
			findItems = itemRepository.findByCategory(category);
		}else {
			findItems = itemRepository.findByItemNameOrDescriptionAndCategoryContains(itemName, itemName, category);
		}
	
		List<SearchData> searchDataList = new ArrayList();
		SearchData searchData = new SearchData();
		
		for (Item item: findItems) {
			Store findStore = storeRepository.findByStoreId(item.getStoreId());
			if (findStore.getPublish()) {
				String destination = findStore.getStreet() + ", " + findStore.getCity() + ", " + findStore.getState() + " " + findStore.getZipcode() + ", " + findStore.getCountry();
				Double distance = Double.parseDouble(lbpUtil.calculateDistance(origin, destination));
				
				List<Review> review = reviewRepository.findByrevieweeUsername(findStore.getEmail());
				
				if (distance <= Double.parseDouble(user.getSearchdistance())){
					//filteredItems.add(item);
					searchData.setItem(item);
					searchData.setStore(findStore);
					searchData.setDistance(distance.toString());
					searchData.setReview(review);
					searchDataList.add(new SearchData(searchData));
				}
			}
		}
		
		return searchDataList;
	}

	public String createOrder(List<Orderx> orders) {
		if (orders != null) {
			for (Orderx order: orders) {
				orderxRepository.save(order);
			}
			return LBPConstants.Status_OK;
		} else {
			return LBPConstants.Status_BadRequest;
		}
	}
	
	public String shipOrder(Orderx order) throws Exception {
		if (order != null) {
			Item item = itemRepository.findByItemId(order.getItemId());
			int newInventory = item.getInventoryQty() - order.getOrderQty();
			if (newInventory >= 0) {
				item.setInventoryQty(newInventory);
				itemRepository.save(item);
				orderxRepository.save(order);
			} else {
				throw new Exception(LBPConstants.Status_NotAcceptable , new Error());
			}
		}
		
		return LBPConstants.Status_OK;
	}

	public List<OrderData> checkOrder(User user) {
		Store findStore = storeRepository.findByUserId(user.getId());
		
		List<Orderx> findOrders = new ArrayList();
		OrderData orderData = new OrderData();
		List<OrderData> orderDataList = new ArrayList();

		if (findStore != null) {
			findOrders = orderxRepository.findByStoreId(findStore.getStoreId());
			//findOrders.sort(Comparator.comparing((Orderx od) -> (od.getOrderDate())).reversed()); //sort the orders by date
			lbpUtil.sortArrayList(findOrders);
			for (Orderx order: findOrders) {
				User customer = userRepository.findById(order.getCustomerId());
				Store store = storeRepository.findByStoreId(order.getStoreId());
				Item item = itemRepository.findByItemId(order.getItemId());
				List<Review> review = reviewRepository.findByrevieweeUsername(customer.getUsername());
				
				orderData.setOrder(order);
				orderData.setCustomer(customer);
				orderData.setStore(store);
				orderData.setItem(item);
				orderData.setReview(review);
				
				orderDataList.add(new OrderData(orderData));
			}
		}
		return orderDataList;
	}
	
	public List<OrderData> orderStatus(User user){
		List<Orderx> findOrders = new ArrayList();
		OrderData orderData = new OrderData();
		
		if (user != null) {
			findOrders = orderxRepository.findByCustomerId(user.getId());
		}
		
		List<OrderData> orderDataList = new ArrayList();
		lbpUtil.sortArrayList(findOrders);
		for (Orderx order: findOrders) {
			User customer = userRepository.findById(order.getCustomerId());
			Store store = storeRepository.findByStoreId(order.getStoreId());
			Item item = itemRepository.findByItemId(order.getItemId());
			List<Review> review = reviewRepository.findByrevieweeUsername(store.getEmail()); //was customer.getUsername() //buyer wants to see the info of the seller in the review
			
			orderData.setOrder(order);
			orderData.setCustomer(customer);
			orderData.setStore(store);
			orderData.setItem(item);
			orderData.setReview(review);
			
			orderDataList.add(new OrderData(orderData));
		}
		
		return orderDataList;
	}

	public List<MessageCenter> updateMessage (MessageCenter messageCenter) {
		List<MessageCenter> findMessages = new ArrayList();
		if (messageCenter != null) {
			messageCenterRepository.save(messageCenter);
			//Retrieving the updated messages
			UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = principal.getUser();
			Store findStore = new Store();
			findMessages = new ArrayList();
			
			if (user.getUsertype().equals("seller")) {
				findStore = storeRepository.findByUserId(user.getId());
				if (findStore != null) {
					findMessages = messageCenterRepository.findBySenderUsernameOrRecipientUsername(findStore.getEmail(), findStore.getEmail());
				} 
			} else {
				findMessages = messageCenterRepository.findBySenderUsernameOrRecipientUsername(user.getUsername(), user.getUsername());
			}
		}
		return findMessages;
	}

	public List<MessageCenter> deleteMessage(MessageCenter messageCenter){
		List<MessageCenter> findMessages = new ArrayList();
		if (messageCenter != null) {
			messageCenterRepository.deleteByMessageId(messageCenter.getMessageId());
			//Retrieving the updated messages
			UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = principal.getUser();
			Store findStore = new Store();
			findMessages = new ArrayList();
			
			if (user.getUsertype().equals("seller")) {
				findStore = storeRepository.findByUserId(user.getId());
				if (findStore != null) {
					findMessages = messageCenterRepository.findBySenderUsernameOrRecipientUsername(findStore.getEmail(), findStore.getEmail());
				} 
			} else {
				findMessages = messageCenterRepository.findBySenderUsernameOrRecipientUsername(user.getUsername(), user.getUsername());
			}
		}
		return findMessages;
	}
	
	public String createReview(Review review) {
		if (review != null) {
			reviewRepository.save(review);
			return LBPConstants.Status_OK;
		} else {
			return LBPConstants.Status_BadRequest;
		}
	}
	
}
