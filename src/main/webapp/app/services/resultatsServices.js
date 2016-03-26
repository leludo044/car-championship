var resultatsServices = angular.module('resultatsServices', [ 'ngResource' ]);
resultatsServices.factory('Resultats', [ '$resource', function($resource) {
	return $resource('./api/championship/race/:gpId/results', {}, {
		query : {
			method : 'GET',
			params : {
				gpId : '2'
			},
			isArray : true
		}
	});
} ]);

