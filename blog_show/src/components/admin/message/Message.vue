<template>
	<div ref="messageContainer" class="message-container">
		<div class="message-box" v-for="(item,index) in messages" :key="item.id">
			<div class="message-avatar">
				<img :src="item.avatar" alt="">
			</div>
			<div class="message-header">
				<span class="message-name">{{ item.nickname }}</span>
				<span v-if="item.adminMessage" class="message-admin">博主</span>
				<span @click="delMessage(item.id)" class="message-del">
					<svg class="iconfont" style="font-size: 1.4em" aria-hidden="true">
					    <use xlink:href="#icondel"></use>
					</svg>
				</span>
				<span v-if="!item.checked" @click="checkMessage(item.id)" class="message-check">
					<svg class="iconfont" style="font-size: 1.5em" aria-hidden="true">
					    <use xlink:href="#iconok"></use>
					</svg>
				</span>
			</div>
			<div class="message-content">
				<span v-if="!item.checked">*</span>
				{{ item.content }}
			</div>
			<div class="message-action">
				<span>{{ item.createTime | formatTime('{y}-{m}-{d}-{h}-{i}') }}</span>
				<span @click="replyMessage(item.id, item.nickname, $event)" class="message-button">回复</span>
			</div>
			<div v-if="item.replyMessages"
			     v-for="(reply,i) in item.replyMessages" :key="reply.id"
			     class="reply-box">
				<div class="reply-avatar">
					<img :src="reply.avatar" alt="">
				</div>
				<div class="reply-header">
					<span class="reply-name">{{ reply.nickname }}</span>
					<span v-if="reply.adminMessage" class="reply-admin">博主</span>
					@ <span>{{ reply.parentMessage.nickname }}</span>
				</div>
				<div class="reply-content">
					{{ reply.content }}
				</div>
				<div class="reply-action">
					<span>{{ reply.createTime | formatTime('{y}-{m}-{d}-{h}-{i}') }}</span>
					<span @click="replyMessage(reply.id, reply.nickname, $event)" class="reply-button">回复</span>
				</div>
			</div>
		</div>
		<div ref="formContainer" class="reply-form">
			<el-form ref="form" :model="message" :rules="rules">
				<el-form-item prop="content" class="reply-content">
					<el-input ref="reply" type="textarea" :rows="2" resize="none"
					          :placeholder="placeholder"
					          v-model="message.content">
					</el-input>
					<div class="form-btn">
						<button v-if="cancelVisible" @click="cancelReply" class="cancel-btn">取消回复</button>
						<button class="reply-btn" @click="onSubmit">回复</button>
					</div>
				</el-form-item>
				<el-form-item prop="nickname" class="reply-item">
					<el-input placeholder="昵称" v-model="message.nickname"></el-input>
				</el-form-item>
				<el-form-item prop="email" class="reply-item">
					<el-input placeholder="邮箱" v-model="message.email"></el-input>
				</el-form-item>
				<el-form-item class="reply-item">
					<el-input placeholder="Web" v-model="message.web"></el-input>
				</el-form-item>
			</el-form>
		</div>
		<pagination ref="pagination" @selectPage="selectPage"></pagination>
	</div>
</template>

