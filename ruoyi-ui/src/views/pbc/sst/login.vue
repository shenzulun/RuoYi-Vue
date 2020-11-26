<template>
  <div class="home2">
    <div class="background"></div>
    <div class="background_bottom"></div>
    <div class="top_title">
      <div class="rightTime">
        <p class="p_1">
          <label>{{time1}}</label>
          <span>{{time2}}</span>
        </p>
        <p class="p_2">{{time3}}</p>
      </div>
    </div>
    <div class="content">
      <div class="left">
        <div class="title">
          <h2>惠企政策</h2>
        </div>
        <div class="card">
          <vue-seamless-scroll :data="hqzcData" :class-option="option" class="seamless-warp">
            <ul class="items">
              <li class="row" v-for="(item, index) in hqzcData" :key="index">
                <p class="dept">{{item.dept}}</p>
                <p class="time">{{getTime(item)}}</p>
                <el-tooltip class="item" effect="dark" :content="item.name" placement="bottom">
                  <p class="name" @click.stop="goUrl(item.url)">{{item.name}}</p>
                </el-tooltip>
              </li>
            </ul>
          </vue-seamless-scroll>
      </div>
      </div>
      <div class="center">
        <div class="top">
          <div class="mapName">
            <p @click.stop="showAll">{{name1}}</p><span v-show='name2'> / </span>
            <p>{{name2}}</p>
          </div>
          <div id="map2" v-if="name2" class="map"></div>
          <div id="map" v-else class="map"></div>
          <div class="map-legend">
            <div class="row">
              <i class="legend4-icon"></i>
              <span>已融资</span>
            </div>  
            <div class="row">
              <i class="legend3-icon"></i>
              <span>正在对接</span>
            </div>    
            <div class="row">
              <i class="legend2-icon"></i>
              <span>未对接</span>
            </div>  
          </div>
        </div>
        <div class="bottom">
          <div class="item btn1" @click="openLoginForm(1)"></div>
          <div class="item btn2" @click="openLoginForm(2)"></div>
          <div class="item btn3" @click="openLoginForm(3)"></div>
        </div>
      </div>
      <div class="right">
        <div class="top">
          <div class="title">
            <h2>银行特色企业贷款产品</h2>
          </div>
          <div class="card">
            <vue-seamless-scroll :data="tsqydkcpData" :class-option="option" class="seamless-warp">
              <ul class="items">
                <li class="row" v-for="(item, index) in tsqydkcpData" :key="index">
                  <el-tooltip class="item" effect="dark" :content="item.bank" placement="bottom">
                    <p class="dept">{{item.bank}}</p>
                  </el-tooltip>
                  <p class="content" @click.stop="goUrl(item.url)">{{item.name}}</p>
                  <p class="remark">{{item.remark}}</p>
                </li>
              </ul>
            </vue-seamless-scroll>
          </div>
        </div>
        <div class="bottom">
          <div class="title">
            <h2>三张清单</h2>
          </div>
          <div class="card">
            <vue-seamless-scroll :data="szqdData" :class-option="option" class="seamless-warp">
              <ul class="items">
                <li class="row" v-for="(item, index) in szqdData" :key="index">
                  <el-tooltip class="item" effect="dark" :content="item.bank" placement="bottom">
                    <p class="dept">{{item.bank}}</p>
                  </el-tooltip>
                  <p class="content" @click.stop="goUrl(item.url)">{{item.content}}</p>
                  <p class="time">{{(getTime1(item))}}</p>
                </li>
              </ul>
            </vue-seamless-scroll>
          </div>
        </div>
      </div>
    </div>

    <!--登录功能-->
    <el-dialog :title="loginTitle" :visible.sync="open" width="500px" top="20vh !important" append-to-body>
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
            
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter.native="handleLogin"
          >
          </el-input>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
          <el-link @click.native="openRegisterForm" :underline="false" style="float: right;" v-show="loginType==2">注册</el-link>
        </el-form-item>
      </el-form>
    </el-dialog>
    

    <!--注册功能-->
    <el-dialog :title="registerTitle" :visible.sync="registerShow" width="500px" top="20vh !important" append-to-body>
      <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="login-form" label-width="80px">

        <el-form-item prop="userName" label="用户名">
          <el-input v-model="registerForm.userName" type="text" auto-complete="off" placeholder="登录账号">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>

        <el-form-item prop="password" label="密码">
          <el-input
            v-model="registerForm.password"
            type="password"
            auto-complete="off"
            placeholder="请输入密码..."
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>

        <el-form-item prop="nickName" label="企业名称">
            <el-input v-model="registerForm.nickName" type="text" auto-complete="off" placeholder="企业名称">
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
        </el-form-item>

        <el-form-item prop="contactPerson" label="联系人">
            <el-input v-model="registerForm.contactPerson" type="text" auto-complete="off" placeholder="联系人">
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
        </el-form-item>

        <el-form-item prop="contactPhone" label="联系方式">
            <el-input v-model="registerForm.contactPhone" type="text" auto-complete="off" placeholder="联系方式">
              <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon" />
            </el-input>
        </el-form-item>
        
        <el-form-item prop="code" label="验证码">
          <el-input
            v-model="registerForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 63%"
            @keyup.enter.native="handleRegister"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" />
          </div>
        </el-form-item>

        <el-form-item style="width:100%;">
          <el-button
            :loading="loading_register"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleRegister"
          >
            <span v-if="!loading_register">注册</span>
            <span v-else>注 册 中...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import moment from 'moment'
