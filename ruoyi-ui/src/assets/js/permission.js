import router from "../../router/index"
import NProgress from "nprogress"
import "nprogress/nprogress.css"
import { getToken } from './auth' 

NProgress.configure({ showSpinner: false }) 

const whiteList = [] // 不重定向白名单

router.beforeEach(async(to, from, next) => {
  NProgress.start()
  next()
  // const hasToken = getToken() // 是否携带token

  // if (hasToken) {
  //   if (to.path === '/login') {
  //     next({ path: '/' })
  //     NProgress.done()
  //   } else {
  //     const hasRoles = store.state.roles // 权限
  //     if (hasRoles) {
  //       next()
  //     } else {
  //       try {
  //         next()
  //       } catch (error) {
  //         next('/login')
  //         NProgress.done()
  //       }
  //     }
  //   }
  // } else {
  //   if (whiteList.indexOf(to.path) !== -1) {
  //     next()
  //   } else {
  //     next('/login')
  //     NProgress.done()
  //   }
  // }
})

router.afterEach(() => {
  NProgress.done()
})