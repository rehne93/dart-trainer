document.addEventListener('keydown', function(event) {
    if (event.repeat) return;

    if (event.key === '1') {
        document.querySelector('#checkForm button[type="submit"]').click();
    }

    if (event.key === '2') {
        document.querySelector('#missForm button[type="submit"]').click();
    }
});