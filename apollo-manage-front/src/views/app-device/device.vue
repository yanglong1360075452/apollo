
<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>

      <div class="container-content">
        <div class="block">
          <span class="demonstration">手机号码</span>
          <el-input placeholder="请输入手机号码" icon="search" v-model="key_phone" :on-icon-click="handleIconClick" style="width: 200px">
          </el-input>

          <span class="demonstration">姓名</span>
          <el-input placeholder="请输入姓名" icon="search" v-model="key_user" :on-icon-click="handleIconClickUserName" style="width: 200px">
          </el-input>

          <el-button type="primary" size="small" @click="phoneAnduName()">查询</el-button>
          <el-button type="primary" size="small" @click="exportDevices()">导出</el-button>
        </div>
        <div  style="height:10px"></div>

        <el-table :data="device" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="devBrand" label="手机品牌"  align="center"></el-table-column>

          <el-table-column prop="devModel" label="手机型号"  show-overflow-tooltip align="center"> </el-table-column>
          <el-table-column prop="devImei" label="IMEI"  align="center"></el-table-column>

          <el-table-column prop="androidApiVer" label="Android版本"  align="center"> </el-table-column>
          <el-table-column prop="factoryOsVer"  label="厂商定制Android版本"  align="center"></el-table-column>
          <el-table-column prop="dMobilenum" label="关联手机号"  align="center"></el-table-column>
          <el-table-column prop="uName" label="用户名" align="center"> </el-table-column>
          <el-table-column prop="dLogintime"  label="绑定时间"  align="center">

            <template slot-scope="scope">
              {{scope.row.dLogintime | date}}
            </template>
          </el-table-column>
        </el-table>

        <div class="newpage-wrap">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="totalCount">
          </el-pagination>
        </div>
      </div>
<!--      <div class="page-wrap">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="totalCount">
        </el-pagination>
      </div>-->
    </div>
  </div>
</template>
<script>
  import DeviceService from '../../service/device';
  import types from '../../store/mutation-types';
  export default {
    data() {
      return {
        currentPage: 1,
        totalPage: 0,
        pageSize: 18,
        totalCount: 0,
        key_phone:'',
        key_user:'',
        device: []
      }
    },created(){
      this.getDeviceList();
    },
     methods:{
      handleIconClick(){
        if(!this.key_phone){
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.getDeviceList();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.getDeviceList();
      },

       handleIconClickUserName(){
         if(!this.key_user){
           this.$message({type: 'info', message: '请输入关键字'});
           return;
         }
         this.getDeviceList();
       },
       handleCurrentChange (val) {
         this.currentPage = val;
         this.getDeviceList();
       },

       phoneAnduName:function () {
         this.getDeviceList();
       },
       handleCurrentChange (val) {
         this.currentPage = val;
         this.getDeviceList();
       },


       getDeviceList (route) {
          let that = this;
         var tab = route ? route.query.tab: that.$route.query.tab;
         setTimeout( () => {
           DeviceService.getList({
             tab: tab,
             page: that.currentPage,
             length: that.pageSize,
             mobileNum: that.key_phone,
             uName: that.key_user
           }).then(function (response) {
             if(response.code == 0) {
               that.device = response.data.data;
               that.totalPage = response.data.page;
               that.totalCount = response.data.total;
             }
             else that.$message({type: 'error', message: response.reason});
           });
         },300);
       },



       exportDevices: function() {
         var base_url;
         let that = this;
         if(window.location.href.indexOf('localhost') != -1){
           base_url = 'http://localhost:8080/apollo/api/manage';
         }else{
           base_url = 'api/manage';
         }
         var exportUrl=base_url +"/devices/export";

         var filter = {
           mobileNum: this.key_phone,
           uName: this.key_user
         };

         console.log("查询条件",filter)

         var param =  exportUrl +"?";

         function formatParams(filter) {
           for (var c in filter) {
             if (filter[c] != null) {
               param = param + c + "=" + filter[c] + "&";
             }
           }
         }
         formatParams(filter)

         console.log("url",param)
         DeviceService.getList(filter).then(function (response) {
           if (response.code == 0) {

             that.totalCount = response.data.total;

             if (that.totalCount >60000){
               that.$message({type: 'error', message: "导出的数据超过6万"});
             }else {
               window.open(param, "_self");
             }
           }
           else that.$message({type: 'error', message: response.reason});
         });
       },
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">
  @import "../../assets/scss/define";
  .container-content{
    @extend %pa;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 70px 20px;
    overflow-x: hidden;
    overflow-y: auto;
  }
  .search-box{
    @extend %pa;
    top: 20px;
    right: 40px;
    z-index: 1;
    width: 360px;
  }
  .container-box{
    @extend %h100;
  }
  .page-wrap{
    @extend %pa;
    @extend %tac;
    background-color: #fff;
    z-index: 1;
    left: 0;
    right: 0;
    padding: 10px 0 20px;
    bottom: 0;
  }
  .newpage-wrap{
    @extend %pa;
    @extend %tac;
    left: 0;
    right: 0;
    padding: 10px 0 20px;
  }
</style>

