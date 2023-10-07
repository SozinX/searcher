<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../styles/base.css" type="text/css">
    <link rel="stylesheet" href="../styles/fileProfile.css" type="text/css">
    <link rel="icon" type="image/x-icon" href="./images/logo.svg">
    <title>${file.fileName}</title>
</head>
<body>
<div class="left-side">
    <img src="./images/logo.svg" alt="Logo"/>
    <#if adminMode?? && adminMode>
        <div class="trash" style="">
            <form action="/remove/${file.id}" method="post">
                <button class="circle" type="submit">
                        <img src="./images/trash.svg" alt="Delete"/>
                </button>

            </form>
        </div>
    </#if>
</div>
<div class="right-side">
    <div class="page-title">Довідково - пошукова система автомобільного заводу</div>
    <div class="iframe-zone">
        <div class="background" style="">
            <iframe src="data:application/pdf;base64,${displayFile}"></iframe>
        </div>
        <div class="title"></div>
        <a href="javascript:history.back()" >
            <img src="./images/arrow.svg" alt="Arrow"/>
        </a>
        <div class="file-name">${file.fileName}</div>
        <div class="file-date">${file.createdDate}</div>
        <div class="file-size" style="">${file.size} Б</div>
    </div>
</div>

</body>
</html>