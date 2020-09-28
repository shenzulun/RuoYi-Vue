<template>
    <div>
        <el-input v-model="custNoChoose" label="客户号" @focus="openCustNoChooseWindow()" clearable @clear="clearCustNoChoose()" placeholder="请选择客户..." ></el-input>

        <el-dialog :title="title" :visible.sync="open" width="70%" height="50%" append-to-body>
            <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
                <el-form-item label="客户名称" prop="custName">
                    <el-input
                      v-model="queryParams.custName"
                      placeholder="请输入客户名称"
                      clearable
                      size="small"
                      @keyup.enter.native="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="证件号码" prop="idenNo">
                    <el-input
                      v-model="queryParams.idenNo"
                      placeholder="请输入证件号码"
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

            <el-table v-loading="loading" :data="custinfoList" stripe border highlight-current-row @current-change="handleCustnoSelectionChange">
                <el-table-column label="主键" align="center" prop="id" v-if="false"/>
                <el-table-column label="客户号" align="center" prop="custNo" />
                <el-table-column label="客户名称" align="center" prop="custName" />
                <el-table-column label="证件类型" align="center" prop="idenType" :formatter="idenTypeFormat" />
                <el-table-column label="证件号码" align="center" prop="idenNo" />
                <el-table-column label="所属行业" align="center" prop="industry" />
                <el-table-column label="联系人" align="center" prop="contactPerson" />
                <el-table-column label="地址" align="center" prop="address" />
                <el-table-column label="坐标" align="center" prop="geoCode" />
                <el-table-column label="联系方式" align="center" prop="contactTel" />
                <el-table-column label="备注" align="center" prop="remark" />
                <el-table-column label="标识" align="center" prop="tag" />
                <el-table-column label="企业规模" align="center" prop="scale" :formatter="scaleFormat" />
                <el-table-column label="创建时间" align="center" prop="createTime">
                  <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="更新时间" align="center" prop="updateTime">
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
                <el-button type="primary" @click="submitCustNoChoose">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import { listCustinfo, getCustinfo } from "@/api/pbc/custinfo";
    
    export default {
        name: "custno",
        props: {
            //回显的客户号
            value: {
                type: String
            }
        },
        data() {
            return {
                custNoChoose: this.value,
                title: '查询客户信息',
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
                custinfoList: [],
                // 证件类型字典
                idenTypeOptions: [],
                // 企业规模字典
                scaleOptions: [],
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    custNo: undefined,
                    custName: undefined,
                    idenNo: undefined,
                    address: undefined,
                },
                // 表单参数
                form: {},
            };
        },
        created() {
            this.reset();
            this.getDicts("CERT_TYPE_C").then(response => {
                this.idenTypeOptions = response.data;
            });
            this.getDicts("SCALE_TYPE").then(response => {
                this.scaleOptions = response.data;
            });
        },
        methods: {
            //打开客户信息查询窗口
            openCustNoChooseWindow(){
                this.open = true
            },
            //选择客户
            submitCustNoChoose(){
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
            clearCustNoChoose(){
                this.custNoChoose = null;
                this.$emit("input", this.custNoChoose);
            },
            /** 查询企业信息列表 */
            getList() {
                this.loading = true;
                listCustinfo(this.queryParams).then(response => {
                    this.custinfoList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 证件类型字典翻译
            idenTypeFormat(row, column) {
                return this.selectDictLabel(this.idenTypeOptions, row.idenType);
            },
            // 企业规模字典翻译
            scaleFormat(row, column) {
                return this.selectDictLabel(this.scaleOptions, row.scale);
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
                    custNo: undefined,
                    custName: undefined,
                    idenType: undefined,
                    idenNo: undefined,
                    industry: undefined,
                    contactPerson: undefined,
                    address: undefined,
                    addressCode: undefined,
                    addressDetail: undefined,
                    geoCode: undefined,
                    contactTel: undefined,
                    remark: undefined,
                    tag: undefined,
                    scale: undefined,
                    status: undefined,
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
            handleCustnoSelectionChange(selection) {
                // this.ids = selection.map(item => item.id)
                // this.single = selection.length!=1
                // this.multiple = !selection.length
                this.custNoChoose = selection.custNo;
                this.$emit("input", this.custNoChoose);
                this.open = false;
            },

        }
    } 
</script>

<style lang="less">

</style>