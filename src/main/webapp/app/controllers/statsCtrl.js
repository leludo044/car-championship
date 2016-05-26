controllers.controller('StatsCtrl', [ '$scope', '$routeParams', '$http',
		'Stats', 'Championnats',
		function($scope, $routeParams, $http, Stats, Championnats) {

			$scope.filteredChampionship = undefined;

			/*
			 * Liste des graphiques statistiques à afficher. Le label correspond
			 * au nom du graphe à afficher et l'url correspond au suffixe de
			 * l'url du webservice qui va récupérer les données
			 */
			$scope.graphs = [ {
				label : "Victoires",
				url : "victory",
				filter: ""
			}, {
				label : "Poles position",
				url : "pole",
				filter: ""
			}, {
				label : "Podiums",
				url : "podium",
				filter: ""
			} ];

			$scope.championship = Championnats.query();

			$scope.filter = function(championship) {
				console.log(championship);
				if ($scope.filteredChampionship != championship) {
					$scope.filteredChampionship = championship

					for (var i = 0; i < 3; i++) {
						$scope.graphs[i].filter = "/" + championship.id;
					}
				} else {
					$scope.filteredChampionship = undefined ;
					for (var i = 0; i < 3; i++) {
						$scope.graphs[i].filter = "";
					}
				}
				
				
				/*
				 * angular.forEach($scope.graphs, function(graph) {
				 * console.log(graph); graph.url =
				 * graph.url+"/"+championship.id; })
				 */

				// $scope.graphs[0].url = "victory/"+championship.id;
			}

		} ]);