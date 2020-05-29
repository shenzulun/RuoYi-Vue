import request from '@/utils/request'

// 查询数据字典列表
export function listDataDict(query) {
  return request({
    url: '/tool/dataDict/list',
    method: 'get',
    params: query
  })
}

// 查询表的详细信息
export function getDataDict(tableName) {
  return request({
    url: '/tool/dataDict/' + tableName,
    method: 'get'
  })
}

// 查询系统分类
export function getSystemCode() {
  return request({
    url: '/tool/dataDict/code',
    method: 'get'
  })
}

// 查询表字段的码值
export function getColumnCode(query) {
  return request({
    url: '/tool/dataDict/column',
    method: 'get',
    params: query
  })
}