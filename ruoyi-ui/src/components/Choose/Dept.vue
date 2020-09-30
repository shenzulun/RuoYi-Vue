<template>
        <div>
            <el-input v-model="deptChoose" label="机构编号" @focus="openDeptChooseWindow()" clearable @clear="clearDeptChoose()" placeholder="请选择机构..." ></el-input>
    
            <el-dialog :title="title" :visible.sync="open" width="70%" height="50%" append-to-body>
                <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
                    <el-form-item label="机构名称" prop="deptName">
                      <el-input
                        v-model="queryParams.deptName"
                        placeholder="请输入机构名称"
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
    
                  <el-table v-loading="loading" :data="deptInfoList" stripe border highlight-current-row @current-change="handleDeptSelectionChange">
                    <el-table-column label="主键" align="center" prop="id" v-if="false"/>
                    <el-table-column label="机构编号" align="center" prop="deptId" />
                    <el-table-column label="机构名称" align="center" prop="deptName" />
                  </el-table>
    
                  <pagination
                    v-show="total>0"
                    :total="total"
                    :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize"
                    @pagination="getList"
                    />
    
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="submitDeptChoose">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                </div>
            </el-dialog>
        </div>
    </template>
    
    <script>
        import { listDept } from "@/api/system/dept";
        
        export default {
            name: "dept",
            props: {
                //回显的机构编号
                value: {
                    type: Number
                }
            },
            data() {
                return {
                    deptChoose: this.value,
                    title: '查询机构信息',
                    open: false,
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
                    // 机构信息表格数据
                    deptInfoList: [],
                    // 查询参数
                    queryParams: {
                        pageNum: 1,
                        pageSize: 10,
                        deptName: undefined
                    },
                    // 表单参数
                    form: {},
                };
            },
            created() {
                this.reset();
                // this.$emit("input", this.deptChoose);
            },
            watch: {
                value: function () {
                    this.deptChoose = this.value;
                }
            },
            methods: {
                //打开客户信息查询窗口
                openDeptChooseWindow(){
                    this.open = true
                },
                //选择客户
                submitDeptChoose(){
                    this.open = false;
                },
                clearDeptChoose(){
                    this.deptChoose = null;
                    this.$emit("input", this.deptChoose);
                },
                /** 查询机构信息列表 */
                getList() {
                    this.loading = true;
                    listDept(this.queryParams).then(response => {
                        this.deptInfoList = response.data;
                        this.total = response.data.length;
                        this.loading = false;
                    });
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
                        deptName: undefined
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
                handleDeptSelectionChange(selection) {
                    this.deptChoose = selection.deptId;
                    this.$emit("input", this.deptChoose);
                    this.open = false;
                }
            }
        } 
    </script>
    
    <style lang="less">
    
    </style>