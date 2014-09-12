chpApp.directive('statsVictories', function() {
  return {
      restrict: 'E',
      replace: 'true',
      template: '<div id="chart">tot</div>',
      link: function (scope, element, attrs) {
          console.log(3);
          
          var stats = [{"name":"Jean Michel","count":16},{"name":"Ludovic","count":9},{"name":"Franck","count":3},{"name":"Stéphane","count":2},{"name":"Frédéric","count":0}];
          var x = [] ;
          
          angular.forEach(stats, function(value, key) {
        	  this.push(value.name) ;
          }, x) ;

          var y = [] ;
          
          angular.forEach(stats, function(value, key) {
        	  this.push(value.count) ;
          }, y) ;

          
          var chart = new Highcharts.Chart({
            chart: {
              renderTo: 'chart',
              type: 'column'
            },
            title: {
              text: 'Victoires'
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },            
            xAxis: {
                categories: x
            },
            yAxis: {
                min: 0
            },
            series: [{
                name: 'Victories',
                data: y
            }]
          });
          
        }
  };
});