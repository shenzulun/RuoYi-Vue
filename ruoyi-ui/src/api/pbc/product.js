import request from '@/utils/request'

// 查询贷款产品列表
export function listProduct(query) {
  return request({
    url: '/pbc/product/list',
    method: 'get',
    params: query
  })
}

// 查询贷款产品详细
export function getProduct(id) {
  return request({
    url: '/pbc/product/' + id,
    method: 'get'
  })
}

// 新增贷款产品
export function addProduct(data) {
  return request({
    url: '/pbc/product',
    method: 'post',
    data: data
  })
}

// 修改贷款产品
export function updateProduct(data) {
  return request({
    url: '/pbc/product',
    method: 'put',
    data: data
  })
}

// 删除贷款产品
export function delProduct(id) {
  return request({
    url: '/pbc/product/' + id,
    method: 'delete'
  })
}

// 导出贷款产品
export function exportProduct(query) {
  return request({
    url: '/pbc/product/export',
    method: 'get',
    params: query
  })
}