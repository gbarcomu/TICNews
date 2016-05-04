angular.module('TICNewsApp', [ "ngRoute" ]).config(function($routeProvider) {
	$routeProvider.when("/", {
		controller : "storiesCtrl",
		controllerAs : "vm",
		templateUrl : "pages/stories.html",
		resolve : {
			delay : function($q, $timeout) {
				var delay = $q.defer();
				$timeout(delay.resolve, 100);
				return delay.promise;
			}
		}
	})
	    	
	.when("/story/:id", {           
		controller: "storyCommentsCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/storyWithComments.html"        
	})
})

.factory("storiesFactory", function($http) {
	var url = 'http://localhost:8080/TICNews/rest/stories/';

	var interfaz = {

		getStories : function() {
			return $http.get(url).then(function(response) {
				return response.data;
			});
		},

		getStoryById : function(id) {
			var urlid = url + id;
			return $http.get(urlid).then(function(response) {
				return response.data;
			});
		},
	}
	return interfaz;
})

.factory("commentsFactory", function($http) {
	var url = 'http://localhost:8080/TICNews/rest/comments/';

	var interfaz = {

		getCommentsById : function(id) {
			var urlid = url + id;
			return $http.get(urlid).then(function(response) {
				return response.data;
			});
		}
	}
	return interfaz;
})



.controller("storiesCtrl", function(storiesFactory) {
	var vm = this;
	vm.stories = [];

	vm.funciones = {

		getStories : function() {
			storiesFactory.getStories().then(function(respuesta) {
				console.log("Trayendo todas las noticias: ", respuesta);
				vm.stories = respuesta;
			}, function(respuesta) {
				console.log("Error obteniendo noticias");
			})
		}
	}
	vm.funciones.getStories();
})

.controller("storyCommentsCtrl", function(storiesFactory, commentsFactory, $routeParams) {
	var vm = this;
	vm.story = {};
	vm.comments = []

	vm.funciones = {	
		getStoryById : function(id) {
			storiesFactory.getStoryById(id).then(function(respuesta){
			console.log("Trayendo la noticia con id: ", id);
			vm.story = respuesta;
			}, function(respuesta){
			console.log("Error obteniendo la noticia con id", id);
			})
		},
	
		getCommentsById : function(id) {
			commentsFactory.getCommentsById(id).then(function(respuesta){
				console.log("Trayendo los comentarios con id: ", id);
				vm.comments = respuesta;
			}, function(respuesta){
				console.log("Error obteniendo los comentarios con id", id);
			})
		}
	}
	vm.funciones.getStoryById($routeParams.id);
	vm.funciones.getCommentsById($routeParams.id);
})