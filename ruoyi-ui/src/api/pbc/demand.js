import request from '@/utils/request'

// 查询企业融资需求列表
export function listDemand(query) {
  return request({
    url: '/pbc/demand/list',
    method: 'get',
    params: query
  })
}

// 查询企业融资需求详细
export function getDemand(id) {
  return request({
    url: '/pbc/demand/' + id,
    method: 'get'
  })
}

// 新增企业融资需求
export function addDemand(data) {
  return request({
    url: '/pbc/demand',
    method: 'post',
    data: data
  })
}

// 修改企业融资需求
export function updateDemand(data) {
  return request({
    url: '/pbc/demand',
    method: 'put',
    data: data
  })
}

// 删除企业融资需求
export function delDemand(id) {
  return request({
    url: '/pbc/demand/' + id,
    method: 'delete'
  })
}

// 导出企业融资需求
export function exportDemand(query) {
  return request({
    url: '/pbc/demand/export',
    method: 'get',
    params: query
  })
}