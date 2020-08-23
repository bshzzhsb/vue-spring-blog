<template>
	<div class="blog-container">
		<div v-if="loading" class="loading"></div>
		<div class="blog-img">
			<img :src="blog.picture" alt="">
		</div>
		<div class="blog-con">
			<div class="blog-flag"><span>{{ blog.flag }}</span></div>
			<div class="blog-title">{{ blog.title }}</div>
			<div class="blog-info">
				<span class="blog-time">
					<svg class="iconfont" style="font-size: 1em" aria-hidden="true">
					    <use xlink:href="#icontime-circle"></use>
					</svg>
					{{ blog.createTime | formatTime('{y}-{m}-{d}') }}
				</span>
				<span class="blog-type">
					<svg class="iconfont" style="font-size: 1em" aria-hidden="true">
					    <use xlink:href="#iconmenu2"></use>
					</svg>
					{{ blog.type.name }}
				</span>
				<span class="blog-view">
					<svg class="iconfont" style="font-size: 1.1em" aria-hidden="true">
					    <use xlink:href="#iconchakan"></use>
					</svg>
					{{ blog.views }}
				</span>
			</div>
			<div class="blog-abs">
				<blockquote>{{ blog.abs }}</blockquote>
			</div>
			<hr>
			<div class="blog-content" v-html="blog.contentHtml"></div>
			<div class="blog-last-time">
				<svg class="iconfont" style="font-size: 1em" aria-hidden="true">
				    <use xlink:href="#icontime-circle"></use>
				</svg>
				最后一次更新于 {{ blog.updateTime | formatTime('{y}-{m}-{d} {h}:{i}')}}
			</div>
			<div class="blog-tags">
				<span v-for="(item,index) in blog.tags" :key="item.id">
					{{ item.name }}
				</span>
			</div>
			<div v-if="blog.appreciation">
				<el-popover placement="top" width="300" trigger="hover"
				            popper-class="pop-img">
					<img src="../../../assets/images/alipay.jpg" alt="">
					<img src="../../../assets/images/wechat.png" alt="">
					<el-button slot="reference" class="blog-appre">赏</el-button>
				</el-popover>
			</div>
			<div v-if="blog.shareStatement" class="blog-copyright">
				<p>本文标题:
					<span class="blog-ca">{{ blog.title }}</span>
				</p>
				<p>本文作者:
					<span class="blog-ca">博思何在</span>
				</p>
				<p>发布时间:
					<span>{{ blog.createTime | formatTime('{y}-{m}-{d}')}}</span>
				</p>
				<p>版权声明:
					转载前请联系作者
				</p>
			</div>
		</div>
		<div></div>
	</div>
</template>

<script>
	import hljs from 'highlight.js'
	import 'highlight.js/styles/atom-one-light.css'

    export default {
        name: "BlogShow",
        data() {
            return {
                blog: { type: {}, },
                loading: true,
            }
        },
        mounted() {
	        hljs.initHighlightingOnLoad();
        },
	    methods: {

	    }
    }
</script>

<style scoped>
	.blog-container {
		border-radius: 14px 14px 4px 4px;
		background-color: #fff;
		border: 1px solid #d4d4d5;
		position: relative;
	}
	.blog-img {
		border-radius: 14px 14px 4px 4px;
		overflow: hidden;
		width: 100%;
	}
	.blog-img img {
		width: 100%;
	}
	.blog-con {
		padding: 1em 2em;
	}
	.blog-con .blog-flag {
		text-align: right;
	}
	.blog-con .blog-flag span {
		border: 1px solid var(--orange);
		border-radius: 4px;
		padding: 3px 5px;
		color: var(--orange);
		font-weight: 600;
		font-size: 12px;
	}
	.blog-con .blog-info {
		margin-bottom: 15px;
		color: rgba(0,0,0,0.7);
	}
	.blog-info span + span {
		margin-left: 5px;
	}
	.blog-con .blog-title {
		font-size: 1.5em;
		font-weight: 600;
		margin-bottom: 10px;
	}
	.blog-con .blog-abs {
		text-align: left;
		border-top: 1px dashed #dadada;
		padding: 10px 0;
	}
	.blog-abs blockquote {
		border-left: 3px solid #333;
		font-size: 15px;
		color: #999;
		margin: 1em 1em;
		padding-left: 1em;
	}
	.blog-con hr {
		background-color: #ddd;
		width: 90%;
		height: 3px;
		background-image: repeating-linear-gradient(-45deg,#fff,#fff 4px,transparent 4px,transparent 8px) !important;
		border: none;
	}
	.blog-con .blog-content {
		text-align: left;
		line-height: 28px;
		margin-top: 2em;
	}
	.blog-content /deep/ p {
		font-size: 15px;
		margin-bottom: 10px;
	}
	.blog-con .blog-tags {
		text-align: left;
	}
	.blog-content /deep/ h1,
	.blog-content /deep/ h2,
	.blog-content /deep/ h3,
	.blog-content /deep/ h4,
	.blog-content /deep/ h5,
	.blog-content /deep/ h6 {
		margin-top: 10px;
		margin-bottom: 10px;
	}
	.blog-content /deep/ pre {
		margin-bottom: 5px;
		line-height: 1.5;
		font-size: 14px !important;
		border-radius: 6px;
		overflow: hidden;
	}
	.blog-content /deep/ blockquote {
		font-size: 15px;
		text-align: left;
		border-left: 3px solid #333;
		color: #888;
		margin: 1em 1em;
		padding-left: 1em;
	}
	.blog-content /deep/ blockquote p:first-letter {
		margin-left: 0;
	}
	.blog-content /deep/ a {
		color: var(--blue);
	}
	.blog-content /deep/ ul {
		margin-left: 1.5em;
	}
	.blog-content /deep/ ul > li {
		list-style-type: disc;
	}
	.blog-content /deep/ ol {
		margin-left: 1.5em;
	}
	.blog-content /deep/ ol > li {
		list-style-type: decimal;
	}
	.blog-con .blog-last-time {
		margin-top: 10px;
		text-align: right;
		font-size: 12px;
		color: rgba(0,0,0,0.6);
	}
	.blog-con .blog-tags {
		margin-top: 10px;
		border-top: 1px dashed #dadada;
		border-bottom: 1px dashed #dadada;
		padding: 1em 0.5em;
		text-align: left;
	}
	.blog-tags span {
		border: 1px solid rgba(34,36,38,.15);
		color: rgba(0,0,0,0.87);
		margin-right: 10px;
		padding: 7px 10px;
		border-radius: 5px;
		font-weight: 600;
		cursor: pointer;
		opacity: 0.5;
	}
	.blog-tags span:hover {
		opacity: 1;
	}
	.blog-appre {
		margin-top: 10px;
		width: 40px;
		height: 40px;
		border-radius: 50%;
		padding: 0;
		border-color: var(--orange);
		color: var(--orange);
	}
	.blog-appre:hover {
		color: var(--white);
		background-color: var(--orange);
	}
	.blog-copyright {
		text-align: left;
		margin: 1em 0 0 0;
		padding: 1em 1em;
		border-left: 3px solid #555;
		background-color: #f9f9f9;
	}
	.blog-copyright .blog-ca {
		cursor: pointer;
		color: var(--blue);
	}
	.blog-copyright .blog-ca:hover {
		color: #0052FF;
	}
</style>

<style lang="css">
	.pop-img {
		font-size: 0 !important;
		padding: 0;
		border-radius: 5px;
		overflow: hidden;
	}
	.pop-img img {
		width: 150px;
		display: inline-block;
	}
</style>
