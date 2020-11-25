<template>
  <div class="home">
    <div class="background"></div>
    <div class="top_title">
      <h1>企业融资三色图系统<i class="point"></i></h1>
      <div class="rightTime">{{time}}</div>
    </div>
    <div class="top_content">
      <div class="left">
        <div class="top">
          <div class="title">
            <h2>各类清单企业数量</h2>
            <!-- <p>Number of listed companies</p> -->
          </div>
          <div class="card"><div class="chart" id="chart1"></div></div>
        </div>
        <div class="bottom">
          <div class="title">
            <h2>惠企政策条目数</h2>
            <!-- <p>Entries of fayored enterprise policy</p> -->
          </div>
          <div class="card"><div class="chart" id="chart2"></div></div>
        </div>
      </div>
      <div class="center">
        <div class="mapName">
          <p @click.stop="showAll">{{name1}}</p><span v-show='name2'> / </span>
          <p>{{name2}}</p>
        </div>
        <div id="map2" v-if="name2" class="map"></div>
        <div id="map" v-else class="map"></div>
      <div class="map-legend">
        <div class="row">
          <i class="legend4-icon"></i>
          <span>已融资</span>
        </div>  
        <div class="row">
          <i class="legend3-icon"></i>
          <span>正在对接</span>
        </div>    
        <div class="row">
          <i class="legend2-icon"></i>
          <span>未对接</span>
        </div>  
      </div>
      </div>
      <div class="right">
        <div class="top">
          <div class="title">
            <h2>企业融资情况</h2>
            <!-- <p>Corporate financing</p> -->
          </div>
          <div class="card"><div class="chart" id="chart3"></div></div>
          <div class="dataList3">
            <div v-for="(item, index) in data3" class="item" :key="index">
              <i></i>
              <p>{{item.name}}</p>
              <p>{{item.val1 + '家'}}</p>
              <p>{{item.val2 + '%'}}</p>
            </div>
          </div>
        </div>
        <div class="bottom">
          <div class="title">
            <h2>分区域融资对接情况</h2>
            <!-- <p>Sub-regional financing connection</p> -->
          </div>
          <div class="card"><div class="chart" id="chart4"></div></div>
        </div>
      </div>
    </div>
    <div class="bottom_content">
      <div class="left">
        <div class="title">
          <h2>企业融资覆盖情况</h2>
          <!-- <p>Corparate financing coverage</p> -->
        </div>
        <div class="card"><div class="chart" id="chart5"></div></div>
      </div>
      <div class="right">
        <div class="title">
          <h2>分机构融资对接情况</h2>
          <!-- <p>Sub-institution financing connection</p> -->
        </div>
        <div class="card"><div class="chart" id="chart6"></div></div>
      </div>
    </div>
  </div>
