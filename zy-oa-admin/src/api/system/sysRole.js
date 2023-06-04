import request from '@/utils/request'

const api_name = '/admin/system/sysRole'
export default {
  getPageList(current, limit, searchObj) {
    return request({
      url: `${api_name}/${current}/${limit}`,
      method: 'get',
      // 如果普通对象参数写法， params:对象参数名称
      // 如果使用json格式传递， data: 对象参数名称
      params: searchObj
    })
  },
  remove(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  save(sysRole) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: sysRole
    })
  },
  update(sysRole) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: sysRole
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },
  batchRemove(ids) {
    return request({
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: ids
    })
  }
}
