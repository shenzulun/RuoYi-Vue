<template>
    <div>
        <el-container v-if="article.isPdf == '0'">
            <el-header height="30%">
              <h1>{{ article.title }}</h1>
            </el-header>
            <el-main height="65%">
                <div v-html="article.content"></div>
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
import { listArticle, getArticle } from "@/api/pbc/article";
import pdf from 'vue-pdf';

export default {
  name: "ArticlePreview",
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
      articleTypeOptions: [],
      // 发布状态字典
      articleStatusOptions: [],
      article: [],
      pdfURL: ""
    };
  },
  created() {
    let parmas = this.$route.query;
    console.log(parmas.id);
    this.getArticle0(parmas.id);
  },
  methods: {
    /** 查询文章信息列表 */
    getArticle0(id) {
        getArticle(id).then(response => {
            this.article = response.data;
            document.title = this.article.title;

            if(this.article.isPdf == '1'){
              let acc = this.article.accessoryUrl;
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