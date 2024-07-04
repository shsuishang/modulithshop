//模拟数据
var  emojiData = {
			"imgArr" : [
				{
				emojiName:"",
				emojiSort:0,
				minEmoji:false,
				emojiPath:"",
				emojiList: [
					  [
						{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
					    { url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
					 ],
					 [
						{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
					    { url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
					 ],
					 [
						{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
					    { url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
					 ]
					]
				},
				{
					// QQ 表情
					emojiName:"QQemoji",
					emojiSort:1,
					minEmoji:true,
					emojiPath:"https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/",
					// emojiPath: "static/img/qq/",
					emojiList: [
							[
								{ url: '0.gif', alt: '[微笑]' },
								{ url: '1.gif', alt: '[撇嘴]' },
								{ url: '2.gif', alt: '[色]' },
								{ url: '3.gif', alt: '[发呆]' },
								{ url: '4.gif', alt: '[得意]' },
								{ url: '5.gif', alt: '[流泪]' },
								{ url: '6.gif', alt: '[害羞]' },
								{ url: '7.gif', alt: '[闭嘴]' },
								{ url: '8.gif', alt: '[睡]' },
								{ url: '9.gif', alt: '[大哭]' },
								{ url: '10.gif', alt: '[尴尬]' },
								{ url: '11.gif', alt: '[发怒]' },
								{ url: '12.gif', alt: '[调皮]' },
								{ url: '13.gif', alt: '[呲牙]' },
								{ url: '14.gif', alt: '[惊讶]' },
								{ url: '15.gif', alt: '[难过]' },
								{ url: '16.gif', alt: '[酷]' },
								{ url: '17.gif', alt: '[冷汗]' },
								{ url: '18.gif', alt: '[抓狂]' },
								{ url: '19.gif', alt: '[吐]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }

							],
							[
								{ url: '20.gif', alt: '[偷笑]' },
								{ url: '21.gif', alt: '[愉快]' },
								{ url: '22.gif', alt: '[白眼]' },
								{ url: '23.gif', alt: '[傲慢]' },
								{ url: '24.gif', alt: '[饥饿]' },
								{ url: '25.gif', alt: '[困]' },
								{ url: '26.gif', alt: '[惊恐]' },
								{ url: '27.gif', alt: '[流汗]' },
								{ url: '28.gif', alt: '[憨笑]' },
								{ url: '29.gif', alt: '[悠闲]' },
								{ url: '30.gif', alt: '[奋斗]' },
								{ url: '31.gif', alt: '[咒骂]' },
								{ url: '32.gif', alt: '[疑问]' },
								{ url: '33.gif', alt: '[嘘]' },
								{ url: '34.gif', alt: '[晕]' },
								{ url: '35.gif', alt: '[疯了]' },
								{ url: '36.gif', alt: '[衰]' },
								{ url: '37.gif', alt: '[骷髅]' },
								{ url: '38.gif', alt: '[敲打]' },
								{ url: '39.gif', alt: '[再见]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '40.gif', alt: '[擦汗]' },
								{ url: '41.gif', alt: '[抠鼻]' },
								{ url: '42.gif', alt: '[鼓掌]' },
								{ url: '43.gif', alt: '[糗大了]' },
								{ url: '44.gif', alt: '[坏笔]' },
								{ url: '45.gif', alt: '[左哼哼]' },
								{ url: '46.gif', alt: '[右哼哼]' },
								{ url: '47.gif', alt: '[哈欠]' },
								{ url: '48.gif', alt: '[鄙视]' },
								{ url: '49.gif', alt: '[委屈]' },
								{ url: '50.gif', alt: '[快哭了]' },
								{ url: '51.gif', alt: '[阴险]' },
								{ url: '52.gif', alt: '[亲亲]' },
								{ url: '53.gif', alt: '[吓]' },
								{ url: '54.gif', alt: '[可怜]' },
								{ url: '55.gif', alt: '[菜刀]' },
								{ url: '56.gif', alt: '[西瓜]' },
								{ url: '57.gif', alt: '[啤酒]' },
								{ url: '58.gif', alt: '[篮球]' },
								{ url: '59.gif', alt: '[乒乓]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '60.gif', alt: '[咖啡]' },
								{ url: '61.gif', alt: '[饭]' },
								{ url: '62.gif', alt: '[猪头]' },
								{ url: '63.gif', alt: '[玫瑰]' },
								{ url: '64.gif', alt: '[凋谢]' },
								{ url: '65.gif', alt: '[嘴唇]' },
								{ url: '66.gif', alt: '[爱心]' },
								{ url: '67.gif', alt: '[心碎]' },
								{ url: '68.gif', alt: '[蛋糕]' },
								{ url: '69.gif', alt: '[闪电]' },
								{ url: '70.gif', alt: '[炸弹]' },
								{ url: '71.gif', alt: '[刀]' },
								{ url: '72.gif', alt: '[足球]' },
								{ url: '73.gif', alt: '[瓢虫]' },
								{ url: '74.gif', alt: '[便便]' },
								{ url: '75.gif', alt: '[月亮]' },
								{ url: '76.gif', alt: '[太阳]' },
								{ url: '77.gif', alt: '[礼物]' },
								{ url: '78.gif', alt: '[拥抱]' },
								{ url: '79.gif', alt: '[强]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '80.gif', alt: '[弱]' },
								{ url: '81.gif', alt: '[握手]' },
								{ url: '82.gif', alt: '[胜利]' },
								{ url: '83.gif', alt: '[抱拳]' },
								{ url: '84.gif', alt: '[勾引]' },
								{ url: '85.gif', alt: '[拳头]' },
								{ url: '86.gif', alt: '[差劲]' },
								{ url: '87.gif', alt: '[爱你]' },
								{ url: '88.gif', alt: '[NO]' },
								{ url: '89.gif', alt: '[OK]' },
								{ url: '90.gif', alt: '[爱情]' },
								{ url: '91.gif', alt: '[飞吻]' },
								{ url: '92.gif', alt: '[跳跳]' },
								{ url: '93.gif', alt: '[发抖]' },
								{ url: '94.gif', alt: '[怄火]' },
								{ url: '95.gif', alt: '[转圈]' },
								{ url: '96.gif', alt: '[磕头]' },
								{ url: '97.gif', alt: '[回头]' },
								{ url: '98.gif', alt: '[跳绳]' },
								{ url: '99.gif', alt: '[投降]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '100.gif', alt: '[激动]' },
								{ url: '101.gif', alt: '[乱舞]' },
								{ url: '102.gif', alt: '[献吻]' },
								{ url: '103.gif', alt: '[左太极]' },
								{ url: '104.gif', alt: '[右太极]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							]
						]
				    },
					{
						// 火信表情
						emojiName:"huoxinList",
						emojiSort:2,
						minEmoji:true,
						emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/huoxin/",
						emojiList: [
								[
									{ url: 'q_000.png', alt: '[测试1]' },﻿﻿
									{ url: 'q_001.png', alt: '[测试2]' },﻿﻿
									{ url: 'q_002.png', alt: '[测试3]' },﻿﻿
									{ url: 'q_003.png', alt: '[]' },﻿﻿
									{ url: 'q_004.png', alt: '[]' },
									﻿﻿{ url: 'q_005.png', alt: '[]' },﻿﻿
									{ url: 'q_006.png', alt: '[]' },﻿﻿
									{ url: 'q_007.png', alt: '[]' },﻿﻿
									{ url: 'q_008.png', alt: '[]' },
									﻿﻿{ url: 'q_009.png', alt: '[]' },﻿﻿
									{ url: 'q_010.png', alt: '[]' },
									﻿﻿{ url: 'q_011.png', alt: '[]' },﻿﻿
									{ url: 'q_012.png', alt: '[]' },﻿﻿
									{ url: 'q_013.png', alt: '[]' },﻿﻿
									{ url: 'q_014.png', alt: '[]' },
									﻿﻿{ url: 'q_015.png', alt: '[]' },﻿﻿
									{ url: 'q_016.png', alt: '[]' },﻿﻿
									{ url: 'q_017.png', alt: '[]' },﻿﻿
									{ url: 'q_018.png', alt: '[]' },﻿﻿
									{ url: 'q_019.png', alt: '[]' },﻿﻿

									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								],
								[
									{ url: 'q_020.png', alt: '[]' },﻿﻿
									{ url: 'q_021.png', alt: '[]' },﻿﻿
									{ url: 'q_022.png', alt: '[]' },﻿﻿
									{ url: 'q_023.png', alt: '[]' },﻿﻿
									{ url: 'q_024.png', alt: '[]' },﻿﻿
									{ url: 'q_025.png', alt: '[]' },﻿﻿
									{ url: 'q_026.png', alt: '[]' },﻿﻿
									{ url: 'q_027.png', alt: '[]' },﻿﻿
									{ url: 'q_028.png', alt: '[]' },﻿﻿
									{ url: 'q_029.png', alt: '[]' },﻿﻿
									{ url: 'q_030.png', alt: '[]' },﻿﻿
									{ url: 'q_031.png', alt: '[]' },﻿﻿
									{ url: 'q_032.png', alt: '[]' },
									{ url: 'q_033.png', alt: '[]' },﻿﻿
									{ url: 'q_034.png', alt: '[]' },﻿﻿
									{ url: 'q_035.png', alt: '[]' },﻿﻿
									{ url: 'q_036.png', alt: '[]' },﻿﻿
									{ url: 'q_037.png', alt: '[]' },﻿﻿
									{ url: 'q_038.png', alt: '[]' },﻿﻿
									{ url: 'q_039.png', alt: '[]' },﻿﻿

									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							   ],
							   [
								    { url: 'q_040.png', alt: '[]' },﻿﻿
									{ url: 'q_041.png', alt: '[]' },﻿﻿
									{ url: 'q_042.png', alt: '[]' },﻿﻿
									{ url: 'q_043.png', alt: '[]' },﻿﻿
									{ url: 'q_044.png', alt: '[]' },﻿﻿
									{ url: 'q_045.png', alt: '[]' },﻿﻿
									{ url: 'q_046.png', alt: '[]' },﻿﻿
									{ url: 'q_047.png', alt: '[]' },﻿﻿
									{ url: 'q_048.png', alt: '[]' },﻿﻿
									{ url: 'q_049.png', alt: '[]' },﻿﻿
									{ url: 'q_050.png', alt: '[]' },﻿﻿
									{ url: 'q_051.png', alt: '[]' },﻿﻿
									{ url: 'q_052.png', alt: '[]' },
									{ url: 'q_053.png', alt: '[]' },﻿﻿
									{ url: 'q_054.png', alt: '[]' },﻿﻿
									{ url: 'q_055.png', alt: '[]' },﻿﻿
									{ url: 'q_056.png', alt: '[]' },﻿﻿
									{ url: 'q_057.png', alt: '[]' },﻿﻿
									{ url: 'q_058.png', alt: '[]' },﻿﻿
									{ url: 'q_059.png', alt: '[]' },﻿﻿

									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								],
								[
									{ url: 'q_060.png', alt: '[]' },﻿﻿
									{ url: 'q_061.png', alt: '[]' },﻿﻿
									{ url: 'q_062.png', alt: '[]' },﻿﻿
									{ url: 'q_063.png', alt: '[]' },﻿﻿
									{ url: 'q_064.png', alt: '[]' },﻿﻿
									{ url: 'q_065.png', alt: '[]' },﻿﻿
									{ url: 'q_066.png', alt: '[]' },﻿﻿
									{ url: 'q_067.png', alt: '[]' },﻿﻿
									{ url: 'q_068.png', alt: '[]' },﻿﻿
									{ url: 'q_069.png', alt: '[]' },﻿﻿
									{ url: 'q_070.png', alt: '[]' },﻿﻿
									{ url: 'q_071.png', alt: '[]' },﻿﻿
									{ url: 'q_072.png', alt: '[]' },
									{ url: 'q_073.png', alt: '[]' },﻿﻿
									{ url: 'q_074.png', alt: '[]' },﻿﻿
									{ url: 'q_075.png', alt: '[]' },﻿﻿
									{ url: 'q_076.png', alt: '[]' },﻿﻿
									{ url: 'q_077.png', alt: '[]' },﻿﻿
									{ url: 'q_078.png', alt: '[]' },﻿﻿
									{ url: 'q_079.png', alt: '[]' },﻿﻿

									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								    { url: 'q_080.png', alt: '[]' },﻿﻿
									{ url: 'q_081.png', alt: '[]' },﻿﻿
									{ url: 'q_082.png', alt: '[]' },﻿﻿
									{ url: 'q_083.png', alt: '[]' },﻿﻿
									{ url: 'q_084.png', alt: '[]' },﻿﻿
									{ url: 'q_085.png', alt: '[]' },﻿﻿
									{ url: 'q_086.png', alt: '[]' },﻿﻿
									{ url: 'q_087.png', alt: '[]' },﻿﻿
									{ url: 'q_088.png', alt: '[]' },﻿﻿
									{ url: 'q_089.png', alt: '[]' },﻿﻿
									{ url: 'q_090.png', alt: '[]' },﻿﻿
									{ url: 'q_091.png', alt: '[]' },﻿﻿
									{ url: 'q_092.png', alt: '[]' },
									{ url: 'q_093.png', alt: '[]' },﻿﻿
									{ url: 'q_094.png', alt: '[]' },﻿﻿
									{ url: 'q_095.png', alt: '[]' },﻿﻿
									{ url: 'q_096.png', alt: '[]' },﻿﻿
									{ url: 'q_097.png', alt: '[]' },﻿﻿
									{ url: 'q_098.png', alt: '[]' },﻿﻿
									{ url: 'q_099.png', alt: '[]' },﻿﻿
									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
									{ url: 'q_101.png', alt: '[]' },﻿﻿
									{ url: 'q_102.png', alt: '[]' },﻿﻿
									{ url: 'q_103.png', alt: '[]' },﻿﻿
									{ url: 'q_104.png', alt: '[]' },﻿﻿
									{ url: 'q_105.png', alt: '[]' },﻿﻿
									{ url: 'q_106.png', alt: '[]' },﻿﻿
									{ url: 'q_107.png', alt: '[]' },﻿﻿
									{ url: 'q_108.png', alt: '[]' },﻿﻿
									{ url: 'q_109.png', alt: '[]' },﻿﻿
									{ url: 'q_110.png', alt: '[]' },﻿﻿
									{ url: 'q_111.png', alt: '[]' },﻿﻿
									{ url: 'q_112.png', alt: '[]' },
									{ url: 'q_113.png', alt: '[]' },﻿﻿
									{ url: 'q_114.png', alt: '[]' },﻿﻿
									{ url: 'q_115.png', alt: '[]' },﻿﻿
									{ url: 'q_116.png', alt: '[]' },﻿﻿
									{ url: 'q_117.png', alt: '[]' },﻿﻿
									{ url: 'q_118.png', alt: '[]' },﻿﻿
									{ url: 'q_119.png', alt: '[]' },﻿﻿
									{ url: 'q_120.png', alt: '[]' },﻿﻿
									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
									{ url: 'q_121.png', alt: '[]' },﻿﻿
									{ url: 'q_122.png', alt: '[]' },﻿﻿
									{ url: 'q_123.png', alt: '[]' },﻿﻿
									{ url: 'q_124.png', alt: '[]' },﻿﻿
									{ url: 'q_125.png', alt: '[]' },﻿﻿
									{ url: 'q_126.png', alt: '[]' },﻿﻿
									{ url: 'q_127.png', alt: '[]' },﻿﻿
									{ url: 'q_128.png', alt: '[]' },﻿﻿
									{ url: 'q_129.png', alt: '[]' },﻿﻿
									{ url: 'q_130.png', alt: '[]' },﻿﻿
									{ url: 'q_131.png', alt: '[]' },﻿﻿
									{ url: 'q_132.png', alt: '[]' },
									{ url: 'q_133.png', alt: '[]' },﻿﻿
									{ url: 'q_134.png', alt: '[]' },﻿﻿
									{ url: 'q_135.png', alt: '[]' },﻿﻿
									{ url: 'q_136.png', alt: '[]' },﻿﻿
									{ url: 'q_137.png', alt: '[]' },﻿﻿
									{ url: 'q_138.png', alt: '[]' },﻿﻿
									{ url: 'q_139.png', alt: '[]' },﻿﻿
									{ url: 'q_140.png', alt: '[]' },﻿﻿
									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
									{ url: 'q_141.png', alt: '[]' },﻿﻿
									{ url: 'q_142.png', alt: '[]' },﻿﻿
									{ url: 'q_143.png', alt: '[]' },﻿﻿
									{ url: 'q_144.png', alt: '[]' },﻿﻿
									{ url: 'q_145.png', alt: '[]' },﻿﻿
									{ url: 'q_146.png', alt: '[]' },﻿﻿
									{ url: 'q_147.png', alt: '[]' },﻿﻿
									{ url: 'q_148.png', alt: '[]' },﻿﻿
									{ url: 'q_149.png', alt: '[]' },﻿﻿
									{ url: 'q_150.png', alt: '[]' },﻿﻿
									{ url: 'q_151.png', alt: '[]' },﻿﻿
									{ url: 'q_152.png', alt: '[]' },
									{ url: 'q_153.png', alt: '[]' },﻿﻿
									{ url: 'q_154.png', alt: '[]' },﻿﻿
									{ url: 'q_155.png', alt: '[]' },﻿﻿
									{ url: 'q_156.png', alt: '[]' },﻿﻿
									{ url: 'q_157.png', alt: '[]' },﻿﻿
									{ url: 'q_158.png', alt: '[]' },﻿﻿
									{ url: 'q_159.png', alt: '[]' },﻿﻿
									{ url: 'q_160.png', alt: '[]' },﻿﻿
									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
									{ url: 'q_161.png', alt: '[]' },﻿﻿
									{ url: 'q_162.png', alt: '[]' },﻿﻿
									{ url: 'q_163.png', alt: '[]' },﻿﻿
									{ url: 'q_164.png', alt: '[]' },﻿﻿
									{ url: 'q_165.png', alt: '[]' },﻿﻿
									{ url: 'q_166.png', alt: '[]' },﻿﻿
									{ url: 'q_167.png', alt: '[]' },﻿﻿
									{ url: 'q_168.png', alt: '[]' },﻿﻿
									{ url: 'q_169.png', alt: '[]' },﻿﻿
									{ url: 'q_170.png', alt: '[]' },﻿﻿
									{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							]
						]
					},
						{
							// 钉钉表情
							emojiName:"dingdingList",
							emojiSort:2,
							minEmoji:true,
							emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/dingding/",
							emojiList: [
									[
										{ url: 'emotion_001.png', alt: '[微笑]' },﻿﻿
										{ url: 'emotion_002.png', alt: '[可爱]' },﻿﻿
										{ url: 'emotion_003.png', alt: '[憨笑]' },﻿﻿
										{ url: 'emotion_004.png', alt: '[色]' },
										﻿﻿{ url: 'emotion_005.png', alt: '[发呆]' },﻿﻿
										{ url: 'emotion_006.png', alt: '[老板]' },﻿﻿
										{ url: 'emotion_007.png', alt: '[流泪]' },﻿﻿
										{ url: 'emotion_008.png', alt: '[害羞]' },
										﻿﻿{ url: 'emotion_009.png', alt: '[闭嘴]' },﻿﻿
										{ url: 'emotion_010.png', alt: '[睡]' },
										﻿﻿{ url: 'emotion_011.png', alt: '[大哭]' },﻿﻿
										{ url: 'emotion_012.png', alt: '[尴尬]' },﻿﻿
										{ url: 'emotion_013.png', alt: '[感谢]' },﻿﻿
										{ url: 'emotion_014.png', alt: '[赞]' },
										﻿﻿{ url: 'emotion_015.png', alt: '[鼓掌]' },﻿﻿
										{ url: 'emotion_016.png', alt: '[打招呼]' },﻿﻿
										{ url: 'emotion_017.png', alt: '[666]' },﻿﻿
										{ url: 'emotion_018.png', alt: '[抱拳]' },﻿﻿
										{ url: 'emotion_019.png', alt: '[握手]' },﻿﻿
										{ url: 'emotion_020.png', alt: '[OK]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
									],
									[
										{ url: 'emotion_021.png', alt: '[]' },﻿﻿
										{ url: 'emotion_022.png', alt: '[]' },﻿﻿
										{ url: 'emotion_023.png', alt: '[]' },﻿﻿
										{ url: 'emotion_024.png', alt: '[]' },﻿﻿
										{ url: 'emotion_025.png', alt: '[]' },﻿﻿
										{ url: 'emotion_026.png', alt: '[]' },﻿﻿
										{ url: 'emotion_027.png', alt: '[]' },﻿﻿
										{ url: 'emotion_028.png', alt: '[]' },﻿﻿
										{ url: 'emotion_029.png', alt: '[]' },﻿﻿
										{ url: 'emotion_030.png', alt: '[]' },﻿﻿
										{ url: 'emotion_031.png', alt: '[]' },﻿﻿
										{ url: 'emotion_032.png', alt: '[]' },
										{ url: 'emotion_033.png', alt: '[]' },﻿﻿
										{ url: 'emotion_034.png', alt: '[]' },﻿﻿
										{ url: 'emotion_035.png', alt: '[]' },﻿﻿
										{ url: 'emotion_036.png', alt: '[]' },﻿﻿
										{ url: 'emotion_037.png', alt: '[]' },﻿﻿
										{ url: 'emotion_038.png', alt: '[]' },﻿﻿
										{ url: 'emotion_039.png', alt: '[]' },﻿﻿
										{ url: 'emotion_040.png', alt: '[]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								   ],
								   [
										{ url: 'emotion_041.png', alt: '[]' },﻿﻿
										{ url: 'emotion_042.png', alt: '[]' },﻿﻿
										{ url: 'emotion_043.png', alt: '[]' },﻿﻿
										{ url: 'emotion_044.png', alt: '[]' },﻿﻿
										{ url: 'emotion_045.png', alt: '[]' },﻿﻿
										{ url: 'emotion_046.png', alt: '[]' },﻿﻿
										{ url: 'emotion_047.png', alt: '[]' },﻿﻿
										{ url: 'emotion_048.png', alt: '[]' },﻿﻿
										{ url: 'emotion_049.png', alt: '[]' },﻿﻿
										{ url: 'emotion_050.png', alt: '[]' },﻿﻿
										{ url: 'emotion_051.png', alt: '[]' },﻿﻿
										{ url: 'emotion_052.png', alt: '[]' },
										{ url: 'emotion_053.png', alt: '[]' },﻿﻿
										{ url: 'emotion_054.png', alt: '[]' },﻿﻿
										{ url: 'emotion_055.png', alt: '[]' },﻿﻿
										{ url: 'emotion_056.png', alt: '[]' },﻿﻿
										{ url: 'emotion_057.png', alt: '[]' },﻿﻿
										{ url: 'emotion_058.png', alt: '[]' },﻿﻿
										{ url: 'emotion_059.png', alt: '[]' },﻿﻿
										{ url: 'emotion_060.png', alt: '[]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
									],
									[
										{ url: 'emotion_061.png', alt: '[]' },﻿﻿
										{ url: 'emotion_062.png', alt: '[]' },﻿﻿
										{ url: 'emotion_063.png', alt: '[]' },﻿﻿
										{ url: 'emotion_064.png', alt: '[]' },﻿﻿
										{ url: 'emotion_065.png', alt: '[]' },﻿﻿
										{ url: 'emotion_066.png', alt: '[]' },﻿﻿
										{ url: 'emotion_067.png', alt: '[]' },﻿﻿
										{ url: 'emotion_068.png', alt: '[]' },﻿﻿
										{ url: 'emotion_069.png', alt: '[]' },﻿﻿
										{ url: 'emotion_070.png', alt: '[]' },﻿﻿
										{ url: 'emotion_071.png', alt: '[]' },﻿﻿
										{ url: 'emotion_072.png', alt: '[]' },
										{ url: 'emotion_073.png', alt: '[]' },﻿﻿
										{ url: 'emotion_074.png', alt: '[]' },﻿﻿
										{ url: 'emotion_075.png', alt: '[]' },﻿﻿
										{ url: 'emotion_076.png', alt: '[]' },﻿﻿
										{ url: 'emotion_077.png', alt: '[]' },﻿﻿
										{ url: 'emotion_078.png', alt: '[]' },﻿﻿
										{ url: 'emotion_079.png', alt: '[]' },﻿﻿
										{ url: 'emotion_080.png', alt: '[]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								],
								[
										{ url: 'emotion_081.png', alt: '[]' },﻿﻿
										{ url: 'emotion_082.png', alt: '[]' },﻿﻿
										{ url: 'emotion_083.png', alt: '[]' },﻿﻿
										{ url: 'emotion_084.png', alt: '[]' },﻿﻿
										{ url: 'emotion_085.png', alt: '[]' },﻿﻿
										{ url: 'emotion_086.png', alt: '[]' },﻿﻿
										{ url: 'emotion_087.png', alt: '[]' },﻿﻿
										{ url: 'emotion_088.png', alt: '[]' },﻿﻿
										{ url: 'emotion_089.png', alt: '[]' },﻿﻿
										{ url: 'emotion_090.png', alt: '[]' },﻿﻿
										{ url: 'emotion_091.png', alt: '[]' },﻿﻿
										{ url: 'emotion_092.png', alt: '[]' },
										{ url: 'emotion_093.png', alt: '[]' },﻿﻿
										{ url: 'emotion_094.png', alt: '[]' },﻿﻿
										{ url: 'emotion_095.png', alt: '[]' },﻿﻿
										{ url: 'emotion_096.png', alt: '[]' },﻿﻿
										{ url: 'emotion_097.png', alt: '[]' },﻿﻿
										{ url: 'emotion_098.png', alt: '[]' },﻿﻿
										{ url: 'emotion_099.png', alt: '[]' },﻿﻿
										{ url: 'emotion_100.png', alt: '[]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								],
								[
										{ url: 'emotion_101.png', alt: '[]' },﻿﻿
										{ url: 'emotion_102.png', alt: '[]' },﻿﻿
										{ url: 'emotion_103.png', alt: '[]' },﻿﻿
										{ url: 'emotion_104.png', alt: '[]' },﻿﻿
										{ url: 'emotion_105.png', alt: '[]' },﻿﻿
										{ url: 'emotion_106.png', alt: '[]' },﻿﻿
										{ url: 'emotion_107.png', alt: '[]' },﻿﻿
										{ url: 'emotion_108.png', alt: '[]' },﻿﻿
										{ url: 'emotion_109.png', alt: '[]' },﻿﻿
										{ url: 'emotion_110.png', alt: '[]' },﻿﻿
										{ url: 'emotion_111.png', alt: '[]' },﻿﻿
										{ url: 'emotion_112.png', alt: '[]' },
										{ url: 'emotion_113.png', alt: '[]' },﻿﻿
										{ url: 'emotion_114.png', alt: '[]' },﻿﻿
										{ url: 'emotion_115.png', alt: '[]' },﻿﻿
										{ url: 'emotion_116.png', alt: '[]' },﻿﻿
										{ url: 'emotion_117.png', alt: '[]' },﻿﻿
										{ url: 'emotion_118.png', alt: '[]' },﻿﻿
										{ url: 'emotion_119.png', alt: '[]' },﻿﻿
										{ url: 'emotion_120.png', alt: '[]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								],
								[
										{ url: 'emotion_121.png', alt: '[]' },﻿﻿
										{ url: 'emotion_122.png', alt: '[]' },﻿﻿
										{ url: 'emotion_123.png', alt: '[]' },﻿﻿
										{ url: 'emotion_124.png', alt: '[]' },﻿﻿
										{ url: 'emotion_125.png', alt: '[]' },﻿﻿
										{ url: 'emotion_126.png', alt: '[]' },﻿﻿
										{ url: 'emotion_127.png', alt: '[]' },﻿﻿
										{ url: 'emotion_128.png', alt: '[]' },﻿﻿
										{ url: 'emotion_129.png', alt: '[]' },﻿﻿
										{ url: 'emotion_130.png', alt: '[]' },﻿﻿
										{ url: 'emotion_131.png', alt: '[]' },﻿﻿
										{ url: 'emotion_132.png', alt: '[]' },
										{ url: 'emotion_133.png', alt: '[]' },﻿﻿
										{ url: 'emotion_134.png', alt: '[]' },﻿﻿
										{ url: 'emotion_135.png', alt: '[]' },﻿﻿
										{ url: 'emotion_136.png', alt: '[]' },﻿﻿
										{ url: 'emotion_137.png', alt: '[]' },﻿﻿
										{ url: 'emotion_138.png', alt: '[]' },﻿﻿
										{ url: 'emotion_139.png', alt: '[]' },﻿﻿
										{ url: 'emotion_140.png', alt: '[]' },﻿﻿
										{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
								]
							]
						},
				       {
						   // 抖音表情
							emojiName:"douyinList",
							emojiSort:3,
							minEmoji:true,
							emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/douyin/",
							emojiList: [
							[
								{ url: '0.png', alt: '[抖音1]' },
								{ url: '1.png', alt: '[抖音2]' },
								{ url: '2.png', alt: '[抖音3]' },
								{ url: '3.png', alt: '[抖音4]' },
								{ url: '4.png', alt: '[]' },
								{ url: '5.png', alt: '[]' },
								{ url: '6.png', alt: '[]' },
								{ url: '7.png', alt: '[]' },
								{ url: '8.png', alt: '[]' },
								{ url: '9.png', alt: '[]' },
								{ url: '10.png', alt: '[]' },
								{ url: '11.png', alt: '[]' },
								{ url: '12.png', alt: '[]' },
								{ url: '13.png', alt: '[]' },
								{ url: '14.png', alt: '[]' },
								{ url: '15.png', alt: '[]' },
								{ url: '16.png', alt: '[]' },
								{ url: '17.png', alt: '[]' },
								{ url: '18.png', alt: '[]' },
								{ url: '19.png', alt: '[]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
								[
								{ url: '20.png', alt: '[]' },
								{ url: '21.png', alt: '[]' },
								{ url: '22.png', alt: '[]' },
								{ url: '23.png', alt: '[]' },
								{ url: '24.png', alt: '[]' },
								{ url: '25.png', alt: '[]' },
								{ url: '26.png', alt: '[]' },
								{ url: '27.png', alt: '[]' },
								{ url: '28.png', alt: '[]' },
								{ url: '29.png', alt: '[]' },
								{ url: '30.png', alt: '[]' },
								{ url: '31.png', alt: '[]' },
								{ url: '32.png', alt: '[]' },
								{ url: '33.png', alt: '[]' },
								{ url: '34.png', alt: '[]' },
								{ url: '35.png', alt: '[]' },
								{ url: '36.png', alt: '[]' },
								{ url: '37.png', alt: '[]' },
								{ url: '38.png', alt: '[]' },
								{ url: '39.png', alt: '[]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '40.png', alt: '[]' },
								{ url: '41.png', alt: '[]' },
								{ url: '42.png', alt: '[]' },
								{ url: '43.png', alt: '[]' },
								{ url: '44.png', alt: '[]' },
								{ url: '25.png', alt: '[]' },
								{ url: '46.png', alt: '[]' },
								{ url: '47.png', alt: '[]' },
								{ url: '48.png', alt: '[]' },
								{ url: '49.png', alt: '[]' },
								{ url: '50.png', alt: '[]' },
								{ url: '51.png', alt: '[]' },
								{ url: '52.png', alt: '[]' },
								{ url: '53.png', alt: '[]' },
								{ url: '54.png', alt: '[]' },
								{ url: '55.png', alt: '[]' },
								{ url: '56.png', alt: '[]' },
								{ url: '57.png', alt: '[]' },
								{ url: '58.png', alt: '[]' },
								{ url: '59.png', alt: '[]' },

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '60.png', alt: '[]' },
								{ url: '61.png', alt: '[]' },
								{ url: '62.png', alt: '[]' },
								{ url: '63.png', alt: '[]' },
								{ url: '64.png', alt: '[]' },
								{ url: '65.png', alt: '[]' },
								{ url: '66.png', alt: '[]' },
								{ url: '67.png', alt: '[]' },
								{ url: '68.png', alt: '[]' },
								{ url: '69.png', alt: '[]' },
								{ url: '70.png', alt: '[]' },
								{ url: '71.png', alt: '[]' },
								{ url: '72.png', alt: '[]' },
								{ url: '73.png', alt: '[]' },
								{ url: '74.png', alt: '[]' },
								{ url: '75.png', alt: '[]' },
								{ url: '76.png', alt: '[]' },
								{ url: '77.png', alt: '[]' },
								{ url: '78.png', alt: '[]' },
								{ url: '79.png', alt: '[]' },
								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '80.png', alt: '[]' },﻿﻿
								{ url: '81.png', alt: '[]' },﻿﻿
								{ url: '82.png', alt: '[]' },﻿﻿
								{ url: '83.png', alt: '[]' },﻿﻿
								{ url: '84.png', alt: '[]' },﻿﻿
								{ url: '85.png', alt: '[]' },﻿﻿
								{ url: '86.png', alt: '[]' },﻿﻿
								{ url: '87.png', alt: '[]' },﻿﻿
								{ url: '88.png', alt: '[]' },﻿﻿
								{ url: '89.png', alt: '[]' },﻿﻿
								{ url: '90.png', alt: '[]' },﻿﻿
								{ url: '91.png', alt: '[]' },﻿﻿
								{ url: '92.png', alt: '[]' },
								{ url: '93.png', alt: '[]' },﻿﻿
								{ url: '94.png', alt: '[]' },﻿﻿
								{ url: '95.png', alt: '[]' },﻿﻿
								{ url: '96.png', alt: '[]' },﻿﻿
								{ url: '97.png', alt: '[]' },﻿﻿
								{ url: '98.png', alt: '[]' },﻿﻿
								{ url: '99.png', alt: '[]' },﻿﻿

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '100.png', alt: '[]' },﻿﻿
								{ url: '101.png', alt: '[]' },﻿﻿
								{ url: '102.png', alt: '[]' },﻿﻿
								{ url: '103.png', alt: '[]' },﻿﻿
								{ url: '104.png', alt: '[]' },﻿﻿
								{ url: '105.png', alt: '[]' },﻿﻿
								{ url: '106.png', alt: '[]' },﻿﻿
								{ url: '107.png', alt: '[]' },﻿﻿
								{ url: '108.png', alt: '[]' },﻿﻿
								{ url: '109.png', alt: '[]' },﻿﻿
								{ url: '110.png', alt: '[]' },﻿﻿
								{ url: '111.png', alt: '[]' },﻿﻿
								{ url: '112.png', alt: '[]' },
								{ url: '113.png', alt: '[]' },﻿﻿
								{ url: '114.png', alt: '[]' },﻿﻿
								{ url: '115.png', alt: '[]' },﻿﻿
								{ url: '116.png', alt: '[]' },﻿﻿
								{ url: '117.png', alt: '[]' },﻿﻿
								{ url: '118.png', alt: '[]' },﻿﻿
								{ url: '119.png', alt: '[]' },﻿﻿

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
							],
							[
								{ url: '120.png', alt: '[]' },﻿﻿
								{ url: '121.png', alt: '[]' },﻿﻿
								{ url: '122.png', alt: '[]' },﻿﻿
								{ url: '123.png', alt: '[]' },﻿﻿
								{ url: '124.png', alt: '[]' },﻿﻿
								{ url: '125.png', alt: '[]' },﻿﻿
								{ url: '126.png', alt: '[]' },﻿﻿
								{ url: '127.png', alt: '[]' },﻿﻿
								{ url: '128.png', alt: '[]' },﻿﻿
								{ url: '129.png', alt: '[]' },﻿﻿
								{ url: '130.png', alt: '[]' },﻿﻿
								{ url: '131.png', alt: '[]' },﻿﻿
								{ url: '132.png', alt: '[]' },
								{ url: '133.png', alt: '[]' },﻿﻿
								{ url: '134.png', alt: '[]' },﻿﻿
								{ url: '135.png', alt: '[]' },﻿﻿
								{ url: '136.png', alt: '[]' },﻿﻿
								{ url: '137.png', alt: '[]' },﻿﻿
								{ url: '138.png', alt: '[]' },﻿﻿
								{ url: '139.png', alt: '[]' },﻿﻿

								{ url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
				        ],
						[
							   { url: '140.png', alt: '[]' },﻿﻿
							   { url: '141.png', alt: '[]' },﻿﻿
							   { url: 'https://static.shopsuite.cn/xcxfile/appicon/im/img/tab/delete2.png', alt: '[删除]' }
						]
					  ]
				     },
					 {
					 	emojiName:"ajmdList",
					 	emojiSort:4,
					 	minEmoji:false,
					 	emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/ajmd/",
					 	emojiList: [
					 		[
					 			{ url: '0.png', alt: '[ajmd1]' },
					 			{ url: '1.png', alt: '[ajmd2]' },
					 			{ url: '2.png', alt: '[ajmd3]' },
					 			{ url: '3.png', alt: '[ajmd4]' },
					 			{ url: '4.png', alt: '[]' },
					 			{ url: '5.png', alt: '[]' },
					 			{ url: '6.png', alt: '[]' },
					 			{ url: '7.png', alt: '[]' },
					 		],
					 		[
					 			{ url: '8.png', alt: '[]' },
					 			{ url: '9.png', alt: '[]' },
					 			{ url: '10.png', alt: '[]' },
					 			{ url: '11.png', alt: '[]' },
					 			{ url: '12.png', alt: '[]' },
					 			{ url: '13.png', alt: '[]' },
					 			{ url: '14.png', alt: '[]' },
					 			{ url: '15.png', alt: '[]' },
					 		],
					 		[
					 			{ url: '16.png', alt: '[]' },
					 			{ url: '17.png', alt: '[]' },
					 			{ url: '18.png', alt: '[]' },
					 			{ url: '19.png', alt: '[]' },
					 			{ url: '20.png', alt: '[]' },
					 			{ url: '21.png', alt: '[]' },
					 			{ url: '22.png', alt: '[]' },
					 			{ url: '23.png', alt: '[]' },
					 		],
					 		[
					 			{ url: '24.png', alt: '[]' },
					 			{ url: '25.png', alt: '[]' },
					 			{ url: '26.png', alt: '[]' },
					 			{ url: '27.png', alt: '[]' },
					 			{ url: '28.png', alt: '[]' },
					 			{ url: '29.png', alt: '[]' },
					 			{ url: '30.png', alt: '[]' },
					 			{ url: '31.png', alt: '[]' },
					 		],
					 		[
					 			{ url: '32.png', alt: '[]' },
					 			{ url: '33.png', alt: '[]' },
					 			{ url: '34.png', alt: '[]' },
					 			{ url: '35.png', alt: '[]' },
					 			{ url: '36.png', alt: '[]' },
					 			{ url: '37.png', alt: '[]' },
					 			{ url: '38.png', alt: '[]' },
					 			{ url: '39.png', alt: '[]' },
					 		],
					 		[
					 			{ url: '40.png', alt: '[]' },
					 			{ url: '41.png', alt: '[]' },
					 			{ url: '42.png', alt: '[]' },
					 			{ url: '43.png', alt: '[]' },
					 			{ url: '44.png', alt: '[]' },
					 			{ url: '45.png', alt: '[]' },
					 			{ url: '46.png', alt: '[]' },
					 			{ url: '47.png', alt: '[]' },
					 		],
					 	  ]
					 	},
					 {
					 	emojiName:"gongfuhuList",
					 	emojiSort:5,
					 	minEmoji:false,
					 	emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/gongfuhu/",
					 	emojiList: [
					 		 [
					 		 	{ url: '0.gif', alt: '[]' },
					 		 	{ url: '1.gif', alt: '[]' },
					 		 	{ url: '2.gif', alt: '[]' },
					 		 	{ url: '3.gif', alt: '[]' },
					 		 	{ url: '4.gif', alt: '[]' },
					 		 	{ url: '5.gif', alt: '[]' },
					 		 	{ url: '6.gif', alt: '[]' },
					 		 	{ url: '7.gif', alt: '[]' },
					 		],
					 		[
					 			{ url: '8.gif', alt: '[]' },
					 		 	{ url: '9.gif', alt: '[]' },
					 		 	{ url: '10.gif', alt: '[]' },
					 		 	{ url: '11.gif', alt: '[]' },
					 		 	{ url: '12.gif', alt: '[]' },
					 		 	{ url: '13.gif', alt: '[]' },
					 		 	{ url: '14.gif', alt: '[]' },
					 		 	{ url: '15.gif', alt: '[]' },

					 		],
					 		[
					 			{ url: '16.gif', alt: '[]' },
					 		 	{ url: '17.gif', alt: '[]' },
					 		 	{ url: '18.gif', alt: '[]' },
					 		 	{ url: '19.gif', alt: '[]' },
					 		 	{ url: '20.gif', alt: '[]' },
					 		 	{ url: '21.gif', alt: '[]' },
					 		 	{ url: '22.gif', alt: '[]' },
					 			{ url: '23.gif', alt: '[]' },
					 		 ],
					 		 [
					 		 	{ url: '24.gif', alt: '[]' },
					 		  	{ url: '25.gif', alt: '[]' },
					 		  	{ url: '26.gif', alt: '[]' },
					 		  	{ url: '27.gif', alt: '[]' },
					 		  	{ url: '28.gif', alt: '[]' },
					 		  	{ url: '29.gif', alt: '[]' },
					 		  ]
					 	]
					 },
				     {
							emojiName:"xxyList",
							emojiSort:6,
							minEmoji: false,
							emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/xxy/",
							emojiList: [
								[
									{ url: '0.png', alt: '[]' },
									{ url: '1.png', alt: '[]' },
									{ url: '2.png', alt: '[]' },
									{ url: '3.png', alt: '[]' },
									{ url: '4.png', alt: '[]' },
									{ url: '5.png', alt: '[]' },
									{ url: '6.png', alt: '[]' },
									{ url: '7.png', alt: '[]' },
								],
								[
									{ url: '8.png', alt: '[]' },
									{ url: '9.png', alt: '[]' },
									{ url: '10.png', alt: '[]' },
									{ url: '11.png', alt: '[]' },
									{ url: '12.png', alt: '[]' },
									{ url: '13.png', alt: '[]' },
									{ url: '14.png', alt: '[]' },
									{ url: '15.png', alt: '[]' },
								],
								[
									{ url: '16.png', alt: '[]' },
									{ url: '17.png', alt: '[]' },
									{ url: '18.png', alt: '[]' },
									{ url: '19.png', alt: '[]' },
									{ url: '20.png', alt: '[]' },
									{ url: '21.png', alt: '[]' },
									{ url: '22.png', alt: '[]' },
									{ url: '23.png', alt: '[]' },
								],
								[
									{ url: '24.png', alt: '[]' },
									{ url: '25.png', alt: '[]' },
									{ url: '26.png', alt: '[]' },
									{ url: '27.png', alt: '[]' },
									{ url: '28.png', alt: '[]' },
									{ url: '29.png', alt: '[]' },
									{ url: '30.png', alt: '[]' },
									{ url: '31.png', alt: '[]' },
								],
								[
									{ url: '32.png', alt: '[]' },
									{ url: '33.png', alt: '[]' },
									{ url: '34.png', alt: '[]' },
									{ url: '35.png', alt: '[]' },
									{ url: '36.png', alt: '[]' },
									{ url: '37.png', alt: '[]' },
									{ url: '38.png', alt: '[]' },
									{ url: '39.png', alt: '[]' },
								]
							]
				        },
						{
							emojiName:"feineneList",
							emojiSort:7,
							minEmoji: false,
							emojiPath:"https://static.shopsuite.cn/xcxfile/appicon/im/img/feinene/",
							emojiList: [
								[
									{ url: '0.gif', alt: '[]' },
									{ url: '1.gif', alt: '[]' },
									{ url: '2.gif', alt: '[]' },
									{ url: '3.gif', alt: '[]' },
									{ url: '4.gif', alt: '[]' },
									{ url: '5.gif', alt: '[]' },
									{ url: '6.gif', alt: '[]' },
									{ url: '7.gif', alt: '[]' },
								],
								[
									{ url: '8.gif', alt: '[]' },
									{ url: '9.gif', alt: '[]' },
									{ url: '10.gif', alt: '[]' },
									{ url: '11.gif', alt: '[]' },
									{ url: '12.gif', alt: '[]' },
									{ url: '13.gif', alt: '[]' },
									{ url: '14.gif', alt: '[]' },
									{ url: '15.gif', alt: '[]' },
								],
								[
									{ url: '16.gif', alt: '[]' },
									{ url: '17.gif', alt: '[]' },
									{ url: '18.gif', alt: '[]' },
									{ url: '19.gif', alt: '[]' },
									{ url: '20.gif', alt: '[]' },
									{ url: '21.gif', alt: '[]' },
									{ url: '22.gif', alt: '[]' },
									{ url: '23.gif', alt: '[]' },
								],
								[
									{ url: '24.gif', alt: '[]' },
									{ url: '25.gif', alt: '[]' },
								]
							]
						   },
						{
						emojiName:"",
						emojiSort:8,
						minEmoji:false,
						emojiPath:"",
						emojiList: [
							  [
								{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
								{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
							   ],
							   [
								{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
								{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
							   ],
							   [
								{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
								{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },﻿﻿{ url: '', alt: '[]' },
							   ]
							  ]
						}
			     ]
	    }




// export与export default均可用于导出常量、函数、文件、模块等
// 在一个文件或模块中，export、import可以有多个，export default仅有一个
// 通过export方式导出，在导入时要加{ }，export default则不需要
// export能直接导出变量表达式，export default不行。


export default emojiData;
