controllers.controller('CircuitCtrl', [ '$scope', '$routeParams', 'Circuits',
		'$http', function($scope, $routeParams, Circuits, $http) {

			$scope.circuits = Circuits.query();

			initForm = function() {
				$scope.formCircuit = {
					id : 0,
					nom : "",
					longueur : 0,
					idPays : 1

				};
			}
			initForm();

			$scope.selectionner = function(circuit, index) {
				$scope.formCircuit = angular.copy(circuit);
				$scope.selection = true;
				$scope.message = "";
				$scope.index = index;
				Circuits.estcouru({
					id : $scope.formCircuit.id
				}, function(response) {
					if (response.code == 1) {
						$scope.formCircuit.supprimable = false;
					} else {
						$scope.formCircuit.supprimable = true;
					}
				});
			}

			$scope.ajouter = function() {
				var circuit = new Circuits();
				circuit.nom = $scope.formCircuit.nom;
				circuit.longueur = $scope.formCircuit.longueur;
				circuit.idPays = $scope.formCircuit.idPays;
				Circuits.save(circuit, function(response) {
					$scope.message = response.message;
					circuit.id = response.code;
					$scope.circuits.push(circuit);
				});
				initForm();
			}

			$scope.modifier = function() {
				var circuit = new Circuits();
				circuit.id = $scope.formCircuit.id;
				circuit.nom = $scope.formCircuit.nom;
				circuit.longueur = $scope.formCircuit.longueur;
				circuit.idPays = 3;
				Circuits.update(circuit, function(response) {
					$scope.message = response.message;
					$scope.circuits[$scope.index] = circuit;
				});

				$scope.selection = false;
				initForm();
			}

			$scope.supprimer = function() {
				$scope.formCircuit.$delete(function(response) {
					$scope.message = response.message;
					$scope.circuits.splice($scope.index, 1);
				})

				$scope.selection = false;
				initForm();
			}

		} ]);