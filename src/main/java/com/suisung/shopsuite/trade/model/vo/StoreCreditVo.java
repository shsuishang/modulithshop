package com.suisung.shopsuite.trade.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StoreCreditVo implements Serializable {

    private StoreDeliverycreditVo storeDesccredit;

    private StoreDeliverycreditVo storeServicecredit;

    private StoreDeliverycreditVo storeDeliverycredit;

}
