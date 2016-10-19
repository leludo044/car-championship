/**
 * Controller for results edition : add, remove or update
 */
controllers.controller('ResultCtrl', [
		'$rootScope',
		'Pilotes',
		'$http',
		function($rootScope, Pilotes, $http) {

			var vm = this;
			// Drivers
			this.drivers = Pilotes.query();
			this.indexSelectedDriver = null;
			this.resultInfos = {}
			this.showDriver = false;
			this.results = [];
			this.raceId = -1;
			this.race = {};
			this.raceNumber = 0;
			this.message = null;
			this.error = null ;
			this.scoringSystem = null ;

			/**
			 * Refresh the results for the race
			 * @param raceId The race id to Refresh
			 * @param raceNumber The race number to refresh
			 */
			var refresh = function(raceId, raceNumber) {
				this.raceId = raceId;
				$http({
					method : 'GET',
					url : './api/race/results/' + raceId + '/' + raceNumber
				}).then(function successCallback(response) {
					vm.results = response.data;
				}, function errorCallback(response) {
				});
			}

			/**
			 * Save the current result, add it on the result list and clear
			 * form. If error, a message is displayed
			 */
			this.save = function() {
				var newResult = {
					driver : this.drivers[this.indexSelectedDriver],
					startingPosition : this.resultInfos.startingPosition,
					arrivalPosition : this.resultInfos.arrivalPosition
				};
				console.log(newResult);
				console.log(newResult.driver.name);

				$http({
					method : 'POST',
					url : './api/race/result',
					data : {
						"raceId" : raceId,
						"driverId" : newResult.driver.id,
						"startingPosition" : newResult.startingPosition,
						"arrivalPosition" : newResult.arrivalPosition,
						"raceNumber" : vm.raceNumber,
						"scoringSystem" : vm.scoringSystem
					}

				}).then(function successCallback(response) {
					vm.results.push(newResult);
					vm.clearResult();
					vm.message = "Result added !";
					vm.error = null ;
				}, function errorCallback(response) {
					vm.error = response.data.message;
					vm.message = null ;
				});

			}

			/**
			 * Update a result. If error, a message is displayed
			 * @param index The index of the result to update
			 */
			this.update = function(index, scoringSystem) {
				var newResult = {
					driver : this.results[index].driver,
					startingPosition : this.results[index].startingPosition,
					arrivalPosition : this.results[index].arrivalPosition
				};

				$http({
					method : 'PUT',
					url : './api/race/result',
					data : {
						"raceId" : raceId,
						"driverId" : newResult.driver.id,
						"startingPosition" : newResult.startingPosition,
						"arrivalPosition" : newResult.arrivalPosition,
						"raceNumber" : vm.raceNumber,
						"scoringSystem" : vm.scoringSystem
					}

				}).then(function successCallback(response) {
					vm.message = "Result updated !";
					vm.error = null ;
				}, function errorCallback(response) {
					vm.error = response.data.message;
					vm.message = null ;
				});

			}
			
			/**
			 * Remove a result. If error, a message is displayed
			 * @param index The index of the result to remove
			 */
			this.remove = function(index) {
				$http({
					method : 'DELETE',
					url : './api/race/result/'+vm.race.race.raceId+'/'+this.results[index].driver.id+'/'+vm.raceNumber
				}).then(function successCallback(response) {
					vm.results.splice(index, 1);
					vm.message = "Result removed !";
					vm.error = null ;
				}, function errorCallback(response) {
					vm.error = response.data.message;
					vm.message = null ;
				});

			}

			/**
			 * On driver selection, show the result form
			 */
			this.selectDriver = function() {
				this.showDriver = true;
			}

			/**
			 * Clear the result form : hide inputs, unselect driver and reset to default values
			 */
			this.clearResult = function() {
				vm.showDriver = false
				vm.resultInfos = {
					startingPosition : 0,
					arrivalPosition : 0
				};
				vm.indexSelectedDriver = null;
			}

			// On race selection
			$rootScope.$on("race.changed",
					function onRaceChanged(event, params) {
						vm.race = params;
						vm.raceNumber = vm.race.raceNumber;
						vm.scoringSystem = params.scoringSystem;
						refresh(vm.race.race.raceId, vm.raceNumber);
					});

			// Cleat the result form on init controller
			this.clearResult();

		} ]);