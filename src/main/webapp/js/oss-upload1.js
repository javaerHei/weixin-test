// web 端签名
var accessid= 'LTAIGn24iIondCnv',
accesskey= 'TEPdIXMnUplKp4lqZmKJfETt6paa5a',
host = 'http://test-bucket-my.oss-cn-shanghai.aliyuncs.com',//  "http://" + bucket + "." + endpoint;
g_dirname = '',// 上传目录
g_object_name = '',// 上传保存文件名
now = timestamp = Date.parse(new Date()) / 1000; 

var policyText = {
    "expiration": "2020-01-01T12:00:00.000Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
    "conditions": [
    ["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
    ]
};

var policyBase64 = Base64.encode(JSON.stringify(policyText))
message = policyBase64
var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, { asBytes: true }) ;
var signature = Crypto.util.bytesToBase64(bytes);

function set_upload_param(up, filename, ret)
{
    g_object_name = g_dirname;
    if (filename != '') {
    	g_object_name += filename;
    }
    new_multipart_params = {
        'key' : g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid, 
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

// 

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    //multi_selection: false,
	container: document.getElementById('container'),
	flash_swf_url : 'js/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : 'js/plupload-2.1.2/js/Moxie.xap',
    url : 'http://oss.aliyuncs.com',
    // 设置上传文件过滤条件
    /*filters: {
        mime_types : [ //只允许上传图片和zip文件
        { title : "Image files", extensions : "jpg,gif,png,bmp" },
        { title : "Zip files", extensions : "zip" }
        ], 
        max_file_size : '400kb', //最大只能上传400kb的文件
        prevent_duplicates : true //不允许选取重复文件
    },*/
	init: {
		PostInit: function() {
			document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
				set_upload_param(uploader, '', false);
				return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar progress-bar-success progress-bar-striped"  role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%"></div></div>'
				+'</div>';
			});
		},

		BeforeUpload: function(up, file) {
            set_upload_param(up, file.name, true);
            startTime = new Date().getTime();
            console.log(startTime);
        },

		UploadProgress: function(up, file) {
			var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			// 进度显示
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= file.percent+'%';
			progBar.setAttribute('aria-valuenow', file.percent);
		},

		FileUploaded: function(up, file, info) {
			// 上传完成
            if (info.status == 200) {
            	// 成功
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传成功';
            }
            else {
            	// 失败
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            }
            endTime = new Date().getTime();
            alert("上传耗时："+ (endTime - startTime)/1000 + "秒");
		},

		Error: function(up, err) {
			// 上传错误
			document.getElementById('console').appendChild(document.createTextNode("\n上传错误: " + err.message));
		}
	}
});

uploader.init();
