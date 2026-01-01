function scrollTo(id) {
    document.getElementById(id).scrollIntoView({ behavior: 'smooth' });
}

function goToRegister() {
    window.location.href = "/html/register.html";
}


let lastScrollY = window.scrollY;
const navbar = document.querySelector(".navbar");

window.addEventListener("scroll", () => {
    const currentScrollY = window.scrollY;

    if (currentScrollY > lastScrollY && currentScrollY > 120) {
        // scrolling down
        navbar.classList.add("hide");
    } else {
        // scrolling up
        navbar.classList.remove("hide");
    }

    lastScrollY = currentScrollY;
});


function registerUser(e) {
    e.preventDefault();
    alert("Registration submitted (API integration next)");
}

function loginUser(e) {
    e.preventDefault();
    alert("Login successful (API integration next)");
}

function submitContact(e) {
    e.preventDefault();
    alert("Message sent successfully");
}
function scrollToSection(id) {
    document.getElementById(id).scrollIntoView({ behavior: "smooth" });
}

function submitContact(e) {
    e.preventDefault();
    alert("Thank you for contacting SSR IT!");
}
function openModal(title, desc, list) {
    document.getElementById("modalTitle").innerText = title;
    document.getElementById("modalDesc").innerText = desc;

    const ul = document.getElementById("modalList");
    ul.innerHTML = "";
    list.forEach(i => {
        const li = document.createElement("li");
        li.innerText = i;
        ul.appendChild(li);
    });

    document.getElementById("courseModal").style.display = "block";
}

function closeModal() {
    document.getElementById("courseModal").style.display = "none";
}

/* SCROLL */
function scrollToSection(id) {
    document.getElementById(id).scrollIntoView({ behavior: "smooth" });
}

/* CONTACT */
async function submitContact(event) {
    event.preventDefault(); // stop page refresh

    const data = {
        name: document.getElementById("contactName").value,
        email: document.getElementById("contactEmail").value,
        phoneNumber: document.getElementById("contactPhone").value,
        message: document.getElementById("contactMessage").value
    };

    try {
        const response = await fetch("http://localhost:8081/api/contact", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("Message sent successfully!");
            document.querySelector(".contact-form").reset(); // ✅ clears form
        } else {
            alert("Failed to send message. Please try again.");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Server error. Please try later.");
    }
}

/* DARK MODE */
function toggleDark() {
    document.body.classList.toggle("dark");
}

/* COURSE MODAL */
function openModal(title, desc, list) {
    document.getElementById("modalTitle").innerText = title;
    document.getElementById("modalDesc").innerText = desc;

    const ul = document.getElementById("modalList");
    ul.innerHTML = "";
    list.forEach(item => {
        const li = document.createElement("li");
        li.innerText = item;
        ul.appendChild(li);
    });

    document.getElementById("courseModal").style.display = "block";
}

function closeModal() {
    document.getElementById("courseModal").style.display = "none";
}

/* =========================
   STATS COUNTER ANIMATION
========================= */
const counters = document.querySelectorAll(".counter");

const runCounter = (counter) => {
    const target = +counter.getAttribute("data-target");
    const speed = 120; // lower = faster

    const update = () => {
        const value = +counter.innerText;
        const increment = Math.ceil(target / speed);

        if (value < target) {
            counter.innerText = value + increment;
            setTimeout(update, 20);
        } else {
            counter.innerText = target;
        }
    };

    update();
};

/* Trigger when visible */
const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            runCounter(entry.target);
            observer.unobserve(entry.target);
        }
    });
}, { threshold: 0.5 });

counters.forEach(counter => observer.observe(counter));


/* ===== TESTIMONIAL SLIDER ===== */
/* =========================
   TESTIMONIAL SLIDER
========================= */
const testimonialCards = document.querySelectorAll(".testimonial-card");
let testimonialIndex = 0;

// Safety check
if (testimonialCards.length > 0) {

    const showTestimonial = (index) => {
        testimonialCards.forEach(card => card.classList.remove("active"));
        testimonialCards[index].classList.add("active");
    };

    // Auto slide
    let testimonialInterval = setInterval(() => {
        testimonialIndex = (testimonialIndex + 1) % testimonialCards.length;
        showTestimonial(testimonialIndex);
    }, 4000);

    // Pause on hover
    testimonialCards.forEach(card => {
        card.addEventListener("mouseenter", () => {
            clearInterval(testimonialInterval);
        });

        card.addEventListener("mouseleave", () => {
            testimonialInterval = setInterval(() => {
                testimonialIndex = (testimonialIndex + 1) % testimonialCards.length;
                showTestimonial(testimonialIndex);
            }, 4000);
        });
    });
}

/* ===== COURSE MODAL ===== */
document.querySelectorAll(".course-card").forEach(card => {
    card.addEventListener("click", () => {
        openModal(
            card.querySelector("h3").innerText,
            "Course highlights and placement support",
            ["Live Projects", "Mock Interviews", "Certification", "Placement Support"]
        );
    });
});

function openModal(title, desc, list) {
    document.getElementById("modalTitle").innerText = title;
    document.getElementById("modalDesc").innerText = desc;

    const ul = document.getElementById("modalList");
    ul.innerHTML = "";
    list.forEach(item => {
        const li = document.createElement("li");
        li.innerText = item;
        ul.appendChild(li);
    });

    document.getElementById("courseModal").style.display = "block";
}

function closeModal() {
    document.getElementById("courseModal").style.display = "none";
}

const jobs = [
    {
        company: "Norstella",
        role: "Python Developer",
        skill: "Python",
        package: "Up to 3.5 LPA (expected)",
        location: "Bangalore",
        color: "#5eead4"
    },
    {
        company: "GE Aerospace",
        role: "Java Developer",
        skill: "Java",
        package: "Up to 8 LPA (expected)",
        location: "Bangalore",
        color: "#1e3a8a"
    },
    {
        company: "CGI",
        role: "Python Developer",
        skill: "Python",
        package: "Up to 5 LPA (expected)",
        location: "Hyderabad",
        color: "#ef4444"
    }
];

function resetFilters() {
    skillFilter.value = "";
    locationFilter.value = "";
    companyFilter.value = "";
    loadJobs();
}


