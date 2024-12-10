const inputFName = document.getElementById('input__first__name')
const inputLName = document.getElementById('input__last__name')
const inputEmail = document.getElementById('input__email')
const inputPassword = document.getElementById('input__password')
const inputUsername = document.getElementById("input__username")
const registerBtn = document.getElementById('register__btn')

const regLastName = document.getElementById('reg__last__name')
const regEmail = document.getElementById('reg__email')
const regPassword = document.getElementById('reg__password')
const regUsername = document.getElementById('reg__username')
const regFirstName = document.getElementById('reg__first__name')
const inputTags = document.getElementsByTagName('input')
const showPasswordReg = document.getElementById('show__password__reg')
const hidePasswordReg = document.getElementById('hide__password__reg')

for (let i = 0; i < inputTags.length; i++) {
    inputTags[i].addEventListener('input', () => {
        registerBtn.disabled = false
        registerBtn.style.opacity = '1'
    })

}

registerBtn.addEventListener('click', (e) => {
    if(inputLName.value === '') {
        e.preventDefault()
        regLastName.style.opacity = '1'
    } else {
        regLastName.style.opacity = '0'
    }
    if (inputEmail.value === '') {
        e.preventDefault()
        regEmail.textContent = 'Vui lòng nhập email'
        regEmail.style.opacity = '1'
    } else if (!inputEmail.value.includes('@')) {
        e.preventDefault()
        regEmail.textContent = 'Email không hợp lệ'
        regEmail.style.opacity = '1'
    }
    else {
        regEmail.style.opacity = '0'
    }
    if (inputFName.value === '') {
        e.preventDefault()
        regFirstName.style.opacity = '1'
    } else {
        regFirstName.style.opacity = '0'
    }
    if (inputPassword.value === '') {
        e.preventDefault()
        regPassword.style.opacity = '1'
    } else {
        regPassword.style.opacity = '0'
    }
    if (inputUsername.value === '') {
        e.preventDefault()
        regUsername.style.opacity = '1'
    } else {
        regUsername.style.opacity = '0'
    }
    if (inputFName.value !== '' && inputLName.value !== '' && inputEmail.value !== '' && inputPassword.value !== '' && inputUsername.value !== '' && inputEmail.value.includes('@')) {
        document.getElementById('register__form').submit()
    }
})

showPasswordReg.addEventListener('click', (e) => {
    if (inputPassword.type === 'password') {
        inputPassword.type = 'text';
        // showPasswordReg.hidden = true
        // hidePasswordReg.hidden = false
        showPasswordReg.style.display = 'none'
        hidePasswordReg.style.display = 'flex'
    }
})

hidePasswordReg.addEventListener('click', (e) => {
    if (inputPassword.type === 'text') {
        inputPassword.type = 'password'
        // showPasswordReg.hidden = false
        // hidePasswordReg.hidden = true
        showPasswordReg.style.display = 'flex'
        hidePasswordReg.style.display = 'none'
    }
})
