<template>
	<el-card class="" shadow="hover">
		<div slot="header" class="clearfix">
			<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
			    <use xlink:href="#icontrending"></use>
			</svg>
			<span>GitHub Trending</span>
		</div>
		<el-scrollbar class="trending-main">
			<div v-if="loading" class="loading"></div>
			<div v-for="(item,i) in trendings" :key="i">
				<span>{{ item.rank }}.</span>
				<a :href="item.url" target="_blank">
					<span>{{ item.trendingName }}</span>&nbsp;/&nbsp;
					<span>{{ item.developer }}</span>
				</a>
				<span class="trending-lang">{{ item.language }}</span>
				<p class="trending-des">{{ item.description }}</p>
			</div>
		</el-scrollbar>
	</el-card>
</template>

<script>
    export default {
        name: "GitHubTrending",
	    data() {
            return {
                trendings: {},
	            loading: true,
            }
	    },
	    created() {
            this.axios.get("/admin/home/githubtrending")
	            .then(response => {
	                if (response.data.status === 200) {
	                    this.trendings = response.data.object;
                        this.$nextTick(() => {
                            this.loading = false;
                        })
	                }
	            })
	            .catch(error => {
	                console.log(error);
	            })
        }
    }
</script>

<style scoped>
	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}
	.clearfix:after {
		clear: both
	}
	.el-card {
		text-align: left;
	}
	.trending-main {
		height: 80vh;
	}
	.el-scrollbar {
		position: relative;
	}
	.el-scrollbar /deep/ .el-scrollbar__wrap {
		overflow-x: auto;
		overflow-y: scroll;
	}
	.el-card a {
		color: #2c3e50;
	}
	.trending-lang {
		float: right;
		padding-right: 5px;
		font-weight: 400;
		opacity: 0.8;
	}
	.trending-des {
		font-weight: 300;
		margin-bottom: 1em;
	}
</style>