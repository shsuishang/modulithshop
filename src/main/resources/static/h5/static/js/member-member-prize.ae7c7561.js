(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["member-member-prize"],{"4825f":function(t,i,e){"use strict";e.r(i);var a=e("48b1"),n=e("daff");for(var s in n)["default"].indexOf(s)<0&&function(t){e.d(i,t,(function(){return n[t]}))}(s);e("91d5");var r=e("828b"),o=Object(r["a"])(n["default"],a["b"],a["c"],!1,null,"5961a8e2",null,!1,a["a"],void 0);i["default"]=o.exports},"48b1":function(t,i,e){"use strict";e.d(i,"b",(function(){return a})),e.d(i,"c",(function(){return n})),e.d(i,"a",(function(){}));var a=function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("v-uni-view",{staticClass:"page"},[t.Prize.length>0?t._l(t.Prize,(function(i,a){return e("v-uni-view",{key:a,staticClass:"m-myprice-item"},[e("v-uni-image",{attrs:{"lazy-load":!0,src:i.awards_image}}),e("v-uni-view",{staticClass:"price-title"},[t._v(t._s(i.awards_name))]),e("v-uni-view",{staticClass:"price-time"},[t._v(t._s(i.alh_datetime))]),e("v-uni-view",{class:"price-btn "+(i.alh_is_send?"gray":"red"),attrs:{"data-id":i.alh_id,"data-activity_id":i.activity_id,"data-alh_is_send":i.alh_is_send,"data-alh_item_id":i.alh_item_id},on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.buttonclicked.apply(void 0,arguments)}}},[t._v(t._s(i.alh_is_send?t.__("已经发奖"):t.__("去领奖品")))])],1)})):e("v-uni-view",{staticClass:"m-nullcontent"},[e("v-uni-view",{staticClass:"m-nullpage-middle"},[e("v-uni-label",{staticClass:"iconfont icon-meiyougengduo"}),e("v-uni-view",{staticClass:"m-null-tip"},[e("v-uni-text",[t._v(t._s(t.__("亲~什么都没有")))]),e("v-uni-text",[t._v(t._s(t.sprintf(t.__("没有%s中的奖品，快去%s吧~~"),t.tip1,t.tip2)))])],1)],1)],1)],2)},n=[]},"4e8f":function(t,i,e){var a=e("c86c");i=a(!1),i.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */[data-v-5961a8e2]:export{theme_bg:#e93323}.m-myprice-item[data-v-5961a8e2]{width:100%;height:%?200?%;background:#fff;padding:%?30?%;box-sizing:border-box;position:relative;border-bottom:%?1?% solid #d3d3d3}.m-myprice-item uni-image[data-v-5961a8e2]{height:%?140?%;width:%?140?%;box-sizing:border-box;position:absolute;top:%?30?%;left:%?30?%}.price-title[data-v-5961a8e2]{font-size:%?30?%;position:absolute;left:%?200?%;top:%?30?%}.price-time[data-v-5961a8e2]{position:absolute;left:%?200?%;bottom:%?30?%;line-height:%?50?%;font-size:%?24?%;color:grey}.price-btn[data-v-5961a8e2]{width:%?150?%;height:%?50?%;color:#fff;position:absolute;bottom:%?30?%;right:%?30?%;font-size:%?26?%;line-height:%?50?%;text-align:center;border-radius:%?8?%}.red[data-v-5961a8e2]{background:#e93323}.gray[data-v-5961a8e2]{background:#ccc}',""]),t.exports=i},"8bff":function(t,i,e){var a=e("4e8f");a.__esModule&&(a=a.default),"string"===typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);var n=e("967d").default;n("042f3e2c",a,!0,{sourceMap:!1,shadowMode:!1})},"91d5":function(t,i,e){"use strict";var a=e("8bff"),n=e.n(a);n.a},"94ab":function(t,i,e){"use strict";e("6a54");var a=e("f5bd").default;Object.defineProperty(i,"__esModule",{value:!0}),i.default=void 0;var n=a(e("9b1b")),s=e("8f59"),r={data:function(){return{Prize:[],Category:0,tip1:"",tip2:"",isLuckDraw:!0,isGoldenEgg:!0}},computed:(0,s.mapState)(["Config","StateCode","notice","plantformInfo","shopInfo","userInfo","hasLogin"]),onLoad:function(t){uni.setNavigationBarTitle({title:this.__("我的奖品")});this.shopInfo.VendorFeatureSet;this.setData({Category:t.category}),1==t.category?(this.setData({isGoldenEgg:!1}),this.setData({isLuckDraw:!0}),this.$.setNavigationBarTitle({title:"幸运大抽奖-我的奖品"}),this.setData({tip1:"抽",tip2:"抽奖"})):(this.setData({isGoldenEgg:!0}),this.setData({isLuckDraw:!1}),this.$.setNavigationBarTitle({title:"幸运砸金蛋-我的奖品"}),this.setData({tip1:"砸",tip2:"砸金蛋"}))},onShow:function(){(this.isGoldenEgg||this.isLuckDraw)&&this.getPrizelist()},methods:(0,n.default)((0,n.default)({},(0,s.mapMutations)(["login","logout","getPlantformInfo","forceUserInfo","getUserInfo"])),{},{getPrizelist:function(){var t={activity_type:this.Category,activity_type_id:this.StateCode.ACTIVITY_TYPE_LOTTERY,store_id:this.shopInfo.store_id},i=this;i.$.request({url:i.Config.URL.user.listsLotteryHistory,data:t,success:function(t,e,a,n){200==e?i.setData({Prize:t.items}):i.$.alert(a)}})},buttonclicked:function(t){"0"==t.target.dataset.alh_is_send&&this.$.navigateTo({url:"/member/member/receiveprize?id="+t.target.dataset.id+"&activity_id="+t.target.dataset.activity_id+"&uniqueid="+t.target.dataset.uniqueid})}})};i.default=r},daff:function(t,i,e){"use strict";e.r(i);var a=e("94ab"),n=e.n(a);for(var s in a)["default"].indexOf(s)<0&&function(t){e.d(i,t,(function(){return a[t]}))}(s);i["default"]=n.a}}]);