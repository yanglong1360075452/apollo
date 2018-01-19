<template>
  <div class="container-wrap">

    <div class="container-box" >

      <div class="container-content" style="width: 1400px;height: 1400px">

        <div style="width: 100%;">
          <div style="width: 44%;float:left">全天网速</div>
          <div style="width: 40%;float:left">数据来源</div>
        </div>

      <div style="width: 600px;height:350px;float:left;border:1px solid #000">
       <div style="width: 100%;height: 20%;position:relative">

         <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
           <el-date-picker
             v-model="valueFullday"
             type="date"
             placeholder="选择日期">
           </el-date-picker>


         </div>

         <div style="margin-top: 20px;width:30%;float:left;margin-left: 20px">
         <el-select v-model="valueFullDayType" clearable placeholder="选择类型">
           <el-option
             v-for="item in options"
             :key="item.value"
             :label="item.label"
             :value="item.value">
           </el-option>
         </el-select>



         </div>


         <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
           <el-cascader
             :show-all-levels="false"
             placeholder="选择应用"
             :options="optionsCas"
             v-model="selectedOptions1"
             clearable
             >
           </el-cascader>
         </div>

       </div>

        <div  id="main" style="width:70%;height:70%;margin-top: 10%;margin-right: auto;margin-left: auto;">
        </div>

      </div>

      <div  style="width: 600px;height:350px;float:left;border:1px solid #000">
        <div style="width: 100%;height: 20%">
          <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
            <el-date-picker
              v-model="valueData"
              type="date"
              placeholder="选择日期">
            </el-date-picker>


          </div>

          <div style="margin-top: 20px;width:30%;float:left;margin-left: 20px">
            <el-select v-model="valueDataType" clearable placeholder="选择类型">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>


          </div>


          <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">

          </div>

        </div>
        <div  id="main1" style="width:70%;height:70%;margin-top: 5%;margin-right: auto;margin-left: auto;"></div>
      </div>

        <div style="width: 100%">
          <div style="width: 44%;float:left;margin-top: 30px">忙时网速</div>
          <div style="width: 40%;float:left;margin-top: 30px">闲时网速</div>
        </div>

      <div  style="width: 600px;height:350px;float:left;border:1px solid #000">
        <div style="width: 100%;height: 20%">
          <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
            <el-date-picker
              v-model="valueBusy"
              type="date"
              placeholder="选择日期">
            </el-date-picker>


          </div>

          <div style="margin-top: 20px;width:30%;float:left;margin-left: 20px">
            <el-select v-model="valueBusyType" clearable placeholder="选择类型">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

          </div>


          <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
            <el-cascader
              :show-all-levels="false"
              placeholder="选择应用"
              :options="optionsCas"
              v-model="selectedOptions2"
              clearable
              >
            </el-cascader>
          </div>

        </div>

        <div  id="main2" style="width:70%;height:70%;margin-top: 10%;margin-right: auto;margin-left: auto;"></div>
      </div>

      <div  style="width: 600px;height:350px;float:left;border:1px solid #000">
        <div style="width: 100%;height: 20%">
          <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
            <el-date-picker
              v-model="valueIdle"
              type="date"
              placeholder="选择日期">
            </el-date-picker>


          </div>

          <div style="margin-top: 20px;width:30%;float:left;margin-left: 20px">
            <el-select v-model="valueIdleType" clearable placeholder="选择类型">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>



          </div>


          <div style="margin-top: 20px;width:30%;float:left;margin-left: 10px">
            <el-cascader
              :show-all-levels="false"
              placeholder="选择应用"
              :options="optionsCas"
              v-model="selectedOptions3"
              clearable
              >
            </el-cascader>
          </div>

        </div>
       <div  id="main3" style="width:70%;height:70%;margin-top: 10%;margin-right: auto;margin-left: auto;"></div>
      </div>

      </div>

    </div>

  </div>
</template>

