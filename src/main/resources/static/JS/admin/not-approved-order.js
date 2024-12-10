const cancelBtn = document.querySelector(".cancel__btn")
const acceptBtn = document.querySelector('.confirm__btn')
const acceptOrderBtn = document.querySelectorAll('.wrapper__accept__icon')
const acceptForm = document.querySelector('.confirm__order__form')
const acceptOrder = document.querySelector('.accept__order')
const content = document.querySelector('#content__modal')
const confirmOrder = document.querySelector('.confirm__order')
const deteleOrder = document.querySelectorAll('.wrapper__delete__icon')
const wrapperReason = document.querySelector('.wrapper__reason')
const inputReason = document.querySelector('#input__reason')
const missReason = document.querySelector('#miss__reason')


let orderId;
let checkType = false;

const today = new Date();

const day = today.getDate();
const month = today.getMonth() + 1;
const year = today.getFullYear();
const hour = today.getHours();
const minute = today.getMinutes();

const paddedMonth = month.toString().padStart(2, '0');
const paddedDay = day.toString().padStart(2, '0');
const paddedHour = hour.toString().padStart(2, '0');
const paddedMinute = minute.toString().padStart(2, '0');

function hasInvalidChars(url) {
    const Regex = /[^a-zA-Z0-9-_.~:\/?#\[\]@!$&'()*+,;=]*|[\x00-\x1F\x7F-\x9F]/g;
    return Regex.test(url);
}


const formattedDate = paddedHour + ":" + paddedMinute + " " + `${paddedDay}-${paddedMonth}-${year}`;

for (let i = 0; i < acceptOrderBtn.length; i++) {
    acceptOrderBtn[i].addEventListener('click', (e) => {
        checkType = true
        acceptOrder.style.display = 'flex';
        wrapperReason.style.display = 'none';
        orderId = acceptOrderBtn[i].dataset.id
        content.textContent = "Bạn có chắc chắn xác nhận đơn hàng có mã " + orderId + " ?"
        console.log(orderId)
        console.log(checkType)
    })
}

for (let i = 0; i < deteleOrder.length; i++) {
    deteleOrder[i].addEventListener('click', (e) => {
        checkType = false
        acceptOrder.style.display = 'flex';
        wrapperReason.style.display = 'flex';
        orderId = acceptOrderBtn[i].dataset.id
        content.textContent = "Bạn có chắc chắn muốn hủy đơn hàng có mã " + orderId + " ?"
        console.log(orderId)
        console.log(checkType)
    })
}

acceptBtn.addEventListener('click', (e) => {
    if (checkType) {
        e.preventDefault()
        acceptForm.action = `/admin/accept/orderid=` + orderId;
    } else {
        e.preventDefault()
        if (inputReason.value === "") {
            missReason.textContent = "Vui lòng nhập lý do hủy đơn hàng";
            missReason.style.opacity = "1";
            return;
        }
        if (inputReason.value.length < 5) {
            missReason.textContent = "Vui lòng nhập chi tiết hơn"
            missReason.style.opacity = "1";
            return;
        }
        if (inputReason.value.length > 255) {
            missReason.textContent = "Lý do hủy đơn hàng không được quá 255 ký tự";
            missReason.style.opacity = "1";
            return;
        }
        if (!hasInvalidChars(inputReason.value)) {
            missReason.textContent = "Không được chứa ký tự đặc biệt";
            missReason.style.opacity = "1";
            return;
        }

        let cancel = inputReason.value + " ,thời gian hủy: " + formattedDate
        acceptForm.action = `/admin/cancel/orderid=${orderId}/reason=${cancel}`
        missReason.style.opacity = "0";
    }
    acceptForm.submit()
})

cancelBtn.addEventListener('click', (e) => {
    e.stopPropagation()
    acceptOrder.style.display = 'none';
    missReason.style.opacity = "0";
    inputReason.value = '';
})

confirmOrder.addEventListener('click', (e) => {
    e.stopPropagation()
})

acceptOrder.addEventListener('click', (e) => {
    acceptOrder.style.display = 'none';
    missReason.style.opacity = "0";
    inputReason.value = '';
})

