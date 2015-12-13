var piloteServices = angular.module('pilotesServices', [ 'ngResource' ]);
piloteServices.factory('Pilotes', [ '$resource', function($resource) {
	return $resource('./ws/admin/pilote/:id/:info', {id : '@id'},{
	    update: {
	        method: 'PUT' // this method issues a PUT request
	      },
	      acouru: {
	    	  method: 'GET',
	    	  params: {info:'acouru'}
	      }
	    }	);
} ]);