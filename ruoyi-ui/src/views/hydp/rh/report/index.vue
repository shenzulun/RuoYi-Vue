<template>
  <div id="data-view">
    <dv-full-screen-container>
    <!--
      <div class="main-header">
        <div class="mh-left"></div>
        <div class="mh-middle"></div>
        <div class="mh-right">
          <dv-border-box-2
            style="width: 120px; height: 50px; line-height: 50px; text-align:center;margin-left:200px;"
          >
          企业融资三色图
          </dv-border-box-2>
        </div>
      </div>
    -->
      <dv-border-box-1 class="main-container">
        <!-- 左侧区域 -->
        <dv-border-box-3 class="left-chart-container">

        </dv-border-box-3>

        <!-- 居中区域 -->
        <div class="right-main-container">
          <div class="rmc-top-container">
            <dv-border-box-3 class="rmctc-left-container">

              <map1 />

            </dv-border-box-3>

            <div class="rmctc-right-container">
              <dv-border-box-3 class="rmctc-chart-1">

                <Right-Chart-2 />

              </dv-border-box-3>

              <dv-border-box-3 class="rmctc-chart-2" :reverse="true">

                <Right-Chart-1 />

              </dv-border-box-3>
            </div>
          </div>

          <dv-border-box-8 class="rmc-bottom-container">

              <dv-border-box-7 class="rmc-bottom-container-left">
                  <bottomLeft />
              </dv-border-box-7>

              <dv-border-box-7 class="rmc-bottom-container-right">
                  <bottomRight />
              </dv-border-box-7>

          </dv-border-box-8>
          
        </div>

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

  .main-header {
    height: 80px;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;

    .mh-left {
      font-size: 20px;
      color: rgb(1,134,187);

      a:visited {
        color: rgb(1,134,187);
      }
    }

    .mh-middle {
      font-size: 30px;
    }

    .mh-left, .mh-right {
      width: 450px;
    }
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

  .head-container {
    height: 65%;
  }

  .botton-container {
    height: 35%;
  }

  .left-chart-container {
    width: 22%;
    height: 65%;
    padding: 10px;
    box-sizing: border-box;

    .border-box-content {
      flex-direction: column;
    }
  }

  .bottom-chart-container {
    width:100%;
    height: 35%;
    padding: 10px;
    box-sizing: border-box;

    .border-box-content {
      flex-direction: column;
    }
  }

  .right-main-container {
    width: 78%;
    padding-left: 5px;
    box-sizing: border-box;
  }

  .rmc-top-container {
    height: 65%;
    display: flex;
  }

  .rmctc-left-container {
    width: 65%;
  }

  .rmctc-right-container {
    width: 35%;
  }

  .rmc-bottom-container {
    height: 35%;
  }

  .rmctc-chart-1, .rmctc-chart-2 {
    height: 50%;
  }

  .rmc-bottom-container-left {
    width: 50%;
  }

  .rmc-bottom-container-right {
    width: 50%;
  }
}
</style>
