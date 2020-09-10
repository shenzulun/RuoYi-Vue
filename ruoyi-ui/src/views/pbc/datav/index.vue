<template>
  <div id="data-view">
    <dv-full-screen-container>
        <dv-border-box-1 class="main-container">        
        <el-container>        
            <el-main height="65%">
                <el-container direction="horizontal" style="height: 100%">    
                    <el-aside width="22%">
                        <dv-border-box-11 title="各类清单企业数量" class="rmctc-chart-1">
                            <LeftChart1 />           
                          </dv-border-box-11>    

                          <dv-border-box-1 title="惠企政策条目数" class="rmctc-chart-2">       
                            <LeftChart2 />
                          </dv-border-box-1>
                    </el-aside>

                    <el-main width="51%">
                        <dv-border-box-3 class="rmctc-left-container">
                            <map1 />             
                          </dv-border-box-3>
                    </el-main>

                    <el-aside width="27%">
                        <dv-border-box-11 title="企业融资情况" class="rmctc-chart-1">
                            <Right-Chart-2 />           
                          </dv-border-box-11>    

                          <dv-border-box-8 title="分区域融资对接情况" class="rmctc-chart-2">       
                            <Right-Chart-1 />
                          </dv-border-box-8>
                    </el-aside>
                </el-container>
            </el-main>
            <el-footer height="35%">
                <el-container direction="horizontal" style="height: 100%"> 
                    <el-aside width="50%">
                        <dv-border-box-12 class="rmc-bottom-container">
                            <bottomLeft />
                        </dv-border-box-12>
                    </el-aside>

                    <el-aside width="50%">
                        <dv-border-box-12 class="rmc-bottom-container">
                            <bottomRight />
                        </dv-border-box-12>
                    </el-aside>
                </el-container>
            </el-footer>
          </el-container>
        </dv-border-box-1>
    </dv-full-screen-container>
  </div>
</template>

<script>
import LeftChart1 from './LeftChart1'
import LeftChart2 from './LeftChart2'
import LeftChart3 from './LeftChart3'

import CenterCmp from './CenterCmp'

import RightChart1 from './RightChart1'
import RightChart2 from './RightChart2'

import BottomCharts from './BottomCharts'
import digitalFlop from './digitalFlop'

import map1 from './map1'
import bottomLeft from './bottomLeft'
import bottomRight from './bottomRight'

export default {
  name: 'DataView',
  components: {
    LeftChart1,
    LeftChart2,
    LeftChart3,
    CenterCmp,
    RightChart1,
    RightChart2,
    BottomCharts,
    map1,
    digitalFlop,
    bottomLeft,
    bottomRight
  },
  data () {
    return {}
  }
}
</script>

<style lang="less">
#data-view {
  width: 100%;
  height: 100%;
  background-color: #030409;
  color: #fff;
  font-family: '微软雅黑, Microsoft Yahei, LiHei Pro, Hiragino Sans, GBHelvetica Neue, Helvetica, Arial, PingFang SC, WenQuanYi Micro Hei, sans-serif';
  font-size: 14px;

  #dv-full-screen-container {
    background-image: url('./img/bg.png');
    background-size: 100% 100%;
    box-shadow: 0 0 3px blue;
    display: flex;
    flex-direction: column;
  }

  .main-container {
    height: calc(~"100% - 80px");
    margin-left: 0px;

    .border-box-content {
      padding: 20px;
      box-sizing: border-box;
      display: flex;
    }
  }

  .el-header, .el-footer {
    color: #fff;
    padding: 1px;
  }
  
  .el-aside {
    color: #fff;
    background: none!important;
    padding: 1px;
  }
  
  .el-main {
    color: #333;
    padding: 1px;
  }

  .rmctc-chart-1, .rmctc-chart-2 {
    height: 50%;
  }

  .rmc-bottom-container {
      height: 100%;
      .border-box-content {
        padding: 20px;
        box-sizing: border-box;
        display: flex;
      }
  }

}
</style>
