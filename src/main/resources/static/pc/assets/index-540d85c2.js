import{P as d,bc as u,r as y,bd as h,w as f,be as a,g as m,f as _,t as g,n as k,p as w,I as A,z as b,x as c}from"./index-21964c78.js";import{_ as v}from"./_plugin-vue_export-helper-c27b6911.js";function l(t,e){const n=a?t.type:t.tag,s=n==="html"?"htmlAttrs":n==="body"?"bodyAttrs":n;if(typeof s!="string"||!(s in e))return;const o=a?t:t.data,i=(a?t.props:o.attrs)||{};if(a||(o.staticClass&&(i.class=o.staticClass),o.staticStyle&&(i.style=Object.entries(o.staticStyle).map(([r,p])=>`${r}:${p}`).join(";"))),t.children){const r=a?"children":"text";i.children=Array.isArray(t.children)?t.children[0][r]:t[r]}Array.isArray(e[s])?e[s].push(i):s==="title"?e.title=i.children:e[s]=i}function H(t){const e={title:void 0,htmlAttrs:void 0,bodyAttrs:void 0,base:void 0,meta:[],link:[],style:[],script:[],noscript:[]};for(const n of t)if(typeof n.type=="symbol"&&Array.isArray(n.children))for(const s of n.children)l(s,e);else l(n,e);return e}const S=d({name:"Head",setup(t,{slots:e}){const n=u(),s=y({}),o=n.push(s);return h(()=>{o.dispose()}),()=>(f(()=>{e.default&&o.patch(H(e.default()))}),null)}}),$=d({props:{keywords:{type:String,default:""},description:{type:String,default:""},title:{type:String,default:""}},components:{Head:S},setup(t){const{info:e}=m();localStorage.getItem("language");let n=_({keywords:"",description:"",title:""});return f(()=>{n.keywords=e.site_meta_keyword,n.description=e.site_meta_description,t.keywords&&(n.keywords=t.keywords),t.description&&(n.description=t.description),t.title&&(document.title=t.title)}),{info:e,...g(n)}}}),C=["content"],B=["content"],j=["href"],x=["lang"];function N(t,e,n,s,o,i){const r=k("Head");return w(),A(r,null,{default:b(()=>[c("meta",{name:"description",content:t.description},null,8,C),c("meta",{name:"Keywords",content:t.keywords},null,8,B),c("link",{rel:"shortcut icon",href:t.info.site_icon,type:"image/x-icon"},null,8,j),c("html",{lang:t.language,class:"theme-dark"},null,8,x)]),_:1})}const V=v($,[["render",N]]);export{V as _};