/**
Core script to handle the entire theme and core functions
**/
var Layout = function () {

    var layoutImgPath = 'admin/layout3/img/';

    var layoutCssPath = 'admin/layout3/css/';

    var resBreakpointMd = Metronic.getResponsiveBreakpoint('md');

    //* BEGIN:CORE HANDLERS *//
    // this function handles responsive layout on screen size resize or mobile device rotate.

    // Handles header
    var handleHeader = function () {        
        // handle search box expand/collapse        
        $('.page-header').on('click', '.search-form', function (e) {
            $(this).addClass("open");
            $(this).find('.form-control').focus();

            $('.page-header .search-form .form-control').on('blur', function (e) {
                $(this).closest('.search-form').removeClass("open");
                $(this).unbind("blur");
            });
        });

        // handle hor menu search form on enter press
        $('.page-header').on('keypress', '.hor-menu .search-form .form-control', function (e) {
            if (e.which == 13) {
                $(this).closest('.search-form').submit();
                return false;
            }
        });

        // handle header search button click
        $('.page-header').on('mousedown', '.search-form.open .submit', function (e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).closest('.search-form').submit();
        });

        // handle scrolling to top on responsive menu toggler click when header is fixed for mobile view
        $('body').on('click', '.page-header-top-fixed .page-header-top .menu-toggler', function(){
            Metronic.scrollTop();
        });     
    };

    // Handles main menu
    var handleMainMenu = function () {

        // handle menu toggler icon click
        $(".page-header .menu-toggler").on("click", function(event) {
            if (Metronic.getViewPort().width < resBreakpointMd) {
                var menu = $(".page-header .page-header-menu");
                if (menu.is(":visible")) {
                    menu.slideUp(300);
                } else {  
                    menu.slideDown(300);
                }

                if ($('body').hasClass('page-header-top-fixed')) {
                    Metronic.scrollTop();
                }
            }
        });

        // handle sub dropdown menu click for mobile devices only
        $(".hor-menu .dropdown-submenu > a").on("click", function(e) {
            if (Metronic.getViewPort().width < resBreakpointMd) {
                if ($(this).next().hasClass('dropdown-menu')) {
                    e.stopPropagation();
                    if ($(this).parent().hasClass("open")) {
                        $(this).parent().removeClass("open");
                        $(this).next().hide();
                    } else {
                        $(this).parent().addClass("open");
                        $(this).next().show();
                    }
                }
            }
        });

        // handle hover dropdown menu for desktop devices only
        if (Metronic.getViewPort().width >= resBreakpointMd) {
            $('.hor-menu [data-hover="megamenu-dropdown"]').not('.hover-initialized').each(function() {   
                $(this).dropdownHover(); 
                $(this).addClass('hover-initialized'); 
            });
        } 

        // handle auto scroll to selected sub menu node on mobile devices
        $(document).on('click', '.hor-menu .menu-dropdown > a[data-hover="megamenu-dropdown"]', function() {
            if (Metronic.getViewPort().width < resBreakpointMd) {
                Metronic.scrollTo($(this));
            }
        });

        // hold mega menu content open on click/tap. 
        $(document).on('click', '.mega-menu-dropdown .dropdown-menu, .classic-menu-dropdown .dropdown-menu', function (e) {
            e.stopPropagation();
        });

        // handle fixed mega menu(minimized) 
        $(window).scroll(function() {                
            var offset = 75;
            if ($('body').hasClass('page-header-menu-fixed')) {
                if ($(window).scrollTop() > offset){
                    $(".page-header-menu").addClass("fixed");
                } else {
                    $(".page-header-menu").removeClass("fixed");  
                }
            }

            if ($('body').hasClass('page-header-top-fixed')) {
                if ($(window).scrollTop() > offset){
                    $(".page-header-top").addClass("fixed");
                } else {
                    $(".page-header-top").removeClass("fixed");  
                }
            }
        });
    };

    // Handle sidebar menu links
    var handleMainMenuActiveLink = function(mode, el) {
        var url = location.hash.toLowerCase();    

        var menu = $('.hor-menu');

        if (mode === 'click' || mode === 'set') {
            el = $(el);
        } else if (mode === 'match') {
            menu.find("li > a").each(function() {
                var path = $(this).attr("href").toLowerCase();       
                // url match condition         
                if (path.length > 1 && url.substr(1, path.length - 1) == path.substr(1)) {
                    el = $(this);
                    return; 
                }
            });
        }

        if (!el || el.size() == 0) {
            return;
        }

        if (el.attr('href').toLowerCase() === 'javascript:;' || el.attr('href').toLowerCase() === '#') {
            return;
        }        

        // disable active states
        menu.find('li.active').removeClass('active');
        menu.find('li > a > .selected').remove();
        menu.find('li.open').removeClass('open');

        el.parents('li').each(function () {
            $(this).addClass('active');

            if ($(this).parent('ul.navbar-nav').size() === 1) {
                $(this).find('> a').append('<span class="selected"></span>');
            }
        });
    };

    // Handles main menu on window resize
    var handleMainMenuOnResize = function() {
        // handle hover dropdown menu for desktop devices only
        var width = Metronic.getViewPort().width;
        var menu = $(".page-header-menu");
            
        if (width >= resBreakpointMd && menu.data('breakpoint') !== 'desktop') { 
            // reset active states
            $('.hor-menu [data-toggle="dropdown"].active').removeClass('open');

            menu.data('breakpoint', 'desktop');
            $('.hor-menu [data-hover="megamenu-dropdown"]').not('.hover-initialized').each(function() {   
                $(this).dropdownHover(); 
                $(this).addClass('hover-initialized'); 
            });
            $('.hor-menu .navbar-nav li.open').removeClass('open');
            $(".page-header-menu").css("display", "block");
        } else if (width < resBreakpointMd && menu.data('breakpoint') !== 'mobile') {
            // set active states as open
            $('.hor-menu [data-toggle="dropdown"].active').addClass('open');
            
            menu.data('breakpoint', 'mobile');
            // disable hover bootstrap dropdowns plugin
            $('.hor-menu [data-hover="megamenu-dropdown"].hover-initialized').each(function() {   
                $(this).unbind('hover');
                $(this).parent().unbind('hover').find('.dropdown-submenu').each(function() {
                    $(this).unbind('hover');
                });
                $(this).removeClass('hover-initialized');    
            });
        } else if (width < resBreakpointMd) {
            //$(".page-header-menu").css("display", "none");  
        }
    };

    var handleContentHeight = function() {
        var height;

        if ($('body').height() < Metronic.getViewPort().height) {            
            height = Metronic.getViewPort().height -
                $('.page-header').outerHeight() - 
                ($('.page-container').outerHeight() - $('.page-content').outerHeight()) -
                $('.page-prefooter').outerHeight() - 
                $('.page-footer').outerHeight();

            $('.page-content').css('min-height', height);
        }
    };

    // Handles the go to top button at the footer
    var handleGoTop = function () {
        var offset = 100;
        var duration = 500;

        if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) {  // ios supported
            $(window).bind("touchend touchcancel touchleave", function(e){
               if ($(this).scrollTop() > offset) {
                    $('.scroll-to-top').fadeIn(duration);
                } else {
                    $('.scroll-to-top').fadeOut(duration);
                }
            });
        } else {  // general 
            $(window).scroll(function() {
                if ($(this).scrollTop() > offset) {
                    $('.scroll-to-top').fadeIn(duration);
                } else {
                    $('.scroll-to-top').fadeOut(duration);
                }
            });
        }
        
        $('.scroll-to-top').click(function(e) {
            e.preventDefault();
            $('html, body').animate({scrollTop: 0}, duration);
            return false;
        });
    };

    //* END:CORE HANDLERS *//

    return {
        
        // Main init methods to initialize the layout
        // IMPORTANT!!!: Do not modify the core handlers call order.

        initHeader: function() {
            handleHeader(); // handles horizontal menu    
            handleMainMenu(); // handles menu toggle for mobile
            Metronic.addResizeHandler(handleMainMenuOnResize); // handle main menu on window resize

            if (Metronic.isAngularJsApp()) {      
                handleMainMenuActiveLink('match'); // init sidebar active links 
            }
        },

        initContent: function() {
            handleContentHeight(); // handles content height 
        },

        initFooter: function() {
            handleGoTop(); //handles scroll to top functionality in the footer
        },

        init: function () {            
            this.initHeader();
            this.initContent();
            this.initFooter();
        },

        setMainMenuActiveLink: function(mode, el) {
            handleMainMenuActiveLink(mode, el);
        },

        closeMainMenu: function() {
            $('.hor-menu').find('li.open').removeClass('open');

            if (Metronic.getViewPort().width < resBreakpointMd && $('.page-header-menu').is(":visible")) { // close the menu on mobile view while laoding a page 
                $('.page-header .menu-toggler').click();
            }
        },

        getLayoutImgPath: function() {
            return Metronic.getAssetsPath() + layoutImgPath;
        },

        getLayoutCssPath: function() {
            return Metronic.getAssetsPath() + layoutCssPath;
        }
    };

}();


