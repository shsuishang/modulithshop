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
package com.suisung.shopsuite.admin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("admin_menu_base")
@ApiModel(value = "Menu对象", description = "系统菜单表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MenuBase implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单编号")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    @ApiModelProperty("菜单父编号")
    @TableField("menu_parent_id")
    private Integer menuParentId;

    @ApiModelProperty("菜单名称")
    @TableField("menu_title")
    private String menuTitle;

    @ApiModelProperty("页面网址")
    @TableField("menu_url")
    private String menuUrl;

    @ApiModelProperty("组件名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty("组件路由")
    @TableField("menu_path")
    private String menuPath;

    @ApiModelProperty("组件路径")
    @TableField("menu_component")
    private String menuComponent;

    /*
    @ApiModelProperty("重定向")
    @TableField("menu_redirect")
    private String menuRedirect;
     */

    @ApiModelProperty("允许关闭(BOOL):0-禁止;1-允许")
    @TableField("menu_close")
    private Boolean menuClose;

    @ApiModelProperty("是否隐藏(BOOL):0-展示;1-隐藏")
    @TableField("menu_hidden")
    private Boolean menuHidden;

    @ApiModelProperty("是否启用(BOOL):0-禁用;1-启用")
    @TableField("menu_enable")
    private Boolean menuEnable;

    @ApiModelProperty("样式class")
    @TableField("menu_class")
    private String menuClass;

    @ApiModelProperty("图标设置")
    @TableField("menu_icon")
    private String menuIcon;

    @ApiModelProperty("是否红点(BOOL):0-隐藏;1-显示")
    @TableField("menu_dot")
    private Boolean menuDot;

    @ApiModelProperty("菜单标签")
    @TableField("menu_bubble")
    private String menuBubble;

    @ApiModelProperty("菜单排序")
    @TableField("menu_sort")
    private Integer menuSort;

    @ApiModelProperty("菜单类型(LIST):0-按钮;1-菜单")
    @TableField("menu_type")
    private Integer menuType;

    @ApiModelProperty("备注")
    @TableField("menu_note")
    private String menuNote;

    @ApiModelProperty("功能开启:设置config_key")
    @TableField("menu_func")
    private String menuFunc;

    @ApiModelProperty("角色类型(LIST):1-平台;2-商户;3-门店")
    @TableField("menu_role")
    private Integer menuRole;

    @ApiModelProperty("url参数")
    @TableField("menu_param")
    private String menuParam;

    @ApiModelProperty("权限标识:后端地址")
    @TableField("menu_permission")
    private String menuPermission;

    @ApiModelProperty("系统内置(BOOL):1-是; 0-否")
    @TableField("menu_buildin")
    private Boolean menuBuildin;

    @ApiModelProperty("最后更新时间")
    @TableField("menu_time")
    private Date menuTime;

    @ApiModelProperty("权限标识")
    @TableField("menu_permission")
    private String authority;

}
