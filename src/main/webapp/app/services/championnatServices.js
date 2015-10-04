var championnatServices = angular.module('championnatServices',
		[ 'ngResource' ]);
championnatServices.factory('Championnats', [ '$resource', function($resource) {
	return $resource('./ws/json/championnat/:chpId/:type', {}, {
		query : {
			method : 'GET',
			params : {
				chpId : 'list',
				type : null
			},
			isArray : true
		},
		get : {
			method : 'GET',
			params : {
				type : null
			}
		},
		classement : {
			method : 'GET',
			params : {
				type : 'classement'
			},
			isArray : true

		}
	});
} ]);