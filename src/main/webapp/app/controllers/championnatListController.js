controllers.controller('ChampionnatListController', [ '$scope', 'Championnats',
		function($scope, Championnats) {
			$scope.championnats = Championnats.query();
		} ]);

