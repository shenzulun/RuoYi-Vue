<template>
    <div id="chart-fjgrzdjqk">
        <div id="fjgrzdjqk" style="width: 100%;height:100%"></div>
    </div>
</template>
  
<script>
    import echarts from 'echarts';
    require('@/layout/echarts/theme/dark_0827'); // echarts theme
    //require('echarts/theme/macarons'); // echarts theme

    export default {
        name: 'fjgrzdjqk',
        data () {
            return {
                chart: null
            }
        },
        mounted() {
            var myChart = echarts.init(document.getElementById('fjgrzdjqk'),'dark');
            var option = {
                color: ['#3398DB'],
                backgroundColor: 'rgba(255, 255, 255, 0)',
                title: {
                    text: '分机构融资对接情况'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: ['黄岩农商银行', '台州银行', '工商银行', '建设银行', '泰隆银行'],
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '对接数量',
                        type: 'bar',
                        barWidth: '40%',
                        data: [50, 40, 30, 20, 10]
                    }
                ]
            };

            myChart.setOption(option);
        }
    }
</script>
  
  <style lang="less">
  #chart-fjgrzdjqk {
    width: 100%;
    box-sizing: border-box;
    margin-left: 20px;
    height: 100%;
    overflow: hidden;
  }
  </style>
  