import { pie } from 'xj-echarts/src/js/pie.js'
import { line } from 'xj-echarts/src/js/line.js'
import echarts from 'echarts'
import echartsGL from 'echarts-gl'
import world from 'echarts/map/js/world.js'
import '@/assets/js/huangyanqu'
import '@/assets/json/maps/331003001'
import '@/assets/json/maps/331003002'
import '@/assets/json/maps/331003003'
import '@/assets/json/maps/331003004'
import '@/assets/json/maps/331003005'
import '@/assets/json/maps/331003006'
import '@/assets/json/maps/331003007'
import '@/assets/json/maps/331003008'
import '@/assets/json/maps/331003101'
import '@/assets/json/maps/331003102'
import '@/assets/json/maps/331003103'
import '@/assets/json/maps/331003107'
import '@/assets/json/maps/331003108'
import '@/assets/json/maps/331003200'
import '@/assets/json/maps/331003201'
import '@/assets/json/maps/331003202'
import '@/assets/json/maps/331003203'
import '@/assets/json/maps/331003204'
import '@/assets/json/maps/331003205'
import vueSeamlessScroll from 'vue-seamless-scroll'
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import store from '@/store';
import { getCodeImg } from "@/api/login";
import { registerUser } from "@/api/register";

let targetName = '茅畲乡'
let geoCoordMap = {
  '头陀镇':[121.14518654798,28.692252772224],
  '沙埠镇':[121.14918654798,28.557252772224],
}
let targetMap = {
  '头陀镇':[121.14118654798,38.559252772224,0],
  '茅畲乡':[112.13118654798,28.557252772224,0],
  '西城街道':[120.91149554752,28.499674208713,0],
  '富山乡':[121.18953854774,28.517142773211,0],
  '院桥镇':[121.12874254739,28.613685361045,0],
  '新前街道':[121.12874254739,28.613685361045,0],
  '沙埠镇':[121.12874254739,28.613685361045,0],
  '高桥街道':[121.12874254739,28.613685361045,0],
  '北洋镇':[121.12874254739,28.613685361045,0],
  '东城街道':[121.12874254739,28.613685361045,0],
  '上垟乡':[121.12874254739,28.613685361045,0],
  '北城街道':[121.12874254739,28.613685361045,0],
  '宁溪镇':[121.12874254739,28.613685361045,0],
  '南城街道':[121.12874254739,28.613685361045,0],
  '澄江街道':[121.12874254739,28.613685361045,0],
  '屿头乡':[121.12874254739,28.613685361045,0],
  '上郑乡':[121.12874254739,28.613685361045,0],
  '江口街道':[121.12874254739,28.613685361045,0],
  '平田乡':[121.12874254739,28.613685361045,0],
}
let target = geoCoordMap[targetName]
let convertData = function(data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
      var geoCoord = geoCoordMap[data[i].name]
      if (geoCoord) {
        if (data[i].name === targetName) {
          res.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value),
            itemStyle: {
              color: '#FFA211'
            },
            label: {
              textStyle: {
                fontSize: 20,
                lineHeight: 20
              }
            }
          })
        } else {
          res.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value)
          })
        }
      }
  }
  return res
}
let lineData = function(data) {
  var res = [];
  for (var i = 0; i < data.length; i++) {
      var geoCoord = geoCoordMap[data[i].name]
      if (geoCoord) {
        res.push([geoCoord, target])
      }
  }
  return res
}
export default {
  data () {
    return {
      time1: '',
      time2: '',
      time3: '',
      map: null,
      map2: null,
      mapData: [
        {name: '头陀镇', value: 10},
        {name: '沙埠镇', value: 12},
      ],
      mapLineData: [],
      name1: '黄岩区',
      name2: '',
      addressCode: '',
      option: {
        step: 0.3,
        openTouch: true,
        switchDelay: 100,
        waitTime: 200,
        autoPlay: true
      },
      hqzcData: [],
      tsqydkcpData: [],
      szqdData: [],
      open: false,
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        //code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
      },
      loading: false,
      loginTitle: '',
      redirect: undefined,
      loading_register: false,
      loginType: 1,
      registerTitle: '注册',
      registerShow: false,
      registerForm: {
        userName: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
        nickName: "",
        contactPerson: "",
        contactPhone: ""
      },
      registerRules: {
        userName: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        code: [{ required: true, trigger: "change", message: "验证码不能为空" }],
        nickName: [
          { required: true, trigger: "blur", message: "企业名称不能为空" }
        ],
        contactPerson: [
          { required: true, trigger: "blur", message: "联系人不能为空" }
        ],
        contactPhone: [
          { required: true, trigger: "blur", message: "联系电话不能为空" }
        ]
      },
      codeUrl: "",
      cookiePassword: ""
    }
  },
  created () {
    moment.lang('zh-cn')
    setInterval(() => {
      this.getTopLeft_text1();
    }, 1000);
  },
  mounted () {
    this.getData()
    this.showAll('first')
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.registerForm.uuid = res.uuid;
      });
    },
    /** 打开登录窗口 */
    openLoginForm(loginType) {
      // 先判断是否已经登录
      if(store.getters.name != null && store.getters.name != ''){
        this.$router.push({ path: this.redirect || "/" });
      }else{
        this.loginType = loginType;
        this.loginTitle = '登录入口';
        this.open = true;
      }
    },
    openRegisterForm() {
      this.open = false;
      this.registerShow = true;
      this.getCode();
    }, 
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" });
            })
            .catch(() => {
              this.loading = false;
              //this.getCode();
            });
        }
      });
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading_register = true;
          registerUser(this.registerForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("注册成功");
                this.registerShow = false;
                this.open = true;
              }
              this.loading_register = false;
          });
        }
      });
    },
    goUrl (url) {
      window.open(url)
    },
    getData () {
      this.$get('http://api.hy96596.com/pbc/api/sst/getHqzcList', {
        addressCode: this. addressCode
      }).then(res => {
        if (res.code === 200) {
          this.hqzcData = res.data
        }
      })
      this.$get('http://api.hy96596.com/pbc/api/sst/getProductList', {
        addressCode: this. addressCode
      }).then(res => {
        if (res.code === 200) {
          this.tsqydkcpData = res.data
        }
      })
      this.$get('http://api.hy96596.com/pbc/api/sst/getSzqdList', {
        addressCode: this. addressCode
      }).then(res => {
        if (res.code === 200) {
          this.szqdData = res.data
        }
      })
    },
    showAll (type) {
      this.name2 = ''
      this.addressCode = ''
      this.$get('http://api.hy96596.com/pbc/api/sst/getQydtzb').then(res => {
        if (res.code === 200) {
          this.randerMap(res.data.data_wdj, res.data.data_yrz, res.data.data_zzdj, type)
        }
      })
    },
    randerMap (data_wdj, data_yrz, data_zzdj, type) {
      this.map = echarts.init(document.getElementById('map'))
      let optionArr = [
        {id: '331003001', name:	'东城街道'},
        {id: '331003002', name:	'南城街道'},
        {id: '331003003', name:	'西城街道'},
        {id: '331003004', name:	'北城街道'},
        {id: '331003005', name:	'新前街道'},
        {id: '331003006', name:	'澄江街道'},
        {id: '331003007', name:	'江口街道'},
        {id: '331003008', name:	'高桥街道'},
        {id: '331003101', name:	'宁溪镇'},
        {id: '331003102', name:	'北洋镇'},
        {id: '331003103', name:	'头陀镇'},
        {id: '331003107', name:	'院桥镇'},
        {id: '331003108', name:	'沙埠镇'},
        {id: '331003200', name:	'屿头乡'},
        {id: '331003201', name:	'上郑乡'},
        {id: '331003202', name:	'富山乡'},
        {id: '331003203', name:	'茅畲乡'},
        {id: '331003204', name:	'上垟乡'},
        {id: '331003205', name:	'平田乡'}
      ]
      var handleEvents = {
        resetOption: function(i, o, n) {
          var breadcrumb = this.createBreadcrumb(n);

          var j = name.indexOf(n);
          var l = o.graphic.length;
          if (j < 0) {
            o.graphic.push(breadcrumb);
            o.graphic[0].children[0].shape.x2 = 145;
            o.graphic[0].children[1].shape.x2 = 145;
            if (o.graphic.length > 2) {
              for (var x = 0; x < opt.data.length; x++) {
                if (n === opt.data[x].name) {
                  o.series[0].data = handleEvents.initSeriesData([opt.data[x]]);
                  break;
                } else o.series[0].data = [];
              }
            }
            name.push(n);
            idx++;
          } else {
            o.graphic.splice(j + 2, l);
            if (o.graphic.length <= 2) {
              o.graphic[0].children[0].shape.x2 = 60;
              o.graphic[0].children[1].shape.x2 = 60;
              o.series[0].data = handleEvents.initSeriesData(opt.data);
              o.series[0].data = handleEvents.initSeriesData(opt.persondata);
            }
            name.splice(j + 1, l);
            idx = j;
            pos.leftCur -= pos.leftPlus * (l - j - 1);
          }

          o.geo.map = n;
          o.geo.zoom = 0.8;
          i.clear();
          i.setOption(o);
          this.zoomAnimation();
          opt.callback(n, o, i);
        },
        createBreadcrumb: function(name) {
          var cityToPinyin = {
            海港区: "haigangqu",
            山海关区: "shanhaiguanqu",
            北戴河区: "beidaihequ",
            抚宁区: "funingqu",
            青龙满族自治县: "qinglongmanzuzizhixian",
            昌黎县: "changlixian",
            卢龙县: "lulongxian"
          };
          var breadcrumb = {
            type: "group",
            id: name,
            left: pos.leftCur + pos.leftPlus,
            top: pos.top + 3,
            children: [
              {
                type: "polyline",
                left: -90,
                top: -5,
                shape: {
                  points: line
                },
                style: {
                  stroke: "#fff",
                  key: name
                  // lineWidth: 2,
                },
                onclick: function() {
                  var name = this.style.key;
                  handleEvents.resetOption(chart, option, name);
                }
              },
              {
                type: "text",
                left: -68,
                top: "middle",
                style: {
                  text: name,
                  textAlign: "center",
                  fill: style.textColor,
                  font: style.font
                },
                onclick: function() {
                  var name = this.style.text;
                  handleEvents.resetOption(chart, option, name);
                }
              },
              {
                type: "text",
                left: -68,
                top: 10,
                style: {
                  name: name,
                  text: cityToPinyin[name]
                    ? cityToPinyin[name].toUpperCase()
                    : "",
                  textAlign: "center",
                  fill: style.textColor,
                  font: '12px "Microsoft YaHei", sans-serif'
                },
                onclick: function() {
                  // console.log(this.style);
                  var name = this.style.name;
                  handleEvents.resetOption(chart, option, name);
                }
              }
            ]
          };

          pos.leftCur += pos.leftPlus;

          return breadcrumb;
        },
        // 设置effectscatter
        initSeriesData: function(data) {
          var temp = [];
          for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
              temp.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value, data[i].level)
              });
            }
          }
          return temp;
        },
        zoomAnimation: function() {
          var count = null;
          var zoom = function(per) {
            if (!count) count = per;
            count = count + per;
            chart.setOption({
              geo: {
                zoom: count
              }
            });
            if (count < 1)
              window.requestAnimationFrame(function() {
                zoom(0.5);
              });
          };
          window.requestAnimationFrame(function() {
            zoom(0.5);
          });
        }
      };
      var levelColorMap = {
        "1": "rgba(241, 109, 115, .8)",
        "2": "rgba(255, 235, 59, .7)",
        "3": "rgba(76, 210, 64, 1)"
      };
      let options = {
        geo: {
          show: true,
          zlevel:13,
          map: 'huangyanqu',
            itemStyle: {
              normal: {
                borderColor: "rgba(147, 235, 248, 1)",
                borderWidth: 1,
                areaColor: {
                  type: "radial",
                  x: 0.5,
                  y: 0.5,
                  r: 0.8,
                  colorStops: [
                    {
                      offset: 0,
                      color: "rgba(147, 235, 248, 0)" // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: "rgba(147, 235, 248, .1)" // 100% 处的颜色
                    }
                  ],
                  globalCoord: false // 缺省为 false
                },
                shadowColor: "rgba(128, 217, 248, 1)",
                // shadowColor: 'rgba(255, 255, 255, 1)',
                shadowOffsetX: -2,
                shadowOffsetY: 2,
                shadowBlur: 10
              },
              emphasis: {
                areaColor: "rgba(128, 217, 248, 0.5)",
                borderWidth: 0
              }
            },
            label: {
              show: true,
              textStyle: {
                color: '#ffffff', //地图初始化区域字体颜色
                fontSize: 14,
                opacity: 1,
                fontFamily: 'Microsoft YaHei',
                backgroundColor: 'rgba(11,63,83,0)'
              },
              emphasis: {
                color: 'rgba(0, 0, 0, 1)'
              }
            },
            emphasis: { //当鼠标放上去  地区区域是否显示名称
              textStyle: {
                color: 'rgba(255,230,243, 0.9)',
              },
              itemStyle: {
                color: 'rgba(255,230,243, 0.8)'
              }
            },
        },
        series: [
          {
            name: 'effectScatter',
            type: "effectScatter",
            coordinateSystem: 'geo',
            symbol: 'circle',
            shading: 'lambert',
            bevelSize:0.5,
            zlevel: 11,
            showEffectOn: "render",
            rippleEffect: {
              period: 5,
              scale: 4,
              brushType: "fill"
            },
            hoverAnimation: true,
            itemStyle: {
              normal: {
                color: 'rgba(241, 109, 115, 0.8)',
                shadowBlur: 6,
                shadowColor: "#333"
              }
            },
            symbolSize: 14,
            data: data_wdj
          },
          {
            name: 'effectScatter',
            type: "effectScatter",
            coordinateSystem: 'geo',
            symbol: 'circle',
            shading: 'lambert',
            bevelSize:0.5,
            zlevel: 11,
            showEffectOn: "render",
            rippleEffect: {
              period: 5,
              scale: 4,
              brushType: "fill"
            },
            hoverAnimation: true,
            itemStyle: {
              normal: {
                color: 'rgba(255, 235, 59, .7)',
                shadowBlur: 6,
                shadowColor: "#333"
              }
            },
            symbolSize: 10,
            data: data_zzdj
          },
          {
            name: 'effectScatter',
            type: "effectScatter",
            coordinateSystem: 'geo',
            symbol: 'circle',
            shading: 'lambert',
            bevelSize:0.5,
            zlevel: 11,
            showEffectOn: "render",
            rippleEffect: {
              period: 5,
              scale: 4,
              brushType: "fill"
            },
            hoverAnimation: true,
            itemStyle: {
              normal: {
                color: 'rgba(76, 210, 64, 1)',
                shadowBlur: 6,
                shadowColor: "#333"
              }
            },
            symbolSize: 10,
            data: data_yrz
          },
        ]
      }
      this.map.setOption(options)
      if (type && type == 'first') {
        this.map.on("click", (params) => {
          let index = optionArr.findIndex(val => {return val.name === params.name})
          if (index >= 0) {
            this.name2 = params.name
            this.initMap2(optionArr[index].id)
          }
        })
      }
    },
    initMap2 (id) {
      this.addressCode = id
      this.getData()
      this.$get('http://api.hy96596.com/pbc/api/sst/getQydtzb', {
        addressCode: id
      }).then(res => {
        if (res.code === 200) {
          let data_wdj = res.data.data_wdj
          let data_yrz = res.data.data_yrz
          let data_zzdj = res.data.data_zzdj
          this.map2 = echarts.init(document.getElementById('map2'))
          let options = {
            geo: {
              show: true,
              zlevel:13,
              map: id,
                itemStyle: {
                  normal: {
                    borderColor: "rgba(147, 235, 248, 1)",
                    borderWidth: 1,
                    areaColor: {
                      type: "radial",
                      x: 0.5,
                      y: 0.5,
                      r: 0.8,
                      colorStops: [
                        {
                          offset: 0,
                          color: "rgba(147, 235, 248, 0)" // 0% 处的颜色
                        },
                        {
                          offset: 1,
                          color: "rgba(147, 235, 248, .1)" // 100% 处的颜色
                        }
                      ],
                      globalCoord: false // 缺省为 false
                    },
                    shadowColor: "rgba(128, 217, 248, 1)",
                    // shadowColor: 'rgba(255, 255, 255, 1)',
                    shadowOffsetX: -2,
                    shadowOffsetY: 2,
                    shadowBlur: 10
                  },
                  emphasis: {
                    areaColor: "rgba(128, 217, 248, 0.5)",
                    borderWidth: 0
                  }
                },
                label: {
                  show: true,
                  textStyle: {
                    color: '#ffffff', //地图初始化区域字体颜色
                    fontSize: 14,
                    opacity: 1,
                    fontFamily: 'Microsoft YaHei',
                    backgroundColor: 'rgba(11,63,83,0)'
                  },
                  emphasis: {
                    color: 'rgba(0, 0, 0, 1)'
                  }
                },
                emphasis: { //当鼠标放上去  地区区域是否显示名称
                  textStyle: {
                    color: 'rgba(255,230,243, 0.9)',
                  },
                  itemStyle: {
                    color: 'rgba(255,230,243, 0.8)'
                  }
                },
            },
            series: [
              {
                name: 'effectScatter',
                type: "effectScatter",
                coordinateSystem: 'geo',
                symbol: 'circle',
                shading: 'lambert',
                bevelSize:0.5,
                zlevel: 11,
                showEffectOn: "render",
                rippleEffect: {
                  period: 5,
                  scale: 4,
                  brushType: "fill"
                },
                hoverAnimation: true,
                itemStyle: {
                  normal: {
                    color: 'rgba(241, 109, 115, 0.8)',
                    shadowBlur: 6,
                    shadowColor: "#333"
                  }
                },
                symbolSize: 14,
                data: data_wdj
              },
              {
                name: 'effectScatter',
                type: "effectScatter",
                coordinateSystem: 'geo',
                symbol: 'circle',
                shading: 'lambert',
                bevelSize:0.5,
                zlevel: 11,
                showEffectOn: "render",
                rippleEffect: {
                  period: 5,
                  scale: 4,
                  brushType: "fill"
                },
                hoverAnimation: true,
                itemStyle: {
                  normal: {
                    color: 'rgba(255, 235, 59, .7)',
                    shadowBlur: 6,
                    shadowColor: "#333"
                  }
                },
                symbolSize: 10,
                data: data_zzdj
              },
              {
                name: 'effectScatter',
                type: "effectScatter",
                coordinateSystem: 'geo',
                symbol: 'circle',
                shading: 'lambert',
                bevelSize:0.5,
                zlevel: 11,
                showEffectOn: "render",
                rippleEffect: {
                  period: 5,
                  scale: 4,
                  brushType: "fill"
                },
                hoverAnimation: true,
                itemStyle: {
                  normal: {
                    color: 'rgba(76, 210, 64, 1)',
                    shadowBlur: 6,
                    shadowColor: "#333"
                  }
                },
                symbolSize: 10,
                data: data_yrz
              },
            ]
          }
          this.map2.setOption(options)
        }
      })
    },
    initSeriesData: function(data) {
      var temp = [];
      for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
          temp.push({
            name: data[i].name,
            value: geoCoord.concat(data[i].value, data[i].level)
          });
        }
      }
      return temp;
    },
    getTime (item) {
      return moment(new Date(item.time)).format('L')
    },
    getTime1 (item) {
      return moment(new Date(item.time)).format('YYYY-MM')
    },
    getTopLeft_text1 () {
      this.time1 = moment().format('dddd')
      this.time2 = moment().format('LTS')
      this.time3 = moment().format('LL')
    }
  }
}
</script>
<style scoped lang='stylus'>
.home2
  position relative
  min-height 100vh
  min-width 1900px
  background: linear-gradient(180deg, #020E34 0%, #000940 100%);
  .background
    position absolute
    top -15%
    left 0
    width 100%
    height 100%
    background-color #000
    background url('../../../assets/images/bg2.png') no-repeat center
    background-size 90% 90%
    z-index 0
  .background_bottom
    position absolute
    bottom 0
    left 0
    width 100%
    height 69px
    background-color #000
    background url('../../../assets/images/bottom.png') no-repeat center
    background-size 100% 100%
    z-index 0
  .title
    padding 5px 20px
    margin-bottom 8px
    background: linear-gradient(90deg, rgba(49,213,246, 0.1) 0%, rgba(49,213,246, 0.1) 100%)
    position relative
    &:before
      content ''
      position absolute
      top 0
      left 0
      width 3px
      height 100%
      background-color #31D5F6
    &:after
      content ''
      position absolute
      top 0
      left 6px
      width 2px
      height 100%
      background-color #31D5F6
    h2
      font-size 16px
      color rgba(255, 255, 255, 1)
      line-height 28px
      font-weight 100
    p
      font-size 12px
      color rgba(255, 255, 255, 0.5)
  .top_title
    width 100%
    height 102px
    background url('../../../assets/images/head_2.png') no-repeat center
    background-size 100% 100%
    .rightTime
      position absolute
      top 20px
      right 80px
      .p_1
        display flex
        line-height 20px
        label
          font-size 12px
          color #FFFFFF
          margin-right 20px
        span
          font-size 16px
          color #3AF3F4
      .p_2
        font-size 16px
        color #FFFFFF
        line-height 20px
  .content
    display flex
    height 950px
    padding 20px
    .left, .right
      width 400px
    .right
      .top
        height 450px
      .bottom
        height calc(100% - 450px)
    .center
      flex 1
      margin-right 20px
      position relative
      // &:after
      //   width 1188px
      //   height 753px
      //   content ''
      //   position absolute
      //   top 0
      //   left 0
      //   right 0
      //   bottom 0
      //   margin auto
      //   background url('../../../assets/images/bg.png') no-repeat center
      //   background-size 100% 100%
      //   transform-origin top left
      //   z-index 0
      &:before
        content ''
        position absolute
        top 0
        left 0
        right 0
        bottom 0
        margin auto
        width 0
        height 0
        background rgba(255, 255, 0, 0.1)
        box-shadow 0 0 250px 250px rgba(255, 255, 0, 0.1)
        border-radius 50%
        animation map 5s 0s infinite
      .top
        height 753px
        position relative
        .map
          width 100%
          height 100%
          position absolute
          top 0
          left 0
          right 0
          bottom 0
          margin auto
          z-index 10
        .mapName
          position absolute
          top 0
          left 0
          color #fff
          line-height 20px
          display flex
          z-index 12
          span
            margin 0 10px
          p
            &:nth-of-type(1)
              font-size 16px
              cursor pointer
            &:nth-of-type(2)
              font-size 14px
              color #ff0
      .bottom
        height 100px
        display flex
        justify-content space-around
        position absolute
        left 0
        width 100%
        bottom 50px
        z-index 10
        .item
          width 271px
          height 112px
          background-size 100% 100%
          background-position center center
          background-repeat no-repeat
        .btn1
          background-image url('../../../assets/images/btn1.png')
          &:hover
            background-image url('../../../assets/images/btn1_hover.png')
        .btn2
          background-image url('../../../assets/images/btn2.png')
          &:hover
            background-image url('../../../assets/images/btn2_hover.png')
        .btn3
          background-image url('../../../assets/images/btn3.png')
          &:hover
            background-image url('../../../assets/images/btn3_hover.png')

    .left
      margin-right 20px
      height 100%

  .chart
    width 100%
    height 100%
  .card
    height calc(100% - 60px)
    position relative
  .map-legend
    position absolute
    top 10px
    right 50px
    .row
      display flex
      margin-bottom 15px
      line-height 16px
      i
        width 10px
        height 10px
        border-radius 50%
        position relative
        margin-right 10px
      span
        color rgba(255, 255, 255, 0.9)
        font-size 14px
      .legend2-icon, .legend3-icon, .legend4-icon
        left 3px
        top 3px
        margin-right 15px
        &:before
          content ''
          width 18px
          height 18px
          border-radius 50%
          position absolute
          top -4px
          left -4px
        &:after
          content ''
          width 26px
          height 26px
          border-radius 50%
          position absolute
          top -8px
          left -8px
      .legend2-icon
        background-color rgba(241, 109, 115, .8)
        &:before
          background-color rgba(241, 109, 115, .5)
        &:after
          background-color rgba(241, 109, 115, .2)
      .legend3-icon
        background-color rgba(255, 235, 59, .7)
        &:before
          background-color rgba(255, 235, 59, .4)
        &:after
          background-color rgba(255, 235, 59, .1)
      .legend4-icon
        background-color rgba(76, 210, 64, .7)
        &:before
          background-color rgba(76, 210, 64, .4)
        &:after
          background-color rgba(76, 210, 64, .1)
  .seamless-warp
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;
    .items 
      .row
        display flex
        line-height 30px
        color #fff
        font-size 14px
        background: linear-gradient(85deg, rgba(62,180,235,0.2) 0%, rgba(16, 207, 248, 0.2) 8%, rgba(62, 180, 235, 0) 100%)
        margin-bottom 20px
        position relative
        height 30px
        .dept
          width 80px
          margin-left 20px
          color #E6FAF9
          text-overflow: ellipsis
          overflow: hidden
          white-space: nowrap
        .time
          width 80px
          color #E6FAF9
        .name, .content
          flex 1
          padding 0 10px 0 15px
          height 26px
          line-height 26px
          position relative
          top 2px
          border-radius 30px
          color #0CD2F9
          font-size 13px
          border 1px solid rgba(7, 213, 253, 0.5)
          background rgba(255, 255, 255, 0.1)
          text-overflow: ellipsis
          overflow: hidden
          white-space: nowrap
          cursor pointer
          &:hover
            background rgba(255, 255, 255, 0.2)
        .content
          margin-right 10px
          text-align center
        .remark
          width 30%
          text-overflow: ellipsis
          overflow: hidden
          white-space: nowrap
        &:before
          content ''
          width 100%
          height 1px
          background-color rgba(11,194,229, 0.4)
          position absolute
          top -4px
          left 0
        &:after
          content ''
          width 100%
          height 1px
          background-color rgba(11,194,229, 0.4)
          position absolute
          left  0
          bottom -4px

@keyframes map 
  0% 
    box-shadow 0 0 100px 100px rgba(255, 255, 100, 0.01)
  50%
    box-shadow 0 0 250px 250px rgba(255, 255, 100, 0.1)
  100% 
    box-shadow 0 0 100px 100px rgba(255, 255, 100, 0.01)
  

no-wrap()
  text-overflow: ellipsis
  overflow: hidden
  white-space: nowrap

</style>