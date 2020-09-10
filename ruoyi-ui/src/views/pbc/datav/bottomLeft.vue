<template>
    <div id="chart-qyrzfgqk">
        <div id="qyrzfgqk" style="width: 100%;height:100%"></div>
    </div>
</template>
  
<script>
    import echarts from 'echarts';
    require('@/layout/echarts/theme/dark_0827'); // echarts theme
    //require('echarts/theme/macarons'); // echarts theme

    export default {
        name: 'qyrzyk_zzt',
        data () {
            return {
                chart: null
            }
        },
        mounted() {
            var myChart = echarts.init(document.getElementById('qyrzfgqk'), 'dark');
            var option = {
                backgroundColor: 'rgba(255, 255, 255, 0)',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#999'
                        }
                    }
                },
                toolbox: {
                    feature: {
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                legend: {
                    data: ['累计成交金额', '累计覆盖率']
                },
                xAxis: [
                    {
                        type: 'category',
                        data: ['202003', '202004', '202005', '202006', '202007', '202008'],
                        axisPointer: {
                            type: 'shadow'
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '累计成交金额',
                        //min: 0,
                        //max: 1000,
                        //interval: 50,
                        axisLabel: {
                            formatter: '{value} 万元'
                        }
                    },
                    {
                        type: 'value',
                        name: '累计覆盖率',
                        min: 0,
                        max: 100,
                        //interval: 10,
                        axisLabel: {
                            formatter: '{value} %'
                        }
                    }
                ],
                series: [
                    {
                        name: '累计成交金额',
                        type: 'bar',
                        barWidth: '40%',
                        data: [10, 20, 40, 80, 120, 200]
                    },
                    {
                        name: '累计覆盖率',
                        type: 'line',
                        yAxisIndex: 1,
                        data: [50, 55, 60, 70, 52, 58]
                    }
                ]
            };

            myChart.setOption(option);
        }
    }
</script>
  
  <style lang="less">
  #chart-qyrzfgqk {
    width: 100%;
    box-sizing: border-box;
    margin-left: 20px;
    height: 100%;
    overflow: hidden;
  }
  </style>
  