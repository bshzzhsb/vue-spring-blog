<template>
	<div class="archive-container">
		<div class="archive-title m-fontf-cursive">Archive</div>
		<div class="timeline-container">
			<div v-for="(year,index) in Object.keys(map).reverse()" :key="year" class="timeline">
				<div class="timeline-year">{{year}}</div>
				<div v-for="(blog,i) in map[year]" :key="blog.id" class="timeline-box">
					<span class="timeline-time">{{ blog.createTime | formatTime('{m}月{d}日', false)}}</span>
					<el-popover placement="right" width="400" trigger="hover"
					            close-delay="0" popper-class="pop-blog">
						<div class="blog-pop">
							<img :src="blog.picture" alt="" class="blog-img">
							<p class="blog-abs">{{ blog.abs }}</p>
						</div>
						<span slot="reference" class="blog-title-container" @click="goBlog(blog.id)">
							<span class="blog-title">{{ blog.title }}</span>
						</span>
					</el-popover>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
    export default {
        name: "Archive",
	    data() {
            return {
                map: {},
            }
	    },
	    mounted() {
            this.getBlogs();
	    },
	    methods: {
            getBlogs() {
                this.axios.get("/blog/total")
	                .then(response => {
	                    if (response.data.status === 200) {
	                        this.map = response.data.object;
	                    }
	                })
	                .catch(error => {
                        console.log(error)
	                })
            },
		    goBlog(id) {
                this.$router.push({ path: '/blog/'+id })
		    }
	    }
    }
</script>

<style scoped>
	.archive-container {
		text-align: left;
		box-sizing: border-box;
	}
	.archive-title {
		color: rgba(0,0,0,1);
		font-weight: 500;
		font-size: 1.8rem;
		text-shadow: 0 0 5px #b2b2b2;
		margin-bottom: 10px;
	}
	.timeline-container {
		position: relative;
	}
	.timeline-container::before {
		content: '';
		z-index: 0;
		position: absolute;
		width: 4px;
		left: 88px;
		top: 10px;
		height: 100%;
		background-color: #dcdcdc;
		float: left;
	}
	.timeline-year {
		position: relative;
		display: inline-block;
		margin: 10px 0;
		padding: 0 10px;
		border-radius: 20px;
		line-height: 30px;
		height: 30px;
		background-color: #6f6f6f;
		color: #ffffff;
		left: 60px;
		font-size: 16px;
	}
	.timeline-box {
		position: relative;
		padding-left: 90px;
		height: 32px;
		box-sizing: border-box;
		line-height: 32px;
		margin-bottom: 5px;
	}
	.timeline-box::before {
		box-sizing: border-box;
		content: '';
		position: absolute;
		width: 20px;
		height: 20px;
		line-height: 20px;
		text-align: center;
		top: 6px;
		left: 80px;
		border-radius: 50%;
		color: #ffffff;
		background-color: #8a8a8a;
	}
	.timeline-time {
		text-align: right;
		width: 64px;
		box-sizing: border-box;
		position: absolute;
		color: #6f6f6f;
		font-size: 14px;
		top: 0;
		left: 10px;
		height: 32px;
		line-height: 32px;
	}
	.blog-title-container {
		display: inline-block;
		height: 32px;
		line-height: 32px;
		position: relative;
		box-sizing: border-box;
		left: 16px;
		color: #2f2f2f;
		text-shadow: 1px 1px 2px rgba(0,0,0,0.15);
		transition: all 0.3s ease;
		cursor: pointer;
	}
	.blog-title-container::before {
		box-sizing: border-box;
		content: '';
		position: absolute;
		width: 12px;
		height: 12px;
		line-height: 12px;
		text-align: center;
		border-radius: 50%;
		left: -22px;
		top: 10px;
		background-color: #fff;
	}
	.blog-title {
		display: inline-block;
		height: 32px;
		line-height: 32px;
	}
	.blog-title::before {
		content: '';
		display: block;
		width: 0;
		height: 2px;
		position: absolute;
		left: 0;
		bottom: 0;
		background-color: #6f6f6f;
		transition: all 0.5s ease-in-out;
	}
	.blog-title-container:hover .blog-title::before {
		width: 100%;
	}
	.blog-title-container:hover::before {
		background-color: #333;
	}
	.blog-pop .blog-img {
		width: 100%;
	}
	.blog-pop .blog-abs {
		padding: 6px 10px;
	}
</style>

<style lang="css">
	.pop-blog {
		padding: 2px !important;
		background-color: #fff;
	}
</style>