/**
 * 
 */


function changeImage(el) {
	document.getElementById("mainImage").src = el.src;
}



const token = document.querySelector('meta[name="_csrf"]').content;
const header = document.querySelector('meta[name="_csrf_header"]').content;

