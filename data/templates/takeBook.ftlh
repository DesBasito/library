<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Books</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-pV0wnJ7PUQO7abOt+jiM72Of4wrgtOPfck+C5gUnibl00Rpakl8E6c5jIxMFwYkN" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.min.js" integrity="sha384-F1ABmEaJQ+H9k3OeKaF2+p4PZ4JiHM7T4weFG0+FsBJ7jztkciY/AZ+6lLrwBf8V" crossorigin="anonymous"></script>
</head>

<head>
        <nav class="navbar navbar-expand-md bg-dark " data-bs-theme="dark">
            <div class="container-xxl">
                <a class="navbar-brand" href="#"><img class="logo" src="images/logo.png" alt="instroom"></a>
                <button class="navbar-toggler text-light bg-orange btn-user ms-auto" type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <i class="bi bi-list-nested"></i>
                </button>
                 <button type="button" class="btn btn-link btn-user bg-orange rounded-1 text-light bg-orange order-md-1 ms-3"
                                    onclick="window.location.href='/profile'"
                                    data-bs-toggle="tooltip" data-bs-placement="left" title="Profile">
                                <i class="bi bi-person-circle"></i>
                            </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav fw-medium mx-auto">
                                            <li class="nav-item">
                                                <a class="nav-link active" aria-current="page" href="/books">О книгах
                                                </a>
                                            </li>
                                            <li class="mx-3 nav-item">
                                                <a class="nav-link " href="/employees">Сотрудники</a>
                                            </li>
                                            <li class="mx-3 nav-item">
                                                <a class="nav-link" href="/journal">Журнал</a>
                                            </li>
                                            <li class="mx-3 nav-item">
                                              <a class="nav-link" href="/giveBack">Вернуть книгу</a>
                                           </li>
                                        </ul>
                </div>

                </div>
            </div>
        </nav>
    </head>


    <div class="bg-secondary py-5">
            <div class="container book-info">
                <div class="row row-cols-1 row-cols-lg-2 row-cols-md-1">
                <div class="col ">
                <h2 class="text-light bg-secondary">Свободные книги:</h2>
                    <table class="table table-success table-striped">
                      <thead>
                         <tr>
                                <th scope="col">#</th>
                                <th scope="col">Book</th>
                                <th scope="col">Author</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list books as book>
                            <#if book.free==true>
                            <tr>
                                <th>${book.id}</th>
                                <td><a class = "nav-link" href="/books/about?id=${book.id}">${book.title}</a></td>
                                <td>${book.author}</td>
                            </tr>
                            </#if>
                      </#list>
                      </tbody>
                    </table></div>
                    <div class="col deWALT  text-light ps-5 align-self-center">
                   <main>
                           <form action="/takeBook" method="post">
                               <fieldset>
                               <div class="d-grid gap-2 col-7 mx-auto">
                                  <div class="form-element">
                                                      <label for="guest">Введите ID книги которую хотите взять</label>
                                                      <input type="number" class="form-control mt-2 mb-4" name="bookId" required placeholder="Id книги" autofocus>
                                                  </div>
                                                  </div>

                                   <div class="d-grid gap-2 col-7 mx-auto">
                                   <#if readingBooks>
                                   <button class="btn btn-danger" disabled type="submit">взять книгу</button>
                                   Больше 2 книг в одни руки не даем!
                                   <#else>
                                   <button class="btn btn-secondary" type="submit">взять книгу</button>
                                   </#if>
                                   </div>
                               </fieldset>
                           </form>
                       </main>
                    </div>
                </div>
            </div>
        </div>




</body>

<footer class="bg-dark py-5">
        <div class="container footer-container ">
            <div class=" opacity-75 mb-2 ">
                <a href="#" class="text-decoration-none text-light">Соглашение пользователя </a>
                <p>«Copyright © Название 2023»</p>
            </div>

            <div class="numbers ms-lg-5 mb-2 ms-md-3 ps-3  ">
                <p>+7 495 120-32-14</p>
                <p>+7 495 120-32-15</p>
            </div>

            <div class="links ms-lg-auto ps-3 ms-md-auto">
                <a href="https://youtu.be/HSt0ZMl4TVQ?si=wrmAXkU2aN_8HxDy"
                    class="btn text-decoration-none text-dark  bg-light"><i class="bi bi-instagram"></i></a>
                <a href="#" class="btn text-decoration-none text-dark  bg-light ms-4"><i class="bi bi-twitter"></i></a>
            </div>
        </div>
    </footer>
</html>