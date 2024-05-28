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
package com.suisung.shopsuite.sys.model.entity;

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

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 页面模块表
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_page_module")
@ApiModel(value = "PageModule对象", description = "页面模块表")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageModule implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "pm_id", type = IdType.AUTO)
    private Integer pmId;

    @ApiModelProperty("模块名称")
    @TableField("pm_name")
    private String pmName;

    @TableField("page_id")
    private Long pageId;

    @ApiModelProperty("所属用户")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("颜色")
    @TableField("pm_color")
    private String pmColor;

    @ApiModelProperty("所在页面")
    @TableField("pm_type")
    private String pmType;

    @ApiModelProperty("模版")
    @TableField("module_id")
    private String moduleId;

    @ApiModelProperty("更新时间")
    @TableField("pm_utime")
    private Date pmUtime;

    @ApiModelProperty("排序")
    @TableField("pm_order")
    private Integer pmOrder;

    @ApiModelProperty("是否显示")
    @TableField("pm_enable")
    private Boolean pmEnable;

    @ApiModelProperty("模块html代码")
    @TableField("pm_html")
    private String pmHtml;

    @ApiModelProperty("模块JSON代码(JSON)")
    @TableField("pm_json")
    private String pmJson;

    @ApiModelProperty("所属分站Id:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("column_left:content_top")
    @TableField("pm_position")
    private String pmPosition;
}
