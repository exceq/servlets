<%@ page import="models.MyFile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Файлы</title>
</head>
<body>

${fileDate}
<h1>
    📂<%= request.getAttribute("currentFolder") %>
</h1>
<div align="right">
    <button>
        <a href="/logout">Выйти</a>
    </button>
</div>
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
                    <c:url var="folderURL" value="/files">
                        <c:param name="path" value="${f.getFile().getAbsolutePath()}"/>
                    </c:url>
                    <a href="${folderURL}">
                            ${f.getFile().getName()}
                    </a>
                </c:if>
                <c:if test="${!f.getFile().isDirectory()}">
                    <span>🗋</span>
                    <c:url var="downloadURL" value="/download">
                        <c:param name="downloaded_file" value="${f.getFile().getAbsolutePath()}"/>
                    </c:url>
                    <a href="${downloadURL}">
                            ${f.getFile().getName()}
                    </a>
                </c:if>
            </td>
            <td>
                <c:if test="${!f.getFile().isDirectory()}">
                    ${f.formatSize()}
                </c:if>
            </td>
            <td>
                    ${f.formatDate()}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
