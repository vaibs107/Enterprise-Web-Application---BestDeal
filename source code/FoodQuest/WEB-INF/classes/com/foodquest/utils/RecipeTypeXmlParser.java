package com.foodquest.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.foodquest.models.CategoryBean;
import com.foodquest.models.CusineTypeBean;
import com.foodquest.models.Pair;

public class RecipeTypeXmlParser {
	private static RecipeTypeXmlParser INSTANCE;
	private SAXParserFactory saxParserFactory;
	private SAXParser saxParser;

	private RecipeTypeXmlParser() throws ParserConfigurationException, SAXException {
		saxParserFactory = SAXParserFactory.newInstance();
		saxParser = saxParserFactory.newSAXParser();
	}

	public Pair<List<CategoryBean>, List<CusineTypeBean>> getCategoriesAndCusines(String rootPath) {
		List<CategoryBean> categories = new ArrayList<CategoryBean>();
		List<CusineTypeBean> cusines = new ArrayList<CusineTypeBean>();
		try {
			RecipeTypeXmlHandler recipeTypeXmlHandler = new RecipeTypeXmlHandler();
			saxParser.parse(new File(rootPath + "WEB-INF/data/recipetype.xml"), recipeTypeXmlHandler);
			categories = recipeTypeXmlHandler.getCategoryList();
			cusines = recipeTypeXmlHandler.getCusineList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Pair<List<CategoryBean>, List<CusineTypeBean>>(categories, cusines);
	}

	public static RecipeTypeXmlParser getInstance() throws ParserConfigurationException, SAXException {
		synchronized (RecipeTypeXmlParser.class) {
			if (INSTANCE == null) {
				INSTANCE = new RecipeTypeXmlParser();
			}
		}
		return INSTANCE;
	}
}
