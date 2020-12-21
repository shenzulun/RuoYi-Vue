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
import com.hyrcb.hydp.modules.pbc.domain.PbcArticle;
import com.hyrcb.hydp.modules.pbc.domain.PbcLoanProduct;
import com.hyrcb.hydp.modules.pbc.service.IPbcLoanProductService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.service.ISysDeptService;

/**
 * 贷款产品Controller
 *
 * @author shenzulun
 * @date 2020-09-10
 */

@RestController
@RequestMapping("/pbc/product" )
public class PbcLoanProductController extends BaseController {

    @Autowired
    private IPbcLoanProductService iPbcLoanProductService;
    
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询贷款产品列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(PbcLoanProduct pbcLoanProduct){
        startPage();
        LambdaQueryWrapper<PbcLoanProduct> lqw = new LambdaQueryWrapper<PbcLoanProduct>();
        // 判断是否管理员
        Long deptId = SecurityUtils.getLoginUser().getUser().getDeptId();
        if(deptId > 103) {
        	// 非管理员只能查看本部门
        	lqw.eq(PbcLoanProduct::getDeptId, deptId);
        }
//        if (pbcLoanProduct.getDeptId() != null && pbcLoanProduct.getDeptId() != 0L){
//            lqw.eq(PbcLoanProduct::getDeptId ,pbcLoanProduct.getDeptId());
//        }
        if (StringUtils.isNotBlank(pbcLoanProduct.getProductType())){
            lqw.eq(PbcLoanProduct::getProductType ,pbcLoanProduct.getProductType());
        }
        if (StringUtils.isNotBlank(pbcLoanProduct.getName())){
            lqw.like(PbcLoanProduct::getName ,pbcLoanProduct.getName());
        }
        List<PbcLoanProduct> list = iPbcLoanProductService.list(lqw);
        format(list);
        return getDataTable(list);
    }
    
    void format(List<PbcLoanProduct> list) {
    	if(list != null && list.size() > 0) {
    		for(PbcLoanProduct pa : list) {
    			Long deptId = pa.getDeptId();
    			if(deptId != null && deptId != 0L) {
    				pa.setDeptName(deptService.selectDeptById(deptId).getDeptName());
    			}
    		}
    	}
    }

    /**
     * 导出贷款产品列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:product:export')" )
    @Log(title = "贷款产品" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PbcLoanProduct pbcLoanProduct) {
        LambdaQueryWrapper<PbcLoanProduct> lqw = new LambdaQueryWrapper<PbcLoanProduct>(pbcLoanProduct);
        List<PbcLoanProduct> list = iPbcLoanProductService.list(lqw);
        ExcelUtil<PbcLoanProduct> util = new ExcelUtil<PbcLoanProduct>(PbcLoanProduct. class);
        return util.exportExcel(list, "product" );
    }

    /**
     * 获取贷款产品详细信息
     */
//    @PreAuthorize("@ss.hasPermi('pbc:product:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPbcLoanProductService.getById(id));
    }

    /**
     * 新增贷款产品
     */
    @PreAuthorize("@ss.hasPermi('pbc:product:add')" )
    @Log(title = "贷款产品" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PbcLoanProduct pbcLoanProduct) {
        return toAjax(iPbcLoanProductService.addPbcLoanProduct(pbcLoanProduct) ? 1 : 0);
    }

    /**
     * 修改贷款产品
     */
    @PreAuthorize("@ss.hasPermi('pbc:product:edit')" )
    @Log(title = "贷款产品" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PbcLoanProduct pbcLoanProduct) {
        return toAjax(iPbcLoanProductService.updatePbcLoanProduct(pbcLoanProduct) ? 1 : 0);
    }

    /**
     * 删除贷款产品
     */
    @PreAuthorize("@ss.hasPermi('pbc:product:remove')" )
    @Log(title = "贷款产品" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPbcLoanProductService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
