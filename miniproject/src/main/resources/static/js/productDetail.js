/**
 * 
 */


function changeImage(el){
    document.getElementById("mainImage").src = el.src;
}


function plusQty(){
    let qty = document.getElementById("quantity");
    qty.value = parseInt(qty.value) + 1;
}

function minusQty(){
    let qty = document.getElementById("quantity");

    if(qty.value > 1){
        qty.value = parseInt(qty.value) - 1;
    }
}
const token = document.querySelector('meta[name="_csrf"]').content;
const header = document.querySelector('meta[name="_csrf_header"]').content;
function addCart(el){
	const productId = el.dataset.productId;
    const qty = document.getElementById("quantity").value;
    fetch("/api/cart-items", {
        method : "POST",
        headers : {
            "Content-Type" : "application/json",
			[header]: token
        },
        body : JSON.stringify({
            productId : parseInt(productId),
            quantity : qty
        })
    }).then((res) => {
		if(!res.ok){
			throw new error("장바구니 추가 실패")
		}
		location.reload();
	})
}