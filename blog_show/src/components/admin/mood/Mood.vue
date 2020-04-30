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
			<div class="mood-content" v-html="item.content">
			</div>
			<div class="mood-footer">
				<span class="mood-comment" @click="showComment(index)">
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
				<span class="mood-edit" @click="editMood(item.id)">
					<svg class="iconfont" style="font-size: 1.8em" aria-hidden="true">
					    <use xlink:href="#iconzhouyejiaoti"></use>
					</svg>
				</span>
				<span class="mood-delete" @click="delMood(item.id)">
					<svg class="iconfont" style="font-size: 1.8em" aria-hidden="true">
					    <use xlink:href="#iconxingxing"></use>
					</svg>
				</span>
			</div>
			<comment ref="comment"></comment>
		</div>
		<pagination ref="pagination" @selectPage="getMoods"></pagination>
		<new-blog @newBlog="newMood"></new-blog>
	</div>
</template>

<script>
	import NewBlog from "../blog/NewBlog";
	import Pagination from "../../common/Pagination";
	import Comment from "../../common/Comment";

    export default {
        name: "Mood",
        components: {Comment, Pagination, NewBlog},
        data() {
            return {
                moodPage: []
            }
	    },
	    created() {
            this.getMoods();
        },
	    mounted() {
            window.addEventListener("scroll", this.scrollInOut);
	    },
	    destroyed() {
            window.removeEventListener("scroll", this.scrollInOut);
	    },
	    methods: {
            scrollInOut() {
                let els = document.getElementsByClassName("mood-box");
                const innerH = window.innerHeight;
                const scrollH = document.documentElement.scrollTop;
                for (let el of els) {
                    const elH = el.offsetHeight;
                    let toTop = el.offsetTop + 104;
                    if (toTop < innerH + scrollH - 10 && toTop > scrollH - elH + 10) {
                        el.style.opacity = 1;
                        el.style.transform = "matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1)";
                    } else {
                        el.style.opacity = 0;
                        el.style.transform = "matrix3d(0.95, 0, 0, 0, 0, 0.95, 0, 0, 0, 0, 1, 0, 0, 50, 0, 1)";
                    }
                }
            },
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
		    editMood(id) {
                this.$router.push({
                    path: '/admin/mooding',
                    query: {
                        moodId: id,
                    }
                })
		    },
		    delMood(id) {
                this.$confirm('此操作将永久删除该动态，是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.post("/admin/mood/delete/"+id, {})
                        .then(response => {
                            if (response.data.status === 200) {
                                this.getMoods();
                                this.$message.success("删除成功");
                            }
                        })
                        .catch(error => {
                            console.log(error);
                        })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
		    },
            showComment(index) {
                const id = this.moodPage[index].id;
                if (this.$refs.comment[index].visible) {
                    this.$refs.comment[index].visible = false;
                } else {
                    this.$refs.comment[index].visible = true;
                    this.axios.get("/mood/" + id + "/comments")
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$refs.comment[index].comment.mood.id = id;
                                this.$refs.comment[index].comments = response.data.object;
                            }
                        })
                        .catch(error => {
                            console.log(error)
                        })
                }
            }
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
		background-color: rgba(255,255,255,0.7);
		border-radius: 6px;
		box-shadow: 0 0.3px 0.6px rgba(0, 0, 0, 0.056),
			0 0.7px 1.3px rgba(0, 0, 0, 0.081),
			0 1.3px 2.5px rgba(0, 0, 0, 0.1),
			0 2.2px 4.5px rgba(0, 0, 0, 0.119),
			0 4.2px 8.4px rgba(0, 0, 0, 0.144),
			0 10px 20px rgba(0, 0, 0, 0.2);
		transition: opacity 1s ease 0s, transform 1s ease 0s;
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
	.mood-content /deep/ img {
		width: 32%;
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
	.mood-footer .mood-edit {
		float: right;
		cursor: pointer;
	}
	.mood-footer .mood-delete {
		float: right;
		cursor: pointer;
	}
</style>