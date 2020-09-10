import request from '@/utils/request'

// 查询银行信息列表
export function listBankinfo(query) {
  return request({
    url: '/pbc/bankinfo/list',
    method: 'get',
    params: query
  })
}

// 查询银行信息详细
export function getBankinfo(id) {
  return request({
    url: '/pbc/bankinfo/' + id,
    method: 'get'
  })
}

// 新增银行信息
export function addBankinfo(data) {
  return request({
    url: '/pbc/bankinfo',
    method: 'post',
    data: data
  })
}

// 修改银行信息
export function updateBankinfo(data) {
  return request({
    url: '/pbc/bankinfo',
    method: 'put',
    data: data
  })
}

// 删除银行信息
export function delBankinfo(id) {
  return request({
    url: '/pbc/bankinfo/' + id,
    method: 'delete'
  })
}

// 导出银行信息
export function exportBankinfo(query) {
  return request({
    url: '/pbc/bankinfo/export',
    method: 'get',
    params: query
  })
}