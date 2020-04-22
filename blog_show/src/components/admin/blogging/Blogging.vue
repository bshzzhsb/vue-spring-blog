<template>
	<div>
		<el-row type="flex" justify="center">
			<el-col :xs="24" :sm="24" :md="20" :lg="18" :xl="16">
				<el-form ref="form" :model="blog" :rules="rules"
				         :hide-required-asterisk="true">
					<el-form-item prop="flag" class="form-flag">
						<el-select v-model="blog.flag" placeholder="博客类型" value="">
							<el-option v-for="item in flagOptions" :key="item.value"
							           :label="item.label" :value="item.value">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item prop="title" class="form-title">
						<el-input v-model="blog.title" placeholder="标题"></el-input>
					</el-form-item>
					<el-form-item prop="contentMd">
						<mavon-editor ref="mavonEditor" v-model="blog.contentMd">
						</mavon-editor>
					</el-form-item>
					<el-form-item prop="type" class="form-type" label="分类" label-width="40px">
						<el-select v-model="blog.type" value-key="id" placeholder="分类" value="">
							<el-option v-for="item in typeList" :key="item.id"
							           :label="item.name" :value="item"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item prop="tags" class="form-tag">
						<el-select v-model="blog.tags" value-key="id" multiple placeholder="标签" value="">
							<el-option v-for="item in tagList" :key="item.id"
							           :label="item.name" :value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item prop="abs">
						<el-input type="textarea" :rows="2"
						          placeholder="摘要" v-model="blog.abs">
						</el-input>
					</el-form-item>
					<el-form-item class="form-picture">
						<el-input v-model="blog.picture" placeholder="图片URL">
							<el-button icon="el-icon-upload" slot="prepend"
							           @click="showDrawer"></el-button>
							<el-popover v-if="blog.picture" placement="top-start"
							            slot="append" width="200"
							            trigger="hover">
								<el-image :src="blog.picture" :fit="'contain'"
								          style="width: 200px;"></el-image>
								<el-button icon="el-icon-picture-outline" slot="reference"></el-button>
							</el-popover>
						</el-input>
					</el-form-item>
					<el-form-item>
						<el-checkbox v-model="blog.commentable">评论</el-checkbox>
						<el-checkbox v-model="blog.shareStatement">转载声明</el-checkbox>
						<el-checkbox v-model="blog.appreciation">打赏</el-checkbox>
						<el-checkbox v-model="blog.recommend">推荐</el-checkbox>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="onSave">保存</el-button>
						<el-button type="primary" @click="onSubmit">发布</el-button>
					</el-form-item>
				</el-form>
				<img-upload ref="imgUpload"></img-upload>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import ImgUpload from "../common/ImgUpload";

    export default {
        name: "Blogging",
        components: {ImgUpload},
        data() {
            var validateType = (rule, value, callback) => {
                console.log("1");
	            if (!value) {
	                return callback(new Error('请选择分类'))
	            }
            };
            return {
                blog: {
                    title: '',
	                contentHtml: '',
	                contentMd: '',
	                abs: '',
	                picture: '',
	                type: {},
	                tags: [],
	                flag: '原创',
	                commentable: true,
	                shareStatement: true,
	                appreciation: false,
	                published: true,
	                recommend: true,
                },
	            rules: {
                    title: [
	                    { required: true, message: '请输入标题', trigger: 'blur' }
                    ],
                    contentMd: [
                        { required: true, message: '请输入内容', trigger: ['blur', 'change'] }
                    ],
                    abs: [
                        { required: true, message: '请输入摘要', trigger: 'blur' }
                    ],
                    type: [
                        { required: true, message: '请选择分类', trigger: ['blur', 'change'] }
                    ],
                    tags: [
                        { required: true, message: '请选择标签', trigger: ['blur', 'change'] }
                    ],
                    flag: [
                        { required: true, message: '*', trigger: 'change' }
                    ],
	            },
	            flagOptions: [
		            { value: '原创', label: '原创'},
                    { value: '转载', label: '转载'},
                    { value: '翻译', label: '翻译'},
	            ],
	            typeList: [],
	            tagList: [],
	            tags: [],
	            typeIndex: '',
            }
	    },
        created() {
            this.getTypes();
            this.getTags();
            if (this.$route.query.blogId) {
                this.loadBlog(this.$route.query.blogId);
            }
        },
	    methods: {
            onSave() {
                this.blog.contentHtml = this.$refs.mavonEditor.d_render;
                this.blog.published = false;
                this.$refs.form.validate((flag, object) => {
                    if (flag) {
                        this.axios.post("/admin/blog", this.blog)
                            .then(response => {
                                this.$message({
                                    type: "success",
                                    message: "保存成功",
                                });
                                this.$router.push({ path: '/admin/blog'});
                            })
                            .catch(error => {
                                this.$message({
                                    type: "error",
                                    message: "保存失败",
                                });
                                console.log(error);
                            })
                    }
                })
            },
		    onSubmit() {
                this.blog.contentHtml = this.$refs.mavonEditor.d_render;
                this.blog.published = true;
                this.$refs.form.validate((flag, object) => {
                    if (flag) {
                        this.axios.post("/admin/blog", this.blog)
                            .then(response => {
                                this.$message({
                        	        type: "success",
                        	        message: "发布成功",
                                });
                                this.$router.push({ path: '/admin/blog'});
                            })
                            .catch(error => {
                                this.$message({
                                    type: "error",
                                    message: "发布失败",
                                });
                                console.log(error);
                            })
                    }
                })
		    },
		    getTypes() {
                this.axios.get("/admin/type")
                    .then(response => {
                        if (response.data.status === 200) {
                            this.typeList = response.data.object;
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    })
		    },
		    getTags() {
                this.axios.get("/admin/tag")
                    .then(response => {
                        if (response.data.status === 200) {
                            this.tagList = response.data.object;
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    })
		    },
            loadBlog(blogId) {
	            this.axios.get('/admin/blog/'+blogId)
		            .then(response => {
		                if (response.data.status === 200) {
		                    this.blog = response.data.object;
		                }
		            })
		            .catch(error => {
		                console.log(error);
		            })
            },
		    showDrawer() {
                this.$refs.imgUpload.drawer=true;
		    },
	    }
    }
</script>

<style scoped>
	.el-form {
		font-size: 0;
	}
	@media screen and (min-width: 768px) {
		.form-flag {
			display: inline-table;
			width: 19%;
		}
		.form-title {
			margin-left: 1%;
			display: inline-table;
			width: 80%;
		}
		.form-type {
			display: inline-table;
			width: 49%;
		}
		.form-tag {
			margin-left: 2%;
			display: inline-table;
			width: 49%;
		}
	}
	.form-flag /deep/ .el-select {
		width: 100%;
	}
	.form-type /deep/ .el-select {
		width: 100%;
	}
	.form-tag /deep/ .el-select {
		width: 100%;
	}
	.markdown-body {
		height: 100vh;
	}
	.markdown-body /deep/ textarea:focus {
		border: none !important;
		box-shadow: none !important;
	}
</style>