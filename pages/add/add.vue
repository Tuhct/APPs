<template>
	<view class="all">
		<!-- 头部区域 -->
		<view class="head flex flex-direction">
			<u-modal title="新增文件夹" :show="addFolderShow" :showCancelButton="true" closeOnClickOverlay @confirm="addFolderConfirm" @cancel="() => (addFolderShow = false)">
				<u--input placeholder="文件夹" border="surround" v-model="newFolder"></u--input>
			</u-modal>
			<u-modal :show="addAgencyShow" :showCancelButton="true" closeOnClickOverlay confirmText="完成" @confirm="addAgencyConfirm" @cancel="() => (addAgencyShow = false)">
				<u-textarea v-model="agencyInfo" placeholder="添加待办" border="none" :maxlength="100"></u-textarea>
			</u-modal>
			<!-- 用来展示错误信息的 -->
			<u-toast ref="uToast"></u-toast>
			<!-- 菜单 -->
			<u-action-sheet
				:actions="actionList"
				:closeOnClickOverlay="true"
				:closeOnClickAction="true"
				@close="actionShow = false"
				cancelText="取消"
				@select="actionSelect"
				:show="actionShow"
			></u-action-sheet>

			<view class="flex justify-between head-top text-xl margin-top-sm" :style="{ paddingTop: paddingTop }">
				<view class="flex justify-between margin-left-sm head-left">
					<view :class="tabNum == 0 ? 'bold-style text-bold' : ''" @tap="tabClick(0)">日记</view>
					<view :class="tabNum == 1 ? 'bold-style text-bold' : ''" @tap="tabClick(1)">代办</view>
				</view>
				<view class="flex justify-between margin-right-sm head-right">
					<u--image src="/static/icon/addFile.png" width="25px" height="25px" @tap="newFolderClick"></u--image>
					<u--image src="/static/icon/personal.png" width="25px" height="25px" @tap="toMine"></u--image>
				</view>
			</view>
			<u-search
				class="head-search padding"
				shape="round"
				:showAction="false"
				bgColor="#fff"
				height="80rpx"
				:placeholder="tabNum == 0 ? '搜索笔记' : '搜索代办'"
				v-model="keyWord"
			></u-search>
		</view>
		<!-- 内容区域 -->
		<swiper class="content swiper-box" :current="swiperCurrent" :style="{ paddingTop: paddingTop }" @animationfinish="animationfinish">
			<swiper-item>
				<scroll-view scroll-y style="height: 100%;width: 100%;">
					<view class="padding-bottom padding-lr">
						<view v-if="noteList.length > 0">
							<!-- v-for使用/不使用key -->
							<!-- 不加key渲染时候会依次替换渲染，加了key会直接将其放在指定位置，加key提升效率。 -->
							<view class="bg-white radius padding-sm noteList" v-for="(item, index) in noteListFilter" :key="index">
								<view class="" @tap="noteListClick(item)">
									<view class="text-bold text-lg">{{ item.noteName || '' }}</view>
									<view class="margin-top-xs" v-html="getEllipsis(item.noteContent, 30)"></view>
									<view class="flex justify-between margin-top-xs">
										<view class="">{{ item.noteTime }}</view>
										<view class="" @tap.stop="menuClick(index)"><u--image src="/static/icon/ellipsis.png" width="20px" height="20px"></u--image></view>
									</view>
								</view>
							</view>
						</view>
						<view class="bg-white radius padding-sm noteList" v-else>
							<view class="text-bold text-lg">你好，欢迎使用随便记</view>
							<view class="margin-top-xs">{{ getEllipsis('随便记是一款高效简洁的内容记录工具，能在你灵感迸发时记录内容：轻巧的文字拥有无限力量。', 30) }}</view>
							<view class="flex justify-between margin-top-xs">
								<view class="">05-20 13:14</view>
								<view class=""><u--image src="/static/icon/ellipsis.png" width="20px" height="20px"></u--image></view>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
			<swiper-item class="swiper-item">
				<scroll-view scroll-y style="height: 100%;width: 100%;">
					<view v-if="agencyListFilter.length > 0">
						<view v-for="(item, index) in agencyListFilter" :key="index">
							<u-checkbox-group placement="column" shape="square">
								<view class="text-bold text-xl padding-left margin-top-sm">{{ contrastDate(item.agencyTime) }}</view>
								<view class="bg-white margin-tb-xs margin-lr radius padding" v-for="(itemList, indexList) in item.list" :key="indexList">
									<u-checkbox :checked="itemList.checked" :label="itemList.agencyName" :name="itemList.agencyName"></u-checkbox>
								</view>
							</u-checkbox-group>
						</view>
					</view>
					<view class="flex align-center justify-center" style="height: 100%;width: 100%;" v-else>
						<u--image src="/static/bak/null.png" width="250px" height="250px"></u--image>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
		<!-- 添加内容-固定定位 -->
		<view class="addIcon" @tap="add"><u--image src="/static/icon/add.png" width="120rpx" height="120rpx"></u--image></view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			tabNum: 0,
			keyWord: '',
			// 默认展示 随便记
			swiperCurrent: 0,
			addFolderShow: false,
			newFolder: '文件夹',
			addAgencyShow: false,
			agencyInfo: '',
			menuClickIndex: null,
			// 计算状态栏的高度并减去
			paddingTop: '',
			actionShow: false,
			// 通过计算属性进行编写模糊搜索代办的关键词返回的新数据
			// agencyList: [],
			actionList: [
				{
					name: '删除'
				}
			],
			noteList: [
				{
					noteName: '我有一个秘密（1）',
					noteContent: `我有一个小秘密，夜深人静时，在皎洁的月光下，我翻阅心灵日记，涌上心头的是涩涩的酸楚。这件事发生在几个月前，我参加了淮安市电视台举办的"小荷风采"才艺大赛，得到了二等奖以及二百元奖金，并在颁奖晚会上参加演出，节目在电视上转播。小朋友们都称我为"小歌星"，可谁知道我心中的难受呢?我一直有一个愿望，能成为一个全世界鼎鼎有名的歌唱家，可这只是个空想，因为我从来没有积极地练过唱歌。每次钢琴课结束时老师都会带我们练一会儿声，唱一会儿歌，老师要我们经常练声练气息，可是不知怎么我却不喜欢练声，也从来没有练过气息。老师说，我只能凭着现在的嗓音唱歌，长大了如果想当歌唱家，是远远不够的。我听了只是伤心，我也知道想当个合格的歌唱家练声练气息是必不可少的，可是回家却仍然不练。"难道这个梦要破碎?"我摇摇头,"不会吧?"这个小秘密一直珍藏在我心里，它启发我，成名成家并不容易，主要是坚持苦练，才能圆了心中的明星梦。`,
					noteTime: '10-11 11:39'
				},
				{
					noteName: '我有一个秘密（2）',
					noteContent: `在生活中，每个人都有自己的秘密，今天，让我告诉大家深藏在我心中的一个小秘密吧!我七岁生日那天，高高兴兴地找小伙伴鑫鑫去玩。她送给我一个精美的存钱罐。我喜欢极了，真有些爱不释手。就在我们谈话的时候，从他父母的房间里传出了吵架的声音，顿时，鑫鑫的眼圈红了。我不知所措，结结巴巴地问: “......他们怎么了?"鑫鑫哽咽着说:"爸爸要和妈妈离婚，我......我害怕...…"我心里一惊,陪着鑫鑫掉起眼泪来。过了一会儿，隔壁房间的吵架声、摔东西的声音更大了。鑫鑫不住地流泪:“我不想让妈妈这么伤心，我要是有好多好多钱，就能帮妈妈留住爸爸....""那我帮你攒，就用这个!“我说着拿起了手中的存钱罐。“好!""就这么说定了!“四只小手握在一起，我们还拉钩发誓不告诉任何人。从此，我们把所有零花钱都省了下来。每当摇着存钱罐，听见那清脆的叮当声时，我心里就特别满足。可是，我们的钱并没有换回鑫鑫的爸爸对她妈妈的感情。鑫鑫随妈妈带着遗憾搬走了。现在，每当我看到这个存钱罐，就觉得它好沉好沉，因为它把我的秘密封存在了里面。这就是我的小秘密，你能告诉我你的秘密吗?`,
					noteTime: '2020-10-11 16:39'
				},
				{
					noteName: '小学生日记',
					noteContent: `今天，妈妈带我去大凌河边玩，我再一个小湖边玩耍着，突然，一个可爱的小灰兔从水里跳了出来,我和妈妈都吓了一大跳，沉默了一会儿，妈妈笑着对我说:“函函，这只小兔子也许是掉进清清的水里，也许，是个两栖动物。”我问:“两栖动物什么意思?”妈妈说:“应该是可以生活在水里，又能生活在陆地上这个意思吧。”`,
					noteTime: '10-11 11:39'
				},
				{
					noteName: '我有一个秘密（1）',
					noteContent: `我有一个小秘密，夜深人静时，在皎洁的月光下，我翻阅心灵日记，涌上心头的是涩涩的酸楚。这件事发生在几个月前，我参加了淮安市电视台举办的"小荷风采"才艺大赛，得到了二等奖以及二百元奖金，并在颁奖晚会上参加演出，节目在电视上转播。小朋友们都称我为"小歌星"，可谁知道我心中的难受呢?我一直有一个愿望，能成为一个全世界鼎鼎有名的歌唱家，可这只是个空想，因为我从来没有积极地练过唱歌。每次钢琴课结束时老师都会带我们练一会儿声，唱一会儿歌，老师要我们经常练声练气息，可是不知怎么我却不喜欢练声，也从来没有练过气息。老师说，我只能凭着现在的嗓音唱歌，长大了如果想当歌唱家，是远远不够的。我听了只是伤心，我也知道想当个合格的歌唱家练声练气息是必不可少的，可是回家却仍然不练。"难道这个梦要破碎?"我摇摇头,"不会吧?"这个小秘密一直珍藏在我心里，它启发我，成名成家并不容易，主要是坚持苦练，才能圆了心中的明星梦。`,
					noteTime: '10-11 11:39'
				},
				{
					noteName: '我有一个秘密（2）',
					noteContent: `在生活中，每个人都有自己的秘密，今天，让我告诉大家深藏在我心中的一个小秘密吧!我七岁生日那天，高高兴兴地找小伙伴鑫鑫去玩。她送给我一个精美的存钱罐。我喜欢极了，真有些爱不释手。就在我们谈话的时候，从他父母的房间里传出了吵架的声音，顿时，鑫鑫的眼圈红了。我不知所措，结结巴巴地问: “......他们怎么了?"鑫鑫哽咽着说:"爸爸要和妈妈离婚，我......我害怕...…"我心里一惊,陪着鑫鑫掉起眼泪来。过了一会儿，隔壁房间的吵架声、摔东西的声音更大了。鑫鑫不住地流泪:“我不想让妈妈这么伤心，我要是有好多好多钱，就能帮妈妈留住爸爸....""那我帮你攒，就用这个!“我说着拿起了手中的存钱罐。“好!""就这么说定了!“四只小手握在一起，我们还拉钩发誓不告诉任何人。从此，我们把所有零花钱都省了下来。每当摇着存钱罐，听见那清脆的叮当声时，我心里就特别满足。可是，我们的钱并没有换回鑫鑫的爸爸对她妈妈的感情。鑫鑫随妈妈带着遗憾搬走了。现在，每当我看到这个存钱罐，就觉得它好沉好沉，因为它把我的秘密封存在了里面。这就是我的小秘密，你能告诉我你的秘密吗?`,
					noteTime: '10-11 11:39'
				},
				{
					noteName: '小学生日记',
					noteContent: `今天，妈妈带我去大凌河边玩，我再一个小湖边玩耍着，突然，一个可爱的小灰兔从水里跳了出来,我和妈妈都吓了一大跳，沉默了一会儿，妈妈笑着对我说:“函函，这只小兔子也许是掉进清清的水里，也许，是个两栖动物。”我问:“两栖动物什么意思?”妈妈说:“应该是可以生活在水里，又能生活在陆地上这个意思吧。”`,
					noteTime: '2021-10-11 11:39'
				},
				{
					noteName: '我有一个秘密（2）',
					noteContent: `在生活中，每个人都有自己的秘密，今天，让我告诉大家深藏在我心中的一个小秘密吧!我七岁生日那天，高高兴兴地找小伙伴鑫鑫去玩。她送给我一个精美的存钱罐。我喜欢极了，真有些爱不释手。就在我们谈话的时候，从他父母的房间里传出了吵架的声音，顿时，鑫鑫的眼圈红了。我不知所措，结结巴巴地问: “......他们怎么了?"鑫鑫哽咽着说:"爸爸要和妈妈离婚，我......我害怕...…"我心里一惊,陪着鑫鑫掉起眼泪来。过了一会儿，隔壁房间的吵架声、摔东西的声音更大了。鑫鑫不住地流泪:“我不想让妈妈这么伤心，我要是有好多好多钱，就能帮妈妈留住爸爸....""那我帮你攒，就用这个!“我说着拿起了手中的存钱罐。“好!""就这么说定了!“四只小手握在一起，我们还拉钩发誓不告诉任何人。从此，我们把所有零花钱都省了下来。每当摇着存钱罐，听见那清脆的叮当声时，我心里就特别满足。可是，我们的钱并没有换回鑫鑫的爸爸对她妈妈的感情。鑫鑫随妈妈带着遗憾搬走了。现在，每当我看到这个存钱罐，就觉得它好沉好沉，因为它把我的秘密封存在了里面。这就是我的小秘密，你能告诉我你的秘密吗?`,
					noteTime: '10-11 11:39'
				},
				{
					noteName: '小学生日记',
					noteContent: `今天，妈妈带我去大凌河边玩，我再一个小湖边玩耍着，突然，一个可爱的小灰兔从水里跳了出来,我和妈妈都吓了一大跳，沉默了一会儿，妈妈笑着对我说:“函函，这只小兔子也许是掉进清清的水里，也许，是个两栖动物。”我问:“两栖动物什么意思?”妈妈说:“应该是可以生活在水里，又能生活在陆地上这个意思吧。”`,
					noteTime: '2021-10-11 11:39'
				}
			],
			rawDataAgencyList: [
				{
					agencyTime: '2022-10-18',
					list: [
						{
							agencyName: '88',
							checked: true
						},
						{
							agencyName: '66',
							checked: false
						}
					]
				},
				{
					agencyTime: '2022-10-11',
					list: [
						{
							agencyName: '3',
							checked: true
						},
						{
							agencyName: '5',
							checked: false
						}
					]
				},
				{
					agencyTime: '2022-08-06',
					list: [
						{
							agencyName: '4',
							checked: false
						},
						{
							agencyName: '6',
							checked: false
						},
						{
							agencyName: '8',
							checked: false
						},
						{
							agencyName: '10',
							checked: true
						},
						{
							agencyName: '11',
							checked: true
						},
						{
							agencyName: '15',
							checked: false
						}
					]
				},
				{
					agencyTime: '2021-12-07',
					list: [
						{
							agencyName: '7',
							checked: false
						},
						{
							agencyName: '9',
							checked: false
						}
					]
				}
			]
		};
	},
	onLoad(option) {
		if (option.type == 'add') {
			this.noteList.unshift(option);
		}
		// #ifdef APP-PLUS
		// 计算状态栏的高度并减去
		this.paddingTop = uni.$u.sys().statusBarHeight + 'px';
		// #endif
	},
	computed: {
		noteListFilter() {
			//filter是过滤函数去除了不包含关键字的情况
			return this.noteList.filter(p => {
				//返回过滤后的数组
				if (p.noteContent) {
					return p.noteContent.indexOf(this.keyWord) !== -1 || p.noteName.indexOf(this.keyWord) !== -1;
				}
				return [];
			});
		},
		agencyListFilter() {
			let list = [];
			if (this.keyWord !== '') {
				list = this.rawDataAgencyList
					.map(p => {
						let o = { agencyTime: p.agencyTime, list: [] };
						o.list = p.list.filter(c => {
							return c.agencyName.indexOf(this.keyWord) !== -1;
						});
						return o;
					})
					.filter(p => p.list.length > 0);
			} else {
				list = this.rawDataAgencyList;
			}
			return list;
		}
	},
	// 通过监听值变化进行模糊搜索代办的关键词，下面是另一种方式，最终用的是computed
	// watch: {
	// 	keyWord: {
	// 		// immediate: true, //在框的值还没变化时执行如下函数显示出所有的情况
	// 		handler(val) {
	// 			if (val !== '') {
	// 				let list = this.rawDataAgencyList
	// 					.map(p => {
	// 						let o = { agencyTime: p.agencyTime, list: [] };
	// 						o.list = p.list.filter(c => {
	// 							return c.agencyName.indexOf(this.keyWord) !== -1;
	// 						});
	// 						return o;
	// 					})
	// 					.filter(p => p.list.length > 0);
	// 				this.agencyList = list;
	// 			} else {
	// 				this.agencyList = this.rawDataAgencyList;
	// 			}
	// 		}
	// 	}
	// },
	methods: {
		tabClick(index) {
			this.tabNum = index;
			this.swiperCurrent = index;
			this.keyWord = '';
			if (this.tabNum == 1) {
				this.agencyList = this.rawDataAgencyList;
			}
		},
		animationfinish({ detail: { current } }) {
			this.swiperCurrent = current;
			this.tabNum = current;
		},
		// 新增文件夹
		newFolderClick() {
			this.addFolderShow = true;
		},
		// 新增文件夹-点击确认按钮触发以后
		// 注意：我这没做操作
		addFolderConfirm() {
			this.addFolderShow = false;
			// 确认以后关闭弹窗 + 清空默认展示条件
			this.newFolder = '文件夹';
		},
		// 新增代办-点击确认按钮触发以后
		addAgencyConfirm() {
			if (!this.agencyInfo) {
				this.addAgencyShow = false;
				this.$refs.uToast.error('添加失败，代办不能为空！');
				return;
			}
			var date = this.toLocaleString();
			// 判断是不是同一天加的数据并且当天有数据，如果是就加到里面。不是，就新建一条
			if (this.rawDataAgencyList.length > 0 && this.rawDataAgencyList[0].agencyTime == date) {
				this.rawDataAgencyList[0].list.push({
					agencyName: this.agencyInfo,
					checked: false
				});
			} else {
				this.rawDataAgencyList.unshift({
					agencyTime: date,
					list: [
						{
							agencyName: this.agencyInfo,
							checked: false
						}
					]
				});
			}
			this.addAgencyShow = false;
		},
		add() {
			// 0 随便记，1 代办
			if (this.tabNum == 0) {
				// 出现随便记的内容，md文件内容
				uni.$u.route('/pages/add/addNote/addNote');
			} else if (this.tabNum == 1) {
				// 打开窗口前先清空内容
				this.agencyInfo = '';
				// 出现代办的内容，弹窗填写内容
				this.addAgencyShow = true;
			}
		},
		// 点击单条日记进行跳转
		noteListClick(e) {
			uni.$u.route('/pages/add/addNote/addNote', e);
		},
		menuClick(index) {
			this.menuClickIndex = index;
			this.actionShow = true;
		},
		actionSelect(e) {
			if (!this.menuClickIndex) {
				this.noteListFilter.splice(this.menuClickIndex, 1);
			}
		},
		toMine() {
			uni.$u.route('/pages/my/my');
		}
	}
};
</script>

<style lang="scss" scoped>
page {
	height: 100%;
}
.all {
	display: flex;
	flex-direction: column;
	height: 100vh;
	width: 100%;
	background-color: #f8f8f8;
	// overflow: hidden;

	.head {
		height: 200rpx;
		.head-top {
			.head-left {
				width: 200rpx;
			}

			.head-right {
				width: 140rpx;
			}

			.bold-style {
				position: relative;
			}

			.bold-style::before {
				content: '';
				position: absolute;
				left: 0;
				bottom: 0;
				width: 100%;
				height: 14rpx;
				background-color: #2a9d8f;
				border-radius: 10rpx;
				// z-index: 2;
			}
		}
	}
	.content {
		flex: 1;
		.noteList + .noteList {
			margin-top: 20rpx;
		}
	}
	.addIcon {
		position: fixed;
		width: 50rpx;
		bottom: 120rpx;
		right: 120rpx;
	}
}
</style>
