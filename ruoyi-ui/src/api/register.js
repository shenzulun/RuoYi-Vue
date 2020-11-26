import request from '@/utils/request'

// 注册方法
// 新增用户
export function registerUser(data) {
    return request({
      url: '/register',
      method: 'post',
      data: data
    })
}