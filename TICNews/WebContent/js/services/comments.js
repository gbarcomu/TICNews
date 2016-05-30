app.factory("commentsFactory",['$http', function($http) {
	var url = 'https://localhost:8443/TICNews/rest/comments/';

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
}]);