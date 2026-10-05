const reservations = [
    {
        id: 1,
        activity: "Gocart",
        date: "2026-10-04",
        time: "10:00",
        participants: 8
    },
    {
        id: 2,
        activity: "Paintball",
        date: "2026-10-04",
        time: "13:00",
        participants: 12
    },
    {
        id: 3,
        activity: "Minigolf",
        date: "2026-10-06",
        time: "15:00",
        participants: 4
    },
    {
        id: 4,
        activity: "Sumo Wrestling",
        date: "2026-10-15",
        time: "12:00",
        participants: 6
    }
];

const reservationContainer = document.querySelector("#reservations");
const periodTitle = document.querySelector("#periodTitle");

const dayBtn = document.querySelector("#dayBtn");
const weekBtn = document.querySelector("#weekBtn");
const monthBtn = document.querySelector("#monthBtn");

const currentDate = new Date("2026-10-04");


function displayReservations(reservationsToShow) {

    reservationContainer.innerHTML = "";

    if (reservationsToShow.length === 0) {
        reservationContainer.innerHTML = "<p>Ingen reservationer.</p>";
        return;
    }

    reservationsToShow.forEach(reservation => {

        const div = document.createElement("div");

        div.innerHTML = `
            <h3>${reservation.activity}</h3>
            <p>Dato: ${reservation.date}</p>
            <p>Tid: ${reservation.time}</p>
            <p>Antal deltagere: ${reservation.participants}</p>
            <hr>
        `;

        reservationContainer.appendChild(div);
    });
}

function showDay() {

    periodTitle.textContent = "Dagsoversigt";

    const dateString = currentDate.toISOString().split("T")[0];

    const dayReservations = reservations.filter(reservation => {
        return reservation.date === dateString;
    });

    displayReservations(dayReservations);
}

function showWeek() {

    periodTitle.textContent = "Ugeoversigt";

    const startDate = new Date(currentDate);

    const endDate = new Date(currentDate);
    endDate.setDate(endDate.getDate() + 7);

    const weekReservations = reservations.filter(reservation => {

        const reservationDate = new Date(reservation.date);

        return reservationDate >= startDate &&
            reservationDate < endDate;
    });

    displayReservations(weekReservations);
}

function showMonth() {

    periodTitle.textContent = "Månedsoversigt";

    const month = currentDate.getMonth();
    const year = currentDate.getFullYear();

    const monthReservations = reservations.filter(reservation => {

        const reservationDate = new Date(reservation.date);

        return reservationDate.getMonth() === month &&
            reservationDate.getFullYear() === year;
    });

    displayReservations(monthReservations);
}

async function fetchReservations() {

    const response = await fetch("http://localhost:8080/api/reservations");

    if (!response.ok) {
        throw new Error("Kunne ikke hente reservationer");
    }

    return await response.json();
}

dayBtn.addEventListener("click", showDay);
weekBtn.addEventListener("click", showWeek);
monthBtn.addEventListener("click", showMonth);

showDay();