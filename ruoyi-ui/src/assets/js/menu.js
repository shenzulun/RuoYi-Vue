export let menu = [
  {
    name: '首页',
    id: '1',
    path: '/layout/home',
    icon: 'iconshouye',
    show: true,
    children: [
      {
        name: '菜单一',
        id: '1-1',
        path: '/layout/menu1',
        icon: 'icon-shuju1',
        show: true,
        children: [
          {
            name: '菜单一',
            id: '1-1-1',
            path: '/layout/menu1',
            icon: 'iconzu3',
            show: true,
            children: []
          },
          {
            name: '菜单二',
            id: '1-1-2',
            path: '/layout/menu2',
            icon: 'iconzu3',
            show: true,
            children: [
              {
                name: '菜单2-1',
                id: '1-1-2-1',
                path: '/layout/menu2',
                icon: 'iconzu3',
                show: true,
                children: []
              },
            ]
          }
        ]
      }
    ]
  }
]