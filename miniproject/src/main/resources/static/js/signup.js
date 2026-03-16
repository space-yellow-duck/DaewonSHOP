/**
 * 
 */
let isAvailableUsername = false;
let isAvailablePwd = false;
let matchPwd = false;
const usernameInput = document.getElementById("username");
const form = document.getElementById("form");
form.addEventListener("submit", validateSubmit)
async function validateSubmit(e) {
	e.preventDefault();
	const formData = new FormData(form);
	if ((isAvailableUsername && isAvailablePwd && matchPwd)) {
		console.log("안되냐?ㄴ")
		try {
			const res = await fetch("/api/users/signup", {
				method: "POST",
				body: formData
			});

			if (!res.ok) {
				const err = await res.json();
				showError(err.message);   // 안내 문구 표시
				return;
			}

			// 성공 처리
			alert("회원가입 성공")
			location.href = "/";
		} catch (e) {
			showError("네트워크 오류가 발생했습니다.");
		}
	}

}

function showError(message) {
    const errorBox = document.getElementById("error-message");
    errorBox.textContent = message;
    errorBox.style.display = "block";
}
usernameInput.addEventListener("Input", () => {
	isAvailableUsername = false;
})
function checkUsername() {

	const username = usernameInput.value;
	const msg = document.getElementById("usernameMessage");
	if (username.length < 8) {
		isAvailableUsername = false;
		msg.innerText = "아이디는 8자리 이상으로 입력해주세요";
		msg.style.color = "red";
		return;
	}
	fetch(`/api/users/check-username?username=${username}`)
		.then(res => res.json())
		.then(data => {

			if (data.available) {
				msg.innerText = "사용 가능한 아이디입니다.";
				msg.style.color = "green";
				isAvailableUsername = true;
			} else {
				msg.innerText = "이미 사용중인 아이디입니다.";
				msg.style.color = "red";
				isAvailableUsername = false;
			}

		});

}



function validatePassword(password) {

	// 1. 길이 체크
	if (password.length < 8 || password.length > 20) {
		return "비밀번호는 8~20자여야 합니다.";
	}

	// 2. 공백 체크
	if (/\s/.test(password)) {
		return "비밀번호에 공백을 사용할 수 없습니다.";
	}

	// 3. 허용되지 않은 문자 체크
	if (!/^[A-Za-z0-9!@#$%^&*]+$/.test(password)) {
		return "허용되지 않은 문자가 포함되어 있습니다. ! @ # $ % ^ & * 를 사용해서 만들어주세요";
	}

	// 4. 문자 종류 체크
	if (!/[A-Za-z]/.test(password)) {
		return "영문자를 포함해야 합니다.";
	}

	if (!/\d/.test(password)) {
		return "숫자를 포함해야 합니다.";
	}

	if (!/[!@#$%^&*]/.test(password)) {
		return "특수문자를 포함해야 합니다.";
	}

	return null;
}

let pwd = document.getElementById("pwd");
pwd.addEventListener("input", (e) => {
	let validatePwd = document.getElementById("validatePassword");
	e.target.value = e.target.value.replace(/\s/g, "");
	let errorMsg = validatePassword(e.target.value);
	if (e.target.value.length === 0) {
		validatePwd.classList.remove("success", "error");
		validatePwd.classList.add("hidden");
		validatePwd.innerText = "";
		isAvailablePwd = false;
		return;
	}
	if (errorMsg === null) {
		validatePwd.classList.remove("hidden", "error");
		validatePwd.classList.add("success");
		validatePwd.innerText = "사용 가능한 비밀번호입니다.";
		isAvailablePwd = true;
	} else {
		validatePwd.classList.remove("hidden", "success");
		validatePwd.classList.add("error");
		validatePwd.innerText = errorMsg;
		isAvailablePwd = false;
	}

})
function reEnterPwd(el) {
	let reEnterMsg = document.getElementById("reEnterMsg")
	if (pwd.value.length === 0 || el.value.length === 0) {
		reEnterMsg.innerText = "";
		reEnterMsg.classList.remove("success", "error")
		reEnterMsg.classList.add("hidden")
		matchPwd = false;
	} else if (pwd.value == el.value) {
		reEnterMsg.innerText = "비밀번호가 일치합니다.";
		reEnterMsg.classList.remove("error", "hidden")
		reEnterMsg.classList.add("success")
		matchPwd = true;
	} else {
		reEnterMsg.innerText = "비밀번호가 일치하지 않습니다. 다시 확인해주세요."
		reEnterMsg.classList.remove("success", "hidden")
		reEnterMsg.classList.add("error")
		matchPwd = false;
	}

}