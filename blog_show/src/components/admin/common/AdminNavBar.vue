<template>
	<div class="navbar">
		<div id="breadcrumb-container">
			<el-breadcrumb separator="/">
				<transition-group name="breadcrumb">
					<el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
						<span v-if="item.redirect === 'noRedirect'||index == levelList.length-1" class="no-redirect">{{ item.meta.title }}</span>
						<a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
					</el-breadcrumb-item>
				</transition-group>
			</el-breadcrumb>
		</div>
		<div class="right-menu">
			<div class="hamburger">
				<hamburger @openSidebar="openSidebar"></hamburger>
			</div>
			<el-dropdown trigger="click">
				<div class="el-dropdown-link">
					<img class="avatar" src="../../../assets/images/doraemon.jpg" alt="">
				</div>
				<el-dropdown-menu slot="dropdown">
					<router-link to="/admin/home">
						<el-dropdown-item>首页</el-dropdown-item>
					</router-link>
					<el-dropdown-item @click.native="logout">
						<span>Log Out</span>
					</el-dropdown-item>
				</el-dropdown-menu>
			</el-dropdown>
		</div>
	</div>
</template>

<script>
    import {compile} from 'path-to-regexp'
    import Hamburger from "./Hamburger";

    export default {
        name: "AdminNavBar",
        components: {Hamburger},
        data() {
            return {
                levelList: null,
            }
        },
        watch: {
            $route(route) {
                if (route.path.startsWith('/redirect/')) {
                    return;
                }
                this.getBreadcrumb();
            }
        },
        created() {
            this.getBreadcrumb();
        },
        methods: {
            getBreadcrumb() {
                let matched = this.$route.matched.filter(item => item.meta && item.meta.title);
                const first = matched[0];
                if (!this.isAdminHome(first)) {
                    matched = [{path: '/admin/home', meta: {title: '首页',}}].concat(matched);
                }
                this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false);
            },
            isAdminHome(route) {
                const name = route && route.name;
                if (!name) {
                    return false;
                }
                return name.trim().toLocaleLowerCase() === 'AdminHome'.toLocaleLowerCase();
            },
            handleLink(item) {
                const {redirect, path} = item;
                if (redirect) {
                    this.$router.push(redirect);
                    return;
                }
                this.$router.push(this.pathCompile(path));
            },
            pathCompile(path) {
                const {params} = this.$route;
                var toPath = compile(path);
                return toPath(params);
            },
	        logout() {
                this.axios.get("/admin/logout")
	                .then(response => {
	                    if (response.data.status === 200) {
                            this.$store.dispatch('user/logout');
                            this.$router.replace({ path: '/admin/login' })
	                    }
	                })
	                .catch(error => {
	                    console.log(error);
	                })
	        },
            openSidebar() {
                this.$store.dispatch('app/toggleSideBar');
            },
        }
    }
</script>

<style scoped>
	.navbar {
		position: relative;
		display: block;
		height: 50px;
		line-height: 50px;
		background-color: #fff;
		box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
	}
	.breadcrumb-leave-active,
	.breadcrumb-enter-active {
		transition: all 0.3s ease-in-out;
		position: absolute;
	}
	.breadcrumb-leave-to {
		opacity: 0;
		transform: translateX(-10px);
	}
	.breadcrumb-enter {
		opacity: 0;
		transform: translateX(10px);
	}
	.el-breadcrumb {
		height: 50px;
		line-height: 50px;
		margin-left: 10px;
		display: table;
		float: left;
		background-color: #fff;
	}
	.el-breadcrumb a {
		font-weight: 400;
	}
	.el-breadcrumb a:hover {
		text-decoration: none;
	}
	.no-redirect {
		color: #97a8be;
	}
	.right-menu {
		margin-right: 10px;
	}
	.right-menu .el-dropdown {
		float: right;
		height: 50px;
		width: 50px;
	}
	.el-dropdown:hover {
		background-color: rgba(0,0,0,0.05);
	}
	.avatar {
		margin-top: 5px;
		width: 40px;
		height: 40px;
		border-radius: 50%;
	}
	a:hover {
		text-decoration: none;
	}
	.mobile .right-menu .el-dropdown {
		float: none;
	}
	.hamburger {
		display: none;
		margin-top: 5px;
		float: right;
	}
	.mobile .hamburger {
		display: block !important;
	}
</style>