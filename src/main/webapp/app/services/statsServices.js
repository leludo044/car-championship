var statsServices = angular.module('statsServices', [ 'ngResource' ]);
statsServices.factory('Stats', [ '$resource', function($resource) {
	return $resource('./ws/stat/victory', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);

