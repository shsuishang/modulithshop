import{a4 as R,s as A,ag as P,_ as I,Z as S,e as U,u as w,f as D,r as T,i as H,t as z,dF as q,T as m,dG as G,dH as B,$ as y,dI as j,n as J,p as u,v,x as a,A as l,y as n,z as d,G as F,F as K,C as O,I as L,E as N,K as Z,D as h,J as Q,L as W}from"./index-21964c78.js";/* empty css              */import{E as X}from"./overlay-b290a267.js";import{E as Y}from"./avatar-df481c73.js";import{E as x}from"./pagination-85e496af.js";import{E as ee}from"./tag-b8b29d1f.js";import"./select-ed2293fb.js";import"./scrollbar-c1d26490.js";import"./popper-251c4401.js";import{E as se}from"./image-viewer-dd6920ac.js";/* empty css               *//* empty css             */import{S as te}from"./index-f12e260f.js";import{f as $}from"./helper-0ec263a7.js";import{_ as ae}from"./_plugin-vue_export-helper-c27b6911.js";import"./refs-1278d799.js";import"./isUndefined-aa0326a0.js";import"./isEqual-3f4e7b62.js";import"./_Uint8Array-4a48d71f.js";import"./strings-b80b2625.js";import"./debounce-6ea0a711.js";import"./_baseIteratee-8f2ec951.js";import"./hasIn-54cf4aec.js";import"./index-a2bc563c.js";import"./dayjs.min-8a7592b2.js";const oe={name:"lists",components:{Plus:R,Search:A,Edit:P,Delete:I,EditPen:S},setup(){const s=U();w();let e=D({readNum:0,unreadNum:0,messageLists:[],outerVisible:!1,replyVisible:!1,message:{},message_content:"",user_nickname:""}),g=D({message_type:T("0"),message_is_read:1,page:parseInt(s.query.page)||1,size:parseInt(s.query.size)||10,total:1,sidx:"message_time",sord:"DESC"}),i=()=>{q(g).then(o=>{o.status==200?(e.messageLists=o.data.items,e.messageLists&&e.messageLists.forEach(r=>{r.message_time&&(r.message_time=$(r.message_time))}),g.total=o.data.records,E()):m.error(o.msg)})},E=()=>{G({}).then(o=>{o.status==200?(e.unreadNum=o.data.unread_number,e.readNum=o.data.red_number):m.error(o.msg)})},V=o=>{i()},p=o=>{g.message_is_read=o,i()};const _=()=>{B({isAll:1}).then(o=>{o.status==200?(i(),m.success(y("操作成功!"))):m.error(o.msg)})},c=o=>{e.message=e.messageLists[o],e.outerVisible=!0,e.message.message_is_read||B({message_id:e.message.message_id}).then(f=>{f.status==200&&i()})},k=o=>{e.replyVisible=!0,e.user_nickname=o.user_other_nickname},b=o=>{if(!e.message.user_other_id){m.error(y("接收者信息错误"));return}if(!e.message_content){m.error(y("请输入消息内容"));return}j({user_other_id:e.message.user_other_id,message_content:e.message_content}).then(r=>{r.status==200?(m.success(y("操作成功!")),e.replyVisible=!1,e.message_content=""):m.error(r.msg)})};return H(()=>{i()}),{StateCode:te,getLists:i,checkDatatype:V,messageRead:p,allAead:_,shoeMessage:c,addReply:b,formatDateOneTime:$,getMessNum:E,showReply:k,...z(e),...z(g)}}};const ie={class:"right-title-box"},le={class:"first-title"},ne={class:"btn-box"},re={class:"little-title-sty"},me=["onClick"],de={style:{position:"relative"}},ue={style:{width:"90%",position:"relative"}},ge={class:"msg-type-sty"},pe={class:"msg-type-span-left"},_e={class:"msg-type-span-right"},ce={class:"msg-info-sty"},fe={key:0,style:{position:"absolute",bottom:"10px",right:"10px"}},ye={class:"sortPagiBar"},ve={class:"bottom-pagination"},he={class:"block",style:{display:"flex","align-items":"center"}},Ee={style:{padding:"0 20px"}},ke={style:{padding:"20px 0"}},be=["innerHTML"];function Ve(s,e,g,i,E,V){const p=J("EditPen"),_=Q,c=Z,k=se,b=ee,o=x,r=Y,f=X,M=W;return u(),v("div",null,[a("div",ie,[a("div",le,l(s.$t("消息中心")),1),a("div",ne,[n(c,{class:"button-clean-sty",size:"small",onClick:i.allAead},{default:d(()=>[n(_,null,{default:d(()=>[n(p)]),_:1}),h(" "+l(s.$t("全部已读")),1)]),_:1},8,["onClick"])])]),a("div",re,[a("span",{class:F(s.message_is_read==1?"check":""),onClick:e[0]||(e[0]=t=>i.messageRead(1))},l(s.$t("已读"))+"("+l(s.readNum)+")",3),a("span",{class:F(s.message_is_read==0?"check":""),onClick:e[1]||(e[1]=t=>i.messageRead(0)),style:{"margin-left":"23px"}},l(s.$t("未读"))+"("+l(s.unreadNum)+")",3)]),(u(!0),v(K,null,O(s.messageLists,(t,C)=>(u(),v("div",{class:"msg-list-style",key:C,onClick:Ce=>i.shoeMessage(C)},[a("div",de,[n(k,{style:{"border-radius":"50%"},src:t.user_other_avatar,fit:"cover",class:"msg-change-img",alt:t.user_other_avatar},null,8,["src","alt"])]),a("div",ue,[a("div",ge,[a("div",pe,l(t.user_other_nickname),1),a("div",_e,l(t.message_time),1)]),a("div",ce,l(t.message_content),1),t.message_is_read?N("",!0):(u(),v("div",fe,[n(b,{type:"danger",size:"small"},{default:d(()=>[h(l(s.$t("未读")),1)]),_:1})]))])],8,me))),128)),a("div",ye,[a("div",ve,[a("nav",null,[(u(),L(o,{currentPage:s.page,"onUpdate:currentPage":e[2]||(e[2]=t=>s.page=t),"page-size":s.size,"onUpdate:pageSize":e[3]||(e[3]=t=>s.size=t),"page-sizes":[10,20,30,40],small:!1,disabled:!1,background:!0,layout:"prev,pager,next,total,jumper",total:s.total,key:s.page,onSizeChange:e[4]||(e[4]=t=>{s.size=t,i.getLists()}),onCurrentChange:e[5]||(e[5]=t=>{s.page=t,i.getLists()})},null,8,["currentPage","page-size","total"]))])])]),n(f,{modelValue:s.outerVisible,"onUpdate:modelValue":e[7]||(e[7]=t=>s.outerVisible=t),title:s.$t("消息")},{default:d(()=>[a("div",null,[a("div",he,[n(r,{size:50,src:s.message.user_other_avatar},null,8,["src"]),a("span",Ee,l(s.message.user_other_nickname),1),a("span",null,l(s.message.message_time),1),a("div",{class:"reply-view",onClick:e[6]||(e[6]=t=>i.showReply(s.message))},[n(_,null,{default:d(()=>[n(p)]),_:1}),h(l(s.$t("回复")),1)])]),a("div",ke,[a("p",{innerHTML:s.message.message_content},null,8,be)])])]),_:1},8,["modelValue","title"]),s.replyVisible?(u(),L(f,{key:0,modelValue:s.replyVisible,"onUpdate:modelValue":e[9]||(e[9]=t=>s.replyVisible=t),title:s.$t("回复")},{default:d(()=>[a("div",null,[n(M,{modelValue:s.message_content,"onUpdate:modelValue":e[8]||(e[8]=t=>s.message_content=t),rows:2,type:"textarea",placeholder:s.$t("请输入短消息")},null,8,["modelValue","placeholder"]),n(c,{style:{"margin-top":"20px"},type:"primary",size:"small",onClick:i.addReply},{default:d(()=>[h(l(s.$t("确定")),1)]),_:1},8,["onClick"])])]),_:1},8,["modelValue","title"])):N("",!0)])}const We=ae(oe,[["render",Ve],["__scopeId","data-v-939388ef"]]);export{We as default};