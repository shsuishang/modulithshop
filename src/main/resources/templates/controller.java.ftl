// +----------------------------------------------------------------------
// | ShopSuite商城系统 [ 赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | 版权所有 随商信息技术（上海）有限公司
// +----------------------------------------------------------------------
// | 未获商业授权前，不得将本软件用于商业用途。禁止整体或任何部分基础上以发展任何派生版本、
// | 修改版本或第三方版本用于重新分发。
// +----------------------------------------------------------------------
// | 官方网站: https://www.shopsuite.cn  https://www.modulithshop.cn
// +----------------------------------------------------------------------
// | 版权和免责声明:
// | 本公司对该软件产品拥有知识产权（包括但不限于商标权、专利权、著作权、商业秘密等）
// | 均受到相关法律法规的保护，任何个人、组织和单位不得在未经本团队书面授权的情况下对所授权
// | 软件框架产品本身申请相关的知识产权，禁止用于任何违法、侵害他人合法权益等恶意的行为，禁
// | 止用于任何违反我国法律法规的一切项目研发，任何个人、组织和单位用于项目研发而产生的任何
// | 意外、疏忽、合约毁坏、诽谤、版权或知识产权侵犯及其造成的损失 (包括但不限于直接、间接、
// | 附带或衍生的损失等)，本团队不承担任何法律责任，本软件框架只能用于公司和个人内部的
// | 法律所允许的合法合规的软件产品研发，详细见https://www.modulithshop.cn/policy
// +----------------------------------------------------------------------
package ${package.Controller};

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${package.Other}.req.${entity}AddReq;
import ${package.Other}.req.${entity}EditReq;
import ${package.Other}.req.${entity}ListReq;
import com.suisung.shopsuite.${package.ModuleName}.service.${entity}Service;
import com.suisung.shopsuite.common.utils.I18nUtil;
import com.suisung.shopsuite.core.web.BaseQueryWrapper;
import com.suisung.shopsuite.core.web.CommonRes;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
        <#assign keyAnnotationColumnName="${field.annotationColumnName}"/>
        <#assign keyPropertyType="${field.propertyType}"/>
    </#if>
</#list>
/**
 * <p>
 * ${table.comment} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>
    @Autowired
    private ${entity}Service ${entity?uncap_first}Service;

    @PreAuthorize("hasAuthority('/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}/list')")
    @ApiOperation(value = "${table.comment}-分页列表查询", notes = "${table.comment}-分页列表查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes <BaseListRes<${entity}>> list(${entity}ListReq ${entity?uncap_first}ListReq) {
        IPage<${entity}> pageList = ${entity?uncap_first}Service.lists(${entity?uncap_first}ListReq);

        return success(pageList);
    }

    @PreAuthorize("hasAuthority('/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}/detail')")
    @ApiOperation(value = "${table.comment}-通过${keyAnnotationColumnName}查询", notes = "${table.comment}-通过${keyAnnotationColumnName}查询")
    @RequestMapping(value = "/{${keyPropertyName}}", method = RequestMethod.GET)
    public CommonRes<${entity}> get(@PathVariable ${keyPropertyType} ${keyPropertyName}) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Service.get(${keyPropertyName});

        return success(${entity?uncap_first});
    }

    @PreAuthorize("hasAuthority('/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}/add')")
    @ApiOperation(value = "${table.comment}-添加", notes = "${table.comment}-添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonRes<?> add(${entity}AddReq ${entity?uncap_first}AddReq) {
        ${entity} ${entity?uncap_first} = BeanUtil.copyProperties(${entity?uncap_first}AddReq, ${entity}.class);
        boolean success = ${entity?uncap_first}Service.add(${entity?uncap_first});

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}/edit')")
    @ApiOperation(value = "${table.comment}-编辑", notes = "${table.comment}-编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonRes<?> edit(${entity}EditReq ${entity?uncap_first}EditReq) {
        ${entity} ${entity?uncap_first} = BeanUtil.copyProperties(${entity?uncap_first}EditReq, ${entity}.class);
        boolean success = ${entity?uncap_first}Service.edit(${entity?uncap_first});

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}/remove')")
    @ApiOperation(value = "${table.comment}-通过${keyAnnotationColumnName}删除", notes = "${table.comment}-通过${keyAnnotationColumnName}删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public CommonRes<?> remove(@RequestParam("${keyAnnotationColumnName}") ${keyPropertyType} ${keyPropertyName}) {
        boolean success = ${entity?uncap_first}Service.remove(${keyPropertyName});

        if (success) {
            return success();
        }

        return fail();
    }

    @PreAuthorize("hasAuthority('/manage<#if package.ModuleName??>/${package.ModuleName}</#if>/${serviceModule}/removeBatch')")
    @ApiOperation(value = "${table.comment}-批量删除", notes = "${table.comment}-批量删除")
    @RequestMapping(value = "/removeBatch", method = RequestMethod.POST)
    public CommonRes<?> removeBatch(@RequestParam("${keyAnnotationColumnName}") String ${keyPropertyName}s) {
        boolean success = ${entity?uncap_first}Service.remove(Convert.toList(${keyPropertyType}.class, ${keyPropertyName}s));

        if (success) {
            return success();
        }

        return fail();
    }
</#if>
}

