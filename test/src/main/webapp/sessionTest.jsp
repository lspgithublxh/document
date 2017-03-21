<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
</head>


<body>
<h3> Session Test: </h3>
<br>
<h3>Usuario.nome = <s:property value="%{#session.Usuario.nome}" /></h3>

</body>

</html>