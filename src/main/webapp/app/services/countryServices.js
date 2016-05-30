var CountryServices = angular.module('countryServices', [ 'ngResource' ]);
piloteServices.factory('Country', [ '$resource', function($resource) {
	return $resource('./api/country/:id/:info', {id : '@id'},{
	    update: {
	        method: 'PUT' // this method issues a PUT request
	      },
	      haveTrack: {
	    	  method: 'GET',
	    	  params: {info:'havetrack'}
	      }
	    }	);
} ]);