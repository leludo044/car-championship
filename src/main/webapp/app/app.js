'use strict';

/**
 * 
 */
var chpApp = angular.module('chpApp', [ 'ngRoute', 'controllers',
		'raceServices', 'championnatServices', 'resultatsServices', 'statsServices', 'highcharts-ng' ]);

chpApp.config([ '$routeProvider', function ($routeProvider) {
	$routeProvider.when('/championnat/:id', {
		templateUrl : 'partials/classement.html',
		controller : 'ChampionnatDetailController'
	}).when('/stats', {
		templateUrl : 'partials/stats.html',
		controller : 'StatsCtrl'
	}).otherwise({
		redirectTo : '/championnat/2'
	});
} ]);

var controllers = angular.module('controllers', []);
