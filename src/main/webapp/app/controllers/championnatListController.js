controllers.controller('ChampionnatListController', ['$scope', '$http', 'Championnats', 'Circuits',
	function ($scope, $http, Championnats, Circuits) {

		$scope.championnats = Championnats.query();
		$scope.circuits = Circuits.query();
		$scope.selected = {};
		// Liste des circuits sélectionnables
		//$scope.circuits = [];
		// Liste des circtuis sélectionnés
		$scope.selectedTracks = []

		initForm = function () {
			$scope.form = {
				id: 0,
				libelle: ""
			};
		}
		initForm();

		$scope.selectionner = function (championnat, index) {
			$scope.form = angular.copy(championnat);
			$scope.selection = true;
			$scope.message = "";
			$scope.index = index;
			Championnats.estcommence({ chpId: $scope.form.id }, function (response) {
				if (response.code == 1) {
					$scope.form.supprimable = false;
				} else {
					$scope.form.supprimable = true;
				}
			});

		}

		$scope.ajouter = function () {
			var championnat = new Championnats();
			championnat.libelle = $scope.form.libelle;
			Championnats.save(championnat, function (response) {
				$scope.message = response.message;
				championnat.id = response.code;
				$scope.championnats.push(championnat);
			});
			initForm();
		}

		$scope.modifier = function () {
			var championnat = new Championnats();
			championnat.id = $scope.form.id;
			championnat.libelle = $scope.form.libelle;
			Championnats.update(championnat, function (response) {
				$scope.message = response.message;
				$scope.championnats[$scope.index] = championnat;
			});

			$scope.selection = false;
			initForm();
		}

		$scope.supprimer = function () {
			$scope.form.$delete(function (response) {
				$scope.message = response.message;
				$scope.championnats.splice($scope.index, 1);
			})

			$scope.selection = false;
			initForm();
		}


		$scope.selectionnerCircuits = function (championnat) {
			$scope.selected = championnat;
			$scope.selectedTracks = [];
			if ($scope.circuits.length == 0) {
				$scope.circuits = Circuits.query();
			}
			$http({
				method: 'GET',
				url: './ws/json/championnat/' + $scope.selected.id + '/tracks'
			}).then(function (response) {
				$scope.selectTracks($scope.circuits, response.data);
			}, function () {

			});

		}

		$scope.onSelect = function (circuit, index) {
			var exists = $scope.selectedTracks.indexOf(index);
			if (exists < 0) {
				$http({
					method: 'PUT',
					url: './ws/json/championnat/' + $scope.selected.id + '/' + circuit.id
				}).then(function () {
					$scope.selectedTracks.push(index);
				}, function () {

				});
			} else {
				delete $scope.selectedTracks[exists];
			}
		}


		$scope.selectTracks = function (allTracks, championshipTracks) {
			for (var i=0; i<allTracks.length; i++) {
				for (var j=0; j<championshipTracks.length; j++) {
					if (allTracks[i].id === championshipTracks[j].circuit.id) {
						$scope.selectedTracks.push(i);
					}
				}
			}

		}

	}]);

