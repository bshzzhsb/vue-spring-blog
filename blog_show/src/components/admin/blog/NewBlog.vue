<template>
	<div class="new-blog" @mouseover="hoverNew=true" @mouseout="hoverNew=false">
		<el-button class="plus-btn" v-if="hoverNew" @click="newBlog">
			<el-icon class="el-icon-plus"></el-icon>
		</el-button>
		<el-button class="left-arrow-btn" v-else>
			<el-icon class="el-icon-arrow-left"></el-icon>
		</el-button>
	</div>
</template>

<script>
    export default {
        name: "NewBlog",
	    data() {
            return {
                hoverNew: false,
            }
	    },
	    methods: {
            newBlog() {
                this.$emit("newBlog");
            },
	    }
    }
</script>

<style scoped>
	.new-blog {
		display: inline-block;
		position: fixed;
		top: 70px;
		right: 0;
	}
	.new-enter,
	.new-leave-to {
		opacity: 0;
	}
	.new-enter-active,
	.new-leave-active {
		transition: opacity 0.5s ease-in-out;
	}
	.left-arrow-btn {
		width: 25px;
		border-radius: 50px 0 0 50px;
		height: 50px;
		padding: 0 !important;
	}
	.el-icon-arrow-left {
		transform: translateX(2px);
	}
	.plus-btn {
		width: 40px;
		border-radius: 25px 0 0 25px;
		height: 50px;
		padding: 0 !important;
	}
</style>