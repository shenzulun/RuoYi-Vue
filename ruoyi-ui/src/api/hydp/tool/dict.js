import request from '@/utils/request'

// 查询客户信息查询列表
export function listDataDict(query) {
  return request({
    url: '/tool/dict/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息查询详细
export function getDataDict(tableName) {
  return request({
    url: '/tool/dict/' + tableName,
    method: 'get'
  })
}
