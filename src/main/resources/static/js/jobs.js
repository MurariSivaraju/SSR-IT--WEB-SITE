// 🔐 BLOCK ACCESS IF NOT LOGGED IN
document.addEventListener("DOMContentLoaded", () => {
    const loggedIn = localStorage.getItem("USER_LOGGED_IN");

    if (loggedIn !== "true") {
        window.location.href = "/login.html";
    }
});
