<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="需求编号" prop="demandNo">
        <el-input
          v-model="queryParams.demandNo"
          placeholder="请输入需求编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户号" prop="custNo">
        <!--
        <el-input
          v-model="queryParams.custNo"
          placeholder="请输入客户号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
        -->
        <Custno v-model="queryParams.custNo"></Custno>
      </el-form-item>
      <el-form-item label="解决状态" prop="solveStatus">
        <el-select v-model="queryParams.solveStatus" placeholder="请选择解决状态" clearable size="small">
          <el-option
            v-for="dict in solveStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="解决银行" prop="solveBankNo">
        <!--
        <el-input
          v-model="queryParams.solveBankNo"
          placeholder="请输入解决银行"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
        -->
        <Bankno v-model="queryParams.solveBankNo"></Bankno>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pbc:demand:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pbc:demand:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pbc:demand:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pbc:demand:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="demandList" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" v-if="false"/>
      <el-table-column label="需求编号" align="center" prop="demandNo" />
      <el-table-column label="客户号" align="center" prop="custNo" />
      <el-table-column label="客户名称" align="center" prop="custName" />
      <el-table-column label="融资类型" align="center" prop="loanType" :formatter="loanTypeFormat" />
      <el-table-column label="融资金额" align="center" prop="loanAmount" />
      <el-table-column label="保证方式" align="center" prop="guaranteeType" :formatter="guaranteeTypeFormat" />
      <el-table-column label="解决状态" align="center" prop="solveStatus" :formatter="solveStatusFormat" />
      <el-table-column label="解决银行" align="center" prop="bankName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pbc:demand:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pbc:demand:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改企业融资需求对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户号" prop="custNo">
          <!--
          <el-input v-model="form.custNo" placeholder="请输入客户号" />
          -->
          <Custno v-model="form.custNo"></Custno>
        </el-form-item>
        <el-form-item label="融资类型">
          <el-select v-model="form.loanType" placeholder="请选择融资类型">
            <el-option
              v-for="dict in loanTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="融资金额" prop="loanAmount">
          <el-input type="number" v-model="form.loanAmount" placeholder="请输入融资金额" />
        </el-form-item>
        <el-form-item label="保证方式">
          <el-select v-model="form.guaranteeType" placeholder="请选择保证方式">
            <el-option
              v-for="dict in guaranteeTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="解决状态">
          <el-select v-model="form.solveStatus" placeholder="请选择解决状态">
            <el-option
              v-for="dict in solveStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="解决银行" prop="solveBankNo">
          <!--
          <el-input v-model="form.solveBankNo" placeholder="请输入解决银行" />
          -->
          <Bankno v-model="form.solveBankNo"></Bankno>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDemand, getDemand, delDemand, addDemand, updateDemand, exportDemand } from "@/api/pbc/demand";
import Custno from "@/components/Choose/Custno";
import Bankno from "@/components/Choose/Bankno";

export default {
  name: "Demand",
  components: {
    Custno,Bankno
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 企业融资需求表格数据
      demandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 融资类型字典
      loanTypeOptions: [],
      // 保证方式字典
      guaranteeTypeOptions: [],
      // 解决状态字典
      solveStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        demandNo: undefined,
        custNo: undefined,
        solveStatus: undefined,
        solveBankNo: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("LOAN_TYPE").then(response => {
      this.loanTypeOptions = response.data;
    });
    this.getDicts("GUAR_TYPE").then(response => {
      this.guaranteeTypeOptions = response.data;
    });
    this.getDicts("RZ_STATUS").then(response => {
      this.solveStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询企业融资需求列表 */
    getList() {
      this.loading = true;
      listDemand(this.queryParams).then(response => {
        this.demandList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 融资类型字典翻译
    loanTypeFormat(row, column) {
      return this.selectDictLabel(this.loanTypeOptions, row.loanType);
    },
    // 保证方式字典翻译
    guaranteeTypeFormat(row, column) {
      return this.selectDictLabel(this.guaranteeTypeOptions, row.guaranteeType);
    },
    // 解决状态字典翻译
    solveStatusFormat(row, column) {
      return this.selectDictLabel(this.solveStatusOptions, row.solveStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        demandNo: undefined,
        custNo: undefined,
        loanType: undefined,
        loanAmount: undefined,
        guaranteeType: undefined,
        solveStatus: undefined,
        solveBankNo: undefined,
        status: "0",
        createUser: undefined,
        createTime: undefined,
        updateUser: undefined,
        updateDate: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加企业融资需求";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDemand(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改企业融资需求";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDemand(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDemand(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除企业融资需求编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDemand(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有企业融资需求数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDemand(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>