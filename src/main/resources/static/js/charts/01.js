(function(jQuery) {

    "use strict";

    function apexChartUpdate(chart, detail) {
        let color = getComputedStyle(document.documentElement).getPropertyValue('--dark');
        if (detail.dark) {
          color = getComputedStyle(document.documentElement).getPropertyValue('--white');
        }
      console.log(chart,detail)
        chart.updateOptions({
          chart: {
            foreColor: color
          }
        })
    }

    if(jQuery("#ct-chart").length){
      // new Chartist.Bar('#ct-chart', {
      //   labels: ['Q1', 'Q2', 'Q3', 'Q4'],
      //   series: [
      //     [800, 1200, 1400, 1300],
      //     [200, 400, 500, 300],
      //     [100, 200, 400, 600]
      //     ]
      // }, {
      //   stackBars: true,
      //   axisY: {
      //     labelInterpolationFnc: function(value) {
      //       return (value / 1000) + 'k';
      //     }
      //   }
      // }).on('draw', function(data) {
      //   if(data.type === 'bar') {
      //     data.element.attr({
      //       style: 'stroke-width: 40px'
      //     });
      //   }
      // }); 
      var options = {
        series: [{
        data: [21, 22, 10, 28, 16, 21, 13, 30]
      }],
      colors: ['#5c00e6', '#5c00e6', '#004d99', '#0044cc', '#5c00e6', '#004d99', '#0044cc', '#5c00e6'],
        chart: {
          sparkline: {
            enabled: true,
        },
        height: 250,
        type: 'bar',
        events: {
          click: function(chart, w, e) {
            // console.log(chart, w, e)
          }
        }
      },
      plotOptions: {
        bar: {
          columnWidth: '45%',
          distributed: true,
        }
      },
      dataLabels: {
        enabled: false
      },
      legend: {
        show: false
      },
      xaxis: {
        categories: [          
          'C',
          'C++',
          'Java',
          'C#',
          'Python',
          'NodeJS',
          'AnglarJS',
          'AI'
        ],
        labels: {
          style: {
            fontSize: '12px'
          }
        }
      }
      };

      var chart = new ApexCharts(document.querySelector("#ct-chart"), options);
      chart.render();
    }

    if(jQuery("#chart-column-01").length){
      var options = {
        series: [{
        name: 'TEAM A',
        type: 'column',
        data: [23, 11, 22, 27, 13, 22, 37, 21, 44, 22, 30]
      }, {
        name: 'TEAM B',
        type: 'area',
        data: [44, 55, 41, 67, 22, 43, 21, 41, 56, 27, 43]
      }, {
        name: 'TEAM C',
        type: 'line',
        data: [30, 25, 36, 30, 45, 35, 64, 52, 59, 36, 39]
      }],
      colors: ['#5c00e6', '#ffff33', '#04237D'],
        chart: {
        height: 240,
        type: 'line',
        stacked: false,
          sparkline: {
            enabled: true,
        }
      },
      stroke: {
        width: [0, 2, 5],
        curve: 'smooth'
      },
      plotOptions: {
        bar: {
          columnWidth: '50%'
        }
      },
      
      fill: {
        opacity: [0.85, 0.25, 1],
        gradient: {
          inverseColors: false,
          shade: 'light',
          type: "vertical",
          opacityFrom: 0.85,
          opacityTo: 0.55,
          stops: [0, 100, 100, 100]
        }
      },
      labels: ['01/01/2003', '02/01/2003', '03/01/2003', '04/01/2003', '05/01/2003', '06/01/2003', '07/01/2003',
        '08/01/2003', '09/01/2003', '10/01/2003', '11/01/2003'
      ],
      markers: {
        size: 0
      },
      xaxis: {
        type: 'datetime'
      },
      yaxis: {
        title: {
          text: 'Points',
        },
        min: 0
      },
      tooltip: {
        shared: true,
        intersect: false,
        y: {
          formatter: function (y) {
            if (typeof y !== "undefined") {
              return y.toFixed(0) + " points";
            }
            return y;
      
          }
        }
      }
      };

      var chart = new ApexCharts(document.querySelector("#chart-column-01"), options);
      chart.render();
    }

    if (jQuery("#chart-01").length) {
        var options = {
          series: [{
            name: 'Successful deals',
            data: [60, 30, 60, 30, 60, 30, 60]
          }, {
            name: 'Failed deals',
            data: [30, 60, 30, 60, 30, 60, 30]
          }],
          colors: ["#4788ff", "#f75676"],
          chart: {
            height: 370,
            type: 'line',
            zoom: {
              enabled: false
            },
            sparkline: {
              enabled: false,
            }
          },
          dataLabels: {
            enabled: false
          },
          stroke: {
            curve: 'smooth',
            width: 3
          },
          title: {
            text: '',
            align: 'left'
          },
          grid: {
            row: {
              colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
              opacity: 0
            },
          },
          xaxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
            labels: {
                 minHeight: 22,
                maxHeight: 35,
             }
          },
          yaxis: {
            labels: {
                offsetY: 0,
                minWidth: 15,
                maxWidth: 15,
              }
           },
           legend: {
            position: 'top',
            horizontalAlign: 'left',
            offsetX: -33
          }
        };
      
        var chart = new ApexCharts(document.querySelector("#chart-01"), options);
        chart.render();
        const body = document.querySelector('body')
        if (body.classList.contains('dark')) {
          apexChartUpdate(chart, {
            dark: true
          })
        }
      
        document.addEventListener('ChangeColorMode', function (e) {
          apexChartUpdate(chart, e.detail)
        })
      
      }

    if (jQuery("#dash-chart-03").length) {
        const options = {
          series: [{
            name: 'Revenue',
            data: [76, 85, 101, 98, 87, 105, 91]
          }, {
              name: 'Net Profit',
              data: [44, 55, 57, 56, 61, 58, 63]
            }],
          chart: {
            type: 'bar',
            height: 260
          },
          colors: ['#4788ff', '#37d5f2'],
          plotOptions: {
            bar: {
              horizontal: false,
              // columnWidth: '45%',
              borderRadius: 4,
            },
          },
          dataLabels: {
            enabled: false
          },
          legend: {
            show: false
          },
          stroke: {
            show: true,
            width: 2,
            colors: ['transparent']
          },
          xaxis: {
            categories: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            labels:{
              minHeight: 30,
              maxHeight: 30,
            }
          },
          yaxis: {
            title: {
              
            },
            labels: {
              offsetY: 0,
              minWidth: 15,
              maxWidth: 15,
            }
          },
          fill: {
            opacity: 1
          },
          tooltip: {
            y: {
              formatter: function (val) {
                return "$ " + val + " thousands"
              }
            }
          }
        };
      
        const chart = new ApexCharts(document.querySelector("#dash-chart-03"), options);
        chart.render();
        const body = document.querySelector('body')
        if (body.classList.contains('dark')) {
          apexChartUpdate(chart, {
            dark: true
          })
        }
      
        document.addEventListener('ChangeColorMode', function (e) {
          apexChartUpdate(chart, e.detail)
        })
      }

      if (jQuery("#dash-chart-02").length) {
        const options = {
          series: [
            {
              name: 'Like',
              data: [50, 25, 10, 70, 25, 30, 25]
            },
            {
              name: 'Comments',
              data: [50, 25, 10, 70, 25, 30, 25]
            },
            {
              name: 'Share',
              data: [60, 44, 20, 35, 22, 22, 10]
            }
          ],
          colors: ['#37d5f2', '#4788ff', '#4fd69c'],
          chart: {
            type: 'bar',
            height: 275,
            stacked: true,
            zoom: {
              enabled: true
            }
          },
          options: {
            legend: {
              markers: {
                radius: 12,
              }
            }
          },
          
          responsive: [{
            breakpoint: 480,
            options: {
              legend: {
                position: 'bottom',
                offsetX: -10,
                offsetY: 0,
               
              }
            }
          }],
          plotOptions: {
            bar: {
              horizontal: false,
              // columnWidth: '45%',
              borderRadius: 4,
            },
          },
          xaxis: {
            type: 'category',
            categories: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            labels: {
                minHeight: 22,
                maxHeight: 40,
             }
          },
          yaxis: {
            labels: {
                // offsetX: -17,
                offsetY: 0,
                minWidth: 20,
                maxWidth: 20,
              }
           },
          legend: {
            position: 'bottom',
            offsetX: 0,
            offsetY: 8
      
          },
          fill: {
            opacity: 1
          },
          dataLabels: {
            enabled: false
          }
        };
        const chart = new ApexCharts(document.querySelector("#dash-chart-02"), options);
        chart.render();
        const body = document.querySelector('body')
        if (body.classList.contains('dark')) {
          apexChartUpdate(chart, {
            dark: true
          })
        }
      
        document.addEventListener('ChangeColorMode', function (e) {
          apexChartUpdate(chart, e.detail)
        })
      }

      if(jQuery("#chart-apex-column-01").length){
        var options = {
          series: [{
          name: 'Incomes',
          data: [90, 105, 72, 90, 65, 109, 130]
        }, {
          name: 'Expenses',
          data: [115, 93, 75, 102, 144, 52, 71]
        }],
        colors: ['#1f1f7a', '#2e2eb8'],
          chart: {
          height: 265,
          fontFamily: 'DM Sans',
          toolbar:{
            show: false,
          },
          type: 'area'
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        fill: {
          type: 'gradient',
          gradient: {
            shade: 'light',
            type: "vertical",
            shadeIntensity: 0.5,
            // gradientToColors: undefined, // optional, if not defined - uses the shades of same color in series
            inverseColors: false,
            opacityFrom: .8,
            opacityTo: .2,
            stops: [0, 50, 100],
            colorStops: []
          }
        },        
        grid: {
          xaxis: {
              lines: {
                  show: false
              }
          },
          yaxis: {
              lines: {
                  show: false
              }
          }
        },
        yaxis: {
          labels: {
          offsetY:0,
          minWidth: 20,
          maxWidth: 20
          },
        },
        xaxis: {
          type: 'datetime',
          labels: {
            minHeight: 20,
            maxHeight: 20
          },
          categories: ["2018-09-19T00:00:00.000Z", "2018-09-19T01:30:00.000Z", "2018-09-19T02:30:00.000Z", "2018-09-19T03:30:00.000Z", "2018-09-19T04:30:00.000Z", "2018-09-19T05:30:00.000Z", "2018-09-19T06:30:00.000Z"]
        },
        tooltip: {
          x: {
            format: 'dd/MM/yy HH:mm'
          },
        },
        };

        var chart = new ApexCharts(document.querySelector("#chart-apex-column-01"), options);
        chart.render();
      }

      if(jQuery("#chart-apex-column-02").length){
        var options = {
          series: [{
          data: [55, 42, 19, 30, 20, 65, 21, 23, 45, 60, 30, 20]
        }],
        colors: ['#b3cccc', '#04237D', '#4d4dff'],
          chart: {
          height: 183,
          type: 'bar',
          toolbar:{
            show: false,
          },
          sparkline: {
            enabled: true,
          },
          events: {
            click: function(chart, w, e) {
              // console.log(chart, w, e)
            }
          }
        },
        plotOptions: {
          bar: {
            columnWidth: '40%',
            distributed: true,
            borderRadius: 5,
          }
        },
        dataLabels: {
          enabled: false
        },
        legend: {
          show: false
        },
        grid: {
          xaxis: {
              lines: {
                  show: false
              }
          },
          yaxis: {
              lines: {
                  show: false
              }
          }
        },
        yaxis: {
          labels: {
          offsetY:0,
          minWidth: 10,
          maxWidth: 10
          },
        },
        
        xaxis: {
          categories: [           
            '30 Jan',
            '25 Feb',
            '28 Mar', 
          ],
          labels: {
            minHeight: 20,
            maxHeight: 20,
            style: {
              fontSize: '12px'
            }
          }
        }
        };

        var chart = new ApexCharts(document.querySelector("#chart-apex-column-02"), options);
        chart.render();
      }

      if(jQuery("#chart-apex-column-03").length){
          var options = {
            series: [43,58,20,35],
            chart: {
            height:330,
            type: 'donut',
            
          },
         
          labels: ["Mobile","Electronics", "Laptop", "Others"],
          colors: ['#ffbb33', '#04237D', '#e60000', '#8080ff'],
          plotOptions: {
            pie: {
              startAngle: -90,
              endAngle: 270,
              donut:{
                size: '80%',               
                labels:{
                  show:true,
                  total:{
                    show:true,
                    color: '#BCC1C8',
                    fontSize: '18px',
                    fontFamily: 'DM Sans',
                    fontWeight: 600,
                  },
                  value: {
                    show: true,
                    fontSize: '25px',
                    fontFamily: 'DM Sans',
                    fontWeight: 700,
                    color: '#8F9FBC',
                  },
                }
              }
            }
          },
          dataLabels: {
            enabled: false,
          },
          stroke: {
            lineCap: 'round'
          }, 
          grid:{
            padding: {
             
              bottom: 0,
          }
          },
          legend: {
            position: 'bottom',
            offsetY: 8,
            show:true,
          },
          responsive: [{
            breakpoint: 480,
            options: {
              chart: {
                height:268
              }
            }
          }]
          };
      
          var chart = new ApexCharts(document.querySelector("#chart-apex-column-03"), options);
          chart.render();

          const body = document.querySelector('body')
          if (body.classList.contains('dark')) {
            apexChartUpdate(chart, {
              dark: true
            })
          }
      
          document.addEventListener('ChangeColorMode', function (e) {
            apexChartUpdate(chart, e.detail)
          })
      }

      function generateData(baseval, count, yrange) {
        var i = 0;
        var series = [];
        while (i < count) {
          //var x =Math.floor(Math.random() * (750 - 1 + 1)) + 1;;
          var y = Math.floor(Math.random() * (yrange.max + yrange.min + 1)) + yrange.min;
          var z = Math.floor(Math.random() * (75 - 15 + 1)) + 15;
      
          series.push([baseval, y, z]);
          baseval += 86400000;
          i++;
        }
        return series;
      }

      if(jQuery("#chart-map-column-04").length){
        const map = L.map('chart-map-column-04').setView([42.860, 74.630], 8);
        var statesData =
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "id": "1",
              "properties": {
                "name": "Bishkek",
                "density": 1000
              },
              "geometry": {
                "type": "Point",
                "coordinates": [74.583733, 42.874621]
              }
            },
            {
              "type": "Feature",
              "id": "2",
              "properties": {
                "name": "Osh",
                "density": 500
              },
              "geometry": {
                "type": "Point",
                "coordinates": [72.807992, 40.513661]
              }
            },
            {
              "type": "Feature",
              "id": "3",
              "properties": {
                "name": "Jalal-Abad",
                "density": 300
              },
              "geometry": {
                "type": "Point",
                "coordinates": [72.996720, 40.933911]
              }
            },
            {
              "type": "Feature",
              "id": "4",
              "properties": {
                "name": "Karakol",
                "density": 200
              },
              "geometry": {
                "type": "Point",
                "coordinates": [78.393600, 42.492931]
              }
            },
            {
              "type": "Feature",
              "id": "5",
              "properties": {
                "name": "Naryn",
                "density": 100
              },
              "geometry": {
                "type": "Point",
                "coordinates": [76.005239, 41.428703]
              }
            },
            {
              "type": "Feature",
              "id": "6",
              "properties": {
                "name": "Talas",
                "density": 80
              },
              "geometry": {
                "type": "Point",
                "coordinates": [72.234039, 42.522652]
              }
            },
            {
              "type": "Feature",
              "id": "7",
              "properties": {
                "name": "Tokmok",
                "density": 150
              },
              "geometry": {
                "type": "Point",
                "coordinates": [75.284725, 42.841972]
              }
            }
          ]
        }

        const tileUrl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
        const attribution = 
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
        const tileLayer = L.tileLayer(tileUrl, { attribution });

        function getColor(d) {
          return d > 1000 ? '#172d69' :
                 d > 500  ? '#1c367d' :
                 d > 200  ? '#203f92' :
                 d > 100  ? '#2548a7' :
                 d > 50   ? '#2950bc' :
                 d > 20   ? '#2e59d1' :
                 d > 10   ? '#d5def6' :
                            '#c0cdf1';
      }
        function style(feature) {
          return {
              fillColor: getColor(feature.properties.density),
              weight: 2,
              opacity: 1,
              color: 'white',
              dashArray: '3',
              fillOpacity: 0.7
          };
      }
      
      L.geoJson(statesData, {style: style}).addTo(map);

        tileLayer.addTo(map);        
        
      }

      if(jQuery("#chart-apex-column-001").length){
        var options = {
          series: [{
          data: [91, 82, 90, 88, 105, 99]
        }],
          chart: {
          height: 265,
          type: 'bar',
          toolbar:{
            show: false,
          },
          events: {
            click: function(chart, w, e) {
              // console.log(chart, w, e)
            }
          }
        },        
        plotOptions: {
          bar: {
            columnWidth: '35%',
            distributed: true,
          }
        },
        dataLabels: {
          enabled: false
        },
        grid: {
          xaxis: {
              lines: {
                  show: false
              }
          },
          yaxis: {
              lines: {
                  show: true
              }
          }
        },
        legend: {
          show: false
        },
        yaxis: {
          labels: {
          offsetY:0,
          minWidth: 20,
          maxWidth: 20
          },
        },
        xaxis: {
          categories: [
            'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'June', 
          ],
          labels: {
            minHeight: 22,
            maxHeight: 22,
            style: {              
              fontSize: '12px'
            }
          }
        }
        };

        var chart = new ApexCharts(document.querySelector("#chart-apex-column-001"), options);
        chart.render();
      }

})(jQuery)