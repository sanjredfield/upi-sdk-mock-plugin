// Empty constructor
function UpiSdkMockPlugin() {}

  // The function that passes work along to native shells
  // Message is a string, duration may be 'long' or 'short'
  UpiSdkMockPlugin.prototype.runAction = function(
    successCallback, errorCallback, action = 'SUCCESS') {
    return cordova.exec(successCallback, errorCallback, 'UpiSdkMockPlugin', action);
  }

  UpiSdkMockPlugin.install = function() {
    if (!window.plugins) {
      window.plugins = {};
    }
    window.plugins.upiSdkMockPlugin = new UpiSdkMockPlugin();
    return window.plugins.upiSdkMockPlugin;
};

cordova.addConstructor(UpiSdkMockPlugin.install);
