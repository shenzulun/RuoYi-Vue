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
 * 银行信息对象 pbc_bankinfo
 * 
 * @author shenzulun
 * @date 2020-09-10
 */

@TableName("pbc_bankinfo")
public class PbcBankinfo implements Serializable {

	private static final long serialVersionUID=1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 银行编号 */
    @Excel(name = "银行编号")
    private String bankId;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 银行行号 */
    @Excel(name = "银行行号")
    private String bankNo;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private Long deptId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 坐标 */
    @Excel(name = "坐标")
    private String geoCode;

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
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankId() {
        return bankId;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankNo() {
        return bankNo;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getGeoCode() {
        return geoCode;
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
