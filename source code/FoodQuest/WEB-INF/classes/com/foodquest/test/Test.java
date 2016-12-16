package com.foodquest.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.models.DistributorBean;
import com.foodquest.models.DistributorBean.DistributorStatus;
import com.foodquest.models.ImageBean;
import com.foodquest.models.LocationBean;
import com.foodquest.models.OrderBean;
import com.foodquest.models.OrderBean.OrderStatus;
import com.foodquest.models.OrderItem;
import com.foodquest.models.Pair;
import com.foodquest.models.RecipeBean;
import com.foodquest.models.RecipeBean.RecipeDistributorStatus;
import com.foodquest.models.RecipeBean.RecipeStatus;
import com.foodquest.models.UserBean;
import com.foodquest.models.UserBean.AccountStatus;
import com.foodquest.models.UserBean.Gender;
import com.foodquest.models.UserBean.UserType;
import com.foodquest.repository.IOrderRepository;
import com.foodquest.repository.IRecipeRepository;
import com.foodquest.repository.impl.OrderRepositoryImpl;
import com.foodquest.repository.impl.RecipeRepositoryImpl;
import com.foodquest.services.IDistributorManager;
import com.foodquest.services.IOrderManager;
import com.foodquest.services.IRecipeManager;
import com.foodquest.services.IRecipeTypeManager;
import com.foodquest.services.IUserManager;
import com.foodquest.services.impl.DistributorManagerImpl;
import com.foodquest.services.impl.OrderManagerImpl;
import com.foodquest.services.impl.RecipeManagerImpl;
import com.foodquest.services.impl.RecipeTypeManagerImpl;
import com.foodquest.services.impl.UserManagerImpl;
import com.foodquest.utils.lang.AppException;

public class Test {
	private static IUserManager userManager;
	private static IRecipeTypeManager recipeTypeManager;
	private static IDistributorManager distributorManager;
	private static IRecipeManager recipeManager;
	private static IOrderManager orderManager;

	static {
		userManager = UserManagerImpl.getInstance();
		recipeTypeManager = RecipeTypeManagerImpl.getInstance();
		distributorManager = DistributorManagerImpl.getInstance();
		recipeManager = RecipeManagerImpl.getInstance();
		orderManager = OrderManagerImpl.getInstance();
	}

	public static UserBean getCustomer1() {
		UserBean bean = new UserBean();
		bean.setFirstName("Chris");
		bean.setLastName("Dixon");
		bean.setGender(Gender.MALE);
		bean.setEmail("chrisdixon@gmail.com");
		bean.setPassword("hello@123");
		bean.setPhone("+3128007014");
		bean.setType(UserType.CUSTOMER);

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abcd.com");
		image.setPublicId("lsndcjwe5");
		image.setSecureUrl("wwws.abcd.com");
		bean.setImage(image);

		LocationBean location = new LocationBean();
		location.setAddressLine("2851, S. King Drive");
		location.setCity("Chicago");
		location.setState("IL");
		location.setCountry("USA");
		location.setZipcode("60616");
		bean.setLocation(location);

		return bean;
	}

	public static UserBean getCustomer2() {
		UserBean bean = new UserBean();
		bean.setFirstName("Catalyn");
		bean.setLastName("Stark");
		bean.setGender(Gender.FEMALE);
		bean.setEmail("catie@gmail.com");
		bean.setPassword("catyln@123");
		bean.setPhone("+3128007015");
		bean.setType(UserType.CUSTOMER);

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abcde.com");
		image.setPublicId("wefew");
		image.setSecureUrl("wwws.abcde.com");

		bean.setImage(image);

		LocationBean location = new LocationBean();
		location.setAddressLine("2851, S. King Drive");
		location.setCity("Chicago");
		location.setState("IL");
		location.setCountry("USA");
		location.setZipcode("60616");

		bean.setLocation(location);

		return bean;
	}

	public static UserBean getCustomer3() {
		UserBean bean = new UserBean();
		bean.setFirstName("Shaun");
		bean.setLastName("Tight");
		bean.setGender(Gender.MALE);
		bean.setEmail("shaun@gmail.com");
		bean.setPassword("shaun@123");
		bean.setPhone("+3128007015=6");
		bean.setType(UserType.CUSTOMER);

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abde.com");
		image.setPublicId("wefjcewew");
		image.setSecureUrl("wwws.abde.com");
		bean.setImage(image);

		LocationBean location = new LocationBean();
		location.setAddressLine("2851, S. King Drive");
		location.setCity("Chicago");
		location.setState("IL");
		location.setCountry("USA");
		location.setZipcode("60616");
		bean.setLocation(location);

		return bean;
	}

