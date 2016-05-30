app.factory("usersFactory",['$http', function($http) {
	var url = 'https://localhost:8443/TICNews/rest/users/';

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
}]);