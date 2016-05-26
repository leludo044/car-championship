controllers.controller('GraphCtrl', [ '$scope', '$routeParams', '$http',
		'Stats', function($scope, $routeParams, $http, Stats) {

			var x = [];
			var y = [];

			$scope.config = {
				options : {
					chart : {
						type : 'bar'
					},
					legend : {
						enabled : false
					},
					title : {
						text : ''
					},
					xAxis : {
						categories : x
					},
					yAxis : {
						title : {
							text: ''
						}
					},
			        plotOptions: {
			            bar: {
			                dataLabels: {
			                    enabled: true
			                }
			            }
			        },
				},
				series : [ {
					data : y
				} ]
			};
/*
			$http({
				url : './ws/stat/' + $scope.url,
				method : "GET",
				isArray : true
			}).success(function(data) {
				$scope.stats = data;
				angular.forEach($scope.stats, function(value, key) {
					this.push(value.name);
				}, x);

				angular.forEach($scope.stats, function(value, key) {
					this.push(value.count);
				}, y);
			}

			);
			*/
			$scope.$watch('filter', function() {
				console.log("watch"+$scope.url);

				$http({
					url : './ws/stat/' + $scope.url+$scope.filter,
					method : "GET",
					isArray : true
				}).success(function(data) {
					$scope.stats = data;
					while(x.length > 0) {
					    x.pop();
					}
					while(y.length > 0) {
					    y.pop();
					}
					
					angular.forEach($scope.stats, function(value, key) {
						this.push(value.name);
					}, x);

					angular.forEach($scope.stats, function(value, key) {
						this.push(value.count);
					}, y);
				}

				);
			})
		} ]);