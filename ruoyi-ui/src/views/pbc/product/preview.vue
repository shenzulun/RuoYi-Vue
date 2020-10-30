<template>
    <div>
        <el-container v-if="product.isPdf == '0'">
            <el-header height="30%">
              <h1>{{ product.name }}</h1>
            </el-header>
            <el-main height="65%">
                <div v-html="product.content"></div>
            </el-main>
            <el-footer height="5%"></el-footer>
        </el-container>
        <iframe v-else  style=" position: absolute;  width: 100%; height: 100%; top: 0;left:0;" :src="pdfURL"></iframe>
    </div>
</template>

<style>
  .el-header, .el-footer {
    color: #333;
    text-align: center;
  }
  
  .el-main {
    color: #333;
    margin: auto 100px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }

</style>

<script>
import { listProduct, getProduct } from "@/api/pbc/product";
import pdf from 'vue-pdf';

export default {
  name: "productPreview",
  metaInfo: {
      // title will be injected into parent titleTemplate
      title: ''
  },
  components:{
      pdf
  },
  data() {
    return {
      // 信息类型字典
      productTypeOptions: [],
      // 发布状态字典
      productStatusOptions: [],
      product: [],
      pdfURL: ""
    };
  },
  created() {
    let parmas = this.$route.query;
    console.log(parmas.id);
    this.getProduct0(parmas.id);
  },
  methods: {
    /** 查询文章信息列表 */
    getProduct0(id) {
        getProduct(id).then(response => {
            this.product = response.data;
            document.title = this.product.name;

            if(this.product.isPdf == '1'){
              let acc = this.product.accessoryUrl;
              if(acc != null && acc != ''){
                let arr2 = acc.split(',')[0].split('|');
                this.pdfURL = arr2[1];
              }
            }
        });
    }
  }
};

</script>