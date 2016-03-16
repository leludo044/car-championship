var raceServices = angular.module('raceServices', [ 'ngResource' ]);
raceServices.factory('Races', [ '$resource', function($resource) {
	return $resource('./api/championship/:chpId/race/list', {}, {
		query : {
			method : 'GET',
			params : {
				chpId : '2'
			},
			isArray : true
		}
	});
} ]);