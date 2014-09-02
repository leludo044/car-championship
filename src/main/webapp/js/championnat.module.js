'use strict';

/**
 * 
 */
var chpApp = angular.module('chpApp', [ 'ngRoute', 'controllers',
		'raceServices', 'championnatServices', 'resultatsServices', 'statsServices' ]);

chpApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/championnat/:id', {
		templateUrl : 'partials/classement.html',
		controller : 'ChampionnatDetailController'
	})
	.when('/stats', {
		templateUrl : 'partials/stats.html',
		controller : 'ChampionnatStatController'
	})
	.otherwise({
		redirectTo : '/championnat/2'
	});
} ]);

var controllers = angular.module('controllers', []);

controllers.controller('ChampionnatListController', [ '$scope', 'Championnats',
		function($scope, Championnats) {
			$scope.championnats = Championnats.query();
		} ]);

controllers.controller('ChampionnatDetailController', [ '$scope',
		'$routeParams', 'Championnats', 'Races', 'Resultats',
		function($scope, $routeParams, Championnats, Races, Resultats) {
			// Initialising the variable.
			$scope.championnat = {
				"id" : 2,
				"libelle" : "Aucun"
			};
			// $scope.championnats = Championnats.query() ;

			$scope.championnat = Championnats.get({
				chpId : $routeParams.id
			});
			/*
			 * $scope.selectChampionnat(2);
			 */

			$scope.selectChampionnat = function(idChp) {
				$scope.championnat = Championnats.get({
					chpId : idChp
				});
				$scope.gps = Races.query({
					chpId : idChp
				});
				$scope.classement = Championnats.classement({
					chpId : idChp
				});
				$scope.concurrents = null;
				$scope.hideMsgSelectionGp = false;
			};
			$scope.selectChampionnat($routeParams.id);

			$scope.resultats = function(idGp) {
				$scope.concurrents = Resultats.query({
					gpId : idGp
				});
				$scope.hideMsgSelectionGp = true;
			};

			/*
			 * $scope.selectChampionnat = function(idChp) { //
			 * Championnat.setIdChampionnat(idChp) ; $scope.classement =
			 * Championnat.getClassement(idChp) ; alert("toto"); } ;
			 * 
			 * $scope.selectChampionnat(2);
			 * 
			 * $scope.championnats =
			 * grandsPrixFactory.getChampionnats().success( function(data,
			 * status, headers, config) { $scope.championnats = data;
			 * }).error(function(data, status, headers, config) {
			 * $scope.championnats = []; }); /* $scope.gps =
			 * grandsPrixFactory.getGrandsPrix($scope.championnat.id)
			 * .success(function(data, status, headers, config) { $scope.gps =
			 * data.grandsprix; }).error(function(data, status, headers, config) {
			 * $scope.gps = []; });
			 * 
			 * $scope.resultats = function(idGp) {
			 * grandsPrixFactory.getResultats(idGp).success( function(data,
			 * status, headers, config) { $scope.concurrents = data;
			 * }).error(function(data, status, headers, config) {
			 * $scope.concurrents = []; }); };
			 */
			// grandsPrixFactory.getClassement($scope.championnat.id).success(
			// function(data, status, headers, config) {
			// $scope.classement = data;
			// }).error(function(data, status, headers, config) {
			// $scope.classement = [];
			// });
		} ]);


controllers.controller('ChampionnatStatController', [ '$scope',
		'$routeParams', 'Stats',
		function($scope, $routeParams, Stats) {
			$scope.championnat = {
				"id" : 2,
				"libelle" : "Aucun"
			};

			$scope.stats = Stats.query();
		} ]);

/*
 * chpApp.factory('grandsPrixFactory', function($http) {
 * 
 * var factory = {};
 * 
 * factory.getGrandsPrix = function(idChampionnat) { return
 * $http.get('ws/championnat/getjson/gps/' + idChampionnat); };
 * 
 * factory.getResultats = function(idGrandPrix) { return
 * $http.get('ws/championnat/resultats/' + idGrandPrix); };
 * 
 * factory.getChampionnats = function() { return
 * $http.get('ws/championnat/listjson'); };
 * 
 * factory.getClassement = function(idChampionnat) { return
 * $http.get('ws/championnat/classement/' + idChampionnat); };
 * 
 * factory.selectChampionnat = function(idChampionnat) { return
 * $http.get('ws/championnat/getjson/' + idChampionnat); };
 * 
 * return factory; });
 */

/*
 * chpApp.provider('Championnat', [$resource, function() { var baseUrl =
 * '/gtrchamp2'; var idChampionnat;
 * 
 * this.setIdChampionnat = function(id) { idChampionnat = id; };
 * 
 * var championnatResource = $resource(url+'/ws/championnat/classement/:chpId',
 * {chpId:2} );
 *  // Service interface this.$get = function($http) { var service = { // Define
 * our service API here getClassement : function(idChp) { chps =
 * championnatResource.query(function() { return chps ; }); } };
 * 
 * return service; }; }]);
 */

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

var resultatsServices = angular.module('resultatsServices', [ 'ngResource' ]);
raceServices.factory('Resultats', [ '$resource', function($resource) {
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


var statsServices = angular.module('statsServices', [ 'ngResource' ]);
raceServices.factory('Stats', [ '$resource', function($resource) {
	return $resource('./ws/stat/victory', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);

