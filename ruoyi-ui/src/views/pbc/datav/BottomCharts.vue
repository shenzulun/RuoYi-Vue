<template>
    <div id="scroll-board">
      <dv-scroll-board :config="config" />
    </div>
  </template>
  
  <script>
  export default {
    name: 'ScrollBoard',
    data () {
      return {
        config: {
          header: ['时间', '企业名称', '融资类型', '融资金额'],
          data: [
            ['2020-08-01 19:25:00', '企业A', '融资类型1', 1000000],
            ['2020-08-02 17:25:00', '企业B', '融资类型2', 1000000],
            ['2020-08-03 16:25:00', '企业C', '融资类型1', 2000000],
            ['2020-08-04 15:25:00', '企业D', '融资类型1', 2000000],
            ['2020-08-05 14:25:00', '企业E', '融资类型1', 1000000],
            ['2020-08-06 13:25:00', '企业F', '融资类型3', 1000000],
            ['2020-08-07 12:25:00', '企业G', '融资类型4', 3000000],
            ['2020-08-08 11:25:00', '企业H', '融资类型2', 1000000],
            ['2020-08-09 10:25:00', '企业I', '融资类型5', 1000000],
            ['2020-08-10 09:25:00', '企业J', '融资类型3', 2000000]
          ],
          index: true,
          columnWidth: [50, 170, 300],
          align: ['center'],
          rowNum: 7,
          headerBGC: 'rgb(7 10 31)',
          headerHeight: 45,
          oddRowBGC: 'rgba(9, 37, 50, 0.4)',
          evenRowBGC: 'rgba(10, 32, 50, 0.3)'
        }
      }
    }
  }
  </script>
  
  <style lang="less">
  #scroll-board {
    width: 100%;
    box-sizing: border-box;
    margin-left: 20px;
    height: 100%;
    overflow: hidden;
  }
  </style>
  