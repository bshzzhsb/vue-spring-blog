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
			<span>
				博客-{{blogCount}} 动态-{{moodCount}} 留言-{{messageCount}}
			</span>
			<div class="hamburger">
				<hamburger @openSidebar="openSidebar"></hamburger>
			</div>
		</div>
	</div>
</template>

<script>
    import {compile} from 'path-to-regexp'
	import Hamburger from "../../common/Hamburger";

    export default {
        name: "NavBar",
	    components: {Hamburger},
        data() {
            return {
                levelList: null,
	            blogCount: '',
	            moodCount: '',
	            messageCount: '',
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
            this.getInfo();
        },
	    methods: {
            getBreadcrumb() {
                let matched = this.$route.matched.filter(item => item.meta && item.meta.title);
                const first = matched[0];
                if (!this.isHome(first)) {
                    matched = [{path: '/index', meta: {title: '首页',}}].concat(matched);
                }
                this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false);
            },
            isHome(route) {
                const name = route && route.name;
                if (!name) {
                    return false;
                }
                return name.trim().toLocaleLowerCase() === 'Index'.toLocaleLowerCase();
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
            openSidebar() {
                this.$store.dispatch('app/toggleSideBar');
            },
		    getInfo() {
                this.axios.get("/blog/count")
	                .then(response => {
	                    this.blogCount = response.data.object;
	                })
	                .catch(error => {
	                    console.log(error)
	                });
			    this.axios.get("/mood/count")
				    .then(response => {
				        this.moodCount = response.data.object;
				    })
				    .catch(error => {
                        console.log(error)
				    });
			    this.axios.get("/message/count")
				    .then(response => {
				        this.messageCount = response.data.object;
				    })
				    .catch(error => {
                        console.log(error)
				    })
		    }
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
		float: right;
	}
	.right-menu > span {
		margin-right: 10px;
	}
	a:hover {
		text-decoration: none;
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