const BASE_URL = "";

const reservationsElement = document.querySelector("#reservations");
const periodTitle = document.querySelector("#periodTitle");

const dayBtn = document.querySelector("#dayBtn");
const weekBtn = document.querySelector("#weekBtn");
const monthBtn = document.querySelector("#monthBtn");
const scheduleElement = document.querySelector("#schedule");

const currentDate = new Date(2026, 9, 4);

const activityNames = {
    1: "Gocart",
    2: "Paintball",
    3: "Minigolf",
    4: "Sumo Wrestling"
};

let reservations = [];
let selectedPeriod = "day";

async function loadReservations() {
    try {
        const response = await fetch(`${BASE_URL}/reservations`, {
            method: "GET",
            credentials: "include"
        });

        if (response.status === 401) {
            window.location.href = "/login.html";
            return;
        }

        if (!response.ok) {
            throw new Error("HTTP " + response.status);
        }

        reservations = await response.json();

        console.log("Reservationer fra databasen:", reservations);

        refreshOverview();

    } catch (error) {
        console.error(error);
        reservationsElement.textContent =
            "Kunne ikke hente reservationer.";
    }
}

function displayReservations(list) {
    reservationsElement.replaceChildren();

    if (list.length === 0) {
        reservationsElement.textContent =
            "Ingen reservationer i denne periode.";
        return;
    }

    list.forEach(reservation => {
        const div = document.createElement("div");

        const activity = document.createElement("h3");
        activity.textContent =
            activityNames[reservation.activityId] ||
            "Aktivitet " + reservation.activityId;

        const info = document.createElement("p");

        const date = reservation.start.substring(0, 10);
        const time = reservation.start.substring(11, 16);

        info.textContent =
            date + " - " +
            time + " - " +
            reservation.amountPeople + " deltagere";

        const customer = document.createElement("p");
        customer.textContent = reservation.name;

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
        div.appendChild(customer);
        div.appendChild(info);
        div.appendChild(editButton);
        div.appendChild(deleteButton);

        reservationsElement.appendChild(div);
    });
}


function showDay() {
    selectedPeriod = "day";
    periodTitle.textContent = "Dagsoversigt";

    const date = formatDate(currentDate);

    const result = reservations.filter(reservation =>
        reservation.start.substring(0, 10) === date
    );

    displayReservations(result);
    loadSchedule();
}


function showWeek() {
    selectedPeriod = "week";
    periodTitle.textContent = "Ugeoversigt";

    const endDate = new Date(currentDate);
    endDate.setDate(endDate.getDate() + 7);

    const result = reservations.filter(reservation => {
        const date = new Date(reservation.start);
        return date >= currentDate && date < endDate;
    });

    displayReservations(result);
    loadSchedule();
}


function showMonth() {
    selectedPeriod = "month";
    periodTitle.textContent = "Månedsoversigt";

    const result = reservations.filter(reservation => {
        const date = new Date(reservation.start);

        return date.getMonth() === currentDate.getMonth()
            && date.getFullYear() === currentDate.getFullYear();
    });

    displayReservations(result);
    loadSchedule();
}


function refreshOverview() {
    if (selectedPeriod === "week") {
        showWeek();
    } else if (selectedPeriod === "month") {
        showMonth();
    } else {
        showDay();
    }
}


