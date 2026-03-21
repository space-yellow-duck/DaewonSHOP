/**
 * 
 */


function changeImage(el) {
	document.getElementById("mainImage").src = el.src;
}


function plusQty() {
	let qty = document.getElementById("quantity");
	qty.value = parseInt(qty.value) + 1;
}

function minusQty() {
	let qty = document.getElementById("quantity");

	if (qty.value > 1) {
		qty.value = parseInt(qty.value) - 1;
	}
}
const token = document.querySelector('meta[name="_csrf"]').content;
const header = document.querySelector('meta[name="_csrf_header"]').content;

async function addCart(btn) {
	const items = Object.entries(selectedItems).map(([id, item]) => ({
		detailId: id,
		quantity: item.quantity
	}));

	await fetch("/api/cart", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			[header]: token
		},
		body: JSON.stringify(items)
	});
}