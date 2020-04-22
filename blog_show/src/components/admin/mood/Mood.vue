<template>
	<div class="mood-container">
		<div v-for="(item,index) in moodPage" class="mood-box" :key="item.id">
			<div class="mood-header">
				<span class="header-name">博思何在</span>
				<div class="mood-profile-container">
					<img src="../../../assets/images/doraemon.jpg" alt="" class="mood-profile">
				</div>
				<span class="header-time">{{ item.createTime | formatTime('{y}-{m}-{d}') }}</span>
			</div>
			<div class="mood-content">
				{{ item.content }}
			</div>
			<div class="mood-footer">
				<span class="mood-comment">
					<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
					    <use xlink:href="#iconpinglun"></use>
					</svg>
					<span>评论</span>
				</span>
				<span class="mood-likes">
					<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
					    <use xlink:href="#iconxihuan"></use>
					</svg>
					<span v-if="item.likes">{{ item.likes }}</span>
				</span>
			</div>
		</div>
		<pagination ref="pagination" @selectPage="getMoods"></pagination>
		<new-blog @newBlog="newMood"></new-blog>
	</div>
</template>

<script>
	import NewBlog from "../blog/NewBlog";
	import Pagination from "../blog/Pagination";

    export default {
        name: "Mood",
        components: {Pagination, NewBlog},
        data() {
            return {
                moodPage: {}
            }
	    },
	    created() {
            this.getMoods();
        },
	    methods: {
            getMoods(page=0) {
                this.axios.get("/admin/mood?page="+page)
	                .then(response => {
	                    if (response.data.status === 200) {
	                        var object = response.data.object;
                            this.moodPage = object.content;
                            this.$refs.pagination.totalPages = object.totalPages;
                            this.$refs.pagination.currentPage = object.number+1;
                            this.$nextTick(() => {
                                window.scrollTo({
                                    top:0,
                                    behavior: "smooth",
                                });
                            });
	                    }
	                })
	                .catch(error => {
	                    console.log(error)
	                })
            },
            newMood() {
                this.$router.push({ path: '/admin/mooding'})
            },
	    }
    }
</script>

<style scoped>
	.mood-container {
		min-height: calc(100vh - 198px);
	}
	.mood-box {
		text-align: left;
		margin-bottom: 2em;
		border: 1px solid rgba(0,0,0,0.1);
		border-radius: 6px;
		box-shadow: 0 0.3px 0.6px rgba(0, 0, 0, 0.056),
			0 0.7px 1.3px rgba(0, 0, 0, 0.081),
			0 1.3px 2.5px rgba(0, 0, 0, 0.1),
			0 2.2px 4.5px rgba(0, 0, 0, 0.119),
			0 4.2px 8.4px rgba(0, 0, 0, 0.144),
			0 10px 20px rgba(0, 0, 0, 0.2);
	}
	.mood-header {
		padding: 5px 1em;
		height: 40px;
		line-height: 40px;
		position: relative;
		border-bottom: 1px solid rgba(0,0,0,0.05);
	}
	.mood-profile-container {
		width: 40px;
		height: 40px;
		display: block;
		position: relative;
		border-radius: 50%;
		float: left;
		border: 0.5px solid rgba(0,0,0,0.05);
	}
	.mood-profile {
		width: 40px;
		height: 40px;
		border-radius: 50%;
		box-shadow: 2px 2px 3px rgb(224,224,224) !important;
		max-width: 100%;
	}
	.mood-header .header-name {
		padding-left: 10px;
	}
	.mood-header .header-time {
		float: right;
	}
	.mood-content {
		padding: 20px 30px;
	}
	.mood-footer {
		border-top: 1px solid rgba(0,0,0,0.05);
		padding: 10px 1em;
	}
	.mood-footer > span + span {
		margin-left: 10px;
	}
	.mood-footer .mood-comment {
		cursor: pointer;
	}
	.mood-footer .mood-likes {
		cursor: pointer;
	}
</style>