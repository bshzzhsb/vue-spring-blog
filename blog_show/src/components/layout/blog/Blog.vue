<template>
	<el-row>
		<el-col :xs="24" :sm="20" :md="18" :lg="16" :xl="16">
			<blog-show ref="blog"></blog-show>
			<blog-comment></blog-comment>
		</el-col>
		<el-col :xs="0" :sm="4" :md="6" :lg="8" :xl="8" class="toc-col">
			<toc ref="toc"></toc>
		</el-col>
		<scroll-top></scroll-top>
	</el-row>
</template>

<script>
	import BlogShow from "./BlogShow";
	import Toc from "./Toc";
	import ScrollTop from "../../common/ScrollTop";
	import BlogComment from "./BlogComment";

    export default {
        name: "Blog",
        components: {BlogComment, ScrollTop, Toc, BlogShow},
	    data() {
            return {
                id: -1,
		    }
	    },
        mounted() {
            this.id = this.$route.params.id;
            this.getBlog();
            window.addEventListener("scroll", this.scrollToc);
        },
	    destroyed() {
            window.removeEventListener("scroll", this.scrollToc);
	    },
	    methods: {
            getBlog() {
                this.axios.get("/blog/"+this.id)
                    .then(response => {
                        if (response.data.status === 200) {
                            var object = response.data.object;
                            var contentHtml = object.contentHtml;
                            contentHtml = this.addIdToH(contentHtml);
                            object.contentHtml = contentHtml;
                            this.$refs.blog.blog = object;
                            this.$refs.blog.loading = false;
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            addIdToH(contentHtml) {
                var div = document.createElement("div");
                div.innerHTML = contentHtml;
                var docs = div.children;
                let id = '';
                let titles = [];
                let hs = ["H1", "H2", "H3"];
                for (let i=0; i<docs.length; i++) {
                    let doc = docs[i];
                    if (hs.indexOf(doc.nodeName) !== -1) {
                        id = "title"+doc.children[0].getAttribute("id");
	                    doc.setAttribute("id", "title"+doc.children[0].getAttribute("id"));
	                    titles.push({
		                    href: "#"+id,
		                    name: doc.innerText,
		                    type: doc.nodeName[1],
	                    })
                    }
                }
                this.$refs.toc.titles = titles;
                return div.innerHTML;
            },
            scrollToc() {
                var titles = this.$refs.toc.titles;
                var ids = [];
                for (let i=0; i<titles.length; i++) {
                    ids.push(titles[i].href.substring(1));
                }
                for (let i=ids.length-1; i>=0; i--) {
                    var scrollD = document.documentElement.scrollTop;
                    if (document.getElementById(ids[i]).offsetTop+84 <= scrollD) {
                        this.$refs.toc.activeToc = i;
                        break;
                    }
                }
            },
	    }
    }
</script>

<style scoped>
	.el-row {
		display: flex;
		align-items: stretch;
	}
	.toc-col {
		padding-top: 1em;
	}
</style>