package com.hyrcb.hydp.modules.pbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.hyrcb.hydp.modules.pbc.domain.PbcBankinfo;
import com.hyrcb.hydp.modules.pbc.domain.PbcCustinfo;
import com.hyrcb.hydp.modules.pbc.domain.PbcLoanDemand;
import com.hyrcb.hydp.modules.pbc.service.IPbcBankinfoService;
import com.hyrcb.hydp.modules.pbc.service.IPbcCustinfoService;
import com.hyrcb.hydp.modules.pbc.service.IPbcLoanDemandService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysUser;

/**
 * 企业融资需求Controller
 *
 * @author shenzulun
 * @date 2020-09-10
 */

@RestController
@RequestMapping("/pbc/demand" )
public class PbcLoanDemandController extends BaseController {

    @Autowired
    private IPbcLoanDemandService iPbcLoanDemandService;
    
    @Autowired
    private IPbcCustinfoService iPbcCustinfoService;
    
    @Autowired
    private IPbcBankinfoService iPbcBankinfoService;

    /**
     * 查询企业融资需求列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:list')")
    @GetMapping("/list")
    public TableDataInfo list(PbcLoanDemand pbcLoanDemand){
        startPage();
        LambdaQueryWrapper<PbcLoanDemand> lqw = new LambdaQueryWrapper<PbcLoanDemand>();
        // 判断是否管理员
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        Long deptId = sysUser.getDeptId();
        if(deptId > 103) {
        	// 非管理员只能查看本部门
        	// 判断当前登录角色
        	String userName = SecurityUtils.getUsername();
        	Long parentDeptId = sysUser.getDept().getParentId();
        	if(parentDeptId == 203) {
        		// 银行
        		// 查询行号
        		List<Record> bankList = Db.find("SELECT bank_no FROM pbc_bankinfo t1 where dept_id=?", deptId);
        		if(bankList == null || bankList.size() == 0) {
        			// 当前机构下无银行
        			lqw.and(i -> i.isNull(PbcLoanDemand::getSolveBankNo)
        					.or().eq(PbcLoanDemand::getCreateUser, userName)
        					.or().eq(PbcLoanDemand::getUpdateUser, userName));
        		}else {
        			List<String> bankList1 = new ArrayList<>();
        			for(Record r : bankList) {
        				bankList1.add(r.getStr("bank_no"));
        			}
        			lqw.and(i -> i.isNull(PbcLoanDemand::getSolveBankNo)
        					.or().in(PbcLoanDemand::getSolveBankNo, bankList)
        					.or().eq(PbcLoanDemand::getCreateUser, userName)
        					.or().eq(PbcLoanDemand::getUpdateUser, userName));
        		}
        	}else if(parentDeptId == 204) {
        		// 企业
        		// 查询客户号
        		Record r = Db.findFirst("select cust_no from pbc_custinfo t1 where cust_name=?", sysUser.getNickName());
        		if(r == null) {
        			lqw.and(i -> i.eq(PbcLoanDemand::getCreateUser, userName)
        					.or().eq(PbcLoanDemand::getUpdateUser, userName));
        		}else {
        			lqw.and(i -> i.eq(PbcLoanDemand::getCustNo, r.getStr("cust_no"))
        					.or().eq(PbcLoanDemand::getCreateUser, userName)
        					.or().eq(PbcLoanDemand::getUpdateUser, userName));
        		}
        	}
        }
        if (StringUtils.isNotBlank(pbcLoanDemand.getDemandNo())){
            lqw.eq(PbcLoanDemand::getDemandNo ,pbcLoanDemand.getDemandNo());
        }
        if (StringUtils.isNotBlank(pbcLoanDemand.getCustNo())){
            lqw.eq(PbcLoanDemand::getCustNo ,pbcLoanDemand.getCustNo());
        }
        if (StringUtils.isNotBlank(pbcLoanDemand.getSolveStatus())){
            lqw.eq(PbcLoanDemand::getSolveStatus ,pbcLoanDemand.getSolveStatus());
        }
        if (StringUtils.isNotBlank(pbcLoanDemand.getSolveBankNo())){
            lqw.eq(PbcLoanDemand::getSolveBankNo ,pbcLoanDemand.getSolveBankNo());
        }
        if (StringUtils.isNotBlank(pbcLoanDemand.getStatus())){
            lqw.eq(PbcLoanDemand::getStatus ,pbcLoanDemand.getStatus());
        }
        List<PbcLoanDemand> list = iPbcLoanDemandService.list(lqw);
        for(PbcLoanDemand pbc : list) {
        	if(StringUtils.isNotNull(pbc.getCustNo())) {
        		PbcCustinfo pbcCustInfo = new PbcCustinfo();
        		pbcCustInfo.setCustNo(pbc.getCustNo());
        		
        		pbcCustInfo = iPbcCustinfoService.getOne(Wrappers.lambdaQuery(pbcCustInfo));
        		if(pbcCustInfo != null) {
        			pbc.setCustName(pbcCustInfo.getCustName());
        		}
        	}
        	
        	if(StringUtils.isNotNull(pbc.getSolveBankNo())) {
        		PbcBankinfo pbcBankinfo = new PbcBankinfo();
        		pbcBankinfo.setBankId(pbc.getSolveBankNo());
        		
        		pbcBankinfo = iPbcBankinfoService.getOne(Wrappers.lambdaQuery(pbcBankinfo));
        		if(pbcBankinfo != null) {
        			pbc.setBankName(pbcBankinfo.getBankName());
        		}
        	}
        }
        return getDataTable(list);
    }

    /**
     * 导出企业融资需求列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:export')" )
    @Log(title = "企业融资需求" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PbcLoanDemand pbcLoanDemand) {
        LambdaQueryWrapper<PbcLoanDemand> lqw = new LambdaQueryWrapper<PbcLoanDemand>(pbcLoanDemand);
        List<PbcLoanDemand> list = iPbcLoanDemandService.list(lqw);
        ExcelUtil<PbcLoanDemand> util = new ExcelUtil<PbcLoanDemand>(PbcLoanDemand. class);
        return util.exportExcel(list, "demand" );
    }

    /**
     * 获取企业融资需求详细信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPbcLoanDemandService.getById(id));
    }

    /**
     * 新增企业融资需求
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:add')" )
    @Log(title = "企业融资需求" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PbcLoanDemand pbcLoanDemand) {
        return toAjax(iPbcLoanDemandService.addPbcLoanDemand(pbcLoanDemand) ? 1 : 0);
    }

    /**
     * 修改企业融资需求
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:edit')" )
    @Log(title = "企业融资需求" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PbcLoanDemand pbcLoanDemand) {
        return toAjax(iPbcLoanDemandService.updatePbcLoanDemand(pbcLoanDemand) ? 1 : 0);
    }

    /**
     * 删除企业融资需求
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:remove')" )
    @Log(title = "企业融资需求" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPbcLoanDemandService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
    
    /**
     * 请求协助
     */
    @PreAuthorize("@ss.hasPermi('pbc:demand:edit')" )
    @Log(title = "企业融资需求" , businessType = BusinessType.UPDATE)
    @PostMapping(value = "/help/{id}" )
    public AjaxResult needHelp(@PathVariable("id" ) Long id) {
    	if(id != null) {
    		Db.update("update pbc_loan_demand set status='2' where id=?", id);
    	}
        return AjaxResult.success();
    }
}
