<template>
	<div class="blog-manager">
		<div class="searchbar">
			<search-bar ref="searchbar" @blogQuery="blogQuery"></search-bar>
		</div>
		<blog-table ref="blogTable"></blog-table>
		<new-blog @newBlog="newBlog"></new-blog>
	</div>
</template>

<script>
	import BlogTable from "./BlogTable";
	import SearchBar from "./SearchBar";
	import NewBlog from "./NewBlog";

    export default {
        name: "BlogManager",
        components: {NewBlog, SearchBar, BlogTable},
        data() {
            return {
                blogPage: [],
            }
	    },
	    created() {

	    },
	    methods: {
		    blogQuery(blogQuery) {
		        this.axios.post('/admin/blog/search', blogQuery)
			        .then(response => {
			            if (response.data.status === 200) {
			                this.$refs.blogTable.blogPage = response.data.object.content;
			            }
			        })
			        .catch(error => {
			            console.log(error);
			        })
		    },
            newBlog() {
		        this.$router.push({ path: '/admin/blogging'})
            }
	    }
    }
</script>

<style scoped>
	@media screen and (min-width: 768px) {
		.blog-manager {
			height: calc(100vh - 175px);
		}
	}
</style>