// pages/second/second_activity.js
Page({
  data:{
    myData:"我是自定义数据"
  },

  showToast:function(){
    wx.showToast({
      title: "点击按钮事件"
    })
  },

  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  },
  onPullDownRefresh: function() {
    // 页面相关事件处理函数--监听用户下拉动作
    String7
  },
  onReachBottom: function() {
    // 页面上拉触底事件的处理函数
    String8
  },
  onShareAppMessage: function() {
    // 用户点击右上角分享
    return {
      title: 'title', // 分享标题
      desc: 'desc', // 分享描述
      path: 'path' // 分享路径
    }
  }

})