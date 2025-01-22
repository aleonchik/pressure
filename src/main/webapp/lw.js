let userId;
const selectElement = document.querySelector(".usr");
const delLink = document.querySelector('.delLink');
const editLink = document.querySelector('.editLink');
	
selectElement.addEventListener("change", (event) => {
	userId = `${event.target.value}`;
	delLink.href = "delpatient?usr=" + userId;
	editLink.href = "editpatient?usr=" + userId;
});
