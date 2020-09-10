package com.hyrcb.hydp.modules.pbc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;
import java.util.Date;


/**
 * 企业信息对象 pbc_custinfo
 * 
 * @author shenzulun
 * @date 2020-09-10
 */

@TableName("pbc_custinfo")
public class PbcCustinfo implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 客户号 */
    @Excel(name = "客户号")
    private String custNo;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String custName;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String idenType;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String idenNo;

    /** 所属行业 */
    @Excel(name = "所属行业")
    private String industry;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactPerson;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 地址代码 */
    private String addressCode;

    /** 街道地址 */
    private String addressDetail;

    /** 坐标 */
    @Excel(name = "坐标")
    private String geoCode;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactTel;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 标识 */
    @Excel(name = "标识")
    private String tag;

    /** 企业规模 */
    @Excel(name = "企业规模")
    private String scale;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCustNo() {
        return custNo;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }
    public void setIdenType(String idenType) {
        this.idenType = idenType;
    }

    public String getIdenType() {
        return idenType;
    }
    public void setIdenNo(String idenNo) {
        this.idenNo = idenNo;
    }

    public String getIdenNo() {
        return idenNo;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return industry;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressCode() {
        return addressCode;
    }
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressDetail() {
        return addressDetail;
    }
    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getGeoCode() {
        return geoCode;
    }
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactTel() {
        return contactTel;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getScale() {
        return scale;
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
}
