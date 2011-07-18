$(document).ready(function() {
	$('#user-panel').click(function(event) {
		var userMenu = $('#user-menu');
		if (userMenu.css("display") == 'none') {
			$(this).addClass('focus');
			userMenu.css('display', 'block');
			event.stopPropagation();
		} else {
			hideUserMenu();
		}
	});
	
	$('body').click(function() {
		if ($('#user-menu').css("display") == 'block') {
			hideUserMenu();
		}
	});
});

function hideUserMenu() {
	$('#user-panel').removeClass('focus');
	$('#user-menu').css('display', 'none');
}