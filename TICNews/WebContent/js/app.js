var app = angular.module('TICNewsApp', [ "ngRoute" ]).config(function($routeProvider) {
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
	
	.when("/top", {    			
		controller: "orderedStoriesCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/stories.html"        
	})
	
	.when("/newStory", {    			
		controller: "createStoryCtrl",           
		controllerAs: "vm",           
		templateUrl: "pages/createEditStory.html"        
	})
	
	.otherwise({templateUrl: "pages/404.html"});
})