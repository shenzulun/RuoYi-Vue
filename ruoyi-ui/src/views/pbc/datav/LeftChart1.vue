<template>
    <div class="left-chart-1">
        <div class="lc1-header"></div>
        <div id="div-glqdqysl" style="width: 100%;height:100%"></div>
    </div>
  </template>
  
<script>
    import echarts from 'echarts';
    require('@/layout/echarts/theme/dark_0827'); // echarts theme
    //require('echarts/theme/macarons'); // echarts theme

    export default {
      name: 'glqdqysl',
        data () {
            return {
                chart: null
            }
        },
        mounted() {
          var c_hyzctms = echarts.init(document.getElementById('div-glqdqysl'), 'dark');
          var option = {
              backgroundColor: 'rgba(255, 255, 255, 0)',
             
              tooltip: {
                  trigger: 'axis',
                  axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                      type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  }
              },
              legend: {
                  data: ['有融资需求', '无融资需求']
              },
              grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
              },
              xAxis: {
                  type: 'value'
              },
              yAxis: {
                  type: 'category',
                  data: ['首贷户', '防控疫情企业']
              },
              series: [
                  {
                      name: '有融资需求',
                      type: 'bar',
                      stack: '总量',
                      label: {
                          show: true,
                          position: 'insideRight'
                      },
                      data: [100, 400]
                  },
                  {
                      name: '无融资需求',
                      type: 'bar',
                      stack: '总量',
                      label: {
                          show: true,
                          position: 'insideRight'
                      },
                      data: [320, 500]
                  }
              ]
          };
          c_hyzctms.setOption(option);
        }
    }
</script>
  
  <style lang="less">
  .left-chart-1 {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
  
    .lc1-header {
      font-size: 24px;
      font-weight: bold;
      height: 50px;
      line-height: 30px;
    }
  
    .lc1-chart-container {
      flex: 1;
      display: flex;
    }
  }
  </style>
  