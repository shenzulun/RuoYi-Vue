package com.hyrcb.hydp.modules.pbc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;


/**
 * 贷款产品对象 pbc_loan_product
 * 
 * @author shenzulun
 * @date 2020-09-10
 */

@TableName("pbc_loan_product")
public class PbcLoanProduct implements Serializable {

	private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private Long deptId;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private String productType;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String name;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 发布状态 */
    @Excel(name = "发布状态")
    private String productStatus;

    /** 状态 */
    private String status;

    /** 创建者 */
    private String createUser;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateUser;

    /** 更新时间 */
    @Excel(name = "更新时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    /**
     * 所属机构名称
     */
    @TableField(exist=false)
    private String deptName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatus() {
        return productStatus;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUser() {
        return createUser;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    
}
