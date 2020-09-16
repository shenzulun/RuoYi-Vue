import request from '@/utils/request'

// 查询文章信息列表
export function listArticle(query) {
  return request({
    url: '/pbc/article/list',
    method: 'get',
    params: query
  })
}

// 查询文章信息详细
export function getArticle(id) {
  return request({
    url: '/pbc/article/' + id,
    method: 'get'
  })
}

// 新增文章信息
export function addArticle(data) {
  return request({
    url: '/pbc/article',
    method: 'post',
    data: data
  })
}

// 修改文章信息
export function updateArticle(data) {
  return request({
    url: '/pbc/article',
    method: 'put',
    data: data
  })
}

// 删除文章信息
export function delArticle(id) {
  return request({
    url: '/pbc/article/' + id,
    method: 'delete'
  })
}

// 导出文章信息
export function exportArticle(query) {
  return request({
    url: '/pbc/article/export',
    method: 'get',
    params: query
  })
}