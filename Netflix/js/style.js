// 풀페이지 js
new fullpage('#fullpage', {
  scrollingSpeed: 750,
  slidesNavigation: true,
  dragAndMove: true,
  dragAndMoveKey: 'YWx2YXJvdHJpZ28uY29tX0EyMlpISmhaMEZ1WkUxdmRtVT0wWUc=',
  controlArrows: true,
  verticalCentered: true,
  responsiveWidth: 1024,
  responsiveHeight: 330,
  bigSectionsDestination: top,
  scrollOverflow: true,
  sectionsColor: ['#111', '#111', '#111', '#111', '#111', '#111', '#111', '#222']
});

$(function () {
  // N 시리즈 예고편 스와이퍼
  var swiper = new Swiper(".section2_mySwiper", {
    loop: true,
    loopFillGroupWithBlank: true,
    slidesPerView: 2,
    pagination: {
      el: ".preview-pagination",
      clickable: true,
    },
    navigation: {
      nextEl: ".preview-next",
      prevEl: ".preview-prev",
    },
    breakpoints: {
      0: { slidesPerView: 1, spaceBetween: 20 },
      767: { slidesPerView: 2, spaceBetween: 20 },
      1024: { slidesPerView: 2, spaceBetween: 30 }
    }
  });

  // N 시리즈 영화 스와이퍼
  var swiper = new Swiper(".mySwiper", {
    slidesPerGroup: 1,
    loop: true,
    loopFillGroupWithBlank: true,
    autoplay: {
      delay: 3000,
    },
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
    breakpoints: {
      0: { slidesPerView: 1, spaceBetween: 20 },
      540: { slidesPerView: 2, spaceBetween: 20 },
      767: { slidesPerView: 3, spaceBetween: 20 },
      1024: { slidesPerView: 3.15, spaceBetween: 30 }
    }
  });

  // carrousel 배너 적용
  $('#dg-container').carrousel({
    current: 0,
    autoplay: false,
    interval: 3000
  });
  // carrousel 배너 각 배너별 적용
  $(".g-scrolling-carousel1 .items").gScrollingCarousel();
  $(".g-scrolling-carousel2 .items").gScrollingCarousel();
  $(".g-scrolling-carousel3 .items").gScrollingCarousel();

  // 햄버거 버튼 클릭시 왼쪽으로 이동
  $('header>.innerBox>.btns').click(function () {
    $(".wrapper>.content>aside").toggleClass("active").siblings().removeClass("active");
  })

  // x버튼 클릭시 모바일 메뉴 오른쪽으로 이동
  $('.wrapper>.content>aside>.close').click(function () {
    $(".wrapper>.content>aside").removeClass("active");
  })

  $(window).resize(function () {
    if ($(window).width() <= 1024) {
      $('header').addClass('on');
    } else {
      $('header').removeClass('on');
    }
  });
});