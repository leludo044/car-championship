/**
 * 
 */
var chpApp = angular.module('chpApp', []);

chpApp.controller('ChampionnatController', function($scope, $http,
		grandsPrixFactory, Championnat) {
	// Initialising the variable.
	$scope.championnat = {
		"id" : 2,
		"libelle" : "Aucun"
	};

	$scope.selectChampionnat = function(idChp) {
		//Championnat.setIdChampionnat(idChp) ;
		Championnat.getClassement(idChp).success(function(data){
			$scope.classement = data.data ;
		}) ;
	};
	
	$scope.selectChampionnat(2);

	$scope.championnats = grandsPrixFactory.getChampionnats().success(
			function(data, status, headers, config) {
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

	$scope.resultats = function(idGp) {
		grandsPrixFactory.getResultats(idGp).success(
				function(data, status, headers, config) {
					$scope.concurrents = data;
				}).error(function(data, status, headers, config) {
			$scope.concurrents = [];
		});
	};

//	grandsPrixFactory.getClassement($scope.championnat.id).success(
//			function(data, status, headers, config) {
//				$scope.classement = data;
//			}).error(function(data, status, headers, config) {
//		$scope.classement = [];
//	});

	$http({
		url : 'ws/championnat/getjson/2',
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

chpApp.factory('grandsPrixFactory', function($http) {

	var factory = {};

	factory.getGrandsPrix = function(idChampionnat) {
		/*
		 * Attention : AJAX est asynchrone. le traitement des callbacks se fait
		 * sur l'appel
		 */
		return $http.get('ws/championnat/getjson/gps/' + idChampionnat);
	};

	factory.getResultats = function(idGrandPrix) {
		/*
		 * Attention : AJAX est asynchrone. le traitement des callbacks se fait
		 * sur l'appel
		 */
		return $http.get('ws/championnat/resultats/' + idGrandPrix);
	};

	factory.getChampionnats = function() {
		/*
		 * Attention : AJAX est asynchrone. le traitement des callbacks se fait
		 * sur l'appel
		 */
		return $http.get('ws/championnat/listjson');
	};

	factory.getClassement = function(idChampionnat) {
		/*
		 * Attention : AJAX est asynchrone. le traitement des callbacks se fait
		 * sur l'appel
		 */
		return $http.get('ws/championnat/classement/' + idChampionnat);
	};

	factory.selectChampionnat = function(idChampionnat) {
		/*
		 * Attention : AJAX est asynchrone. le traitement des callbacks se fait
		 * sur l'appel
		 */
		return $http.get('ws/championnat/getjson/' + idChampionnat);
	};

	return factory;
});

chpApp.provider('Championnat', function() {
	var baseUrl = '/gtrchamp2';
	var idChampionnat;

	this.setIdChampionnat = function(id) {
		idChampionnat = id;
	}
	// Service interface
	this.$get = function($http) {
		var service = {
			// Define our service API here
			getClassement : function(idChp) {
				return $http({
					method : 'JSONP',
					url : baseUrl + '/ws/championnat/classement/' + idChp,
					params : {
						'jsoncallback' : 'JSON_CALLBACK'
					}
				});
			}
		};

		return service;
	}
});