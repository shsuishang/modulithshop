import{aC as h,aF as o,bl as n,Q as m,bG as g,a7 as c,aO as $,aJ as p,c2 as N,p as _,I as C,z as j,ba as x,G as O,aU as f,H as v,an as E,aX as k,b1 as w}from"./index-78223f15.js";import{r as S}from"./constants-2604e35b.js";const B=h({tag:{type:String,default:"div"},span:{type:Number,default:24},offset:{type:Number,default:0},pull:{type:Number,default:0},push:{type:Number,default:0},xs:{type:o([Number,Object]),default:()=>n({})},sm:{type:o([Number,Object]),default:()=>n({})},md:{type:o([Number,Object]),default:()=>n({})},lg:{type:o([Number,Object]),default:()=>n({})},xl:{type:o([Number,Object]),default:()=>n({})}}),G=m({name:"ElCol"}),I=m({...G,props:B,setup(b){const t=b,{gutter:u}=g(S,{gutter:c(()=>0)}),a=$("col"),d=c(()=>{const e={};return u.value&&(e.paddingLeft=e.paddingRight=`${u.value/2}px`),e}),i=c(()=>{const e=[];return["span","offset","pull","push"].forEach(s=>{const l=t[s];p(l)&&(s==="span"?e.push(a.b(`${t[s]}`)):l>0&&e.push(a.b(`${s}-${t[s]}`)))}),["xs","sm","md","lg","xl"].forEach(s=>{p(t[s])?e.push(a.b(`${s}-${t[s]}`)):N(t[s])&&Object.entries(t[s]).forEach(([l,r])=>{e.push(l!=="span"?a.b(`${s}-${l}-${r}`):a.b(`${s}-${r}`))})}),u.value&&e.push(a.is("guttered")),[a.b(),e]});return(e,y)=>(_(),C(E(e.tag),{class:O(f(i)),style:v(f(d))},{default:j(()=>[x(e.$slots,"default")]),_:3},8,["class","style"]))}});var K=k(I,[["__file","/home/runner/work/element-plus/element-plus/packages/components/col/src/col.vue"]]);const H=w(K);export{H as E};