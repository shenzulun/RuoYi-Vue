package com.hyrcb.hydp.modules.pbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
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
import com.hyrcb.hydp.modules.pbc.domain.PbcLoanProduct;
import com.hyrcb.hydp.modules.pbc.service.IPbcBankinfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.service.ISysDeptService;

/**
 * 银行信息Controller
 *
 * @author shenzulun
 * @date 2020-09-10
 */

@RestController
@RequestMapping("/pbc/bankinfo" )
public class PbcBankinfoController extends BaseController {

    @Autowired
    private IPbcBankinfoService iPbcBankinfoService;
    
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询银行信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:bankinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PbcBankinfo pbcBankinfo){
        startPage();
        LambdaQueryWrapper<PbcBankinfo> lqw = new LambdaQueryWrapper<PbcBankinfo>();
        // 判断是否管理员
        Long deptId = SecurityUtils.getLoginUser().getUser().getDeptId();
        if(deptId > 103) {
        	// 非管理员只能查看本部门
        	lqw.eq(PbcBankinfo::getDeptId, deptId);
        }
        if (StringUtils.isNotBlank(pbcBankinfo.getBankId())){
            lqw.eq(PbcBankinfo::getBankId ,pbcBankinfo.getBankId());
        }
        if (StringUtils.isNotBlank(pbcBankinfo.getBankName())){
            lqw.like(PbcBankinfo::getBankName ,pbcBankinfo.getBankName());
        }
        if (pbcBankinfo.getDeptId() != null && pbcBankinfo.getDeptId() != 0L){
            lqw.eq(PbcBankinfo::getDeptId ,pbcBankinfo.getDeptId());
        }
        if (StringUtils.isNotBlank(pbcBankinfo.getAddress())){
            lqw.like(PbcBankinfo::getAddress ,pbcBankinfo.getAddress());
        }
        List<PbcBankinfo> list = iPbcBankinfoService.list(lqw);
        format(list);
        return getDataTable(list);
    }
    
    void format(List<PbcBankinfo> list) {
    	if(list != null && list.size() > 0) {
    		for(PbcBankinfo pa : list) {
    			Long deptId = pa.getDeptId();
    			if(deptId != null && deptId != 0L) {
    				pa.setDeptName(deptService.selectDeptById(deptId).getDeptName());
    			}
    		}
    	}
    }

    /**
     * 导出银行信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:bankinfo:export')" )
    @Log(title = "银行信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PbcBankinfo pbcBankinfo) {
        LambdaQueryWrapper<PbcBankinfo> lqw = new LambdaQueryWrapper<PbcBankinfo>(pbcBankinfo);
        List<PbcBankinfo> list = iPbcBankinfoService.list(lqw);
        ExcelUtil<PbcBankinfo> util = new ExcelUtil<PbcBankinfo>(PbcBankinfo. class);
        return util.exportExcel(list, "bankinfo" );
    }

    /**
     * 获取银行信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:bankinfo:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPbcBankinfoService.getById(id));
    }

    /**
     * 新增银行信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:bankinfo:add')" )
    @Log(title = "银行信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PbcBankinfo pbcBankinfo) {
        return toAjax(iPbcBankinfoService.addPbcBankinfo(pbcBankinfo) ? 1 : 0);
    }

    /**
     * 修改银行信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:bankinfo:edit')" )
    @Log(title = "银行信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PbcBankinfo pbcBankinfo) {
        return toAjax(iPbcBankinfoService.updatePbcBankinfo(pbcBankinfo) ? 1 : 0);
    }

    /**
     * 删除银行信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:bankinfo:remove')" )
    @Log(title = "银行信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPbcBankinfoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
