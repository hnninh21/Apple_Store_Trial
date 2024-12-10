const editFormWrapper = document.querySelector('.back-ground-wrapper')
const edit_btn = document.querySelector('.editBtn')
const addNewFormWrapper = document.querySelector('.back-ground-wrapper-add')
const confirmDialog = document.querySelector('.back-ground-wrapper-confirm')
const cancel_btn = document.querySelector(".btn-second")
const backgroundAddNew = document.querySelector('.back-ground-wrapper-add')
const addingWrapper = document.querySelector('.adding-form-wrapper')
const backgroundEdit = document.querySelector('.back-ground-wrapper')
const wrapperEditForm = document.querySelector('#edit-form-wrapper')
const backgroundConfirm = document.querySelector('.back-ground-wrapper-confirm')
const wrapperConfirmForm = document.querySelector('.confirm-form-wrapper')
const editProductBtn = document.getElementById('edit__product__btn')
const addProductBtn = document.getElementById('add__product__btn')


backgroundAddNew.addEventListener('click', () => {
    backgroundAddNew.style.display = 'none'
    for (let i = 0; i < checkEmpty.length; i++) {
        checkEmpty[i].style.opacity = '0';
    }

    for (let i = 0; i < inputElementList.length; i++) {
        inputElementList[i].value = ''
    }
})



addingWrapper.addEventListener('click', (e) => {
    e.stopPropagation()
})

backgroundEdit.addEventListener('click', () => {
    backgroundEdit.style.display = 'none'
    for (let i = 0; i < checkEmpty.length; i++) {
        checkEmpty[i].style.opacity = '0';
    }
})

wrapperEditForm.addEventListener('click', (e) => {
    e.stopPropagation()
})

backgroundConfirm.addEventListener('click', () => {
    backgroundConfirm.style.display = 'none'
})

wrapperConfirmForm.addEventListener('click', (e) => {
    e.stopPropagation()
})

function openEditModal (wrapper){
    wrapper.style.display = 'flex';
}

function closeEditModal (wrapper) {
    wrapper.style.display = 'none';
    for (let i = 0; i < inputElementList.length; i++) {
        inputElementList[i].value = ''
    }
    for (let i = 0; i < inputElementListEdit.length; i++) {
        inputElementListEdit[i].value = ''
    }
    for (let i = 0; i < checkEmpty.length; i++) {
        checkEmpty[i].style.opacity = '0';
    }
}

$(".filterColorBtn").click(function () {
    $(".colorComboBoxForm").submit()
})

$("#search-icon").click(function () {
    $("#search-form").submit();
})

$('.searchBox').keydown(function (e) {
    if (e.keyCode === 13) {
        $("#search-form").submit();
    }
})

$("#comboBox").change(function () {
    $("#sort-form").submit();
})

$('.editBtn').click(function () {
    const product = {
        id: $(this).data('id'),
        name: $(this).data('name'),
        des: $(this).data('des'),
        price: $(this).data('price'),
        stock: $(this).data('stock'),
        color: $(this).data('color')
    };

    console.log(parseFloat(product.price.replaceAll(",", "").replace(" ₫", "")))

    $('#editForm input[name="name"]').val(product.name);
    $('#editForm input[name="id"]').val(product.id);
    $('#editForm textarea[name="description"]').val(product.des);
    $('#editForm input[name="price"]').val(parseFloat(product.price.replaceAll(",", "").replace(" ₫", "")));
    $('#editForm input[name="stock"]').val(product.stock);
    $('#editForm input[name="color"]').val(product.color);
});



$(document).ready(function() {
    $("#upload-img").change(function() {
        $('#upload-label').text("")
        $('#success-icon').css("display", "flex")
        $('.upload-btn').css("border", "2px solid #54B435");
    });
})
const deleteContent = document.getElementById('delete__content')
var id = ''
$('.deleteBtn').click(function () {
    id = $(this).data('product_id')
    deleteContent.textContent = 'Bạn có chắc chắn muốn xóa sản phẩm có mã ' + id + ' không?';
})

$('#yes-btn').click(function () {
    const form = $('#confirm-form')
    form.attr('action', '/admin/delete/id=' + id)
    form.submit();
})

$(document).ready(function() {
    // Them so thu tu vao cac dong cua bang
    $(".productTable tbody tr").each(function(index) {
        $(this).find(".stt-column").text(index);
    });
});


// VALIDATE ADD NEW PRODUCT FORM

const addNewForm = document.querySelector('.add-new-form')
const checkEmpty = document.getElementsByClassName('check__empty')

const productName = document.getElementById('input__name')
const productPrice = document.getElementById('input__price')
const productColor = document.getElementById('input__color')
const productStock = document.getElementById('input__quantity')
const productDescription = document.getElementById('input__des')
const uploadImg = document.getElementById('upload-img')

