(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["member-member-logout"],{1703:function(t,e,a){"use strict";a.r(e);var n=a("6026"),i=a.n(n);for(var s in n)["default"].indexOf(s)<0&&function(t){a.d(e,t,(function(){return n[t]}))}(s);e["default"]=i.a},4535:function(t,e,a){var n=a("fd56");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var i=a("967d").default;i("407c7067",n,!0,{sourceMap:!1,shadowMode:!1})},"54e5":function(t,e,a){"use strict";a.r(e);var n=a("cfef"),i=a("1703");for(var s in i)["default"].indexOf(s)<0&&function(t){a.d(e,t,(function(){return i[t]}))}(s);a("6ad7");var r=a("828b"),l=Object(r["a"])(i["default"],n["b"],n["c"],!1,null,"61c967f2",null,!1,n["a"],void 0);e["default"]=l.exports},6026:function(t,e,a){"use strict";a("6a54");var n=a("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var i=n(a("9b1b")),s=a("8f59"),r={data:function(){return{remark:"",showAgree:!1,remarkLength:0,wechat_id:"",Email:"",platform:"",categoryId:""}},computed:(0,s.mapState)(["Config","StateCode","notice","plantformInfo","shopInfo","userInfo","hasLogin"]),onLoad:function(t){uni.setNavigationBarTitle({title:this.__("账号注销")});this.setData({Email:this.$.isNull(this.userInfo.user_email)?"":this.userInfo.user_email,categoryId:t.id?t.id:1});try{var e=this.$.getSystemInfoSync();this.setData({platform:e.platform})}catch(e){}},methods:(0,i.default)((0,i.default)({},(0,s.mapMutations)(["login","logout","getPlantformInfo","forceUserInfo","getUserInfo"])),{},{inputwechat:function(t){this.setData({wechat_id:t.detail.value})},inputemail:function(t){this.setData({Email:t.detail.value})},inputRemark:function(t){this.setData({remark:t.detail.value,remarkLength:t.detail.value.length})},isShowAgree:function(){this.showAgree=!this.showAgree},submitdata:function(){var t=this;if(t.$.isNull(this.remark))t.$.alert(t.__("请输入您宝贵的意见"));else{if(!this.showAgree)return t.$.confirm(t.__("请先阅读并同意《用户注销协议》")),void uni.showToast({icon:"none",title:t.__("请先阅读并同意《用户注销协议》")});var e={MiniAppVersion:"",Wechat:this.wechat_id,feedback_question:this.remark,Platform:this.platform,Email:this.Email,feedback_category_id:t.categoryId};t.$.request({url:this.Config.URL.user.feedback_add,data:e,method:"POST",success:function(e,a,n,i){200==a?(t.$.alert(t.__("提交成功！")),setTimeout((function(){t.$.navigateBack(1,(function(){}))}),1e3)):t.$.alert(n)}})}}})};e.default=r},"6ad7":function(t,e,a){"use strict";var n=a("4535"),i=a.n(n);i.a},cfef:function(t,e,a){"use strict";a.d(e,"b",(function(){return n})),a.d(e,"c",(function(){return i})),a.d(e,"a",(function(){}));var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"page"},[a("v-uni-view",{staticClass:"feedback-box"},[a("v-uni-view",{staticClass:"m-cells m-cells-form"},[a("v-uni-view",{staticClass:"m-cell"},[a("v-uni-view",{staticClass:"m-cell-bd"},[a("v-uni-textarea",{staticClass:"u-textarea",staticStyle:{height:"300rpx"},attrs:{maxlength:"500",placeholder:t.__("请填写账号注销申请理由，账号注销申请通过前，您仍能使用该账号登录。")},on:{input:function(e){arguments[0]=e=t.$handleEvent(e),t.inputRemark.apply(void 0,arguments)}}}),a("v-uni-view",{staticClass:"u-textarea-counter"},[a("v-uni-label",[t._v(t._s(t.remarkLength))]),t._v("/500")],1)],1)],1)],1),a("v-uni-view",{staticClass:"m-cells m-cells-form",staticStyle:{"margin-top":"80rpx"},attrs:{hidden:"true"}},[a("v-uni-view",{staticClass:"m-cell"},[a("v-uni-view",{staticClass:"m-cell-hd"},[a("v-uni-label",{staticClass:"u-label"},[t._v(t._s(t.__("微信号")))])],1),a("v-uni-view",{staticClass:"m-cell-bd"},[a("v-uni-input",{staticClass:"u-input",attrs:{type:"text",placeholder:t.__("请输微信号(可选)")},on:{input:function(e){arguments[0]=e=t.$handleEvent(e),t.inputwechat.apply(void 0,arguments)}}})],1)],1),a("v-uni-view",{staticClass:"m-cell",staticStyle:{"border-top":"1px solid #eee"}},[a("v-uni-view",{staticClass:"m-cell-hd"},[a("v-uni-label",{staticClass:"u-label"},[t._v(t._s(t.__("邮箱")))])],1),a("v-uni-view",{staticClass:"m-cell-bd"},[a("v-uni-input",{staticClass:"u-input",attrs:{type:"text",value:t.Email,placeholder:t.__("请输入邮箱地址(可选)"),maxlength:"30"},on:{input:function(e){arguments[0]=e=t.$handleEvent(e),t.inputemail.apply(void 0,arguments)}}})],1)],1)],1),a("v-uni-view",{staticClass:"btn_box",attrs:{bind:"submitdata"}},[a("v-uni-view",{staticStyle:{"font-size":"24upx",display:"flex","align-items":"center","justify-content":"center"}},[a("v-uni-text",{class:t.showAgree?"cuIcon-radiobox":"cuIcon-round",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.isShowAgree.apply(void 0,arguments)}}}),a("v-uni-text",{staticStyle:{margin:"0 0 0 2px"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.isShowAgree.apply(void 0,arguments)}}},[t._v(t._s(t.__("我已阅读并知晓了")))]),a("v-uni-navigator",{staticStyle:{color:"#e8152d"},attrs:{url:"/member/member/protocol?id=cancellation_agreement","open-type":"navigate"}},[t._v(t._s(t.__("《用户注销协议》")))])],1),a("v-uni-button",{staticClass:"u-btn u-btn-default",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.submitdata.apply(void 0,arguments)}}},[t._v(t._s(t.__("提交申请")))])],1)],1)],1)},i=[]},fd56:function(t,e,a){var n=a("c86c");e=n(!1),e.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */[data-v-61c967f2]:export{theme_bg:#e93323}.feedback-box[data-v-61c967f2]{padding:%?30?%}.m-cells[data-v-61c967f2]::before{border:none}.m-cells[data-v-61c967f2]::after{border:none}.m-cell[data-v-61c967f2]::before{border:none}.btn_box[data-v-61c967f2]{margin-top:%?50?%}',""]),t.exports=e}}]);