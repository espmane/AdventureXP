const reservations = [
    {
        activity: "Gocart",
        date: "2026-10-04",
        time: "10:00",
        participants: 8
    },
    {
        activity: "Paintball",
        date: "2026-10-04",
        time: "13:00",
        participants: 12
    },
    {
        activity: "Minigolf",
        date: "2026-10-06",
        time: "15:00",
        participants: 4
    },
    {
        activity: "Sumo Wrestling",
        date: "2026-10-15",
        time: "12:00",
        participants: 6
    }
];

const reservationsElement = document.querySelector("#reservations");
const periodTitle = document.querySelector("#periodTitle");

const dayBtn = document.querySelector("#dayBtn");
const weekBtn = document.querySelector("#weekBtn");
const monthBtn = document.querySelector("#monthBtn");

const currentDate = new Date("2026-10-04");


function displayReservations(list) {

    reservationsElement.replaceChildren();

    list.forEach(reservation => {

        const div = document.createElement("div");

        const activity = document.createElement("h3");
        activity.textContent = reservation.activity;

        const info = document.createElement("p");
        info.textContent =
            reservation.date + " - " +
            reservation.time + " - " +
            reservation.participants + " deltagere";

        div.appendChild(activity);
        div.appendChild(info);

        reservationsElement.appendChild(div);
    });
}


function showDay() {

    periodTitle.textContent = "Dagsoversigt";

    const date = currentDate.toISOString().split("T")[0];

    const result = reservations.filter(reservation =>
        reservation.date === date
    );

    displayReservations(result);

    document.querySelector("#logoutBtn").addEventListener("click", async () => {
        await logout();
        window.location.href = "login.html";
    });

// Kun medarbejdere der er logget ind må se siden
    isLoggedIn().then(loggedIn => {
        if (loggedIn) {
            showDay();
        } else {
            window.location.href = "login.html";
        }
    });
}


function showWeek() {

    periodTitle.textContent = "Ugeoversigt";

    const endDate = new Date(currentDate);
    endDate.setDate(endDate.getDate() + 7);

    const result = reservations.filter(reservation => {
        const date = new Date(reservation.date);

        return date >= currentDate && date < endDate;
    });

    displayReservations(result);
}


function showMonth() {

    periodTitle.textContent = "Månedsoversigt";

    const result = reservations.filter(reservation => {

        const date = new Date(reservation.date);

        return date.getMonth() === currentDate.getMonth()
            && date.getFullYear() === currentDate.getFullYear();
    });

    displayReservations(result);
}


dayBtn.addEventListener("click", showDay);
weekBtn.addEventListener("click", showWeek);
monthBtn.addEventListener("click", showMonth);

showDay();