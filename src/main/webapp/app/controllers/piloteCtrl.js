controllers.controller('PiloteCtrl', [ '$scope', '$routeParams', 'Pilotes',
		'$http', function($scope, $routeParams, Pilotes, $http) {

			$scope.pilotes = Pilotes.query();

			initForm = function() {
				$scope.formPilote = {
					id : 0,
					name : "",
					birthdate : ""
				};
			}
			initForm();

			$scope.selectionner = function(pilote, index) {
				$scope.formPilote = angular.copy(pilote);
				$scope.selection = true;
				$scope.message = "";
				$scope.index = index;
				Pilotes.acouru({id:$scope.formPilote.id}, function(response) {
					if (response.code == 1) {
						$scope.formPilote.supprimable = false ;
					} else {
						$scope.formPilote.supprimable = true ;
					}
				}) ;
				/*
				if ($scope.formPilote.supprimable) {
					alert(toto) ;
				}
				*/
			}

			$scope.ajouter = function() {
				var pilote = new Pilotes(); 
				pilote.name = $scope.formPilote.name;
				pilote.birthdate = $scope.formPilote.birthdate;
				Pilotes.save(pilote, function(response) {
					$scope.message = response.message;
					pilote.id = response.code;
					$scope.pilotes.push(pilote);
				});
				initForm();
			}

			$scope.modifier = function() {
				var pilote = new Pilotes();
				pilote.id = $scope.formPilote.id;
				pilote.name = $scope.formPilote.name;
				pilote.birthdate = $scope.formPilote.birthdate;
				Pilotes.update(pilote, function(response) {
					$scope.message = response.message;
					$scope.pilotes[$scope.index] = pilote;
				});

				$scope.selection = false;
				initForm();
			}

			$scope.supprimer = function() {
				$scope.formPilote.$delete(function(response) {
					$scope.message = response.message;
					$scope.pilotes.splice($scope.index, 1);
				})

				$scope.selection = false;
				initForm();
			}

		} ]);