<template>
    <div class="right-chart-2">
      <div id="div-hqzctms" style="width: 100%;height:100%"></div>
    </div>
</template>
  
<script>
    import echarts from 'echarts';
    require('@/layout/echarts/theme/dark_0827'); // echarts theme
    //require('echarts/theme/macarons'); // echarts theme

    export default {
      name: 'hqzctms',
        data () {
            return {
                chart: null
            }
        },
        mounted() {
          var c_hyzctms = echarts.init(document.getElementById('div-hqzctms'), 'dark');
          var option = {
              backgroundColor: 'rgba(255, 255, 255, 0)',
              title: {
                  text: '惠企政策条目数'
              },
              dataset: {
                  source: [
                      ['数量', '部门'],
                      [10, '发改局'],
                      [20, '经科局'],
                      [30, '财政局']
                  ]
              },
              grid: {containLabel: true},
              xAxis: {name: '数量'},
              yAxis: {type: 'category'},
              // visualMap: {
              //     orient: 'horizontal',
              //     left: 'center',
              //     min: 10,
              //     max: 100,
              //     text: ['High Score', 'Low Score'],
              //     dimension: 0,
              //     inRange: {
              //         color: ['#D7DA8B', '#E15457']
              //     }
              // },
              series: [
                  {
                      type: 'bar',
                      encode: {
                          x: '数量',
                          y: '部门'
                      }
                  }
              ]
          };
          c_hyzctms.setOption(option);
        }
    }
</script>
  
  <style lang="less">
  .right-chart-2 {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
  
    .rc2-header {
      font-size: 18px;
      font-weight: bold;
      height: 30px;
      line-height: 30px;
    }
  
    .rc2-chart-container {
      flex: 1;
      display: flex;
    }
  
    .left {
      width: 30%;
      font-size: 16px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
  
      .number {
        font-size: 34px;
        color: #096dd9;
        font-weight: bold;
        margin-bottom: 30px;
      }
    }
  
    .right {
      flex: 1;
      padding-bottom: 20px;
      padding-right: 20px;
      box-sizing: border-box;
    }
  }
  </style>
  