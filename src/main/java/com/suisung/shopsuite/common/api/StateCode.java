package com.suisung.shopsuite.common.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StateCode {

    public static final int DELIVERY_TYPE_EXPRESS = 1;    //快递配送（运费 10 元）
    public static final int DELIVERY_TYPE_EMS = 2;    //EMS（邮政）
    public static final int DELIVERY_TYPE_MAIL = 3;    //平邮
    public static final int DELIVERY_TYPE_AIR_FREIGHT = 4;    //货运（空运、水运、铁路运输、公路运输）
    public static final int DELIVERY_TYPE_SELF_PICK_UP = 5;    //自提（运费 0 元）
    public static final int DELIVERY_TYPE_EXP = 10;    //配送

    public static final int DELIVERY_TIME_NO_LIMIT = 1;    //不限送货时间：周一至周日
    public static final int DELIVERY_TIME_WORKING_DAY = 2;    //工作日送货：周一至周五
    public static final int DELIVERY_TIME_WEEKEND = 3;    //双休日、假日送货：周六至周日

    public static final int USER_STATE_LOCKING = 0; //用户状态:锁定
    public static final int USER_STATE_NOTACTIVE = 1; //用户状态:未激活
    public static final int USER_STATE_ACTIVATION = 2; //用户状态:已激活

    public static final int PRODUCT_STATE_ILLEGAL = 1000; //违规下架禁售
    public static final int PRODUCT_STATE_NORMAL = 1001; //正常
    public static final int PRODUCT_STATE_OFF_THE_SHELF = 1002; //下架

    public static final int DEMAND_STATE_CONDUCT = 1000; //采购中
    public static final int DEMAND_STATE_REJECT = 1030; //被驳回
    public static final int DEMAND_STATE_EXAMINE = 1040; //审核中


    public static final int ACTIVITY_TYPE_VOUCHER = 1105; //店铺优惠券  coupon 优惠券

    public static final int ACTIVITY_TYPE_TODAYHOT = 5001; //今日爆款
    public static final int ACTIVITY_TYPE_DAILYGOOD = 5002; //每日好店
    public static final int ACTIVITY_TYPE_SECONDSKILL = 5003; //限时秒杀
    public static final int ACTIVITY_TYPE_SECONDSEVERY = 5004; //天天秒淘
    public static final int ACTIVITY_TYPE_ZEROBUY = 5005; //零元购
    public static final int ACTIVITY_TYPE_HIGHRETURN = 5006; //高额返区

    public static final int ACTIVITY_GROUPBOOKING_SALE_PRICE = 1; //以固定折扣购买
    public static final int ACTIVITY_GROUPBOOKING_FIXED_AMOUNT = 2; //以固定价格购买
    public static final int ACTIVITY_GROUPBOOKING_FIXED_DISCOUNT = 3; //优惠固定金额


    public static final int MARKRTING_ACTIVITY_JOIN = 1;//参加活动
    public static final int MARKRTING_ACTIVITY_JOIN_BY_QRCODE = 2;//通过二维码参加


    public static final int VOUCHER_STATE_UNUSED = 1501; //未用
    public static final int VOUCHER_STATE_USED = 1502; //已用
    public static final int VOUCHER_STATE_TIMEOUT = 1503; //过期
    public static final int VOUCHER_STATE_DEL = 1504; //收回

    //商品标签
    public static final int PRODUCT_TAG_NEW = 1401; //新品上架
    public static final int PRODUCT_TAG_REC = 1402; //热卖推荐
    public static final int PRODUCT_TAG_BARGAIN = 1403; //清仓优惠
    public static final int PRODUCT_TAG_BARGAIN1 = 1404; //清仓优惠
    public static final int PRODUCT_TAG_CROSSBORDS = 1405; //清仓优惠

    //商品种类
    public static final int PRODUCT_KIND_ENTITY = 1201; //实体商品	实物商品 （网购物流发货）
    public static final int PRODUCT_KIND_FUWU = 1202; //预购订单	服务类   （无需物流-门店或者上门服务，核销）
    public static final int PRODUCT_KIND_CARD = 1203; //电子卡券	电子卡券 （无需物流，发送代码即代表订单完成-目前同步处理，未来可以异步处理）
    public static final int PRODUCT_KIND_WAIMAI = 1204; //外卖订单	外卖订单 （同城配送），类似PRODUCT_KIND_ENTITY， 根据类型增加配送时间选项。可以由O2O店铺类型决定是否需要配送时间等等
    public static final int PRODUCT_KIND_EDU = 1205; //教育课程类订单


    public static final int PRODUCT_VERIFY_REFUSED = 3000; //审核未通过
    public static final int PRODUCT_VERIFY_PASSED = 3001; //审核通过
    public static final int PRODUCT_VERIFY_WAITING = 3002; //审核中

    public static final int ORDER_STATE_WAIT_PAY = 2010; //待付款 - 虚拟映射
    public static final int ORDER_STATE_WAIT_PAID = 2016; //已经付款 - 虚拟映射
    public static final int ORDER_STATE_WAIT_REVIEW = 2011; //待订单审核
    public static final int ORDER_STATE_WAIT_FINANCE_REVIEW = 2013; //待财务审核
    public static final int ORDER_STATE_PICKING = 2020; //待配货
    public static final int ORDER_STATE_WAIT_SHIPPING = 2030; //待发货
    public static final int ORDER_STATE_SHIPPED = 2040; //已发货
    public static final int ORDER_STATE_RECEIVED = 2050; //已签收
    public static final int ORDER_STATE_FINISH = 2060; //已完成
    public static final int ORDER_STATE_CANCEL = 2070; //已取消
    public static final int ORDER_STATE_SELF_PICKUP = 2080; //自提 - 虚拟映射     交易关闭	         交易关闭

    public static final int ORDER_STATE_ERROR = 2090; //异常订单
    public static final int ORDER_STATE_RETURN = 2091; //退回订单 - 虚拟映射

    //骑手端
    public static final int ORDER_STATE_PICKUP = 2045; //骑手取货
    public static final int ORDER_STATE_RIDER_RECEIVED = 2046; //骑手取货


    public static final int ORDER_PAID_STATE_NO = 3010; //未付款
    public static final int ORDER_PAID_STATE_FINANCE_REVIEW = 3011; //待付款审核
    public static final int ORDER_PAID_STATE_PART = 3012; //部分付款
    public static final int ORDER_PAID_STATE_YES = 3013; //已付款

    public static final int ORDER_PICKING_STATE_NO = 3020; //未出库
    public static final int ORDER_PICKING_STATE_PART = 3021; //部分出库通过拆单解决这种问题
    public static final int ORDER_PICKING_STATE_YES = 3022; //已出库

    public static final int ORDER_CARDKIND_STATE_CARD = 1001; //次卡类订单
    public static final int ORDER_CARDKIND_STATE_VOUCHER = 1002; //优惠券类订单
    public static final int ORDER_CARDKIND_STATE_COUPON = 1003; //券码类订单

    public static final int ORDER_SHIPPED_STATE_NO = 3030; //未发货
    public static final int ORDER_SHIPPED_STATE_PART = 3031; //部分发货
    public static final int ORDER_SHIPPED_STATE_YES = 3032; //已发货

    public static final int VIRTUAL_ORDER_USED = 2101; //虚拟订单已使用
    public static final int VIRTUAL_ORDER_UNUSE = 2100; //虚拟订单未使用
    public static final int VIRTUAL_ORDER_TIMEOUT = 2103; //虚拟订单过期

    public static final int ORDER_CANCEL_BY_BUYER = 2201; //买家取消订单
    public static final int ORDER_CANCEL_BY_SELLER = 2202; //卖家取消订单
    public static final int ORDER_CANCEL_BY_ADMIN = 2203; //平台取消


    public static final int SOURCE_TYPE_OTHER = 2310; //来源于其它
    public static final int SOURCE_TYPE_PC = 2311; //来源于pc端
    public static final int SOURCE_TYPE_H5 = 2312; //来源于H5端
    public static final int SOURCE_TYPE_APP = 2313; //来源于APP
    public static final int SOURCE_TYPE_MP = 2314; //来源于小程序


    public static final int SOURCE_FROM_OTHER = 2320; //来源于其它
    public static final int SOURCE_FROM_WECHAT = 2321; //来源于微信平台，包含公众号，小程序等等
    public static final int SOURCE_FROM_BAIDU = 2322; //来源于百度
    public static final int SOURCE_FROM_ALIPAY = 2323; //来源于支付宝
    public static final int SOURCE_FROM_TOUTIAO = 2324; //来源于头条


    //订单来源
    public static final int ORDER_FROM_PC = 2301; //来源于pc端
    public static final int ORDER_FROM_WAP = 2302; //来源于WAP手机端
    public static final int ORDER_FROM_WEBPOS = 2303; //来源于WEBPOS线下下单

    //状态
    public static final int SETTLEMENT_STATE_WAIT_OPERATE = 2401; //已出账
    public static final int SETTLEMENT_STATE_SELLER_COMFIRMED = 2402; //商家已确认
    public static final int SETTLEMENT_STATE_PLATFORM_COMFIRMED = 2403; //平台已审核
    public static final int SETTLEMENT_STATE_FINISH = 2404; //结算完成

    public static final int ORDER_RETURN_NO = 2500; //无退货
    public static final int ORDER_RETURN_ING = 2501; //退货中
    public static final int ORDER_RETURN_END = 2502; //退货完成

    public static final int ORDER_REFUND_STATE_NO = 2600; //无退款
    public static final int ORDER_REFUND_STATE_ING = 2601; //退款中
    public static final int ORDER_REFUND_STATE_END = 2602; //退款完成


    public static final int ORDER_TYPE_DD = 3061; //订单类型
    public static final int ORDER_TYPE_DC = 3063; //线下收银
    public static final int ORDER_TYPE_FX = 3062; //分销订单
    public static final int ORDER_TYPE_TH = 3066; //分销订单
    public static final int ORDER_TYPE_MD = 3068; //转单大厅订单
    public static final int ORDER_TYPE_PT = 3069; //跑腿订单

    public static final int ORDER_TYPE_XQ = 4034; //需求订单
    public static final int ORDER_TYPE_FW = 4035; //服务订单
    public static final int ORDER_TYPE_XX = 5000; //线下记账


    public static final int ACTIVITY_STATE_WAITING = 0; //活动状态:0-未开启
    public static final int ACTIVITY_STATE_NORMAL = 1; //活动状态:1-正常
    public static final int ACTIVITY_STATE_FINISHED = 2; //活动状态:2-已结束
    public static final int ACTIVITY_STATE_CLOSED = 3; //活动状态:3-管理员关闭

    public static final int GET_VOUCHER_FREE = 1; //活动状态:1-免费参与;
    public static final int GET_VOUCHER_BY_POINT = 2; //活动状态:2-积分参与;
    public static final int GET_VOUCHER_BY_PURCHASE = 3; //活动状态:3-购买参与
    public static final int GET_VOUCHER_BY_SHARE = 4; //活动状态:4-分享参与

    public static final int CART_GET_TYPE_BUY = 1; //购买
    public static final int CART_GET_TYPE_POINT = 2; //积分兑换
    public static final int CART_GET_TYPE_GIFT = 3; //赠品
    public static final int CART_GET_TYPE_BARGAIN = 4; //活动促销
    
    /*
    public static final int    BILL_TYPE_PO   = 4001;   //购货单
    public static final int    BILL_TYPE_PORO = 4002;   //销货退货单
    public static final int    BILL_TYPE_OI   = 4003;   //其他入库单
    public static final int    BILL_TYPE_SO   = 4031;   //销货单
    public static final int    BILL_TYPE_SORO = 4032;   //购货退货单
    public static final int    BILL_TYPE_OO   = 4033;   //其他出库单
    */

    public static final int STOCK_IN_PURCHASE = 2701;   //采购入库
    public static final int STOCK_IN_RETURN = 2702;   //退货入库
    public static final int STOCK_IN_ALLOCATE = 2703;   //调库入库
    public static final int STOCK_IN_INVENTORY_P = 2704;   //盘盈入库
    public static final int STOCK_IN_INIT = 2705;   //期初入库
    public static final int STOCK_IN_OTHER = 2706;   //手工入库
    public static final int STOCK_OUT_SALE = 2751;   //销售出库
    public static final int STOCK_OUT_DAMAGED = 2752;   //损坏出库
    public static final int STOCK_OUT_ALLOCATE = 2753;   //调库出库
    public static final int STOCK_OUT_LOSSES = 2754;   //盘亏出库
    public static final int STOCK_OUT_OTHER = 2755;   //手工出库
    public static final int STOCK_OUT_PO_RETURN = 2756;   //损坏出库


    public static final int STOCK_OUT_ALL = 2700;   //出库单
    public static final int STOCK_IN_ALL = 2750;   //入库单

    public static final int BILL_TYPE_OUT = 2700;   //出库单
    public static final int BILL_TYPE_IN = 2750;   //入库单


    public static final int BILL_TYPE_SO = 2800;   //销售订单
    public static final int BILL_TYPE_PO = 2850;   //采购订单


    //修改掉，和订单状态对应。
    public static final int ORDER_PROCESS_SUBMIT = 3070; //【客户】提交订单1OrderOrder

    public static final int ORDER_PROCESS_PAY = 2010; //待支付Order
    public static final int ORDER_PROCESS_CHECK = 2011; //订单审核1OrderOrder
    public static final int ORDER_PROCESS_FINANCE_REVIEW = 2013; //财务审核0OrderOrder
    public static final int ORDER_PROCESS_OUT = 2020; //出库审核商品库存在“出库审核”节点完成后扣减，如需进行库存管理或核算销售成本毛利，需开启此节点。0OrderOrder
    public static final int ORDER_PROCESS_SHIPPED = 2030; //发货确认如需跟踪订单物流信息，需开启此节点0OrderOrder
    public static final int ORDER_PROCESS_RECEIVED = 2040; //【客户】收货确认0OrderOrder

    public static final int ORDER_PROCESS_FINISH = 3098; //完成1OrderOrder

    public static final HashMap<Integer, Integer> RETURN_PROCESS_MAP = new HashMap();
    public static final int RETURN_PROCESS_SUBMIT = 3100; //【客户】提交退单1ReturnReturn
    public static final int RETURN_PROCESS_CHECK = 3105; //退单审核1ReturnReturn
    public static final int RETURN_PROCESS_RECEIVED = 3110; //收货确认0ReturnReturn
    public static final int RETURN_PROCESS_REFUND = 3115; //退款确认0ReturnReturn
    public static final int RETURN_PROCESS_RECEIPT_CONFIRMATION = 3120; //客户】收款确认0ReturnReturn
    public static final int RETURN_PROCESS_FINISH = 3125; //完成1ReturnReturn3130-商家拒绝退货
    public static final int RETURN_PROCESS_REFUSED = 3130; //-商家拒绝退货
    public static final int RETURN_PROCESS_CANCEL = 3135; //-买家取消

    //查找同意后的退款订单
    /*
    $return_where = array(
        'return_state_id' => array(
            StateCode::RETURN_PROCESS_RECEIVED             ,
            StateCode::RETURN_PROCESS_REFUND               ,
            StateCode::RETURN_PROCESS_RECEIPT_CONFIRMATION ,
            StateCode::RETURN_PROCESS_FINISH
        ),
        'order_id'=>$order_id
    );
    */

    public static final int PLANTFORM_RETURN_STATE_WAITING = 3180; //申请状态平台(ENUM):3180-处理中;
    public static final int PLANTFORM_RETURN_STATE_AGREE = 3181; //为待管理员处理卖家同意或者收货后;
    public static final int PLANTFORM_RETURN_PROCESS_FINISH = 3182; //-为已完成


    public static final int STORE_STATE_WAIT_PROFILE = 3210; //待完善资料
    public static final int STORE_STATE_WAIT_VERIFY = 3220; //等待审核
    public static final int STORE_STATE_NO = 3230; //审核资料没有通过
    public static final int STORE_STATE_YES = 3240; //审核资料通过,待付款

    public static final int TRADE_TYPE_SHOPPING = 1201;//购物
    public static final int TRADE_TYPE_TRANSFER = 1202;//转账
    public static final int TRADE_TYPE_DEPOSIT = 1203;//充值
    public static final int TRADE_TYPE_WITHDRAW = 1204;//提现
    public static final int TRADE_TYPE_SALES = 1205;//销售
    public static final int TRADE_TYPE_COMMISSION = 1206;//佣金
    public static final int TRADE_TYPE_REFUND_PAY = 1207;//退货付款
    public static final int TRADE_TYPE_REFUND_GATHERING = 1208;//退货收款
    public static final int TRADE_TYPE_TRANSFER_GATHERING = 1209;//转账收款
    public static final int TRADE_TYPE_COMMISSION_TRANSFER = 1210;//佣金付款
    public static final int TRADE_TYPE_BONUS = 1211;//分红
    public static final int TRADE_TYPE_BUY_SP = 1212;//购买SP
    public static final int TRADE_TYPE_SALE_SP = 1213;//销售SP
    public static final int TRADE_TYPE_FAVORABLE = 1214;//线下买单
    public static final int TRADE_TYPE_OTHER = 1215;//线下买单
    public static final int TRADE_TYPE_BUY_SELLER = 1216;//升级为商家
    public static final int TRADE_TYPE_SALE_SELLER = 1217;//销售升级为商家
    public static final int TRADE_TYPE_WITHDRAW_CANCEL = 1218;//提现驳回
    public static final int TRADE_TYPE_TRANSFER_COMMISSION = 1220;//转单分佣
    public static final int TRADE_TYPE_PAOTUI_FEE = 1221;//跑腿运费
    public static final int TRADE_TYPE_REBATE = 1222;//购物返利


    public static final int TRADE_TYPE_HALL_FEE = 1221;//需求订单


    public static final int TRADE_TYPE_BUY_POINTS = 1223;//购买积分
    public static final int TRADE_TYPE_SALE_POINTS = 1224;//销售积分
    public static final int TRADE_TYPE_STORE_RENEW = 1225;//店铺续费

    public static final int TRADE_TYPE_BUY_POSTER = 1226;//购买广告
    public static final int TRADE_TYPE_SHOPPING_CARD = 1227;//充值卡消费
    public static final int TRADE_TYPE_DEPOSIT_CARD = 1228;//充值卡充值

    public static final int TRADE_TYPE_OFFLINE_INCREASE = 1501;//线下记账收入
    public static final int TRADE_TYPE_OFFLINE_DECREASE = 1502;//线下记账支出

    public static final int TRADE_TYPE_DOCTOR_BUY = 1503;//购物
    public static final int TRADE_TYPE_DOCTOR_SERVICE = 1504;//销售

    public static final int TRADE_TYPE_XQ_BUY = 1505;//购物
    public static final int TRADE_TYPE_XQ_SERVICE = 1506;//销售


    public static final int PAYMENT_TYPE_DELIVER = 1301;//货到付款
    public static final int PAYMENT_TYPE_ONLINE = 1302;//在线支付
    //public static final int PAYMENT_TYPE_CREDIT  = 1303;//白条支付
    //public static final int PAYMENT_TYPE_CASH    = 1304;//现金支付
    public static final int PAYMENT_TYPE_OFFLINE = 1305;//线下支付


    public static final int PAYMENT_MET_MONEY = 1; //余额支付
    public static final int PAYMENT_MET_RECHARGE_CARD = 2; //充值卡支付
    public static final int PAYMENT_MET_POINTS = 3; //积分支付
    public static final int PAYMENT_MET_CREDIT = 4; //信用支付
    public static final int PAYMENT_MET_REDPACK = 5; //红包支付
    public static final int PAYMENT_MET_OFFLINE = 6; //线下支付
    public static final int PAYMENT_MET_SP = 7; //众宝支付

    public static final int PAYMENT_CHANNEL_WECHAT = 1403; //微信支付
    public static final int PAYMENT_CHANNEL_ALIPAY = 1401; //支付宝支付
    public static final int PAYMENT_CHANNEL_OFFLINE = 1422; //线下支付
    public static final int PAYMENT_CHANNEL_MONEY = 1406; //余额支付
    public static final int PAYMENT_CHANNEL_POINTS = 1413; //积分支付

    public static final int ORDER_ITEM_EVALUATION_NO = 0;    //未评价
    public static final int ORDER_ITEM_EVALUATION_YES = 1;    //已评价
    public static final int ORDER_ITEM_EVALUATION_TIMEOUT = 2;    //失效评价


    public static final int ORDER_PICKUP_CODE_UNUSED = 0;    //未评价
    public static final int ORDER_PICKUP_CODE_USED = 1;    //已评价
    public static final int ORDER_PICKUP_CODE_TIMEOUT = 2;    //失效评价

    public static final int ORDER_EVALUATION_NO = 0;    //未评价
    public static final int ORDER_EVALUATION_YES = 1;    //已评价
    public static final int ORDER_EVALUATION_TIMEOUT = 2;    //失效评价

    public static final int ORDER_NOT_NEED_RETURN_GOODS = 0;    //不用退货
    public static final int ORDER_NEED_RETURN_GOODS = 1;    //需要退货

    public static final int ORDER_REFUND = 1;    //1-退款申请; 2-退货申请; 3-虚拟退款
    public static final int ORDER_RETURN = 2;    //需要退货
    public static final int ORDER_VIRTUAL_REFUND = 3;    //需要退货

    public static final int USER_CERTIFICATION_NO = 0;    //0-未认证;1-待审核;2-认证通过;3-认证失败
    public static final int USER_CERTIFICATION_VERIFY = 1;    //待审核
    public static final int USER_CERTIFICATION_YES = 2;    //认证通过
    public static final int USER_CERTIFICATION_FAILED = 3;    //认证失败

    public static final int TO_STORE_SERVICE = 1001;    //到店取货
    public static final int DOOR_TO_DOOR_SERVICE = 1002;    // 上门服务

    public static final double ORDER_AUTO_TRANSFER_HALL_TIME = 0.5; //用户付款后多长时间没被接单就转到抢单大厅
    public static final int ORDER_AUTO_REFUND_TIME = 1; //用户付款后多长时间没被接单就自动退款

    public static final int SUPPLY_TASK_STATE_BIDDING = 2000; //竞标中
    public static final int SUPPLY_TASK_STATE_OVER = 2010; //已结束
    public static final int SUPPLY_TASK_STATE_ACCEPTANCE = 2020; //验收完成

    public static final int LIVEANCHOR_COMMIT = 1; // 提交
    public static final int LIVEANCHOR_PASS = 2; // 已审核通过
    public static final int LIVEANCHOR_REFUSE = 3; // 审核拒绝

    public static final int CHECK_STATE_NO = 1000; //不需处理
    public static final int CHECK_STATE_TODO = 1001; //待处理
    public static final int CHECK_STATE_OK = 1002; //处理完成
    public static final int CHECK_STATE_ERR = 1003; //异常

    public static final int CONTRACT_TYPE_7_RETURN = 1001; //7天无理由退货
    public static final int CONTRACT_TYPE_DENY_RETURN = 1006; //不支持退货


    public static final int ADMIN_PLANTFORM_USERID = 10001;

    public static final Map DELIVERY_TIME_NOT_TIMER = new HashMap();

    public static final Map DELIVERY_TIME_HOUR = new HashMap();

    public static final Map DELIVERY_TIME_MINUTE = new HashMap();

    static {
        DELIVERY_TIME_NOT_TIMER.put(1, "不限时段");
        DELIVERY_TIME_NOT_TIMER.put(15, "上午");
        DELIVERY_TIME_NOT_TIMER.put(16, "下午");
        DELIVERY_TIME_NOT_TIMER.put(17, "晚上");
        DELIVERY_TIME_NOT_TIMER.put(2, "08-10点");
        DELIVERY_TIME_NOT_TIMER.put(3, "10-12点");
        DELIVERY_TIME_NOT_TIMER.put(4, "10-14点");
        DELIVERY_TIME_NOT_TIMER.put(5, "14-16点");
        DELIVERY_TIME_NOT_TIMER.put(6, "16-18点");
        DELIVERY_TIME_NOT_TIMER.put(7, "18-20点");
        DELIVERY_TIME_NOT_TIMER.put(8, "20-22点");

        DELIVERY_TIME_HOUR.put("00", 0);
        DELIVERY_TIME_HOUR.put("01", 1);
        DELIVERY_TIME_HOUR.put("02", 2);
        DELIVERY_TIME_HOUR.put("03", 3);
        DELIVERY_TIME_HOUR.put("04", 4);
        DELIVERY_TIME_HOUR.put("05", 5);
        DELIVERY_TIME_HOUR.put("06", 6);
        DELIVERY_TIME_HOUR.put("07", 7);
        DELIVERY_TIME_HOUR.put("08", 8);
        DELIVERY_TIME_HOUR.put("09", 9);
        DELIVERY_TIME_HOUR.put("10", 10);
        DELIVERY_TIME_HOUR.put("11", 11);
        DELIVERY_TIME_HOUR.put("12", 12);
        DELIVERY_TIME_HOUR.put("13", 13);
        DELIVERY_TIME_HOUR.put("14", 14);
        DELIVERY_TIME_HOUR.put("15", 15);
        DELIVERY_TIME_HOUR.put("16", 16);
        DELIVERY_TIME_HOUR.put("17", 17);
        DELIVERY_TIME_HOUR.put("18", 18);
        DELIVERY_TIME_HOUR.put("19", 19);
        DELIVERY_TIME_HOUR.put("20", 20);
        DELIVERY_TIME_HOUR.put("21", 21);
        DELIVERY_TIME_HOUR.put("22", 22);
        DELIVERY_TIME_HOUR.put("23", 23);

        DELIVERY_TIME_MINUTE.put("00", "00");
        DELIVERY_TIME_MINUTE.put("05", "05");
        DELIVERY_TIME_MINUTE.put("10", "10");
        DELIVERY_TIME_MINUTE.put("15", "15");
        DELIVERY_TIME_MINUTE.put("20", "20");
        DELIVERY_TIME_MINUTE.put("25", "25");
        DELIVERY_TIME_MINUTE.put("30", "30");
        DELIVERY_TIME_MINUTE.put("35", "35");
        DELIVERY_TIME_MINUTE.put("40", "40");
        DELIVERY_TIME_MINUTE.put("45", "45");
        DELIVERY_TIME_MINUTE.put("50", "50");
        DELIVERY_TIME_MINUTE.put("55", "55");

        RETURN_PROCESS_MAP.put(RETURN_PROCESS_SUBMIT, 1);
        RETURN_PROCESS_MAP.put(RETURN_PROCESS_CHECK, 2);
        RETURN_PROCESS_MAP.put(RETURN_PROCESS_RECEIVED, 3);
        RETURN_PROCESS_MAP.put(RETURN_PROCESS_REFUND, 4);
        RETURN_PROCESS_MAP.put(RETURN_PROCESS_RECEIPT_CONFIRMATION, 5);
        RETURN_PROCESS_MAP.put(RETURN_PROCESS_FINISH, 6);
    }

    public static final Map<Integer, String> TRADE_TYPE_MAP = new HashMap();

    static {
        TRADE_TYPE_MAP.put(1201, "购物");
        TRADE_TYPE_MAP.put(1202, "转账");
        TRADE_TYPE_MAP.put(1203, "充值");
        TRADE_TYPE_MAP.put(1204, "提现");
        TRADE_TYPE_MAP.put(1205, "销售");
        TRADE_TYPE_MAP.put(1206, "佣金");
        TRADE_TYPE_MAP.put(1207, "退货付款");
        TRADE_TYPE_MAP.put(1208, "退货收款");
        TRADE_TYPE_MAP.put(1209, "转账收款");
        TRADE_TYPE_MAP.put(1210, "佣金付款");
        TRADE_TYPE_MAP.put(1211, "分红");
        TRADE_TYPE_MAP.put(1212, "购买SP");
        TRADE_TYPE_MAP.put(1213, "销售SP");
        TRADE_TYPE_MAP.put(1214, "线下消费");
        TRADE_TYPE_MAP.put(1215, "其它");
    }

    public static boolean ifH5(Integer source_type) {
        return source_type != null && SOURCE_TYPE_H5 == source_type;
    }

    public static boolean ifMp(Integer source_type) {
        return source_type != null && SOURCE_TYPE_MP == source_type;
    }

    public static boolean ifApp(Integer source_type) {
        return source_type != null && SOURCE_TYPE_APP == source_type;
    }

    public static boolean ifPc(Integer source_type) {
        return source_type != null && SOURCE_TYPE_PC == source_type;
    }

    public static boolean ifOther(Integer source_type) {
        return source_type != null && SOURCE_TYPE_OTHER == source_type;
    }

    public static final String WX_MP_ACCESSTOKEN = "wx_token:mp_token"; // 公众号Access_Token
    public static final String WX_XCX_ACCESSTOKEN = "wx_token:xcx_token"; // 小程序Access_Token

    public static final BigDecimal commissionInit = new BigDecimal("0.00");

}
