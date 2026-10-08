"use strict";

const ACTIVITIES_URL = "/activities"
const SAVE_URL = "/reservations/save"
const COMPANY_SAVE_URL = "/reservations/company"

const bookingForm = document.querySelector("#bookingForm")
const activitySelect = document.querySelector("#activity")
const nameSelect = document.querySelector("#name")
const phoneSelect = document.querySelector("#phone")
const participantsSelect = document.querySelector("#participants")
const dateSelect = document.querySelector("#startDate")
const startTimeSelect = document.querySelector("#startTime")
const hoursSelect = document.querySelector("#hours")
const companySelect = document.querySelector("#company")
const totalPriceElement = document.querySelector("#totalPrice")

let activities = []
let selectedActivity

bookingForm.addEventListener("submit", (event) => {
    event.preventDefault()
    sendData()
})
activitySelect.addEventListener("change", () => {
    updateConstraints()
    updateSelectedActivity()
    updatePrice()
})
hoursSelect.addEventListener("change", updatePrice)
participantsSelect.addEventListener("input", updatePrice)
companySelect.addEventListener("change", () => {
    activitySelect.selectedIndex = 1
    activitySelect.disabled = companySelect.checked
    participantsSelect.value = 8
    participantsSelect.disabled = companySelect.checked
    updateSelectedActivity()
    updatePrice()
})

function updateSelectedActivity() {
    selectedActivity = activities.find(item => {
        return String(item.id) === activitySelect.value
    })
}

function getToday() {
    return new Date().toLocaleDateString("sv-SE")
}

function toDateTimeString(date) {
    return date.toLocaleString("sv-SE").replace(" ", "T")
}

function updatePrice() {
    const duration = Number(hoursSelect.value) || 1
    const amount = Number(participantsSelect.value) || 1
    let total = 0
    let totalPerPerson = 0

    if (companySelect.checked) {
        let sum = 0
        for (const item of activities) {
            sum += Number(item.price)
        }
        total = sum * duration * amount
        totalPerPerson = total / amount
    } else {
        totalPerPerson = (selectedActivity?.price || 0) * duration
        total = totalPerPerson * amount
    }

    let text = `I alt ${total} kr`
    if (amount > 1) {
        text += ` (${totalPerPerson} kr per person)`
    }
    totalPriceElement.textContent = text
}

function updateConstraints() {
    participantsSelect.min = selectedActivity?.minParticipants || 1
    participantsSelect.max = selectedActivity?.maxParticipants || 50
    dateSelect.min = getToday()
}

async function getActivities() {
    const response = await fetch(ACTIVITIES_URL)
    activities = await response.json()
    addActivities()
}

function addActivities() {
    activitySelect.length = 1
    for (const item of activities) {
        activitySelect.add(new Option(item.name, item.id))
    }
}

function validateRequest(request) {
    if ((request.amountPeople < selectedActivity.minParticipants || request.amountPeople > selectedActivity.maxParticipants)) {
        alert(`Antal deltagere skal være mellem ${selectedActivity.minParticipants} ` +
            `og ${selectedActivity.maxParticipants}`)
        return false
    }
    return true
}

async function sendData() {
    const start = new Date(`${dateSelect.value}T${startTimeSelect.value}:00`)
    const end = new Date(start.getTime() + Number(hoursSelect.value) * 3_600_000)
    const request = {
        activityId: selectedActivity.id,
        name: nameSelect.value,
        phoneNumber: phoneSelect.value,
        amountPeople: Number(participantsSelect.value),
        start: toDateTimeString(start),
        end: toDateTimeString(end)
    }

    if (!validateRequest(request)) {
        return
    }

    try {
        await fetch(companySelect.checked ? COMPANY_SAVE_URL : SAVE_URL, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request)
        })
    } catch (error) {
        console.error(error)
    }
}

async function initialize() {
    await getActivities()
    updatePrice()
    updateConstraints()
    dateSelect.value = getToday()
}

initialize()