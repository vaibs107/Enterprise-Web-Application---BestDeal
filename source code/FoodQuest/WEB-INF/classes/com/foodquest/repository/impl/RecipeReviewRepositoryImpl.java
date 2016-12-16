package com.foodquest.repository.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.foodquest.models.RecipeReviewBean;
import com.foodquest.models.db.MongoDBConnectionManager;
import com.foodquest.repository.IRecipeReviewRepository;
import com.foodquest.utils.lang.AppException;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RecipeReviewRepositoryImpl implements IRecipeReviewRepository{
		
	private static IRecipeReviewRepository INSTANCE = null;
	private static String COLLECTIONS = "recipe_reviews";
	private MongoDBConnectionManager dbManager;
	private DB appDB;

	private RecipeReviewRepositoryImpl() {
		dbManager = MongoDBConnectionManager.INSTANCE;
		appDB = dbManager.getAppDB();
	}


	
	@Override
	public String create(RecipeReviewBean review) throws AppException {
		ObjectId id = new ObjectId();
		try {
			BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start().add("recipe_id", review.getRecipeId())
					.add("user_id", review.getUserId()).add("user_name", review.getUserName())
					.add("user_age", review.getUserAge()).add("user_gender", review.getUserGender())
					.add("user_occupation", review.getUserOcuupation()).add("zipcode", review.getZipCode())
					.add("rating", review.getRating())
					.add("review_date", review.getReviewdate()).add("comment", review.getComment()).add("_id", id);
			appDB.getCollection(COLLECTIONS).insert(docBuilder.get());
			System.out.println("hello");
			System.out.println(docBuilder.get());
		} catch (Exception e) {
			return null;
		}
		return id.toString();
	}

	@Override
	public List<RecipeReviewBean> getReviewsByRecipeId(Integer recipeId) throws AppException {
		BasicDBObject query = new BasicDBObject("recipe_id", recipeId);
		DBCursor curs = appDB.getCollection(COLLECTIONS).find(query);

		List<RecipeReviewBean> reviews = new ArrayList<>();

		while (curs.hasNext()) {
			DBObject dbObj = curs.next();
			RecipeReviewBean bean = new RecipeReviewBean();

			bean.set_id(dbObj.get("_id").toString());
			if (dbObj.containsKey("comment"))
				bean.setComment(dbObj.get("comment").toString());
			if (dbObj.containsKey("review_date"))
				bean.setReviewdate(new Date(dbObj.get("review_date").toString()));
			if (dbObj.containsKey("recipe_id"))
				bean.setRecipeId(Integer.parseInt(dbObj.get("recipe_id").toString().trim()));
			if (dbObj.containsKey("rating"))
				bean.setRating(Integer.parseInt(dbObj.get("rating").toString().trim()));
			if (dbObj.containsKey("zipcode"))
				bean.setZipCode(dbObj.get("zipcode").toString());
			if (dbObj.containsKey("user_name"))
				bean.setUserName(dbObj.get("user_name").toString());
			if (dbObj.containsKey("user_age"))
				bean.setUserAge(Integer.parseInt(dbObj.get("user_age").toString().trim()));
			if (dbObj.containsKey("user_gender"))
				bean.setUserGender(dbObj.get("user_gender").toString());
			if (dbObj.containsKey("user_id"))
				bean.setUserId(Integer.parseInt(dbObj.get("user_id").toString().trim()));
			if (dbObj.containsKey("user_occupation"))
				bean.setUserOcuupation(dbObj.get("user_occupation").toString());
			reviews.add(bean);
		}

		return reviews;
	}

	public static IRecipeReviewRepository getInstance() {
		synchronized (RecipeReviewRepositoryImpl.class) {
			if (INSTANCE == null) {
				INSTANCE = new RecipeReviewRepositoryImpl();
			}
		}
		return INSTANCE;
	}
	
	
	public static void main(String[] args) throws ParseException, AppException {
		
		String Date = "12/02/2016";
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
		Date dd1 = df.parse(Date);
		RecipeReviewRepositoryImpl r = new RecipeReviewRepositoryImpl();
		RecipeReviewBean b = new RecipeReviewBean();
		b.setComment("the best mix of flavours test 1");
		b.setRating(5);
		b.setRecipeId(1);
		b.setReviewdate(dd1);
		b.setUserAge(24);
		b.setUserGender("female");
		b.setUserId(1);
		b.setUserName("mouna");
		b.setUserOcuupation("analyst");
		b.setZipCode("60616");
		//r.create(b);
		List<RecipeReviewBean> rev=r.getReviewsByRecipeId(1);
		for(RecipeReviewBean t : rev){
			System.out.println(t);
			System.out.println(t.getReviewdate());
			System.out.println(rev.size());
		}
	}


}
