/**
 * 
 */

async function updateQuantity(id, diff) {
	const token = document.querySelector('meta[name="_csrf"]').content;
	const header = document.querySelector('meta[name="_csrf_header"]').content;
    try {
        const res = await fetch(`/api/cart-items/${id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
				[header]: token
            },
            body: JSON.stringify({
                diff: diff
            })
        });

        if (!res.ok) {
            throw new Error("수량 변경 실패");
        }

        let data = await res.json();
		let quantity = document.getElementById(data.id);
		let totalPrice = document.getElementById("totalPrice");
		let finalPrice = document.getElementById("finalPrice");
		let itemPrice = document.getElementById(`itemTotalPrice${data.id}`);
		console.log("토탈프라이스",data.totalPrice)
		quantity.innerText = data.quantity.toLocaleString();
		itemPrice.innerText = "₩"+addPrice(itemPrice,data.totalPrice);
		totalPrice.innerText = "₩"+ addPrice(totalPrice,data.totalPrice);
		finalPrice.innerText ="₩"+ addPrice(finalPrice,data.totalPrice);
		
	

    } catch (err) {
        console.error(err);
        alert("수량 변경 중 오류 발생");
    }
}

function addPrice(price,addPrice){
	console.log(parseInt(addPrice))
	console.log(parseInt(price.innerText.slice(1).replaceAll(",","")))
	return (parseInt(price.innerText.slice(1).replaceAll(",",""))+parseInt(addPrice)).toLocaleString();
}

async function removeItem(id) {
	const token = document.querySelector('meta[name="_csrf"]').content;
		const header = document.querySelector('meta[name="_csrf_header"]').content;
    try {
        const res = await fetch(`/api/cart-items/${id}`, {
            method: "DELETE",
			headers:{
				[header]:token
			}
        });

        if (!res.ok) {
            throw new Error("삭제 실패");
        }
		
        location.reload();

    } catch (err) {
        console.error(err);
        alert("삭제 중 오류 발생");
    }
}
