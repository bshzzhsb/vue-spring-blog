<template>
  <div class="type-show">
    <type-table ref="typeTable" @selectType="selectType"></type-table>
    <blogs ref="typeBlog"></blogs>
    <pagination ref="pagination" @selectPage="selectPage"></pagination>
  </div>
</template>

<script>
  import TypeTable from "./TypeTable";
  import Blogs from "../index/Blogs";
  import Pagination from "../../common/Pagination";

  export default {
    name: "TypeShow",
    components: {Pagination, Blogs, TypeTable},
    data() {
      return {
        id: this.$route.query.typeId,
      }
    },
    mounted() {
      this.getType();
    },
    methods: {
      getType() {
        this.axios.get("/type")
          .then(response => {
            if (response.data.status === 200) {
              this.$refs.typeTable.typeList = response.data.object;
              this.id = response.data.object[0].id;
              if(!this.$route.query.typeId) this.$router.push({ query: {'typeId': this.id} });
              this.getTypeBlogs(0);
            }
          })
          .catch(error => {
            console.log(error);
          })
      },
      getTypeBlogs(page = 0) {
        this.$refs.typeBlog.loading = true;
        this.axios.get("/type/" + this.id + "?page=" + page)
          .then(response => {
            if (response.data.status === 200) {
              var object = response.data.object;
              this.$refs.typeBlog.blogPage = object.content;
              this.$refs.pagination.totalPages = object.totalPages;
              this.$refs.pagination.currentPage = object.number + 1;
              this.$nextTick(() => {
                document.body.scrollTop;
                window.scrollTo({
                  top: 0,
                  left: 0,
                  behavior: "smooth",
                });
                this.$refs.typeBlog.loading = false;
              })
            }
          })
          .catch(error => {
            console.log(error)
          })
      },
      selectPage(page) {
        this.getTypeBlogs(page);
      },
      selectType(id) {
        this.$router.push({
          query: {'typeId': id},
        });
        this.id = id;
        this.getTypeBlogs(0);
      }
    }
  }
</script>

<style scoped>
  .type-show {

  }
</style>
