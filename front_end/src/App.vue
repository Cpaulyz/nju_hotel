<template>
  <div id="app" v-title data-title="NJUSE酒店管理系统">
    <transition name="fade-transform" mode="out-in">
      <router-view/>
    </transition>
  </div>
</template>
<script>
export default {
  components: {
    
  }
}
</script>
<style>
 #app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100%;
  /* background: #f0f2f5 url('assets/background.svg') repeat 100%; */
  background: #f0f2f5 url('assets/white-waves.png') repeat 100%;
  /* background: #f0f2f5; */
  height: 100%;
}
 fade-transform 
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all .5s;
}

.fade-transform-enter {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>
