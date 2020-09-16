<template>
    <div>
        <el-container>
            <el-header height="30%">
              <h1>{{ article.title }}</h1>
            </el-header>
            <el-main height="65%">
                <div v-html="article.content"></div>
            </el-main>
            <el-footer height="5%"></el-footer>
        </el-container>
    </div>
</template>

<style>
  .el-header, .el-footer {
    color: #333;
    text-align: center;
  }
  
  .el-main {
    color: #333;
    text-align: center;
    margin: auto 20px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }

</style>

<script>
import { listArticle, getArticle } from "@/api/pbc/article";

export default {
  name: "ArticlePreview",
  data() {
    return {
      // 信息类型字典
      articleTypeOptions: [],
      // 发布状态字典
      articleStatusOptions: [],
      article: []
    };
  },
  created() {
    let parmas = this.$route.query;
    console.log(parmas.id);
    this.getArticle0(parmas.id);
    // this.getDicts("ARTICLE_TYPE").then(response => {
    //   this.articleTypeOptions = response.data;
    // });
    // this.getDicts("POST_STATUS").then(response => {
    //   this.articleStatusOptions = response.data;
    // });
  },
  methods: {
    /** 查询文章信息列表 */
    getArticle0(id) {
        getArticle(id).then(response => {
            this.article = response.data;
        });
    },
    // 信息类型字典翻译
    articleTypeFormat(row, column) {
      return this.selectDictLabel(this.articleTypeOptions, row.articleType);
    },
    // 发布状态字典翻译
    articleStatusFormat(row, column) {
      return this.selectDictLabel(this.articleStatusOptions, row.articleStatus);
    }
  }
};

</script>