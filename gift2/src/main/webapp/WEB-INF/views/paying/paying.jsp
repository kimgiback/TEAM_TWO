<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/pay/paying.css">

<script src="${pageContext.request.contextPath}/resources/js/httpRequest.js"></script>
<script type="text/javascript">

function card(paymentType) {
    var payment = paymentType;
    var item_no = document.querySelector('input[name="item_no"]').value;
    
    let url = "card";
    
    let param = "payment=" + payment +
                "&item_no=" + item_no;
    
    sendRequest(url, param, CardCheck, "post");
    
    
}

function CardCheck() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        let data = xhr.responseText;
        let json = eval(data); // JSON 형식의 응답 데이터를 JavaScript 배열로 변환
           
        // 서버로부터 받은 데이터를 확인하여 팝업 창을 닫거나 다른 동작을 수행합니다.
        if (json[0].result == "success") {
            // 서버에서 처리가 성공했을 경우
            alert("결제수단이 변경되었습니다.");
            //클릭한 값 결제수단에 반영
            var paymentValue =  document.querySelector('input[name="payment"]').value; // 서버에서 반환된 결제수단 값
            document.querySelector('.active').innerText = paymentValue;
            console.log(paymentValue);
            location.href="payitem&item_no=${itemDTO.item_no}";
            
          
        } else if (json[0].result == "fail") {
            // 서버에서 처리가 실패했을 경우
        	alert("결제수단 변경에 실패했습니다.");
           
        }
        
    }    
}

// 버튼 클릭 이벤트 처리 함수
function handleButtonClick(event) {
	var paymentType = event.target.value;
	
    var localButtons = document.querySelectorAll('.button'); // 지역 변수로 변경
    // 모든 버튼에서 'active' 클래스 제거
    localButtons.forEach(function(btn) {
        btn.classList.remove('active');
    });

    // 현재 클릭한 버튼에만 'active' 클래스 추가
    event.target.classList.add('active');

    // 클릭한 버튼의 값을 결제 수단에 바로 반영
    var paymentValue = event.target.value;
    document.querySelector('input[name="payment"]').value = paymentValue;
    document.querySelector('.active').innerText = paymentValue;

    // card 함수 호출
    card(paymentType);
    
}

document.addEventListener("DOMContentLoaded", function() {
    var buttons = document.querySelectorAll('.button');

    // 각 버튼에 클릭 이벤트 리스너 등록
    buttons.forEach(function(btn) {
        btn.addEventListener('click', handleButtonClick);
    });
});

function buying() {
	
	let item_no = document.querySelector('input[name="item_no"]').value;
	let item_stock = document.querySelector('input[name="item_stock"]').value;
	let m_idx = document.querySelector('input[name="m_idx"]').value;
	let category_no = document.querySelector('input[name="category_no"]').value;
	let payment = document.querySelector('input[name="payment"]').value;
	
	let url = "BuyingCheck";
	let param ="item_no="+item_no+
				"&item_stock="+item_stock+
				"&m_idx="+m_idx+
				"&category_no="+category_no+
				"&payment="+payment;
	
	sendRequest(url, param, BuyingCheck, "post");
	
}


function BuyingCheck() {
   
            if (xhr.readyState == 4 && xhr.status == 200) {
                	var data = xhr.responseText;
                	var json = (new Function('return' + data))();
                	
            if (!confirm("정말 결제하시겠습니까? 진짜로??")) {
                	  alert("결제를 취소하셨습니다. 홈 화면으로 돌아갑니다."); 
                	  return "payitem";
                	    } 
            
            else if (json[0].result == "yes") {
                   	 alert("결제에 성공하셨습니다. 상품 화면으로 돌아갑니다.");
                   	location.href="payitem";
               	 } 	else if(json[0].result="no") {
                   	 alert("결제에 실패하셨습니다. 결제화면으로 돌아갑니다.");
                   	location.href="payitem";
                	}
          
            	}
    		}
		
		function payitem(f) {
			
			f.submit();
		}


    </script>

</head>
<body>

<h2 style="margin-top:100px;">결제 정보</h2>
<hr>
<form action="payitem" method="post" name="f">
<div>
	    <c:forEach var="dto" items="${Payinglist}">
	        <div class="container">
		            <c:choose>
		                <c:when test="${dto.item_no eq itemDTO.item_no }">
		                    <img src="${pageContext.request.contextPath}/resources/images/item/${itemDTO.item_image}.jpg" width="200" height="200" class="image">
		                </c:when>
		                <c:otherwise>
		                    <!-- 이곳에 다른 처리를 위한 코드를 작성 -->
		                </c:otherwise>
		            </c:choose>
		         <div class="text-container">
		                <input type="hidden" name="payment" value="${dto.payment}">
		                <input type="hidden" name="item_no" value="${dto.item_no}">
		                <input type="hidden" name="item_stock" value="${dto.item_stock}">
		                <input type="hidden" name="category_no" value="${dto.category_no}">
		                <input type="hidden" name="m_idx" value="${dto.m_idx}">
		                <span class="brand">${dto.brand}</span>
		                <span class="item_name">${dto.item_name}</span>
		                <span class="quantity">총 1개</span>
		                <span class="item_price">${dto.item_price}</span>
		         </div>
	        </div>
	    </c:forEach>
</div>
</form>		
	<table border="1">
		<c:forEach var="dto" items="${Payinglist}">
			<tr>
				<td>결제 금액</td>
			</tr>
			<tr>
				<td colspan="2">상품 금액</td>
				<td>${dto.item_price }</td>
			</tr>
			<tr>
				<td colspan="2">할인 금액</td>
				<td>0원</td>
			</tr>
			<tr >
				<td colspan="2">쿠폰</td>
				<td>0원</td>
			</tr>
			<tr>
				<td colspan="2">결제 수단</td>
				<td>${dto.payment }</td>
			</tr>
			<tr>
				<td colspan="2">결제 금액</td>
				<td>${dto.item_price }</td>
			</tr>
			 <tr>
			 	<td><input type="button" value="결제하기" onclick="buying()"></td>
			 </tr>	
		 </c:forEach>
	</table>		
			
		<h2 style="margin-top: -94px;">결제수단</h2>
		<input type="button" value="신용카드" onclick="card('신용카드')" class="button" name="credit_button">
		<input type="button" value="휴대전화" onclick="card('휴대전화')" class="button" name="phone_button">
		<input type="button" value="삼성페이" onclick="card('삼성페이')" class="button" name="pay_button">
		<input type="button" value="계좌이체" onclick="card('계좌이체')" class="button" name="send_button">

</body>
</html>