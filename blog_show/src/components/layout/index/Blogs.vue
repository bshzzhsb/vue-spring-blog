<template>
	<div class="blogs">
		<div v-for="(item,index) in blogPage" class="blog-box">
			<div class="blog-img">
				<div v-if="loading" class="loading"></div>
				<img @click="goBlog(item.id)" :src="item.picture" alt="">
			</div>
			<div class="blog-con">
				<div class="blog-time">
					<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
					    <use xlink:href="#icontime-circle"></use>
					</svg>
					<span>发布于{{ item.createTime | formatTime('{y}-{m}-{d}') }}</span>
				</div>
				<div @click="goBlog(item.id)" class="blog-title m-fontf-noto">{{ item.title }}</div>
				<div class="blog-info">
					<span>
						<el-icon class="el-icon-view"></el-icon>
						{{ item.views }}热度
					</span>
					<span @click="goType(item.type.id)">
						<el-icon class="el-icon-folder"></el-icon>
						{{ item.type.name }}
					</span>
				</div>
				<div class="blog-abs m-fontf-noto">{{ item.abs }}</div>
			</div>
		</div>
	</div>
</template>

<script>
    export default {
        name: "Blogs",
	    data() {
            return {
                blogPage: [],
                loading: true,
            }
	    },
	    methods: {
            goBlog(id) {
                this.$router.push({ path: '/blog/'+id })
            },
		    goType(typeId) {
                this.$router.push({
	                path: '/type',
	                query: {'typeId': typeId},
                })
		    },
	    }
    }
</script>

<style scoped>
	.blogs {
		font-size: 0;
	}
	.blog-box {
		display: block;
		height: 250px;
		box-shadow: 0 1px 3px 0 #d4d4d5,0 0 0 1px #d4d4d5;
		border-radius: 14px;
		margin-bottom: 36px;
		background-color: #fff;
	}
	.blog-box:hover {
		box-shadow: 0 5px 10px 5px rgba(110,110,110,.4) !important;
		transform: translateY(-3px);
		transition: transform 0.3s ease-in-out;
	}
	.blog-img {
		position: relative;
		width: 55%;
		display: inline-block;
	}
	.blog-box:nth-child(2n+1) .blog-img {
		float: left;
		height: 100%;
		border-radius: 14px 0 0 14px;
		overflow: hidden;
	}
	.blog-box:nth-child(2n+2) .blog-img {
		float: right;
		height: 100%;
		border-radius: 0 14px 14px 0;
		overflow: hidden;
	}
	.blog-img img {
		width: 100%;
		height: 100%;
		cursor: pointer;
		transition: transform 0.3s ease-in-out;
		display: block;
	}
	.blog-img img:hover {
		transform: scale(1.05);
	}
	.blog-con {
		box-sizing: border-box;
		font-size: 14px;
		width: 45%;
		height: 100%;
		display: inline-block;
		padding: 10px 20px;
	}
	.blog-box:nth-child(2n+1) .blog-con {
		text-align: right;
	}
	.blog-box:nth-child(2n+2) .blog-con {
		text-align: left;
	}
	.blog-con .blog-time {
		font-size: 12px;
		color: rgba(0,0,0,0.4);
	}
	.blog-con .blog-title {
		padding: 5px 0;
		font-size: 20px;
		cursor: pointer;
	}
	.blog-con .blog-info {
		font-size: 12px;
		color: rgba(0,0,0,0.6);
		margin-bottom: 10px;
	}
	.blog-con .blog-info span {
		cursor: pointer;
	}
	.blog-con .blog-info span + span {
		margin-left: 5px;
	}
	.blog-con .blog-abs {
		letter-spacing: 1px;
	}
	.mobile .blog-box {
		width: 100%;
		height: auto;
	}
	.mobile .blog-img {
		width: 100%;
		display: block;
		float: none !important;
		height: auto;
		border-radius: 14px 14px 0 0 !important;
	}
	.mobile .blog-img img {
		border-radius: 14px 14px 0 0 !important;
		height: 200px;
	}
	.mobile .blog-con {
		text-align: left !important;
		box-sizing: border-box;
		font-size: 14px;
		width: 100%;
		height: auto;
		display: block;
		padding: 10px 20px 20px 20px;
	}
</style>