<template>
  <div id="layout">
    <a-layout-header class="header">
      <Header></Header>
    </a-layout-header>
    <a-layout-content style="padding: 0 20px">
      <div class="content-wrapper">
        <a-layout-content
          style="padding: 24px 24px; background: #fff;box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)"
        >
          <transition name="fade-transform" mode="out-in">
            <router-view />
          </transition>
        </a-layout-content>
      </div>
    </a-layout-content>
  </div>
</template>
<script>
import Header from "@/components/header";
export default {
  name: "layout",
  components: {
    Header,
  },
};
</script>

<style>
#layout .logo {
  height: 31px;
  margin: 16px 28px 16px 0;
  float: left;
}
.content-wrapper {
  padding: 50px;
}
</style>
