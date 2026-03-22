/**
 * 
 */

let selectedItems = {};
// key: detailId
// value: { color, size, quantity }
function onSelectComplete() {
	const color = document.getElementById("colorSelect").value;
	const detailId = document.getElementById("sizeSelect").value;
	let size;
	if (!color || !detailId) return;

	groupedDetails[color].forEach(product => {
		if (product.id == detailId) size = product.size;
	})
	console.log(size)
	addOptionItem(detailId, color, size);
}
function addOptionItem(detailId, color, size) {

	if (selectedItems[detailId]) {
		selectedItems[detailId].quantity++;
	} else {
		selectedItems[detailId] = {
			type: "SINGLE",
			color,
			size,
			quantity: 1
		};
	}

	renderSelectedOptions();
}
function renderSelectedOptions() {
	const container = document.getElementById("selectedOptions");
	container.innerHTML = "";

	Object.entries(selectedItems).forEach(([id, item]) => {
		container.innerHTML += `
            <div class="selected-item">
                <div class="item-info">
                    ${item.color} / ${item.size}
                </div>

                <div class="item-qty">
                    <button onclick="changeQty(${id}, -1)">-</button>
                    <span>${item.quantity}</span>
                    <button onclick="changeQty(${id}, 1)">+</button>
                </div>

                <button class="remove-btn" onclick="removeItem(${id})">X</button>
            </div>
        `;
	});
}

function changeQty(id, diff) {
	const item = selectedItems[id];
	if (!item) return;

	item.quantity += diff;

	if (item.quantity <= 0) {
		delete selectedItems[id];
	}

	renderSelectedOptions();
}
function removeItem(id) {
	delete selectedItems[id];
	renderSelectedOptions();
}
function changeColor(select) {
	const color = select.value;
	const sizeSelect = document.getElementById("sizeSelect");

	sizeSelect.innerHTML = '<option value="">사이즈 선택</option>';

	console.log(color, groupedDetails);
	if (!color || !groupedDetails[color]) return;
	groupedDetails[color].forEach(d => {
		const option = document.createElement("option");
		option.value = d.id;  // 🔥 여기 중요 (detail id)
		option.text = d.size;
		sizeSelect.appendChild(option);

	});
}

async function addCart(btn) {
	const items = Object.entries(selectedItems).map(([id, item]) => ({
		productDetailId: id,
		quantity: item.quantity,
		type:item.type
	}));
	console.log(items)
	await fetch("/api/cart-items", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			[header]: token
		},
		body: JSON.stringify(items)
	});
}