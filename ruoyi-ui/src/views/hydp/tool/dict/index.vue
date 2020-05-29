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

      <el-col :span="4">
        &nbsp;
      </el-col>
      <el-col :span="4">
        <div class="">
          <el-tooltip class="item" effect="light" placement="bottom">
            <div slot="content">
                1. 支持根据表英文名、表中文名、列英文名、列中文名4种查询方式,系统会自动适配<br/>
                <span style="color: red;">2. 查询算法简介:</span><br/>
                &nbsp;&nbsp;&nbsp;&nbsp;1)判断查询条件是否含中文字符<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;2)优先按全名查询表名,找不到再模糊搜索表名,同时模糊搜索列名<br/>
                3. 支持正则表达式,如<span style="color: green;" bold>对公客户(.+?)文件</span>,查询对公客户的补充信息和复制信息<br/>
                4. 支持多个查询条件按逗号分隔    <br/>      
            </div>
            <el-tag size="medium">使用说明</el-tag>
          </el-tooltip>  
        </div>
      </el-col>
    </el-row>
    
    <el-table v-loading="loading" :data="dataDictList" stripe border :height="tableHeight">
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column label="系统分类" align="center" prop="systemName" sortable
        :filters="systemCodeOptionsForFilter"
        :filter-method="filterHandler"
      />
      <el-table-column label="表英文名" align="center" prop="tableName" sortable>
        <template slot-scope="scope">
          <el-link @click="showTableInfo(scope.row)">{{scope.row.tableName}}<i class="el-icon-view el-icon--right"></i> </el-link>
        </template>
      </el-table-column>
      <el-table-column label="表中文名" align="center" prop="tableCnName" sortable/>
      <el-table-column label="列英文名" align="center" prop="columnName" sortable>
        <template slot-scope="scope">
          <el-link @click="showColumnCodeInfo(scope.row)" v-if="scope.row.codeCounts > 0">{{scope.row.columnName}}<i class="el-icon-view el-icon--right"></i> </el-link>
          <span v-else>{{scope.row.columnName}}</span>
        </template>
      </el-table-column>
      <el-table-column label="列中文名" align="center" prop="columnCnName" sortable/>
      <el-table-column label="数据类型" align="center" prop="dataTypeShow" />
      <el-table-column label="数据长度" align="center" prop="dataLength" />
      <el-table-column label="库名" align="center" prop="schema" sortable/> 
    </el-table>

    <!-- 表结构明细 -->
    <el-dialog :title="tableDialogTitle" :visible.sync="tableDialogVisible">
      <el-table :data="tableInfo">
        <el-table-column label="序号" align="center" type="index"/>
        <el-table-column label="列英文名" align="center" prop="columnName" />
        <el-table-column label="列中文名" align="center" prop="columnCnName" />
        <el-table-column label="数据类型" align="center" prop="dataTypeShow" />
        <el-table-column label="数据长度" align="center" prop="dataLength" />
      </el-table>
    </el-dialog>

    <!-- 表字段码值明细 -->
    <el-dialog :title="columnDialogTitle" :visible.sync="columnDialogVisible">
      <el-table :data="columnInfo">
        <el-table-column label="序号" align="center" type="index"/>
        <el-table-column label="key" align="center" prop="key" />
        <el-table-column label="value" align="center" prop="value" />
      </el-table>
    </el-dialog>
    
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
import { listDataDict, getDataDict, getSystemCode, getColumnCode } from "@/api/hydp/tool/dataDict";

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
      tableHeight: window.innerHeight - 200,
      tableInfo: undefined,
      // 弹出层标题
      tableDialogTitle: "",
      // 是否显示弹出层
      tableDialogVisible: false,
      columnInfo: undefined,
      columnDialogTitle: "",
      columnDialogVisible: false
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
        this.$message({
          showClose: true,
          message: '查询内容不能为空...',
          type: 'error'
        });
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
    },
    /** 查询表的明细 */
    showTableInfo(row) {
      var tableName = row.tableName;
      getDataDict(tableName).then(response => {
        this.tableInfo = response.data.columns;
        this.tableDialogTitle = response.data.tableName + " " + response.data.cnName;
        this.tableDialogVisible = true;
      });
    },
    /** 查询表字段的码值 */
    showColumnCodeInfo(row) {
      var param1 = new Object();
      param1.tableName = row.tableName;
      param1.columnName = row.columnName;
      getColumnCode(param1).then(response => {
        this.columnInfo = response.data.codeValues;
        this.columnDialogTitle = response.data.tableName + " " + response.data.columnName;
        this.columnDialogVisible = true;
      });
    }
  }
};
</script>