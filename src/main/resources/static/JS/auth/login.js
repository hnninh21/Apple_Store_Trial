const loginBtn = document.getElementById('login__btn');
const inputUsername = document.getElementById('username');
const inputPassword = document.getElementById('password');
const emptyUsername = document.getElementById('fill__username');
const emptyPassword = document.getElementById('fill__password');
const showPassword = document.getElementById('show__password');
const hidePassword = document.getElementById('hide__password');

loginBtn.addEventListener('mousedown', (e) => {
    loginBtn.style.scale = 0.95
})

loginBtn.addEventListener('mouseup', (e) => {
    loginBtn.style.scale = 1
})


loginBtn.addEventListener('click', (e) => {
    e.preventDefault();
    if (inputUsername.value === '') {
        emptyUsername.style.opacity = 1;
    } else {
        emptyUsername.style.opacity = 0;
    }
    if (inputPassword.value === '') {
        emptyPassword.style.opacity = 1;
    } else {
        emptyPassword.style.opacity = 0;
    }
    if (inputUsername.value !== '' && inputPassword.value !== '') {
        document.getElementById('login__form').submit();
    }
})

if (window.location.href.split('/')[3].includes("error=true")) {
    emptyPassword.textContent = "Tên đăng nhập hoặc mật khẩu không đúng"
    emptyPassword.style.opacity = 1;
} else {
    emptyPassword.style.opacity = 0;
}

showPassword.addEventListener('click', (e) => {
    if (inputPassword.type === 'password') {
        inputPassword.type = 'text';
        showPassword.hidden = true
        hidePassword.hidden = false
    }
})

hidePassword.addEventListener('click', (e) => {
    if (inputPassword.type === 'text') {
        inputPassword.type = 'password'
        showPassword.hidden = false
        hidePassword.hidden = true
    }
})


