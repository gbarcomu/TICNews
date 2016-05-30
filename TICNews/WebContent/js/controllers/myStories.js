app.controller("myStoriesCtrl",['storiesFactory', '$routeParams', '$location', function(storiesFactory, $routeParams, $location) {
	var vm = this;
	vm.stories = [];
	vm.storiesByUser = [];
	vm.myStories = true;

	vm.funciones = {

		getStories : function() {
			storiesFactory.getStories().then(function(respuesta) {
				vm.stories = respuesta;
				
				for (var i = 0; i < vm.stories.length; i++) {	
					console.log("Trayendo las noticias de: ", $routeParams.id);
					if (vm.stories[i].value.id == $routeParams.id) {
						vm.storiesByUser.push(vm.stories[i]);
					}
				}
				vm.stories = vm.storiesByUser;
								
			}, function(respuesta) {
				console.log("Error obteniendo noticias");
			})			
		},
	
		deleteStory : function(id) {

			storiesFactory.deleteStory(id).then(function(respuesta){
			console.log("Borrando noticia");
			$location.path('/');
			}, function(respuesta){
				console.log("Error borrar noticia");
			})
		},
	}
	vm.funciones.getStories();
}]);