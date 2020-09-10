<template>
  <div class="right-chart-1">
    <div class="rc1-header">分区域融资对接情况</div>
    <div class="rc1-chart-container">
      <div id="div-fqyrzdjqk" style="width: 100%;height:100%"></div>
    </div>
  </div>
</template>

<script>
    import echarts from 'echarts';
    require('@/layout/echarts/theme/dark_0827'); // echarts theme
    //require('echarts/theme/macarons'); // echarts theme

    export default {
      name: 'fqyrzdjqk',
      data () {
            return {
                chart: null
            }
        },
        mounted() {
          var c_fqyrzdjqk = echarts.init(document.getElementById('div-fqyrzdjqk'), 'dark');
          var option = {
              backgroundColor: 'rgba(255, 255, 255, 0)',
              tooltip: {
                  trigger: 'axis',
                  axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                      type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  }
              },
              legend: {
                  data: ['已融资', '总数']
              },
              grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
              },
              xAxis: [
                  {
                      type: 'value'
                  }
              ],
              yAxis: [
                  {
                      type: 'category',
                      axisTick: {
                          show: false
                      },
                      data: ['西城街道', '东城街道', '南城街道', '北城街道', '院桥']
                  }
              ],
              series: [
                  {
                      name: '已融资',
                      type: 'bar',
                      stack: '总量',
                      
                      label: {
                          show: true
                      },
                      data: [120, 132, 101, 134, 190]
                  },
                  {
                      name: '总数',
                      type: 'bar',
                      stack: '总量',
                      barGap: "-100%", 
                      label: {
                          show: true
                      },
                      itemStyle: {
                          //color: 'rgba(0,0,0,0.25)'
                      },
                      data: [320, 302, 341, 374, 390]
                  }
              ]
          };

          c_fqyrzdjqk.setOption(option);
        }
    }
</script>

<style lang="less">
.right-chart-1 {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;

  .rc1-header {
    font-size: 20px;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }

  .rc1-chart-container {
    flex: 1;
    display: flex;
  }
}
</style>
