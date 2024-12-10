window.onload = function() {
    document.querySelector('.wrap-prod').scrollIntoView({behavior: "smooth"});
};
$('.carousel').carousel({
    interval: 2000
})
$("#comboBox").change(function () {
    console.log("Clicked: " + $('#comboBox').val())
    $("#sort-form").submit();
})


window.onscroll = function() {scrollFunction()};
function scrollFunction() {
    var productContainer = document.querySelector(".product-container");
    var productContainerTop = productContainer.getBoundingClientRect().top;
    var windowHeight = window.innerHeight || document.documentElement.clientHeight;

    // Hiển thị nút nếu phần tử product-container nằm phía trên của cửa sổ
    if (productContainerTop <= windowHeight && productContainerTop < 0) {
        document.getElementById('scrollToSectionBtn').style.display = "block";
    } else {
        document.getElementById('scrollToSectionBtn').style.display = "none";
    }
}

// JavaScript để cuộn đến phần cụ thể
function scrollToSection() {
    document.querySelector('.wrap-prod').scrollIntoView({ behavior: 'smooth' });
}