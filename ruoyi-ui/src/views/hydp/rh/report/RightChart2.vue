<template>
  <div class="right-chart-2">
    <div class="rc1-header">企业融资情况</div>

    <div class="rc1-chart-container">
      <div class="left">
        <div class="number">267</div>
        <div>融资需求总数</div>
      </div>

      <!--
      <dv-charts class="right" :option="option" />
      -->
      <dv-active-ring-chart :config="config" style="width:300px;height:300px" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'RightChart2',
  data () {
    return {
      config: {
        data: [
          {
            name: '未对接',
            value: 93
          },
          {
            name: '已融资',
            value: 66
          },
          {
            name: '有问题',
            value: 52
          }
        ],
        color: ['red', 'green', 'yellow'],
        lineWidth: 30,
        radius: '55%',
        activeRadius: '60%',
        showOriginValue: true
      }
    }
  }
}
</script>

<style lang="less">
.right-chart-2 {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;

  .rc1-header {
    font-size: 24px;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
  }

  .rc1-chart-container {
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
