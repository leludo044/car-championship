controllers.controller('ChampionnatListController', ['$scope', '$rootScope', '$http', 'Championnats', 'Circuits',
	function ($scope, $rootScope, $http, Championnats, Circuits) {

		$scope.championnats = Championnats.query();
		$scope.tracks = Circuits.query();
		/** Store the selected championship */
		$scope.selected = undefined;
		
		$scope.selectedTracks = []
		/** Store the index of the selected track */
		var selectedIndex = -1 ;

		initForm = function () {
			$scope.form = {
				id: 0,
				libelle: "",
				type: "",
				mode: ""
			};
		}
		initForm();

		$scope.selectionner = function (championnat, index) {
			$scope.form = angular.copy(championnat);
			$scope.selection = true;
			$scope.message = "";
			$scope.index = index;
			Championnats.isstarted({ chpId: $scope.form.id }, function (response) {
				if (response.code == 1) {
					$scope.form.supprimable = false;
				} else {
					$scope.form.supprimable = true;
				}
			});

		}

		$scope.ajouter = function () {
			var championnat = new Championnats();
			championnat.name = $scope.form.name;
			championnat.type = $scope.form.type;
			championnat.mode = $scope.form.mode;
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
			championnat.name = $scope.form.name;
			championnat.type = $scope.form.type;
			championnat.mode = $scope.form.mode;
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
			if ($scope.tracks.length == 0) {
				$scope.tracks = Circuits.query();
			}
			$http({
				method: 'GET',
				url: './api/championship/' + $scope.selected.id + '/tracks'
			}).then(function (response) {
				selectTracks($scope.tracks, response.data);
			}, function () {

			});

		}

		/**
		 * On clic a track
		 */
		$scope.onSelect = function(track, index) {
			var exists = $scope.selectedTracks[index];
			if (!exists) {
					if (selectedIndex != -1 & selectedIndex != index) {
						delete $scope.selectedTracks[selectedIndex];
					}
					$scope.selectedTracks[index]={selected:true, dateFr:""};
					selectedIndex = index ;
			} else {
				$http({
					method: 'DELETE',
					url: './api/championship/' + $scope.selected.id + '/' + track.id
				}).then(function () {
					delete $scope.selectedTracks[index];
				}, function () {
				});
			}
			
		};
		/**
		 * On clic race date button validation
		 */
		$scope.onValid = function (circuit, date, index) {
				$http({
					method: 'PUT',
					url: './api/championship/' + $scope.selected.id + '/' + circuit.id,
					data: {
						championshipId: $scope.selected.id,
						trackId: circuit.id,
						date: date
					}
				}).then(function (response) {
					console.log(response.data.code);
					selectedIndex = -1 ;
					$scope.selectedTracks[index]={selected:true, date:date, raceId:response.data.code};
				}, function () {

				});
		}


		var selectTracks = function (allTracks, championshipTracks) {
			for (var i=0; i<allTracks.length; i++) {
				for (var j=0; j<championshipTracks.length; j++) {
					if (allTracks[i].id === championshipTracks[j].track.id) {
						//$scope.selectedTracks.push({index:i, date:championshipTracks[j].track.date});
						$scope.selectedTracks[i] = {selected:true, date:championshipTracks[j].dateFr, raceId: championshipTracks[j].id, towRacesMode: championshipTracks[j].twoRacesMode };
					}
				}
			}

		}
		
		/**
		 * Show the modal containing the results
		 */
		$scope.showResults = function(track, race, raceNumber) {
			$rootScope.$emit("race.changed", {track: track, race: race, raceNumber: raceNumber, scoringSystem:$scope.selected.type}) ;
			$('#resultsModal').modal('show') ;
		}

	}]);

