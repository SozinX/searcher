<div class="file-upload">
    <div class="upload-title">Додати файл:</div>
    <form method="POST" action="/upload" enctype="multipart/form-data">
        <input type="file" id="files" name="files" accept=".pdf" multiple required>
        <br><br>
        <input class="button" type="submit" value="Додати">
    </form>
</div>
