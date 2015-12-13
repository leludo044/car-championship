controllers.controller('ChampionnatListController', [ '$scope', 'Championnats',
		function($scope, Championnats) {
	
			$scope.championnats = Championnats.query();
			
			initForm = function() {
				$scope.form = {
					id : 0,
					libelle : ""
				};
			}
			initForm();
			
			$scope.selectionner = function(championnat, index) {
				$scope.form = angular.copy(championnat);
				$scope.selection = true;
				$scope.message = "";
				$scope.index = index;
				Championnats.estcommence({chpId:$scope.form.id}, function(response) {
					if (response.code == 1) {
						$scope.form.supprimable = false ;
					} else {
						$scope.form.supprimable = true ;
					}
				}) ;

			}
			
			$scope.ajouter = function() {
				var championnat = new Championnats(); 
				championnat.libelle = $scope.form.libelle;
				Championnats.save(championnat, function(response) {
					$scope.message = response.message;
					championnat.id = response.code;
					$scope.championnats.push(championnat);
				});
				initForm();
			}

			$scope.modifier = function() {
				var championnat = new Championnats();
				championnat.id = $scope.form.id;
				championnat.libelle = $scope.form.libelle;
				Championnats.update(championnat, function(response) {
					$scope.message = response.message;
					$scope.championnats[$scope.index] = championnat;
				});

				$scope.selection = false;
				initForm();
			}
			
			$scope.supprimer = function() {
				$scope.form.$delete(function(response) {
					$scope.message = response.message;
					$scope.championnats.splice($scope.index, 1);
				})

				$scope.selection = false;
				initForm();
			}
			
		} ]);

