const deliveredOrderBtn = document.getElementsByClassName('wrapper__accept__icon');
const cancelDeliveredBtn = document.querySelector(".cancel__btn");
const confirmDeliveredBtn = document.querySelector(".confirm__btn");
const wrapperDeliveredOrder = document.querySelector('.wrapper__delivered__order');
const deliveredOrder = document.querySelector('.delivered__order');
const deliveredOrderForm = document.querySelector('.delivered__order__form');
const content = document.querySelector('#content__modal');
const deteleOrder = document.querySelectorAll('.wrapper__delete__icon')
const wrapperReason = document.querySelector('.wrapper__reason')
const inputReason = document.querySelector('#input__reason')
const missReason = document.querySelector('#miss__reason')

let orderId;
let checkType = false

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

const formattedDate = paddedHour + ":" + paddedMinute + " " + `${paddedDay}-${paddedMonth}-${year}`;

function hasInvalidChars(url) {
    const Regex = /[ "<>#%{}|^~\[\]`]/;
    return Regex.test(url);
}

for (let i = 0; i < deliveredOrderBtn.length; i++) {
    deliveredOrderBtn[i].addEventListener('click', (e) => {
        checkType = true
        orderId = deliveredOrderBtn[i].dataset.id;
        wrapperDeliveredOrder.style.display = 'flex';
        wrapperReason.style.display = 'none'
        content.textContent = "Bạn có chắc chắn xác nhận đã giao đơn hàng có mã " + orderId + " ?";
        console.log(orderId);
    });
}

for (let i = 0; i < deteleOrder.length; i++) {
    deteleOrder[i].addEventListener('click', (e) => {
        checkType = false
        orderId = deteleOrder[i].dataset.id;
        wrapperDeliveredOrder.style.display = 'flex';
        wrapperReason.style.display = 'flex'
        content.textContent = "Bạn có chắc chắn xác nhận đã giao hàng thất bại đơn hàng có mã " + orderId + " ?";
        console.log(orderId);
    });
}

confirmDeliveredBtn.addEventListener('click', (e) => {
    console.log("Click")
    if (checkType) {
        console.log("Click2")
        e.preventDefault();
        deliveredOrderForm.action = `/admin/complete/orderid=` + orderId;
    } else {
        e.preventDefault();
        if (inputReason.value === "") {
            missReason.textContent = 'Vui lòng nhập lý do giao hàng thất bại'
            missReason.style.opacity = '1'
            return;
        }
        if (inputReason.value.length > 255) {
            missReason.textContent = 'Lý do không được quá 255 ký tự'
            missReason.style.opacity = '1'
            return;
        }
        if (inputReason.value.length < 5) {
            missReason.textContent = 'Hãy nhập chi tiết hơn'
            missReason.style.opacity = '1'
            return;
        }
        if (!hasInvalidChars(inputReason.value.trim())) {
            missReason.textContent = "Không được chứa ký tự đặc biệt";
            missReason.style.opacity = "1";
            return;
        }
        missReason.style.opacity = "0";
        let failDelivery = inputReason.value + " ,thời gian hủy: " + formattedDate
        deliveredOrderForm.action = `/admin/fail/orderid=` + orderId + `/reason=` + failDelivery;
    }
    deliveredOrderForm.submit();
});

cancelDeliveredBtn.addEventListener('click', (e) => {
    e.stopPropagation();
    wrapperDeliveredOrder.style.display = 'none';
    missReason.style.opacity = "0";
    inputReason.value = '';
})

deliveredOrder.addEventListener('click', (e) => {
    e.stopPropagation();
})

wrapperDeliveredOrder.addEventListener('click', (e) => {
    wrapperDeliveredOrder.style.display = 'none';
    missReason.style.opacity = "0";
    inputReason.value = '';
})


