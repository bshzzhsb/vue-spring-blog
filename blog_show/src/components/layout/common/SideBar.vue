<template>
	<div>
		<nav>
			<div class="profile-div">
				<img class="profile" src="../../../assets/images/doraemon.jpg" @click="toggleCollapse" alt="">
			</div>
			<ul>
				<li v-for="(item, index) in adminRoutes" class="nav-item">
					<router-link :to="item.path" :class="(isCollapse?'s-nav-item-a':'b-nav-item-a')+' nav-item-a'">
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">
							<svg class="iconfont" style="font-size: 1.2rem" aria-hidden="true">
							    <use :xlink:href="item.icon"></use>
							</svg>
						</span>
						<span :class="(isCollapse?'s-nav-item-span':'b-nav-item-span')+' nav-item-span'">{{ item.title }}</span>
					</router-link>
				</li>
			</ul>
		</nav>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex'

    export default {
        name: "SideBar",
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
                adminRoutes: [
                    { path: '/index', title: '主页', icon: '#iconhome1' },
                    { path: '/type', title: '分类', icon: '#iconfenlei' },
                    { path: '/message', title: '留言', icon: '#iconduzheliuyan' },
                    { path: '/mood', title: '动态', icon: '#iconxingxi' },
                    { path: '/archive', title: '归档', icon: '#iconcc-box' },
                ]
            };
        },
        watch: {
            $route(to, from) {
                this.activeMenu = to.path.split('/')[2];
            }
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
		width: 160px;
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