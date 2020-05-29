<template>
  <div class="app-container">

    <el-row>
      <el-col :span="8">
      &nbsp;
      </el-col>
      <el-col :span="8">
        <div class="query-area">

            <el-input placeholder="请输入表名或字段名(中英文都可)" v-model="queryParams.queryValue" class="input-with-select" size="large" @keyup.enter.native="handleQuery">
              <!--
              <el-select v-model="queryParams.reserver1" slot="prepend" placeholder="请选择系统类型" clearable filterable size="small">
                <el-option
                  v-for="dict in systemCodeOptions"
                  :key="dict.key"
                  :label="dict.value"
                  :value="dict.value"
                />
              </el-select>
              -->
              <el-button type="primary" slot="append" icon="el-icon-search" @click="handleQuery">搜索</el-button>
            </el-input>

        </div>
      </el-col>
      <el-col :span="8">
      &nbsp;
      </el-col>
    </el-row>
    
    <el-table v-loading="loading" :data="dataDictList" stripe border :height="tableHeight">
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column label="系统分类" align="center" prop="systemName" sortable
        :filters="systemCodeOptionsForFilter"
        :filter-method="filterHandler"
      />
      <el-table-column label="表英文名" align="center" prop="tableName" sortable/>
      <el-table-column label="表中文名" align="center" prop="tableCnName" sortable/>
      <el-table-column label="列英文名" align="center" prop="columnName" sortable/>
      <el-table-column label="列中文名" align="center" prop="columnCnName" sortable/>
      <el-table-column label="数据类型" align="center" prop="dataTypeShow" />
      <el-table-column label="数据长度" align="center" prop="dataLength" />
      <el-table-column label="库名" align="center" prop="schema" sortable/> 
    </el-table>
    
  </div>
</template>

<style>
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .query-area .el-input {
    width: 500px;
  }
  .el-select .el-input{
    width: 150px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>

<script>
import { listDataDict, getDataDict, getSystemCode } from "@/api/hydp/tool/dataDict";

export default {
  name: "DataDict",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 数据字典查询表格数据
      dataDictList: [],
      //系统分类字典
      systemCodeOptions: [],
      systemCodeOptionsForFilter: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        queryValue: undefined,
        reserver1: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        queryValue: [
            { required: true, message: '查询参数非空', trigger: 'blur' }
        ]
      },
      tableHeight: window.innerHeight - 200
    };
  },
  created() {
    //this.getList();
    window.addEventListener('resize', this.getHeight);
    getSystemCode().then(response => {
      this.systemCodeOptions = response.data;
    });
  },
  destroyed () {
    window.removeEventListener('resize', this.getHeight);
  },
  methods: {
    /** 查询数据字典查询列表 */
    getList() {
      if(this.queryParams.queryValue == null){
        alert("查询条件不能为空");
        return;
      }
      this.loading = true;
      listDataDict(this.queryParams).then(response => {
        this.dataDictList = response.rows;
        this.total = response.total;
        this.loading = false;

        //动态生成filter数组
        var set = new Set();  
        response.rows.forEach(function (item, idnex, array) {
            var systemName = item.systemName;
            console.log(systemName);
            set.add(systemName);
        })

        var arr = [];
        for(var s of set){
          var obj = new Object();
          obj.text = s;
          obj.value = s;
          arr.push(obj);
        }
        this.systemCodeOptionsForFilter = arr;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() { 
      this.getList();
    },
    getHeight () {
      this.tableConfig.height = window.innerHeight - 200
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    }
  }
};
</script>