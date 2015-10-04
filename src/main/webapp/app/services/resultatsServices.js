var resultatsServices = angular.module('resultatsServices', [ 'ngResource' ]);
resultatsServices.factory('Resultats', [ '$resource', function($resource) {
	return $resource('./ws/json/grandprix/:gpId/resultat/list', {}, {
		query : {
			method : 'GET',
			params : {
				gpId : '2'
			},
			isArray : true
		}
	});
} ]);

