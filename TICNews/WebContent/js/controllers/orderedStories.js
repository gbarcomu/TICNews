app.controller("orderedStoriesCtrl",['storiesFactory', function(storiesFactory) {
	var vm = this;
	vm.stories = [];
	vm.myStories = false;

	vm.funciones = {

		getStories : function() {
			storiesFactory.getStoriesByLikes().then(function(respuesta) {
				console.log("Trayendo todas las noticias: ", respuesta);
				vm.stories = respuesta;
			}, function(respuesta) {
				console.log("Error obteniendo noticias");
			})
		},
	
		like : function(index) {
		
			console.log("Le has dado un like");
			vm.stories[index].key.likes = vm.stories[index].key.likes + 1;
			storiesFactory.editStory(vm.stories[index].key).then(function(respuesta) {
				console.log("Actualizando");
			}, function(respuesta) {
				console.log("Error actualizando");
			})
		},

		dislike : function(index) {
	
			console.log("Le has dado un dislike");
			vm.stories[index].key.likes = vm.stories[index].key.likes - 1;
			storiesFactory.editStory(vm.stories[index].key).then(function(respuesta) {
				console.log("Actualizando");
			}, function(respuesta) {
				console.log("Error actualizando");
			})
		}
	}
	vm.funciones.getStories();
}]);