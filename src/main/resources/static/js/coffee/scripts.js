(function($, window, undefined) {
	//'use strict';
	TCH = {
		init: function() {
			var that = this;
			that.initViews();
			that.initScript();
			that.searchScript();
			that.menuMobile();
			that.loadPage();
		},
		initViews: function() {
			var view = window.shop.template,
					that = this;
			switch (view) {
				case 'index':
					that.indexScript();
					that.getImageInstagram();
					that.getStoreIndex();
					break;
				case 'collection':
					that.collectionScript();
					break;
				case 'search':
					break;
				case 'product':
					break;
				case 'blog':
					break;
				case 'page.contact':
					break;
				case 'page.brand-story':
					break;
				case 'page.store':
					that.initMap();
					break;
				case 'blog':
					that.getImageInstagramBlog();
					break;
				case 'page.all-store':
					that.initAllStore();
					break;
				case 'page.rewards':
					break;
				case 'page':
					break;
				default:
			}
		},
		loadPage: function(){
			$('.loader_overlay').addClass('loaded');
		},
		initScript:function(){
			/* Variant Product loop */
			$('.variant_product_loop').change(function(){
				var that = $(this);
				$(this).parents('.variant_product_item').find('.price_product_item').html(that.attr('data-price'));
			});
			$('.view_more_human').click(function(){
				$('.human_item_more').removeClass('hidden');
				$(this).hide();
			});
		},
		indexScript: function(){

			/* Slider index */
			var owlSliderIndex = $('#owl_slide');
			owlSliderIndex.owlCarousel({
				nav : false,
				dotsSpeed : 400,
				dots : true,
				mouseDrag: false,
				loop: true,
				items: 1,
				autoplayHoverPause: true,
				autoplay: true,
				autoplayTimeout: 6000
			});
			owlSliderIndex.on('changed.owl.carousel translated.owl.carousel initialized.owl.carousel', function(event) {
				$("#owl_slide .owl-item .hrv-banner-caption").css('display','none');
				$("#owl_slide .owl-item .hrv-banner-caption").removeClass('hrv-caption')
				$("#owl_slide .owl-item.active .hrv-banner-caption").css('display','block');

				var heading = $('#owl_slide .owl-item.active .hrv-banner-caption').clone().removeClass();
				$('#owl_slide .owl-item.active .hrv-banner-caption').remove();
				$('#owl_slide .owl-item.active>.item').append(heading);
				$('#owl_slide .owl-item.active>.item>div').addClass('hrv-banner-caption hrv-caption');
			});
			var owlSliderDot = $('#owl_slide .owl-dot');
			owlSliderDot.each(function(){
				var indexTemp = parseInt($(this).index());
				var index = 0;
				if(index < 10){
					index = "0" + (indexTemp + 1);
				}else{
					index = (indexTemp + 1);
				}
				$(this).html("<span class='dot-border'></span><span class='dot-number'>"+index+"</span>");
			});
			$('#owl_slide .owl-dots').wrap('<div class="container wrap-dots"></div>');
			/* Slider view more scroll */
			$('.fixed-scroll-down').on('click', function(e) {
				var height = $(window).scrollTop() + $(window).height();
				$('html, body').animate({
					scrollTop: height
				}, 1000);
			});
			/* Get Blog top */
			var str_url = encodeURIComponent('((blogid:article>=0)&&(tag:article=top))');
			$.ajax({
				url: "/search?q=filter=("+str_url+")&view=blog_top",
				async: false,
				success:function(data){
					$(".top_blog_home").html(data);
				}
			});
			/* Slider Store */
			$('.store_slider').owlCarousel({
				nav : true,
				dots : false,
				loop: true,
				items: 1,
				mouseDrag: false,
				autoplayHoverPause: true,
				autoplay: true,
				navText: ['<i class="fas fa-angle-left"></i>','<i class="fas fa-angle-right"></i>'],
				autoplayTimeout: 7000
			});
		},
		searchScript: function(){
			/* search */
			$('.search a').click(function(){
				$(this).parents('.search').find('.search_input_wrap').toggle();
			});
			/*$('.search_form').submit(function(e){
				e.preventDefault();
				var thatForm = $(this), input_type = thatForm.find('input[name="follow"]:checked').val(),
						val_search = thatForm.find('input[name="q"]').val(),
						collection_id = thatForm.find('input[name="collection_id_search"]').val();
debugger;
				if(input_type == 'product'){
					if(val_search == ''){
						var q = '((collectionid:product='+collection_id+'))';
					}else{
						var q = '((collectionid:product='+collection_id+')&&(title:product**' + val_search + '))';
					}
					window.location = '/search?q=filter=(' + encodeURIComponent(q) + ')';
				}
			});*/
		},
		menuMobile: function(){
			/* Menu mobile */
			$('#showmenu-mobile').click(function(e){
				e.preventDefault();
				$(".header_menu").addClass("show");
				$('#opacity').addClass("opacity_body");
				$('body').addClass("overflow_hidden");
			});
			$('#opacity,.icon_close_menu').click(function(){
				$(".header_menu").removeClass("show");
				$('#opacity').removeClass("opacity_body");
				$('body').removeClass("overflow_hidden");
			});
			$(".more").on("click", function() {
				var icon_fa = $('.icon_more').attr('data-icon');
				if(icon_fa == 'plus'){
					$('.icon_more').attr('data-icon', 'minus');
				}else{
					$('.icon_more').attr('data-icon', 'plus');
				}
			});
		},
		collectionScript: function(){
			var that = this;
			/* Slider Collection */
			var owlSliderIndex = $('#owl_slide');
			owlSliderIndex.owlCarousel({
				nav : false,
				dotsSpeed : 400,
				dots : true,
				mouseDrag: false,
				loop: true,
				items: 1,
				autoplayHoverPause: true,
				autoplay: true,
				autoplayTimeout: 1455000
			});
			/* Scroll Menu sidebar */
			that.scrollSidebar();
			$('.menu_scroll_link').click(function(e){
				e.preventDefault();
				$('.menu_scroll_link').removeClass('active');
				$(this).addClass('active');
				$("html, body").animate({ scrollTop: $($(this).attr('href')).offset().top }, "500");
			});
		},
		scrollSidebar: function(){
			$(window).scroll(function(){
				$('.sidebar_menu ul li').each(function(){
					var currLink = $(this),
							elementLink = $(currLink.find('a').attr('href'));
					if($(window).scrollTop() > (elementLink.offset().top - 500) && elementLink.offset().top + elementLink.height() > $(window).scrollTop()){
						$('.sidebar_menu ul li a').removeClass('active');
						currLink.find('a').addClass('active');
					}
				})
			})
		},
		// Kiểm tra Id lớn nhất
		myArrayMax: function (arr) {
			var len = arr.length,max = '';
			while(len--){if(arr[len].id > max){max = arr[len].id;}}
			return max;
		},
		// Trả lỗi khi người dùng không cho phép lấy vị trí hiện tại
		handleLocationError: function(browserHasGeolocation, infoWindow, pos,map) {
			infoWindow.setPosition(pos);
			infoWindow.setContent(browserHasGeolocation ? 'Error: The Geolocation service failed.' : 'Error: Your browser doesn\'t support geolocation.');
			infoWindow.open(map);
		},
		// Đổ dữ liệu tỉnh thành
		getState: function(map,arr){
			var state='',boolState = false,state2 = '',state='',boolAll = false,that = this,
					district = '',boolDistrict = false,district2 = '',district = '',html_store_item = '',
					infoWindow = new google.maps.InfoWindow(),marker;
			var imagePath = {
				url: "//file.hstatic.net/1000075078/file/group_2773.svg", // url
				scaledSize: new google.maps.Size(24, 24), // scaled size
				origin: new google.maps.Point(0,0), // origin
				anchor: new google.maps.Point(0, 0) // anchor
			};
			// Sort Data trả về theo Tỉnh thành và quận huyện
			var arrStore = arr.sort(function(a,b){
				if(a.district_name === null){
					return 1;
				}else if(b.district_name === null){
					return -1;
				}else{
					return a.state_name.localeCompare(b.state_name) || a.district_name.localeCompare(b.district_name);
				}
			});
			// Each
			$.each(arrStore,function(i,store){
				state2=state.split('-');district2=district.split('-');
				if (store.id == '367' || store.district_name == null) return true;
				for(var c=0;c < state2.length-1;c++) {
					if( state2[c].toLowerCase() == store.state_name.toLowerCase()) {
						boolState=true;
						break;
					}else{
						boolAll = true;
					}
				}
				for(var c=0;c < district2.length-1;c++ ) {

					if(district2[c].toLowerCase() == store.district_name.toLowerCase()) {
						boolDistrict=true;
						break;
					}else{
						boolAll = true;
					}
				}
				if(boolState == false && boolDistrict == false && boolAll == true){
					html_store_item += '</div>';
				}
				if(boolState == false ){
					html_store_item += '<div class="dropdown_select_store_item" data-state="'+that.slug(store.state_name)+'"><div class="dropdown_select_store_state">'+store.state_name+'</div>';
				}
				if(boolDistrict == false ){
					html_store_item += '<div class="dropdown_select_store_district" data-district="'+store.district_name+'">'+store.district_name+'</div>';
				}
				boolState = false;boolDistrict = false;boolAll=false;
				state = state+store.state_name+'-';
				if(store.district_name == null){
					district = district+'null'+'-';
				}else{
					district = district+store.district_name+'-';
				}

				marker = new google.maps.Marker({
					position:  new google.maps.LatLng(store.latitude,store.longitude),
					title: store.external_name,
					icon:imagePath,
					map: map
				});
				google.maps.event.addListener(marker, 'click', (function(marker) {
					return function() {
						infoWindow.setContent("<h3 style='text-align: center;margin: 0;font-size:14px;color:#000;'>" + store.external_name + "</h3><p style='margin: 0;'>" + store.street + "</p>");
						infoWindow.open(map, marker);
					}
				})(marker));
			});
			$('.dropdown_select_store').html(html_store_item);
		},
		// Thêm button lấy GPS
		addYourLocationButton: function(map,marker,arr) {
			var that = this;
			var controlDiv = document.createElement('div');

			var firstChild = document.createElement('button');
			firstChild.style.backgroundColor = '#fff';
			firstChild.style.border = 'none';
			firstChild.style.outline = 'none';
			firstChild.style.width = '28px';
			firstChild.style.height = '28px';
			firstChild.style.borderRadius = '2px';
			firstChild.style.boxShadow = '0 1px 4px rgba(0,0,0,0.3)';
			firstChild.style.cursor = 'pointer';
			firstChild.style.marginRight = '10px';
			firstChild.style.padding = '0px';
			firstChild.title = 'Your Location';
			firstChild.id = 'geolocationBtn';
			controlDiv.appendChild(firstChild);

			var secondChild = document.createElement('div');
			secondChild.style.margin = '5px';
			secondChild.style.width = '18px';
			secondChild.style.height = '18px';
			secondChild.style.backgroundImage = 'url(https://maps.gstatic.com/tactile/mylocation/mylocation-sprite-1x.png)';
			secondChild.style.backgroundSize = '180px 18px';
			secondChild.style.backgroundPosition = '0px 0px';
			secondChild.style.backgroundRepeat = 'no-repeat';
			secondChild.id = 'geolocationIcon';
			firstChild.appendChild(secondChild);

			controlDiv.index = 1;
			map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(controlDiv);

			google.maps.event.addListener(map, 'dragend', function() {
				$('#geolocationIcon').css('background-position', '0px 0px');
			});
			firstChild.addEventListener('click', function() {
				that.getMyLocation(marker,function(position){
					var position_lat = '', position_lng = '';
					if(position.code == 1){
						position_lat == ''; position_lng == '';
					}else{
						position_lat = position.coords.latitude;
						position_lng = position.coords.longitude;
					}
					that.loopStore(arr,position_lat,position_lng);
				});
			});
		},
		// Lấy dữ liệu GPS
		getMyLocation: function(marker,callback){
			var imgX = '0';
			var animationInterval = setInterval(function(){
				if(imgX == '-18') imgX = '0';
				else imgX = '-18';
				$('#geolocationIcon').css('background-position', imgX+'px 0px');
			}, 500);

			if(navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
					marker.setPosition(latlng);
					clearInterval(animationInterval);
					$('#geolocationIcon').css('background-position', '-144px 0px');
					callback(position);
				},function(position){
					clearInterval(animationInterval);
					$('#geolocationIcon').css('background-position', '-144px 0px');
					callback(position);
				});
			}
			else{
				clearInterval(animationInterval);
				$('#geolocationIcon').css('background-position', '0px 0px');
			}
		},
		// Get Store Default
		getStoreDefault: function(arr){
			var html = '',img1='',img2='',img3='';
			$.each(arr,function(i,store){
				if(store.id == '367') return true;
				if(store.state_name == 'Hồ Chí Minh'){
					if (store.images[0] != undefined) { img1 = store.images[0].replace(/(^\w+:|^)/, ''); } else { img1 = '' ; }
					if (store.images[1] != undefined) { img2 = store.images[1].replace(/(^\w+:|^)/, ''); } else { img2 = '' ; }
					if (store.images[2] != undefined) { img3 = store.images[2].replace(/(^\w+:|^)/, ''); } else { img3 = '' ; }
					html += '<li class="item"><div class="img-store">';
					html += '<img src="'+store.images[0].replace(/(^\w+:|^)/, '') +'" alt="'+store.external_name+'"/></div><div class="info">';
					html += '<h3>'+store.external_name+'</h3><p>'+store.street + ', ' + store.state_name +'</p>';
					html += '<a class="view_popup_store" onclick="TCH.openModal(this)" href="javascript:void(0)" data-name="'+store.external_name+'" data-fulladdress="'+store.address.full_address+'" data-image1="'+img1+'" data-image2="'+ img2 +'" data-image3="'+img3+'" data-latlng="'+store.latitude+','+store.longitude+'">Chi tiết cửa hàng</a></div></li>';
				}
			})
			$('.list-address ul').html(html);
		},
		// Loop data Store
		loopStore: function(arr,position_lat,position_lng){
			var that=this,distanceKM='',arrLists=[],i,html='',img1='',img2='',img3='';
			var directionsService = new google.maps.DirectionsService;
			var directionsDisplay = new google.maps.DirectionsRenderer;
			$.each(arr,function(i,store){
				if(position_lat == '' && position_lng == ''){

				}else{
					$('.list-address').show();
					/*distanceKM = that.getDistanceFromLatLonInKm(Number(position.coords.latitude),Number(position.coords.longitude), Number(store.latitude), Number(store.longitude));*/
					var formLatLng = new google.maps.LatLng(Number(position_lat),Number(position_lng));
					var toLatLng = new google.maps.LatLng(Number(store.latitude), Number(store.longitude));
					distanceKM = google.maps.geometry.spherical.computeDistanceBetween(formLatLng,toLatLng);
					arrLists.push({'index':i,'id':store.id,'met':distanceKM});
				}
			});
			if(arrLists.length != 0){
				arrLists.sort(function(a,b){
					return a.met - b.met;
				});
				$.each(arrLists,function(i,store){
					var changeMetToKM = 0;
					if(store.id == '367'){
						return true;
					}
					if (store.met < 1000){
						changeMetToKM = Math.round(store.met) + 'm';
					}else{
						changeMetToKM = Math.round((store.met / 1000) * 100) / 100 + ' km';
					}
					if (arr[store.index].images[0] != undefined) { img1 = arr[store.index].images[0].replace(/(^\w+:|^)/, ''); } else { img1 = '' ; }
					if (arr[store.index].images[1] != undefined) { img2 = arr[store.index].images[1].replace(/(^\w+:|^)/, ''); } else { img2 = '' ; }
					if (arr[store.index].images[2] != undefined) { img3 = arr[store.index].images[2].replace(/(^\w+:|^)/, ''); } else { img3 = '' ; }
					html += '<li class="item"><div class="img-store">';
					html += '<img src="'+arr[store.index].images[0].replace(/(^\w+:|^)/, '') +'" alt="'+arr[store.index].external_name+'"/></div><div class="info">';
					html += '<h3>'+arr[store.index].external_name+'</h3><p>'+arr[store.index].street + ', ' + arr[store.index].state_name +'</p>';
					html += '<a class="view_popup_store" onclick="TCH.openModal(this)" href="javascript:void(0)" data-name="'+arr[store.index].external_name+'" data-fulladdress="'+arr[store.index].address.full_address+'" data-image1="'+img1 +'" data-image2="'+img2 +'" data-image3="'+img3 +'" data-latlng="'+arr[store.index].latitude+','+arr[store.index].longitude+'">Chi tiết cửa hàng</a><span>'+ changeMetToKM +' </span></div></li>';
					if(i == 4) return false;
				})
				$('.list-address ul').html(html);
			}
		},
		// Loop Store by District
		loopStoreDistrict: function(map,arr,district_select,position_lat,position_lng){
			var that = this,html='',count = 0,distanceKM='',changeMetToKM='',
					img1 = '',img2 = '',img3 = '';
			$.each(arr,function(i,store){
				var str = '';
				if(position_lat == '' || position_lng == ''){
					if(store.district_name == null)
						str == '';
					else
						str = store.district_name.toLowerCase();

					if(str == district_select){
						if (store.images[0] != undefined) { img1 = store.images[0].replace(/(^\w+:|^)/, ''); } else { img1 = '' ; }
						if (store.images[1] != undefined) { img2 = store.images[1].replace(/(^\w+:|^)/, ''); } else { img2 = '' ; }
						if (store.images[2] != undefined) { img3 = store.images[2].replace(/(^\w+:|^)/, ''); } else { img3 = '' ; }
						html += '<li class="item"><div class="img-store">';
						html += '<img src="'+store.images[0].replace(/(^\w+:|^)/, '') +'" alt="'+store.external_name+'"/></div><div class="info">';
						html += '<h3>'+store.external_name+'</h3><p>'+store.street + ', ' + store.state_name +'</p>';
						html += '<a class="view_popup_store" onclick="TCH.openModal(this)" href="javascript:void(0)" data-name="'+store.external_name+'" data-fulladdress="'+store.address.full_address+'" data-image1="'+img1 +'" data-image2="'+ img2 +'" data-image3="'+img3 +'" data-latlng="'+store.latitude+','+store.longitude+'">Chi tiết cửa hàng</a></div></li>';
						count++;
					}
				}else{
					if(store.district_name == null)
						str == '';
					else
						str = store.district_name.toLowerCase();

					if(str == district_select){
						if (store.images[0] != undefined) { img1 = store.images[0].replace(/(^\w+:|^)/, ''); } else { img1 = '' ; }
						if (store.images[1] != undefined) { img2 = store.images[1].replace(/(^\w+:|^)/, ''); } else { img2 = '' ; }
						if (store.images[2] != undefined) { img3 = store.images[2].replace(/(^\w+:|^)/, ''); } else { img3 = '' ; }
						var formLatLng = new google.maps.LatLng(Number(position_lat),Number(position_lng));
						var toLatLng = new google.maps.LatLng(Number(store.latitude), Number(store.longitude));
						distanceKM = google.maps.geometry.spherical.computeDistanceBetween(formLatLng,toLatLng);

						if (distanceKM < 1000){
							changeMetToKM = Math.round(distanceKM) + 'm';
						}else{
							changeMetToKM = Math.round((distanceKM / 1000) * 100) / 100 + ' km';
						}
						html += '<li class="item"><div class="img-store">';
						html += '<img src="'+store.images[0].replace(/(^\w+:|^)/, '') +'" alt="'+store.external_name+'"/></div><div class="info">';
						html += '<h3>'+store.external_name+'</h3><p>'+store.street + ', ' + store.state_name +'</p>';
						html += '<a class="view_popup_store" onclick="TCH.openModal(this)" href="javascript:void(0)" data-name="'+store.external_name+'" data-fulladdress="'+store.address.full_address+'" data-image1="'+img1+'" data-image2="'+ img2 +'" data-image3="'+img3+'" data-latlng="'+store.latitude+','+store.longitude+'">Chi tiết cửa hàng</a><span>'+ changeMetToKM +' </span></div></li>';
						count++;
					}
				}
			});
			$('.list-address ul').html(html);$('.list-address').show();
		},
		openModal: function(ele){
			var name = $(ele).data("name"),
					fullAddress = $(ele).data("fulladdress"),
					image1 = $(ele).data("image1"),
					image2 = $(ele).data("image2"),
					image3 = $(ele).data("image3"),
					latlng = $(ele).data("latlng"),
					html_right = '<div class="swiper-container"><div class="swiper-wrapper">';
			if(image1 != '' || image2 != '' || image3 != ''){
				if(image1 != ''){
					html_right += '<div class="swiper-slide modal_map_image_item"><div class="modal_map_bg_image" style="background-image:url('+image1.replace(/(^\w+:|^)/, '')+')"></div></div>';
				}
				if(image2 != ''){
					html_right += '<div class="swiper-slide modal_map_image_item"><div class="modal_map_bg_image" style="background-image:url('+image2.replace(/(^\w+:|^)/, '')+')"></div></div>';
				}
				if(image3 != ''){
					html_right += '<div class="swiper-slide modal_map_image_item"><div class="modal_map_bg_image" style="background-image:url('+image3.replace(/(^\w+:|^)/, '')+')"></div></div>';
				}
			}
			html_right += '</div><div class="modal-button-prev swiper-button-prev"><i class="fas fa-caret-left fa-7x"></i></div><div class="modal-button-next swiper-button-next"><i class="fas fa-caret-right fa-7x"></i></div></div>';
			if(name != ''){$('.title_map_modal').html(name);}
			if(fullAddress != ''){
				$('.modal_map_address').html('<i class="fas fa-map-marker-alt"></i><span>' + fullAddress + '</span>');
			}
			if(latlng != '' || latlng != null){
				$('.btn_open_map').attr('href','https://www.google.com/maps/search/?api=1&query='+latlng);
			}

			$('.modal_map_image').html(html_right);

			$('#modalMap').modal('show');
			setTimeout(function(){
				var swiper = new Swiper('.modal_map_image .swiper-container', {
					slidesPerView: 'auto',
					spaceBetween: 30,
					pagination: {
						el: '.swiper-pagination',
						clickable: false,
					},
					navigation: {
						nextEl: '.modal-button-next',
						prevEl: '.modal-button-prev',
					},
				});

			},300);
		},
		initMap: function(){
			var arrStore = window.stores;

			// Khai báo biến
			var that = this,
					start_lng="106.687147000",start_lat="10.773043000";
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom: 7,
				center: new google.maps.LatLng(10.9777365,104.9041829),
			});
			var marker = new google.maps.Marker({
				map: map,
				animation: google.maps.Animation.DROP
			});

			// Đổ dữ liệu của tỉnh thành
			that.getState(map,arrStore);

			// Thêm btn lấy vị trí hiện tại
			that.addYourLocationButton(map,marker,arrStore);

			// Kiểm tra và lấy vị trị hiện tại
			that.getMyLocation(marker,function(position){
				var position_lat = '', position_lng = '';

				if(position.code == 1){
					position_lat == ''; position_lng == '';
					that.getStoreDefault(arrStore);
				}else{
					position_lat = position.coords.latitude;
					position_lng = position.coords.longitude;
				}
				that.loopStore(arrStore,position_lat,position_lng);
			});

			$('.btn_select_store').on('click',function(){
				if($(this).parents('.group_select_store').hasClass('open')){
					$(this).next().slideUp('fast');
					$(this).parents('.group_select_store').removeClass('open');
				}else{
					$(this).parents('.group_select_store').addClass('open');
					$(this).next().css({'display':'-ms-flexbox', 'display':'-webkit-flex', 'display':'flex'});
				}
			});
			$('.dropdown_select_store_district').on('click',function(){
				var district = $(this).data('district').toLowerCase();
				$(this).parents('.group_select_store').find('.btn_select_store').html($(this).text());
				$(this).parents('.group_select_store').find('.dropdown_select_store').slideUp('fast');
				$(this).parents('.group_select_store').removeClass('open');
				that.getMyLocation(marker,function(position){
					var position_lat = '', position_lng = '';
					if(position.code == 1){
						position_lat == ''; position_lng == '';
					}else{
						position_lat = position.coords.latitude;
						position_lng = position.coords.longitude;
					}
					that.loopStoreDistrict(map,arrStore,district,position_lat,position_lng);
				});
			});	
		},
		slug: function(str) {
			str = str.toLowerCase();
			str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
			str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
			str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
			str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
			str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
			str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
			str = str.replace(/đ/g, "d");
			str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'| |\"|\&|\#|\[|\]|~|$|_/g, "-");
			str = str.replace(/-+-/g, "-"); //thay thế 2- thành 1- 
			str = str.replace(/^\-+|\-+$/g, "");
			return str;
		},
		initAllStore: function(){
			var that = this;
			var state='',boolState=false,state2='',state='',boolAll=false,boolStore = false,
					district='',boolDistrict=false,district2='',district='',html_store_item='',img1='',img2='',img3='';
			// Sort Data trả về theo Tỉnh thành và quận huyện
			var arrStore = window.stores.sort(function(a,b){
				if(a.district_name === null){
					return 1;
				}else if(b.district_name === null){
					return -1;
				}else{
					return a.state_name.localeCompare(b.state_name) || a.district_name.localeCompare(b.district_name);
				}
			});
			// Each
			$.each(arrStore,function(i,store){
				state2=state.split('-');district2=district.split('-');
				if (store.id == '367' || store.district_name == null) return true;
				for(var c=0;c < state2.length-1;c++) {
					if( state2[c].toLowerCase() == store.state_name.toLowerCase()) {
						boolState=true;
						break;
					}else{
						boolAll = true;
					}
				}
				for(var c=0;c < district2.length-1;c++ ) {
					var checkNull = '';
					if(district2[c].toLowerCase() == store.district_name.toLowerCase()) {
						boolDistrict=true;
						break;
					}else{
						boolAll = true;
						boolStore = false;
					}
				}
				if(boolState == true && boolDistrict == false && boolStore == false){
					html_store_item += '</div></div>';
				}
				if(boolState == false && boolDistrict == false && boolAll == true){
					html_store_item += '</div></div></div></div>';
				}
				if(boolState == false ){
					html_store_item += '<div class="list_all_store_item" data-state="'+that.slug(store.state_name)+'"><h2 class="list_all_store_state section_heading line_after_heading line_after_heading_section">'+store.state_name+'</h2><div class="lists_all_store_district clearfix">';
				}
				if(boolDistrict == false ){
					html_store_item += '<div class="list_all_store_district"><h3 class="list_all_store_district_name" data-district="'+store.district_name+'">'+store.district_name+'</h3><div class="list_all_store">';
					boolStore == true;
				}
				if (store.images[0] != undefined) { img1 = store.images[0].replace(/(^\w+:|^)/, ''); } else { img1 = '' ; }
				if (store.images[1] != undefined) { img2 = store.images[1].replace(/(^\w+:|^)/, ''); } else { img2 = '' ; }
				if (store.images[2] != undefined) { img3 = store.images[2].replace(/(^\w+:|^)/, ''); } else { img3 = '' ; }
				html_store_item += '<a onclick="TCH.openModal(this)" href="javascript:void(0)" data-name="'+store.external_name+'" data-fulladdress="'+store.address.full_address+'" data-image1="'+img1.replace(/(^\w+:|^)/, '')+'" data-image2="'+ img2.replace(/(^\w+:|^)/, '') +'" data-image3="'+img3.replace(/(^\w+:|^)/, '')+'" data-latlng="'+store.latitude+','+store.longitude+'"><i class="fas fa-map-marker-alt"></i><span>'+store.external_name+'</span></a>';

				boolState = false;boolDistrict = false;boolAll=false;
				state = state+store.state_name+'-';
				if(store.district_name == null){
					district = district+'null'+'-';
				}else{
					district = district+store.district_name+'-';
				}
			});
			$('.list_all_store_wrap').html(html_store_item);

			$('.lists_all_store_district').masonry({
				itemSelector: '.list_all_store_district'
			});
		},
		getStoreIndex: function(){
			var html = '';	
			// Sort Data theo id
			var arrStore = window.stores.sort(function(a,b){
				return b.id - a.id;
			});
			$.each(arrStore,function(i,store){
				html += '<div class="store_slider_item"><div class="store_slider_image" style="background-image: url('+store.images[0].replace(/(^\w+:|^)/, '')+');"></div>';
				html += '<div class="store_name">'+store.external_name+'</div></div>';
				if(i == 8) return false;
			});
			$('.store_slider_2').html(html);
			$('.store_slider_2').owlCarousel({
				nav : true,
				dots : false,
				margin: 30,
				loop: true,
				mouseDrag: false,
				items: 4,
				autoplayHoverPause: true,
				autoplay: true,
				navText: ['<i class="fas fa-angle-left"></i>','<i class="fas fa-angle-right"></i>'],
				autoplayTimeout: 7000,
				responsive : {
					// breakpoint from 0 up
					0 : {
						items: 2,
					},
					// breakpoint from 480 up
					480 : {
						items: 2,
					},
					// breakpoint from 768 up
					768 : {
						items: 3,
					},
					991 : {
						items: 4,
					}
				}
			});
		},
		getImageInstagram: function() {
			var feed = new Instafeed({
				get: 'user',
				limit: "8",
				userId: "4087605087",
				//clientId: 'c35f9d868c48411eb187774ec6daa06a',
				accessToken: "4087605087.ad0b1c2.294330447a8542e6943ab0c04bfc2284",
				target: 'instafeed',
				resolution: 'standard_resolution',
				template: '<a class="instagram_item" href="{{link}}" target="_blank"><div class="instagram_image" style="background-image: url({{image}})"></div><div class="instagram_overlay"><span class="instagram_likecount"><svg class="svg-logo"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#icon-heart"></use></svg>{{likes}} likes</span><p class="instagram_caption">{{caption}}</p></div></a>',
				before: function() {
					$('#instafeed').html('<div class="icon-loading"><div class="uil-ring-css"><div></div></div></div>');
				},after: function() {
					$('.icon-loading').remove();
				}
			});
			feed.run();
		},
	}
	$(document).ready(function(){
		$.when(getApiStore()).then(function () {
			TCH.init();
		});
		new WOW().init();
	});
})(jQuery, window);