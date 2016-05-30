app.controller("usersDetailCtrl",['usersFactory', '$routeParams', function(usersFactory, $routeParams) {
	var vm = this;
	vm.user = {};

	vm.funciones = {

		getUser : function(id) {
			usersFactory.getUsersById(id).then(function(respuesta) {
				console.log("Trayendo el usuario con id: ", id);
				vm.user = respuesta;
			}, function(respuesta) {
				console.log("Error obteniendo el usuario con id", id);
			})
		}
	}
	vm.funciones.getUser($routeParams.id);
}]);