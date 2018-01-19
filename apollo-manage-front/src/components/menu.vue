<template>
    <div class="menu-wrap">
      <el-menu :default-active="tabIndex" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
        <li v-if=sign>
          <el-menu-item index="1"><a href="#/exceptionsUsers">异常用户</a></el-menu-item>
          <el-menu-item index="2"><a href="#/disabled">非活跃用户</a></el-menu-item>
          <el-menu-item index="3"><a href="#/nopermission">无障碍权限未开启的用户</a></el-menu-item>
        </li>
        <li v-if=!sign>
          <el-menu-item index="1"><a href="#/echarts">统计图表</a></el-menu-item>
        <el-menu-item index="2"><a href="#/users">用户信息</a></el-menu-item>
        <el-menu-item index="3"><a href="#/device">设备信息</a></el-menu-item>
        <el-menu-item index="4"><a href="#/access">网络质量</a></el-menu-item>
          <el-menu-item index="5"><a href="#/network">网络速度</a></el-menu-item>
        <el-menu-item index="6"><a href="#/system">APP安装统计</a></el-menu-item>
       <!-- <el-menu-item index="5"><a href="#/note">APP运行统计</a></el-menu-item>-->
          <el-menu-item index="7"><a href="#/exceptionsUsers">异常用户</a></el-menu-item>
        <el-menu-item index="8"><a href="#/disabled">非活跃用户</a></el-menu-item>
          <el-menu-item index="9"><a href="#/nopermission">无障碍权限未开启的用户</a></el-menu-item>
        </li>
      </el-menu>

    </div>
</template>
<script>
  import Util from '../assets/lib/util';
    export default {
        name: 'menu',
        computed: {
            tabIndex(){
                return this.$store.state.tab_index;
            }
        },
      data () {
        return {
          user_name: '',
          sign:false
        };
      },
      created () {
        this.achieveUser();
      },
        methods: {
          achieveUser () {
           var username= this.user_name;
            var sign= this.sign;
            username= Util.dataToSessionStorageOperate.achieve('user').username;
            if (username != "admin"){
              sign=true,
              this.sign=sign
              console.log(sign)
            }
          },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>
<style lang="scss">
    @import "../assets/scss/define";
    .menu-wrap{
        @extend %pa;
        top: 60px;
        left: 0;
        width: 200px;
        bottom: 0;
        background-color: #EFF2F7;
    }
    .el-menu-item{
        @extend %pr;
        a{
            @extend %pa;
            @extend %db;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 0 45px;
            color: inherit;
        }
    }
</style>
