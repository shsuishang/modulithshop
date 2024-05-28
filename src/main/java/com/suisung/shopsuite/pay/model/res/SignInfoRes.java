package com.suisung.shopsuite.pay.model.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suisung.shopsuite.pay.model.vo.PointStepVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "签到信息对象")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SignInfoRes implements Serializable {

    private Integer continueSignDays;

    private List<String> signDayArr;

    private List<PointStepVo> signList;

    private Integer todayIsSign;

}
