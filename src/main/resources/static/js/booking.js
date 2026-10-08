"use strict";

const API_URL = "http://localhost:8080/activities"
const RES_URL = "http://localhost:8080/reservations"

const durationSelect = document.querySelector("#hours")
const participantsSelect = document.querySelector("#participants")
const totalPriceSelect = document.querySelector("#totalPrice")
const activitySelect = document.querySelector("#activity")
const companySelect = document.querySelector("#company")
const formSubmit = document.querySelector("#bookingForm")
let activities = []

// for updating price
activitySelect.addEventListener("change", updatePrice)
durationSelect.addEventListener("change", updatePrice)
participantsSelect.addEventListener("input", updatePrice)
companySelect.addEventListener("change", () => {
    activitySelect.disabled = companySelect.checked
    updatePrice()
});

function updatePrice() {
    const duration = Number(durationSelect.value)
    const amount = Number(participantsSelect.value)
    let total = 0
    if (companySelect.checked) {
        total = sumActivities() * Number(durationSelect.value) * amount
    } else {
        const selected = activities.find(item => String(item.id) === activitySelect.value)
        total = (selected?.price ?? 0) * duration * amount
    }
    totalPriceSelect.textContent = `${total} kr`
}

function sumActivities() {
    let sum = 0
    for (const item of activities) {
        sum += Number(item.price)
    }
    return sum
}

function addActivities() {
    activitySelect.length = 1
    for (const item of activities) {
        activitySelect.add(new Option(item.name))
    }
}

async function getActivities() {
    const response = await fetch(API_URL)
    activities = await response.json()
    addActivities()
    updatePrice()
}

getActivities();