const emptyName = document.getElementById('empty__name')
const emptyPrice = document.getElementById('empty__price')
const emptyColor = document.getElementById('empty__color')
const emptyStock = document.getElementById('empty__stock')
const emptyDes = document.getElementById('empty__des')
const emptyImg = document.getElementById('empty__img')

document.getElementById('colorPicker').addEventListener('input', function () {
    document.getElementById('input__color_id').value = this.value;
});

document.querySelector('colorPicker').addEventListener('submit', function() {
    console.log(document.getElementById('input__color_id').value);
});

function validateInput(input) {
    const regex = /^[a-zA-Z0-9àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ\s]+$/;
    return regex.test(input);
}

function containsNumber(input) {
    const regex = /\d/;
    return regex.test(input);
}

const inputElementList = document.getElementsByClassName('add-form-control')

addProductBtn.addEventListener('click', function (e) {
     e.preventDefault()
     // VALIDATE PRODUCT NAME
     if (productName.value.trim() === "") {
        emptyName.textContent = 'Vui lòng nhập tên sản phẩm';
        emptyName.style.opacity = '1';
        return
    }
     if (!validateInput(productName.value.trim())) {
         emptyName.textContent = 'Không được chứa kí tự đặc biệt';
         emptyName.style.opacity = '1';
         return
     }
     if (productName.value.trim().length < 6) {
         emptyName.textContent = 'Tên sản phẩm quá ngắn';
         emptyName.style.opacity = '1';
         return
     }
     emptyName.style.opacity = '0';

     // VALIDATE PRODUCT PRICE
     const price = parseFloat(productPrice.value.trim());
     console.log(price,price < 0, typeof price)
     if (productPrice.value.trim() === '') {
        emptyPrice.textContent = 'Vui lòng nhập giá sản phẩm';
        emptyPrice.style.opacity = '1';
         return
    }
     if (price < 0) {
         emptyPrice.textContent = 'Giá sản phẩm không được âm';
         emptyPrice.style.opacity = '1';
         return
     }
     if (isNaN(price) || price === 0) {
         emptyPrice.textContent = 'Giá sản phẩm không hợp lệ';
         emptyPrice.style.opacity = '1';
         return
     }
     emptyPrice.style.opacity = '0';


     // VALIDATE PRODUCT COLOR
     if (productColor.value.trim() === '') {
         emptyColor.textContent = 'Vui lòng nhập màu sản phẩm';
         emptyColor.style.opacity = '1';
         return
     }
     if (!validateInput(productColor.value.trim())) {
         emptyColor.textContent = 'Không được chứa kí tự đặc biệt';
         emptyColor.style.opacity = '1';
         return
     }
     if (containsNumber(productColor.value.trim())) {
         emptyColor.textContent = 'Không được chứa số';
         emptyColor.style.opacity = '1';
         return
     }
     if (productColor.value.trim().length < 2) {
         emptyColor.textContent = 'Màu sản phẩm không hợp lệ';
         emptyColor.style.opacity = '1';
         return
     }
     emptyColor.style.opacity = '0';


     //VALIDATE PRODUCT STOCK
     if (productStock.value.trim() === '') {
         emptyStock.textContent = 'Vui lòng nhập số lượng sản phẩm';
         emptyStock.style.opacity = '1';
         return
     }
     if (productStock.valueAsNumber <= 0) {
         emptyStock.textContent = 'Số lượng sản phẩm phải lớn hơn 0';
         emptyStock.style.opacity = '1';
         return
     }
     if (productStock.valueAsNumber % 1 !== 0) {
         emptyStock.textContent = 'Số lượng sản phẩm phải là số nguyên';
         emptyStock.style.opacity = '1';
         return
     }
     emptyStock.style.opacity = '0';

     //VALIDATE PRODUCT IMAGE
     if (uploadImg.value.length === 0) {
         emptyImg.textContent = 'Vui lòng thêm hình ảnh sản phẩm';
         emptyImg.style.opacity = '1';
         return
     }
     const maxSize = 1024 * 1024 * 2;
     const fileSize = uploadImg.files[0];
     if (fileSize.size > maxSize) {
        emptyImg.textContent = 'Dung lượng hình ảnh tối đa cho phép 2MB. Vui lòng chọn thêm hình ảnh khác';
        emptyImg.style.opacity = '1';
        uploadImg.value = '';
         document.getElementById('upload-label').textContent = 'Chọn hình ảnh';
         document.getElementById('success-icon').style.display = 'none';
         document.querySelector('.upload-btn').style.border = '2px dashed #032D3C';
         return;
     }
     emptyImg.style.opacity = '0';


     // VALIDATE PRODUCT DESCRIPTION
     if (productDescription.value.trim() === '') {
        emptyDes.textContent = 'Vui lòng nhập mô tả sản phẩm';
        emptyDes.style.opacity = '1';
        return
     }
     if (productDescription.value.trim().length < 10) {
        emptyDes.textContent = 'Vui lòng nhập chi tiết hơn';
        emptyDes.style.opacity = '1';
        return
     }
     emptyDes.style.opacity = '0';

     addNewForm.submit();

 })