	public static CategoryBean getVegCategory() {
		CategoryBean category = new CategoryBean();
		category.setName("VEG");
		category.setDescription("Vegetarian foods");

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abcd.com");
		image.setPublicId("lsndcjwe5");
		image.setSecureUrl("wwws.abcd.com");

		category.setImage(image);

		return category;
	}

	public static CategoryBean getNonVegCategory() {
		CategoryBean category = new CategoryBean();
		category.setName("NON VEG");
		category.setDescription("Non Vegetarian foods");

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.sdf.com");
		image.setPublicId("asd");
		image.setSecureUrl("wwws.dcds.com");

		category.setImage(image);

		return category;
	}

	public static CusineTypeBean getCusine1() {
		CusineTypeBean cusine = new CusineTypeBean();
		cusine.setName("Italian");
		cusine.setDescription("Italian foods");

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.italian.com");
		image.setPublicId("wedew");
		image.setSecureUrl("wwws.italian.com");

		cusine.setImage(image);

		return cusine;
	}

	public static CusineTypeBean getCusine2() {
		CusineTypeBean cusine = new CusineTypeBean();
		cusine.setName("Chinese");
		cusine.setDescription("Chinese foods");

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.Chinese.com");
		image.setPublicId("wedew");
		image.setSecureUrl("wwws.Chinese.com");

		cusine.setImage(image);

		return cusine;
	}

	public static UserBean getDistributorUser1() {
		UserBean bean = new UserBean();
		bean.setFirstName("Dorris");
		bean.setLastName("Dalvic");
		bean.setGender(Gender.MALE);
		bean.setEmail("dorris@gmail.com");
		bean.setPassword("dorris@123");
		bean.setPhone("+3128007019");
		bean.setType(UserType.DISTRIBUTOR);

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abcdd.com");
		image.setPublicId("lsndcjwe5d");
		image.setSecureUrl("wwws.abcdd.com");

		bean.setImage(image);

		return bean;
	}

	public static UserBean getDistributorUser2() {
		UserBean bean = new UserBean();
		bean.setFirstName("Kris");
		bean.setLastName("Paul");
		bean.setGender(Gender.MALE);
		bean.setEmail("kris@gmail.com");
		bean.setPassword("kris@123");
		bean.setPhone("+312800709");
		bean.setType(UserType.DISTRIBUTOR);

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abcdd.com");
		image.setPublicId("lsndcjwe5d");
		image.setSecureUrl("wwws.abcdd.com");

		bean.setImage(image);

		return bean;
	}

	public static DistributorBean getDistributor() {
		DistributorBean distributor = new DistributorBean();
		distributor.setName("Cargo Distributors");
		distributor.setPhone("+312866457");
		distributor.setWebsiteUrl("www.cargo.com");

		ImageBean image = new ImageBean();
		image.setImageUrl("wwww.abde.com");
		image.setPublicId("wefjcewew");
		image.setSecureUrl("wwws.abde.com");

		distributor.setImage(image);

		LocationBean location = new LocationBean();
		location.setAddressLine("430 S Michigan Ave");
		location.setCity("Chicago");
		location.setState("IL");
		location.setCountry("USA");
		location.setZipcode("60605");

		distributor.setLocation(location);

		return distributor;
	}

	public static RecipeBean getRecipe() throws AppException {
		RecipeBean bean = new RecipeBean();
		CategoryBean category = recipeTypeManager.findCategoryByName("VEG");
		CusineTypeBean cusine = recipeTypeManager.findCusineTypeByName("Italian");

		bean.setCategory(category);
		bean.setCusineType(cusine);
		bean.setName("Pasta");

		ImageBean image = new ImageBean();
		image.setImageUrl("http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg");
		image.setPublicId("wefjcewew");
		image.setSecureUrl("wwws.abde.com");

		bean.setImage(image);
		bean.setDescription("Pasta !!!");
		bean.setOwner(userManager.findByEmail(getCustomer3().getEmail()));
		bean.setPrice(25d);
		bean.setDateOfServing(new Date(2016, 12, 14).getTime());
		bean.setTotalServings(10);

		return bean;
	}

