<template>
	<view>
		<view class="box">
			
			<view class="cards radius">
				<view class="cover shadow" :animation="animationMain">
					<view class="container">
						<view class="times">
							<span class="din dates-day">{{ dateNum }}</span>
				            <span class="din dates-info">{{ timeNum }}</span>
							<span>{{ hour }} : {{ minutes }}</span>
							<span style="font-size: 38upx;margin-left: 10upx;;">{{ seconds }}s</span>
						</view>
						</view>
					<view class="cover-img" :style="'background-image: url(https://img.implay.co/pulai/file/0/1628697715213082624.jpg);'"></view>
					<view class="cover-info">
						<view class="cover-dot">时间会刺破青春表面的彩饰，会在美人的额上掘深沟浅槽；会吃掉稀世之珍！天生丽质，什么都逃不过他那横扫的镰刀。</view>
						<view class="cover-author">
							<text class="gray">-</text> 莎士比亚 <text class="gray">-</text>
						</view>
					</view>
					<view class="cover-sign" data-id="1" @click="toRotate">拾</view>
				</view> 
				<view class="signs shadow" data-id="2" :animation="animationBack">
					<view class="times">
						<span class="din dates-day">{{ dateNum }}</span>
					    <span class="din dates-info">{{ timeNum }}</span>
					</view>
					<view class="cover-img" :style="'background-image: url(https://img.implay.co/pulai/file/0/1628697715213082624.jpg);'"></view>
					<view class="cover-info">
						<view class="cover-dot">时间会刺破青春表面的彩饰，会在美人的额上掘深沟浅槽；会吃掉稀世之珍！天生丽质，什么都逃不过他那横扫的镰刀。</view>
						<view class="cover-author">
							<text class="gray">-</text> 莎士比亚 <text class="gray">-</text>
						</view>
					</view>
					<view class="signs-back" @click="toRotate">
						<text >Re</text>
					</view>
				</view>
			</view>
			
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			const now = new Date()
			return {
				animationMain: null,
				animationBack: null,
				dateNum: '',
				timeNum: '',
				statusBarHeight: 20,
				hour: now.getHours() < 10 ? '0' + now.getHours() : now.getHours(), //当小时为个为数时在在前加0（例：01），以下同理
				minutes: now.getMinutes() < 10 ? '0' + now.getMinutes() : now.getMinutes(),
				seconds: now.getSeconds() < 10 ? '0' + now.getSeconds() : now.getSeconds(),
				timer: null,
				x: 0
			}
		},
		onLoad() {
			this.initDate()
			
			this.animation_main = uni.createAnimation({
				duration: 500,
				timingFunction: 'linear'
			})
			this.animation_back = uni.createAnimation({
				duration: 500,
				timingFunction: 'linear'
			})
			
			if (this.timer) {
				clearInterval(this.timer)
			}
			setInterval(() => {
				this.getTime()
			},1000)
		},
		mounted() {
			this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight + 'px'
		},
		methods: {
			getTime () {
				var date = new Date()
				this.hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
				this.minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
				this.seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
			},
			onNavigationBarButtonTap(e){
			    uni.navigateTo({
			        url: './wish/wish'
			    })
			},
			// 卡片翻转
			toRotate(e) {
				const id = e.currentTarget.dataset.id
				
				if(id == 1) {
					this.animation_main.rotateY(180).step()
					this.animation_back.rotateY(0).step()
				} else {
					this.animation_main.rotateY(0).step()
					this.animation_back.rotateY(-180).step()
				}
				this.animationMain = this.animation_main.export()
				this.animationBack = this.animation_back.export()
			},
			
			onNavigationBarButtonTap(e){
			    uni.navigateTo({
			        url: './wish/wish'
			    })
			   },
			// 初始化日期
			initDate() {
				const time = new Date(), dayArr = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'], enMonth = ['Jan.', 'Feb.', 'Mar.', 'Apr.', 'May.', 'Jun.', 'Jul.', 'Aug.', 'Sep.', 'Oct.', 'Nov.', 'Dec.']
		
				let d = time.getDate(), m = time.getMonth(), y = time.getFullYear(), x = time.getDay()
				d = d < 10 ? '0' + d : d
				
				this.dateNum = d
				this.timeNum = enMonth[m] + y + ' ' + dayArr[x]
			}
			
		}
	}
</script>

