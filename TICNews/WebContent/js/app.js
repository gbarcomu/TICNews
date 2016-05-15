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
		
	.when("/user/:id", {    			
		controller: "usersDetailCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/user.html"        
	})
	
	.when("/login", {    			
		controller: "loginCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/login.html"        
	})
	
	.when("/myStories/:id", {    			
		controller: "myStoriesCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/stories.html"        
	})
	
	.when("/editStory/:id", {    			
		controller: "editStoryCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/createEditStory.html"        
	})
	
	.when("/register", {    			
		controller: "registerCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/login.html"        
	})
	
	.when("/newStory", {    			
		controller: "createStoryCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/createEditStory.html"        
	});
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
		
		createStory : function(story) {
			return $http.post(url, story).then(function(response) {
				return response.data;
			});
		},
		
		editStory : function(story) {
			return $http.put(url, story).then(function(response) {
				return response.data;
			});
		},
		
		deleteStory : function(id) {
			var urlid = url + id;
			return $http.delete(urlid).then(function(response) {
				return response.data;
			});
		}
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
		},
	
		postComment : function(comment) {
			return $http.post(url, comment).then(function(response) {
				return response.data;
			});
		}
	}
	return interfaz;
})

.factory("usersFactory", function($http) {
	var url = 'http://localhost:8080/TICNews/rest/users/';

	var interfaz = {

		getUsersById : function(id) {
			var urlid = url + id;
			return $http.get(urlid).then(function(response) {
				return response.data;
			});
		},
	
		login : function(user) {
			return $http.put(url, user).then(function(response) {
				return response.data;
			});
		},
		
		checkSession : function() {
			return $http.get(url).then(function(response) {
				return response.data;
			});						
		},
		
		logout : function() {
			return $http.delete(url).then(function(response) {
				return response.data;
			});						
		},
		
		register : function(user) {
			return $http.post(url, user).then(function(response) {
				return response.data;
			});						
		}
	}
	return interfaz;
})

.controller("storiesCtrl", function(storiesFactory) {
	var vm = this;
	vm.stories = [];
	vm.myStories = false;

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

.controller("myStoriesCtrl", function(storiesFactory, $routeParams, $location) {
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
})

.controller("storyCommentsCtrl", function(storiesFactory, commentsFactory, usersFactory, $routeParams, $window) {
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
			vm.newComment.owner = usersFactory.checkSession().id;
			vm.newComment.news = vm.story.id;
			commentsFactory.postComment(vm.newComment).then(function(respuesta){
				console.log("Anadiendo comentario");
				$window.location.reload();
			}, function(respuesta){
				console.log("Error anadiendo comentario");
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
	vm.funciones.getStoryById($routeParams.id);
	vm.funciones.getCommentsById($routeParams.id);
})

.controller("createStoryCtrl", function(storiesFactory, usersFactory, $location) {
	var vm = this;
	vm.story = {};

	vm.funciones = {	
			createEditStory : function() {
				vm.story.owner = usersFactory.checkSession().id;
				storiesFactory.createStory(vm.story).then(function(respuesta){
					console.log("Anadiendo noticia");
					$location.path('/');
			}, function(respuesta){
				console.log("Error anadir noticia");
			})
		},		
	}	
})

.controller("editStoryCtrl", function(storiesFactory, usersFactory, $routeParams, $location) {
	var vm = this;
	vm.story = {};

	vm.funciones = {	
			createEditStory : function() {
			
			vm.story.owner = usersFactory.checkSession().id;
			
			storiesFactory.editStory(vm.story).then(function(respuesta){
			console.log("Anadiendo noticia");
			$location.path('/');
			}, function(respuesta){
			console.log("Error anadir noticia");
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
})


.controller("usersDetailCtrl", function(usersFactory, $routeParams) {
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
})

.controller("loginCtrl", function(usersFactory, $routeParams, $window, $location) {
	var vm = this;
	vm.user = {};
	vm.session = {};
	vm.register = false;

	vm.funciones = {

		login : function() {
			usersFactory.login(vm.user).then(function(respuesta) {
				console.log("Login usuario");
				vm.session = vm.user;
				$window.location.href='#/';
				$window.location.reload();
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
})

.controller("registerCtrl", function(usersFactory, $routeParams, $window, $location) {
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
})