/**
Demo script to handle the theme demo
**/
var Demo = function () {

    // Handle Theme Settings
    var handleTheme = function () {

        var panel = $('.theme-panel');

        if ($('.page-head > .container-fluid').size() === 1) {
            $('.theme-setting-layout', panel).val("fluid");
        } else {
            $('.theme-setting-layout', panel).val("boxed");
        }

        if ($('.top-menu li.dropdown.dropdown-dark').size() > 0) {
            $('.theme-setting-top-menu-style', panel).val("dark");
        } else {
            $('.theme-setting-top-menu-style', panel).val("light");
        }

        if ($('body').hasClass("page-header-top-fixed")) {
            $('.theme-setting-top-menu-mode', panel).val("fixed");
        } else {
            $('.theme-setting-top-menu-mode', panel).val("not-fixed");
        }

        if ($('.hor-menu.hor-menu-light').size() > 0) {
            $('.theme-setting-mega-menu-style', panel).val("light");
        } else {
            $('.theme-setting-mega-menu-style', panel).val("dark");
        }

        if ($('body').hasClass("page-header-menu-fixed")) {
            $('.theme-setting-mega-menu-mode', panel).val("fixed");
        } else {
            $('.theme-setting-mega-menu-mode', panel).val("not-fixed");
        }

        //handle theme layout
        var resetLayout = function () {
            $("body").
            removeClass("page-header-top-fixed").
            removeClass("page-header-menu-fixed");

            $('.page-header-top > .container-fluid').removeClass("container-fluid").addClass('container');
            $('.page-header-menu > .container-fluid').removeClass("container-fluid").addClass('container');
            $('.page-head > .container-fluid').removeClass("container-fluid").addClass('container');
            $('.page-content > .container-fluid').removeClass("container-fluid").addClass('container');
            $('.page-prefooter > .container-fluid').removeClass("container-fluid").addClass('container');
            $('.page-footer > .container-fluid').removeClass("container-fluid").addClass('container');              
        };

        var setLayout = function () {

            var layoutMode = $('.theme-setting-layout', panel).val();
            var headerTopMenuStyle = $('.theme-setting-top-menu-style', panel).val();
            var headerTopMenuMode = $('.theme-setting-top-menu-mode', panel).val();
            var headerMegaMenuStyle = $('.theme-setting-mega-menu-style', panel).val();
            var headerMegaMenuMode = $('.theme-setting-mega-menu-mode', panel).val();
            
            resetLayout(); // reset layout to default state

            if (layoutMode === "fluid") {
                $('.page-header-top > .container').removeClass("container").addClass('container-fluid');
                $('.page-header-menu > .container').removeClass("container").addClass('container-fluid');
                $('.page-head > .container').removeClass("container").addClass('container-fluid');
                $('.page-content > .container').removeClass("container").addClass('container-fluid');
                $('.page-prefooter > .container').removeClass("container").addClass('container-fluid');
                $('.page-footer > .container').removeClass("container").addClass('container-fluid');

                //Metronic.runResizeHandlers();
            }

            if (headerTopMenuStyle === 'dark') {
                $(".top-menu > .navbar-nav > li.dropdown").addClass("dropdown-dark");
            } else {
                $(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");
            }

            if (headerTopMenuMode === 'fixed') {
                $("body").addClass("page-header-top-fixed");
            } else {
                $("body").removeClass("page-header-top-fixed");
            }

            if (headerMegaMenuStyle === 'light') {
                $(".hor-menu").addClass("hor-menu-light");
            } else {
                $(".hor-menu").removeClass("hor-menu-light");
            }

            if (headerMegaMenuMode === 'fixed') {
                $("body").addClass("page-header-menu-fixed");
            } else {
                $("body").removeClass("page-header-menu-fixed");
            }          
        };

        // handle theme colors
        var setColor = function (color) {
            var color_ = (Metronic.isRTL() ? color + '-rtl' : color);
            $('#style_color').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");
            $('.page-logo img').attr("src", Layout.getLayoutImgPath() + 'logo-' + color + '.png');
        };

        $('.theme-colors > li', panel).click(function () {
            var color = $(this).attr("data-theme");
            setColor(color);
            $('.theme-colors > li', panel).removeClass("active");
            $(this).addClass("active");
        });

        $('.theme-setting-top-menu-mode', panel).change(function(){
            var headerTopMenuMode = $('.theme-setting-top-menu-mode', panel).val();
            var headerMegaMenuMode = $('.theme-setting-mega-menu-mode', panel).val();            

            if (headerMegaMenuMode === "fixed") {
                alert("The top menu and mega menu can not be fixed at the same time.");
                $('.theme-setting-mega-menu-mode', panel).val("not-fixed");   
                headerTopMenuMode = 'not-fixed';
            }                
        });

        $('.theme-setting-mega-menu-mode', panel).change(function(){
            var headerTopMenuMode = $('.theme-setting-top-menu-mode', panel).val();
            var headerMegaMenuMode = $('.theme-setting-mega-menu-mode', panel).val();            

            if (headerTopMenuMode === "fixed") {
                alert("The top menu and mega menu can not be fixed at the same time.");
                $('.theme-setting-top-menu-mode', panel).val("not-fixed");   
                headerTopMenuMode = 'not-fixed';
            }                
        });

        $('.theme-setting', panel).change(setLayout);

        $('.theme-setting-layout', panel).change(function(){
            Index.redrawCharts();  // reload the chart on layout width change
        });
    };

    // handle theme style
    var setThemeStyle = function(style) {
        var file = (style === 'rounded' ? 'components-rounded' : 'components');
        file = (Metronic.isRTL() ? file + '-rtl' : file);

        $('#style_components').attr("href", Metronic.getGlobalCssPath() + file + ".css");

        if ($.cookie) {
            $.cookie('layout-style-option', style);
        }


    };

    return {

        //main function to initiate the theme
        init: function() {
            // handles style customer tool
            handleTheme(); 

            // handle layout style change
            $('.theme-panel .theme-setting-style').change(function() {
                 setThemeStyle($(this).val());
            });

            // set layout style from cookie
            if ($.cookie && $.cookie('layout-style-option') === 'rounded') {
                setThemeStyle($.cookie('layout-style-option'));  
                $('.theme-panel .theme-setting-style').val($.cookie('layout-style-option'));
            }            
        }
    };

}();

var projectListApp=angular.module('projectList',[]);
projectListApp.controller('showBugTab',function ($scope,$http) {
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
projectListApp.controller('projectList',function ($scope,$http) {
    var array = new Array();
    $scope.num=1;
    $http.get('/autoTest/project/projectPage/1').success(function (response) {
        $scope.projects=response.data.projectMap.projectList;
        $scope.totalPage=response.data.projectMap.totalPage;
        for(var i=0; i<$scope.totalPage;i++){
            array[i]=i+1;
        }
        $scope.pageNums=array;

    });
    $scope.projectPage=function (pageNum) {
        $scope.num=pageNum;

        $http.get('/autoTest/project/projectPage/'+pageNum).success(function (response) {
            $scope.projects=response.data.projectMap.projectList;
            $scope.totalPage=response.data.projectMap.totalPage;
            for(var i=0; i<$scope.totalPage;i++){
                array[i]=i+1;
            }
            $scope.pageNums=array;

        });
    }

    $scope.projectDetail=function (projectId) {

        location.href='/autoTest/project/editProject/'+projectId;
    }

});

var bugListApp=angular.module('bugList',[]);
bugListApp.controller('showBugTab',function ($scope,$http) {
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
bugListApp.controller('bugList',function ($scope,$http) {
    var array = new Array();
    $scope.num=1;
            $http.get('/autoTest/form/formPage/1').success(function (response) {
            var formList=response.data.formMap.formList;
            $scope.bugs=formList;
            for(var i=0;i<formList.length;i++){
                if(formList[i].priority==0){
                    formList[i].priority='一般';
                }else if(formList[i].priority==1){
                    formList[i].priority='轻微';
                }else {
                    formList[i].priority='严重';
                }
            }
            $scope.totalPage=response.data.formMap.totalPage;
            for(var i=0; i<$scope.totalPage;i++){
                array[i]=i+1;
            }
            $scope.bugPageNums=array;

        });

    $scope.bugPage=function (bugPageNum) {
        $scope.num=bugPageNum;
        $http.get('/autoTest/form/formPage/'+bugPageNum).success(function (response) {

            var formList=response.data.formMap.formList;
            $scope.bugs=formList;
            for(var i=0;i<formList.length;i++){
                if(formList[i].priority==0){
                    formList[i].priority='一般';
                }else if(formList[i].priority==1){
                    formList[i].priority='轻微';
                }else {
                    formList[i].priority='严重';
                }
            }
            $scope.totalPage=response.data.formMap.totalPage;
            for(var i=0; i<$scope.totalPage;i++){
                array[i]=i+1;
            }
            $scope.bugPageNums=array;

        });
    }
    $scope.bugDetail=function (bugId) {

        location.href='/autoTest/form/bugEdit/'+bugId;
        
    }


});

var homeApp=angular.module('indexApp',[]);
homeApp.controller('showBugTab',function ($scope,$http) {
    //查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId=response.data.roleId;
        if(roleId==3){
            $scope.bugCreate=true;
        }else {
            $scope.bugCreate=false;

        }
    });
    //注销
    $scope.logout=function () {
        $http({
            url: '/logout',
            method : 'POST'
        }).success(function (response) {
            alert(response.data.msg);
           // location.href='/login';
            location.reload();
        }).error(function () {
            alert("注销失败");
        });
    }
});
homeApp.controller('taskController',function ($scope,$http) {
    var array = new Array();
    $scope.num = 1;

    $http.get('/autoTest/form/myFormTask/1').success(function (response) {
        var formList = response.data.formMap.formList;
        $scope.tasks = formList;
        for (var i = 0; i < formList.length; i++) {
            if (formList[i].status == 0) {
                formList[i].status = '待修复';
            } else if (formList[i].status == 1) {
                formList[i].status = '待回归';
            } else if (formList[i].status == 2) {
                formList[i].status = '回归完成';
            } else if (formList[i].status == 3) {
                formList[i].status = '待修复';
            } else {
                formList[i].status = '关闭';
            }
        }
        $scope.totalPage = response.data.formMap.totalPage;
        for (var i = 0; i < $scope.totalPage; i++) {
            array[i] = i + 1;
        }
        $scope.pageNums = array;
    });

    $scope.taskPage = function (pageNum) {
        $scope.num = pageNum;
        $http.get('/autoTest/form/myFormTask/' + pageNum).success(function (response) {
            var formList = response.data.formMap.formList;
            $scope.tasks = formList;
            for (var i = 0; i < formList.length; i++) {
                if (formList[i].status == 0) {
                    formList[i].status = '待修复';
                } else if (formList[i].status == 1) {
                    formList[i].status = '待回归';
                } else if (formList[i].status == 2) {
                    formList[i].status = '回归完成';
                } else if (formList[i].status == 3) {
                    formList[i].status = '待修复';
                } else {
                    formList[i].status = '关闭';
                }
            }
            $scope.totalPage = response.data.formMap.totalPage;
            for (var i = 0; i < $scope.totalPage; i++) {
                array[i] = i + 1;
            }
            $scope.pageNums = array;
        });
    }
});

var roleEditApp=angular.module('roleEdit',[]);
roleEditApp.controller('showBugTab',function ($http,$scope) {
//查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId = response.data.roleId;
        if (roleId == 3) {
            $scope.bugCreate = true;
        } else {
            $scope.bugCreate = false;
        }
    })
});

roleEditApp.controller('roleEdit', function ($scope, $http) {
//获取全部的角色列表
    $http.get('/autoTest/role/roleNameList').success(function (response) {
        $scope.roleNames=response.data.roleNameList;
    });
//获取全部用户列表
    $http.get('/getAllUsers').success(function (response) {
        $scope.usernames=response.data.userList;
    });

    $scope.incompleteU=true;
    $scope.incompleteR=true;
    $scope.send=false;

    $scope.test=function () {
        if($scope.username){
            $scope.incompleteU=false;
        }
        if($scope.roleName){
            $scope.incompleteR=false;
        }
    }

    $scope.$watch('username',function () {
        $scope.test();
    })
    $scope.$watch('roleName',function () {
        $scope.test();
    })

    $scope.saveRole=function () {
        $http({
            url:'/autoTest/role/updateRoleName',
            method:'POST',
            data:{roleName:$scope.roleName,username:$scope.username}
        }).success(function (response) {
            alert(response.data.msg);
        });
    }

});

var roleListApp=angular.module('roleList',[]);
roleListApp.controller('showBugTab',function ($scope,$http) {
    //查询当前用户的角色
    $http.get('/queryRoleId').success(function (response) {
        var roleId = response.data.roleId;
        if (roleId == 3) {
            $scope.bugCreate = true;
        } else {
            $scope.bugCreate = false;
        }
    })
});

roleListApp.controller('roleList',function ($scope,$http) {
    var totalPage=0;
    $scope.num=1;
    var pageArray=[];
    $http.get('/autoTest/role/rolePage/1').success(function (response) {
        $scope.users=response.data.rolePageList;
        totalPage=response.data.totalPage;
        console.log("totalPage:"+totalPage);
        for(var i=0;i<totalPage;i++){
            pageArray[i]=i+1;
        }
        $scope.userPageNums=pageArray;

    });
    $scope.userPage=function (userPageNum) {
        $http.get('/autoTest/role/rolePage/'+userPageNum).success(function (response) {
            $scope.users=response.data.rolePageList;
            totalPage=response.data.totalPage;
            for(var i=0;i<totalPage;i++){
                pageArray[i]=i+1;
                $scope.num=userPageNum;
            }
            $scope.userPageNums=pageArray;

        });
    }
});