// VALIDATE EDIT PRODUCT FORM
const productNameEdit = document.getElementById('edit__name')
const  productPriceEdit = document.getElementById('edit__price')
const productColorEdit = document.getElementById('edit__color')
const productStockEdit = document.getElementById('edit__quantity')
const productDescriptionEdit = document.getElementById('descriptionTextarea')

const emptyNameEdit = document.getElementById('empty__edit__name')
const emptyPriceEdit = document.getElementById('empty__edit__price')
const emptyColorEdit = document.getElementById('empty__edit__color')
const emptyStockEdit = document.getElementById('empty__edit__stock')
const emptyDesEdit = document.getElementById('empty__edit__des')

const editForm = document.getElementById('editForm')

const inputElementListEdit = document.getElementsByClassName('form-control')

editProductBtn.addEventListener('click', function (e) {
    e.preventDefault()

    //VALIDATE EDIT NAME
    if (productNameEdit.value.trim() === "") {
        emptyNameEdit.textContent = 'Vui lòng nhập tên sản phẩm';
        emptyNameEdit.style.opacity = '1';
        return
    }
    if (!validateInput(productNameEdit.value.trim())) {
        emptyNameEdit.textContent = 'Không được chứa kí tự đặc biệt';
        emptyNameEdit.style.opacity = '1';
        return
    }
    if (productNameEdit.value.trim().length < 6) {
        emptyNameEdit.textContent = 'Tên sản phẩm quá ngắn';
        emptyNameEdit.style.opacity = '1';
        return
    }
    emptyNameEdit.style.opacity = '0';

    //VALIDATE EDIT PRICE
    if (isNaN(parseFloat(productPriceEdit.value.trim())) || parseFloat(productPriceEdit.value.trim()) === 0){
        emptyPriceEdit.textContent = 'Giá sản phẩm không hợp lệ';
        emptyPriceEdit.style.opacity = '1';
        return
    }
    if (productPriceEdit.value.trim() === '') {
        emptyPriceEdit.textContent = 'Vui lòng nhập giá sản phẩm';
        emptyPriceEdit.style.opacity = '1';
        return
    }
    if (parseFloat(productPriceEdit.value.trim()) < 0) {
        emptyPriceEdit.textContent = 'Giá sản phẩm không được âm';
        emptyPriceEdit.style.opacity = '1';
        return
    }
    emptyPriceEdit.style.opacity = '0';

    //VALIDATE EDIT COLOR
    if (productColorEdit.value.trim() === '') {
        emptyColorEdit.textContent = 'Vui lòng nhập màu sản phẩm';
        emptyColorEdit.style.opacity = '1';
        return
    } else
    if (!validateInput(productColorEdit.value.trim())) {
        emptyColorEdit.textContent = 'Không được chứa kí tự đặc biệt';
        emptyColorEdit.style.opacity = '1';
        return
    } else
    if (containsNumber(productColorEdit.value.trim())) {
        emptyColorEdit.textContent = 'Không được chứa số';
        emptyColorEdit.style.opacity = '1';
        return
    } else
    if (productColorEdit.value.trim().length < 2) {
        emptyColorEdit.textContent = 'Màu sản phẩm không hợp lệ';
        emptyColorEdit.style.opacity = '1';
        return
    } else {
        emptyColorEdit.style.opacity = '0';
    }


    //VALIDATE EDIT STOCK
    if (isNaN(productStockEdit.valueAsNumber)) {
        emptyStockEdit.textContent = 'Số lượng sản phẩm không hợp lệ';
        emptyStockEdit.style.opacity = '1';
        return
    }
    if (productStockEdit.value.trim() === '') {
        emptyStockEdit.textContent = 'Vui lòng nhập số lượng sản phẩm';
        emptyStockEdit.style.opacity = '1';
        return
    }
    if (productStockEdit.valueAsNumber <= 0) {
        emptyStockEdit.textContent = 'Số lượng phải lớn hơn 0';
        emptyStockEdit.style.opacity = '1';
        return
    }
    if (productStockEdit.valueAsNumber % 1 !== 0) {
        emptyStockEdit.textContent = 'Số lượng phải là số nguyên';
        emptyStockEdit.style.opacity = '1';
        return
    }
    emptyStockEdit.style.opacity = '0';

    //VALIDATE EDIT DESCRIPTION
    if (productDescriptionEdit.value.trim() === '') {
        emptyDesEdit.textContent = 'Vui lòng nhập mô tả sản phẩm';
        emptyDesEdit.style.opacity = '1';
        return
    }
    if (productDescriptionEdit.value.trim().length < 10) {
        emptyDesEdit.textContent = 'Vui lòng nhập chi tiết hơn';
        emptyDesEdit.style.opacity = '1';
        return
    }
    emptyDesEdit.style.opacity = '0';

    editForm.submit()
})