</template>
<script>
import { pie } from 'xj-echarts/src/js/pie.js'
import { line } from 'xj-echarts/src/js/line.js'
import echarts from 'echarts'
import echartsGL from 'echarts-gl'
import world from 'echarts/map/js/world.js'
import '@/assets/js/huangyanqu'
import '@/assets/json/maps/331003001'
import '@/assets/json/maps/331003002'
import '@/assets/json/maps/331003003'
import '@/assets/json/maps/331003004'
import '@/assets/json/maps/331003005'
import '@/assets/json/maps/331003006'
import '@/assets/json/maps/331003007'
import '@/assets/json/maps/331003008'
import '@/assets/json/maps/331003101'
import '@/assets/json/maps/331003102'
import '@/assets/json/maps/331003103'
import '@/assets/json/maps/331003107'
import '@/assets/json/maps/331003108'
import '@/assets/json/maps/331003200'
import '@/assets/json/maps/331003201'
import '@/assets/json/maps/331003202'
import '@/assets/json/maps/331003203'
import '@/assets/json/maps/331003204'
import '@/assets/json/maps/331003205'
import vueSeamlessScroll from 'vue-seamless-scroll'
let targetName = '茅畲乡'
let geoCoordMap = {
  '头陀镇':[121.14518654798,28.692252772224],
  '沙埠镇':[121.14918654798,28.557252772224],
}
let targetMap = {
  '头陀镇':[121.14118654798,38.559252772224,0],
  '茅畲乡':[112.13118654798,28.557252772224,0],
  '西城街道':[120.91149554752,28.499674208713,0],
  '富山乡':[121.18953854774,28.517142773211,0],
  '院桥镇':[121.12874254739,28.613685361045,0],
  '新前街道':[121.12874254739,28.613685361045,0],
  '沙埠镇':[121.12874254739,28.613685361045,0],
  '高桥街道':[121.12874254739,28.613685361045,0],
  '北洋镇':[121.12874254739,28.613685361045,0],
  '东城街道':[121.12874254739,28.613685361045,0],
  '上垟乡':[121.12874254739,28.613685361045,0],
  '北城街道':[121.12874254739,28.613685361045,0],
  '宁溪镇':[121.12874254739,28.613685361045,0],
  '南城街道':[121.12874254739,28.613685361045,0],
  '澄江街道':[121.12874254739,28.613685361045,0],
  '屿头乡':[121.12874254739,28.613685361045,0],
  '上郑乡':[121.12874254739,28.613685361045,0],
  '江口街道':[121.12874254739,28.613685361045,0],
  '平田乡':[121.12874254739,28.613685361045,0],
}
let target = geoCoordMap[targetName]
let convertData = function(data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
      var geoCoord = geoCoordMap[data[i].name]
      if (geoCoord) {
        if (data[i].name === targetName) {
          res.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value),
            itemStyle: {
              color: '#FFA211'
            },
            label: {
              textStyle: {
                fontSize: 20,
                lineHeight: 20
              }
            }
          })
        } else {
          res.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value)
          })
        }
      }
  }
  return res
}
let lineData = function(data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
      var geoCoord = geoCoordMap[data[i].name]
      if (geoCoord) {
        res.push([geoCoord, target])
      }
  }
  return res
}
export default {
  data () {
    return {
      time: "",
      school: 1,
      map: null,
      map2: null,
      mapData: [
        {name: '头陀镇', value: 10},
        {name: '沙埠镇', value: 12},
      ],
      mapLineData: [],
      data3: [
        {name: '正在对接', val1: '', val2: ''},
        {name: '已融资', val1: '', val2: ''},
        {name: '未对接', val1: '', val2: ''}
      ],
      name1: '黄岩区',
      name2: '',
      addressCode: '',
    }
  },
  created () {
    setInterval(() => {
      this.getTopLeft_text1();
    }, 1000);
  },
  mounted () {
    this.getData()
  },
  methods: {
    getData () {
      this.$get('http://api.hy96596.com/pbc/api/sst/getCustAnalyse').then(res => {
        if (res.code === 200) {
          this.showChart1(res.data.xAxis, res.data.data_y, res.data.data_w)
        }
      })
      this.$get('http://api.hy96596.com/pbc/api/sst/getHqzctms').then(res => {
        if (res.code === 200) {
          this.showChart2(res.data.xAxis, res.data.data)
        }
      })
      this.showAll('first')
    },
    showAll (type) {
      this.name2 = ''
      this.addressCode = ''
      this.$get('http://api.hy96596.com/pbc/api/sst/getQydtzb').then(res => {
        if (res.code === 200) {
          this.randerMap(res.data.data_wdj, res.data.data_yrz, res.data.data_zzdj, type)
        }
      })
      this.refreshData()
    },
    refreshData (name) { // 4个根据地图和清单企业总数来刷新数据的图
      this.$get('http://api.hy96596.com/pbc/api/sst/getQyrzqk', {
        custType: name,
        addressCode: this. addressCode
      }).then(res => {
        if (res.code === 200) {
          let total = res.data.zzdj + res.data.yrz + res.data.wdj
          this.data3[0].val1 = res.data.zzdj
          this.data3[0].val2 = (res.data.zzdj / total * 100).toFixed(2)
          this.data3[1].val1 = res.data.yrz
          this.data3[1].val2 = (res.data.yrz / total * 100).toFixed(2)
          this.data3[2].val1 = res.data.wdj
          this.data3[2].val2 = (100 - this.data3[0].val2 - this.data3[1].val2).toFixed(2)
          this.showChart3(res.data.zzdj, res.data.yrz, res.data.wdj)
        }
      })
      this.$get('http://api.hy96596.com/pbc/api/sst/getFqyrzdjqk', {
        custType: name,
        addressCode: this. addressCode 
      }).then(res => {
        if (res.code === 200) {
          this.showChart4(res.data.xAxis, res.data.data_w, res.data.data_y, res.data.data_zzdj)
        }
      })
      this.$get('http://api.hy96596.com/pbc/api/sst/getQyrzfgqk', {
        custType: name,
        addressCode: this. addressCode 
      }).then(res => {
        if (res.code === 200) {
          let data_fg = []
          res.data.data_fg.map(val => {
            data_fg.push(val * 100)
          })
          this.showChart5(res.data.xAxis, res.data.data_cj, data_fg)
        }
      })
      this.$get('http://api.hy96596.com/pbc/api/sst/getFjgrzdjqk', {
        custType: name,
        addressCode: this. addressCode 
      }).then(res => {
        if (res.code === 200) {
          this.showChart6(res.data.xAxis, res.data.data, res.data.data_bs)
        }
      })
    },
    showChart1 (xAxis, data_y, data_w) {
      let chart1 = echarts.init(document.getElementById("chart1"))
      let option = {
          tooltip: { //提示框组件
            show: true,
            trigger:'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            right: 10,
            top: 10,
            itemWidth: 7,
            itemHeight: 7,
            textStyle: {
              color: '#ddd'
            }
          },
          grid: {
              top: 30,
              left: 10,
              right: 10,
              bottom: 0,
              containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: true,//坐标轴两边留白
              data: xAxis,
              axisLabel: { //坐标轴刻度标签的相关设置。
                interval: 0,//设置为 1，表示『隔一个标签显示一个标签』
                margin:15,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  lineHeight: 18,
                  fontSize: 11,
                },
                formatter: (value, index) => {
                  let str = ''
                  for (let i = 0; i < value.length; i++) {
                    str += value[i]
                    if (i % 4 == 0 && i != 0) {
                      str += '\r\n'
                    }
                  }
                  return str
                }
              },
              axisTick:{//坐标轴刻度相关设置。
                show: false,
              },
              axisLine:{//坐标轴轴线相关设置
                lineStyle:{
                  color: 'rgba(255, 255, 255, 0.5)',
                  type: 'dotted'
                }
              },
              splitLine: { //坐标轴在 grid 区域中的分隔线。
                show: false,
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              splitNumber: 5,
              axisLabel: {
                show: false
              },
              axisLine:{
                show: false
              },
              axisTick:{
                show: false
              },
              splitLine: {
                show: false
              }

            }
          ],
          series : [
              {
                  name:'有融资需求',
                  type:'bar',
                  data: data_y,
                  barWidth: 10,
                  barGap:1,//柱间距离
                  itemStyle: {
                    normal: {
                      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        {
                          offset: 0,
                          color: "rgba(243, 242, 84, 1)"
                        },
                        {
                          offset: 1,
                          color: "rgba(247, 200, 100, 1)"
                        }
                      ]),
                      barBorderRadius: 50
                    }
                  },
              },
              {
                  name:'无融资需求',
                  type:'bar',
                  data: data_w,
                  barWidth: 10,
                  barGap:0.5,//柱间距离
                  itemStyle: {
                    normal: {
                      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        {
                          offset: 0,
                          color: "rgba(250, 150, 150, 1)"
                        },
                        {
                          offset: 1,
                          color: "rgba(250, 70, 100, 1)"
                        }
                      ]),
                      barBorderRadius: 50
                    }
                  },
              }
          ]
      }
      chart1.setOption(option)
      chart1.on("click", (params) => {
        let str = params.name == '企业总数' ? '' : params.name
        this.refreshData(str)
      })
    },
    showChart2 (xAxis, data) {
      let chart2 = echarts.init(document.getElementById("chart2"))
      let option = {
          tooltip: { //提示框组件
            show: false
          },
          grid: {
              top: 30,
              left: 10,
              right: 20,
              bottom: 10,
              containLabel: true
          },
          yAxis: [
            {
              type: 'value',
              splitNumber: 5,
              axisLabel: {
                show: true,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisLine:{
                show: true,
                lineStyle:{
                  color: 'rgba(255, 255, 255, 0.5)',
                  type: 'dotted'
                }
              },
              axisTick:{
                show: false
              },
              splitLine: {
                show: false
              }
            }
          ],
          xAxis: [
            {
              type: 'category',
              boundaryGap: true,//坐标轴两边留白
              data: xAxis,
              axisLabel: { //坐标轴刻度标签的相关设置。
                interval: 0,//设置为 1，表示『隔一个标签显示一个标签』
                margin:15,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisTick:{//坐标轴刻度相关设置。
                show: false,
              },
              axisLine:{//坐标轴轴线相关设置
                lineStyle:{
                  color: 'rgba(255, 255, 255, 0.5)',
                  type: 'dotted'
                }
              },
              splitLine: { //坐标轴在 grid 区域中的分隔线。
                show: false,
              }
            }
          ],
          series : [{
            name: "男",
            type: "bar",
              barWidth: 10,
              barGap:1,//柱间距离
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: "rgba(243, 242, 84, 1)"
                    },
                    {
                      offset: 1,
                      color: "rgba(103, 202, 154, 1)"
                    }
                  ]),
                  barBorderRadius: 50
                }
              },
              label: {
                show: true,
                position: 'top',
                fontSize: 12,
                color: "rgba(243, 242, 84, 1)"
              },
              data: data,
          }
        ]
      }
      chart2.setOption(option)
    },
    showChart3 (val1, val2, val3) {
      let chart3 = echarts.init(document.getElementById("chart3"))
      // let val1 = Math.floor(Math.random() * 100)
      // let val2 = Math.floor(Math.random() * 200)
      // let val3 = Math.floor(Math.random() * 300)
      let option = {
          legend: {
              show: false
          },
          color: [
            "rgba(243, 242, 84, 1)",
            "rgba(236, 79, 64, 1)",
            "rgba(76, 210, 64, 1)"
          ],
          series: [
          {
            type: "pie",
            name: "name",
            center: ['22%', '50%'],
            radius: ["25%", "60%"],
            startAngle: 180,
            endAngle: 0,
            label: {
              show: false,
              formatter: "{b}",
              fontSize: 9,
              color: "#eee",
              fontWeight: "normal"
            },
            labelLine: {
              show: false,
              length: 6,
              length2: 6,
              lineStyle: {
                color: "rgba(203,82,235,1)"
              }
            },
            data: [
              { value: val1, name: "正在对接" },
              { value: val3, name: "未对接" },
              { value: val2, name: "已融资" },
            ]
          },
            {
              type: "pie",
              name: "huan",
              center: ['22%', '50%'],
              radius: ["20%", "30%"],
              label: {
                show: false,
              },
              labelLine: {
                show: false,
              },
              z: 10,
              data: [
                { value: 30, name: "" ,
                  itemStyle: {
                    normal: {
                      color: 'rgba(255,255,255,0.5)'
                    },
                    emphasis: {
                      color: 'rgba(0,0,0,0)'
                    }
                  }
                },
              ]
            },
            {
              type: "pie",
              name: "huan",
              center: ['22%', '50%'],
              radius: ["65%", "66%"],
              label: {
                show: false,
              },
              labelLine: {
                show: false,
              },
              z: 10,
              data: [
                { value: 30, name: "" ,
                  itemStyle: {
                    normal: {
                      color: 'rgba(255,255,255,0.5)'
                    },
                    emphasis: {
                      color: 'rgba(0,0,0,0)'
                    }
                  }
                },
              ]
            }
          ]
      }
      chart3.setOption(option)
    },
    showChart4 (xAxis, data_w, data_y, data_zzdj) {
      let chart4 = echarts.init(document.getElementById("chart4"))
      // let data1 = []
      // let data2 = []
      // for (let i = 0; i < 6; i++) {
      //   data1.push(Math.floor(Math.random() * 100))
      //   data2.push(Math.floor(Math.random() * 100))
      // }
      let option = {
          tooltip: { //提示框组件
            show: true,
            trigger: 'axis'
          },
          legend: {
            right: 10,
            top: 0,
            itemWidth: 7,
            itemHeight: 7,
            textStyle: {
              color: '#ddd'
            }
          },
          grid: {
              top: 30,
              left: 10,
              right: 20,
              bottom: 10,
              containLabel: true
          },
          yAxis: [
            {
              type: 'value',
              splitNumber: 5,
              axisLabel: {
                show: true,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisLine:{
                show: true,
                lineStyle:{
                  color: 'rgba(255, 255, 255, 0.5)',
                  type: 'dotted'
                }
              },
              axisTick:{
                show: false
              },
              splitLine: {
                show: false
              }
            }
          ],
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,//坐标轴两边留白
              data: xAxis,
              axisLabel: { //坐标轴刻度标签的相关设置。
                interval: 0,//设置为 1，表示『隔一个标签显示一个标签』
                margin:15,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisTick:{//坐标轴刻度相关设置。
                show: false,
              },
              axisLine:{//坐标轴轴线相关设置
                lineStyle:{
                  color: 'rgba(255, 255, 255, 0.5)',
                  type: 'dotted'
                }
              },
              splitLine: { //坐标轴在 grid 区域中的分隔线。
                show: false,
              }
            }
          ],
          series : [{
              name: "正在对接",
              type: "line",
              smooth: true,
              symbolSize: 4,
              zlevel: 2,
              lineStyle: {
                normal: {
                    color: "rgba(243, 242, 84, 1)",
                }
              },
              itemStyle: {
                normal: {
                  color: "rgba(243, 242, 84, 1)",
                  borderColor: '#fff'
                }
              },
              label: {
                show: true,
                position: 'top',
                fontSize: 12,
                color: "rgba(243, 242, 84, 1)"
              },
              areaStyle: {
                  normal: {
                      color: new echarts.graphic.LinearGradient(
                          0,
                          0,
                          0,
                          1,
                          [{
                                  offset: 0,
                                  color: "rgba(243, 242, 84, 0.5)"
                              },
                              {
                                  offset: 1,
                                  color: 'transparent'
                              }
                          ],
                          false
                      )
                  }
              },
              data: data_zzdj,
          },{
              name: "已融资",
              type: "line",
              smooth: true,
              symbolSize: 4,
              zlevel: 1,
              lineStyle: {
                normal: {
                    color: "rgba(76, 230, 124, 1)",
                }
              },
              itemStyle: {
                normal: {
                  color: "rgba(76, 230, 124, 1)",
                  borderColor: '#fff'
                }
              },
              label: {
                show: true,
                position: 'top',
                fontSize: 12,
                color: "rgba(76, 230, 124, 1)"
              },
              areaStyle: {
                  normal: {
                      color: new echarts.graphic.LinearGradient(
                          0,
                          0,
                          0,
                          1,
                          [{
                                  offset: 0,
                                  color: "rgba(76, 230, 124, 0.5)"
                              },
                              {
                                  offset: 1,
                                  color: 'transparent'
                              }
                          ],
                          false
                      )
                  }
              },
              data: data_y,
          },{
              name: "未对接",
              type: "line",
              smooth: true,
              symbolSize: 4,
              zlevel: 3,
              lineStyle: {
                  normal: {
                      color: "#f33",
                  }
              },
              itemStyle: {
                normal: {
                  color: "#f33",
                  borderColor: '#fff'
                }
              },
              label: {
                show: true,
                position: 'top',
                fontSize: 12,
                color: '#f55'
              },
              areaStyle: {
                  normal: {
                      color: new echarts.graphic.LinearGradient(
                          0,
                          0,
                          0,
                          1,
                          [{
                                  offset: 0,
                                  color: "rgba(255, 100, 100, 0.8)"
                              },
                              {
                                  offset: 1,
                                  color: 'transparent'
                              }
                          ],
                          false
                      )
                  }
              },
              data: data_w,
          }
        ]
      }
      chart4.setOption(option)
    },
    showChart5 (xAxis, data_cj, data_fg) {
      let chart5 = echarts.init(document.getElementById("chart5"))
      // let data1 = []
      // let data2 = []
      // for (let i = 0; i < 9; i++) {
      //   let num = Math.floor(Math.random() * 100)
      //   data1.push(num)
      //   data2.push(num)
      // }
      let option = {
          tooltip: {
            show:true,
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            },
            formatter: (val) => {
              return `<h2 class="tooltipName">${val[0].name}</h2>
              <p class="p1"> ${val[0].seriesName}：${val[0].data}</p>
              <p class="p1"> ${val[1].seriesName}：${(val[1].data).toFixed(2)}%</p>`
            }
          },
          legend: {
            right: 150,
            top: -3,
            itemWidth: 7,
            itemHeight: 7,
            textStyle: {
              color: '#ddd'
            }
          },
          grid: {
              top: 30,
              left: 20,
              right: 40,
              bottom: 20,
              containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: true,//坐标轴两边留白
              data: xAxis,
              axisLabel: { //坐标轴刻度标签的相关设置。
                interval: 0,//设置为 1，表示『隔一个标签显示一个标签』
                margin:15,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisTick:{//坐标轴刻度相关设置。
                show: false,
              },
              axisLine:{//坐标轴轴线相关设置
                lineStyle:{
                  color: 'rgba(255, 255, 255, 0.5)',
                  type: 'dotted'
                }
              },
              splitLine: { //坐标轴在 grid 区域中的分隔线。
                show: false,
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '累计成交额',
              splitNumber: 5,
              axisLabel: {
                show: true,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisLine:{
                show: true,
                lineStyle:{
                  color: '#ddd',
                  type: 'dotted'
                }
              },
              axisTick:{
                show: false
              },
              splitLine: {
                show: false
              }
            },
            {
              type: 'value',
              position: 'right',
              name: '累计覆盖率',
              splitNumber: 5,
              axisLabel: {
                show: true,
                textStyle: {
                  color: '#ddd',
                  fontStyle: 'normal',
                  fontSize: 12,
                }
              },
              axisLine:{
                show: true,
                lineStyle:{
                  color: '#ddd',
                  type: 'dotted'
                }
              },
              axisTick:{
                show: false
              },
              splitLine: {
                show: false
              }

            }
          ],
          series : [
              {
                name:'累计成交金额',
                type:'bar',
                data: data_cj,
                barWidth: 10,
                barGap:0.5,//柱间距离
                itemStyle: {
                  normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                      {
                        offset: 0,
                        color: "rgba(255, 255, 255, 1)"
                      },
                      {
                        offset: 1,
                        color: "rgba(50, 100, 250, 1)"
                      }
                    ]),
                    barBorderRadius: 50
                  }
                },
              },
              {
                type: 'line',
                name:'累计覆盖率',
                yAxisIndex: 1,
                symbolSize: 6,
                itemStyle: {
                  normal: {
                    color: 'rgba(76, 230, 124, 1)',
                  }
                },
                label: {
                  show: true,
                  position: 'top',
                  fontSize: 12,
                  color: 'rgba(76, 230, 124, 1)',
                  formatter: (val) => {
                    return (val.data).toFixed(2) + '%'
                  }
                },
                lineStyle: {
                  width: 2,
                  color: 'rgba(76, 230, 124, 1)'
                },
                data: data_fg
              }
          ]/*  */
      }
      chart5.setOption(option)
    },
    showChart6 (xAxis, data, data_bs) {
      let chart6 = echarts.init(document.getElementById("chart6"))
      // var tooltipData = [[1,20],[10,2],[1,2],[1,52],[1,6],[10,2]]
      let data1 = []
      let data2 = []
      data.map(val => {
        data1.push(val * 0.9)
        data2.push(val)
      })
      // for (let i = 0; i < 9; i++) {
      //   let num = Math.floor(Math.random() * 100)
      //   data1.push(num)
      //   data2.push(num + 5)
      // }
      let option = {
        legend: {
          right: 200,
          top: -3,
          itemWidth: 7,
          itemHeight: 7,
          textStyle: {
            color: '#ddd'
          }
        },
        tooltip: {
          show:true,
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
            top: 30,
            left: 30,
            right: 50,
            bottom: 20,
            containLabel: true
        },
        xAxis: [{
          type: 'category',
          name: '',
          nameTextStyle: {
            padding: [0, 0, -40, 0],
            color: 'rgba(230,250,249,0.8)'
          },
          data: xAxis,
          axisLine: {
            show: true,
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.5)',
              type: 'dotted'
            }
          },
          axisLabel: {
            margin: 10,
            interval:0,
            color: 'rgba(255, 255, 255, 0.5)',
            textStyle: {
              color: '#ddd',
              fontStyle: 'normal',
              fontSize: 12,
            }
          },
          axisTick: {
            show: false
          },
        }],
        yAxis: [{
          name: '累计成交笔数',
          axisLabel: {
            margin: 10,
            color: '#ddd',
            textStyle: {
              fontSize: 14
            },
          },
          nameTextStyle: {
            padding: [0, 0, 0, -40],
            color: '#ddd',
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#ddd',
              type: 'dotted'
            }
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          }
        },
        {
          type: 'value',
          position: 'right',
          name: '累计成交金额',
          splitNumber: 5,
          axisLabel: {
            show: true,
            textStyle: {
              color: '#ddd',
              fontStyle: 'normal',
              fontSize: 12,
            }
          },
          nameTextStyle: {
            padding: [0, 0, 0, 20],
            color: '#ddd',
          },
          axisLine:{
            show: true,
            lineStyle:{
              color: '#ddd',
              type: 'dotted'
            }
          },
          axisTick:{
            show: false
          },
          splitLine: {
            show: false
          }

        }],
        series: [
          // {
          //     type: 'bar',
          //     barGap: '-100%',
          //     yAxisIndex: 0,
          //     label: {
          //       show: true,
          //       position: 'top',
          //       fontSize: 12,
          //       color: "rgba(243, 242, 84, 1)"
          //     },
          //     barWidth: 10,
          //     itemStyle: {
          //         normal: {
          //             borderColor: "rgba(243, 242, 84, 1)",
          //             borderWidth: 1,
          //             barBorderRadius: 50,
          //             color: 'rgba(102, 102, 102,0)'
          //         },
          //     },
          //     z: 1,
          //     data: data2
          // },
          {
              type: 'bar',
              name:'累计成交笔数',
              barGap: '-100%',
              yAxisIndex: 0,
              barWidth: 10,
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: "rgba(243, 242, 84, 1)"
                    },
                    {
                      offset: 1,
                      color: "rgba(76, 210, 64, 1)"
                    }
                  ]),
                  barBorderRadius: 50
                }
              },
              max: 100,
              label: {
                show: true,
                position: 'top',
                fontSize: 12,
                color: "rgba(243, 242, 84, 1)"
              },
              labelLine: {
                  show: true,
              },
              z: 2,
              data: data_bs
          },
              {
                name:'累计成交金额',
                type:'bar',
                yAxisIndex: 1,
                data: data2,
                barWidth: 10,
                barGap:0.5,//柱间距离
                label: {
                  show: false,
                  position: 'top',
                  fontSize: 12,
                  color: "rgba(255, 255, 255, 1)"
                },
                itemStyle: {
                  normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                      {
                        offset: 0,
                        color: "rgba(255, 255, 255, 1)"
                      },
                      {
                        offset: 1,
                        color: "rgba(50, 100, 250, 1)"
                      }
                    ]),
                    barBorderRadius: 50
                  }
                },
              },
        ]
      }
      chart6.setOption(option)
    },
    randerMap (data_wdj, data_yrz, data_zzdj, type) {
      this.map = echarts.init(document.getElementById('map'))
      let optionArr = [
        {id: '331003001', name:	'东城街道'},
        {id: '331003002', name:	'南城街道'},
        {id: '331003003', name:	'西城街道'},
        {id: '331003004', name:	'北城街道'},
        {id: '331003005', name:	'新前街道'},
        {id: '331003006', name:	'澄江街道'},
        {id: '331003007', name:	'江口街道'},
        {id: '331003008', name:	'高桥街道'},
        {id: '331003101', name:	'宁溪镇'},
        {id: '331003102', name:	'北洋镇'},
        {id: '331003103', name:	'头陀镇'},
        {id: '331003107', name:	'院桥镇'},
        {id: '331003108', name:	'沙埠镇'},
        {id: '331003200', name:	'屿头乡'},
        {id: '331003201', name:	'上郑乡'},
        {id: '331003202', name:	'富山乡'},
        {id: '331003203', name:	'茅畲乡'},
        {id: '331003204', name:	'上垟乡'},
        {id: '331003205', name:	'平田乡'}
      ]
      var handleEvents = {
        resetOption: function(i, o, n) {
          var breadcrumb = this.createBreadcrumb(n);

          var j = name.indexOf(n);
          var l = o.graphic.length;
          if (j < 0) {
            o.graphic.push(breadcrumb);
            o.graphic[0].children[0].shape.x2 = 145;
            o.graphic[0].children[1].shape.x2 = 145;
            if (o.graphic.length > 2) {
              for (var x = 0; x < opt.data.length; x++) {
                if (n === opt.data[x].name) {
                  o.series[0].data = handleEvents.initSeriesData([opt.data[x]]);
                  break;
                } else o.series[0].data = [];
              }
            }
            name.push(n);
            idx++;
          } else {
            o.graphic.splice(j + 2, l);
            if (o.graphic.length <= 2) {
              o.graphic[0].children[0].shape.x2 = 60;
              o.graphic[0].children[1].shape.x2 = 60;
              o.series[0].data = handleEvents.initSeriesData(opt.data);
              o.series[0].data = handleEvents.initSeriesData(opt.persondata);
            }
            name.splice(j + 1, l);
            idx = j;
            pos.leftCur -= pos.leftPlus * (l - j - 1);
          }

          o.geo.map = n;
          o.geo.zoom = 0.8;
          i.clear();
          i.setOption(o);
          this.zoomAnimation();
          opt.callback(n, o, i);
        },
        createBreadcrumb: function(name) {
          var cityToPinyin = {
            海港区: "haigangqu",
            山海关区: "shanhaiguanqu",
            北戴河区: "beidaihequ",
            抚宁区: "funingqu",
            青龙满族自治县: "qinglongmanzuzizhixian",
            昌黎县: "changlixian",
            卢龙县: "lulongxian"
          };
          var breadcrumb = {
            type: "group",
            id: name,
            left: pos.leftCur + pos.leftPlus,
            top: pos.top + 3,
            children: [
              {
                type: "polyline",
                left: -90,
                top: -5,
                shape: {
                  points: line
                },
                style: {
                  stroke: "#fff",
                  key: name
                  // lineWidth: 2,
                },
                onclick: function() {
                  var name = this.style.key;
                  handleEvents.resetOption(chart, option, name);
                }
              },
              {
                type: "text",
                left: -68,
                top: "middle",
                style: {
                  text: name,
                  textAlign: "center",
                  fill: style.textColor,
                  font: style.font
                },
                onclick: function() {
                  var name = this.style.text;
                  handleEvents.resetOption(chart, option, name);
                }
              },
              {
                type: "text",
                left: -68,
                top: 10,
                style: {
                  name: name,
                  text: cityToPinyin[name]
                    ? cityToPinyin[name].toUpperCase()
                    : "",
                  textAlign: "center",
                  fill: style.textColor,
                  font: '12px "Microsoft YaHei", sans-serif'
                },
                onclick: function() {
                  // console.log(this.style);
                  var name = this.style.name;
                  handleEvents.resetOption(chart, option, name);
                }
              }
            ]
          };

          pos.leftCur += pos.leftPlus;

          return breadcrumb;
        },
        // 设置effectscatter
        initSeriesData: function(data) {
          var temp = [];
          for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
              temp.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value, data[i].level)
              });
            }
          }
          return temp;
        },
        zoomAnimation: function() {
          var count = null;
          var zoom = function(per) {
            if (!count) count = per;
            count = count + per;
            chart.setOption({
              geo: {
                zoom: count
              }
            });
            if (count < 1)
              window.requestAnimationFrame(function() {
                zoom(0.5);
              });
          };
          window.requestAnimationFrame(function() {
            zoom(0.5);
          });
        }
      };
      var levelColorMap = {
        "1": "rgba(241, 109, 115, .8)",
        "2": "rgba(255, 235, 59, .7)",
        "3": "rgba(76, 210, 64, 1)"
      };
      let options = {
        geo: {
          show: true,
          zlevel:13,
          map: 'huangyanqu',
            itemStyle: {
              normal: {
                borderColor: "rgba(147, 235, 248, 1)",
                borderWidth: 1,
                areaColor: {
                  type: "radial",
                  x: 0.5,
                  y: 0.5,
                  r: 0.8,
                  colorStops: [
                    {
                      offset: 0,
                      color: "rgba(147, 235, 248, 0)" // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: "rgba(147, 235, 248, .1)" // 100% 处的颜色
                    }
                  ],
                  globalCoord: false // 缺省为 false
                },
                shadowColor: "rgba(128, 217, 248, 1)",
                // shadowColor: 'rgba(255, 255, 255, 1)',
                shadowOffsetX: -2,
                shadowOffsetY: 2,
                shadowBlur: 10
              },
              emphasis: {
                areaColor: "rgba(128, 217, 248, 0.5)",
                borderWidth: 0
              }
            },
            label: {
              show: true,
              textStyle: {
                color: '#ffffff', //地图初始化区域字体颜色
                fontSize: 14,
                opacity: 1,
                fontFamily: 'Microsoft YaHei',
                backgroundColor: 'rgba(11,63,83,0)'
              },
              emphasis: {
                color: 'rgba(0, 0, 0, 1)'
              }
            },
            emphasis: { //当鼠标放上去  地区区域是否显示名称
              textStyle: {
                color: 'rgba(255,230,243, 0.9)',
              },
              itemStyle: {
                color: 'rgba(255,230,243, 0.8)'
              }
            },
        },
        series: [
          {
            name: 'effectScatter',
            type: "effectScatter",
            coordinateSystem: 'geo',
            symbol: 'circle',
            shading: 'lambert',
            bevelSize:0.5,
            zlevel: 11,
            showEffectOn: "render",
            rippleEffect: {
              period: 5,
              scale: 4,
              brushType: "fill"
            },
            hoverAnimation: true,
            itemStyle: {
              normal: {
                color: 'rgba(241, 109, 115, 0.8)',
                shadowBlur: 6,
                shadowColor: "#333"
              }
            },
            symbolSize: 14,
            data: data_wdj
          },
          {
            name: 'effectScatter',
            type: "effectScatter",
            coordinateSystem: 'geo',
            symbol: 'circle',
            shading: 'lambert',
            bevelSize:0.5,
            zlevel: 11,
            showEffectOn: "render",
            rippleEffect: {
              period: 5,
              scale: 4,
              brushType: "fill"
            },
            hoverAnimation: true,
            itemStyle: {
              normal: {
                color: 'rgba(255, 235, 59, .7)',
                shadowBlur: 6,
                shadowColor: "#333"
              }
            },
            symbolSize: 10,
            data: data_zzdj
          },
          {
            name: 'effectScatter',
            type: "effectScatter",
            coordinateSystem: 'geo',
            symbol: 'circle',
            shading: 'lambert',
            bevelSize:0.5,
            zlevel: 11,
            showEffectOn: "render",
            rippleEffect: {
              period: 5,
              scale: 4,
              brushType: "fill"
            },
            hoverAnimation: true,
            itemStyle: {
              normal: {
                color: 'rgba(76, 210, 64, 1)',
                shadowBlur: 6,
                shadowColor: "#333"
              }
            },
            symbolSize: 10,
            data: data_yrz
          },
        ]
      }
      this.map.setOption(options)
      if (type && type == 'first') {
        this.map.on("click", (params) => {
          let index = optionArr.findIndex(val => {return val.name === params.name})
          if (index >= 0) {
            this.name2 = params.name
            this.initMap2(optionArr[index].id)
          }
        })
      }
    },
    initMap2 (id) {
      this.addressCode = id
      this.refreshData()
      this.$get('http://api.hy96596.com/pbc/api/sst/getQydtzb', {
        addressCode: id
      }).then(res => {
        if (res.code === 200) {
          let data_wdj = res.data.data_wdj
          let data_yrz = res.data.data_yrz
          let data_zzdj = res.data.data_zzdj
          this.map2 = echarts.init(document.getElementById('map2'))
          let options = {
            geo: {
              show: true,
              zlevel:13,
              map: id,
                itemStyle: {
                  normal: {
                    borderColor: "rgba(147, 235, 248, 1)",
                    borderWidth: 1,
                    areaColor: {
                      type: "radial",
                      x: 0.5,
                      y: 0.5,
                      r: 0.8,
                      colorStops: [
                        {
                          offset: 0,
                          color: "rgba(147, 235, 248, 0)" // 0% 处的颜色
                        },
                        {
                          offset: 1,
                          color: "rgba(147, 235, 248, .1)" // 100% 处的颜色
                        }
                      ],
                      globalCoord: false // 缺省为 false
                    },
                    shadowColor: "rgba(128, 217, 248, 1)",
                    // shadowColor: 'rgba(255, 255, 255, 1)',
                    shadowOffsetX: -2,
                    shadowOffsetY: 2,
                    shadowBlur: 10
                  },
                  emphasis: {
                    areaColor: "rgba(128, 217, 248, 0.5)",
                    borderWidth: 0
                  }
                },
                label: {
                  show: true,
                  textStyle: {
                    color: '#ffffff', //地图初始化区域字体颜色
                    fontSize: 14,
                    opacity: 1,
                    fontFamily: 'Microsoft YaHei',
                    backgroundColor: 'rgba(11,63,83,0)'
                  },
                  emphasis: {
                    color: 'rgba(0, 0, 0, 1)'
                  }
                },
                emphasis: { //当鼠标放上去  地区区域是否显示名称
                  textStyle: {
                    color: 'rgba(255,230,243, 0.9)',
                  },
                  itemStyle: {
                    color: 'rgba(255,230,243, 0.8)'
                  }
                },
            },
            series: [
              {
                name: 'effectScatter',
                type: "effectScatter",
                coordinateSystem: 'geo',
                symbol: 'circle',
                shading: 'lambert',
                bevelSize:0.5,
                zlevel: 11,
                showEffectOn: "render",
                rippleEffect: {
                  period: 5,
                  scale: 4,
                  brushType: "fill"
                },
                hoverAnimation: true,
                itemStyle: {
                  normal: {
                    color: 'rgba(241, 109, 115, 0.8)',
                    shadowBlur: 6,
                    shadowColor: "#333"
                  }
                },
                symbolSize: 14,
                data: data_wdj
              },
              {
                name: 'effectScatter',
                type: "effectScatter",
                coordinateSystem: 'geo',
                symbol: 'circle',
                shading: 'lambert',
                bevelSize:0.5,
                zlevel: 11,
                showEffectOn: "render",
                rippleEffect: {
                  period: 5,
                  scale: 4,
                  brushType: "fill"
                },
                hoverAnimation: true,
                itemStyle: {
                  normal: {
                    color: 'rgba(255, 235, 59, .7)',
                    shadowBlur: 6,
                    shadowColor: "#333"
                  }
                },
                symbolSize: 10,
                data: data_zzdj
              },
              {
                name: 'effectScatter',
                type: "effectScatter",
                coordinateSystem: 'geo',
                symbol: 'circle',
                shading: 'lambert',
                bevelSize:0.5,
                zlevel: 11,
                showEffectOn: "render",
                rippleEffect: {
                  period: 5,
                  scale: 4,
                  brushType: "fill"
                },
                hoverAnimation: true,
                itemStyle: {
                  normal: {
                    color: 'rgba(76, 210, 64, 1)',
                    shadowBlur: 6,
                    shadowColor: "#333"
                  }
                },
                symbolSize: 10,
                data: data_yrz
              },
            ]
          }
          this.map2.setOption(options)
        }
      })
    },
    initSeriesData: function(data) {
      var temp = [];
      for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
          temp.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value, data[i].level)
          });
        }
      }
      return temp;
    },
    getTopLeft_text1() {
      let time = new Date();
      this.time = `${time.getFullYear()}/${this.getNumber(
        time.getMonth() + 1
      )}/${this.getNumber(time.getDate())}  ${this.getNumber(
        time.getHours()
      )}:${this.getNumber(time.getMinutes())}:${this.getNumber(
        time.getSeconds()
      )}`;
    },
    getNumber(num) {
      return num < 10 ? "0" + num : num;
    },
  }
}
</script>
<style scoped lang="stylus">
.home
  position relative
  min-height 100vh
  min-width 1900px
  background: linear-gradient(180deg, #1c4989 0%, #000940 100%);
  .background
    position absolute
    top -15%
    left 0
    width 100%
    height 100%
    background-color #000
    background url('../../../assets/images/bg2.png') no-repeat center
    background-size 90% 90%
    z-index 0
  .title
    padding 5px 20px
    margin-bottom 8px
    background: linear-gradient(90deg, rgba(0, 255, 255, 0.1) 0%, rgba(0, 0, 0, 0) 100%)
    position relative
    &:before
      content ''
      position absolute
      top 0
      left 0
      width 1px
      height 100%
      background-color #0ff
    h2
      font-size 16px
      color rgba(255, 255, 255, 1)
      line-height 35px
      font-weight 100
    p
      font-size 12px
      color rgba(255, 255, 255, 0.5)
  .top_title
    width 100%
    height 70px
    background url('../../../assets/images/header.png') no-repeat center
    background-size 100% 100%
    h1
      color #0ff
      font-weight 600
      text-align center
      line-height 70px
      font-size 35px
      letter-spacing 8px
      margin 0
      position relative
      .point
        display block
        position absolute
        bottom 3px
        left 0
        width 20px
        height 1px
        border-radius 50%
        background-color rgba(255, 255, 255, 0.3)
        box-shadow 0 0 10px 3px rgba(255, 255, 255, 0.5) 
        animation movePoint 10s 0s linear infinite
    .rightTime
      font-size 13px
      color #eee
      position absolute
      top 20px
      right 20px
  .top_content
    display flex
    height 650px
    padding 20px
    .left, .right
      width 400px
      .top
        height 330px
        margin-bottom 20px
      .bottom
        height calc(100% - 350px)
      .top, .bottom
        position relative
    .right
      .top
        height 250px
      .bottom
        height calc(100% - 270px)
    .center
      flex 1
      margin-right 20px
      position relative
      &:after
        width 1188px
        height 753px
        content ''
        position absolute
        top 0
        left 0
        right 0
        bottom 0
        margin auto
        background url('../../../assets/images/bg.png') no-repeat center
        background-size 100% 100%
        transform-origin top left
        z-index 0
      &:before
        content ''
        position absolute
        top 0
        left 0
        right 0
        bottom 0
        margin auto
        width 0
        height 0
        background rgba(255, 255, 0, 0.1)
        box-shadow 0 0 250px 250px rgba(255, 255, 0, 0.1)
        border-radius 50%
        animation map 5s 0s infinite
      .map
        width 100%
        height 100%
        position absolute
        top 0
        left 0
        right 0
        bottom 0
        margin auto
        z-index 10
      .mapName
        position absolute
        top 0
        left 0
        color #fff
        line-height 20px
        display flex
        z-index 12
        span
          margin 0 10px
        p
          &:nth-of-type(1)
            font-size 16px
            cursor pointer
          &:nth-of-type(2)
            font-size 14px
            color #ff0
    .left
      margin-right 20px
  .bottom_content
    height 360px
    padding 0 20px 20px 20px
    display flex
    .left, .right
      flex 1
    .left
      margin-right 20px
  .dataList3
    position absolute
    width 200px
    top 90px
    right 20px
    height 200px
    .item
      display flex
      height 30px
      line-height 30px
      margin-bottom 20px
      i
        width 10px
        height 10px
        margin 10px 8px
        border-radius 50%
      p
        color #eee
        font-size 13px
        &:nth-of-type(1)
          width 70px
        &:nth-of-type(2)
          flex 1
         &:nth-of-type(3)
          width 60px
      &:nth-of-type(1)
        border 1px solid rgba(243, 242, 84, 1)
        i
          border 1px solid rgba(243, 242, 84, 1)
        p
          &:nth-of-type(1)
            color rgba(243, 242, 84, 1)
      &:nth-of-type(2)
        border 1px solid rgba(76, 210, 64, 1)
        i
          border 1px solid rgba(76, 210, 64, 1)
        p
          &:nth-of-type(1)
            color rgba(76, 210, 64, 1)
      &:nth-of-type(3)
        border 1px solid rgba(236, 79, 64, 1)
        i
          border 1px solid rgba(236, 79, 64, 1)
        p
          &:nth-of-type(1)
            color rgba(236, 79, 64, 1)
  .chart
    width 100%
    height 100%
  .card
    background-color rgba(0, 255, 255, 0.1)
    padding 10px
    height calc(100% - 60px)
    position relative
    &:before
      content ''
      position absolute
      top 0
      left 0
      width 1px
      height 100%
      background-color #0ff
  .map-legend
    position absolute
    bottom 10px
    right 20px
    .row
      display flex
      margin-bottom 15px
      line-height 16px
      i
        width 10px
        height 10px
        border-radius 50%
        position relative
        margin-right 10px
      span
        color rgba(255, 255, 255, 0.9)
        font-size 14px
      .legend2-icon, .legend3-icon, .legend4-icon
        left 3px
        top 3px
        margin-right 15px
        &:before
          content ''
          width 18px
          height 18px
          border-radius 50%
          position absolute
          top -4px
          left -4px
        &:after
          content ''
          width 26px
          height 26px
          border-radius 50%
          position absolute
          top -8px
          left -8px
      .legend2-icon
        background-color rgba(241, 109, 115, .8)
        &:before
          background-color rgba(241, 109, 115, .5)
        &:after
          background-color rgba(241, 109, 115, .2)
      .legend3-icon
        background-color rgba(255, 235, 59, .7)
        &:before
          background-color rgba(255, 235, 59, .4)
        &:after
          background-color rgba(255, 235, 59, .1)
      .legend4-icon
        background-color rgba(76, 210, 64, .7)
        &:before
          background-color rgba(76, 210, 64, .4)
        &:after
          background-color rgba(76, 210, 64, .1)

@keyframes map 
  0% 
    box-shadow 0 0 100px 100px rgba(255, 255, 100, 0.01)
  50%
    box-shadow 0 0 250px 250px rgba(255, 255, 100, 0.1)
  100% 
    box-shadow 0 0 100px 100px rgba(255, 255, 100, 0.01)

@keyframes movePoint
  0% 
    opacity 0
    left 0
  10%
    opacity 0
  25% 
    opacity 1
    left 50%
  40%
    opacity 0
  50%
    opacity 0
    left calc(100% - 20px)
  60%
    opacity 0
  75% 
    opacity 1
    left 50%
  90%
    opacity 0
  100% 
    opacity 0
    left 0
</style>
<style lang="stylus">
.home
  .tooltipName
    color #fff
    font-size 14px
  .p1
    display flex
    color #fff
    font-size 13px
</style>