/*
var flag = true;
com.company.project.services.newCase.statusCheck = function(){
    if(flag == false){
        var status = hidden_frame.window.document.getElementById("hideForm").innerHTML;
        console.log(status);
    }
    flag = false;
};

//上传文件
com.company.project.services.newCase.fileUpLoad = function(){
    $("#needHide").submit();
}*/

if (!window.com) {
    window.com = {};
}
if (!window.com.company) {
    window.com.company= {};
}
if (!window.com.company.project) {
    window.com.company.project= {};
}
if (!window.com.company.project.services) {
    window.com.company.project.services = {};
}
if (!window.com.company.project.services.newCase) {
    window.com.company.project.services.newCase = {};
}

//生成随机guid数
com.company.project.services.newCase.getGuidGenerator = function() {
    var S4 = function() {
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    };
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
};

//上传的文件
var filePathArray=new Array();
//查询到的文件
var filePathList=null;
var id=0;
var projectId=0;
//上传文件
com.company.project.services.newCase.fileUpLoad = function(){

    var fileName = $("#btnFile").val();//文件名
    fileName = fileName.split("\\");
    fileName = fileName[fileName.length-1];

    if(fileName.length>30){
        $("#attachmemtMsg").html("文件长度不能超过30");
        return;
    }
    if($("#fileDelete div").length>=5){
        $("#attachmemtMsg").html("上传文件个数不能超过5个");
        return;
    }
    var guid = com.company.project.services.newCase.getGuidGenerator();//唯一标识guid
    var data = {guid:guid};
  //  jQuery.ajaxSettings.traditional = true;
    $.ajaxFileUpload({
        url : '/autoTest/file/upload/'+id,
 //       secureuri : false,//安全协议
        fileElementId:'btnFile',//id
        type : 'POST',
        data : data,
        dataType : 'JSON',
 //       async : false,
        success : function(response) {
            var reg = /<pre.+?>(.+)<\/pre>/g;
            var result = response.match(reg);
            response = JSON.parse(RegExp.$1);
          //  console.log(result);
            if (!response.success){
                $("#attachmemtMsg").html(response.data.msg);
            }else{
                $("#attachmemtMsg").html(response.data.msg);
                filePathArray.push(response.data.filePath);
                console.log("filePathArray:"+filePathArray);
              //  var next = $("#fileUpLoad").html();

                $("#fileDelete").append("<div style='float:left;' id='"+guid+"'>"+"文件:"+fileName+"   <a href='javascript:;' onclick='com.company.project.services.newCase.filedelete("+"\""+guid+"\""+","+"\""+fileName+"\""+")'>删除</a>"+"<br/></div>");
             //   $("#fileUpLoad").append(next);

            }
        },
        error : function(response) {
            var reg = /<pre.+?>(.+)<\/pre>/g;
            var result = response.match(reg);
            response = JSON.parse(RegExp.$1);
            alert(response.data.msg);
        }
    });
};

