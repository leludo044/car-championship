chpApp.directive('stats', function() {
  return {
      restrict: 'E',
      scope: {
    	label: '=label',
    	url: '=',
    	filter: '='
      },
      templateUrl: './partials/graph.html',
      controller: 'GraphCtrl'
  };
});