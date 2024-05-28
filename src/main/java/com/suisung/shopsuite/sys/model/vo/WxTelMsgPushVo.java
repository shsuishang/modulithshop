package com.suisung.shopsuite.sys.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WxTelMsgPushVo {

    @JSONField(ordinal = 1)
    private String touser;

    @JSONField(ordinal = 2)
    private String template_id;

    @JSONField(ordinal = 3)
    private String url;

    @JSONField(ordinal = 4)
    private String topcolor = "#FF0000";

    @JSONField(ordinal = 5)
    private BaseValue data;

    @Data
    public static class BaseValue {

        @JSONField(ordinal = 1)
        private DetailValue first;

        @JSONField(ordinal = 2)
        private DetailValue keyword1;

        @JSONField(ordinal = 3)
        private DetailValue keyword2;

        @JSONField(ordinal = 4)
        private DetailValue keyword3;

        @JSONField(ordinal = 5)
        private DetailValue keyword4;

        @JSONField(ordinal = 6)
        private DetailValue remark;

    }

    @Data
    public static class DetailValue {

        @JSONField(ordinal = 1)
        private String value;

        @JSONField(ordinal = 2)
        private String color = "#173177";

    }

}
