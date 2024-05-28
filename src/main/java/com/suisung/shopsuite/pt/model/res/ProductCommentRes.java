package com.suisung.shopsuite.pt.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.core.web.model.res.BaseListRes;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductCommentRes extends BaseListRes implements Serializable {

    private long good;

    private long satisfied;

    private long bad;

}
