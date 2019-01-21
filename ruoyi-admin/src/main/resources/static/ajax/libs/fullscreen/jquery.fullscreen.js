/**
 * 基于jQuery FullScreen修改
 * 新增支持IE全屏显示
 * Copyright (c) 2019 ruoyi
 */

/*jshint browser: true, jquery: true */
(function($){
	"use strict";

	// These helper functions available only to our plugin scope.
	function supportFullScreen(){
		var doc = document.documentElement;

		return ('requestFullscreen' in doc) ||
		        ('msRequestFullscreen' in doc) ||
				('mozRequestFullScreen' in doc && document.mozFullScreenEnabled) ||
				('webkitRequestFullScreen' in doc);
	}

	function requestFullScreen(elem){
		if (elem.requestFullscreen) {
			elem.requestFullscreen();
		} else if (elem.mozRequestFullScreen) {
			elem.mozRequestFullScreen();
		} else if (elem.webkitRequestFullScreen) {
			elem.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
		} else if (elem.msRequestFullscreen) {
			elem.msRequestFullscreen();
		}
	}

	function fullScreenStatus(){
		return document.fullscreen ||
				document.mozFullScreen ||
				document.webkitIsFullScreen ||
				document.msFullscreenElement ||
				false;
	}

	function cancelFullScreen(){
		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitCancelFullScreen) {
			document.webkitCancelFullScreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		}
	}

	function onFullScreenEvent(callback){
		$(document).on("fullscreenchange mozfullscreenchange webkitfullscreenchange", function(){
			// The full screen status is automatically
			// passed to our callback as an argument.
			callback(fullScreenStatus());
		});
	}

	// Adding a new test to the jQuery support object
	$.support.fullscreen = supportFullScreen();

	// Creating the plugin
	$.fn.fullScreen = function(props){
		if(!$.support.fullscreen || this.length !== 1) {
			// The plugin can be called only
			// on one element at a time

			return this;
		}

		if(fullScreenStatus()){
			// if we are already in fullscreen, exit
			cancelFullScreen();
			return this;
		}

		// You can potentially pas two arguments a color
		// for the background and a callback function

		var options = $.extend({
			'background'      : '#111',
			'callback'        : $.noop( ),
			'fullscreenClass' : 'fullScreen'
		}, props),

		elem = this,

		// This temporary div is the element that is
		// actually going to be enlarged in full screen

		fs = $('<div>', {
			'css' : {
				'overflow-y' : 'auto',
				'background' : options.background,
				'width'      : '100%',
				'height'     : '100%'
			}
		})
			.insertBefore(elem)
			.append(elem);

		// You can use the .fullScreen class to
		// apply styling to your element
		elem.addClass( options.fullscreenClass );

		// Inserting our element in the temporary
		// div, after which we zoom it in fullscreen

		requestFullScreen(fs.get(0));

		fs.click(function(e){
			if(e.target == this){
				// If the black bar was clicked
				cancelFullScreen();
			}
		});

		elem.cancel = function(){
			cancelFullScreen();
			return elem;
		};

		onFullScreenEvent(function(fullScreen){
			if(!fullScreen){
				// We have exited full screen.
			        // Detach event listener
			        $(document).off( 'fullscreenchange mozfullscreenchange webkitfullscreenchange' );
				// Remove the class and destroy
				// the temporary div

				elem.removeClass( options.fullscreenClass ).insertBefore(fs);
				fs.remove();
			}

			// Calling the facultative user supplied callback
			if(options.callback) {
                            options.callback(fullScreen);
                        }
		});

		return elem;
	};

	$.fn.cancelFullScreen = function( ) {
			cancelFullScreen();

			return this;
	};
}(jQuery));
