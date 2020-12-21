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
import com.hyrcb.hydp.modules.pbc.domain.PbcCustinfo;
import com.hyrcb.hydp.modules.pbc.service.IPbcCustinfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 企业信息Controller
 *
 * @author shenzulun
 * @date 2020-09-10
 */

@RestController
@RequestMapping("/pbc/custinfo" )
public class PbcCustinfoController extends BaseController {

    @Autowired
    private IPbcCustinfoService iPbcCustinfoService;

    /**
     * 查询企业信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:custinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PbcCustinfo pbcCustinfo){
        startPage();
        LambdaQueryWrapper<PbcCustinfo> lqw = new LambdaQueryWrapper<PbcCustinfo>();
        // 判断是否管理员
        Long deptId = SecurityUtils.getLoginUser().getUser().getDeptId();
        if(deptId > 103) {
        	// 非管理员只能查看本部门
        	lqw.eq(PbcCustinfo::getCreateUser, SecurityUtils.getUsername());
        }
        if (StringUtils.isNotBlank(pbcCustinfo.getCustNo())){
            lqw.eq(PbcCustinfo::getCustNo ,pbcCustinfo.getCustNo());
        }
        if (StringUtils.isNotBlank(pbcCustinfo.getCustName())){
            lqw.like(PbcCustinfo::getCustName ,pbcCustinfo.getCustName());
        }
        if (StringUtils.isNotBlank(pbcCustinfo.getIdenNo())){
            lqw.eq(PbcCustinfo::getIdenNo ,pbcCustinfo.getIdenNo());
        }
        if (StringUtils.isNotBlank(pbcCustinfo.getAddress())){
            lqw.like(PbcCustinfo::getAddress ,pbcCustinfo.getAddress());
        }
        if (StringUtils.isNotBlank(pbcCustinfo.getTag())){
            lqw.like(PbcCustinfo::getTag ,pbcCustinfo.getTag());
        }
        List<PbcCustinfo> list = iPbcCustinfoService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出企业信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:custinfo:export')" )
    @Log(title = "企业信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PbcCustinfo pbcCustinfo) {
        LambdaQueryWrapper<PbcCustinfo> lqw = new LambdaQueryWrapper<PbcCustinfo>(pbcCustinfo);
        List<PbcCustinfo> list = iPbcCustinfoService.list(lqw);
        ExcelUtil<PbcCustinfo> util = new ExcelUtil<PbcCustinfo>(PbcCustinfo. class);
        return util.exportExcel(list, "custinfo" );
    }

    /**
     * 获取企业信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:custinfo:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPbcCustinfoService.getById(id));
    }

    /**
     * 新增企业信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:custinfo:add')" )
    @Log(title = "企业信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PbcCustinfo pbcCustinfo) {
        return toAjax(iPbcCustinfoService.addPbcCustinfo(pbcCustinfo) ? 1 : 0);
    }

    /**
     * 修改企业信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:custinfo:edit')" )
    @Log(title = "企业信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PbcCustinfo pbcCustinfo) {
        return toAjax(iPbcCustinfoService.updateCustinfo(pbcCustinfo) ? 1 : 0);
    }

    /**
     * 删除企业信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:custinfo:remove')" )
    @Log(title = "企业信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPbcCustinfoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
