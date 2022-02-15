"use strict";
var page = require('webpage').create(),
    system = require('system'),
    address, output;

address = system.args[1];
output = system.args[2];

page.paperSize = {
	// A4 72dpi 595*842
	width: '595px',
	height: '842px',
	margin: '0px',
	padding: '0px',
    footer: {
        height: "1cm",
        contents: phantom.callback(function(pageNum, numPages) {
            if (pageNum == 1) {
                return '';
            }
            return '<div style="text-align: center;font-size: 10px;color: gray;">' + pageNum + ' / ' + numPages + '</div>';
        })
    }
};

page.settings.userAgent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36';

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