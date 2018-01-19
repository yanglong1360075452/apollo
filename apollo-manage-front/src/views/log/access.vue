<template>
  <div class="container-wrap">
<!--    <div class="" v-show="imgUrl==''?false:true">
      <img v-bind:src="imgUrl"  style="width:200px;height: 220px">
      &lt;!&ndash;<img class="head-img" src="../../assets/images/header-img.jpg" alt="头像" style="height: 220px">&ndash;&gt;
    </div>-->
    <div class="container-box">
      <div class="">

      </div>
      <div class="grid-content bg-purple-dark" align="">
        <el-dialog :visible.sync="operation" size="samll" title="图片显示">
          <div style="width:100%;height:100%">
            <div class="" v-show="imgUrl==''?false:true">
              <img v-bind:src="imgUrl"  style="width:60%">
            </div>
          </div>
        </el-dialog>
      </div>
      <div class="container-content">

        <div class="block">
          <span class="demonstration">手机号码</span>
          <el-input placeholder="请输入手机号码" icon="search" v-model="key_word" :on-icon-click="handleIconClick" style="width: 200px">
          </el-input>

          <span class="demonstration">姓名</span>
          <el-input placeholder="请输入姓名" icon="search" v-model="key_user" :on-icon-click="handleIconClickUserName" style="width: 200px">
          </el-input>

          <span class="demonstration">请选择事件</span>
          <el-select v-model="test_Event" clearable placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>

          <span class="demonstration">时间段</span>
          <el-date-picker
            v-model="value"
            type="daterange"
            align="right"
            placeholder="选择日期范围"
            :picker-options="pickerOptions2">
          </el-date-picker>
          <el-button type="primary" size="small" @click="phoneAnduName()">查询</el-button>
          <el-button type="primary" size="small" @click="exportNetTest()">导出</el-button>
        </div>
        <div  style="height:10px"></div>

        <el-table :data="accessLogs" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="testTime" label="时间"  align="center">
            <template slot-scope="scope">
              {{scope.row.testTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="testEventDesc" label="事件"  align="center"></el-table-column>
          <el-table-column prop="userName" label="用户姓名"  show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="mobileNum" label="手机号码"  align="center"></el-table-column>
          <el-table-column prop="deviceImei" label="设备IMEI"  align="center"></el-table-column>
          <el-table-column prop="operator"  label="运营商"  show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="netType" label="网络类型"  align="center"></el-table-column>
          <el-table-column prop="ssid" label="SSID"  align="center"></el-table-column>
          <el-table-column prop="ipAddr" label="IP地址"  align="center"></el-table-column>
          <el-table-column prop="gps" label="地理位置"  align="center"></el-table-column>
          <el-table-column prop="errTypeDesc" label="错误类型"  align="center"></el-table-column>
          <el-table-column prop="screenShap" label="用户截屏"  align="center">
            <template slot-scope="scope">
              <p @click="test(scope.row)" style="color:blue">{{ scope.row.screenShap }}</p>
            </template>
          </el-table-column>
          <el-table-column prop="describe" label="文字反馈"  align="center"></el-table-column>
          <el-table-column prop="appName" label="前台APP"  align="center"></el-table-column>
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

      <div class="page-wrap">

      </div>
    </div>


  </div>



</template>
<script>
  import LogService from '../../service/log';
  import types from '../../store/mutation-types';
  import Util from '../../assets/lib/util';
  import config from '../../config/index';

  export default {

    data() {
      return {
          photo:"",
        operation:false,
        dialogFormVisible:false,
        currentPage: 1,
        totalPage: 0,
        pageSize: 18,
        totalCount: 0,
        key_word: '',
        key_user:'',
        accessLogs: [],
        detailLog:{},
        imgUrl:'',
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

        options: [{
          value: '1',
          label: '网络接通'
        }, {
          value: '2',
          label: '网络故障'
        }, {
          value: '4',
          label: '基站切换'
        }, {
          value: '5',
          label: '手动触发'
        }, {
          value: '6',
          label: '自动定时'
        }, {
          value: '7',
          label: '网络切换'
        }],
        test_Event: '',
        value: '',
      }
    },
    created () {
      this.logsData();
    },
    watch: {
      '$route': 'logsData',
      'value' : function (newValue) {
        console.log(newValue)
        console.log(newValue[0])

        if (newValue[0] == undefined && newValue[1] == undefined){

        }else{
          var createTime= Util.msToDate(newValue[0]).toString();
          var endTime= Util.msToDate(newValue[1]).toString();
        }
        if (createTime == undefined){
          createTime=null;
        }

        if (endTime == undefined){
          endTime=null;
        }

        this.createTime = (new Date(createTime)).getTime();
        this.endTime = (new Date(endTime)).getTime();

        this.logsData();
      },
      'test_Event' : function (testEvent) {
        console.log(testEvent)

        this.logsData();
      }
    },
    computed: {
      keyWordUsers () {
        var arr = [];
        if(!this.key_word)
          return this.accessLogs;
        else{
          this.accessLogs.forEach((item,index) => {
            if(item.username.indexOf(this.key_word) > -1)
              arr.push(item);
          });
          return arr;
        }
      }
    },
    methods: {
      detail(row){
        console.log(103);
        console.log(row);
        this.dialogFormVisible=true;
        this.detailLog=row;


      },
      test:function(accessLogs){
          console.log(accessLogs);
        this.operation = true;
        var base_url;
        if(window.location.href.indexOf('localhost') != -1){
          base_url = 'http://localhost:8080/apollo/api/manage';
        }else{
          base_url = 'api/manage';
        }

        var photo=base_url +"/netTestLog/photo/"+accessLogs.testId;
        this.imgUrl=photo;
      /*window.open('').document.write('<img  style="height:800px" src="'+photo+'"/>');*/


      },

      handleIconClick () {
        if(!this.key_word){
          this.$message({type: 'info', message: '请输入手机号码'});
          return;
        }
        this.logsData();
      },
      handleIconClick1 () {
        if(!this.key_word1){
          this.$message({type: 'info', message: '请输入用户IP'});
          return;
        }
        this.logsData();
      },

      handleIconClickUserName () {
        if (!this.key_user) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.logsData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.logsData();
      },

      phoneAnduName:function () {
        this.logsData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.logsData();
      },
      /**获取访问日志列表数据*/
      logsData (route) {
          let that = this;
        setTimeout( () => {
          LogService.getAccessLogs({
            page: that.currentPage,
            length: that.pageSize,
            filter: that.key_word,
            uName: that.key_user,
            testEvent: that.test_Event,
            startTime: that.createTime,
            endTime: that.endTime
          }).then(function (response) {
            if(response.code == 0) {
              that.accessLogs = response.data.data;
              console.log("****")
              console.log(that.accessLogs);
              that.totalPage = response.data.page;
              that.totalCount = response.data.total;
            }
            else that.$message({type: 'error', message: response.reason});
          });
        },300);
      },

/*      exportNetTest(){

            open('http://localhost:8080/apollo/api/manage/netTestLog/export');
      }*/

      exportNetTest: function() {
        var base_url;
        let that = this;
        if(window.location.href.indexOf('localhost') != -1){
          base_url = 'http://localhost:8080/apollo/api/manage';
        }else{
          base_url = 'api/manage';
        }
        var exportUrl=base_url +"/netTestLog/export";

        var filter = {
          filter: that.key_word,
          uName: that.key_user,
          testEvent: that.test_Event,
          startTime: that.createTime,
          endTime: that.endTime
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

        LogService.getAccessLogs(filter).then(function (response) {
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
