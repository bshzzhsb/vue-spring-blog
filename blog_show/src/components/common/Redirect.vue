<template>

</template>

<script>
  export default {
    name: "Redirect",
    created() {
      const {params, query} = this.$route;
      const {path} = params;
      console.log(params, query)
      this.$router.replace({path: '/' + path, query})
    },
  }
</script>

<style scoped>

</style>