//文件删除
com.company.project.services.newCase.filedelete = function(guid,fileName){
    jQuery.ajaxSettings.tradition = true;
    console.log("文件删除guid:"+guid);
    var data = {guid:guid,fileName:fileName};
    $.ajax({
        url : '/autoTest/file/delete/'+id,
        type : 'POST',
        data:data,
        success : function(response) {
           /* var reg = /<pre.+?>(.+)<\/pre>/g;
            var result = response.match(reg);
            response = JSON.parse(RegExp.$1);*/
            if (!response.success){
                $("#attachmemtMsg").html(response.data.msg);
            }else{
                $("#attachmemtMsg").html(response.data.msg);
                for(var i = 0;i<filePathArray.length;i++){
                    console.log(filePathArray[i]);
                    if (response.data.filePath == filePathArray[i]){
                        filePathArray.splice(i,1);
                    }
                }
                console.log(guid);
                $("#"+guid).remove();
            }
        },
        error : function(response) {
            /*var reg = /<pre.+?>(.+)<\/pre>/g;
            var result = response.match(reg);
            response = JSON.parse(RegExp.$1);*/
            $("#attachmemtMsg").html(response.data.msg);
        }
    });
};


var bugFormApp = angular.module('bugForm',[]);
bugFormApp.controller('showBugTab',function ($scope, $http) {
//查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId=response.data.roleId;
        if(roleId==3){
            $scope.bugCreate=true;
        }else {
            $scope.bugCreate=false;

        }
    });
});
bugFormApp.controller('bugForm',function ($scope,$http) {

//查询项目别名
    $http.get('/autoTest/project/queryProject').success(function (response) {
        $scope.aliases=response.data.aliasList;
    });

   $scope.priorities=['一般','轻微','严重'];
//分配处理人逻辑处理
    $http.get('/getAllUsers').success(function (response) {
        $scope.assigners=response.data.userList;
    })

//判断标题、优先级、分配人必填
    $scope.incompleteT=true;
    $scope.incompleteP=true;
    $scope.incompleteA=true;
    $scope.incompleteAL=true;
    $scope.send=false;

    $scope.test=function () {
        if($scope.alias){
            $scope.incompleteAL=false;
        }else {
            $scope.incompleteAL=true;
        }
        if($scope.title){
            $scope.incompleteT=false;
        }else{
            $scope.incompleteT=true;
        }
        if($scope.priority){
            $scope.incompleteP=false;
        }else{
            $scope.incompleteP=true;
        }
        if($scope.assigner){
            $scope.incompleteA=false;
        }else{
            $scope.incompleteA=true;
        }
    }

    $scope.$watch('alias',function () {
        $scope.test();
    });
    $scope.$watch('title',function () {
        $scope.test();
    });
    $scope.$watch('priority',function () {
        $scope.test();
    });
    $scope.$watch('assigner',function () {
        $scope.test();
    });




//创建表单
    $scope.createBugForm=function () {
        var title=$scope.title;
        var description=$scope.description;
        var priority=$scope.priority;
        var assigner=$scope.assigner;
        var alias=$scope.alias;

        var filePathArrayStr=filePathArray.toString();
        console.log("after:"+filePathArray);
        $http({
            url: '/autoTest/form/create',
            method: 'POST',
            data: {alias:alias,title: title, description: description, priority: priority, assigner: assigner, filePath:filePathArrayStr}
        }).success(function (response) {
            $scope.send=true;
           // alert(response.data.msg);
            location.href='/autoTest/form/bugEdit/'+ response.data.id;
            $http({
                url:'/autoTest/email/sendEmail',
                method:'POST',
                data:{emailTo:assigner,subject:title,content:'<span>创建bug</span><a href="http://192.168.40.12:8080/autoTest/form/bugEdit/'+response.data.id+'">bug链接</a>'}
            }).success(function (response) {
                alert(response.data.msg);
            }).error(function (response) {
                alert(response.data.msg);
            });
        }).error(function (response) {
            $scope.send=true;
            alert(response.data.msg);
        });
        $scope.send=false;
    }
});

