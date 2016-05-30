app.factory("storiesFactory",['$http', function($http) {
	var url = 'https://localhost:8443/TICNews/rest/stories/';

	var interfaz = {

		getStories : function() {
			return $http.get(url).then(function(response) {
				return response.data;
			});
		},
		
		getStoriesByLikes : function() {
			var _url = url + 'likes/'
			return $http.get(_url).then(function(response) {
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
}]);