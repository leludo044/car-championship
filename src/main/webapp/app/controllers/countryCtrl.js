controllers.controller('CountryCtrl', [ '$scope', '$routeParams', 'Country',
		'$http', function($scope, $routeParams, Country, $http) {

			$scope.countries = Country.query();
			
			initForm = function() {
				$scope.formCountry = {
					id : 0,
					name : ""
				};
			}
			initForm();

			$scope.select = function(country, index) {
				$scope.formCountry = angular.copy(country);
				$scope.selection = true;
				$scope.message = "";
				$scope.index = index;
				Country.haveTrack({id:$scope.formCountry.id}, function(response) {
					if (response.code == 1) {
						$scope.formCountry.removable = false ;
					} else {
						$scope.formCountry.removable = true ;
					}
				}) ;

			}

			$scope.create = function() {
				var country = new Country();
				country.name = $scope.formCountry.name;
				Country.save(country, function(response) {
					$scope.message = response.message;
					country.id = response.code;
					$scope.countries.push(country);
				});
				initForm();
			}

			$scope.update = function() {
				var country = new Country();
				country.id = $scope.formCountry.id;
				country.name = $scope.formCountry.name;
				Country.update(country, function(response) {
					$scope.message = response.message;
					$scope.countries[$scope.index] = country;
				});

				$scope.selection = false;
				initForm();
			}

			
			$scope.remove = function() {
				$scope.formCountry.$delete(function(response) {
					$scope.message = response.message;
					$scope.countries.splice($scope.index, 1);
				})

				$scope.selection = false;
				initForm();
			}
			
		} ]);