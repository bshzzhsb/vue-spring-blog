<template>
	<div class="tag-table">
		<el-card>
			<el-popover :key="item.id" v-for="(item,index) in tagList"
			            placement="top" width="200" class="pop-tag"
			            @hide="cancelEdit"
			            :value="editVisible">
				<el-input v-model="item.name" type="text">
					<el-button slot="append" type="primary" size="mini" @click="edit(index)">确定</el-button>
				</el-input>
				<el-tag slot="reference" closable
				        @close="handleDelete(item.id)">
					{{item.name}}
				</el-tag>
			</el-popover>
			<el-input class="input-new-tag" v-if="inputVisible"
			          v-model="tag.name" ref="saveTagInput"
			          size="small" @keyup.enter.native="handleInputConfirm"
			          @blur="cancel">
			</el-input>
			<el-button v-else class="button-new-tag" size="small" @click="showInput">添加标签</el-button>
		</el-card>
	</div>
</template>

<script>
    export default {
        name: "TagTable",
        data() {
            return {
                tagList: [],
                tagListTemp: [],
                inputVisible: false,
                tag: { name: '' },
                editVisible: false,
            }
        },
        created() {
            this.getTag();
        },
        methods: {
            getTag() {
                this.axios.get("/admin/tag")
                    .then(response => {
                        this.tagList = response.data.object;
                        this.tagListTemp = JSON.parse(JSON.stringify(this.tagList));
                    })
                    .catch(error => {
                        console.log(error);
                    })
            },
            handleInputConfirm() {
                if (!this.tag.name) {
                    this.$message.warning("不能添加空标签");
                } else {
                    this.axios.post("/admin/tag", this.tag)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.inputVisible = false;
                                this.tag = { name: '' };
                                this.getTag();
                                this.$message.success("添加成功");
                            } else if (response.data.status === 400) {
                                this.$message.warning("标签已存在");
                            }
                        })
                        .catch(error => {
                            console.log(error);
                            this.$message.success("添加失败");
                        })
                }
            },
            cancel() {
                this.inputVisible = false;
                this.tag.name = '';
            },
            edit(index) {
                if (this.tagList[index].name === this.tagListTemp[index].name) {
                    this.$message({
                        type: 'info',
                        message: '未改变',
                        duration: 1000,
                    })
                } else {
                    this.axios.post("/admin/tag", this.tagList[index])
                        .then(response => {
                            if (response.data.status === 200) {
                                this.getTag();
                                this.editVisible = false;
                                this.$message.success("更新成功");
                            }
                        })
                        .catch(error => {
                            console.log(error);
                        })
                }
            },
            cancelEdit() {
                // 深拷贝
                this.tagList = JSON.parse(JSON.stringify(this.tagListTemp));
                this.editVisible = false;
            },
            handleDelete(id) {
                this.$confirm('此操作将永久删除该标签，是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.post("/admin/tag/delete/"+id, {})
                        .then(response => {
                            if (response.data.status === 200) {
                                this.getTag();
                                this.$message.success("删除成功");
                            }
                        })
                        .catch(error => {
                            console.log(error);
                            this.$message.error("该标签被博客关联，无法删除");
                        })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            showInput() {
                this.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
        }
    }
</script>

<style scoped>
	.el-tag {
		cursor: pointer;
	}
	.el-card {
		text-align: left;
	}
	.pop-tag {
		margin: 5px 5px;
		display: inline-block;
	}
	.pop-tag:nth-child(5n+1) .el-tag {
		background-color: #f0f9eb;
		border: 1px solid #e1f3d8;
		color: #67c23a;
	}
	.pop-tag:nth-child(5n+2) .el-tag {
		background-color: #ecf5ff;
		border: 1px solid #d9ecff;
		color: #409eff;
	}
	.pop-tag:nth-child(5n+3) .el-tag {
		background-color: #f4f4f5;
		border: 1px solid #e9e9eb;
		color: #909399;
	}
	.pop-tag:nth-child(5n+4) .el-tag {
		background-color: #fdf6ec;
		border: 1px solid #faecd8;
		color: #e6a23c;
	}
	.pop-tag:nth-child(5n+5) .el-tag {
		background-color: #fef0f0;
		border: 1px solid #fde2e2;
		color: #f56c6c;
	}
</style>