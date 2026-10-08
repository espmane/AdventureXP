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

        const editButton = document.createElement("button");
        editButton.textContent = "Rediger";

        editButton.addEventListener("click", () => {
            editReservation(reservation.id);
        });

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Slet";

        deleteButton.addEventListener("click", () => {
            deleteReservation(reservation.id);
        });

        div.appendChild(activity);
        div.appendChild(info);
        div.appendChild(editButton);
        div.appendChild(deleteButton);

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


async function deleteReservation(id) {

    const confirmed = confirm("Er du sikker på, at du vil slette reservationen?");

    if (!confirmed) {
        return;
    }

    try {

        const response = await fetch(
            `/reservations/${id}/delete`,
            {
                method: "DELETE"
            }
        );

        if (!response.ok) {
            throw new Error("Kunne ikke slette reservationen");
        }

        alert("Reservationen er slettet");

        showDay();

    } catch (error) {

        alert("Der opstod en fejl");
        console.error(error);
    }
}


async function editReservation(id) {

    const reservation = reservations.find(
        reservation => reservation.id === id
    );

    if (!reservation) {
        return;
    }

    const participants = prompt(
        "Antal deltagere:",
        reservation.participants
    );

    if (participants === null) {
        return;
    }

    const time = prompt(
        "Tid:",
        reservation.time
    );

    if (time === null) {
        return;
    }

    const request = {
        activityId: 1,
        name: "Hector",
        phoneNumber: "12345678",
        amountPeople: Number(participants),
        start: reservation.date + "T" + time + ":00",
        end: reservation.date + "T" + time + ":00"
    };

    try {

        const response = await fetch(
            `/reservations/${id}/update`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(request)
            }
        );

        if (!response.ok) {
            throw new Error("Kunne ikke redigere reservationen");
        }

        alert("Reservationen er opdateret");

        showDay();

    } catch (error) {

        alert("Der opstod en fejl");
        console.error(error);
    }
}


dayBtn.addEventListener("click", showDay);
weekBtn.addEventListener("click", showWeek);
monthBtn.addEventListener("click", showMonth);
