<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.row {
  margin: 0px auto;
  width:700px;
}
h3 {
   text-align: center;
}
</style>
<script type="text/javascript">
function postfind()
{
	window.open("postfind.jsp","postfind","width=450,height=350,scrollbars=yes");
}
</script>
</head>
<body>
   <!-- 화면 출력 -->
   <div class="container">
     <h3>회원가입</h3>
     <div class="row">
      <form name="frm">
       <table class="table">
        <tr>
         <td class="success" width=15%>우편번호</td>
         <td width=85%>
           <input type=text size=7 readonly name=post1>-<input type=text size=7 readonly name=post2>
           <input type=button class="btn btn-sm btn-danger" value="우편번호검색" onclick="postfind()">
         </td>
        </tr>
        <tr>
          <td class="success" width="15%">주소</td>
          <td width=85%>
           <input type=text size=35 readonly name=addr>
          </td>
        </tr>
       </table>
      </form>
     </div>
   </div>
</body>
</html>