<script>
	import Pagination from '../../common/Pagination'

    export default {
        name: "Message",
	    components: {Pagination},
	    data() {
            return {
                messages: [],
	            message: {
                    content: '',
                    nickname: '博思何在',
                    email: 'sibo_he@qq.com',
		            web: 'http://www.bshz.xyz',
		            avatar: 'https://i.loli.net/2020/04/24/14CyFZk5OILBmWM.jpg',
		            adminMessage: true,
		            parentMessage: { id: -1 },
                },
                rules: {
                    content: [
                        { required: true, message:'*', trigger: 'blur' },
                    ],
                    nickname: [
                        { required: true, message: '请输入昵称', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
                    ],
                },
                cancelVisible: false,
                placeholder: '回复',
            }
	    },
	    created() {
            this.getMessage();
	    },
        methods: {
            getMessage(page=0, scroll=true) {
                this.axios.get("/admin/message?page="+page)
                    .then(response => {
                        if (response.data.status === 200) {
                            var object = response.data.object;
                            this.messages = object.messages;
                            if (scroll) {
                                this.$nextTick(() => {
                                    window.scrollTo({
                                        top:0,
                                        behavior: "smooth",
                                    });
                                });
                            }
                            this.$refs.pagination.currentPage = object.pageNumber+1;
                            this.$refs.pagination.totalPages = object.totalPages;
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            replyMessage(id, nickname, event) {
                event.currentTarget.after(this.$refs.formContainer);
                this.cancelVisible = true;
                this.placeholder = `@${nickname}`;
                this.message.parentMessage.id = id;
                this.$refs.reply.focus();
            },
            onSubmit() {
                this.axios.post("/admin/message", this.message)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.message.content = '';
                            this.message.parentMessage.id = -1;
                            this.$message.success("回复成功");
                            this.placeholder = '回复';
                            this.getMessage();
                            this.$nextTick(() => {
                                this.cancelReply();
                            })
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            cancelReply() {
                this.cancelVisible = false;
                this.$refs.messageContainer.appendChild(this.$refs.formContainer);
                this.placeholder = '回复';
                this.message.parentMessage.id = -1;
            },
            selectPage(pageNumber) {
                this.getMessage(pageNumber);
            },
            checkMessage(id) {
                this.axios.post("/admin/message/"+id)
	                .then(response => {
	                    if (response.data.status === 200) {
	                        this.getMessage(this.$refs.pagination.currentPage-1, false);
                            this.$notify({
                                title: 'checked',
                                type: 'success',
	                            duration: 1000,
                            });
	                    }
	                })
	                .catch(error => {
                        console.log(error)
	                })
            },
            delMessage(id) {
                this.$confirm('确定要删除该留言吗', '确认', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.axios.get("/admin/message/delete/"+id)
                        .then(resposne => {
                            if (resposne.data.status === 200) {
                                this.getMessage(this.$refs.pagination.currentPage-1, false);
                                this.$notify({
                                    title: '已删除该留言',
                                    type: 'error',
                                    duration: 1000,
                                });
                            }
                        })
                })
            },
        },
    }
</script>

<style scoped>
	.message-container {
		margin: 10px 0;
		min-height: calc(100vh - 328px);
	}
	.message-box {
		text-align: left;
		margin-left: 60px;
		margin-right: 20px;
		padding: 1em 0 3px 0;
		position: relative;
		margin-bottom: 1em;
	}
	.message-box + .message-box {
		border-top: 1px solid rgba(0,0,0,0.2);
	}
	.message-avatar {
		display: inline-block;
		position: absolute;
		margin-left: -44px;
	}
	.message-avatar img {
		width: 36px;
		height: 36px;
		border-radius: 50%;
	}
	.message-header,
	.reply-header {
		font-size: 12px;
		margin-bottom: 4px;
	}
	.message-header .message-name,
	.reply-header .reply-name {
		font-weight: 600;
	}
	.message-header .message-admin,
	.reply-header .reply-admin {
		background-color: rgba(0,0,0,0.2);
		padding: 1px 3px;
		margin-left: 4px;
		border-radius: 4px;
	}
	.message-header .message-del {
		margin-left: 10px;
		cursor: pointer;
		color: var(--red);
	}
	.message-header .message-check {
		margin-left: 5px;
		cursor: pointer;
	}
	.message-content,
	.reply-content {
		padding: 2px 0;
	}
	.message-action,
	.reply-action {
		font-size: 12px;
		color: #99a2aa;
		border-radius: 4px;
		display: inline-block;
	}
	.message-action .message-button,
	.reply-action .reply-button {
		margin-left: 6px;
		cursor: pointer;
	}
	.message-action .message-button:hover,
	.reply-action .reply-button:hover {
		color: #00a1d6;
	}
	.message-action .reply-form,
	.reply-action .reply-form {
		margin-left: 0 !important;
	}
	.reply-box {
		margin-left: 40px;
		margin-right: 20px;
		position: relative;
		padding: 6px 0;
	}
	.reply-avatar {
		display: inline-block;
		position: absolute;
		margin-left: -40px;
	}
	.reply-avatar img {
		width: 30px;
		height: 30px;
		border-radius: 50%;
	}
	.reply-form {
		margin-left: 20px;
		margin-right: 20px;
		font-size: 0;
	}
	.message-container > .reply-form {
		margin-left: 60px;
	}
	.reply-form .el-form-item {
		margin-bottom: 6px;
	}
	.reply-form .reply-item {
		display: inline-block;
		width: 31.33%;
	}
	.reply-form .reply-item + .reply-item {
		margin-left: 3%;
	}
	.reply-form .reply-content /deep/ .el-form-item__content {
		font-size: 0;
		line-height: 0;
	}
	.reply-form .el-textarea {
		width: 80%;
	}
	.reply-form /deep/ textarea {
		font-size: 12px;
		display: inline-block;
		box-sizing: border-box;
		background-color: #f4f5f7;
		border: 1px solid #e5e9ef;
		overflow: auto;
		border-radius: 4px;
		color: #555;
		width: 100% !important;
		height: 65px;
		transition: 0s;
		padding: 5px 10px;
		line-height: normal;
	}
	.reply-form .form-btn {
		width: 18%;
		height: 65px;
		margin-left: 2%;
		display: inline-block;
	}
	.reply-form .reply-btn {
		width: 100%;
		height: 40px;
		font-weight: 600;
		font-size: 14px;
		cursor: pointer;
		background: -moz-linear-gradient(top,#fff,#ededed) !important;
		background: -webkit-gradient(linear,left top,left bottom,from(#fff),to(#ededed)) !important;
		box-shadow: 0 1px 2px rgba(0,0,0,.2) !important;
		border-radius: 0.5rem !important;
		border: 1px solid #b7b7b7 !important;
	}
	.reply-form .reply-btn:nth-child(1) {
		height: 65px;
	}
	.reply-form .reply-btn:hover {
		background: -moz-linear-gradient(top,#fff,#dcdcdc) !important;
		background: -webkit-gradient(linear,left top,left bottom,from(#fff),to(#dcdcdc)) !important;
	}
	.reply-form .cancel-btn {
		border: none;
		border-radius: 4px;
		width: 100%;
		height: 20px;
		line-height: 20px;
		margin-bottom: 5px;
		cursor: pointer;
		font-size: 12px;
		color: rgba(0,0,0,0.7);
		background-color: rgba(0,0,0,0.2);
	}
	.reply-form .cancel-btn:hover {
		background-color: rgba(0,0,0,0.3);
	}
	.reply-form /deep/ input {
		font-size: 12px;
		padding: 5px 10px;
		background-color: #f4f5f7;
		border: 1px solid #e5e9ef;
		overflow-x: auto;
	}
	.reply-form /deep/ textarea:focus,
	.reply-form /deep/ input:focus {
		background-color: #fff;
	}
	@media screen and (max-width: 768px){
		.reply-form .reply-item:nth-child(2),
		.reply-form .reply-item:nth-child(3) {
			width: 48.5%;
		}
		.reply-form .reply-item:nth-child(4) {
			margin-left: 0;
			width: 100%;
		}
	}
</style>