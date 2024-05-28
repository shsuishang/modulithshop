package com.suisung.shopsuite.sys.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "手机装修")
public class PageMobileVo {

    @ApiModelProperty("页面编号")
    @TableId(value = "page_id", type = IdType.AUTO)
    @JsonProperty("Id")
    private Long pageId;

    @ApiModelProperty("页面名称")
    @TableField("page_name")
    @JsonProperty("PageTitle")
    private String pageName;

    @ApiModelProperty("所属店铺")
    @TableField("store_id")
    @JsonProperty("StoreId")
    private Integer storeId;

    @ApiModelProperty("所属用户")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("所属分站:0-总站")
    @TableField("subsite_id")
    private Integer subsiteId;

    @ApiModelProperty("是否内置(BOOL):0-否;1-是")
    @TableField("page_buildin")
    private Boolean pageBuildin;

    @ApiModelProperty("类型(ENUM):1-WAP;2-PC;3-APP")
    @TableField("page_type")
    private Integer pageType = 3;

    @ApiModelProperty("页面布局模板")
    @TableField("page_tpl")
    private Integer pageTpl = 0;

    @ApiModelProperty("所属APP")
    @TableField("app_id")
    @JsonProperty("AppId")
    private Integer appId;

    @ApiModelProperty("页面代码")
    @TableField("page_code")
    @JsonProperty("PageCode")
    private String pageCode;

    @TableField("page_nav")
    @JsonProperty("PageNav")
    private String pageNav;

    @TableField("page_config")
    @JsonProperty("PageConfig")
    private String pageConfig;

    @TableField("page_share_title")
    @JsonProperty("ShareTitle")
    private String pageShareTitle;

    @TableField("page_share_image")
    @JsonProperty("ShareImg")
    private String pageShareImage;

    @TableField("page_qrcode")
    @JsonProperty("PageQRCode")
    private String pageQrcode;

    @ApiModelProperty("是否首页(BOOL):0-非首页;1-首页")
    @JsonProperty("IsHome")
    @TableField("page_index")
    private Boolean pageIndex;

    @ApiModelProperty("是否用户中心(BOOL):0-非用户中心;1-用户中心")
    @JsonProperty("IsPersonalCenter")
    @TableField(exist = false)
    private Boolean isUserCenter;

    @ApiModelProperty("拼团首页(BOOL):0-非首页;1-首页")
    @JsonProperty("IsGb")
    @TableField("page_gb")
    private Boolean pageGb;

    @ApiModelProperty("活动首页(BOOL):0-非首页;1-首页")
    @JsonProperty("IsActivity")
    @TableField("page_activity")
    private Boolean pageActivity;

    @ApiModelProperty("积分首页(BOOL):0-非首页;1-首页")
    @JsonProperty("IsPoint")
    @TableField("page_point")
    private Boolean pagePoint;

    @ApiModelProperty("团购首页(BOOL):0-非首页;1-首页")
    @TableField("page_gbs")
    private Boolean pageGbs;

    @ApiModelProperty("组合套餐(BOOL):0-非首页;1-首页")
    @TableField("page_package")
    private Boolean pagePackage;

    @ApiModelProperty("批发团购首页(BOOL):0-非首页;1-首页")
    @TableField("page_pfgb")
    private Boolean pagePfgb;

    @ApiModelProperty("社区(BOOL):0-非首页;1-首页")
    @TableField("page_sns")
    @JsonProperty("IsSns")
    private Boolean pageSns;

    @ApiModelProperty("资讯(BOOL):0-非首页;1-首页")
    @TableField("page_article")
    @JsonProperty("IsArticle")
    private Boolean pageArticle;

    @ApiModelProperty("零元购区(BOOL):0-否;1-是")
    @TableField("page_zerobuy")
    private Boolean pageZerobuy;

    @ApiModelProperty("高额返区(BOOL):0-否;1-是")
    @TableField("page_higharea")
    private Boolean pageHigharea;

    @ApiModelProperty("今日爆款(BOOL):0-否;1-是")
    @TableField("page_taday")
    private Boolean pageTaday;

    @ApiModelProperty("每日好店(BOOL):0-否;1-是")
    @TableField("page_everyday")
    private Boolean pageEveryday;

    @ApiModelProperty("整点秒杀(BOOL):0-否;1-是")
    @TableField("page_secondkill")
    @JsonProperty("SecondKill")
    private Boolean pageSecondkill;

    @ApiModelProperty("天天秒淘(BOOL):0-否;1-是")
    @TableField("page_secondday")
    private Boolean pageSecondday;

    @ApiModelProperty("设置土特产(BOOL):0-否;1-是")
    @TableField("page_rura")
    private Boolean pageRura;

    @ApiModelProperty("用户页banner(BOOL):0-否;1-是")
    @TableField("page_likeyou")
    private Boolean pageLikeyou;

    @ApiModelProperty("兑换专区(BOOL):0-否;1-是")
    @TableField("page_exchange")
    private Boolean pageExchange;

    @ApiModelProperty("新品首发(BOOL):0-否;1-是")
    @TableField("page_new")
    private Boolean pageNew;

    @ApiModelProperty("新人优惠(BOOL):0-否;1-是")
    @TableField("page_newperson")
    private Boolean pageNewperson;

    @ApiModelProperty("升级VIP(BOOL):0-否;1-是")
    @TableField("page_upgrade")
    @JsonProperty("IsUpgrade")
    private Boolean pageUpgrade;

    @ApiModelProperty("信息发布(BOOL):0-否;1-是")
    @TableField("page_message")
    private Boolean pageMessage;

    @ApiModelProperty("是否发布(BOOL):0-否;1-是")
    @JsonProperty("IsRelease")
    @TableField("page_release")
    private Boolean pageRelease;

}
