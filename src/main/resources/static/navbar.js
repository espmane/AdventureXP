async function buildNavbar() {
    const loggedIn = await isLoggedIn();

    const nav = document.createElement("nav");
    nav.className = "navbar";

    if (loggedIn) {
        //kunde
        nav.innerHTML = `
            <a class="brand" href="/booking.html">AdventureXP Medarbejder</a>
            <div class="navbar-right">
                <a class="nav-link" href="/booking.html">Kalender</a>
                <a class="nav-link" href="/overview.html">Home</a>
                <button id="navbarLogoutBtn" class="nav-btn" type="button">Log ud</button>
            </div>
        `;
    } else {
        // medarbejder
        nav.innerHTML = `
            <a class="brand" href="/front.html">AdventureXP</a>
            <div class="navbar-right">
                <a class="nav-link" href="/front.html">Forside</a>
                <a class="nav-link" href="/booking.html">Book events</a>
            </div>
        `;
    }

    document.body.prepend(nav);

    // Log ud (både navbarens knap og #logoutBtn på overview-siden)
    document.querySelectorAll("#navbarLogoutBtn, #logoutBtn").forEach(button => {
        button.addEventListener("click", async () => {
            await logout();
            window.location.href = "/login.html";
        });
    });
}

buildNavbar();