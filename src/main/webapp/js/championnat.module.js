/**
 * 
 */
var chpApp = angular.module('chpApp', []);

chpApp.controller('ChampionnatController', function($scope, $http,
		grandsPrixFactory) {
	// Initialising the variable.
	$scope.championnat = [ {
		"id" : 1,
		"libelle" : "Aucun"
	} ];

	$scope.gps = grandsPrixFactory.getGrandsPrix($scope.championnat[0].id)
			.success(function(data, status, headers, config) {
				$scope.gps = data.grandsprix;
			}).error(function(data, status, headers, config) {
				$scope.gps = [];
			});

	$http({
		url : 'http://localhost:8080/gtrchamp2/ws/championnat/getjson/1',
		method : 'GET',
	}).success(function(data, status, headers, config) {
		$scope.championnat = data;
	}).error(function(data, status, headers, config) {
		// called asynchronously if an error occurs
		// or server returns response with an error status.
		$scope.championnat = [ {
			"id" : 1,
			"libelle" : "Championnat 2012"
		} ];
	});
});

chpApp
		.factory(
				'grandsPrixFactory',
				function($http) {
					return {
						getGrandsPrix : function(idChampionnat) {
							/*
							 * Attention : AJAX est asynchrone. le traitement
							 * des callbacks se fait sur l'appel
							 */
							return $http
									.get('http://localhost:8080/gtrchamp2/ws/championnat/getjson/gps/'
											+ idChampionnat);
						}
					};
				});