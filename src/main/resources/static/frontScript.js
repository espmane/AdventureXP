const activities = [
    { name: "Paintball", description: "Skyd løs på hindanden med vennerne!"},
    { name: "Gokart", description: "Kør om kap på bannen, og se hvem der er den bedste racer!"},
    { name: "Minigolf", description: "Hyggeligt for både familier og venner"},
    { name: "Sumo Wrestling", description: "Skub hindanden ud af ringen, med vores store sumo dragter"}
]


function renderActivities(){
    const list = document.getElementById("activityList");

    activities.forEach(activity => {

        const card = document.createElement("div");
        card.classList.add("card");

        const title = document.createElement("h3");
        title.textContent = activity.name;

        const text = document.createElement("p");
        text.textContent = activity.description;

        card.appendChild(title);
        card.appendChild(text);
        list.appendChild(card);

    });
}

renderActivities();