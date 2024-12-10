const inputNewPassword = document.getElementById('password');
const inputConfirmPassword = document.getElementById('confirmPassword');
const resetPasswordBtn = document.getElementById('reset__password__btn');
const resetPasswordForm = document.getElementById('reset__password__form');
const errorNewPassword = document.getElementById('error__new__password');
const errorConfirmNewPassword = document.getElementById('error__confirm__new__password');
const showNewPassword = document.getElementById('show__new__password');
const showConfirmPassword = document.getElementById('show__confirm__password');
const hideNewPassword = document.getElementById('hide__new__password');
const hideConfirmPassword = document.getElementById('hide__confirm__password');

resetPasswordBtn.addEventListener('click', (e) => {
    e.preventDefault();
    if (inputNewPassword.value === '') {
        errorNewPassword.textContent = 'Vui lòng nhập mật khẩu mới';
        errorNewPassword.style.opacity = '1'
    } else if(inputNewPassword.value.length < 5) {
        errorNewPassword.textContent = 'Mật khẩu phải có ít nhất 5 ký tự';
        errorNewPassword.style.opacity = '1'
    } else {
        errorNewPassword.style.opacity = '0'
    }
    if (inputConfirmPassword.value === '') {
        errorConfirmNewPassword.textContent = 'Vui lòng xác nhận mật khẩu';
        errorConfirmNewPassword.style.opacity = '1'
    } else if (inputConfirmPassword.value !== inputNewPassword.value) {
        errorConfirmNewPassword.textContent = 'Mật khẩu không khớp';
        errorConfirmNewPassword.style.opacity = '1'
    } else {
        errorConfirmNewPassword.style.opacity = '0'
    }
    if (inputNewPassword.value !== '' && inputConfirmPassword.value !== '' && inputConfirmPassword.value === inputNewPassword.value && inputNewPassword.value.length >= 5) {
        resetPasswordForm.submit();
    }
})

showNewPassword.addEventListener('click', () => {
    inputNewPassword.type = 'text';
    showNewPassword.hidden = true;
    hideNewPassword.hidden = false;
    console.log("Show new password")
})

hideNewPassword.addEventListener('click', () => {
    inputNewPassword.type = 'password';
    showNewPassword.hidden = false;
    hideNewPassword.hidden = true;
    console.log("Hide new password")
})

showConfirmPassword.addEventListener('click', () => {
    inputConfirmPassword.type = 'text';
    showConfirmPassword.hidden = true;
    hideConfirmPassword.hidden = false;
    console.log("Show confirm password")
})

hideConfirmPassword.addEventListener('click', () => {
    inputConfirmPassword.type = 'password';
    showConfirmPassword.hidden = false;
    hideConfirmPassword.hidden = true;
    console.log("Hide confirm password")
})