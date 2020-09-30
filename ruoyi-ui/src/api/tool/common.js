import request from '@/utils/request'

// 获取行政区划代码的详细说明
export function getAddressCodeDetail(query) {
    return request({
      url: '/tool/addressCodeDetail',
      method: 'get',
      params: query
    })
}
