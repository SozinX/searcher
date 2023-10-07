<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../styles/base.css" type="text/css">
    <link rel="stylesheet" href="../styles/search.css" type="text/css">
    <link rel="icon" type="image/x-icon" href="./images/logo.svg">
    <title>Пошук</title>
</head>
<body>

<div class="left-side">
    <img src="./images/logo.svg" alt="Logo"/>
    <#if adminMode?? && adminMode>
        <#include "fileUpload.ftl">
    </#if>
</div>
<div class="right-side">
    <div class="page-title <#if value=="">centering</#if>">Довідково - пошукова система автомобільного заводу</div>
    <div class="search-side">
        <form class="search" action="/search" method="get">
            <img src="./images/search.svg" alt="Search"/>
            <input class="search-input" name="value" value="${value}" type="text" placeholder="Введіть запит для пошуку">
        </form>
        <#if searchResult??>
            <#if searchResult?has_content>
                <div class="search-result">
                    <#list searchResult as searchItem>
                        <a class="file-item" href="/${searchItem.id}">
                            <div class="file-name">${searchItem.fileName}</div>
                            <div class="file-size">Розмір: ${searchItem.size}Б</div>
                            <div class="file-date">Дата створення: ${searchItem.createdDate}</div>
                        </a>
                    </#list>
                </div>
                <#elseif value != "">
                <div class="search-result">
                    <div class="nothing-found">Нічого не знайдено, спробуйте інший запит</div>
                </div>
            </#if>
        </#if>

    </div>
</div>
</body>
</html>