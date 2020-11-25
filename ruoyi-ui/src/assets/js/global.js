// 全局组件引入
import Pagination from '../../components/base/pagination.vue'

const globalComponents = (Vue) => {
  if (globalComponents.installed) return
  Vue.component('pagination', Pagination) // 分页组件
}

export default globalComponents
