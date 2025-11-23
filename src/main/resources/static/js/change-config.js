document.addEventListener("DOMContentLoaded", () => {
    const minInput = document.getElementById("min");
    const maxInput = document.getElementById("max");

    let debounceTimer;

    const sendUpdate = () => {
        const min = minInput.value;
        const max = maxInput.value;

        fetch("/configure", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                min: min,
                max: max
            })
        })
            .then(res => {
                if (!res.ok) throw new Error("Fehler beim Senden");
                return res.text();
            })
            .then(data => console.log("Update gesendet:", data))
            .catch(err => console.error(err));
    };

    const debouncedUpdate = () => {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(sendUpdate, 300); // 300 ms debounce
    };

    minInput.addEventListener("input", debouncedUpdate);
    maxInput.addEventListener("input", debouncedUpdate);
});