<script>
  import echarts from '../../../node_modules/echarts';

  export default {
    name: 'list',
    data() {
      return {
        valueFullday:'',
        valueData:'',
        valueBusy:'',
        valueIdle:'',
        options: [{
          value: '选项1',
          label: 'WIFI'
        }, {
          value: '选项2',
          label: 'Mobile'
        }],
        valueFullDayType: '',
        valueDataType: '',
        valueBusyType: '',
        valueIdleType: '',

        optionsCas: [{
          value: 'zhinan',
          label: '指南',
          children: [{
            value: 'shejiyuanze',
            label: '设计原则',
            children: [{
              value: 'yizhi',
              label: '一致'
            }, {
              value: 'fankui',
              label: '反馈'
            }, {
              value: 'xiaolv',
              label: '效率'
            }, {
              value: 'kekong',
              label: '可控'
            }]
          }, {
            value: 'daohang',
            label: '导航',
            children: [{
              value: 'cexiangdaohang',
              label: '侧向导航'
            }, {
              value: 'dingbudaohang',
              label: '顶部导航'
            }]
          }]
        }, {
          value: 'zujian',
          label: '组件',
          children: [{
            value: 'basic',
            label: 'Basic',
            children: [{
              value: 'layout',
              label: 'Layout 布局'
            }, {
              value: 'color',
              label: 'Color 色彩'
            }, {
              value: 'typography',
              label: 'Typography 字体'
            }, {
              value: 'icon',
              label: 'Icon 图标'
            }, {
              value: 'button',
              label: 'Button 按钮'
            }]
          }, {
            value: 'form',
            label: 'Form',
            children: [{
              value: 'radio',
              label: 'Radio 单选框'
            }, {
              value: 'checkbox',
              label: 'Checkbox 多选框'
            }, {
              value: 'input',
              label: 'Input 输入框'
            }, {
              value: 'input-number',
              label: 'InputNumber 计数器'
            }, {
              value: 'select',
              label: 'Select 选择器'
            }, {
              value: 'cascader',
              label: 'Cascader 级联选择器'
            }, {
              value: 'switch',
              label: 'Switch 开关'
            }, {
              value: 'slider',
              label: 'Slider 滑块'
            }, {
              value: 'time-picker',
              label: 'TimePicker 时间选择器'
            }, {
              value: 'date-picker',
              label: 'DatePicker 日期选择器'
            }, {
              value: 'datetime-picker',
              label: 'DateTimePicker 日期时间选择器'
            }, {
              value: 'upload',
              label: 'Upload 上传'
            }, {
              value: 'rate',
              label: 'Rate 评分'
            }, {
              value: 'form',
              label: 'Form 表单'
            }]
          }, {
            value: 'data',
            label: 'Data',
            children: [{
              value: 'table',
              label: 'Table 表格'
            }, {
              value: 'tag',
              label: 'Tag 标签'
            }, {
              value: 'progress',
              label: 'Progress 进度条'
            }, {
              value: 'tree',
              label: 'Tree 树形控件'
            }, {
              value: 'pagination',
              label: 'Pagination 分页'
            }, {
              value: 'badge',
              label: 'Badge 标记'
            }]
          }, {
            value: 'notice',
            label: 'Notice',
            children: [{
              value: 'alert',
              label: 'Alert 警告'
            }, {
              value: 'loading',
              label: 'Loading 加载'
            }, {
              value: 'message',
              label: 'Message 消息提示'
            }, {
              value: 'message-box',
              label: 'MessageBox 弹框'
            }, {
              value: 'notification',
              label: 'Notification 通知'
            }]
          }, {
            value: 'navigation',
            label: 'Navigation',
            children: [{
              value: 'menu',
              label: 'NavMenu 导航菜单'
            }, {
              value: 'tabs',
              label: 'Tabs 标签页'
            }, {
              value: 'breadcrumb',
              label: 'Breadcrumb 面包屑'
            }, {
              value: 'dropdown',
              label: 'Dropdown 下拉菜单'
            }, {
              value: 'steps',
              label: 'Steps 步骤条'
            }]
          }, {
            value: 'others',
            label: 'Others',
            children: [{
              value: 'dialog',
              label: 'Dialog 对话框'
            }, {
              value: 'tooltip',
              label: 'Tooltip 文字提示'
            }, {
              value: 'popover',
              label: 'Popover 弹出框'
            }, {
              value: 'card',
              label: 'Card 卡片'
            }, {
              value: 'carousel',
              label: 'Carousel 走马灯'
            }, {
              value: 'collapse',
              label: 'Collapse 折叠面板'
            }]
          }]
        }, {
          value: 'ziyuan',
          label: '资源',
          children: [{
            value: 'axure',
            label: 'Axure Components'
          }, {
            value: 'sketch',
            label: 'Sketch Templates'
          }, {
            value: 'jiaohu',
            label: '组件交互文档'
          }]
        }],
        selectedOptions1: [],
        selectedOptions2: [],
        selectedOptions3: [],
      }
    },
    created () {

    },
    mounted() {
      this.echarts();
},
    watch: {
      'selectedOptions3':'test',
    },
    computed: {

    },
    methods: {

      echarts(){

// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        var myChart1 = echarts.init(document.getElementById('main1'));
        var myChart2 = echarts.init(document.getElementById('main2'));
        var myChart3 = echarts.init(document.getElementById('main3'));


        var option1 = {
          title: {
            text: '数据来源',
            subtext: '1028条数据',
            left: 'center'
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: ['电信', '联通','移动','鹏博士']
          },
          series : [
            {
              type: 'pie',
              radius : '65%',
              center: ['50%', '50%'],
              selectedMode: 'single',
              data:[
                {value:500, name: '电信'},
                {value:300, name: '联通'},
                {value:100, name: '移动'},
                {value:128, name: '鹏博士'},

              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        };


        var posList = [
          'left', 'right', 'top', 'bottom',
          'inside',
          'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
          'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
        ];

        app.configParameters = {
          rotate: {
            min: -90,
            max: 90
          },
          align: {
            options: {
              left: 'left',
              center: 'center',
              right: 'right'
            }
          },
          verticalAlign: {
            options: {
              top: 'top',
              middle: 'middle',
              bottom: 'bottom'
            }
          },
          position: {
            options: echarts.util.reduce(posList, function (map, pos) {
              map[pos] = pos;
              return map;
            }, {})
          },
          distance: {
            min: 0,
            max: 100
          }
        };

        app.config = {
          rotate: 90,
          align: 'left',
          verticalAlign: 'middle',
          position: 'insideBottom',
          distance: 15,
          onChange: function () {
            var labelOption = {
              normal: {
                rotate: app.config.rotate,
                align: app.config.align,
                verticalAlign: app.config.verticalAlign,
                position: app.config.position,
                distance: app.config.distance
              }
            };
            myChart.setOption({
              series: [{
                label: labelOption
              }, {
                label: labelOption
              }, {
                label: labelOption
              }, {
                label: labelOption
              }]
            });
          }
        };


        var labelOption = {
          normal: {
            show: true,
            position: app.config.position,
            distance: app.config.distance,
            align: app.config.align,
            verticalAlign: app.config.verticalAlign,
            rotate: app.config.rotate,
            formatter: '{c}  {name|{a}}',
            fontSize: 16,
            rich: {
              name: {
                textBorderColor: '#fff'
              }
            }
          }
        };

        var option = {
          color: ['#003366', '#006699', '#D8402E', '#aa4E56'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['下载峰值', '下载平均值', '上传峰值', '上传平均值']
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
              mark: {show: true},
              dataView: {show: true, readOnly: false},
              magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          calculable: true,
          xAxis: [
            {
              type: 'category',
              axisTick: {show: false},
              data: ['电信', '联通', '移动', '鹏博士']
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '下载峰值',
              type: 'bar',
              barGap: 0,
              label: labelOption,
              data: [3200, 2320, 2010, 2340]
            },
            {
              name: '下载平均值',
              type: 'bar',
              label: labelOption,
              data: [2200, 1802, 1910, 2034]
            },
            {
              name: '上传峰值',
              type: 'bar',
              label: labelOption,
              data: [1500, 2302, 2001, 1054]
            },
            {
              name: '上传平均值',
              type: 'bar',
              label: labelOption,
              data: [980, 770, 701, 699]
            }
          ]
        };


        /*var option={
         title: {
         text: '全天网速'
         },
         tooltip: {},
         xAxis: {
         data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
         },
         yAxis: {},
         series: [{
         name: '销量',
         type: 'bar',
         data: [5, 20, 36, 10, 10, 20]
         }]
         }*/

// 绘制图表
        myChart.setOption(option);
        myChart1.setOption(option1);
        myChart2.setOption(option);
        myChart3.setOption(option);

      },

      test(){
          console.log(this.selectedOptions3);
      }

    },

  }
</script>
<style lang="scss">
  @import "../../assets/scss/define";

  .container-content {
    @extend %pa;
    top: 0px;
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