	public static RecipeBean getRecipe2() throws AppException {
		RecipeBean bean = new RecipeBean();
		CategoryBean category = recipeTypeManager.findCategoryByName("NON VEG");
		CusineTypeBean cusine = recipeTypeManager.findCusineTypeByName("Italian");

		bean.setCategory(category);
		bean.setCusineType(cusine);
		bean.setName("Chicken Pasta");

		ImageBean image = new ImageBean();
		image.setImageUrl("http://res.cloudinary.com/di2fxuii1/image/upload/v1480477038/j3nzrr3t7lghhpeqpsul.jpg");
		image.setPublicId("wefjcewew");
		image.setSecureUrl("wwws.abde.com");

		bean.setImage(image);
		bean.setDescription("Pasta !!!");
		bean.setOwner(userManager.findByEmail(getCustomer3().getEmail()));
		bean.setPrice(15d);
		bean.setDateOfServing(new Date(2016, 12, 15).getTime());
		bean.setTotalServings(10);

		return bean;
	}

	public static void createUser() throws AppException {
		UserBean customer = getCustomer1();
		Integer id = userManager.create(customer);
		customer.setId(id);
		System.out.println("Is created customer == customer read from DB by Id ? "
				+ (customer.equals(userManager.findById(customer.getId()))));
		System.out.println("Is created customer == customer read from DB by email ? "
				+ (customer.equals(userManager.findByEmail(customer.getEmail()))));
		System.out.println("Update customer first name, email and location.");
		customer.setFirstName("Christian");
		customer.setEmail("cristian@gmail.com");
		System.out.println("Changes not updated to DB. So Is customer == customer read from DB by Id  false? "
				+ (customer.equals(userManager.findById(customer.getId()))));
		System.out.println("Update customer");
		customer.getLocation().setAddressLine("Barry Plaza, 3025 N Pulaski Rd");
		customer.getLocation().setZipcode("60641");
		userManager.update(customer);
		System.out.println("Is updated customer == customer read from DB by Id ? "
				+ (customer.equals(userManager.findById(customer.getId()))));

		userManager.create(getCustomer2());
	}

	public static void findAll() throws AppException {
		System.out.println("Find all active users");
		for (UserBean bean : userManager.findAll(AccountStatus.ACTIVE)) {
			System.out.println(bean);
		}
		System.out.println("Disable Catalyn account and search only active users");
		UserBean user = userManager.findByEmail(getCustomer2().getEmail());
		user.setStatus(AccountStatus.DISABLED);
		userManager.update(user);
		System.out.println("Find all active users");
		for (UserBean bean : userManager.findAll(AccountStatus.ACTIVE)) {
			System.out.println(bean);
		}
		System.out.println("Find all users");
		for (UserBean bean : userManager.findAll(null)) {
			System.out.println(bean);
		}
		user = userManager.findByEmail(getCustomer2().getEmail());
		user.setStatus(AccountStatus.ACTIVE);
		userManager.update(user);
	}

	public static void authenticateAnddelete() throws AppException {
		int id = userManager.create(getCustomer3());
		System.out
				.println("Invalid credentials " + userManager.authenticateByEmail("nj", getCustomer3().getPassword()));
		System.out
				.println("Invalid credentials " + userManager.authenticateByEmail(getCustomer3().getEmail(), "asdsf"));
		System.out.println("Valid credentials "
				+ userManager.authenticateByEmail(getCustomer3().getEmail(), getCustomer3().getPassword()));
		UserBean bean = userManager.findById(id);
		bean.setPassword("123");
		userManager.update(bean);
		System.out.println(
				"Valid credentials " + userManager.authenticateByEmail(getCustomer3().getEmail(), bean.getPassword()));
		System.out.println("delete");
		userManager.delete(id);
		System.out.println(userManager.findById(id));
	}

	public static UserBean findById(Integer id) throws AppException {
		return userManager.findById(id);
	}

	public static void createCategory() throws AppException {
		CategoryBean category = getVegCategory();
		int id = recipeTypeManager.createCategory(category);
		category.setId(id);
		System.out.println("Is created category == category read from DB by id ? "
				+ category.equals(recipeTypeManager.findCategoryById(id)));
		System.out.println("Is created category == category read from DB by name ? "
				+ category.equals(recipeTypeManager.findCategoryByName(category.getName())));
		recipeTypeManager.createCategory(getNonVegCategory());
	}

