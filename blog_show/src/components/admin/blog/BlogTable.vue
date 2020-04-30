<template>
	<div class="blog-table">
		<table class="table table-bordered">
			<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">标题</th>
				<th scope="col">分类</th>
				<th scope="col">推荐</th>
				<th scope="col">状态</th>
				<th scope="col">发布日期</th>
				<th scope="col">最后更新</th>
				<th scope="col">操作</th>
			</tr>
			</thead>
			<tbody>
			<div v-if="loading" class="loading"></div>
			<tr v-for="(item,index) in blogPage" class="blog-tr">
				<th scope="row">{{ pageNum*pageSize+index+1 }}</th>
				<td>{{ item.title }}</td>
				<td>{{ item.type.name }}</td>
				<td>
					<svg v-if="item.recommend" class="iconfont" style="font-size: 1.2em" aria-hidden="true">
						<use xlink:href="#iconcheckmark"></use>
					</svg>
				</td>
				<td>
					<svg v-if="item.published" class="iconfont" style="font-size: 1.2em" aria-hidden="true">
						<use xlink:href="#iconcheckmark"></use>
					</svg>
				</td>
				<td>{{ item.createTime | formatTime('{y}-{m}-{d}') }}</td>
				<td>{{ item.updateTime | formatTime('{y}-{m}-{d}') }}</td>
				<td>
					<div>
						<span class="edit-icon" @click="editBlog(item.id)">
							<svg class="iconfont" style="font-size: 20px" aria-hidden="true">
							    <use xlink:href="#iconedit"></use>
							</svg>
						</span>
						<span class="delete-icon" @click="deleteBlog(item.id)">
							<svg class="iconfont" style="font-size: 24px" aria-hidden="true">
								<use xlink:href="#icondelete"></use>
							</svg>
						</span>
					</div>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
    export default {
        name: "BlogTable",
	    data() {
            return {
                blogPage: [],
                pageNum: 0,
	            pageSize: 3,
	            loading: true,
            }
	    },
	    mounted() {
            this.$emit("getBlog", 0);
        },
	    methods: {
            editBlog(id) {
	            this.$router.push({
		            path: '/admin/blogging',
		            query: {
		                blogId: id,
		            }
	            })
            },
		    deleteBlog(id) {
                this.$confirm('此操作将永久删除该博客，是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.post("/admin/blog/delete/"+id, {})
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$emit("getBlog", 0);
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
		    }
	    }
    }
</script>

<style scoped>
	.table {
		width: 100%;
		margin-bottom: 1rem;
		color: #212529;
		overflow-x: scroll;
		position: relative;
	}
	.table th,
	.table td {
		padding: 0.5rem;
		vertical-align: middle;
		min-width: 40px;
	}
	.table thead th {
		vertical-align: bottom;
		background-color: #f9fafb;
		border-bottom: 1px solid rgba(34,36,38,.1);
	}
	.table tbody + tbody {
		border-top: 2px solid #dee2e6;
	}
	.table-bordered {
		border: 1px solid #dee2e6;
		border-radius: 4px;
		border-top: 2px solid blue;
		border-collapse: separate;
	}
	.table thead tr:first-child th:first-child {
		border-radius: 4px 0 0 0;
	}
	.table thead tr:first-child th:last-child {
		border-radius: 0 4px 0 0;
	}
	.blog-tr {
		box-shadow: 0 -1px 0 0 rgba(0,0,0,.1) inset;
	}
	.el-icon-check {
		font-size: 16px;
		font-weight: 700;
		color: green;
	}
	@media screen and (max-width: 768px) {
		.table th,
		.table td{
			display: block;
		}
		.table thead th {
			border-bottom: none;
		}
	}
	.edit-icon {
		cursor: pointer;
	}
	.delete-icon {
		cursor: pointer;
		margin-left: 5px;
	}
</style>