package com.foodquest.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.foodquest.models.RecipeBean;
import com.foodquest.models.db.MongoDBConnectionManager;
import com.foodquest.repository.IRecipeAnalyticsRepository;
import com.foodquest.repository.IRecipeRepository;
import com.foodquest.utils.lang.AppException;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class RecipeAnalyticsRepositoryImpl implements IRecipeAnalyticsRepository{
	
	private static IRecipeAnalyticsRepository RecipeAnalyticsRepository;
	private static String collection = "recipe_reviews";
	private MongoDBConnectionManager dbManager;
	private DB appDB;
	private IRecipeRepository recpieRepository;
	
	private RecipeAnalyticsRepositoryImpl() {
		dbManager = MongoDBConnectionManager.INSTANCE;
		appDB = dbManager.getAppDB();
		recpieRepository = RecipeRepositoryImpl.getInstance();
	}

	@Override
	public List<RecipeBean> findTopRatedRecipes(int limit) throws AppException {
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("recipe_id", "$_id");
		projectFields.put("rating", "$averageQuantity");
		DBObject project = new BasicDBObject("$project", projectFields);

		List<RecipeBean> recipes = new ArrayList<>();
		DBCollection dbCollection = appDB.getCollection(collection);
		AggregationOutput aggregateOutupt = dbCollection.aggregate(
				new BasicDBObject("$group",
						new BasicDBObject("_id", "$recipe_id").append("averageQuantity",
								new BasicDBObject("$avg", "$rating"))),
				project,new BasicDBObject("$sort", new BasicDBObject("rating", -1)),
				new BasicDBObject("$limit", limit));
		for (DBObject dbObj : aggregateOutupt.results()) {
			int id = (int) dbObj.get("recipe_id");
			RecipeBean bean = recpieRepository.findById(id);
			if (bean != null) {
				if(Double.valueOf(dbObj.get("rating").toString()) > 3.5)
					recipes.add(bean);
			}
		}

		return recipes;
	}

	@Override
	public List<String> findTopZipcodesBasedOnRecipeSale(int limit) throws AppException {
		List<String> zipcodes = new ArrayList<>();
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("zipcode", "$_id");
		projectFields.put("total_sales", "$count");
		DBObject project = new BasicDBObject("$project", projectFields);
		DBCollection dbCollection = appDB.getCollection(collection);
		AggregationOutput aggregateOutupt = dbCollection
				.aggregate(
						new BasicDBObject("$group",
								new BasicDBObject("_id", "$zipcode").append("count",
										new BasicDBObject("$sum", 1))),
						project, new BasicDBObject("$sort", new BasicDBObject("total_sales", -1)),
						new BasicDBObject("$limit", limit));
		for (DBObject dbObj : aggregateOutupt.results()) {
			String zipcode = dbObj.get("zipcode").toString();
			if (zipcode != null) {
				zipcodes.add(zipcode);
			}
		}
		return zipcodes;
	}

	@Override
	public List<RecipeBean> findTopReviewedRecipes(int limit) throws AppException {
		List<RecipeBean> Recipes = new ArrayList<>();
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("recipe_id", "$_id");
		projectFields.put("total_sales", "$count");
		DBObject project = new BasicDBObject("$project", projectFields);
		DBCollection dbCollection = appDB.getCollection(collection);
		AggregationOutput aggregateOutupt = dbCollection.aggregate(
				new BasicDBObject("$group",
						new BasicDBObject("_id", "$recipe_id").append("count", new BasicDBObject("$sum", 1))),
				project, new BasicDBObject("$sort", new BasicDBObject("total_sales", -1)),
				new BasicDBObject("$limit", limit));
		for (DBObject dbObj : aggregateOutupt.results()) {
//			System.out.println(dbObj);
			int id = (int) dbObj.get("recipe_id");
			RecipeBean bean = recpieRepository.findById(id);
			if (bean != null) {
				
					Recipes.add(bean);
			}
		}
		return Recipes;
	}

	public static IRecipeAnalyticsRepository getInstance() {
		synchronized (RecipeAnalyticsRepositoryImpl.class) {
			if(RecipeAnalyticsRepository == null) {
				RecipeAnalyticsRepository = new RecipeAnalyticsRepositoryImpl();
			}
		}
		return RecipeAnalyticsRepository;
	}
	
	public static void main(String[] args) throws AppException {
		RecipeAnalyticsRepositoryImpl r = new RecipeAnalyticsRepositoryImpl();
		for(RecipeBean b : r.findTopReviewedRecipes(5)) {
			System.out.println(b);
		}
	}

}
