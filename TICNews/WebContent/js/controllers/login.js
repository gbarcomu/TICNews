app.controller("loginCtrl",['usersFactory', '$routeParams', '$window', '$location', function(usersFactory, $routeParams, $window, $location) {
	var vm = this;
	vm.user = {};
	vm.error = false;
	vm.session = {};
	vm.register = false;

	vm.funciones = {

		login : function() {
			usersFactory.login(vm.user).then(function(respuesta) {
				console.log("Login usuario");
				
				usersFactory.checkSession().then(function(respuesta) {
					vm.session = respuesta;
					if(vm.session.id == -1) {					
						console.log("Usuario o pass incorrecto");
						vm.error = true;
					}
					else {
						console.log("Usuario o pass correcto");
						$window.location.href='#/';
						$window.location.reload();					
					}
				})				


			}, function(respuesta) {
				console.log("Error login usuario");
			})
		},
		
		logout : function() {
			usersFactory.logout().then(function(respuesta) {
				console.log("Logout usuario");
				$window.location.reload();
			}, function(respuesta) {
				console.log("Error logout usuario");
			})
		},
		
		checkSession : function() {
			usersFactory.checkSession().then(function(respuesta) {
				console.log("Comprobando sesion");
				vm.session = respuesta;
			}, function(respuesta) {
				console.log("Error comprobando sesion");
			})
		}
	}
	
	vm.funciones.checkSession();
}]);