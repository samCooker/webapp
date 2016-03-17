
var app = angular.module('start',['ui.bootstrap']);

app.controller('loginController',function($scope,$http){
	$scope.loginData={username:'cookie',password:'1'};
	//登陆
	$scope.login=function(){
		console.log($scope.loginData);
		$http.post('./login_check',$scope.loginData).success(function(data){
			if(data=="true"){
				
			}else{
				$scope.loginData.error='server error: '+data;
			}
		}).error(function(error){
			$scope.loginData.error='server error: '+error;
		});
	};
	
})

;