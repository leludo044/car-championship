var championnatServices = angular.module('championnatServices',
		[ 'ngResource' ]);
championnatServices.factory('Championnats', [ '$resource', function($resource) {
	return $resource('./ws/json/championnat/:chpId/:type', {chpId : '@id'}, {
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

		},
		estcommence: {
	    	  method: 'GET',
	    	  params: {type:'estcommence'}
	      },
	      update: {
		        method: 'PUT' // this method issues a PUT request
		      },
	});
} ]);