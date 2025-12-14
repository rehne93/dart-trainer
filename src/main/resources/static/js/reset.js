function reset() {
    fetch('/reset', {
        method: 'GET',
        credentials: 'include'
    })
    .then(res => {
        if(!res.ok) throw new Error("Fehler " + res.status);
        console.log(res);
        window.location.href = '/index?reset=true'
    });
}

document.getElementById('reset').addEventListener('click', () => {
    reset();
});