<style>
	.container {
		margin: 0 auto;
	}
	
	.times {
		margin-left: 0upx ;
		font-size: 60upx;
		padding-top: 60upx;
		font-weight: bold;
	}
	.movearea {
	  transition: 0.3s background-color ease;
	}
	
	.dates {
		height: 44px;
		display: flex;
		align-items: flex-end;
		padding: 0 25rpx 10rpx;
		/* #ifdef MP-WEIXIN */
		padding: 0 130rpx 10rpx 25rpx;
		/* #endif */
	}
	
	.dates-day {
		font-size: 35rpx;
		font-weight: bold;
	}
	
	.dates-info {
		font-size: 22rpx;
		margin: 0 0 7rpx 7rpx;
	}
	
	.box {
		position: relative;
		background-color: #f6f7f8;
		background-size: 100% auto;
		background-repeat: no-repeat;
		background-attachment: fixed;
	}
	
	.cards {
		position: relative;
		overflow: hidden;
		perspective: 1500;
		transform-style: preserve-3d;
		margin: 0rpx 25rpx 10rpx;
	}
	
	.cover {
		min-height: 860rpx;
		position: relative;
		display: flex;
		flex-direction: column;
		overflow: hidden;
		backface-visibility: hidden;
		transition: all 1s;
		background-color: rgba(255, 255, 255, .97);
	}
	
	.cover-img {
		flex: 1;
		background-size: cover;
		background-position: center;
		background-repeat: no-repeat;
	}
	
	.cover-info {
		flex-shrink: 0;
		margin-top: 20rpx;
	}
	
	.cover-dot {
		margin: 50rpx 50rpx 70rpx;
		line-height: 52rpx;
	}
	
	.cover-author {
		text-align: center;
		margin-bottom: 70rpx;
	}
	
	.cover-sign {
		position: absolute;
		bottom: -12rpx;
		right: -12rpx;
		height: 86rpx;
		width: 86rpx;
		font-size: 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #f1d168;
		border-top-left-radius: 86rpx;
		background-color: rgba(255, 255, 255, .97);
		box-shadow: -5rpx 6rpx 12rpx rgba(100, 100, 100, 0.1);
	}
	
	.signs {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		min-height: 100%;
		padding: 25rpx 30rpx;
		backface-visibility: hidden;
		transition: all 1s;
		transform: rotateY(-180deg);
		background-color: rgba(255, 255, 255, .97);
	}
	
	.signs-tit {
		font-size: 32rpx;
		font-weight: 500;
		margin-bottom: 20rpx;
	}
	
	.signs-des {
		max-height: 680rpx;
		position: relative;
		margin-bottom: 20rpx;
		line-height: 48rpx;
	}
	
	.signs-nav {
		position: absolute;
		bottom: 20rpx;
		left: 0;
		right: 0;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.signs-nav navigator {
		color: #fff;
		height: 62rpx;
		width: 200rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		background-color: rgba(122, 175, 193, .58);
	}
	
	.signs-back {
		position: absolute;
		bottom: -22rpx;
		right: -20rpx;
		height: 86rpx;
		width: 86rpx;
		font-size: 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #f1d168;
		border-top-left-radius: 86rpx;
		background-color: rgba(255, 255, 255, .97);
		box-shadow: -5rpx 6rpx 12rpx rgba(100, 100, 100, 0.1);
	}
	
	.signs-back text {
		opacity: .7;
	}
	/*每个页面公共css */
	page {
		background-color: #f6f7f8;
		color: #393c3e;
		font-size: 30rpx;
		font-family: system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", "Liberation Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
		-webkit-overflow-scrolling: touch
	}
	
	view, scroll-view, swiper, button, input, text, textarea, label, navigator, image {
	    box-sizing: border-box;
	}
	
	.radius {
		border-radius: 8rpx;
	}
	
	.round {
		border-radius: 5000rpx;
	}
	
	.shadow {
		box-shadow: 0 6px 17px rgb(170 170 170 / 10%);
	}
	
	.gray {
		color: #aaa;
	}
	
	.red {
		color: #ed6666;
	}
	
	.head {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 1000;
		background-color: rgba(255, 255, 255, 0);
	}
	
	.blank {
		height: 44px;
	}
	
	.status-bar {
		height: 20px;
	}
	
	/* 没有更多了 */
	.more {
		width: 100%;
		height: 120rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #becdd5;
		font-size: 26rpx;
	}
	
	@font-face {
		font-family: "iconfont"; /* Project id 3815441 */
		src: url('data:font/ttf;charset=utf-8;base64,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') format('truetype');
	}
	
	.icon {
		font-family: "iconfont" !important;
		font-size: 18px;
		font-style: normal;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
	}
	
	.icon-back:before {
		content: "\e602";
	}
	
	.icon-backa:before {
		content: "\e601";
	}
	
	.icon-arrow:before {
		content: "\e7b9";
	}
	
	.icon-qr:before {
		content: "\e73a";
	}
</style>
