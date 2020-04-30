<template>
	<div>
		<el-card shadow="hover">
			<div slot="header" class="clearfix">
				<svg class="iconfont" style="font-size: 1.2em" aria-hidden="true">
					<use xlink:href="#icontrending"></use>
				</svg>
				<span>Todo</span>
				<span  class="todo-actions" @mouseenter="listVisible=true"
				      @mouseleave="listVisible=false">
					<el-icon class="el-icon-s-fold"
					         v-show="listVisible" @click.native="showFinishTodo"></el-icon>
					<el-icon class="el-icon-plus" @click.native="showInput"></el-icon>
				</span>
			</div>
			<el-scrollbar class="todo-container">
				<div v-if="loading" class="loading"></div>
				<el-form ref="form" :model="todo">
					<el-form-item>
						<el-input ref="input" v-model="todo.content" v-show="todoInput" placeholder="TODO">
							<el-button icon="el-icon-check" slot="append" @click="onSubmit"></el-button>
						</el-input>
					</el-form-item>
				</el-form>
				<div v-for="(item,i) in todos" :key="item.id" class="todo">
					<span class="todo-content">{{ item.content }}</span>
					<span class="todo-ops">
						<el-popconfirm confirmButtonText='确定'
						               cancelButtonText='取消'
						               icon="el-icon-info"
						               iconColor="red"
						               title="确定删除TODO吗？" @onConfirm="delTodo(item.id, 1)">
							<svg slot="reference" class="iconfont" style="font-size: 1.2em" aria-hidden="true">
							    <use xlink:href="#iconsingleWrong"></use>
							</svg>
						</el-popconfirm>
						<svg @click="finishTodo(item.id)" class="iconfont" style="font-size: 1.2em" aria-hidden="true">
						    <use xlink:href="#iconcheckmark"></use>
						</svg>
						<el-icon class="el-icon-search" @click.native="editTodo(item.id)"></el-icon>
					</span>
					<span class="todo-time">{{ item.createTime | formatTime('{y}-{m}-{d}') }}</span>
				</div>
				<div v-for="(item,index) in todoPage" :key="item.id"
				     v-if="finishVisible" class="todo todo-finish">
					<span class="todo-content">{{ item.content }}</span>
					<span class="todo-ops">
						<el-popconfirm confirmButtonText='确定'
						               cancelButtonText='取消'
						               icon="el-icon-info"
						               iconColor="red"
						               title="确定删除TODO吗？" @onConfirm="delTodo(item.id, 2)">
							<el-icon class="el-icon-close" slot="reference"></el-icon>
						</el-popconfirm>
					</span>
					<span class="todo-time">{{ item.finishTime | formatTime('{y}-{m}-{d}') }}</span>
				</div>
			</el-scrollbar>
		</el-card>
	</div>
</template>

