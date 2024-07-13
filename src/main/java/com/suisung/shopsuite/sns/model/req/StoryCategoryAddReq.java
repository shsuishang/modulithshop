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
package com.suisung.shopsuite.sns.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 版块
 * </p>
 *
 * @author Xinze
 * @since 2018-07-04
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "版块参数")
public class StoryCategoryAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("版块名称")
    private String storyCategoryName;

    @ApiModelProperty("所属父类")
    private Integer storyCategoryParentId;

    @ApiModelProperty("版块图标")
    private String storyCategoryLogo;

    @ApiModelProperty("版块背景")
    private String storyCategoryBg;

    @ApiModelProperty("版块关键词")
    private String storyCategoryKeywords;

    @ApiModelProperty("版块描述")
    private String storyCategoryDesc;

    @ApiModelProperty("版块内容数量")
    private Integer storyCategoryCount;

    @ApiModelProperty("版块模板")
    private String storyCategoryTemplate;

    @ApiModelProperty("版块别名")
    private String storyCategoryAlias;

    @ApiModelProperty("版块排序")
    private Integer storyCategoryOrder;

    @ApiModelProperty("系统内置(ENUM):0-非内置;1-内置;")
    private Boolean storyCategoryBuildin;

    @ApiModelProperty("板块链接")
    private String storyCategoryLink;

    @ApiModelProperty("打开方式:1-弹出打开; 0-窗内打开。")
    private Boolean storyOpenMode;



}
