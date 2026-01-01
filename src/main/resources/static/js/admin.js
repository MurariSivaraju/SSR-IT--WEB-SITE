const token = localStorage.getItem("ADMIN_TOKEN");
let currentPage = 0;

/* =====================
   AUTH
   ===================== */
function adminLogin() {
    fetch("/api/admin/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            username: user.value,
            password: pass.value
        })
    })
    .then(r => r.json())
    .then(d => {
        if (d.token) {
            localStorage.setItem("ADMIN_TOKEN", d.token);
            location.href = "/admin.html";
        } else {
            document.getElementById("error").innerText = "Invalid credentials";
        }
    });
}

function logout() {
    localStorage.removeItem("ADMIN_TOKEN");
    location.href = "/admin-login.html";
}

/* =====================
   INIT
   ===================== */
function initAdmin() {
    if (!token) {
        location.href = "/admin-login.html";
        return;
    }
    loadUsers(0);
}

/* =====================
   LOAD USERS
   ===================== */
function loadUsers(page) {
    currentPage = page;
    const search = document.getElementById("search").value;

    fetch(`/api/admin/users?search=${search}&page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderUsers(data.content);
            renderPagination(data.totalPages);
        });
}

/* =====================
   RENDER USERS
   ===================== */
function renderUsers(users) {
    const tbody = document.querySelector("#usersTable tbody");
    tbody.innerHTML = "";

    users.forEach(u => {
        tbody.innerHTML += `
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
                <td>${u.mobile}</td>
                <td>${u.highestQualification || "-"}</td>
                <td>${u.workStatus || "-"}</td>
                <td>${u.yearOfExperience || "-"}</td>
                <td>
                    ${u.resumePath
                        ? `<a href="/api/admin/users/${u.id}/resume" target="_blank">View</a>`
                        : "N/A"}
                </td>
                <td>
                    <button onclick="deleteUser(${u.id})">Delete</button>
                </td>
            </tr>
        `;
    });
}

/* =====================
   PAGINATION
   ===================== */
function renderPagination(totalPages) {
    const div = document.getElementById("pagination");
    div.innerHTML = "";

    for (let i = 0; i < totalPages; i++) {
        div.innerHTML += `
            <button onclick="loadUsers(${i})"
              class="${i === currentPage ? 'active' : ''}">
              ${i + 1}
            </button>
        `;
    }
}

/* =====================
   DELETE USER
   ===================== */
function deleteUser(id) {
    if (!confirm("Delete this user?")) return;

    fetch(`/api/admin/users/${id}`, { method: "DELETE" })
        .then(() => loadUsers(currentPage));
}
