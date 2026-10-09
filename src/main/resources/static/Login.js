const BASE = "";

async function login(username, password) {
    const response = await fetch(`${BASE}/auth/login`, {
        method: "POST",
        credentials: "include",
        body: new URLSearchParams({ username, password })
    });

    if (!response.ok) {
        throw new Error(await response.text());
    }
}

async function logout() {
    await fetch(`${BASE}/auth/logout`, {
        method: "POST",
        credentials: "include"
    });
}

async function isLoggedIn() {
    try {
        const response = await fetch(`${BASE}/auth/me`, { credentials: "include" });
        return response.ok;
    } catch (error) {
        return false;
    }
}

async function api(path, options = {}) {
    const response = await fetch(`${BASE}${path}`, { credentials: "include", ...options });

    if (response.status === 401) {
        window.location.href = "login.html";
        return null;
    }
    return response;
}