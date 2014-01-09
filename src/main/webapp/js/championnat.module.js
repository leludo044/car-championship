/**
 * 
 */
var chpApp = angular.module('chpApp', []);

chpApp.controller('ChampionnatController', function($scope, $http,
		grandsPrixFactory) {
	// Initialising the variable.
	$scope.championnat = {
		"id" : 1,
		"libelle" : "Aucun"
	};

	 $scope.championnats = grandsPrixFactory.getChampionnats()
	 .success(function(data, status, headers, config) {
	 $scope.championnats = data;
	 }).error(function(data, status, headers, config) {
	 $scope.championnats = [];
	 });

	$scope.gps = grandsPrixFactory.getGrandsPrix($scope.championnat.id)
			.success(function(data, status, headers, config) {
				$scope.gps = data.grandsprix;
			}).error(function(data, status, headers, config) {
				$scope.gps = [];
			});

	$scope.concurrents = grandsPrixFactory.getResultats(11)
	.success(function(data, status, headers, config) {
		$scope.concurrents = data;
	}).error(function(data, status, headers, config) {
		$scope.concurrents = [];
	});

	$http({
		url : 'http://localhost:8080/gtrchamp2/ws/championnat/getjson/1',
		method : 'GET',
	}).success(function(data, status, headers, config) {
		$scope.championnat = data.championnat;
	}).error(function(data, status, headers, config) {
		// called asynchronously if an error occurs
		// or server returns response with an error status.
		$scope.championnat = {
			"id" : 1,
			"libelle" : "Championnat 2012"
		};
	});
});

chpApp
		.factory(
				'grandsPrixFactory',
				function($http) {

					var factory = {};

					factory.getGrandsPrix = function(idChampionnat) {
						/*
						 * Attention : AJAX est asynchrone. le traitement des
						 * callbacks se fait sur l'appel
						 */
						return $http
								.get('http://localhost:8080/gtrchamp2/ws/championnat/getjson/gps/'
										+ idChampionnat);
					};

					factory.getResultats = function(idGrandPrix) {
						/*
						 * Attention : AJAX est asynchrone. le traitement des
						 * callbacks se fait sur l'appel
						 */
						return $http
								.get('http://localhost:8080/gtrchamp2/ws/championnat/resultats/'
										+ idGrandprix);
					};

					factory.getChampionnats = function() {
						/*
						 * Attention : AJAX est asynchrone. le traitement des
						 * callbacks se fait sur l'appel
						 */
						return $http
								.get('http://localhost:8080/gtrchamp2/ws/championnat/listjson');
					};

					return factory;

				});