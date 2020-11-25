import Vue from 'vue'
import axios from 'axios'
import router from '@/router'
import store from '@/store'
import Qs from 'qs'
import { MessageBox } from 'element-ui'

export const hostUrl = window.baseUrl
const http = axios.create({
    timeout: 1000 * 60 * 30,
    // withCredentials: true,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
  if (config.data) {
    // config.headers = {
    //   "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
    // }                
    // config.data.ticket = sessionStorage.getItem('ticket')
    // config.data = Qs.stringify(config.data)
  } else if (config.params) {
    // config.params.ticket = sessionStorage.getItem('ticket')
  }
  config.url = http.adornUrl(config.url)
  return config
}, error => {
  return Promise.reject(error)
})

// http.defaults.validateStatus(status => {
//   console.log(status)
// })

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
  if (response.data.code === 99999) {
    MessageBox.confirm(
      `<div class="error_message">
       <p><i>${response.data.message}</i></p>
       </div>`,
      '提示', 
    {
      type: 'error',
      center: true,
      showCancelButton: false,
      showConfirmButton: false,
      dangerouslyUseHTMLString: true
    }).catch(() => {})
  }
  return response.data
}, error => {
    return Promise.reject(error)
})

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
  return '' + actionName
}

function clearLoginInfo() {
    Vue.cookie.delete('token')
}
export default http

export function post (url, data) {
  return http(url, {
    method: 'post',
    data: data
  }).then(res =>{
    return res
  })
}

export function get (url, params) {
  return http(url, {
    method: 'get',
    params: params
  }).then(res => {
    return res
  })
}

export function put (url, params) {
  return http(url, {
    method: 'put',
    data: params
  }).then(res => {
    return res
  })
}

export function del (url, params) {
  return http(url, {
    method: 'delete',
    data: params
  }).then(res => {
    return res
  })
}