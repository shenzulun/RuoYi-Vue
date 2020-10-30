<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="所属机构" prop="deptId">
        <!--
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入所属机构"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
        -->
        <Dept v-model="queryParams.deptId"></Dept>
      </el-form-item>
      <el-form-item label="产品类型" prop="productType">
        <el-select v-model="queryParams.productType" placeholder="请选择产品类型" clearable size="small">
          <el-option
            v-for="dict in productTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入产品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['pbc:product:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pbc:product:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pbc:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pbc:product:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="productList" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" v-if="false"/>
      <el-table-column label="所属机构" align="center" prop="deptName" />
      <el-table-column label="产品类型" align="center" prop="productType" :formatter="productTypeFormat" />
      <el-table-column label="产品名称" align="center" prop="name" />
      <!--
      <el-table-column label="内容" align="center" prop="content" />
      -->
      <el-table-column label="是否PDF" align="center" prop="isPdf" v-if="false"/>
      <el-table-column label="附件URL" align="center" prop="accessoryUrl" v-if="false"/>
      <el-table-column label="发布状态" align="center" prop="productStatus" :formatter="productStatusFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
            <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handlePreview(scope.row)"
          >预览</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pbc:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pbc:product:remove']"
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

    <!-- 添加或修改贷款产品对话框 -->
    <el-dialog :title="title" :visible.sync="open"  width="80%" height="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属机构" prop="deptId" v-show="loginUserName=='admin'">
          <!--
          <el-input v-model="form.deptId" placeholder="请输入所属机构" />
          -->
          <Dept v-model="form.deptId"></Dept>
        </el-form-item>
        <el-form-item label="产品类型">
          <el-select v-model="form.productType" placeholder="请选择产品类型">
            <el-option
              v-for="dict in productTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="是否PDF" prop="isPdf">
          <el-select v-model="form.isPdf" placeholder="请选择是否PDF" @change="isPdfOnChange">
            <el-option
              v-for="dict in yesOrNotOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form-item>
        <el-form-item label="内容" prop="content" v-show="form.isPdf != '1'">
          <!--
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
          -->
          <Editor v-model="form.content" />
        </el-form-item>
        <el-form-item label="PDF上传" prop="accessoryUrl" v-show="form.isPdf == '1'">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :headers="headers"
            :on-preview="handleOnPreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :before-upload="handleUploadBefore"
            multiple
            accept=".pdf"
            :limit="1"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传pdf文件</div>
          </el-upload>
          <el-input v-model="form.accessoryUrl" v-show="false" />
      </el-form-item>
        <el-form-item label="发布状态">
          <el-select v-model="form.productStatus" placeholder="请选择发布状态">
            <el-option
              v-for="dict in productStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct, exportProduct } from "@/api/pbc/product";
import Dept from "@/components/Choose/Dept";
import Editor from '@/components/Editor';
import { getToken } from '@/utils/auth';
import store from '@/store';

export default {
  name: "Product",
  components: {
    Dept,Editor
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
      // 贷款产品表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 产品类型字典
      productTypeOptions: [],
      // 发布状态字典
      productStatusOptions: [],
      // 是否字典
      yesOrNotOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        productType: undefined,
        name: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      fileList: [],
      uploadUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      loginUserName: ""
    };
  },
  created() {
    this.getList();
    this.getDicts("LOAN_PRODUCT_TYPE").then(response => {
      this.productTypeOptions = response.data;
    });
    this.getDicts("POST_STATUS").then(response => {
      this.productStatusOptions = response.data;
    });
    this.getDicts("YES_OR_NOT").then(response => {
      this.yesOrNotOptions = response.data;
    });
    this.loginUserName = store.getters.name;
  },
  methods: {
    /** 查询贷款产品列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 产品类型字典翻译
    productTypeFormat(row, column) {
      return this.selectDictLabel(this.productTypeOptions, row.productType);
    },
    // 发布状态字典翻译
    productStatusFormat(row, column) {
      return this.selectDictLabel(this.productStatusOptions, row.productStatus);
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
        deptId: undefined,
        productType: undefined,
        name: undefined,
        content: undefined,
        accessoryUrl: undefined,
        isPdf: undefined,
        productStatus: undefined,
        status: "0",
        createUser: undefined,
        createTime: undefined,
        updateUser: undefined,
        updateTime: undefined
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
      this.title = "添加贷款产品";
      this.form.productStatus = '1';
      this.form.isPdf = '0';
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProduct(id).then(response => {
        this.form = response.data;
        let acc = this.form.accessoryUrl;
        if(acc != null && acc != ''){
          let arr1 = acc.split(',');
          for(let a1 of arr1){
            let arr2 = a1.split('|');
            var obj1 = {name: arr2[0],url: arr2[1]};
            this.fileList.push(obj1);
          }
        }
        this.open = true;
        this.title = "修改贷款产品";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateProduct(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addProduct(this.form).then(response => {
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
      this.$confirm('是否确认删除贷款产品编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProduct(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有贷款产品数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportProduct(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    /** 预览 **/
    handlePreview(row) {
      if(row.isPdf == '1'){
        //pdf直接显示附件
        // let acc = row.accessoryUrl;
        // if(acc != null && acc != ''){
        //   let arr1 = acc.split(',');
        //   let url = "";
        //   for(let a1 of arr1){
        //     let arr2 = a1.split('|');
        //     var win1 = window.open(arr2[1], '_blank');
        //     win1.document.title=arr2[0];
        //   }
        // }else{
        //   this.$message.error("没有附件，无法预览");
        //   return false;
        // }
        const id = row.id;
        let routeData = this.$router.resolve({ 
            path: '/input/product/preview',
            query: {id: row.id},
        });
        window.open(routeData.href, '_blank');
      }else{
        const id = row.id;
        let routeData = this.$router.resolve({ 
            path: '/input/product/preview',
            query: {id: row.id},
        });
        window.open(routeData.href, '_blank');
      }
    },
    isPdfOnChange(val){
      if(val == '1'){
        //是

      }else if(val == '0'){
        //否

      }
    },
    //删除附件
    handleRemove(file, fileList) {
      console.log(file, fileList);
      this.form.accessoryUrl = null;
    },
    //预览附件
    handleOnPreview(file, fileList){
      // let routeData = this.$router.resolve({ 
      //     path: file.url
      // });
      window.open(file.url, '_blank');
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      return false;
    },
    beforeRemove(file, fileList) {
      // return this.$confirm(`确定移除 ${ file.name }？`);
      return true;
    },
    handleUploadBefore(file) {
      let fileType = file.type;
      console.log(fileType);
			if(fileType === 'application/pdf'){
				return true;
			}else {
				this.$message.error('只能上传pdf');
				return false;
			}
    },
    handleUploadSuccess(res, file) {
      // 如果上传成功
      if (res.code == 200) {
        this.form.accessoryUrl = file.name + "|" + res.url;
      } else {
        this.$message.error("文件上传失败");
      }
    },
    handleUploadError() {
      // loading动画消失
      this.$message.error("文件上传失败");
    }
  }
};
</script>