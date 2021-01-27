<template>
  <transition name="fade">
    <div class="modal position" v-show="showModal">
      <div class="mask position"></div>
      <div class="modal-dialog">
        <!-- 弹出层内容 -->
        <div class="modal-dialog-body">
          <slot name="dialog"></slot>
        </div>
        <span class="icon-guanbi iconfont" @click="$emit('cancel')"></span>
      </div>
    </div>
  </transition>
</template>
<script>
export default {
  name: "modal",
  props: {
    showModal: Boolean
  }
};
</script>
<style lang='scss' scoped>
.fade-enter-active,
.fade-leave-active {
  opacity: 1;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.modal {
  z-index: 10;
  transition: all 0.4s;
  .mask {
    background-color: #000;
    opacity: 0.5;
  }
  .modal-dialog {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    align-items: flex-start;
    &-body {
      background: #ffffff;
    }
  }

  .icon-guanbi {
    margin: 16px 0 0 16px;
    color: #ffffff;
    cursor: pointer;
  }
}
.position {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
</style>