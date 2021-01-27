<template>
  <div id="home">
      <nav-header ></nav-header>
    <router-view></router-view>
  </div>
</template>

<script>
// @ is an alias to /src
import NavHeader from "@/components/NavHeader.vue";

export default {
  name: "home",
  components: {
    NavHeader
  }
};
</script>
<style lang="scss" scoped>

</style>

