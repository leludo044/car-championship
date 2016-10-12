controllers
		.controller(
				'ResultCtrl',
				[
						'$rootScope',
						'Pilotes',
						'$http',
						function($rootScope, Pilotes, $http) {

							var vm = this;
							// Drivers 
							this.drivers = Pilotes.query();
							this.indexSelectedDriver = -1;
							this.resultInfos = {}
							this.showDriver = false;
							this.results = [];
							this.raceId = -1;
							this.race = {};

							clearResult() ;
							
							/**
							 * Refresh the results for the race
							 */
							var refresh = function(raceId) {
								this.raceId = raceId;
								$http({
									method : 'GET',
									url : './api/race/results/' + raceId + '/1'
								}).then(function successCallback(response) {
									vm.results = response.data;
								}, function errorCallback(response) {
								});

							}

							/**
							 * Save the current result, add it on the results lists and clear form
							 */
							this.save = function() {
								var newResult = {
									driver : this.drivers[this.indexSelectedDriver],
									startingPosition : this.resultInfos.startingPosition,
									arrivalPosition : this.resultInfos.arrivalPosition
								};
								console.log(newResult);
								console.log(newResult.driver.name);

								$http(
										{
											method : 'POST',
											url : './api/race/results',
											data : {
												"raceId" : raceId,
												"driverId" : newResult.driver.id,
												"startingPosition" : newResult.startingPosition,
												"arrivalPosition" : newResult.arrivalPosition,
												"raceNumber" : 1
											}

										}).then(
										function successCallback(response) {
											vm.results.push(newResult);
											clearResult() ; 
										}, function errorCallback(response) {
										});

							}

							/**
							 * On driver selection, show the result form
							 */ 
							this.selectDriver = function() {
								this.showDriver = true;
							}

							/**
							 * Clear the result form : hide inputs and reset to default values
							 */
							var clearResult = function() {
								vm.showDriver = false 
								vm.resultInfos = {
										startingPosition : 0,
										arrivalPosition : 0
									};
							}
							// Catch for the race selection
							$rootScope
									.$on(
											"race.changed",
											function onRaceChanged(event,
													params) {
												vm.race = params ;
												refresh(vm.race.race.raceId);
											});

						} ]);