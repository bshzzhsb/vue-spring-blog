<template>
	<div>
		<nav>
			<div class="profile-div">
				<img class="profile" src="../../../assets/images/doraemon.jpg" @click="toggleCollapse" alt="">
			</div>
			<ul>
				<li class="nav-item">
					<router-link to="/admin/home" :class="(isCollapse?'s-nav-item-a':'b-nav-item-a')+' nav-item-a'">
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">
							<svg class="iconfont" style="font-size: 1.2rem" aria-hidden="true">
							    <use xlink:href="#iconhome1"></use>
							</svg>
						</span>
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">主页</span>
					</router-link>
				</li>
				<li class="nav-item">
					<router-link to="/admin/blog" :class="(isCollapse?'s-nav-item-a':'b-nav-item-a')+' nav-item-a'">
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">
							<svg class="iconfont" style="font-size: 1.2rem" aria-hidden="true">
							    <use xlink:href="#iconego-blog"></use>
							</svg>
						</span>
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">博客</span>
					</router-link>
				</li>
				<li class="nav-item">
					<router-link to="/admin/typeandtag" :class="(isCollapse?'s-nav-item-a':'b-nav-item-a')+' nav-item-a'">
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">
							<svg class="iconfont" style="font-size: 1.2rem" aria-hidden="true">
							    <use xlink:href="#iconfenlei"></use>
							</svg>
						</span>
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">分类</span>
					</router-link>
				</li>
				<li class="nav-item">
					<router-link to="/admin/moodmanager" :class="(isCollapse?'s-nav-item-a':'b-nav-item-a')+' nav-item-a'">
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">
							<svg class="iconfont" style="font-size: 1.2rem" aria-hidden="true">
							    <use xlink:href="#iconxingxi"></use>
							</svg>
						</span>
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">动态</span>
					</router-link>
				</li>
			</ul>
		</nav>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex'

    export default {
        name: "AdminSideBar",
	    computed: {
            //这里的...是超引用，ES6的语法，意思是state里有多少属性值我可以在这里放多少属性值
            ...mapGetters([
                'sidebar'
            ]),
		    isCollapse() {
                return !this.sidebar.opened;
		    },
	    },
	    data() {
            return {
	            activeMenu: 'home',
            };
	    },
        watch: {
            $route(to, from) {
                this.activeMenu = to.path.split('/')[2];
            }
        },
	    mounted() {

	    },
	    methods: {
            toggleCollapse() {
                this.$store.dispatch('app/toggleSideBar');
            }
	    },
    }
</script>

<style scoped>
	.router-link-active {
		background-color: rgb(215,225,225);
		color: rgba(64,158,255,1);
	}
	.router-link-active:hover {
		color: rgba(64,158,255,1) !important;
	}
	.profile {
		margin-top: 20px;
		margin-bottom: 20px;
		width: 60px;
		border-radius: 50%;
	}
	.nav-item {
		text-align: center;
		display: inline-block;
	}
	.nav-item-a {
		box-sizing: border-box;
		height: 50px;
		display: block;
		overflow: hidden;
		transition: width 0.1s ease-in-out;
	}
	.b-nav-item-a {
		width: 120px;
	}
	.s-nav-item-a {
		width: 60px;
		padding: 0 5px;
	}
	.nav-item-a:hover {
		background-color: rgb(215,225,225);
		color: rgb(140, 142, 149);
	}
	.nav-item-span {
		line-height: 50px;
		height: 50px;
		width: 50px;
		box-sizing: border-box;
		padding: 0 2px;
		transition: margin-top 0.3s ease-in-out;
	}
	.b-nav-item-span:first-child {
		text-align: right;
	}
	.b-nav-item-span:last-child {
		text-align: left;
	}
	.s-nav-item-span {
		display: block;
	}
	.b-nav-item-span {
		display: inline-block;
	}
	.nav-item-a:hover span:first-child {
		margin-top: -50px;
	}
	a {
		color: #2c3e50;
	}
	a:hover {
		text-decoration: none;
	}
	ul {
		font-size: 0;
	}
	ul li {
		font-size: 14px;
	}
</style>