	public static void updateCategory() throws AppException {
		System.out.println("All categories");
		for (CategoryBean bean : recipeTypeManager.findAllCategories()) {
			System.out.println(bean);
		}
		CategoryBean category = recipeTypeManager.findCategoryByName("VEG");
		category.setDescription("Enjoy vegetarian foods");
		category.getImage().setImageUrl("www.veg.com");
		System.out.println("Updating category");
		recipeTypeManager.updateCategory(category);
		System.out.println("All categories");
		for (CategoryBean bean : recipeTypeManager.findAllCategories()) {
			System.out.println(bean);
		}
	}

	public static void testCusineType() throws AppException {
		CusineTypeBean cusine1 = getCusine1();
		CusineTypeBean cusine2 = getCusine2();
		int id = recipeTypeManager.createCusineType(cusine1);
		cusine1.setId(id);
		id = recipeTypeManager.createCusineType(cusine2);
		cusine2.setId(id);
		System.out.println("Is cusine 1 === cusine read from DB by id ? "
				+ cusine1.equals(recipeTypeManager.findCusineTypeById(cusine1.getId())));
		System.out.println("Is cusine 1 === cusine read from DB by name ? "
				+ cusine1.equals(recipeTypeManager.findCategoryByName(cusine1.getName())));
		System.out.println("Unkown id. Is null returned? " + recipeTypeManager.findCategoryById(Integer.MAX_VALUE));
		System.out.println("Unkown id. Is null returned ? " + recipeTypeManager.findCategoryByName("UNKOWN"));
		System.out.println("All cusines");
		for (CusineTypeBean cusine : recipeTypeManager.findAllCusines()) {
			System.out.println(cusine);
		}
		System.out.println("Updating cusine 1");
		cusine1.setName("Italian");
		recipeTypeManager.updateCusineType(cusine1);
		System.out.println("All cusines");
		for (CusineTypeBean cusine : recipeTypeManager.findAllCusines()) {
			System.out.println(cusine);
		}
	}

	public static void testDistributor() throws AppException {
		UserBean user1 = getDistributorUser1();
		UserBean user2 = getDistributorUser2();
		DistributorBean distributor = getDistributor();
		Pair<Integer, Integer> pair = distributorManager.createDistributorAndUser(distributor, user1);
		user1.setId(pair.getRight());
		distributor.setId(pair.getLeft());

		pair = distributorManager.createDistributorAndUser(distributor, user2);
		user2.setId(pair.getRight());

		System.out.println("Verify primary distributor :: " + userManager.findById(user1.getId()));
		System.out.println("verify secondary distributor :: " + userManager.findById(user2.getId()));

		System.out.println("Display all distributors of pending status");
		for (DistributorBean db : distributorManager.findAll(DistributorStatus.PENDING, null)) {
			System.out.println(db);
		}
		distributor = distributorManager.findByName(getDistributor().getName());
		distributor.setStatus(DistributorStatus.VERIFIED);
		distributorManager.update(distributor);
		for (DistributorBean db : distributorManager.findAll(DistributorStatus.VERIFIED, "gffh")) {
			System.out.println(db);
		}
	}

	public static void testRecipe() throws AppException {
		RecipeBean recipe = getRecipe();
		int id = recipeManager.create(recipe);
		recipe.setId(id);
		RecipeBean _recipe = recipeManager.findById(recipe.getId());
		System.out.println(_recipe);
		System.out.println("Updating recipe ");
		recipe.setName("Cheese Pasta");
		recipe.setTotalServings(20);
		recipeManager.update(recipe);
		for (RecipeBean bean : recipeManager.findAll(RecipeStatus.PENDING, null)) {
			System.out.println(bean);
		}
		System.out.println("Approve recipe");
		recipe.setStatus(RecipeStatus.PUBLISHED);
		recipeManager.update(recipe);
		for (RecipeBean bean : recipeManager.findAll(RecipeStatus.PUBLISHED, null)) {
			System.out.println(bean);
		}
		System.out.println("Assign distributor");
		recipeManager.assignDistributor(recipe, 5);
		for (RecipeBean bean : recipeManager.findAll(RecipeStatus.PUBLISHED, null)) {
			System.out.println(bean);
		}
		recipe.setTotalBookings(5);
		recipeManager.update(recipe);
		for (RecipeBean bean : recipeManager.findAll(RecipeStatus.PUBLISHED, null)) {
			System.out.println(bean);
		}
		System.out.println("Recipe dispatch");
		recipe.setRecipeDistributorStatus(RecipeDistributorStatus.DISPATCHED);
		System.out.println("Display all Completed recipes");
		recipeManager.update(recipe);
		for (RecipeBean bean : recipeManager.findAll(RecipeStatus.COMPLETED, null)) {
			System.out.println(bean);
		}
		System.out.println("Multiple statuses query");
		List<RecipeStatus> statuses = new ArrayList<>();
		statuses.add(RecipeStatus.PUBLISHED);
		statuses.add(RecipeStatus.COMPLETED);
		for (RecipeBean bean : recipeManager.findAll(statuses, "lvmdlv")) {
			System.out.println(bean);
		}
		System.out.println("Single status search");
		for (RecipeBean bean : recipeManager.findAll(RecipeStatus.COMPLETED, "chees")) {
			System.out.println(bean);
		}
		recipeManager.create(getRecipe2());
	}

