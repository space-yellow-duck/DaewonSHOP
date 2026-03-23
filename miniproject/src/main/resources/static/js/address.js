/**
 * 
 */
document.addEventListener("keydown", function(e) {
  if (e.key === "Escape") closeModal();
});
window.onclick = function(e) {
  const modal = document.getElementById("addressModal");
  if (e.target === modal) closeModal();
}

function openModal() {
  document.getElementById("addressModal").style.display = "flex";

  new daum.Postcode({
    oncomplete: function(data) {

      document.getElementById("postcode").value = data.zonecode;
      document.getElementById("roadAddress").value = data.roadAddress;
      document.getElementById("jibunAddress").value = data.jibunAddress;

      closeModal();
    }
  }).embed(document.getElementById("postcode-container")); // ⭐ 핵심
}

function closeModal() {
  document.getElementById("addressModal").style.display = "none";
}
