# 项目说明

IDE by AndroidStudio

## 本项目设置两个git地址

https://codeleading.com/article/59774038985/
```shell
# 给origin添加一个远程push地址，这样一次push就能同时push到两个地址上面
git remote set-url --add origin https://github.com/JUBO1992/Jyb-nacol.git
# 查看是否多了一条push地址
git remote -v
# 如果第一次推不上去代码，可以使用强推的方式
git push origin master -f
# 删除origin添加的远程push地址
git remote set-url --delete origin https://github.com/JUBO1992/Jyb-nacol.git
```
