  /*----------------------------- Site Loader & Popup --------------------*/
  $(window).on("load", function () {
    $("#ms-overlay").fadeOut("slow");
  });


(function ($) {

	"use strict";

	/*--------------------- Return To Top --------------------*/
	var btn = $('#return-to-top');

	$(window).scroll(function () {
		if ($(window).scrollTop() > 300) {
			btn.fadeIn(500);
		} else {
			btn.fadeOut(500);
		}
	});

	btn.on('click', function (evnt) {
		evnt.preventDefault();
		$('html, body').animate({ scrollTop: 0 }, '500');
	});

	/*--------------------- Portfolio Section --------------------*/
	$(document).ready(function () {
		var mixer = mixitup('.portfolio-list');		
		$( ".filter" ).trigger( "click" );
	});
	
	/*--------------------- Scroll Top --------------------*/
	$(".btn-scroll").on('click', function() {
		$('html,body').animate({
			scrollTop: $("#demos").offset().top},
			'slow');
	});
	$(function() {
		var header = $(".start-style");
		$(window).scroll(function() {    
			var scroll = $(window).scrollTop();
		
			if (scroll >= 10) {
				header.removeClass('start-style').addClass("scroll-on");
			} else {
				header.removeClass("scroll-on").addClass('start-style');
			}
		});
	});		
		
	//Animation
	
	$(document).ready(function() {
		$('body.hero-anime').removeClass('hero-anime');
	});

	//Menu On Hover
		
	$('body').on('mouseenter mouseleave','.nav-item',function(e){
			if ($(window).width() > 750) {
				var _d=$(e.target).closest('.nav-item');_d.addClass('show');
				setTimeout(function(){
				_d[_d.is(':hover')?'addClass':'removeClass']('show');
				},1);
			}
	});	
	
 
	$(".navbar-toggler").on('click', function () {
			$("#ms-mobile-menu").addClass("ms-mobile-menu-open");
			$(".ms-side-cart-overlay").fadeIn();
	});   
	$(".ms-side-cart-overlay, .ms-close").on('click', function () {
		$("#ms-mobile-menu").removeClass("ms-mobile-menu-open");
		$(".ms-side-cart-overlay").fadeOut();
	}); 

	$(document).ready(function() {
		$('.mn-nav').bind('click', function(e) {
				e.preventDefault(); // prevent hard jump, the default behavior

				var target = $(this).attr("href"); // Set the target as variable

				// perform animated scrolling by getting top-position of target-element and set it as scroll target
				$('html, body').stop().animate({
						scrollTop: $(target).offset().top
				}, 1000, function() {
						location.hash = target; //attach the hash (#jumptarget) to the pageurl
				});

				return false;
		});
});

$(window).scroll(function() {
		var scrollDistance = $(window).scrollTop();
		// Assign active class to nav links while scolling
		$('.page-section').each(function(i) {
				if ($(this).position().top <= scrollDistance+60) {
						$('.navbar-nav li.active').removeClass('active');
						$('.navbar-nav li').eq(i).addClass('active');
				}
		});
}).scroll();

  /*--------------------- Custom Cursor JS -------------------------------- */
  var ms_cursor = document.querySelector('.ms-cursor');
  var ms_cursorinner = document.querySelector('.ms-cursor-2');
  var a = document.querySelectorAll('a');

  document.addEventListener('mousemove', function(e){
    var x = e.clientX;
    var y = e.clientY;
    ms_cursor.style.transform = `translate3d(calc(${e.clientX}px - 50%), calc(${e.clientY}px - 50%), 0)`
  });

  document.addEventListener('mousemove', function(e){
    var x = e.clientX;
    var y = e.clientY;
    ms_cursorinner.style.left = x + 'px';
    ms_cursorinner.style.top = y + 'px';
  });

  document.addEventListener('mousedown', function(){
    ms_cursor.classList.add('click');
    ms_cursorinner.classList.add('ms-cursorinnerhover')
  });

  document.addEventListener('mouseup', function(){
    ms_cursor.classList.remove('click')
    ms_cursorinner.classList.remove('ms-cursorinnerhover')
  });

  a.forEach(item => {
    item.addEventListener('mouseover', () => {
      ms_cursor.classList.add('ms-hover-cursor');
    });
    item.addEventListener('mouseleave', () => {
      ms_cursor.classList.remove('ms-hover-cursor');
    });
  })
  var date = new Date().getFullYear();
  document.getElementById("copyright_year").innerHTML = date;

})(jQuery);