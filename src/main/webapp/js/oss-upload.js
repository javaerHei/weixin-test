
var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    multi_selection: false,
	container: document.getElementById('container'),
	flash_swf_url : 'js/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : 'js/plupload-2.1.2/js/Moxie.xap',
    url : '/oss/upload',
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
				uploader.start();
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
			 if (err.code == -600) {
               alert("选择的视频文件太大了");
            } else if (err.code == -601) {
               alert("选择的文件格式不正确");
            } else if (err.code == -602) {
               alert("这个文件已经上传过一遍了");
            } else {
              alert("上传错误:"+err.message);
           }
		}
	}
});

uploader.init();
