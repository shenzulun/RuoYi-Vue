import request from '@/utils/request'

// 查询文章信息列表
export function listActicle(query) {
  return request({
    url: '/pbc/acticle/list',
    method: 'get',
    params: query
  })
}

// 查询文章信息详细
export function getActicle(id) {
  return request({
    url: '/pbc/acticle/' + id,
    method: 'get'
  })
}

// 新增文章信息
export function addActicle(data) {
  return request({
    url: '/pbc/acticle',
    method: 'post',
    data: data
  })
}

// 修改文章信息
export function updateActicle(data) {
  return request({
    url: '/pbc/acticle',
    method: 'put',
    data: data
  })
}

// 删除文章信息
export function delActicle(id) {
  return request({
    url: '/pbc/acticle/' + id,
    method: 'delete'
  })
}

// 导出文章信息
export function exportActicle(query) {
  return request({
    url: '/pbc/acticle/export',
    method: 'get',
    params: query
  })
}