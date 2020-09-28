import request from '@/utils/request'

// 百度地图API 
// 地址 -> 坐标
export function showLocation(query) {
    return request({
      url: '/tool/showLocation',
      method: 'get',
      params: query
    })
}
