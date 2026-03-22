/**
 * 
 */
let selectedItems = {};
function checkAllSelected() {
    const selects = document.querySelectorAll(".multiOption");

    for (let s of selects) {
        if (!s.value) return false;
    }
    return true;
}

function getSelectedOptions() {
    const selects = document.querySelectorAll(".multiOption");
	
    return Array.from(selects).map(s => s.value);
}
function getSelectedOptionTexts() {
    const selects = document.querySelectorAll(".multiOption");

    return Array.from(selects).map(s => {
        return s.options[s.selectedIndex].text;
    });
}

document.querySelectorAll(".multiOption").forEach(select => {
    select.addEventListener("change", () => {
        if (checkAllSelected()) {
            createSelectedItem();
        }
    });
});

function createSelectedItem() {
    const selectedIds = getSelectedOptions(); // [1,3,5]
    const selectedTexts = getSelectedOptionTexts();

    const key = selectedIds.join("-");

    if (selectedItems[key]) {
        alert("이미 추가된 구성입니다.");
        return;
    }

    // ✅ 상태 저장
    selectedItems[key] = {
		type:"MULTI_PACK",
        detailIds: selectedIds,
        quantity: 1
    };

    const container = document.getElementById("selectedOptions");

    const div = document.createElement("div");
    div.classList.add("selected-item");
    div.setAttribute("data-key", key);

    div.innerHTML = `
        <div class="item-info">${selectedTexts.join(" / ")}</div>
        <div class="item-qty">
            <button onclick="changeQty(this, -1)">-</button>
            <span class="qty">1</span>
            <button onclick="changeQty(this, 1)">+</button>
        </div>
        <button onclick="removeItem(this)">X</button>
    `;

    container.appendChild(div);
    resetMultiOptions();
}
function resetMultiOptions() {
    const selects = document.querySelectorAll(".multiOption");

    selects.forEach(s => {
        s.value = ""; // 첫 번째 option (옵션 선택)으로 돌아감
    });
}
function changeQty(btn, delta) {
    const itemDiv = btn.closest(".selected-item");
    const key = itemDiv.dataset.key;

    const qtySpan = itemDiv.querySelector(".qty");
    let qty = parseInt(qtySpan.innerText);

    qty += delta;
    if (qty < 1) qty = 1;

    qtySpan.innerText = qty;

    // ✅ 상태 반영
    selectedItems[key].quantity = qty;
}
function removeItem(btn) {
    const itemDiv = btn.closest(".selected-item");
    const key = itemDiv.dataset.key;

    // ✅ 상태 삭제
    delete selectedItems[key];

    itemDiv.remove();
}

async function addCart(btn) {
	const items = Object.entries(selectedItems).map(([id,item]) => ({
		type:item.type,
		detailIds: item.detailIds,
		quantity: item.quantity
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