var projectFormApp=angular.module('projectForm',[]);
projectFormApp.controller('showBugTab',function ($scope, $http) {
//查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId=response.data.roleId;
        if(roleId==3){
            $scope.bugCreate=true;
        }else {
            $scope.bugCreate=false;

        }
    });
});
projectFormApp.controller('createProject',function ($scope,$http) {

    $scope.incompleteN=true;
    $scope.incompleteA=true;
    $scope.send=false;
    $scope.comment='';
    $scope.test=function () {
        if($scope.name){
            $scope.incompleteN=false;
        }else {
            $scope.incompleteN=true;
        }
        if($scope.alias){
            $scope.incompleteA=false;
        }else {
            $scope.incompleteA=true;
        }
        
    }

    $scope.$watch('name',function () {
        $scope.test();
    });
    $scope.$watch('alias',function(){
        $scope.test()
    });
    
    $scope.createProjectForm=function () {
        var name=$scope.name;
        var alias=$scope.alias;
        var comment=$scope.comment;
        $http({
            url: '/autoTest/project/createProject',
            method: 'POST',
            data:{name:name,alias:alias,comment:comment}
        })
            .success(function (response) {
             //   alert(response.data.msg);
                projectId=response.data.id;
                location.href='/autoTest/project/editProject/'+projectId;
            })
            .error(function (response) {
                alert(response.data.msg);
            });
        
    }
});
//项目编辑页面
var editProjectFormApp=angular.module('editProjectForm',[]);
editProjectFormApp.controller('showBugTab',function ($scope, $http) {
//查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId=response.data.roleId;
        if(roleId==3){
            $scope.bugCreate=true;
        }else {
            $scope.bugCreate=false;

        }
    });
});
editProjectFormApp.controller('editProject',function ($scope,$http) {

    projectId=window.location.pathname.split("/")[4];
    $scope.incompleteN=true;
    $scope.incompleteA=true;
    $scope.send=false;
    $scope.comment='';
    $scope.test=function () {
        if($scope.name){
            $scope.incompleteN=false;
        }else {
            $scope.incompleteN=true;
        }
        if($scope.alias){
            $scope.incompleteA=false;
        }else {
            $scope.incompleteA=true;
        }

    }

    $scope.$watch('name',function () {
        $scope.test();
    });
    $scope.$watch('alias',function(){
        $scope.test()
    });

    $http.get('/autoTest/project/queryProject/'+projectId).success(function (response) {
        $scope.name=response.data.project.name;
        $scope.alias=response.data.project.alias;
        $scope.comment=response.data.project.comment;
    });

    $scope.updateProject=function () {
        var name=$scope.name;
        var alias=$scope.alias;
        var comment=$scope.comment;
        $http({
            url:'/autoTest/project/updateProject/'+projectId,
            method:'POST',
            data:{name:name,alias:alias,comment:comment}
            })
            .success(function (response) {
                alert(response.data.msg);
            })
            .error(function (response) {
                alert(response.data.msg);
            });
        
    }


});

