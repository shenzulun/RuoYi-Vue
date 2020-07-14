<template>
  <div>
    <iframe :src="src" id="mobsf" scrolling="no" frameborder="0"></iframe>
  </div>
</template>
 
 
<script>
  export default {
    data () {
      return {
          src: ""
      }
    },
    created() {
        let p = this.$route.path;
        //TODO 目前设置路由时,如果HTTP开头会被自动当成外链跳转
        p = 'http://' + p.substring(p.lastIndexOf('/') + 1);
        console.log(p);
        this.src = p;
    },
    mounted(){
      /**
      * iframe-宽高自适应显示   
      */
      function changeMobsfIframe(){
        const mobsf = document.getElementById('mobsf');
        const deviceWidth = document.body.clientWidth;
        const deviceHeight = document.body.clientHeight;
        mobsf.style.width = (Number(deviceWidth)) + 'px'; //数字是页面布局宽度差值
        mobsf.style.height = (Number(deviceHeight)) + 'px'; //数字是页面布局高度差
      }
 
      changeMobsfIframe();
 
      window.onresize = function(){
        changeMobsfIframe()
      }
    },     
  }
</script>