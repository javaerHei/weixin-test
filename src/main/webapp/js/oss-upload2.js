
var accessid = '',
host = '',
policyBase64 = '',
signature = '',
callbackbody = '',
filename = '',
key = '',
expire = 0,
g_object_name = '',// 上传保存文件名
now = timestamp = Date.parse(new Date()) / 1000; 

function get_signature() {
    //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    now = timestamp = Date.parse(new Date()) / 1000; 
    if (expire < now + 3) {
    	$.ajax({
    		url : "/oss/signature",
    		type : "get",
    		dataType : "json",
    		async : false,
    		success : function(result) {
    			if(result.success) {
    				var data = result.data;
    				host = data.host;
			        policyBase64 = data.policy;
			        accessid = data.accessid;
			        signature = data.signature;
			        expire = parseInt(data.expire);
			        callbackbody = data.callback;
			        console.log(data.callback);
			        key = data.dir;
    			}else {
    				alert("获取签名失败");
    			}
    		}
    	});
        return true;
    }
    return false;
};

function set_upload_param(up, filename, ret) {
    if (ret == false) {
        ret = get_signature();
    }
    g_object_name = key;
    if (filename != '') {
    	g_object_name += filename;
    }
    new_multipart_params = {
        'key' : g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid, 
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
        'callback' : callbackbody,
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
            } else if (info.status == 203) {
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:' + info.response;
            }
            else {
            	// 失败
                document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            }
		},

		Error: function(up, err) {
			// 上传错误
			 if (err.code == -600) {
                document.getElementById('console').appendChild(document.createTextNode("\n选择的文件太大了,可以根据应用情况，在upload.js 设置一下上传的最大大小"));
             } else if (err.code == -601) {
                document.getElementById('console').appendChild(document.createTextNode("\n选择的文件后缀不对,可以根据应用情况，在upload.js进行设置可允许的上传文件类型"));
             } else if (err.code == -602) {
                document.getElementById('console').appendChild(document.createTextNode("\n这个文件已经上传过一遍了"));
             } else {
                document.getElementById('console').appendChild(document.createTextNode("\n上传错误:" + err.message));
            }
		}
	}
});

uploader.init();
