var Login = function () {

	var handleLogin = function() {
		$('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
/*	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }*/
	            },

	            /*messages: {
	                username: {
	                    required: "用户名必填."
	                },
	                password: {
	                    required: "密码必填."
	                }
	            },*/

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-danger', $('.login-form')).show();
	            },

	       /*     highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },*/

	            /*success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },
*/
	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

/*	            submitHandler: function (form) {
	                form.submit();
	            }*/
	        });

/*	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                    $('.login-form').submit();
	                }
	                return false;
	            }
	        });*/
	}

	var handleForgetPassword = function () {
		$('.forget-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            /*rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "Email is required."
	                }
	            },*/

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            /*highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },*/

	            /*success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },
*/
	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    $('.forget-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	}

	var handleRegister = function () {

		function format(state) {
            if (!state.id) return state.text; // optgroup
            return "<img class='flag' src='/static/assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
        }


		$("#select2_sample4").select2({
		  	placeholder: '<i class="fa fa-map-marker"></i>&nbsp;Select a Country',
            allowClear: true,
            formatResult: format,
            formatSelection: format,
            escapeMarkup: function (m) {
                return m;
            }
        });


			$('#select2_sample4').change(function () {
                $('.register-form').validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
            });



         $('.register-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	          /*  rules: {
	                
	                fullname: {
	                    required: true
	                },
	                email: {
	                    required: true,
	                    email: true
	                },
	                address: {
	                    required: true
	                },
	                city: {
	                    required: true
	                },
	                country: {
	                    required: true
	                },

	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                rpassword: {
	                    equalTo: "#register_password"
	                },

	                tnc: {
	                    required: true
	                }
	            },

	            messages: { // custom messages for radio buttons and checkboxes
	                tnc: {
	                    required: "Please accept TNC first."
	                },
					username: {
						required: "用户名必填."
					},
					password: {
						required: "密码必填."
					}
	            },*/

	            invalidHandler: function (event, validator) { //display error alert on form submit
				},

	            /*highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },*/

	            /*success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },*/

	            errorPlacement: function (error, element) {
	                if (element.attr("name") == "tnc") { // insert checkbox errors after the container
	                    error.insertAfter($('#register_tnc_error'));
	                } else if (element.closest('.input-icon').size() === 1) {
	                    error.insertAfter(element.closest('.input-icon'));
	                } else {
	                	error.insertAfter(element);
	                }
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

			$('.register-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.register-form').validate().form()) {
	                    $('.register-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#register-btn').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	        });

	        jQuery('#register-back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        });
	}

    return {
        //main function to initiate the module
        init: function () {

            handleLogin();
            handleForgetPassword();
            handleRegister();
        }

    };

}();


var loginApp = angular.module('login',[]);

loginApp.controller('login',function ($scope, $http) {
//判断用户名和密码不能为空
	$scope.incompleteU=true;
	$scope.incompleteP=true;
	$scope.checkUser=true;
	$scope.send=false;
	$scope.test=function () {
		var reg="^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";

		if($scope.username){
			$scope.incompleteU=false;
		}else {
			$scope.incompleteU=true;
		}
		if($scope.password){
			$scope.incompleteP=false;
		}else{
			$scope.incompleteP=true;
		}
		console.log("用户名:"+$scope.username);

		if($scope.username.match(reg)){
			$scope.checkUser=false;
		}else {
			$scope.checkUser=true;
		}
	}
	$scope.$watch('username',function () {
		$scope.test();
	});
	$scope.$watch('password',function () {
		$scope.test();
	});

//登录
    $scope.login=function () {
        var username = $scope.username;
        var password = $scope.password;
        $http({
            url : '/login',
            method : 'POST',
            data:{username:username,password:password},
            dataType:'json'
        }).success(function (response) {
            $scope.msg = response.msg;
			$scope.send=false;

			if(response.success){
				location.href='/autoTest/index';
			}else {
				$('.alert-danger', $('.login-form')).show();
			}

		}).error(function (response) {
            $scope.msg = response.msg;
			$scope.send=false;

			$('.alert-danger', $('.login-form')).show();

		});

		$scope.send=true;

	}
    
});

loginApp.controller('reg',function ($scope , $http) {

//判断用户名和密码不能为空
	$scope.incompleteU=true;
	$scope.incompleteP=true;
	$scope.checkUser=true;
	$scope.send=false;
	$scope.test=function () {
		var reg="^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";

		if($scope.username){
			$scope.incompleteU=false;
		}else {
			$scope.incompleteU=true;
		}
		if($scope.password){
			$scope.incompleteP=false;
		}else{
			$scope.incompleteP=true;
		}
		console.log("注册用户名:"+$scope.username);

		if($scope.username.match(reg)){

			$scope.checkUser=false;
		}else {
			$scope.checkUser=true;
		}

	}
	$scope.$watch('username',function () {
		$scope.test();
	});
	$scope.$watch('password',function () {
		$scope.test();
	});

//注册
	$scope.reg=function () {
		var username = $scope.username;
		var password = $scope.password;
        $http({
			url: '/reg',
            method : 'POST',
            data:{username:username,password:password},
			dataType:'json'
        }).success(function (response) {
			$scope.msg = response.msg;
			$scope.send=true;

			if(response.success){
				jQuery('.login-form').show();
				jQuery('.forget-form').hide();
				jQuery('.register-form').hide();
			}
			else {
				$('.alert-danger', $('.register-form')).show();
			}

        }).error(function(response){
			$scope.msg = response.msg;
			$scope.send=true;

			$('.alert-danger', $('.register-form')).show();
		});
		$scope.send=false;

	}
});
