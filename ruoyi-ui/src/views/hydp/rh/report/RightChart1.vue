<template>
  <div class="right-chart-1">
    <div class="rc1-header">分区域融资对接情况</div>

    <div class="rc1-chart-container">
      <div class="left">
        <div class="number">500</div>
        <div>企业总数</div>
      </div>

      <dv-capsule-chart class="right" :config="config" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'RightChart1',
  data () {
    return {
      config: {
        data: [
          {
            name: '西城街道',
            value: 41
          },
          {
            name: '东城街道',
            value: 15
          },
          {
            name: '北城街道',
            value: 10
          },
          {
            name: '南城街道',
            value: 9
          },
          {
            name: '院桥',
            value: 50
          }
        ],
        unit: '个'
      }
    }
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
