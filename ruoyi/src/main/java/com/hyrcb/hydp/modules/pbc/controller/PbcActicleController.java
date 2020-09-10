package com.hyrcb.hydp.modules.pbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Arrays;
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
import com.hyrcb.hydp.modules.pbc.domain.PbcActicle;
import com.hyrcb.hydp.modules.pbc.service.IPbcActicleService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 文章信息Controller
 *
 * @author shenzulun
 * @date 2020-09-10
 */

@RestController
@RequestMapping("/pbc/acticle" )
public class PbcActicleController extends BaseController {

    @Autowired
    private IPbcActicleService iPbcActicleService;

    /**
     * 查询文章信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:acticle:list')")
    @GetMapping("/list")
    public TableDataInfo list(PbcActicle pbcActicle){
        startPage();
        LambdaQueryWrapper<PbcActicle> lqw = new LambdaQueryWrapper<PbcActicle>();
        if (StringUtils.isNotBlank(pbcActicle.getDeptId())){
            lqw.eq(PbcActicle::getDeptId ,pbcActicle.getDeptId());
        }
        if (StringUtils.isNotBlank(pbcActicle.getArticleType())){
            lqw.eq(PbcActicle::getArticleType ,pbcActicle.getArticleType());
        }
        if (StringUtils.isNotBlank(pbcActicle.getTitle())){
            lqw.like(PbcActicle::getTitle ,pbcActicle.getTitle());
        }
        if (StringUtils.isNotBlank(pbcActicle.getContent())){
            lqw.like(PbcActicle::getContent ,pbcActicle.getContent());
        }
        List<PbcActicle> list = iPbcActicleService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出文章信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:acticle:export')" )
    @Log(title = "文章信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PbcActicle pbcActicle) {
        LambdaQueryWrapper<PbcActicle> lqw = new LambdaQueryWrapper<PbcActicle>(pbcActicle);
        List<PbcActicle> list = iPbcActicleService.list(lqw);
        ExcelUtil<PbcActicle> util = new ExcelUtil<PbcActicle>(PbcActicle. class);
        return util.exportExcel(list, "acticle" );
    }

    /**
     * 获取文章信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:acticle:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPbcActicleService.getById(id));
    }

    /**
     * 新增文章信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:acticle:add')" )
    @Log(title = "文章信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PbcActicle pbcActicle) {
        return toAjax(iPbcActicleService.addPbcActicle(pbcActicle) ? 1 : 0);
    }

    /**
     * 修改文章信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:acticle:edit')" )
    @Log(title = "文章信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PbcActicle pbcActicle) {
        return toAjax(iPbcActicleService.updatePbcActicle(pbcActicle) ? 1 : 0);
    }

    /**
     * 删除文章信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:acticle:remove')" )
    @Log(title = "文章信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPbcActicleService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
