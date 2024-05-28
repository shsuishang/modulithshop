package com.suisung.shopsuite.sys.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Mq发送消息Vo", description = "Mq发送消息Vo")
public class MqMessageVo implements Serializable {

    private String exchange;

    private String routing_key;

    private Object data;

}
