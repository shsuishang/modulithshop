(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["edu-course-exam-chapter-test"],{"38cb":function(t,e,a){var n=a("c86c");e=n(!1),e.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */uni-page-body[data-v-6c96a63e]{background-color:#fff!important;height:100%!important}body.?%PAGE?%[data-v-6c96a63e]{background-color:#fff!important}.page_bg[data-v-6c96a63e]{background-color:#fff!important;height:100%!important}.box[data-v-6c96a63e]{height:68%!important}.exam-title[data-v-6c96a63e]{margin:%?0?% %?10?%;font-size:%?32?%;font-family:PingFangSC-Medium,PingFang SC;font-weight:700;color:#333;line-height:%?45?%}.exam-context[data-v-6c96a63e]{margin:%?10?%;font-size:%?32?%;font-family:PingFangSC-Regular,PingFang SC;font-weight:400;color:#666;line-height:%?45?%}.star-practice[data-v-6c96a63e]{font-size:%?32?%;line-height:%?90?%;text-align:center;font-weight:500;color:#fff;width:90%;margin-left:5%;margin-right:5%;height:%?90?%;background:linear-gradient(90deg,#ff6161,#ff9661);border-radius:%?45?%}',""]),t.exports=e},"46b3":function(t,e,a){"use strict";a.d(e,"b",(function(){return n})),a.d(e,"c",(function(){return i})),a.d(e,"a",(function(){}));var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"page_bg"},[a("hr",{staticStyle:{"background-color":"#F0F0F0",height:"1px",border:"none"}}),a("v-uni-view",{staticStyle:{"margin-left":"20rpx","margin-top":"20rpx",height:"20%"}},[a("v-uni-view",{staticClass:"exam-title"},[t._v("考试内容：")]),a("v-uni-view",{staticClass:"exam-context"},[t._v(t._s(t.examInfo.exam_name))]),a("v-uni-view",{staticClass:"exam-title"},[t._v("考试时间：")]),a("v-uni-view",{staticClass:"exam-context"},[t._v(t._s(t.exam_long_time_str))])],1),a("v-uni-view",{staticClass:"box"}),a("v-uni-view",{staticClass:"star-practice",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.starPractice.apply(void 0,arguments)}}},[t._v("开始考试")]),a("u-toast",{ref:"uToast"})],1)},i=[]},5031:function(t,e,a){"use strict";a.r(e);var n=a("dbcc"),i=a.n(n);for(var r in n)["default"].indexOf(r)<0&&function(t){a.d(e,t,(function(){return n[t]}))}(r);e["default"]=i.a},"5c32":function(t,e,a){"use strict";var n=a("f3f6"),i=a.n(n);i.a},"5ded":function(t,e,a){"use strict";a.r(e);var n=a("46b3"),i=a("5031");for(var r in i)["default"].indexOf(r)<0&&function(t){a.d(e,t,(function(){return i[t]}))}(r);a("5c32");var o=a("828b"),c=Object(o["a"])(i["default"],n["b"],n["c"],!1,null,"6c96a63e",null,!1,n["a"],void 0);e["default"]=c.exports},dbcc:function(t,e,a){"use strict";a("6a54");var n=a("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0,a("5c47"),a("a1c1"),a("0506");var i,r=n(a("9b1b")),o=a("8f59"),c=n(a("80c5")),d=n(a("4225")),s=n(a("0c0f")),u=n(a("6a29")),f=n(a("9885")),l=n(a("79da")),m=n(a("6c0e")),p=n(a("5b28")),_={components:{uSubsection:d.default,zPaging:c.default,uTabs:s.default,uBadge:u.default,uButton:f.default,uIcon:l.default,uToast:m.default,uOverlay:p.default},data:function(){return{exam_start_date:"",exam_end_date:"",exam_long_time:"",exam_long_time_str:"",product_id:0,classes_id:0,timeData:{},treeDataF:[],examInfo:{},collHeadStyle:{padding:"28rpx 20rpx"},itemStyle:{borderBottom:"2rpx solid #f9f9f9"},show:!1,leftSelect:0}},onLoad:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};i=this,i.product_id=t.product_id,i.classes_id=t.classes_id,i.init(t)},onReady:function(){},onShow:function(){},onHide:function(){},computed:(0,r.default)((0,r.default)({},(0,o.mapState)(["Config"])),{},{doExamShow:function(){return function(t){return"开始做题"}}}),methods:{init:function(t){i.getExamList()},timeDifference:function(t,e){var a=/\d{4}-\d{1,2}-\d{1,2} /;t=new Date((a.test(t)?t:"2018-1-1 "+t).replace(/-/g,"/")),e=new Date((a.test(e)?e:"2018-1-1 "+e).replace(/-/g,"/"));var n=e.getTime()-t.getTime(),r=n/1e3/60;i.exam_long_time=r;var o=Math.floor(r/60),c=Math.floor(r%60),d="";o>0&&(d+=o+"小时"),c>0&&(d+=c+"分钟"),r>0&&o<=0&&c<=0&&(d+="小于1分钟"),i.exam_long_time_str=d},getExamList:function(t,e){i.$.request({url:i.Config.URL.edu.getExamByClassesId,data:{pageNum:t,pageSize:e,classes_id:i.classes_id},dataType:"json",success:function(t,e,a,n){i.examInfo=t.data,i.exam_start_date=t.exam_start_date,i.exam_end_date=t.exam_end_date,i.timeDifference(i.exam_start_date,i.exam_end_date)}})},screen:function(t){var e=this;this.show=!1,this.$nextTick((function(){e.topic_category_id=t.topic_category_id,e.topic_category_name=e.treeDataF[e.leftSelect].topic_category_name+t.topic_category_name,i.$refs.paging.reload()}))},starPractice:function(){uni.navigateTo({url:"/edu/course/exam/practice-page?&paper_id="+this.examInfo.paper_id+"&paper_name="+this.examInfo.paper_name+"&exam_id="+this.examInfo.exam_id+"&course_id="+i.product_id+"&exam_long_time="+this.exam_long_time})}},onUnload:function(){uni.$off("chapterUpdate")}};e.default=_},f3f6:function(t,e,a){var n=a("38cb");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var i=a("967d").default;i("77f934a2",n,!0,{sourceMap:!1,shadowMode:!1})}}]);