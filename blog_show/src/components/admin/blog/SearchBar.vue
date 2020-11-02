<template>
  <div class="searchbar">
    <el-form :inline="true" :model="blogQuery">
      <el-form-item label="标题">
        <el-input v-model="blogQuery.title" placeholder="标题"></el-input>
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="blogQuery.typeId" clearable placeholder="分类" value="">
          <el-option v-for="(item, index) in typeList" :key="item.id"
                     :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="blogQuery.recommend">推荐</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: "SearchBar",
    data() {
      return {
        blogQuery: {
          title: '',
          typeId: '',
          recommend: false,
        },
        typeList: [],
      }
    },
    created() {
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
    methods: {
      onSubmit() {
        this.$emit("blogQuery", 0);
      },
    }
  }
</script>

<style scoped>
  .searchbar {
    margin-bottom: 20px;
    background-color: #f9fafb;
    box-shadow: 0 1px 2px 0 rgba(34, 36, 38, .15);
    border: 1px solid rgba(34, 36, 38, .15);
    border-radius: 3px;
  }

  .el-form-item {
    vertical-align: middle !important;
    margin: 12px 10px 12px 0 !important;
  }
</style>
