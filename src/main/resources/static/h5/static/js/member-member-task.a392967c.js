(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["member-member-task"],{"106a":function(e,t,i){"use strict";i.d(t,"b",(function(){return a})),i.d(t,"c",(function(){return n})),i.d(t,"a",(function(){}));var a=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"page"},[i("v-uni-scroll-view",{staticClass:"u-pa1",attrs:{"scroll-y":"true"},on:{scrolltolower:function(t){arguments[0]=t=e.$handleEvent(t),e.scrollbottom.apply(void 0,arguments)},scroll:function(t){arguments[0]=t=e.$handleEvent(t),e.scrolltoupper.apply(void 0,arguments)}}},[i("v-uni-view",{class:e.flag||e.flag1?"cover":"",staticStyle:{"z-index":"9"}}),i("v-uni-view",{staticStyle:{background:"#f7f7f7",position:"relative"}},[i("v-uni-view",{staticClass:"header"},[i("v-uni-view",{class:e.member.IsEnabled?"header-top":"if_carh1"},[i("v-uni-view",{class:e.member.IsEnabled?"if_carh2":"if_carh3",staticStyle:{width:"94%",margin:"0 auto","border-radius":"12px"}},[i("v-uni-view",{class:e.member.IsEnabled?"":"if_carh",staticStyle:{width:"100%",display:"flex"}},[i("v-uni-view",{staticClass:"message",staticStyle:{flex:"1","margin-top":"4%",width:"140rpx",height:"140rpx","border-radius":"120rpx",border:"1px solid #fff","vertical-align":"middle",overflow:"hidden","margin-left":"4%"}},[i("v-uni-image",{staticStyle:{width:"140rpx",height:"140rpx"},attrs:{"lazy-load":!0,src:e.UserInfo.user_avatar}})],1),i("v-uni-view",{staticClass:"grade",staticStyle:{flex:"4","text-align":"left","font-family":"PingFangSC-Semibold","font-size":"14px",color:"#4A4A4A","font-weight":"bold"}},[i("v-uni-view",[e._v(e._s(e.message.user_nickname))]),i("v-uni-view",{staticStyle:{margin:"2% 0 0 0","text-align":"left","font-family":"PingFangSC-Regular","font-size":"22rpx",color:"#8f6846"}},[e._v(e._s(e.plantformInfo.user_level_map[e.userInfo.user_level_id]||e.__("普通会员"))),e.Config.PLANTFORM_FX_ENABLE?i("v-uni-label",{staticStyle:{float:"right","margin-right":"20rpx"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.copyId(e.message.user_id)}}},[e._v(e._s(e.__("推广码"))+": "+e._s(e.message.user_id))]):e._e()],1)],1)],1),1==e.member.IsEnabled?i("v-uni-view",{staticClass:"card_vip",staticStyle:{overflow:"hidden"}},[i("v-uni-view",{staticClass:"show_card",staticStyle:{position:"relative"},attrs:{"data-index":"index"}},[0==e.member.IsReceive?i("v-uni-view",{staticClass:"name_card",staticStyle:{position:"absolute",top:"13%",left:"24%","font-family":"PingFangSC-Semibold",color:"#4A4A4A","letter-spacing":"0",width:"54%","text-align":"center"}},[e._v(e._s(e.message.user_level_name)+e._s(e.__("会员卡")))]):e._e(),1==e.member.IsReceive?i("v-uni-view",{staticClass:"name_card",staticStyle:{position:"absolute",top:"23%",left:"5%","font-family":"PingFangSC-Semibold",color:"black"}},[e._v(e._s(e.message.user_level_name)+e._s(e.__("会员卡")))]):e._e(),e.member.IsEnabled&&e.member.IsReceive&&1==e.member.ReciveStatus&&e.member.IsToWeixin?i("v-uni-view",{staticClass:"num_card",staticStyle:{position:"absolute",top:"54%",left:"5%","font-size":"22rpx",color:"#9B9B9B","font-family":"PingFangSC-Semibold"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.openCard.apply(void 0,arguments)}}},[e._v(e._s(e.message.user_level_card))]):e._e(),e._e(),2==e.member.ReciveStatus&&e.member.IsToWeixin&&null!=e.message.user_level_card&&1==e.member.DrawMethod?i("v-uni-view",{staticClass:"num_card",staticStyle:{position:"absolute",top:"56%",left:"-2%","font-size":"22rpxcolor: #9B9B9B","font-family":"PingFangSC-Semibold"}},[i("v-uni-button",{staticClass:"num_card",staticStyle:{background:"#fcf3f4",width:"200rpx","text-align":"center",position:"absolute",top:"54%","border-radius":"49rpx","font-family":"PingFangSC-Semibold","font-size":"22rpx"},attrs:{"open-type":"getPhoneNumber",hidden:"showPhoneNumber"},on:{getphonenumber:function(t){arguments[0]=t=e.$handleEvent(t),e.getPhoneNumber.apply(void 0,arguments)}}},[i("v-uni-view",{staticStyle:{color:"#9B9B9B"}},[e._v(e._s(e.message.user_level_card))])],1)],1):e._e(),2==e.member.ReciveStatus&&e.member.IsToWeixin&&null!=e.message.user_level_card&&2==e.member.DrawMethod?i("v-uni-view",{staticClass:"num_card",staticStyle:{position:"absolute",top:"56%",left:"5%","font-size":"22rpx",color:"#9B9B9B","font-family":"PingFangSC-Semibold"},on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.cardDetail.apply(void 0,arguments)}}},[e._v(e._s(e.message.user_level_card))]):e._e(),e.member.IsEnabled&&e.member.IsReceive&&1==e.member.ReciveStatus&&e.member.IsToWeixin?i("v-uni-view",{staticStyle:{width:"120rpx",height:"120rpx",background:"#F92C46","border-radius":"50%",position:"absolute",top:"32%",right:"6%"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.openCard.apply(void 0,arguments)}}},[i("v-uni-image",{staticStyle:{"border-radius":"0",width:"49%",height:"49%",position:"absolute",top:"25%",left:"25%"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/erweima.png"}})],1):e._e(),e._e(),2==e.member.ReciveStatus&&e.member.IsToWeixin&&null!=e.message.user_level_card?i("v-uni-view",{staticStyle:{width:"120rpx",height:"120rpx",background:"#F92C46","border-radius":"50%",position:"absolute",top:"32%",right:"6%"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.getPhoneNumber.apply(void 0,arguments)}}},[i("v-uni-image",{staticStyle:{"border-radius":"0",width:"49%",height:"49%",position:"absolute",top:"25%",left:"25%"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/erweima.png"}})],1):e._e(),1==e.member.DrawMethod&&0==e.member.IsReceive?i("v-uni-button",{staticClass:"num_card",staticStyle:{background:"#F92C46",width:"41%","/*padding-right":"47%*/text-align: center",position:"absolute",top:"60%",right:"29%","border-radius":"27px"},attrs:{"open-type":"getPhoneNumber",hidden:"showPhoneNumber"},on:{getphonenumber:function(t){arguments[0]=t=e.$handleEvent(t),e.getPhoneNumber.apply(void 0,arguments)}}},[i("v-uni-view",[e._v(e._s(e.__("立即领取")))])],1):e._e(),2==e.member.DrawMethod&&0==e.member.IsReceive?i("v-uni-view",{staticClass:"num_card",staticStyle:{background:"#F92C46",width:"48%","text-align":"center",position:"absolute",top:"60%",right:"24%","border-radius":"49rpx"},on:{click:function(t){t.stopPropagation(),arguments[0]=t=e.$handleEvent(t),e.cardDetail.apply(void 0,arguments)}}},[e._v(e._s(e.__("立即领取")))]):e._e()],1)],1):e._e()],1),e.member.IsEnabled&&e.member.IsReceive&&1==e.member.ReciveStatus&&e.member.IsToWeixin?i("v-uni-view",{staticClass:"wx_kb grade",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.openCard.apply(void 0,arguments)}}},[i("v-uni-image",{staticStyle:{width:"106px",height:"29px","border-radius":"0"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/cardp-bg.png"}})],1):e._e()],1)],1),i("v-uni-view",{class:e.member.IsEnabled?"header-bottom":"if_carh4"},[i("v-uni-navigator",{staticClass:"growth",attrs:{url:"/member/member/growth"}},[i("v-uni-view",{staticClass:"num"},[e._v(e._s(e.message.user_exp))]),i("v-uni-view",{staticStyle:{color:"#9B9B9B"}},[e._v(e._s(e.__("成长值")))])],1),i("v-uni-navigator",{staticClass:"growth",attrs:{url:"/member/cash/predeposit"}},[i("v-uni-view",{staticClass:"num"},[e._v(e._s(e.number_format(e.message.user_money,2)))]),i("v-uni-view",{staticStyle:{color:"#9B9B9B"}},[e._v(e._s(e.__("余额")))])],1),i("v-uni-navigator",{staticClass:"growth",attrs:{url:"/member/member/coupon"}},[i("v-uni-view",{staticClass:"num"},[e._v(e._s(e.message.voucher))]),i("v-uni-view",{staticStyle:{color:"#9B9B9B"}},[e._v(e._s(e.__("优惠券")))])],1),i("v-uni-navigator",{staticClass:"integral",attrs:{url:"/integral/integral/integral"}},[i("v-uni-view",{staticClass:"num"},[e._v(e._s(e.number_format(e.message.user_points)))]),i("v-uni-view",{staticStyle:{color:"#9B9B9B"}},[e._v(e._s(e.__("积分")))])],1)],1),i("v-uni-view",{staticClass:"prerogative",staticStyle:{padding:"130rpx 0 20rpx 0","margin-top":"0"}},[i("v-uni-view",{staticClass:"prerogative-top"},[i("v-uni-text",{staticStyle:{"margin-left":"5px",color:"#8f6846","font-size":"32rpx"}},[e._v(e._s(e.__("会员权益")))])],1)],1),100==e.message.user_level_rate||0==e.message.user_level_rate?i("v-uni-view",{staticClass:"prerogative1"},[i("v-uni-view",{staticClass:"prerogative-bottom"},[e._v(e._s(e.__("尊敬的会员，您现在无会员折扣可以使用。")))])],1):i("v-uni-view",{staticClass:"prerogative1"},[i("v-uni-view",{staticClass:"prerogative-bottom"},[e._v(e._s(e.__("尊敬的会员，您在购物时可享受"))+e._s(e.message.user_level_rate/10)+e._s(e.__("折优惠")))])],1),i("v-uni-view",{staticStyle:{background:"#fff"}},[i("v-uni-view",{staticClass:"prerogative"},[i("v-uni-view",{staticClass:"prerogative-top"},[i("v-uni-text",{staticStyle:{"margin-left":"5px",color:"#8f6846","font-size":"32rpx"}},[e._v(e._s(e.__("任务中心")))])],1)],1),i("v-uni-view",{staticClass:"mission",staticStyle:{padding:"26rpx 0rpx"}},[i("v-uni-view",{staticClass:"draw"},[i("v-uni-image",{staticStyle:{width:"12px",height:"12px"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/qiandao.png"}}),i("v-uni-text",[e._v(e._s(e.__("签到领积分")))])],1),i("v-uni-button",{class:"color",staticStyle:{"margin-right":"0px","padding-left":"9rpx","padding-right":"9rpx"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.click.apply(void 0,arguments)}}},[e._v(e._s(e.isSign?e.__("去签到"):e.__("已签到")))])],1),i("v-uni-view",{staticClass:"mission",staticStyle:{padding:"26rpx 0rpx"}},[i("v-uni-view",{staticClass:"draw"},[i("v-uni-image",{staticStyle:{width:"12px",height:"10px"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/gouwu.png"}}),i("v-uni-text",[e._v(e._s(e.__("购物领积分")))])],1),i("v-uni-navigator",{staticClass:"button",attrs:{"open-type":"switchTab",url:"/pages/index/index"}},[e._v(e._s(e.__("去购物")))])],1),i("v-uni-view",{staticClass:"mission",staticStyle:{padding:"26rpx 0rpx"}},[i("v-uni-view",{staticClass:"draw"},[i("v-uni-image",{staticStyle:{width:"12px",height:"12px"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/pinglun.png"}}),i("v-uni-text",[e._v(e._s(e.__("评价领积分")))])],1),i("v-uni-navigator",{staticClass:"button",attrs:{url:"/member/order/list?type=2060&sl=4"}},[e._v(e._s(e.__("去评价")))])],1),e.flag?i("v-uni-view",{staticClass:"alert",staticStyle:{"z-index":"1000"}},[i("v-uni-image",{staticStyle:{width:"50rpx",height:"50rpx"},attrs:{src:"https://static.shopsuite.cn/xcxfile/appicon/img/true.png"}}),i("v-uni-text",{staticStyle:{"margin-left":"20rpx"}},[e._v(e._s(e.signMsg))])],1):e._e(),e.flag1?i("v-uni-view",{staticClass:"alert1",staticStyle:{"z-index":"1000"}},[i("v-uni-view",[e._v(e._s(e.__("抱歉！今日您已签到")))]),i("v-uni-view",{staticStyle:{"margin-top":"20rpx"}},[e._v(e._s(e.__("~明天再来哦~")))])],1):e._e()],1)],1),i("guess-you-like",{ref:"guessYouLike",attrs:{titleText:e.__("——猜你喜欢——")}})],1),i("v-uni-view",{staticClass:"u-top-default"},[i("v-uni-navigator",{staticClass:"u-back2",attrs:{url:"/pages/index/index","open-type":"switchTab"}},[i("v-uni-image",{attrs:{src:"http://appicon-1253690476.file.myqcloud.com/endorsement/gohome.png"}})],1)],1)],1)},n=[]},5334:function(e,t,i){var a=i("c86c");t=a(!1),t.push([e.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */[data-v-1c4f398e]:export{theme_bg:#e93323}.header[data-v-1c4f398e]{width:100%;background:#e93323;padding-top:%?20?%}.header-top[data-v-1c4f398e]{width:100%;height:%?290?%;background:#e93323;justify-content:left}.cover[data-v-1c4f398e]{position:fixed;width:100%;height:100%;top:0;background:rgba(0,0,0,.4);overflow:hidden}.butoo[data-v-1c4f398e]{background-color:#f7f7f7;width:200px;color:red;text-align:left;padding-left:5px;margin-top:0;margin-left:15px}\r\n/* .message { margin-top: 30rpx; } */.grade[data-v-1c4f398e]{font-size:%?28?%;color:#fff;text-align:center;padding:%?49?% 0 0 %?20?%}.header-top uni-image[data-v-1c4f398e]{width:%?118?%;height:%?118?%;border-radius:50%}.header-bottom[data-v-1c4f398e]{width:94%;height:%?120?%;background:#fff;padding:%?20?% 0;display:flex;margin:0 auto;border-radius:%?21?%;position:absolute;top:%?236?%;left:3%;box-shadow:0 8px 8px rgba(0,0,0,.048)}.growth[data-v-1c4f398e]{width:50%;font-size:%?28?%;color:#fff;text-align:center;margin-top:2%}.integral[data-v-1c4f398e]{width:50%;font-size:%?28?%;color:#fff;text-align:center;margin-top:2%}.num[data-v-1c4f398e]{margin-bottom:%?10?%;color:#8f6846;font-family:Tahoma-Bold;font-size:%?26?%}.prerogative[data-v-1c4f398e]{margin-top:%?20?%;padding:%?45?% 0 %?20?% 0;background:#fff}.prerogative-top[data-v-1c4f398e]{border-left:%?6?% solid #fff;font-size:%?28?%;height:%?28?%;margin-left:5%;line-height:%?28?%}.prerogative1[data-v-1c4f398e]{padding:0 0 %?50?% 0;background:#fff}.prerogative-bottom[data-v-1c4f398e]{font-size:%?26?%;height:%?28?%;margin-left:7%;color:#9b9b9b}.color[data-v-1c4f398e]{width:%?106?%;height:%?48?%;display:flex;justify-content:center;line-height:%?48?%;font-size:%?24?%;background:#e93323;color:#fff;border-radius:%?10?%}\r\n/* .mission{ padding: 26rpx 0; border-top:1px solid #d9d9d9; position: relative; background: #fff; display: flex; justify-content: space-between; align-items: center } .mission::before{ content:" "; position:absolute; top:0; right:0; height:1px; border-top:1px solid #ebebe7; color:#ebebe7; -webkit-transform-origin:0 0; transform-origin:0 0; -webkit-transform:scaleY(0.5); transform:scaleY(0.5); } */.mission[data-v-1c4f398e]{padding:%?20?% %?0?%;display:-webkit-box;position:relative;display:-webkit-flex;display:flex;-webkit-box-align:center;-webkit-align-items:center;align-items:center;justify-content:space-between;background:#fff;width:85%;margin:0 auto}.mission[data-v-1c4f398e]::before{content:" ";position:absolute;left:0;top:0;right:0;height:1px;border-top:1px solid #f1f1f1;color:#d9d9d9;-webkit-transform-origin:0 0;transform-origin:0 0;-webkit-transform:scaleY(.5);transform:scaleY(.5)}.button[data-v-1c4f398e]{width:%?106?%;height:%?48?%;\r\n  /* text-align: center; */display:flex;justify-content:center;line-height:%?48?%;font-size:%?24?%;color:#fff;border-radius:%?10?%}.draw[data-v-1c4f398e]{font-size:%?28?%;height:%?40?%;line-height:%?40?%;display:flex;align-items:center}.draw uni-image[data-v-1c4f398e]{width:%?40?%;height:%?40?%}.draw uni-text[data-v-1c4f398e]{margin-left:%?20?%}.alert[data-v-1c4f398e]{font-size:%?32?%;width:70%;height:%?200?%;position:absolute;left:15%;top:40%;background:#fff;border-radius:%?10?%;justify-content:center;display:flex;align-items:center}.alert1[data-v-1c4f398e]{font-size:%?32?%;width:70%;height:%?200?%;position:absolute;left:15%;top:40%;background:#fff;border-radius:%?10?%;justify-content:center;display:flex;flex-flow:column;align-items:center}.u-back2 uni-image[data-v-1c4f398e]{border-radius:100%;width:%?77?%;height:%?77?%;border:1px solid #eee;font-size:%?20?%;text-align:center;background-color:#fff;box-shadow:0 %?4?% %?8?% rgba(0,0,0,.35);z-index:999;opacity:.8;line-height:%?77?%;margin-bottom:%?20?%}.wx_kb[data-v-1c4f398e]{width:100%;text-align:right;position:absolute;top:1%;right:3%}.if_carh[data-v-1c4f398e]{height:%?205?%}.if_carh1[data-v-1c4f398e]{width:100%;height:%?223?%;background:#db384c;justify-content:left}.if_carh2[data-v-1c4f398e]{background:#fcf3f4;padding-bottom:%?20?%}.if_carh3[data-v-1c4f398e]{background:#fff}.if_carh4[data-v-1c4f398e]{position:absolute;top:17%;left:3%;width:94%;height:%?153?%;background:#fff;padding:%?20?% 0;display:flex;margin:0 auto;border-radius:%?21?%;box-shadow:0 1px 0 rgba(0,0,0,.1)}.if_carh5[data-v-1c4f398e]{background:#fff}.card_vip[data-v-1c4f398e]{width:100%;height:%?253?%;display:none}.show_card[data-v-1c4f398e]{width:91%;height:%?192?%;border-top-left-radius:10px;border-top-right-radius:10px;margin:0 auto}.name_card[data-v-1c4f398e]{float:left;color:#fff;line-height:%?102?%;font-size:%?24?%}.num_card[data-v-1c4f398e]{color:#fff;line-height:%?75?%;font-size:%?32?%}.overbalance[data-v-1c4f398e]{width:100%;margin:%?22?% auto;box-sizing:border-box;background:#fff}.overbalanceTitle[data-v-1c4f398e]{width:100%;height:%?88?%;font-family:PingFangSC-Medium;font-size:%?26?%;color:#000;font-weight:500;line-height:%?37?%;padding-top:%?27?%;padding-bottom:%?24?%;box-sizing:border-box;\r\n  /* letter-spacing: 0; */background:#fff;box-shadow:0 %?2?% 0 0 #efefef;padding-right:%?24?%}.overbalanceTitle uni-image[data-v-1c4f398e]{width:%?23?%;height:%?26?%;margin-right:%?15?%}.overbalanceConversion[data-v-1c4f398e]{width:100%;height:%?469?%;padding:%?30?%;box-sizing:border-box;white-space:nowrap;display:flex}.everyConversion[data-v-1c4f398e]{display:inline-block;width:%?300?%;height:%?430?%;margin-right:%?20?%;padding:%?20?% %?25?% %?34?%;box-sizing:border-box;background:#fff;box-shadow:0 %?5?% %?10?% 0 rgba(0,0,0,.06);border-radius:%?8?%}.everyConversion .moreCoupon[data-v-1c4f398e]{width:%?250?%;height:%?250?%;background-image:linear-gradient(-225deg,#f87c5e,#db384c);margin-bottom:%?24?%;border-radius:%?8?%;text-align:center;box-sizing:border-box;font-size:%?24?%;line-height:%?33?%;padding-top:%?55?%;color:#fff}.moreCoupon uni-view[data-v-1c4f398e]:first-child{width:100%;height:%?90?%;margin:0 auto;text-align:center;font-size:%?40?%}.moreCoupon uni-view:first-child uni-text[data-v-1c4f398e]{font-size:%?64?%;line-height:%?90?%}.moreCoupon uni-view[data-v-1c4f398e]:last-child{width:100%;text-align:center;padding:0 %?10?%;box-sizing:border-box;word-wrap:break-word;white-space:pre-wrap}.everyConversion uni-image[data-v-1c4f398e]{display:block;width:%?250?%;height:%?250?%;margin-bottom:%?24?%}.everyConversion .titel[data-v-1c4f398e]{display:block;width:100%;font-family:PingFangSC-Regular;font-size:%?24?%;line-height:%?33?%;color:#000;\r\n  /* letter-spacing: 0; */overflow:hidden;text-overflow:ellipsis;white-space:nowrap;margin-bottom:%?19?%}.everyConversion .integralBtn[data-v-1c4f398e]{display:flex;align-items:center;justify-content:space-between;margin:0;padding:0}.everyConversion .integralBtn uni-text[data-v-1c4f398e]{font-family:PingFangSC-Semibold;font-size:%?28?%;line-height:%?40?%;color:#db384c\r\n  /* letter-spacing: 0; */}.everyConversion .integralBtn uni-button[data-v-1c4f398e]{width:%?100?%;height:%?50?%;background:#db384c;border-radius:%?10?%;font-family:PingFangSC-Regular;font-size:%?24?%;color:#fff;\r\n  /* letter-spacing: 0; */padding:0;margin:0;line-height:%?50?%}.m-listv[data-v-1c4f398e]{margin-top:%?20?%;background:#f8f8f8;padding:0 %?4?% 0 %?16?%;overflow:hidden}.recommend[data-v-1c4f398e]{text-align:center;padding:%?20?% 0;color:#db384c;font-size:%?30?%;font-weight:700}.m-listv .m-product-item[data-v-1c4f398e]{margin:%?10?%;width:%?345?%;height:%?520?%;overflow:hidden;float:left;border-radius:%?8?%;background-color:#fff}.m-listv .m-product-img[data-v-1c4f398e]{width:%?345?%;height:%?345?%;float:left;text-align:center}.m-product-img uni-image[data-v-1c4f398e]{width:100%;height:100%;box-sizing:border-box;padding:%?20?%}.m-product-info[data-v-1c4f398e]{height:%?210?%;width:%?538?%;float:left;box-sizing:border-box;padding:%?20?% 0;position:relative}.m-listv .m-product-name[data-v-1c4f398e]{width:%?345?%;height:%?100?%;box-sizing:border-box;padding:%?10?% %?20?%;font-size:%?28?%;line-height:%?40?%;position:relative}.m-product-name uni-label[data-v-1c4f398e]{overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2}.m-listv .m-product-price[data-v-1c4f398e]{padding:0 %?20?%;font-size:%?32?%;line-height:%?40?%;color:#db384c}.m-product-price uni-label[data-v-1c4f398e]{font-size:%?24?%}.msk2[data-v-1c4f398e]{position:fixed;top:0;left:0;width:100%;height:100%;background-color:rgba(0,0,0,.5);z-index:10}.m-coupon-box[data-v-1c4f398e]{background-color:#db384c;padding:%?10?% %?25?% %?35?% %?25?%;border-radius:%?35?%;width:62%;margin:25% auto}.coupon_title[data-v-1c4f398e]{font-size:%?52?%;color:#fff;margin:%?30?% auto;text-align:center;letter-spacing:%?3?%}.coupon_back[data-v-1c4f398e]{width:%?468?%;height:%?450?%}.shopbox[data-v-1c4f398e]{display:flex;align-items:center;padding:%?28?% %?48?% %?22?% %?38?%}.shop_name[data-v-1c4f398e]{font-size:%?28?%}.context_box[data-v-1c4f398e]{margin:%?10?% auto 0;display:flex;align-items:center;color:#db384c}.left[data-v-1c4f398e]{margin-right:%?20?%;text-align:center;width:30%;max-width:45%;overflow:hidden}.left2[data-v-1c4f398e]{margin-right:%?20?%;text-align:center;width:35%;max-width:50%;overflow:hidden;line-height:53px;font-size:%?38?%}.right[data-v-1c4f398e]{margin:auto 0;overflow:hidden}.youhuiquan[data-v-1c4f398e]{border-bottom:%?1?% solid #db384c;overflow:hidden;height:%?47?%;max-width:%?280?%;line-height:%?43?%;white-space:nowrap;text-overflow:ellipsis}.manjian[data-v-1c4f398e]{max-width:%?306?%;line-height:%?50?%;overflow:hidden;white-space:nowrap;text-overflow:ellipsis}.youxiaoqi[data-v-1c4f398e]{font-size:%?24?%;color:#999;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;padding-top:%?22?%;\r\n  /* text-align: center; */padding-left:%?38?%}.lijishiyong_box[data-v-1c4f398e]{width:100%;background:#fff;display:flex;align-items:center;justify-content:center}.lijishiyong[data-v-1c4f398e]{width:%?250?%;height:%?60?%;background-color:#db384c;color:#fff;border-radius:%?35?%;letter-spacing:%?4?%;text-align:center;font-size:%?28?%;line-height:%?60?%;margin-top:%?32?%}.close_msk1[data-v-1c4f398e]{display:flex;justify-content:center;align-items:center;margin-top:%?124?%}',""]),e.exports=t},"5ba6c":function(e,t,i){"use strict";var a=i("a833"),n=i.n(a);n.a},"5e3a":function(e,t,i){"use strict";i.r(t);var a=i("e7c1"),n=i.n(a);for(var o in a)["default"].indexOf(o)<0&&function(e){i.d(t,e,(function(){return a[e]}))}(o);t["default"]=n.a},"812c":function(e,t,i){"use strict";i.r(t);var a=i("106a"),n=i("5e3a");for(var o in n)["default"].indexOf(o)<0&&function(e){i.d(t,e,(function(){return n[e]}))}(o);i("5ba6c");var r=i("828b"),s=Object(r["a"])(n["default"],a["b"],a["c"],!1,null,"1c4f398e",null,!1,a["a"],void 0);t["default"]=s.exports},a833:function(e,t,i){var a=i("5334");a.__esModule&&(a=a.default),"string"===typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);var n=i("967d").default;n("705f71a0",a,!0,{sourceMap:!1,shadowMode:!1})},e7c1:function(e,t,i){"use strict";i("6a54");var a=i("f5bd").default;Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,i("5ef2");var n=a(i("9b1b")),o=a(i("2634")),r=a(i("2fdc")),s=i("8f59"),d=a(i("7198")),l={data:function(){return{message:"",member:{IsEnabled:!0,IsReceive:!0,CardNum:111,CardName:"sss",ReciveStatus:2,IsToWeixin:!1,DrawMethod:2},con:"",Info:"",UserInfo:"",flag:!1,flag2:!1,flag1:!1,flag3:!1,isSign:!0,signMsg:"",screenHeight:0,isquicknav:!1}},computed:(0,s.mapState)(["Config","StateCode","notice","plantformInfo","shopInfo","userInfo","hasLogin"]),components:{guessYouLike:d.default},onLoad:function(){var e=(0,r.default)((0,o.default)().mark((function e(t){var i;return(0,o.default)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,this.$onLaunched;case 2:uni.setNavigationBarTitle({title:this.__("会员中心")}),i=this,this.forceUserInfo((function(e){i.setData({UserInfo:e}),i.load()}));case 5:case"end":return e.stop()}}),e,this)})));return function(t){return e.apply(this,arguments)}}(),methods:(0,n.default)((0,n.default)({},(0,s.mapMutations)(["login","logout","getPlantformInfo","forceUserInfo","getUserInfo","reloadUserResource"])),{},{scrollbottom:function(e){this.$refs.guessYouLike.scrollbottom()},scrolltoupper:function(e){e.detail.scrollTop>=this.screenHeight?this.setData({isquicknav:!0}):this.setData({isquicknav:!1})},getPhoneNumber:function(e){if(-1<e.detail.errMsg.indexOf("ok"))if(0==(i=this).member.IsToWeixin){var t={userId:app.globalData.UserInfo.Id,vendorId:app.globalData.VendorInfo.Id,cardId:i.member.Id,phone:app.globalData.UserInfo.Phone};$.xsr($.makeUrl(user.AddMemberCard,t),(function(e){}))}else{i.GetMemberCard();var i=this,a={vendorId:app.globalData.VendorInfo.Id,cardInfo:i.member.WeixinId};$.xsr($.makeUrl(user.GetMemberCardPostInfo,a),(function(e){i.setData({id:e.Info.id,tsamp:e.Info.tsamp,nonstr:e.Info.nonstr,sign:e.Info.sign}),wx.addCard({cardList:[{cardId:i.id,cardExt:'{ "timestamp":'+i.tsamp+', "signature":"'+i.sign+'","nonce_str":"'+i.nonstr+'"}'}],success:function(e){i.onLoad();var t={vendorId:app.globalData.VendorInfo.Id,userId:app.globalData.UserInfo.Id,cCode:e.cardList[0].code,cCard:e.cardList[0].cardId,cardId:i.member.Id};$.xsr($.makeUrl(user.GetMCardRelationInfo,t),(function(e){i.onLoad()}));var a={userId:app.globalData.UserInfo.Id,vendorId:app.globalData.VendorInfo.Id,cardId:i.member.Id,phone:app.globalData.UserInfo.Phone};$.xsr($.makeUrl(user.AddMemberCard,a),(function(e){0==e.Code&&($.alert(e.Info.Msg),i.onLoad())}))},fail:function(e){}})}))}},openCard:function(){},load:function(){var e=this,t={user_id:this.userInfo.user_id};e.$.request({url:this.Config.URL.user.signState,data:t,success:function(t,i,a,n){e.setData({isSign:250==i?1:0})}}),e.setData({message:this.userInfo}),e.message.user_level_rate=e.plantformInfo.user_level_rate_map[this.userInfo.user_level_id]},click:function(){var e=this;e.$.navigateTo({url:"/member/member/sign"})},cardDetail:function(e){wx.navigateTo({url:"../vip_ind/vip_ind?cardid="+this.member.Id})},cardvip:function(e){wx.navigateTo({url:"/member/member/card"})},copyId:function(e){uni.setClipboardData({data:e,complete:function(){uni.showToast({title:"已复制到剪贴板"})}})}})};t.default=l}}]);