document.getElementById("registerForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const form = document.getElementById("registerForm");
    const formData = new FormData(form);

    fetch("/api/auth/register", {
        method: "POST",
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            alert(data.message);
            window.location.href = "/login.html";
        } else {
            alert(data.error || "Registration failed");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Server error");
    });
});




//=========login========//
function loginUser(event) {
    event.preventDefault();

    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;

    const params = new URLSearchParams();
    params.append("email", email);
    params.append("password", password);

    fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params
    })
    .then(res => res.json())
    .then(data => {
        if (data.message) {

            // ✅ SET LOGIN FLAG ONLY ON SUCCESS
            localStorage.setItem("USER_LOGGED_IN", "true");

            alert(data.message);

            // ✅ NORMAL REDIRECT (NO #jobs)
            window.location.href = "/jobs.html";

        } else {
            alert(data.error || "Login failed");
        }
    })
    .catch(() => {
        alert("Server error");
    });
}



//=======================ContactUs======================//
function submitContact(event) {
    event.preventDefault();

    const payload = {
        name: document.getElementById("contactName").value,
        email: document.getElementById("contactEmail").value,
        phoneNumber: document.getElementById("contactPhone").value,
        message: document.getElementById("contactMessage").value
    };

    fetch("/api/contact", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(err => { throw err; });
        }
        return response.text();
    })
    .then(msg => {
        alert(msg);
        document.querySelector(".contact-form").reset();
    })
    .catch(err => {
        alert("Error: " + err);
    });
}
function logout() {
    localStorage.removeItem("USER_LOGGED_IN");
    window.location.href = "/login.html";
}


