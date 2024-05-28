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
package com.suisung.shopsuite.sys.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 页面表
 * </p>
 *
 * @author Xinze
 * @since 2021-07-03
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "页面表参数")
public class PageBaseAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("所属店铺")
    private Integer storeId;

    @ApiModelProperty("所属用户")
    private Integer userId;

    @ApiModelProperty("所属分站:0-总站")
    private Integer subsiteId;

    @ApiModelProperty("是否内置(BOOL):0-否;1-是")
    private Boolean pageBuildin;

    @ApiModelProperty("类型(ENUM):1-WAP;2-PC;3-APP")
    private Integer pageType;

    @ApiModelProperty("页面布局模板")
    private Integer pageTpl;

    @ApiModelProperty("所属APP")
    private Integer appId;

    @ApiModelProperty("页面代码")
    private String pageCode;

    private String pageNav;

    private String pageConfig;

    private String pageShareTitle;

    private String pageShareImage;

    private String pageQrcode;

    @ApiModelProperty("是否首页(BOOL):0-非首页;1-首页")
    private Boolean pageIndex;

    @ApiModelProperty("拼团首页(BOOL):0-非首页;1-首页")
    private Boolean pageGb;

    @ApiModelProperty("活动首页(BOOL):0-非首页;1-首页")
    private Boolean pageActivity;

    @ApiModelProperty("积分首页(BOOL):0-非首页;1-首页")
    private Boolean pagePoint;

    @ApiModelProperty("团购首页(BOOL):0-非首页;1-首页")
    private Boolean pageGbs;

    @ApiModelProperty("组合套餐(BOOL):0-非首页;1-首页")
    private Boolean pagePackage;

    @ApiModelProperty("批发团购首页(BOOL):0-非首页;1-首页")
    private Boolean pagePfgb;

    @ApiModelProperty("社区(BOOL):0-非首页;1-首页")
    private Boolean pageSns;

    @ApiModelProperty("资讯(BOOL):0-非首页;1-首页")
    private Boolean pageArticle;

    @ApiModelProperty("零元购区(BOOL):0-否;1-是")
    private Boolean pageZerobuy;

    @ApiModelProperty("高额返区(BOOL):0-否;1-是")
    private Boolean pageHigharea;

    @ApiModelProperty("今日爆款(BOOL):0-否;1-是")
    private Boolean pageTaday;

    @ApiModelProperty("每日好店(BOOL):0-否;1-是")
    private Boolean pageEveryday;

    @ApiModelProperty("整点秒杀(BOOL):0-否;1-是")
    private Boolean pageSecondkill;

    @ApiModelProperty("天天秒淘(BOOL):0-否;1-是")
    private Boolean pageSecondday;

    @ApiModelProperty("设置土特产(BOOL):0-否;1-是")
    private Boolean pageRura;

    @ApiModelProperty("用户页banner(BOOL):0-否;1-是")
    private Boolean pageLikeyou;

    @ApiModelProperty("兑换专区(BOOL):0-否;1-是")
    private Boolean pageExchange;

    @ApiModelProperty("新品首发(BOOL):0-否;1-是")
    private Boolean pageNew;

    @ApiModelProperty("新人优惠(BOOL):0-否;1-是")
    private Boolean pageNewperson;

    @ApiModelProperty("升级VIP(BOOL):0-否;1-是")
    private Boolean pageUpgrade;

    @ApiModelProperty("信息发布(BOOL):0-否;1-是")
    private Boolean pageMessage;

    @ApiModelProperty("是否发布(BOOL):0-否;1-是")
    private Boolean pageRelease;


}
