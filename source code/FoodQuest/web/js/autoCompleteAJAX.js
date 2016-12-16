function showResult(key) {
	console.log("Key event");
	if (key.length == 0) {
		document.getElementById("autocomplete_result").innerHTML = "";
		document.getElementById("autocomplete_result").style.border = "none";
		return;
	}

	if (window.XMLHttpRequest) {
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else { // code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onload = function() {
		if (xmlhttp.status === 200) {
			var jsonObject = JSON.parse(xmlhttp.responseText);
			var fragment = document.createDocumentFragment();
			var element = document.createElement("a");
			for ( var prop in jsonObject) {
				
				element.href = "/FoodQuest/recipe/_search?id=" + prop;
				element.innerHTML = jsonObject[prop];
				fragment.appendChild(element);

			}
			autocomplete_result.innerHTML = "";
			autocomplete_result.style.display = "block";
			autocomplete_result.appendChild(fragment);
		}
	};
	xmlhttp.open("GET", "/FoodQuest/recipe/ajax/_search?query=" + key, true);
	xmlhttp.send();
}
function clearSearch() {
	document.getElementById("autocomplete_result").innerHTML = "";
	document.getElementById("autocomplete").value = "";
	document.getElementById("autocomplete_result").style.border = "none";
}