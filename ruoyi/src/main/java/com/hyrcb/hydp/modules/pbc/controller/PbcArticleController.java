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
import com.ruoyi.framework.aspectj.lang.annotation.DataAuth;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.hyrcb.hydp.modules.pbc.domain.PbcArticle;
import com.hyrcb.hydp.modules.pbc.service.IPbcArticleService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.service.ISysDeptService;

/**
 * 文章信息Controller
 *
 * @author shenzulun
 * @date 2020-09-11
 */

@RestController
@RequestMapping("/pbc/article" )
public class PbcArticleController extends BaseController {

    @Autowired
    private IPbcArticleService iPbcArticleService;
    
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询文章信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:article:list')")
    @DataAuth(method="dept_id=?")
    @GetMapping("/list")
    public TableDataInfo list(PbcArticle pbcArticle){
        startPage();
        LambdaQueryWrapper<PbcArticle> lqw = new LambdaQueryWrapper<PbcArticle>();
        if (pbcArticle.getDeptId() != null && pbcArticle.getDeptId() != 0L){
            lqw.eq(PbcArticle::getDeptId ,pbcArticle.getDeptId());
        }
        if (StringUtils.isNotBlank(pbcArticle.getArticleType())){
            lqw.eq(PbcArticle::getArticleType ,pbcArticle.getArticleType());
        }
        if (StringUtils.isNotBlank(pbcArticle.getTitle())){
            lqw.like(PbcArticle::getTitle ,pbcArticle.getTitle());
        }
        if (StringUtils.isNotBlank(pbcArticle.getContent())){
            lqw.like(PbcArticle::getContent ,pbcArticle.getContent());
        }
        List<PbcArticle> list = iPbcArticleService.list(lqw);
        format(list);
        return getDataTable(list);
    }
    
    void format(List<PbcArticle> list) {
    	if(list != null && list.size() > 0) {
    		for(PbcArticle pa : list) {
    			Long deptId = pa.getDeptId();
    			pa.setDeptName(deptService.selectDeptById(deptId).getDeptName());
    		}
    	}
    }

    /**
     * 导出文章信息列表
     */
    @PreAuthorize("@ss.hasPermi('pbc:article:export')" )
    @Log(title = "文章信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(PbcArticle pbcArticle) {
        LambdaQueryWrapper<PbcArticle> lqw = new LambdaQueryWrapper<PbcArticle>(pbcArticle);
        List<PbcArticle> list = iPbcArticleService.list(lqw);
        ExcelUtil<PbcArticle> util = new ExcelUtil<PbcArticle>(PbcArticle. class);
        return util.exportExcel(list, "article" );
    }

    /**
     * 获取文章信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:article:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iPbcArticleService.getById(id));
    }

    /**
     * 新增文章信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:article:add')" )
    @Log(title = "文章信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PbcArticle pbcArticle) {
        return toAjax(iPbcArticleService.addPbcArticle(pbcArticle) ? 1 : 0);
    }

    /**
     * 修改文章信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:article:edit')" )
    @Log(title = "文章信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PbcArticle pbcArticle) {
        return toAjax(iPbcArticleService.updatePbcArticle(pbcArticle) ? 1 : 0);
    }

    /**
     * 删除文章信息
     */
    @PreAuthorize("@ss.hasPermi('pbc:article:remove')" )
    @Log(title = "文章信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iPbcArticleService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
