var raceServices = angular.module('raceServices', [ 'ngResource' ]);
raceServices.factory('Races', [ '$resource', function($resource) {
	return $resource('./ws/json/championnat/:chpId/grandprix/list', {}, {
		query : {
			method : 'GET',
			params : {
				chpId : '2'
			},
			isArray : true
		}
	});
} ]);