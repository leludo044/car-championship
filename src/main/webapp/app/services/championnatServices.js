var championnatServices = angular.module('championnatServices',
		[ 'ngResource' ]);
championnatServices.factory('Championnats', [ '$resource', function($resource) {
	return $resource('./api/championship/:chpId/:type', {chpId : '@id'}, {
		query : {
			method : 'GET',
			params : {
				chpId : null,
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
		standings : {
			method : 'GET',
			params : {
				type : 'standings'
			},
			isArray : true

		},
		isstarted: {
	    	  method: 'GET',
	    	  params: {type:'isstarted'}
	      },
	      update: {
		        method: 'PUT' // this method issues a PUT request
		      },
	});
} ]);