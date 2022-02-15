var page = require('webpage').create(),
    system = require('system'),
    address, output, size;

if (system.args.length < 3 || system.args.length > 5) {
    phantom.exit(1);//凡是结束必须调用。否则phantomjs不会停止
} else {
    address = system.args[1];//传入url地址
    output = system.args[2];//输出图片的地址
    size = system.args[3];//大小

    page.viewportSize = {width: 530, height: 940};//自定义截图宽高
    page.open(address, function (success) {
        page.open(address, function (status) {
            if (status !== 'success') {
                console.log('Unable to load the address!');
                phantom.exit(1);
            } else {
                page.evaluate(function() {
                    document.getElementsByTagName("html")[0].style.zoom = 0.68;/*workaround for phantomJS2 rendering pages too large*/
                });
                window.setTimeout(function () {
                    page.render(output);
                    phantom.exit();
                }, 5000);
            }
        });

    });

}