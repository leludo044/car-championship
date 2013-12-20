/**
 * 
 */
var chpApp = angular.module('chpApp', []);

chpApp.controller('ChampionnatController', function($scope, $http) {
	// Initialising the variable.
	$scope.championnat = [ {
		"id" : 0,
		"libelle" : "Aucun"
	} ];

	$scope.gps = [] ;
	
	// Getting the list of users through ajax call.
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
	
	// Getting the list of users through ajax call.
	$http({
		url : 'http://localhost:8080/gtrchamp2/ws/championnat/getjson/gps/1',
		method : 'GET',
	}).success(function(data, status, headers, config) {
		$scope.gps = data.grandsprix;
	});

});