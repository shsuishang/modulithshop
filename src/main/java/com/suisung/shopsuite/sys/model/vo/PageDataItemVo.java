package com.suisung.shopsuite.sys.model.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
public class PageDataItemVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("图片地址")
    private String path;

    @ApiModelProperty("图片地址 fix for swipe")
    @JsonProperty("pathBg")
    private String pathBg;

    @ApiModelProperty("标题")
    private String name;

    @ApiModelProperty("市场价")
    @JsonProperty("MarketPice")
    private Integer MarketPice;

    @ApiModelProperty("销售价")
    @JsonProperty("ItemSalePrice")
    private Integer ItemSalePrice;

    @ApiModelProperty("广告语")
    @JsonProperty("ProductTips")
    private String ProductTips;

    @ApiModelProperty("访问网址")
    @JsonProperty("AppUrl")
    private String AppUrl;

    @ApiModelProperty("小程序AppId")
    @JsonProperty("AppId")
    private String AppId;

    @ApiModelProperty("小程序跳转的页面")
    @JsonProperty("MinAppUrl")
    private String MinAppUrl;

    @ApiModelProperty("开始时间")
    @JsonProperty("StartTime")
    private String StartTime;

    @ApiModelProperty("开始时间")
    @JsonProperty("StartTimeStr")
    private String StartTimeStr;

    @ApiModelProperty("结束时间")
    @JsonProperty("EndTime")
    private String EndTime;

    @ApiModelProperty("结束时间")
    @JsonProperty("EndTimeStr")
    private String EndTimeStr;

    @ApiModelProperty("{{items.UserLimit}}人团")
    @JsonProperty("UserLimit")
    private Integer UserLimit;

    @ApiModelProperty("已有{{items.OrderCount}}人参加")
    @JsonProperty("OrderCount")
    private Integer OrderCount;


    @ApiModelProperty("表格宽度")
    @JsonProperty("flexNum")
    private int flexNum;

    @JsonProperty("selectType")
    private int selectType;

    private Long did;

    @ApiModelProperty("图片规格")
    @JsonProperty("specImg")
    private String specImg;

    @ApiModelProperty("搜索关键字")
    @JsonProperty("keyWord")
    private String keyWord;

    @ApiModelProperty("富文本")
    @JsonProperty("words")
    private String words;
}
