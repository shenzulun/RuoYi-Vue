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
 * 企业融资需求对象 pbc_loan_demand
 * 
 * @author shenzulun
 * @date 2020-09-10
 */

@TableName("pbc_loan_demand")
public class PbcLoanDemand implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 需求编号 */
    @Excel(name = "需求编号")
    private String demandNo;

    /** 客户号 */
    @Excel(name = "客户号")
    private String custNo;

    /** 融资类型 */
    @Excel(name = "融资类型")
    private String loanType;

    /** 融资金额 */
    @Excel(name = "融资金额")
    private String loanAmount;

    /** 保证方式 */
    @Excel(name = "保证方式")
    private String guaranteeType;

    /** 解决状态 */
    @Excel(name = "解决状态")
    private String solveStatus;

    /** 解决银行 */
    @Excel(name = "解决银行")
    private String solveBankNo;

    /** 状态 */
    private String status;

    /** 创建人 */
    private String createUser;

    /** 创建时间 */
    @Excel(name = "创建时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新人 */
    private String updateUser;

    /** 更新时间 */
    @Excel(name = "更新时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    
    @TableField(exist=false)
    private String custName;
    
    @TableField(exist=false)
    private String bankName;
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getDemandNo() {
        return demandNo;
    }
    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCustNo() {
        return custNo;
    }
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanType() {
        return loanType;
    }
    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }
    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }
    public void setSolveStatus(String solveStatus) {
        this.solveStatus = solveStatus;
    }

    public String getSolveStatus() {
        return solveStatus;
    }
    public void setSolveBankNo(String solveBankNo) {
        this.solveBankNo = solveBankNo;
    }

    public String getSolveBankNo() {
        return solveBankNo;
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
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
    
}
