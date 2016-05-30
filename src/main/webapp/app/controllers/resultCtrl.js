controllers.controller('ResultCtrl', [ '$scope', '$routeParams', 'Pilotes',
		'$http', function($scope, $routeParams, Pilotes, $http) {

			$scope.drivers = Pilotes.query();

			initForm = function() {
				$scope.formPilote = {
					id : 0,
					name : "",
					birthdate : ""
				};
			}
			initForm();


		} ]);