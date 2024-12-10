const inputEmail = document.getElementById('gmail');
const wrongEmail = document.getElementById('wrong__email');
const resetPasswordBtn = document.getElementById('reset__password__btn');
const showMessage = document.getElementById('show__message');
const forgotPasswordForm = document.getElementById("forgot__password__form")

resetPasswordBtn.addEventListener('click', (e) => {
    e.preventDefault()
    if (inputEmail.value === '') {
        wrongEmail.textContent = "Vui lòng nhập địa chỉ mail"
        wrongEmail.style.opacity = 1
    } else if (!inputEmail.value.includes('@')) {
        wrongEmail.textContent = "Địa chỉ mail không hợp lệ!"
        wrongEmail.style.opacity = 1
    } else {
        wrongEmail.style.opacity = 0
        forgotPasswordForm.submit()
    }
})

inputEmail.addEventListener('input', () => {
    resetPasswordBtn.disabled = false
    resetPasswordBtn.style.opacity = '1'
})
