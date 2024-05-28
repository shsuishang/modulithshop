package com.suisung.shopsuite.pay.model.res;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pay.model.entity.BaseBank;
import com.suisung.shopsuite.pay.model.entity.UserBankCard;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Accessors(chain = true)
@ApiModel(value = "账号银行集合")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BankRes {

    @ApiModelProperty("结算银行集合")
    private List<BaseBank> bankList;

    @ApiModelProperty("结算账户集合")
    private List<UserBankCard> userBankList;

}
