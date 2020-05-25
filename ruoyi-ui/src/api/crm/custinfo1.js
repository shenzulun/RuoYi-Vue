import request from '@/utils/request'

// 查询客户信息查询列表
export function listCustinfo1(query) {
  return request({
    url: '/crm/custinfo1/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息查询详细
export function getCustinfo1(id) {
  return request({
    url: '/crm/custinfo1/' + id,
    method: 'get'
  })
}

// 新增客户信息查询
export function addCustinfo1(data) {
  return request({
    url: '/crm/custinfo1',
    method: 'post',
    data: data
  })
}

// 修改客户信息查询
export function updateCustinfo1(data) {
  return request({
    url: '/crm/custinfo1',
    method: 'put',
    data: data
  })
}

// 删除客户信息查询
export function delCustinfo1(id) {
  return request({
    url: '/crm/custinfo1/' + id,
    method: 'delete'
  })
}

// 导出客户信息查询
export function exportCustinfo1(query) {
  return request({
    url: '/crm/custinfo1/export',
    method: 'get',
    params: query
  })
}