async function deleteReservation(id) {
    const confirmed = confirm(
        "Er du sikker på, at du vil slette reservationen?"
    );

    if (!confirmed) {
        return;
    }

    try {
        const response = await fetch(
            `${BASE_URL}/reservations/${id}/delete`,
            {
                method: "DELETE",
                credentials: "include"
            }
        );

        if (!response.ok) {
            throw new Error("HTTP " + response.status);
        }

        alert("Reservationen er slettet");

        await loadReservations();

    } catch (error) {
        console.error(error);
        alert("Der opstod en fejl ved sletning.");
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
        reservation.amountPeople
    );

    if (participants === null) {
        return;
    }

    const amountPeople = Number(participants);

    if (!Number.isInteger(amountPeople) || amountPeople <= 0) {
        alert("Indtast et gyldigt antal deltagere.");
        return;
    }

    const time = prompt(
        "Starttid (HH:mm):",
        reservation.start.substring(11, 16)
    );

    if (time === null) {
        return;
    }

    if (!/^([01]\d|2[0-3]):[0-5]\d$/.test(time)) {
        alert("Indtast tidspunkt som HH:mm.");
        return;
    }

    const originalStart = new Date(reservation.start);
    const originalEnd = new Date(reservation.end);

    const duration =
        originalEnd.getTime() - originalStart.getTime();

    const date = reservation.start.substring(0, 10);

    const newStart = new Date(date + "T" + time + ":00");
    const newEnd = new Date(newStart.getTime() + duration);

    const request = {
        activityId: reservation.activityId,
        name: reservation.name,
        phoneNumber: reservation.phoneNumber,
        amountPeople: amountPeople,
        start: formatDateTime(newStart),
        end: formatDateTime(newEnd)
    };

    try {
        const response = await fetch(
            `${BASE_URL}/reservations/${id}/update`,
            {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(request)
            }
        );

        if (!response.ok) {
            throw new Error("HTTP " + response.status);
        }

        alert("Reservationen er opdateret");

        await loadReservations();

    } catch (error) {
        console.error(error);
        alert("Der opstod en fejl ved redigering.");
    }
}


function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    return `${year}-${month}-${day}`;
}


function formatDateTime(date) {
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");

    return `${formatDate(date)}T${hours}:${minutes}:${seconds}`;
}

function getPeriodRange() {
    const from = new Date(currentDate);
    const to = new Date(currentDate);

    if (selectedPeriod === "week") {
        to.setDate(to.getDate() + 6);
    } else if (selectedPeriod === "month") {
        from.setDate(1);
        to.setMonth(to.getMonth() + 1, 0); // sidste dag i måneden
    }

    return { from: formatDate(from), to: formatDate(to) };
}



async function loadSchedule() {
    let url;

    if (selectedPeriod === "day") {
        const date = formatDate(currentDate);
        url = `${BASE_URL}/schedule/day?date=${date}`;
    } else if (selectedPeriod === "week") {
        url = `${BASE_URL}/schedule/week`;
    } else {
        url = `${BASE_URL}/schedule/month`;
    }

    try {
        const response = await fetch(url, {
            method: "GET",
            credentials: "include"
        });

        if (response.status === 401) {
            window.location.href = "/login.html";
            return;
        }

        if (!response.ok) {
            throw new Error("HTTP " + response.status);
        }

        const data = await response.json();

        const schedules = Array.isArray(data) ? data : [data];

        displaySchedule(schedules);

    } catch (error) {
        console.error(error);
        scheduleElement.textContent =
            "Kunne ikke hente vagtplan.";
    }
}



function displaySchedule(schedules) {
    scheduleElement.replaceChildren();

    const byEmployee = new Map();

    schedules.forEach(schedule => {
        const assignments = schedule.assignments || [];

        assignments.forEach(assignment => {
            const employeeId = assignment.employeeId;

            if (!byEmployee.has(employeeId)) {
                byEmployee.set(employeeId, {
                    name: assignment.employeeName ||
                        "Medarbejder " + employeeId,
                    shifts: []
                });
            }

            byEmployee.get(employeeId).shifts.push({
                date: schedule.date,
                assignment: assignment
            });
        });
    });

    if (byEmployee.size === 0) {
        scheduleElement.textContent =
            "Ingen medarbejdere på vagt i denne periode.";
        return;
    }

    byEmployee.forEach(employee => {
        const card = document.createElement("div");
        card.classList.add("employee-shift");

        const name = document.createElement("h3");
        name.textContent = employee.name;

        card.appendChild(name);

        employee.shifts.forEach(({ date, assignment }) => {
            const activityName =
                assignment.activityName ||
                activityNames[assignment.activityId] ||
                "Aktivitet " + assignment.activityId;

            const start = assignment.workStart
                ? assignment.workStart.substring(11, 16)
                : "Ukendt";

            const end = assignment.workEnd
                ? assignment.workEnd.substring(11, 16)
                : "Ukendt";

            const shift = document.createElement("p");

            shift.textContent =
                `${date} | ${activityName} | ${start} - ${end}`;

            card.appendChild(shift);
        });

        scheduleElement.appendChild(card);
    });
}




dayBtn.addEventListener("click", showDay);
weekBtn.addEventListener("click", showWeek);
monthBtn.addEventListener("click", showMonth);

loadReservations();
