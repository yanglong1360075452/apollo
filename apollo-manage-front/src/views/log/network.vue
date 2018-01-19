<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>

      <div class="container-content">
        <div class="block">
          <span class="demonstration">手机号码</span>
          <el-input placeholder="请输入手机号码" icon="search" v-model="key_phone" :on-icon-click="handleIconClick" style="width: 180px">
          </el-input>

          <span class="demonstration">姓名</span>
          <el-input placeholder="请输入姓名" icon="search" v-model="key_user" :on-icon-click="handleIconClickUserName" style="width: 180px">
          </el-input>
          <span class="demonstration">上传最大值</span>
          <el-input placeholder="请填写数值下限" icon="search" v-model="upload_max" :on-icon-click="handleIconClickUploadMax" style="width: 180px">
          </el-input>
          <span class="demonstration">下载最大值</span>
          <el-input placeholder="请填写数值下限" icon="search" v-model="down_min" :on-icon-click="handleIconClickDownMin" style="width: 180px">
          </el-input>

          <span class="demonstration">时间段</span>
          <el-date-picker
            v-model="value"
            type="daterange"
            align="right"
            placeholder="选择日期范围"
            :picker-options="pickerOptions2">
          </el-date-picker>

          <el-button type="primary" size="small" @click="phoneAnduName()">查询</el-button>
          <el-button type="primary" size="small" @click="exportUsers()">导出</el-button>
        </div>
        <div  style="height:10px"></div>

        <el-table :data="networks" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="timeStamp" label="时间"  align="center">
            <template scope="scope">
              {{scope.row.timeStamp | date}}
            </template>
          </el-table-column>
          <el-table-column prop="uName" label="用户名"  align="center"> </el-table-column>
          <el-table-column prop="mobileNum" label="手机号码" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="netType" label="网络类型"  align="center"></el-table-column>
          <el-table-column prop="ssid" label="SSID"  align="center"></el-table-column>
          <el-table-column prop="operator" label="网络运营商"  align="center"></el-table-column>
          <el-table-column prop="uploadMaximum" label="上传最大值"  align="center"> </el-table-column>
          <el-table-column prop="uploadMinimum" label="上传最小值" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="uploadAvg" label="上传平均值"  align="center"></el-table-column>
          <el-table-column prop="downMaximum" label="下载最大值"  align="center"> </el-table-column>
          <el-table-column prop="downMinimum" label="下载最小值" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="downAvg" label="下载平均值"  align="center"></el-table-column>
          <el-table-column prop="appPackages" label="前台APP"  align="center"></el-table-column>

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
  import UserService from '../../service/user';
  import types from '../../store/mutation-types';
  import Util from '../../assets/lib/util';
  export default {
    name: 'list',
    data() {
      return {
        currentPage: 1,
        totalPage: 0,
        pageSize: 18,
        totalCount: 0,
        key_phone: '',
        key_user:'',
        upload_max: '',
        down_min:'',
        networks: [],
        pickerOptions2: {
          shortcuts: [
            {
              text: '最近一天',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 );
                picker.$emit('pick', [start, end]);
              }
            },{
              text: '最近一周',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }]
        },
        value: '',
      }
    },
    created () {
      this.fetchData();
    },
    watch: {
      '$route': 'fetchData',
      'value' : function (newValue) {
        console.log(newValue)
        console.log(newValue[0])

        var createTime= Util.msToDate(newValue[0]);
        var endTime= Util.msToDate(newValue[1]);
        if (createTime == undefined){
          createTime=null;
        }

        if (endTime == undefined){
          endTime=null;
        }

        this.createTime = (new Date(createTime)).getTime();
        this.endTime = (new Date(endTime)).getTime();

        this.fetchData();
      }
    },
    computed: {
      keyWordUsers () {
        var arr = [];
        if (!this.key_phone)
          return this.users;
        else {
          this.users.forEach((item) => {
            if (item.username.indexOf(this.key_phone) > -1)
              arr.push(item);
          });
          return arr;
        }
      }
    },
    methods: {
      handleIconClick () {
        if (!this.key_phone) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.fetchData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      handleIconClickUserName () {
        if (!this.key_user) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.fetchData();
      },
      handleIconClickUploadMax () {
        if (!this.upload_max) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.fetchData();
      },

      handleIconClickDownMin () {
        if (!this.down_min) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.fetchData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      phoneAnduName:function () {
        this.fetchData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      /**获取用户列表数据*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          UserService.getNetworks({
            tab: tab,
            page: that.currentPage,
            length: that.pageSize,
            mobileNum: that.key_phone,
            uName:that.key_user,
            uploadMaximum: that.upload_max,
            downMaximum:that.down_min,
            startTime: that.createTime,
            endTime: that.endTime
          }).then(function (response) {
            if (response.code == 0) {
              that.networks = response.data.data;
              console.log("qwdqedqwdqwdqwdqwdsa")
              that.totalPage = response.data.page;
              that.totalCount = response.data.total;
            }
            else that.$message({type: 'error', message: response.reason});
          });
        }, 300);
      },


      exportUsers: function() {
        var base_url;
        let that = this;
        if(window.location.href.indexOf('localhost') != -1){
          base_url = 'http://localhost:8080/apollo/api/manage';
        }else{
          base_url = 'api/manage';
        }
        var exportUrl=base_url +"/networks/export";

        var filter = {
          mobileNum: that.key_phone,
          uName: that.key_user,
          startTime: that.createTime,
          endTime: that.endTime,
          uploadMaximum: that.upload_max,
          downMaximum:that.down_min
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
        UserService.getNetworks(filter).then(function (response) {
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
<style lang="scss">
  @import "../../assets/scss/define";

  .container-content {
    @extend %pa;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 70px 20px;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .search-box {
    @extend %pa;
    top: 20px;
    right: 40px;
    z-index: 1;
    width: 360px;
  }

  .container-box {
    @extend %h100;
  }

  .page-wrap {
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
