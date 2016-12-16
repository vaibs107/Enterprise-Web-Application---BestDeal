<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>FOOD QUEST</title>
<link rel="stylesheet" href="styles.css" type="text/css" />
</head>


<body>
<div id="container">
  <header>
    	<h1><a href="home.jsp">FOOD<span>QUEST</span></a></h1>
    </header>

    

    <div id="body">

        <section id="content">

	    <article>
                
            <%
         	Integer prodID = Integer.parseInt(request.getParameter("recipe_id").trim());
            session.setAttribute("recipe_id",prodID); 
        	

			%>
                  

            <%@include file="/web/views/partials/reviewsPage.jsp"%>
<!-- <div class="border-page"> -->
<!--         <form action="/FoodQuest/web/views/CreateReview.jsp" method="post"> -->
        
<!--         <div class="form-elem"> -->
            
<!--             <label class="label">User Name </label>  -->
<!--             <input type="text" class="review-field" name="username"> </br> -->
            
<!--             <label class="label">User Zipcode</label>  -->
<!--             <input type="text" class="review-field" name="userzipcode"> </br> -->
            
          
<!--             <label class="label">Customer's Age </label>  -->
<!--             <input type="text" class="review-field" name="UserAge"> </br> -->
            
<!--             <label class="label">Customer's Gender </label>  -->
<!--             <input type="text" class="review-field" name="UserGender"> </br> -->

<!--             <label class="label">Customer's Occupation </label>  -->
<!--             <input type="text" class="review-field" name="UserOccupation"> </br> -->
            
<!--             <label class="label">Review Rating (1-5) </label>  -->
<!--             <input type="number" min="1" max="5" class="review-field" name="ReviewRating"> </br> -->
            
<!--             <label class="label">Date </label>  -->
<!--             <input type="date" class="review-field" name="ReviewDate"> </br> -->
            
<!--             <label class="label">Comments</label> -->
<!--             <textarea placeholder="Enter Your comments here" rows="4" cols="50" class="textarea" name="ReviewText"> -->
<!--             </textarea></br> -->
            
<!--             <input type="submit" value="submit" name="submit/"> -->
<!--             </br> -->
<!--         </div> -->
<!--     </form> -->
<!--     </div> -->

          
        </article>
        </section>
        
        

    	<div class="clear"></div>
    </div>

   <footer>

            
            <div class="footer-bottom">
                <p></p>
            </div>

        </footer>
</div>
</body>
</html>
