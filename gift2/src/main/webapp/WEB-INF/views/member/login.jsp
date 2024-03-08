    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/login_style.css">
<!-- ${pageContext.request.contextPath}넣어서 가능했음. -->

<script src="resources/js/httpRequest.js"></script>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>

<script type="text/javascript">
	
	//로그인 처리 자바스크립트
	function send(f) {
		let id = f.login_id_text.value.trim();
		let pwd = f.login_pw_text.value.trim();
	
		if(id==''){
			alert("아이디를 입력해주세요");
			return;
		}
		
		if(pwd==''){
			alert("비밀번호를 입력해주세요");
			return;
		}
		
		let url = "mloginconf";
		let param = "id="+id+"&pwd="+pwd;
		//alert(param);
		sendRequest(url,param,callback,"post");
	}
	
	
	//로그인 처리 자바스크립트 함수와 연결된 콜백함수
	function callback() {
		//var data = json.parse({f});
		//alert(data);
		//encodeURIComponent
		if(xhr.readyState==4 && xhr.status==200) {
		//4: 데이터를 전부 받음, 200: 서버로부터의 응답상태가 요청에 성공
		
			let temp = xhr.responseText;
			console.log(temp);
			let eval2 = eval(temp);
			console.log(eval2);
			console.log(eval2[0]['data']);
			//alert(eval2[0]['data']);
			
			if(eval2[0]['data']=='null_data'){
				alert("아이디나 비밀번호가 일치하지 않습니다.");
			} else{
				location.href='/gift/testpage';
			}
			
			//메인화면으로 리다이렉트
			//location.href="/gift";
			//if(eval2[data]=="null_data"){
			//	alert("아이디나 비밀번호가 일치하지 않습니다."));
			//}
			
			
		}
		
		//입력한 아이디가 db에 있든 없든 쿠키로 저장해버리기
		
		//url, param, callback, method
		//url은 get은 param 추가, post는 추가 안함, 이 상태로 xhr.open()에 메서드, true/false 사이에 들이부음.
		//get은 null, post 방식 메서드면 param은 send()에 붙임
		//post면 xhr.sendRequestHeader에 Content-type(jquery ajax는 ContentType), application/x-www-form-urlencoded
		
		//xhr = new XMLHttpRequest(); -> ie쓰면 다른것도 붙여야 함
		//get:url+param
		//post:url그대로
		//send()에는 get은 null
		//post면 xhr.sendRequestHeader에 Content-type(jquery ajax는 ContentType), 
		//application/x-www-form-urlencoded
		//xhr.onreadystatechange = callBack;
	}
	
	function idsave(f){
		
		
		let saved_id = f.login_id_text.value.trim();
		let query_saved_id="[{'saved_id':"+"'"+saved_id+"'}]";
		
		
		
		//체크박스 체크 시 아이디를 세션으로 보냄
		if(document.getElementById('member_save_id_checkbox').checked == true){
			//alert("잘 입력하셨습니다");
			//alert(saved_id);
			if(saved_id==null || saved_id=='') {
				alert("아이디를 입력해 주십시오");
				//alert(query_saved_id);
			}else{
				alert("당신의 저장한 아이디는 "+saved_id);
				alert(query_saved_id);
				//여기서 시작
				console.log(query_saved_id);
				
				//ajax 안됨 지금..?
				$.ajax({
					
					//url, data, datatype, method
					
					url:"/gift/mloginidsave",
					data: query_saved_id,
					dataType:"text", // 돌려받는게 DATATYPE
					//headers:{'Content-Type':'application/json'},
					method:"POST",
					success: function(data){
						if(data){
							alert("데이터가 넘어감");
							alert(data);
						} else {
							alert("데이터가 안넘어감");
							console.log(data);
							//console.log(JSON.stringify(query_saved_id));
							console.log(eval(query_saved_id)[0]);
							console.log(eval(query_saved_id)[0]['saved_id']);
						}
					},
					error:function(data){
						alert("실패");
						alert(data);
					}
					

				});
				
				
				
				
			}
		}
		
		//체크박스 체크 시 세션, 쿠키 날림.
		if(document.getElementById('member_save_id_checkbox').checked == false) {
			alert("입력 해제");
		}
		
		/*if(saved_id==null || saved_id=='') {
			alert("아이디를 입력해 주십시오");
		}else{
			alert("당신의 저장한 아이디는 "+saved_id);
		}*/

	}
	

</script>

<style type="text/css">


</style>

</head>
<body>
    <form id = "member_login_form">
        <table id="login_table">
			<caption id="member_login_title">로그인</caption>
            <tbody>
	            <tr>
	                <td class="member_login_text">
	                    <input type="text" placeholder="아이디를 입력해주세요" name="login_id_text" class="member_login_id_pwd_text">
	                </td>
	            </tr>
	            <tr>
	                <td class="member_login_text">
						<input type="password" placeholder="비밀번호를 입력해주세요" name="login_pw_text" class="member_login_id_pwd_text">
	                </td>
	            </tr>
	           
	            <tr>
		            <td >
		            	<input type="button" value="로그인" onclick="send(this.form)"class="member_login">
		            </td>
	            </tr>
	            <tr>
	           		<td>
	             		<label id="member_id_save_text">아이디 저장</label>
	             		<input type="checkbox"  onclick="idsave(this.form)" id="member_save_id_checkbox">
	             		<label id = "member_save_id_checkbox"></label>
	             	</td>
	            </tr>
	             <tr>
	            	<td id="member_login_top_bottom">
	            		<input type="button" value="아이디 찾기" onclick="selectmidfor(this.form)" class="member_login_select_detail">
	            		<input type="button" value="비밀번호 찾기" onclick="selectmpwdfor(this.form)" class="member_login_select_detail">
	            		<input type="button" value="회원가입" onclick="location.href='/gift/mjoin'" class="member_login_select_detail">
	            	</td>
	            </tr>
            </tbody>
            	
            
            
        </table>
    </form>
</body>
</html>


    
