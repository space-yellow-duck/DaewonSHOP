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

function addCart(el){
	const productId = el.dataset.productId;
    const qty = document.getElementById("quantity").value;

    fetch("/cart/add", {
        method : "POST",
        headers : {
            "Content-Type" : "application/json"
        },
        body : JSON.stringify({
            productId : productId,
            quantity : qty
        })
    })
}