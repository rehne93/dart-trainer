function showCheckSnackbar(isCheck) {
    const snackbar = document.getElementById("snackbar");
    if(isCheck) {
        snackbar.textContent = 'Check!';
    } else {
        snackbar.textContent = 'Miss!';
    }
    snackbar.classList.add("show");

    setTimeout(() => {
        snackbar.classList.remove("show");
    }, 3000);
}