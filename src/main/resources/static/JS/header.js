const dropdownIcon = document.querySelector('#dropdown__icon');
const dropdownMenu = document.querySelector('#wrapper__dropdown');
const manageOrder= document.querySelector('#wrapper__manage__order');
const logoutBtn = document.querySelector('#wrapper__logout__btn');
const confirmLogout = document.querySelector('#outside__logout__form');
const cancelLogout = document.querySelector('#cancel__logout__btn');

dropdownIcon.addEventListener('click', (e) => {
    e.preventDefault()
})

dropdownIcon.addEventListener('mouseover', () => {
    dropdownMenu.style.display = 'flex';
})

dropdownIcon.addEventListener('mouseleave', () => {
    dropdownMenu.style.display = 'none';
})

dropdownMenu.addEventListener('mouseenter', () => {
    dropdownMenu.style.display = 'flex';
})

dropdownMenu.addEventListener('mouseleave', () => {
    dropdownMenu.style.display = 'none';
})

logoutBtn.addEventListener('click', () => {
    confirmLogout.style.display = 'flex';
})

cancelLogout.addEventListener('click', (e) => {
    e.preventDefault()
    confirmLogout.style.display = 'none';
})

confirmLogout.addEventListener('mouseleave', () => {
    confirmLogout.style.display = 'none';
})

function toast({ title, message, type }) {
    console.log("Toast was called")
    const main = document.querySelector("#toast")
    const icons = {
        success: '<i class="fa-solid fa-circle-check"></i>',
        error: '<i class="fa-solid fa-circle-exclamation"></i>',
        warning: '<i class="fa-solid fa-triangle-exclamation"></i>'
    }
    if (main) {
        const toast = document.createElement("div")
        toast.classList.add('toast', `toast__${type}`, 'center')
        toast.innerHTML = `
            <div class="toast__icon">
                ${icons[type]}
            </div>
            <div class="toast__body">
                <h3 class="toast__title">${title}</h3>
                <p class="toast__message">${message}</p>
            </div>
            <div class="toast__close">
                <i class="fa-solid fa-xmark" style="color: gray;"></i>
            </div>
        `
        main.appendChild(toast)
        // Auto close
        const autoCloseId = setTimeout(function () {
            main.removeChild(toast)
        }, 10000)
        // Close when click
        toast.onclick = function () {
            // toast.style.opacity = 0
            setTimeout(function () {
                main.removeChild(toast)
            }, 300)
            clearTimeout(autoCloseId)
        }
    }
}