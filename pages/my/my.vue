<template>
	<view class="padding container">
	    <view class="mainly">
		    <img src="static/logo.png"  class="profile" />
	        <text style="border:0;position: absolute;left: 35%;	top:25%;">
		    昵称：
	        </text>
	        <input style="border:0;border-bottom:;background:;position: absolute;left: 50%;top:25%;width:100px;height:10px;">
	    </view>
	<view class="appendix">
		<view class="flex align-center text-lg padding-sm radius firstLine">
			<view class="firstLine-icon padding-xs radius"><u--image src="/static/icon/headPortrait.png" width="30px" height="30px"></u--image></view>
			<span class="padding-left">随便写点</span>
		</view>
		<view class="flex flex-direction text-lg padding-xs radius margin-top firstLine">
			<view class="flex justify-between">
				<span class="padding-left height">回收站</span>
				<u-icon name="arrow-right" size="20px" color="#303133"></u-icon>
			</view>
			<view class="flex justify-between">
				<span class="padding-left height">隐私政策</span>
				<u-icon name="arrow-right" size="20px" color="#303133"></u-icon>
			</view>
			<view class="flex justify-between">
				<span class="padding-left height">关于我们</span>
				<u-icon name="arrow-right" size="20px" color="#303133"></u-icon>
			</view>
		</view>
		<view class="flex align-center text-lg padding-xs radius margin-top firstLine" @tap="signOut"><span class="padding-left height">退出登录</span></view>
	</view>
	</view>

</template>
<script>
export default {
	data() {
		return {};
	},
	methods: {
		signOut() {
			uni.$u.route({
				type: 'reLaunch',
				url: 'pages/index/index'
			});
		}
	}
};
</script>    

<style lang="scss" scoped>
page {
	height: 100%;
}
.profile{
	position: absolute;
	left: 40%;
	top: 5%;
	width:100px;
	height:100px;
	border-radius:50%;
}
.container {
	height: 100%;
	background-color: white;
	.firstLine {
		border: 2px solid #585858;
		.firstLine-icon {
			border: 2px solid #c63434;
		}
		.height {
			height: 80rpx;
			line-height: 80rpx;
		}
	}
	.radius {
		border-radius: 20rpx;
	}
}

</style>
