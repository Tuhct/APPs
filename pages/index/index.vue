<template>
	<view class="start-up">
		<view class="catImage"><u--image :showLoading="true" :src="src" width="120px" height="120px"></u--image></view>
		<view class="catButton"><u-button type="primary" size="medium" text="开启" color="#140508" @tap="startUp"></u-button></view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			show: true,
			src: '/static/bak/potatoChips.png'
		};
	},
	onReady() {},
	onLoad() {},
	methods: {
		startUp() {
			// uni.$u.route默认是直接跳转到改地址
			uni.switchTab({url:'/pages/clock/clock'});
		}
	}
};
</script>

<style lang="scss" scoped>
.start-up {
	height: 100%;
	display: flex;
	justify-content: center;
	background-color: white;
	overflow: hidden;
	padding-top: 80px;

	@-webkit-keyframes zy {
		10% {
			transform: rotate(5deg);
		}
		20% {
			transform: rotate(-5deg);
		}
		30% {
			transform: rotate(5deg);
		}
		40% {
			transform: rotate(-5deg);
		}
		50%,
		100% {
			transform: rotate(0deg);
		}
	}

	.catImage {
		animation: zy 2.5s 0.15s linear infinite;
		margin-top: 350rpx;
	}

	.catButton {
		width: 200rpx;
		position: absolute;
		bottom: 450rpx;
		left: 50%;
		transform: translateX(-50%);
	}
}
</style>
