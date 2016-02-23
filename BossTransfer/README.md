# boss直聘跳转动画效果
详情见[我的博客](http://blog.csdn.net/qwe511455842/article/details/50726291)</br>
![效果图](https://github.com/AriaLyy/BlogDemo/blob/master/BossTransfer/img/boss.gif "")

#使用
```java
  mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TurnHelp.turn(MainActivity.this, findViewById(android.R.id.content), view);
            }
        });
```



