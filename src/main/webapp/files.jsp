<%@ page import="models.MyFile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Files</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
${fileDate}
<h1>
    📂<%= request.getAttribute("currentFolder") %>
</h1>
<h4>
    Корни:
    <c:forEach var="r" items="${roots}">
        <a href ="files?path=${r}">${r}</a>
    </c:forEach>
</h4>
<hr>
<a href="files?path=${parentFolder}">Вверх</a>
<table cellspacing="7">
    <tr>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>
    <c:forEach var="f" items="${files}">
        <tr>
            <td>
                <c:if test="${f.getFile().isDirectory()}">
                    <span>📂</span>
                    <a href="files?path=${f.getFile().getAbsolutePath()}">
                            ${f.getFile().getName()}
                    </a>
                </c:if>
                <c:if test="${!f.getFile().isDirectory()}">
                    <span>🗋</span>
                    <a href="download?downloaded_file=${f.getFile().getAbsolutePath()}">
                            ${f.getFile().getName()}
                    </a>
                </c:if>
            </td>
            <td>
                <c:if test="${!f.getFile().isDirectory()}">
                    ${f.getFormattedSize()}
                </c:if>
            </td>
            <td>
                ${f.getDate()}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
