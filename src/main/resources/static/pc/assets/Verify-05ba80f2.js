import{db as y,dw as w,r as C,f as X,$ as g,i as k,bd as M,t as E,e1 as S,n as m,p as c,v as B,x as l,H as h,A as L,I as v,z as P,y as R,J as V}from"./index-21964c78.js";/* empty css             */import{_ as W}from"./_plugin-vue_export-helper-c27b6911.js";const u={components:{DArrowRight:y,CircleCheck:w},props:{isPassing:{type:Boolean,default:!1},handlerWidth:{type:Number,default:50}},emits:["update:isPassing"],setup(t,{emit:n}){const a=C(),e=X({isMoving:!1,x:0,width:0,currentX:0,textColor:"#8a8989",textMes:g("按住滑块向右拖动")});k(()=>{e.width=a.value.offsetWidth});const f=()=>{n("update",!0),e.isMoving=!1,e.textColor="#fff",e.textMes=g("验证成功！")},_=()=>{e.currentX=0},o=s=>{if(e.isMoving&&!t.isPassing){const i=(s.pageX||s.touches[0].pageX)-e.x;i>0&&i<=e.width-t.handlerWidth&&(e.currentX=i),i>=e.width-t.handlerWidth-10&&(e.currentX=e.width-t.handlerWidth,f())}},r=()=>{e.isMoving&&!t.isPassing&&(e.isMoving=!1,t.isPassing||_())},d=s=>{t.isPassing||(e.isMoving=!0,e.x=s.pageX||s.touches[0].pageX,document.addEventListener("mouseover",o),document.addEventListener("touchmove",o),document.addEventListener("mouseup",r),document.addEventListener("touchend",r))};return M(()=>{document.removeEventListener("mouseover",o),document.removeEventListener("touchmove",o),document.removeEventListener("mouseup",r),document.removeEventListener("touchend",r)}),{...E(e),slidingBlockRef:a,dragStart:d}}},p=()=>{S(t=>({"6b1d6441":t.textColor}))},x=u.setup;u.setup=x?(t,n)=>(p(),x(t,n)):p;const b={class:"sliding-block",ref:"slidingBlockRef"},z={class:"text"};function A(t,n,a,e,f,_){const o=m("DArrowRight"),r=V,d=m("CircleCheck");return c(),B("div",b,[l("div",{class:"success",style:h(`transform: translateX(calc(-100% + ${t.currentX}px));`)},null,4),l("span",z,L(t.$t(t.textMes)),1),l("div",{class:"sliding flex flex-center",style:h(`width:${a.handlerWidth}px;transform: translateX(${t.currentX}px)`),onMousedown:n[0]||(n[0]=(...s)=>e.dragStart&&e.dragStart(...s)),onTouchstart:n[1]||(n[1]=(...s)=>e.dragStart&&e.dragStart(...s))},[a.isPassing?(c(),v(d,{key:1,style:{color:"#52c41a","font-size":"12px"}})):(c(),v(r,{key:0,style:{"vertical-align":"middle","font-size":"16px"}},{default:P(()=>[R(o)]),_:1}))],36)],512)}const N=W(u,[["render",A],["__scopeId","data-v-d1dfe2cf"]]);export{N as _};