//bug编辑页面
        var bugEditApp=angular.module('bugFormEdit',[]);
bugEditApp.controller('showBugTab',function ($scope, $http) {
//查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId=response.data.roleId;
        if(roleId==3){
            $scope.bugCreate=true;
        }else {
            $scope.bugCreate=false;
        }
    });
});
bugEditApp.controller('bugFormEdit',function ($scope,$http) {

    //bugId
    id=location.pathname.split("/")[4];
    var filePathArrayStr=null;
    $scope.priorities=['一般','轻微','严重'];

    //查询项目别名                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    $http.get('/autoTest/project/queryProject').success(function (response) {                                                                                                   
        $scope.aliases=response.data.aliasList;
        //分配处理人逻辑处理
        $http.get('/getAllUsers').success(function (response) {
            $scope.assigners=response.data.userList;
            //获取某个bug单的信息
            $http.get('/autoTest/form/bugEditJson/'+id).success(function (response) {
                $scope.alias=response.data.form.project.alias;
                $scope.title=response.data.form.title;
                $scope.priority=response.data.form.priority;
                $scope.assigner=response.data.form.assigner;
                $scope.description=response.data.form.description;
                $scope.status=response.data.form.status;
                var roleId=response.data.roleId;
                console.log("roleId:"+roleId);
                console.log("表单状态："+$scope.status);
                if(($scope.status==0 || $scope.status==3) && roleId==2){
                    $scope.toTester=true;
                    $scope.tester=false;
                }else if($scope.status==2 && roleId==3){
                    $scope.close=true;
                    $scope.toTester=true;
                    $scope.tester=false;
                }else if($scope.status==1 && roleId==3){
                    $scope.finishTest=true;
                    $scope.refix=true;
                    $scope.tester=true;
                }else if($scope.status==1 && roleId==2){
                    $scope.tester=false;
                }else if($scope.status==4){
                    $scope.tester=false;
                }
                filePathArray =response.data.form.attachment;
/*
                console.log("查询到的附件结果："+filePathArray);
*/
                filePathArrayStr = filePathArray.toString();
                if(filePathArrayStr==""  || filePathArrayStr==null){
                    return;
                }
                var filePathArray2 = filePathArrayStr.split(",")

                for(var i=0; i<filePathArray2.length; i++){
                    var fileList = filePathArray2[i].split(".");
                    var fileName = fileList[1]+"."+fileList[2];
                    var guidList = fileList[0].split("//");
                    var guid = guidList[2];
                    $("#fileDelete").append("<div style='float:left;' id='"+guid+"'>"+"文件:"+fileName+"   <a href='javascript:;' onclick='com.company.project.services.newCase.filedelete("+"\""+guid+"\""+","+"\""+fileName+"\""+")'>删除</a>"+"<br/></div>");

                }

            });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        });
    });

    $scope.updateBugForm=function () {
     /*   console.log($scope.alias);
        console.log($scope.title);*/
        var filePathArrayUpdate=null;
      /*  filePathArray==""||null ?filePathArrayUpdate=filePathList : filePathArrayUpdate=filePathArray+","+filePathList;
        console.log("更新后的文件："+filePathArrayUpdate);
        console.log("增加的文件："+filePathArray);
        console.log("上传的文件："+filePathArrayStr);*/
/*
        console.log("修改后上传的文件："+filePathArrayStr);
*/
        $http({
            url:'/autoTest/form/updateBugForm',
            method:'POST',
            data:{alias:$scope.alias,title:$scope.title,priority:$scope.priority,
                assigner:$scope.assigner,description:$scope.description,id:id}
        }).success(function (response) {
            $http({
                url:'/autoTest/email/sendEmail',
                method:'POST',
                data:{emailTo:$scope.assigner,subject:$scope.title,content:'<span>更新bug</span><a href="http://192.168.40.12:8080/autoTest/form/bugEdit/'+id+'">bug链接</a>'}
            });
//,attachment:filePathArrayStr
            alert(response.data.msg);

        });
    }
    $scope.fixedForm=function () {
        $http({
            url:'/autoTest/form/fixedForm',
            method:'POST',
            data:{id:id}
        }).success(function (response) {

            $http({
                url:'/autoTest/email/sendEmail',
                method:'POST',
                data:{emailTo:response.data.creator,subject:$scope.title,content:'<span>修复完成</span><a href="http://192.168.40.12:8080/autoTest/form/bugEdit/'+id+'">bug链接</a>'}
            });
            alert(response.data.msg);

            location.reload();
        });
    }
    $scope.regressForm=function () {

            $http({
                url:'/autoTest/form/regressForm',
                method:'POST',
                data:{id:id}
            }).success(function (response) {
                alert(response.data.msg);
                location.reload();
        });
    }
    $scope.refixForm=function () {
        $http({
            url:'/autoTest/form/refixForm',
            method:'POST',
            data:{id:id}
        }).success(function (response) {
            $http({
                url:'/autoTest/email/sendEmail',
                method:'POST',
                data:{emailTo:$scope.assigner,subject:$scope.title,content:'<span>重新修复</span><a href="http://192.168.40.12:8080/autoTest/form/bugEdit/'+id+'">bug链接</a>'}
            });
            alert(response.data.msg);
            location.reload();
        });
    }
    $scope.closeForm=function () {
        $http({
            url:'/autoTest/form/closeForm',
            method:'POST',
            data:{id:id}
        }).success(function (response) {
            alert(response.data.msg);
            location.reload();
        });
    }


    //判断标题、优先级、分配人必填
    $scope.incompleteT=true;
    $scope.incompleteP=true;
    $scope.incompleteA=true;
    $scope.incompleteAL=true;
    $scope.send=false;

    $scope.test=function () {
        if($scope.alias){
            $scope.incompleteAL=false;
        }else {
            $scope.incompleteAL=true;
        }
        if($scope.title){
            $scope.incompleteT=false;
        }else{
            $scope.incompleteT=true;
        }
        if($scope.priority){
            $scope.incompleteP=false;
        }else{
            $scope.incompleteP=true;
        }
        if($scope.assigner){
            $scope.incompleteA=false;
        }else{
            $scope.incompleteA=true;
        }
    }

    $scope.$watch('alias',function () {
        $scope.test();
    });
    $scope.$watch('title',function () {
        $scope.test();
    });
    $scope.$watch('priority',function () {
        $scope.test();
    });
    $scope.$watch('assigner',function () {
        $scope.test();
    });



});
