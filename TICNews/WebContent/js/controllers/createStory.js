app.controller("createStoryCtrl",['storiesFactory', 'usersFactory', '$location', function(storiesFactory, usersFactory, $location) {
	var vm = this;
	vm.story = {};

	vm.funciones = {	
			createEditStory : function() {
				usersFactory.checkSession().then(function(respuesta){
					console.log("Obteniendo sesion", respuesta.id);
					vm.story.owner = respuesta.id;
					console.log(vm.story.owner);
					storiesFactory.createStory(vm.story).then(function(respuesta){
						console.log("Anadiendo noticia");
						$location.path('/');
					}, function(respuesta){
						console.log("Error anadir noticia");
					})					
					}, function(respuesta){
					console.log("Error obteniendo sesion");
					})				
		},		
	}	
}]);