<script>
    export default {
        name: "Todo",
	    data() {
            return {
                todo: {
	                content: '',
                },
                todos: [],
                todoPage: [],
                todoInput: false,
	            loading: true,
                listVisible: false,
	            finishVisible: false,
            }
	    },
	    created() {
            this.getTodo();
	    },
	    methods: {
            getTodo() {
                this.axios.get("/admin/todo")
	                .then(response => {
	                    this.todos = response.data.object;
	                    this.$nextTick(() => {
	                        this.loading = false;
	                    })
	                })
	                .catch(error => {
                        console.log(error)
	                })
            },
            showInput() {
                this.todoInput = !this.todoInput;
            },
            finishTodo(id) {
                this.axios.post("/admin/todo/finish/"+id)
	                .then(response => {
	                    if (response.data.status === 200) {
                            this.getTodo();
                            this.$message.success("已完成，继续努力");
                        }
	                })
	                .catch(error => {
                        console.log(error)
	                })
            },
            onSubmit() {
                this.axios.post("/admin/todo", this.todo)
	                .then(response => {
	                    if (response.data.status === 200) {
	                        this.getTodo();
	                        this.todoInput = false;
	                        this.todo.content = '';
	                        this.$message.success("添加成功");
	                    }
	                })
	                .catch(error => {
                        console.log(error)
	                })
            },
            showFinishTodo() {
                if (this.finishVisible) {
                    this.finishVisible = false;
                } else {
                    this.getFinishTodo();
                }
            },
		    getFinishTodo(page=0) {
                this.axios.get("/admin/todo/finish?page="+page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.finishVisible = true;
                            this.todoPage = response.data.object;
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
		    },
            delTodo(id, flag) {
                this.axios.post("/admin/todo/delete/"+id)
	                .then(response => {
	                    if (response.data.status === 200) {
	                        if (flag === 1) {
	                            this.getTodo();
	                        } else {
	                            this.getFinishTodo();
	                        }
	                        this.$message.success("删除成功");
	                    }
	                })
            },
            editTodo(id) {
                this.axios.get("/admin/todo/"+id)
	                .then(response => {
	                    if (response.data.status === 200) {
                            this.todo = response.data.object;
                            this.todoInput = true;
                        }
	                })
            }
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
	.todo-container {
		height: 80vh;
		padding-left: 6px;
	}
	.el-scrollbar {
		position: relative;
	}
	.el-scrollbar /deep/ .el-scrollbar__wrap {
		overflow-x: auto;
		overflow-y: scroll;
	}
	.todo {
		height: 24px;
		line-height: 24px;
		position: relative;
		margin-left: 6px;
	}
	.todo-content {
		padding-left: 12px;
		margin-left: 6px;
		height: 24px;
		line-height: 24px;
		display: inline-block;
		cursor: pointer;
	}
	.todo:nth-child(5n+1) {
		border-left: 1px solid var(--blue);
	}
	.todo:nth-child(5n+2) {
		border-left: 1px solid var(--orange);
	}
	.todo:nth-child(5n+3) {
		border-left: 1px solid var(--purple);
	}
	.todo:nth-child(5n+4) {
		border-left: 1px solid var(--green);
	}
	.todo:nth-child(5n+5) {
		border-left: 1px solid var(--cyan);
	}
	.todo::before {
		content: '';
		position: absolute;
		left: -5px;
		top: 7px;
		width: 9px;
		height: 9px;
		border-radius: 50%;
		background-color: #fff;
		transition: background-color 0.2s ease-in-out;
	}
	.todo:nth-child(5n+1)::before {
		border: 1px solid var(--blue);
	}
	.todo:nth-child(5n+1):hover::before {
		background-color: var(--blue);
	}
	.todo:nth-child(5n+2)::before {
		border: 1px solid var(--orange);
	}
	.todo:nth-child(5n+2):hover::before {
		background-color: var(--orange);
	}
	.todo:nth-child(5n+3)::before {
		border: 1px solid var(--purple);
	}
	.todo:nth-child(5n+3):hover::before {
		background-color: var(--purple);
	}
	.todo:nth-child(5n+4)::before {
		border: 1px solid var(--green);
	}
	.todo:nth-child(5n+4):hover::before {
		background-color: var(--green);
	}
	.todo:nth-child(5n+5)::before {
		border: 1px solid var(--cyan);
	}
	.todo:nth-child(5n+5):hover::before {
		background-color: var(--cyan);
	}
	.todo-ops {
		margin-left: 6px;
		cursor: pointer;
		display: none;
	}
	.todo:hover .todo-ops {
		display: inline-block;
	}
	.todo-time {
		float: right;
		font-size: 12px;
		color: rgba(0,0,0,0.3);
	}
	.todo-actions {
		float: right;
	}
	.todo-finish .todo-content {
		text-decoration: line-through !important;
		opacity: 0.5;
	}
	.el-icon-s-fold {
		cursor: pointer;
		font-size: 16px;
	}
	.el-icon-plus {
		font-weight: 700 !important;
		cursor: pointer;
		line-height: 1.5 !important;
	}
	.el-icon-close {
		cursor: pointer;
	}
	.pagination-todo {
		margin: auto;
	}
</style>