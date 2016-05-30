app.controller("storyCommentsCtrl",['storiesFactory','commentsFactory','usersFactory','$routeParams','$window', 
                                    function(storiesFactory, commentsFactory, usersFactory, $routeParams, $window) {
	var vm = this;
	vm.story = {};
	vm.user = {};
	vm.comments = [];
	vm.newComment = {};
	vm.session = {};

	vm.funciones = {	
		getStoryById : function(id) {
			storiesFactory.getStoryById(id).then(function(respuesta){
			console.log("Trayendo la noticia con id: ", id);
			vm.story = respuesta;
			vm.funciones.getStoryOwner(vm.story.owner);
			}, function(respuesta){
			console.log("Error obteniendo la noticia con id", id);
			})
		},
		
		getStoryOwner : function(id) {
			usersFactory.getUsersById(id).then(function(respuesta){
			console.log("Trayendo el usuario con id: ", id);
			vm.user = respuesta;
			}, function(respuesta){
			console.log("Error obteniendo el usuario con id", id);
			})
		},
	
		getCommentsById : function(id) {
			commentsFactory.getCommentsById(id).then(function(respuesta){
				console.log("Trayendo los comentarios con id: ", id);
				vm.comments = respuesta;
			}, function(respuesta){
				console.log("Error obteniendo los comentarios con id", id);
			})
		},
		
		addComment : function(id) {

			vm.newComment.news = vm.story.id;
			
			usersFactory.checkSession().then(function(respuesta){
			
			console.log("Obteniendo sesion", respuesta.id);
			vm.newComment.owner = respuesta.id;

			commentsFactory.postComment(vm.newComment).then(function(respuesta){
				console.log("Anadiendo comentario");
				$window.location.reload();
			}, function(respuesta){
				console.log("Error anadiendo comentario");
			},	function(respuesta) {
				console.log("Error obteniendo sesion");
			})})
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
	vm.funciones.getStoryById($routeParams.id);
	vm.funciones.getCommentsById($routeParams.id);
}]);