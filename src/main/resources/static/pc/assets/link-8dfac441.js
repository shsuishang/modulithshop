import{az as m,aB as y,P as u,aL as h,a7 as v,p as a,v as i,I as r,z as b,ak as E,aR as o,J as C,E as t,G as c,b6 as d,aU as _,a_ as B}from"./index-21964c78.js";const g=m({type:{type:String,values:["primary","success","warning","info","danger","default"],default:"default"},underline:{type:Boolean,default:!0},disabled:{type:Boolean,default:!1},href:{type:String,default:""},icon:{type:y}}),w={click:l=>l instanceof MouseEvent},L=["href"],P=u({name:"ElLink"}),$=u({...P,props:g,emits:w,setup(l,{emit:p}){const s=l,n=h("link"),f=v(()=>[n.b(),n.m(s.type),n.is("disabled",s.disabled),n.is("underline",s.underline&&!s.disabled)]);function k(e){s.disabled||p("click",e)}return(e,I)=>(a(),i("a",{class:c(o(f)),href:e.disabled||!e.href?void 0:e.href,onClick:k},[e.icon?(a(),r(o(C),{key:0},{default:b(()=>[(a(),r(E(e.icon)))]),_:1})):t("v-if",!0),e.$slots.default?(a(),i("span",{key:1,class:c(o(n).e("inner"))},[d(e.$slots,"default")],2)):t("v-if",!0),e.$slots.icon?d(e.$slots,"icon",{key:2}):t("v-if",!0)],10,L))}});var z=_($,[["__file","/home/runner/work/element-plus/element-plus/packages/components/link/src/link.vue"]]);const N=B(z);export{N as E};