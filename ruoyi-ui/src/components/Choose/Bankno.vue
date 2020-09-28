<template>
    <div>
        <el-input v-model="bankNoChoose" label="银行编号" @focus="openbankNoChooseWindow()" clearable @clear="clearbankNoChoose()" placeholder="请选择银行..." ></el-input>

        <el-dialog :title="title" :visible.sync="open" width="70%" height="50%" append-to-body>
            <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
                <el-form-item label="银行名称" prop="bankName">
                  <el-input
                    v-model="queryParams.bankName"
                    placeholder="请输入银行名称"
                    clearable
                    size="small"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>              
                <el-form-item label="地址" prop="address">
                  <el-input
                    v-model="queryParams.address"
                    placeholder="请输入地址"
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

              <el-table v-loading="loading" :data="bankinfoList" stripe border highlight-current-row @current-change="handleBanknoSelectionChange">
                <el-table-column label="主键" align="center" prop="id" v-if="false"/>
                <el-table-column label="银行编号" align="center" prop="bankId" />
                <el-table-column label="银行名称" align="center" prop="bankName" />
                <el-table-column label="银行行号" align="center" prop="bankNo" />
                <el-table-column label="所属机构" align="center" prop="deptId" />
                <el-table-column label="地址" align="center" prop="address" />
                <el-table-column label="坐标" align="center" prop="geoCode" />
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
              </el-table>

              <pagination
                v-show="total>0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
                />

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitbankNoChoose">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { listBankinfo, getBankinfo } from "@/api/pbc/bankinfo";
    
    export default {
        name: "bankNo",
        props: {
            //回显的银行编号
            value: {
                type: String
            }
        },
        data() {
            return {
                bankNoChoose: this.value,
                title: '查询银行信息',
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
                // 企业信息表格数据
                bankinfoList: [],
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    bankId: undefined,
                    bankName: undefined,
                    deptId: undefined,
                    address: undefined,
                },
                // 表单参数
                form: {},
            };
        },
        created() {
            this.reset();
        },
        methods: {
            //打开客户信息查询窗口
            openbankNoChooseWindow(){
                this.open = true
            },
            //选择客户
            submitbankNoChoose(){
                // if(this.ids == null || this.ids.length == 0){
                //     this.$message.error("请选择客户...");
                //     return;
                // }
                // if(multiple){
                //     this.$message.error("只能选择单个客户...");
                //     return;
                // }
                // if(single){
                //     this.open = false;
                // }
                this.open = false;
            },
            clearbankNoChoose(){
                this.bankNoChoose = null;
                this.$emit("input", this.bankNoChoose);
            },
            /** 查询银行信息列表 */
            getList() {
                this.loading = true;
                listBankinfo(this.queryParams).then(response => {
                    this.bankinfoList = response.rows;
                    this.total = response.total;
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
                    bankId: undefined,
                    bankName: undefined,
                    bankNo: undefined,
                    deptId: undefined,
                    address: undefined,
                    geoCode: undefined,
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
            handleBanknoSelectionChange(selection) {
                this.bankNoChoose = selection.bankId;
                this.$emit("input", this.bankNoChoose);
                this.open = false;
            }
        }
    } 
</script>

<style lang="less">

</style>