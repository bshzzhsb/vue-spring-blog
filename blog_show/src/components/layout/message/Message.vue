<template>
  <el-row type="flex" justify="center">
    <el-col :xs="24" :sm="20" :md="18" :lg="16" :xl="16">
      <message-show></message-show>
    </el-col>
  </el-row>
</template>

<script>
  import MessageShow from "./MessageShow";

  export default {
    name: "Message",
    components: {MessageShow}
  }
</script>

<style scoped>

</style>
