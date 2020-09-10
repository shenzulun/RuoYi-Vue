import request from '@/utils/request'

// 查询企业信息列表
export function listCustinfo(query) {
  return request({
    url: '/pbc/custinfo/list',
    method: 'get',
    params: query
  })
}

// 查询企业信息详细
export function getCustinfo(id) {
  return request({
    url: '/pbc/custinfo/' + id,
    method: 'get'
  })
}

// 新增企业信息
export function addCustinfo(data) {
  return request({
    url: '/pbc/custinfo',
    method: 'post',
    data: data
  })
}

// 修改企业信息
export function updateCustinfo(data) {
  return request({
    url: '/pbc/custinfo',
    method: 'put',
    data: data
  })
}

// 删除企业信息
export function delCustinfo(id) {
  return request({
    url: '/pbc/custinfo/' + id,
    method: 'delete'
  })
}

// 导出企业信息
export function exportCustinfo(query) {
  return request({
    url: '/pbc/custinfo/export',
    method: 'get',
    params: query
  })
}