document.addEventListener('keydown', function(event) {
    if (event.repeat) return;

    if (event.key === 'q') {
        document.querySelector('#checkForm button[type="submit"]').click();
    }

    if (event.key === 'w') {
        document.querySelector('#missForm button[type="submit"]').click();
    }
});