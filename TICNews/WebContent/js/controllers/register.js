app.controller("registerCtrl",['usersFactory', '$routeParams', '$window', '$location', function(usersFactory, $routeParams, $window, $location) {
	var vm = this;
	vm.user = {};
	vm.session = {};
	vm.register = true;

	vm.funciones = {

		login : function() {
			usersFactory.register(vm.user).then(function(respuesta) {
				console.log("Registro usuario");
				$window.location.href='#/';
				$window.location.reload();
			}, function(respuesta) {
				console.log("Error registro usuario");
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