	public static void testOrder() throws AppException {
		RecipeBean recipe1 = getRecipe();
		recipe1.setDateOfServing(new Date(2016, 12, 20).getTime());
		recipe1.setTotalServings(10);
		RecipeBean recipe2 = getRecipe2();
		recipe2.setDateOfServing(new Date(2016, 12, 24).getTime());
		recipe2.setTotalServings(12);

		recipe1.setId(recipeManager.create(recipe1));
		recipe2.setId(recipeManager.create(recipe2));

		recipe1 = recipeManager.findById(recipe1.getId());
		recipe2 = recipeManager.findById(recipe2.getId());
		System.out.println("Hello "+recipe1);
		recipe1.setStatus(RecipeStatus.PUBLISHED);
		recipe1.setStatus(RecipeStatus.PUBLISHED);

		recipeManager.update(recipe1);
		recipeManager.update(recipe2);

		recipe1 = recipeManager.findById(recipe1.getId());
		recipe2 = recipeManager.findById(recipe2.getId());

		OrderItem item1 = new OrderItem(recipe1, 2);
		OrderItem item2 = new OrderItem(recipe2, 3);

		OrderBean orderBean = new OrderBean();
		orderBean.getItems().add(item1);
		orderBean.getItems().add(item2);
		orderBean.setCustomer(userManager.findByEmail(getCustomer2().getEmail()));

		orderBean.setId(orderManager.create(orderBean));

		System.out.println("finding order by id");
		System.out.println(orderManager.findById(orderBean.getId()));

		orderManager.updateOrderItem(orderBean.getId(), recipe1.getId(), true, 4);
		System.out.println("Displaying orders");
		for (OrderBean _order : orderManager.findAll(OrderStatus.IN_CART)) {
			System.out.println(_order);
		}
		orderBean = orderManager.findById(orderBean.getId());
		orderManager.placeOrder(orderBean.getId(), null);
		for (OrderBean _order : orderManager.findAll(OrderStatus.CONFIRMED)) {
			System.out.println(_order);
		}
	}
	
	public static UserBean getAdmin() {
        UserBean bean = getCustomer1();
        bean.setFirstName("admin");
        bean.setLastName("admin");
        bean.setEmail("admin@admin.com");
        bean.setPassword("admin");
        bean.setType(UserType.ADMIN);
        return bean;
    }
	
	public static void createAdmin() throws AppException {
		if(userManager.findByEmail(getAdmin().getEmail()) == null) {
			userManager.create(getAdmin());
		}
	}
	
	public static void main(String[] args) throws AppException {
//		try {
//			createUser();
//			findAll();
//			authenticateAnddelete();
//			createCategory();
//			updateCategory();
//			testCusineType();
//			testDistributor();
//			testRecipe();
//			testOrder();
//			userManager.create(getCustomer1());
//			for(RecipeBean b :  recipeManager.findAll(RecipeStatus.PENDING, null)) {
//				System.out.println(b);
//			}
//			UserBean u = userManager.findById(4);
//			u.setPassword("123");
//			userManager.update(u);
//			UserBean u = getCustomer2();
//			u.setEmail("admin@admin.com");
//			u.setPassword("admin");
//			u.setType(UserType.ADMIN);
//			userManager.create(u);
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
	}
}
