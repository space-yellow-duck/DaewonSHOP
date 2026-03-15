/**
 * 
 */

function checkUsername(){

  const username = document.getElementById("username").value;

  fetch(`/api/users/check-username?username=${username}`)
    .then(res => res.json())
    .then(data => {

      const msg = document.getElementById("usernameMessage");

      if(data.available){
        msg.innerText = "사용 가능한 아이디입니다.";
        msg.style.color = "green";
      }else{
        msg.innerText = "이미 사용중인 아이디입니다.";
        msg.style.color = "red";
      }

    });

}

	let pwd = document.getElementById("pwd");
function reEnterPwd(el){
	let reEnterMsg = document.getElementById("reEnterMsg")
	if(pwd.value.length === 0 || el.value.length === 0){
		reEnterMsg.innerText = "";
		reEnterMsg.classList.remove("success","error")
		reEnterMsg.classList.add("hidden")
	}else if(pwd.value == el.value){
		reEnterMsg.innerText = "비밀번호가 일치합니다.";
		reEnterMsg.classList.remove("error","hidden")
		reEnterMsg.classList.add("success")
	}else{
		reEnterMsg.innerText = "비밀번호가 일치하지 않습니다. 다시 확인해주세요."
		reEnterMsg.classList.remove("success","hidden")
		reEnterMsg.classList.add("error")
	}
		
}