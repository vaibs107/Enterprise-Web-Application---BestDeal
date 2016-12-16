<%@page import="com.foodquest.models.RecipeBean"%>
<%@page import="com.foodquest.models.CusineTypeBean"%>
<%@page import="com.foodquest.models.CategoryBean"%>
<%@page import="java.util.List"%>
<%@page import="com.foodquest.services.impl.*"%>
<%@page import="com.foodquest.services.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="commonImport.jsp"%>
<%!
IRecipeTypeManager recipeTypeManager;

	public void jspInit() {
		try {
			recipeTypeManager = RecipeTypeManagerImpl.getInstance();
		} catch (Exception e) {

		}
	}
%>
<%
	List<CategoryBean> categories = recipeTypeManager.findAllCategories();
	List<CusineTypeBean> cusines = recipeTypeManager.findAllCusines();
	%>
	<div class="col-md-3">
	<p class="lead">Category</p>
	<div class="list-group">
	<a href="/FoodQuest/recipes?categoryName=_all" class="list-group-item"><%="All"%></a> 
	<%for(CategoryBean category : categories) {
		%>
		<a href="/FoodQuest/recipes?categoryName=<%=category.getName()%>" class="list-group-item"><%=category.getName()%></a> 
	<%}
	%>
	</div>
    <br>
	<p class="lead">Cusine Type</p>
	<div class="list-group">
	<%for(CusineTypeBean cusine : cusines) {
		%>
		<a href="/FoodQuest/recipes?recipeName=<%=cusine.getName()%>" class="list-group-item"><%=cusine.getName()%></a> 
	<%}
	%>
	</div>
	</div>
<%%>
