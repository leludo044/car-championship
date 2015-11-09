var piloteServices = angular.module('pilotesServices', [ 'ngResource' ]);
piloteServices.factory('Pilotes', [ '$resource', function($resource) {
	return $resource('./ws/admin/pilote/:id', {id : '@id'},{
	    update: {
	        method: 'PUT'
	      }
	    }	);
} ]);