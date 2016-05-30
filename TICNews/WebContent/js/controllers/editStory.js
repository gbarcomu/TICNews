app.controller("editStoryCtrl",['storiesFactory', 'usersFactory', '$routeParams', '$location', function(storiesFactory, usersFactory, $routeParams, $location) {
	var vm = this;
	vm.story = {};

	vm.funciones = {	
			createEditStory : function() {
			
			usersFactory.checkSession().then(function(respuesta){
				console.log("Obteniendo sesion", respuesta.id);
				vm.story.owner = respuesta.id;
				
				storiesFactory.editStory(vm.story).then(function(respuesta){
					console.log("Anadiendo noticia");
					$location.path('/');
					}, function(respuesta){
					console.log("Error anadir noticia");
					})								
				}, function(respuesta){
				console.log("Error obteniendo sesion");
				})
		},
		
		loadStory : function(id) {
			console.log (id);
			storiesFactory.getStoryById(id).then(function(respuesta){
			console.log("Trayendo noticia");
			vm.story = respuesta;
			}, function(respuesta){
			console.log("Error trayendo noticia");
			})
		},
	}
	vm.funciones.loadStory($routeParams.id);
}]);